package com.example.qtminer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    Thread thread1 = null;
    EditText etIP;
    String server_ip;
    static final int SERVER_PORT=8080;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etIP = findViewById(R.id.etIP);
        Button btnConnect = findViewById(R.id.btnConnect);
        //Crea la connessione
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                server_ip =etIP.getText().toString().trim();
                thread1 =new Thread(new Thread1());
                thread1.start();
            }
        });
        Button btnInfo =findViewById(R.id.buttonI);
        btnInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                runInfo();
            }
        });
    }
    class Thread1 implements Runnable {
        public void run() {

            try {
                SocketHandler.setSocket( new Socket(server_ip, SERVER_PORT));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext()," Connected!! ",Toast.LENGTH_SHORT).show();
                        runDialog();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void runInfo(){
        Intent info = new Intent(this,Info.class);
        startActivity(info);
    }
    public void runDialog(){
        Intent dialog = new Intent(this, Dialog.class);
        startActivity(dialog);
    }

}
