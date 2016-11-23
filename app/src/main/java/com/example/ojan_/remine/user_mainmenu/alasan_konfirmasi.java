package com.example.ojan_.remine.user_mainmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ojan_.remine.R;

import java.util.ArrayList;

public class alasan_konfirmasi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alasan_konfirmasi);

        String[] radio={"Karena Rusak", "karena A", "karena B"};
        String[] TV={"Karena Rusak", "Karena A", "karena B"};
        String[] komputer={"Karena Rusak", "Karena A", "Karena B"};

        ArrayAdapter alasan_reparasi = new ArrayAdapter(this, R.layout.listview_alasan_reparasi); //setup array dari database
        ListView tampil_list_alasan = (ListView) findViewById(R.id.list_alasan_reparasi); //tampilkan list reparasi
        tampil_list_alasan.setAdapter(alasan_reparasi);



    }


    public void check_data(){
        //nanti, tunggu setup data


    }
}
