package com.example.qtminer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Dialog extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydialog1);
        final android.app.Dialog dialog = new android.app.Dialog(Dialog.this);

        // Carico il layout della dialog al suo intenro
        dialog.setContentView(R.layout.mydialog);

        //Titolo
        dialog.setTitle("Choose an Option");
        //Evito che cliccando fuori scompaia il dialog
        dialog.setCancelable(false);

        //Gestisco il bottone di chiusura della dialog (quello al centro)
        ImageView imgclose = dialog.findViewById(R.id.image1);
        imgclose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent main = new Intent(getBaseContext(),MainActivity.class);
                startActivity(main);
            }
        });

        //Gestisco il bottone, della dialog per il file
        final Button button_dialog_to_call =  dialog.findViewById(R.id.file);
        button_dialog_to_call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                runFile();
            }
        });

        //Gestisco il bottone, della dialog per il db
        final Button button_dialog_reply =  dialog.findViewById(R.id.Db);
        button_dialog_reply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                runDataB();
            }
        });
        // Faccio comparire la dialog
        dialog.show();
    }
    //Fa partire l'activity FromFile
    public void runFile(){
        Intent file = new Intent(this, FromFile.class);
        startActivity(file);
    }
    //Fa partire l'activity DataB
    public void runDataB(){
        Intent datab = new Intent(this,DataB.class);
        startActivity(datab);
    }


}