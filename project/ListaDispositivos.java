package com.example.terminal;

import android.Manifest;
import android.app.ListActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;

import java.util.Set;

import android.app.Activity;

import androidx.core.app.ActivityCompat;

public class ListaDispositivos extends ListActivity {

    ArrayAdapter<String> arrayAdaptador = null;
    BluetoothAdapter adaptadorBluetooth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrayAdaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        adaptadorBluetooth = BluetoothAdapter.getDefaultAdapter();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Set<BluetoothDevice> dispositivosPareados = adaptadorBluetooth.getBondedDevices();

            if(dispositivosPareados.size() > 0){
                for(BluetoothDevice dispositivo: dispositivosPareados){
                    String nome = dispositivo.getName();
                    String enderecoMAC = dispositivo.getAddress();

                    arrayAdaptador.add(nome + "\n" + enderecoMAC);
                }
            }

            setListAdapter(arrayAdaptador);
        } else {
            Mensagem.toastMsg(this, "Sem permissão para acessar pareados");
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String texto = ((TextView) v).getText().toString();
        String enderecoMAC = texto.substring(texto.length() - 17);

        Intent retornaMAC = new Intent();
        retornaMAC.putExtra("enderecoMAC", enderecoMAC);
        setResult(Activity.RESULT_OK, retornaMAC);
        finish();
    }
}
