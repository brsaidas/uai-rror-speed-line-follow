/*
* Parametro:
* A -> CACE
* B -> CDES
* C -> VMAX
* D -> VMIN
* E -> VB
* F -> VM
* G -> KP
* H -> KD
* I -> LIGAR
* J -> ATUALIZAR DADOS
* */

#include <SoftwareSerial.h>

#define PINOLED 13

int CACE = 10;
int CDES = 20;
int VMAX = 30;
int VMIN = 40;
int VB = 50;
int VM = 60;
int KP = 70;
int KD = 80;

bool ligado = false;
String blocoMensagem = "";

SoftwareSerial bluetooth(5, 3);

void setup() {
  Serial.begin(9600);
  bluetooth.begin(9600);

  pinMode(PINOLED, OUTPUT);
  digitalWrite(PINOLED, ligado);
}

void mudarParametro(char parametro, int valor) {
  switch(parametro) {
    case 'A':
      CACE = valor;
      break;
    case 'B':
      CDES = valor;
      break;
    case 'C':
      VMAX = valor;
      break;
    case 'D':
      VMIN = valor;
      break;
    case 'E':
      VB = valor;
      break;
    case 'F':
      VM = valor;
      break;
    case 'G':
      KP = valor;
      break;
    case 'H':
      KD = valor;
      break;
  }
}

void enviarParametros() {
  bluetooth.print("{A" + String(CACE) + "}");
  bluetooth.print("{B" + String(CDES) + "}");
  bluetooth.print("{C" + String(VMAX) + "}");
  bluetooth.print("{D" + String(VMIN) + "}");
  bluetooth.print("{E" + String(VB) + "}");
  bluetooth.print("{F" + String(VM) + "}");
  bluetooth.print("{G" + String(KP) + "}");
  bluetooth.print("{H" + String(KD) + "}");
}

void ligarDesligar() {
  ligado = !ligado;
  digitalWrite(PINOLED, ligado);
}

void imprimirDados() {
  Serial.println("--------------------");
  Serial.println("CACE: " + String(CACE));
  Serial.println("CDES: " + String(CDES));
  Serial.println("VMAX: " + String(VMAX));
  Serial.println("VMIN: " + String(VMIN));
  Serial.println("VB: " + String(VB));
  Serial.println("VM: " + String(VM));
  Serial.println("KP: " + String(KP));
  Serial.println("KD: " + String(KD));
  Serial.println("--------------------");
}

void loop() {
  if (bluetooth.available() > 0) {
    while(bluetooth.available() > 0) {
      char byte = bluetooth.read();

      if (byte == '{') {
        blocoMensagem = "";
      } else if (byte == '}') {
        
        if (blocoMensagem.length() > 1) {
          mudarParametro(blocoMensagem[0], blocoMensagem.substring(1).toInt());
        } else if (blocoMensagem.length() == 1) {
          switch(blocoMensagem[0]) {
            case 'I':
              ligarDesligar();
              break;
            case 'J':
              enviarParametros();
              break;
            default:
              break;
          }
        }

        //imprimirDados();
      } else {
        blocoMensagem += byte;
      }
    }
  }
}
