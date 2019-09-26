package com.example.qtminer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class DataB extends AppCompatActivity {

    EditText etTable, etRadius;
    TextView tvMessages;
    String Table_name;
    double Radius;
    static String testo;
    static Socket s;
    static ObjectOutputStream out;
    static ObjectInputStream in;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datab);
        etTable = findViewById(R.id.TableD);
        etRadius = findViewById(R.id.RadiusD);
        tvMessages = findViewById(R.id.ShowClustersD);
        //carica gli stream della Socket
        Connection c= new Connection();
        c.start();
        //Aspetto che finisca il ciclo del thread
        try {
            c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Button btnInvio = findViewById(R.id.InvioD);
        btnInvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvMessages.setText(" ");
                Table_name = etTable.getText().toString().trim();
                String g = etRadius.getText().toString().trim();
                //controllo che il testo inserito non sia nullo
                if (!g.isEmpty() && !Table_name.isEmpty()) {
                    Radius = Double.parseDouble(g);
                    //Fa partire i messaggi tra server e client
                    Thread3 t = new Thread3(Table_name,Radius);
                    t.start();
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tvMessages.setMovementMethod(new ScrollingMovementMethod());
                    if (testo != null) {
                        if (testo.equals("SQL_Error")) {
                            Toast.makeText(getApplicationContext(), "Table doesnt't match any existings tables", Toast.LENGTH_LONG).show();
                            runActivityMain();
                        } else if (testo.equals("ClusteringRadiusException")) {
                            Toast.makeText(getApplicationContext(), " Too much tuples in one cluster !!", Toast.LENGTH_LONG).show();
                            runActivityMain();
                        } else {
                            testo += " \n File saved";
                            tvMessages.setText(testo);
                        }
                    }
                }
            }
        });

    }

    static class Connection extends Thread {
        @Override
        public void run() {
                out =SocketHandler.getObjectout();
                in = SocketHandler.getObjectin();
        }
    }

    static class Thread3 extends Thread {
        String tab;
        double rag;
        Thread3(String tabella, double raggio){
            tab=tabella;
            rag=raggio;
        }
        @Override
        public void run() {

            try {
                out.writeObject(0);

                out.writeObject(tab);

                String result = (String) in.readObject();
                if (!result.equals("OK")) {
                    testo = "SQL_Error";
                    this.interrupt();
                }
                out.writeObject(1);

                out.writeObject(rag);
                result = (String) in.readObject();
                if (result.equals("OK"))
                    testo= "Number of Clusters:" + in.readObject() +" \n" + in.readObject();
                else if(result.equals("ErroreC")) {
                    Log.i("err",result);
                    testo="ClusteringRadiusException";
                }else{
                    testo="SQL_Error";
                    this.interrupt();
                }
                out.writeObject(2);

                result = (String) in.readObject();

                if (!result.equals("OK")) {
                    testo = "SQL_Error";
                    this.interrupt();
                }
            }catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(DataB.this);
        builder.setTitle("Finish");
        builder.setMessage("Would you repeat");
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                runOptions();
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void runOptions() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DataB.this);
        builder.setTitle("Exit");
        builder.setMessage("Would you choose a new Option?");
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent main = new Intent(getBaseContext(), MainActivity.class);
                startActivity(main);
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                runDialog();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void runDialog() {
        Intent dialog = new Intent(this, Dialog.class);
        startActivity(dialog);
    }

    public void runActivityMain() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
}