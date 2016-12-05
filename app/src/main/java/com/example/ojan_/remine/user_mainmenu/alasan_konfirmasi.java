package com.example.ojan_.remine.user_mainmenu;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ojan_.remine.Custom_listview.Adapter_alasanReparasi;
import com.example.ojan_.remine.Custom_listview.Alasan_reparasi;
import com.example.ojan_.remine.DBcomms;
import com.example.ojan_.remine.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class alasan_konfirmasi extends AppCompatActivity {

    SharedPreferences shpref;
    String user_id, alamat_toko, nama_toko, kategori, toko_id;
    String alasan_all, aditional_alasan;
    private double lintang;
    private double bujur;

    TextView namaToko, alamatToko, hargaTotal;
    ListView list_alasan;
    EditText alasan_lain;
    List<Alasan_reparasi> list_alasanReparasi = new ArrayList<>();


    Adapter_alasanReparasi adapterAlasan;
    Alasan_reparasi[] TV, radio, komputer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alasan_konfirmasi);

        shpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        setAlasan();
        getData_fromActivity();

        namaToko = (TextView) findViewById(R.id.alasanReparasi_text_namaToko);
        alamatToko = (TextView) findViewById(R.id.alasanReparasi_text_alamatToko);
        ListView list_alasan = (ListView) findViewById(R.id.alasanReparasi_listView_alasanReparasi); //tampilkan list reparasi
        alasan_lain = (EditText) findViewById(R.id.alasanReparasi_editText_alasanLain);
        Button button_konfirmasi = (Button) findViewById(R.id.alasanReparasi_button_konfirmasi);

        switch (kategori){
            case "TV" : Log.d("swit chos", "choosed TV" ); addList(TV); break;
            case "radio" : Log.d("swit chos", "choosed radio" ) ;addList(radio); break;
            case "komputer" : Log.d("swit chos", "choosed komputer" ) ;addList(komputer); break;
            default:
                addList(TV);
        }


        adapterAlasan = new Adapter_alasanReparasi(this, R.id.alasanReparasi_listView_alasanReparasi, list_alasanReparasi);
        list_alasan.setAdapter(adapterAlasan);

        namaToko.setText(nama_toko);
        alamatToko.setText(alamat_toko);

        button_konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                konfirmasi();
            }
        });


    }

    private void addList(Alasan_reparasi[] data) {
        int i;
        for (i=0; i<data.length; i++){
            list_alasanReparasi.add(data[i]);
        }
    }

    private void setAlasan() {
        TV=new Alasan_reparasi[]{new Alasan_reparasi(0,false,"karena rusak", 120000),
                                new Alasan_reparasi(1,false,"karena A", 35000),
                                new Alasan_reparasi(2,false,"karena B", 240000)};

        radio=new Alasan_reparasi[]{new Alasan_reparasi(0,false,"karena rusak", 120000),
                new Alasan_reparasi(1,false,"karena A", 35000),
                new Alasan_reparasi(2,false,"karena B", 240000)};

        komputer=new Alasan_reparasi[]{new Alasan_reparasi(0,false,"karena rusak", 120000),
                new Alasan_reparasi(1,false,"karena A", 35000),
                new Alasan_reparasi(2,false,"karena B", 240000)};

    }

    private void getData_fromActivity() {
        Intent getData = getIntent();
        nama_toko = getData.getStringExtra("nama_toko");
        alamat_toko = getData.getStringExtra("alamat_toko");
        kategori=shpref.getString("kategori", "TV");
        user_id=shpref.getString("username", "none");
        toko_id=getData.getStringExtra("toko_id");
        lintang = Double.parseDouble(shpref.getString("lintang", "0.0000"));
        bujur = Double.parseDouble(shpref.getString("bujur", "0.0000"));


        Log.d("shared pref", "kategori: " + kategori);
        Log.d("data", "nama toko: "+ nama_toko + ", Alamat Toko: " + alamat_toko
                + ", kategori: " + kategori  + ", User_id: " + user_id + ", toko_id: " + toko_id);
        Log.d("data", "lintang: "+ lintang + ", Bujur: " + bujur);

    }

    private void konfirmasi(){
        final String alasan_lain_Str = alasan_lain.getText().toString();

        if (TextUtils.isEmpty(alasan_lain_Str)){
            Log.d("additEmpty", "additional is empty");
            insertData();
        }

        else {
            AlertDialog.Builder alert_adaAlasanLain;

            alert_adaAlasanLain = new AlertDialog.Builder(this).setTitle("ada alasan lain");
            alert_adaAlasanLain.setMessage("Jika ada alasan lain, maka ada kemungkinan penambahan harga tidak tertera pada aplikasi" +
                    "ini. \n\n Mau Lanjut ?");
            alert_adaAlasanLain.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    insertDataWithAlasanTambahan(alasan_lain_Str);
                }
            });

            alert_adaAlasanLain.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alert_adaAlasanLain.show();
        }
    }

    private void insertDataWithAlasanTambahan(String alasan_lain_str) {
        int total = adapterAlasan.getTotal();
        ArrayList<String> alasan_build = adapterAlasan.getAlasan_build();
        alasan_build.add(alasan_lain_str);
        aditional_alasan = alasan_lain_str;
        alasan_all = alasan_build.toString();

        Log.d("PrePost", "total: "+ total + ", alasan: " + alasan_all + ", additional" +
                "alasan :" + aditional_alasan);

        new OrderRepair().execute(String.valueOf(total));

    }

    private void insertData() {
        int total = adapterAlasan.getTotal();

        if (aditional_alasan!=null){
            adapterAlasan.getAlasan_build().remove(aditional_alasan);
            aditional_alasan=null;
        }

        alasan_all = adapterAlasan.getAlasan_build().toString();

        Log.d("PrePost", "total: "+ total + ", alasan: " + alasan_all + ", " +
                "alasan from adapter: " + adapterAlasan.getAlasan_build().toString());

        new OrderRepair().execute(String.valueOf(total));
    }

    private void pindahMainActivity() {
        Intent pindahActivity = new Intent(this, MainMenu.class);
        startActivity(pindahActivity);
    }

    class OrderRepair extends AsyncTask<String, Integer, String> {
        private ProgressDialog pgDialog;
        private AlertDialog.Builder alert_isDone;

        @Override
        protected String doInBackground(String... params) {
            String value_hargaTotal = params[0];


            //build URL
            String URL = "http://" + shpref.getString("IP_address", "192.168.8.101") + "/";
            Log.d("URL Exec", "URL:" + URL);

            DBcomms send_order = new DBcomms();
            send_order.setCustomIP(URL);

            String outData = null;
            try {

                String postData_request = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8") + "&" +
                        URLEncoder.encode("kategori", "UTF-8") + "=" + URLEncoder.encode(kategori, "UTF-8") + "&" +
                        URLEncoder.encode("toko_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(toko_id), "UTF-8") + "&" +
                        URLEncoder.encode("alasan", "UTF-8") + "=" + URLEncoder.encode(alasan_all, "UTF-8") + "&" +
                        URLEncoder.encode("harga", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(value_hargaTotal), "UTF-8") + "&" +
                        URLEncoder.encode("lintang", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lintang), "UTF-8") + "&" +
                        URLEncoder.encode("bujur", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(bujur), "UTF-8");


                Log.d("postData", "postData encoded: " + postData_request);

                outData = send_order.customQuery("php_file/request_service.php", postData_request);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return outData;
        }

        @Override
        protected void onPreExecute() {
            pgDialog = new ProgressDialog(alasan_konfirmasi.this);
            pgDialog.setTitle("Sedang Memasukkan Data");
            pgDialog.setProgress(0);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pgDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result!=null){
                pgDialog.dismiss();

                alert_isDone = new AlertDialog.Builder(alasan_konfirmasi.this);
                alert_isDone.setTitle("Info").setMessage(result);
                alert_isDone.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                       /* pindahMainActivity();*/
                    }
                });
                alert_isDone.show();
            }
            else {
                Toast.makeText(alasan_konfirmasi.this, "Tak ada koneksi...", Toast.LENGTH_LONG).show();
                cancel(true);
            }
        }
    }






}
