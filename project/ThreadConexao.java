package com.example.terminal;

import java.io.OutputStream;
import android.bluetooth.BluetoothSocket;
import java.io.IOException;
import android.content.Context;
import java.io.InputStream;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.os.SystemClock;
import android.widget.ImageButton;

public class ThreadConexao extends Thread {
    private Context contexto = null;
    private BluetoothSocket socket = null;
    private Handler manipulador = null;
    private InputStream fluxoEntrada = null;
    private OutputStream fluxoSaida = null;

    int inicio = -1;
    String entrada = "", bloco = "";

    public ThreadConexao(Context contexto, BluetoothSocket socket, Handler manipulador){
        this.manipulador = manipulador;
        this.socket = socket;
        this.contexto = contexto;

        try{
            fluxoEntrada = socket.getInputStream();
            fluxoSaida = socket.getOutputStream();
        }catch(IOException e){
            Mensagem.alertaMsg(contexto, "Erro ao configurar saída de dados");
        }
    }

    @Override
    public void run(){
        while(true){
            try{
                char letra = (char) fluxoEntrada.read();

                entrada += letra;

                switch(letra){
                    case '{':
                        if(inicio != -1){
                            entrada = "{";
                        }

                        inicio = entrada.length() - 1;
                        break;
                    case '}':
                        if(inicio != -1){
                            bloco = entrada.substring(inicio + 1, entrada.length() - 1);

                            //Mensagem recebida de forma confiavel
                            Message msg = manipulador.obtainMessage(MainActivity.MENSAGEM_LIDA, bloco);
                            manipulador.sendMessage(msg);

                            inicio = -1;
                        }

                        entrada = "";
                        break;
                    default:
                        break;
                }

                SystemClock.sleep(10);
            }catch(IOException e){
                break;
            }
        }
    }

    public void enviar(String texto, boolean conexao, ImageButton butao){
        try{
            fluxoSaida.write(texto.getBytes());
        }catch(IOException e){
            try{
                socket.close();

                conexao = false;
                //butao.setText("Conectar");
            }catch(IOException ex){
                conexao = true;
                //butao.setText("Desconectar");

                Mensagem.alertaMsg(contexto, "Erro ao fechar conexao");
            }

            Mensagem.alertaMsg(contexto, "Erro ao enviar os dados");
        }
    }
}
