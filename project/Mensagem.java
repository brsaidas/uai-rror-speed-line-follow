package com.example.terminal;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

public class Mensagem {

    public static void alertaMsg(Context contexto, String texto){
        AlertDialog.Builder alerta = new AlertDialog.Builder(contexto);
        alerta.setMessage(texto);
        alerta.setNeutralButton("OK", null);
        alerta.show();
    }

    public static void toastMsg(Context contexto, String texto){
        Toast.makeText(contexto, texto, Toast.LENGTH_LONG).show();
    }
}
