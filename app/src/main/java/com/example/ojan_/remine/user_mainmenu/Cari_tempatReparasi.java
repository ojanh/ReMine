package com.example.ojan_.remine.user_mainmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ojan_.remine.R;

public class Cari_tempatReparasi extends AppCompatActivity {

        //tampilkan data ke dalam
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_tempat_reparasi);

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
}
