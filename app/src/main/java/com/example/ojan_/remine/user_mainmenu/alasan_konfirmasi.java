package com.example.ojan_.remine.user_mainmenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ojan_.remine.DBcomms;
import com.example.ojan_.remine.R;

import java.net.URLEncoder;

public class alasan_konfirmasi extends AppCompatActivity {

    SharedPreferences shpref;
    String id_toko;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alasan_konfirmasi);

        shpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        getData_fromActivity();

        String[] radio={"Karena Rusak", "karena A", "karena B"};
        String[] TV={"Karena Rusak", "Karena A", "karena B"};
        String[] komputer={"Karena Rusak", "Karena A", "Karena B"};

        ArrayAdapter alasan_reparasi = new ArrayAdapter(this, R.layout.listview_alasan_reparasi); //setup array dari database
        ListView tampil_list_alasan = (ListView) findViewById(R.id.list_alasan_reparasi); //tampilkan list reparasi
        tampil_list_alasan.setAdapter(alasan_reparasi);



    }

    private void getData_fromActivity() {
        Intent getData = getIntent();
        id_toko = getData.getStringExtra("id_toko");
        Log.d("dataFromIntent", "id_toko: " + id_toko);

    }


    class check_toko extends AsyncTask<String, Void, String> {


        @Override/*String postData = URLEncoder.encode()*/
        protected String doInBackground(String... params) {

            DBcomms look_detailToko = new DBcomms();






            return null;
        }
    }
}
