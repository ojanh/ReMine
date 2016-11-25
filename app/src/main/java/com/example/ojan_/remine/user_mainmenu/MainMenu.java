package com.example.ojan_.remine.user_mainmenu;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ojan_.remine.R;

public class MainMenu extends AppCompatActivity {
    TextView username;
    TextView welcome_menu;
    ImageButton radio_button;
    ImageButton tv_button;
    ImageButton komputer_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main_menu);

        //set setelah activity ditampilkan
        username=(TextView) findViewById(R.id.username_loginMenu);
        welcome_menu=(TextView) findViewById(R.id.MainMenu_welcome_text);
        radio_button=(ImageButton) findViewById(R.id.radioservice_button);
        tv_button=(ImageButton) findViewById(R.id.tv_icon);
        komputer_button=(ImageButton) findViewById(R.id.komputer_icon);

        SharedPreferences shpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String getUsername=shpref.getString("username","default");
        Log.d("username", getUsername);


        welcome_menu.setText("Mau Service Apa, " + getUsername + " ?");

        radio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent radioActivity = new Intent(getApplicationContext(), Cari_tempatReparasi.class);
                radioActivity.putExtra("pilih", "radio");
                startActivity(radioActivity);

            }
        });

        tv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        komputer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }




}


