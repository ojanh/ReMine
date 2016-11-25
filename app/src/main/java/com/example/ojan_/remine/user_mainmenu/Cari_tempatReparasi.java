package com.example.ojan_.remine.user_mainmenu;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ojan_.remine.R;

public class Cari_tempatReparasi extends AppCompatActivity {
    SharedPreferences shpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //tampilkan data ke dalam
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_tempat_reparasi);

        String pilihan = getIntent().getStringExtra("pilih");


        //tambahan sistem:
        /*Google Map Fragment
         * Buat List View jadi button */

        /* Algoritma:
        * cari di database sesuai dengan permintaan TV, radius 5 km
        * file diterima dalam JSON, ubah ke dalam Array
        * Dari Array, tampilkan Di List View
        * */

        ArrayAdapter list_reparasi = new ArrayAdapter(this, R.layout.listview_cari_reparasi); //setup array dari database
        ListView tampil_list_reparasi = (ListView) findViewById(R.id.list_reparasi); //tampilkan list reparasi
        tampil_list_reparasi.setAdapter(list_reparasi);

    }

    class getInfo extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... params) {
            String IP_addr= shpref.getString("IP_address","127.0.0.1");
            Log.d("conn", "IP addr :" + IP_addr );



            return null;
        }


    }
}

