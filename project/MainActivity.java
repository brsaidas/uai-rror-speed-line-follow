package com.example.terminal;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.ScrollView;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.util.UUID;
import java.io.IOException;

import android.os.Handler;
import android.widget.LinearLayout;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {

    private static final int SOLICITAR_CONEXAO = 2;
    public static final int MENSAGEM_LIDA = 3;

    public boolean conexao = false;

    ImageButton bt_conexao = null;
    SeekBar sb_velocidadeBase = null;
    TextView tv_velocidadeBase = null;
    SeekBar sb_velocidadeMaxima = null;
    TextView tv_velocidadeMaxima = null;
    SeekBar sb_kp = null;
    TextView tv_kp = null;
    SeekBar sb_kd = null;
    TextView tv_kd = null;
    EditText et_constanteAceleracao = null;
    EditText et_constanteDesaceleracao = null;
    EditText et_velocidadeMaxima = null;
    EditText et_velocidadeMinima = null;
    ImageButton bt_enviar = null;

    BluetoothSocket socketBluetooth = null;
    BluetoothAdapter adaptadorBluetooth = null;
    BluetoothDevice dispositivoBluetooth = null;

    Handler manipulador = null;
    ThreadConexao threadConexao = null;

    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    ActivityResultLauncher<Intent> abreSolicitarBluetooth = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Mensagem.toastMsg(this, "Bluetooth ativado");
                } else {
                    Mensagem.toastMsg(this, "Bluetooth desativado, fechando app...");
                    finish();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recebendo as referencia
        bt_conexao = findViewById(R.id.bt_conexao);
        sb_velocidadeBase = findViewById(R.id.sb_velocidadeBase);
        tv_velocidadeBase = findViewById(R.id.tv_velocidadeBase);
        sb_velocidadeMaxima = findViewById(R.id.sb_velocidadeMaxima);
        tv_velocidadeMaxima = findViewById(R.id.tv_velocidadeMaxima);
        sb_kp = findViewById(R.id.sb_kp);
        tv_kp = findViewById(R.id.tv_kp);
        sb_kd = findViewById(R.id.sb_kd);
        tv_kd = findViewById(R.id.tv_kd);
        et_constanteAceleracao = findViewById(R.id.et_constanteAceleracao);
        et_constanteDesaceleracao = findViewById(R.id.et_constanteDesaceleracao);
        et_velocidadeMaxima = findViewById(R.id.et_velocidadeMaxima);
        et_velocidadeMinima = findViewById(R.id.et_velocidadeMinima);
        bt_enviar = findViewById(R.id.bt_enviar);

        bt_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviar("A", et_constanteAceleracao.getText().toString());
                enviar("B", et_constanteDesaceleracao.getText().toString());
                enviar("C", et_velocidadeMaxima.getText().toString());
                enviar("D", et_velocidadeMinima.getText().toString());
            }
        });

        sb_velocidadeBase.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_velocidadeBase.setText("VB: " + Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int i = seekBar.getProgress();
                enviar("E", Integer.toString(i));
            }
        });

        sb_velocidadeMaxima.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_velocidadeMaxima.setText("VM: " + Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int i = seekBar.getProgress();
                enviar("F", Integer.toString(i));
            }
        });

        sb_kp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_kp.setText("KP: " + Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int i = seekBar.getProgress();
                enviar("G", Integer.toString(i));
            }
        });

        sb_kd.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_kd.setText("KD: " + Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int i = seekBar.getProgress();
                enviar("H", Integer.toString(i));
            }
        });

        //Obtendo o driver bluetooth do apararelho
        adaptadorBluetooth = BluetoothAdapter.getDefaultAdapter();

        if (adaptadorBluetooth == null) {
            Mensagem.toastMsg(this, "Dispositivo não suporta bluetooth");
            finish();
        } else if (!adaptadorBluetooth.isEnabled()) {
            Intent solicitarBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            //Verificando se existe a permissão BLUETOOTH_CONNECT
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {}

            abreSolicitarBluetooth.launch(solicitarBluetooth);
        }

        manipulador = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MENSAGEM_LIDA) {
                    String bloco = msg.obj.toString();

                    //Log.d("RECEBIDO", "{" + bloco + "}");

                    mudarParametro(bloco.charAt(0), Integer.parseInt(bloco.substring(1)));
                }
            }
        };
    }

    private void mudarParametro(char parametro, int valor) {
        switch(parametro) {
            case 'A':
                et_constanteAceleracao.setText(Integer.toString(valor));
                break;
            case 'B':
                et_constanteDesaceleracao.setText(Integer.toString(valor));
                break;
            case 'C':
                et_velocidadeMaxima.setText(Integer.toString(valor));
                break;
            case 'D':
                et_velocidadeMinima.setText(Integer.toString(valor));
                break;
            case 'E':
                sb_velocidadeBase.setProgress(valor);
                break;
            case 'F':
                sb_velocidadeMaxima.setProgress(valor);
                break;
            case 'G':
                sb_kp.setProgress(valor);
                break;
            case 'H':
                sb_kd.setProgress(valor);
                break;
        }
    }

    /*
    * Tratando os resultados das Activity
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SOLICITAR_CONEXAO:
                if (resultCode == Activity.RESULT_OK) {
                    String enderecoMAC = data.getExtras().getString("enderecoMAC");
                    //Log.d("ERRORCONEXAO", enderecoMAC);

                    dispositivoBluetooth = adaptadorBluetooth.getRemoteDevice(enderecoMAC);

                    try {
                        //Verificando se existe a permissão BLUETOOTH_CONNECT
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {}

                        socketBluetooth = dispositivoBluetooth.createRfcommSocketToServiceRecord(uuid);
                        socketBluetooth.connect();

                        conexao = true;
                    } catch(IOException e) {
                        conexao = false;

                        Mensagem.toastMsg(this, "Erro ao criar conexão com o dispositivo");
                        Log.d("ERRORCONEXAO", e.getMessage());
                    }

                    if (conexao) {
                        threadConexao = new ThreadConexao(this, socketBluetooth, manipulador);
                        threadConexao.start();

                        Mensagem.toastMsg(this, "Bluetooth conectado");
                    }
                }
                break;
            default:
                break;
        }
    }

    //Metodos para os botoes
    public void enviar(String parametro, String dado) {
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
         * J -> ATUALIZAR
         * */

        if (conexao) {
            String dadoFormatado = "{" + parametro + dado + "}";
            threadConexao.enviar(dadoFormatado, conexao, bt_conexao);
        } else {
            Mensagem.toastMsg(this, "Não pareado");
        }
    }

    public void ligarDesligar(View v) {
        enviar("I", "");
    }

    public void atualizarDados(View v) {
        enviar("J", "");
    }

    public void conexao(View v){
        if(conexao){
            try{
                conexao = false;
                socketBluetooth.close();
            }catch(IOException e){
                conexao = true;
                Mensagem.toastMsg(this, "Erro ao fechar conexão com o dispositivo");
            }

            if(!conexao){
                Mensagem.toastMsg(this, "Bluetooth desconectado");
            }
        }else{
            Intent listaDispositivos = new Intent(this, ListaDispositivos.class);
            startActivityForResult(listaDispositivos, SOLICITAR_CONEXAO);
        }
    }

}