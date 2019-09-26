package com.example.qtminer;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class Info extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        TextView info = findViewById(R.id.TestoInfo);
        //Unisce le stringhe per creare un unico testo
        Appendable sampleString = new StringBuilder();
        try {
            sampleString.append(getString(R.string.Titolo)).append("\n");
            sampleString.append(getString(R.string.Incipit)).append("\n");
            sampleString.append(getString(R.string.Titolostep)).append("\n");
            sampleString.append(getString(R.string.step)).append("\n");
            sampleString.append(getString(R.string.Titolovantaggi)).append("\n");
            sampleString.append(getString(R.string.vantaggi)).append("\n");
            sampleString.append(getString(R.string.Titolosvantaggi)).append("\n");
            sampleString.append(getString(R.string.svantaggi)).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        info.setMovementMethod(new ScrollingMovementMethod());
        //Aggiusta i vari font e dimensioni del testo
        SpannableString spannableString = new SpannableString(sampleString.toString());
        spannableString.setSpan(new RelativeSizeSpan(1.5f), 0, 43, 0);
        spannableString.setSpan(new RelativeSizeSpan(1.3f), 710, 745, 0);
        spannableString.setSpan(new RelativeSizeSpan(1.3f), 1255, 1265, 0);
        spannableString.setSpan(new RelativeSizeSpan(1.3f), 1572, 1587, 0);
        info.setTextColor(ContextCompat.getColor(this, R.color.black));
        //Fa visualizzare il testo
        info.setText(spannableString);
    }
}