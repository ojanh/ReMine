package com.example.ojan_.remine.user_mainmenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ojan_.remine.Custom_listview.Adapter_alasanReparasi;
import com.example.ojan_.remine.Custom_listview.Alasan_reparasi;
import com.example.ojan_.remine.DBcomms;
import com.example.ojan_.remine.R;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class alasan_konfirmasi extends AppCompatActivity {

    SharedPreferences shpref;
    String alamat_toko, nama_toko, kategori;

    TextView namaToko, alamatToko, hargaTotal;
    ListView list_alasan;
    EditText alasan_lain;
    List<Alasan_reparasi> list_reparasi = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alasan_konfirmasi);

        shpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        kategori=shpref.getString("kategori", "TV");
        Log.d("shared pref", "kategori: " + kategori);

        getData_fromActivity();

        String[] radio={"Karena Rusak", "karena A", "karena B"};
        String[] TV={"Karena Rusak", "Karena A", "karena B"};
        String[] komputer={"Karena Rusak", "Karena A", "Karena B"};


        namaToko = (TextView) findViewById(R.id.alasanReparasi_text_namaToko);
        alamatToko = (TextView) findViewById(R.id.alasanReparasi_text_alamatToko);
        ListView list_alasan = (ListView) findViewById(R.id.alasanReparasi_listView_alasanReparasi); //tampilkan list reparasi
        alasan_lain = (EditText) findViewById(R.id.alasanReparasi_editText_alasanLain);



        switch (kategori){
            case "TV" : addList(TV); break;
            case "radio" : addList(radio); break;
            case "komputer":addList(komputer); break;
            default:
                addList(TV);
        }

        Adapter_alasanReparasi adapterAlasan = new Adapter_alasanReparasi(this, list_reparasi);
        list_alasan.setAdapter(adapterAlasan);


    }

    private void addList(String[] data) {
        int i;
        for (i=0; i < data.length; i++){
            list_reparasi.add(new Alasan_reparasi(i, false, data[i]));

        }

    }

    private void getData_fromActivity() {
        Intent getData = getIntent();
        nama_toko = getData.getStringExtra("nama_toko");
        alamat_toko = getData.getStringExtra("alamat_toko");
        Log.d("dataFromIntent", "nama toko: "+ nama_toko + ", Alamat Toko: " + alamat_toko );

    }


    private void konfirmasi(){
        String alasan_lain_Str = alasan_lain.getText().toString();

        if (!(TextUtils.isEmpty(alasan_lain_Str))){
            AlertDialog.Builder alert_adaAlasanLain;

            alert_adaAlasanLain = new AlertDialog.Builder(this).setTitle("ada alasan lain");
            alert_adaAlasanLain.setMessage("Jika ada alasan lain, maka ada kemungkinan penambahan harga tidak tertera pada aplikasi" +
                    "ini. \n\n Mau Lanjut ?");
            alert_adaAlasanLain.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            alert_adaAlasanLain.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    return;
                }
            });

        }

        else {

        }
    }


}
