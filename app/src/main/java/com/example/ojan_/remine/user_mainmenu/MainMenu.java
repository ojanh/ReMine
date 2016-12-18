package com.example.ojan_.remine.user_mainmenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ojan_.remine.R;

public class MainMenu extends AppCompatActivity {

    //Deklarasi Variabel dari GUI
    TextView username;
    TextView welcome_menu;
    ImageButton laptop_button;
    ImageButton printer_button;
    ImageButton komputer_button;
    Button gotoTest_button;
    private ImageButton Hape_button;

    //uncuk cek berapa kali tombol back dipencet
    int manyPressedBack = 0;


    //starting activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main_menu);

        //set setelah activity ditampilkan
        welcome_menu = (TextView) findViewById(R.id.MainMenu_welcome_text);
        laptop_button = (ImageButton) findViewById(R.id.umenu_laptop_button);
        printer_button = (ImageButton) findViewById(R.id.usermenu_im_printer);
        komputer_button = (ImageButton) findViewById(R.id.uname_im_komputer);
        Hape_button = (ImageButton) findViewById(R.id.umenu_im_handphone);

        //mengambil data username dari sharedPreferences
        SharedPreferences shpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String getUsername = shpref.getString("username", "default");
        Log.d("username", getUsername);

        //set text di welcome menu
        welcome_menu.setText("Mau Service Apa, " + getUsername + " ?");

        //saat dijalankan activity, akan dibuat thread onclick listener untuk menampilkan data
        laptop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoIntent("laptop");
            }
        });

        printer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoIntent("printer");
            }
        });

        komputer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoIntent("komputer");
            }
        });

        Hape_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoIntent("handphone");
            }
        });
    }


    //fungsi pindah intent
    private void gotoIntent(String value_toActivity) {
        Intent radioActivity = new Intent(getApplicationContext(), Cari_tempatReparasi.class);
        radioActivity.putExtra("pilih", value_toActivity);
        startActivity(radioActivity);
    }


    //menutup aplikasi
    @Override
    public void onBackPressed() {
        if (manyPressedBack>=1){
            Intent closeApp = new Intent(Intent.ACTION_MAIN);
            closeApp.addCategory(Intent.CATEGORY_HOME);
            closeApp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(closeApp);
            finish();
        }
        else {
            Toast.makeText(this, "tekan tombol back untuk menutup aplikasi", Toast.LENGTH_LONG).show();
            manyPressedBack++;
        }
    }

}


