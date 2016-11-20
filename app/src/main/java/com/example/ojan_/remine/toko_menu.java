package com.example.ojan_.remine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class toko_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_menu);



        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.listview_toko_menu);
        ListView listView = (ListView) findViewById(R.id.list_request);
        listView.setAdapter(arrayAdapter);




    }
}
