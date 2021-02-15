package com.yandexindia.realcaller.ui;

import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yandexindia.realcaller.R;

public class Detail_activity extends AppCompatActivity { ;
    TextView textView;
    String Contactname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);
        textView = findViewById(R.id.contact);
        if (getIntent().getExtras() != null) {
            Contactname = getIntent().getStringExtra("ContactName");
        }
        if (Contactname != null) {
            textView.setText(Contactname);
        }

    }

}