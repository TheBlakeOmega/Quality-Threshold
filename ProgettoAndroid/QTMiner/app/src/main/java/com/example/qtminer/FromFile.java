package com.example.qtminer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class FromFile extends AppCompatActivity {

    private EditText etTable, etRadius;
    private TextView tvMessages;
    private String table_name;
    private double radius;
    private static String testo;
    private static Socket s;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file);
        Connection c = new Connection();
        c.start();
        try {
            c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        etTable = findViewById(R.id.Table);
        etRadius = findViewById(R.id.Radius);
        tvMessages = findViewById(R.id.ShowFiles);
        final Button btnInvio = findViewById(R.id.Invio);
        tvMessages.setMovementMethod(new ScrollingMovementMethod());
        btnInvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvMessages.setText("");
                table_name = etTable.getText().toString().trim();
                String g = etRadius.getText().toString().trim();
                if (!g.isEmpty() || !table_name.isEmpty()) {
                    radius = Double.parseDouble(g);
                    btnInvio.setEnabled(true);
                    Thread2 t = new Thread2(table_name, radius);
                    t.start();
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tvMessages.setText(testo);
                }
            }
        });
    }

    static class Connection extends Thread {
        @Override
        public void run() {
                out = SocketHandler.getObjectout();
                in = SocketHandler.getObjectin();
        }
    }

    static class Thread2 extends Thread {
        String tab;
        double rag;

        Thread2(String tabella, double raggio) {
            tab = tabella;
            rag = raggio;
        }

        @Override
        public void run() {
            try {
                out.writeObject(3);
                // out.reset();
                out.writeObject(tab);
                //out.reset();
                out.writeObject(rag);
                //out.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String result = "";
            try {
                result = (String) in.readObject();
                //in.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (result.equals("OK"))
                try {
                    testo = (String) in.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            else
                testo = "Input non validi";
        }
    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(FromFile.this);
        builder.setTitle("Exit");
        builder.setMessage("Would you choose a new Option?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                runDialog();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent main = new Intent(getBaseContext(), MainActivity.class);
                startActivity(main);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void runDialog() {
        Intent dialog = new Intent(this, Dialog.class);
        startActivity(dialog);
    }
}


