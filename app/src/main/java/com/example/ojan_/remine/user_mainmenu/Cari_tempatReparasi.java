package com.example.ojan_.remine.user_mainmenu;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ojan_.remine.DBcomms;
import com.example.ojan_.remine.Dumbs.GetGPS;
import com.example.ojan_.remine.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/** system
 * Google Map Fragment
 * Buat List View jadi button
 *
 * *Algoritma:
 * cari di database sesuai dengan permintaan yang mau direparasi, radius 5 km
 * file diterima dalam JSON, ubah ke dalam Array
 * Dari Array, tampilkan Di List View
 *
 *
 * Api Gmaps Key =  AIzaSyApttRaZZeTxMmt1-HdB3FZFyENx7WyvXo
 */


public class Cari_tempatReparasi extends AppCompatActivity implements OnMapReadyCallback {

    //initial objects and variables
    SharedPreferences shpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    String pilihan;

    MapFragment mapFragment;
    GoogleMap mGoogleMap;
    LatLng posLatLng;
    double[] position = new double[]{0.00000, 0.0000}; //{latitude, Longitude}


    //tampilkan data ke dalam
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_cari_tempat_reparasi);

        pilihan = getIntent().getStringExtra("pilih");

        initMap();


        ArrayAdapter list_reparasi = new ArrayAdapter(this, R.layout.listview_cari_reparasi); //setup array dari database
        ListView tampil_list_reparasi = (ListView) findViewById(R.id.list_reparasi); //tampilkan list reparasi
        tampil_list_reparasi.setAdapter(list_reparasi);





    }

    private void initMap() {
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        posLatLng = new LatLng(position[0], position[1]);
        CameraUpdate camUpdate = CameraUpdateFactory.newLatLng(posLatLng);
        mGoogleMap.moveCamera(camUpdate);
    }

    //get data dari internet
    class getInfo extends AsyncTask<String, Void, String> {
        ProgressDialog pgDialog = new ProgressDialog(Cari_tempatReparasi.this);
        double longitude,latitude;


        @Override
        protected String doInBackground(String... params) {
            //set IP address
            String IP_addr = shpref.getString("IP_address", "192.168.8.101");

            //set URL dan dapatkan Lokasi
            String URL = "http://" + IP_addr + "/";
            Log.d("conn", "URL address: " + URL);

            checkGPS();

            DBcomms getBengkel = new DBcomms();
            String query = "SELECT * FROM list_toko WHERE kategori='" + pilihan + "'";
            Log.d("query: ", query);

            String postData = " ";

            String hasil = null;
            try {
                postData = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(query, "UTF-8");
                hasil = getBengkel.getQuery(postData);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return hasil;
        }

        private void checkGPS() {
            GetGPS getGPS = new GetGPS(getApplicationContext());

            getGPS.getLocation();
            boolean isNotAllowed = getGPS.isNotAllowed();

            if (isNotAllowed){
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET},
                        10);

            }

            longitude=getGPS.getLongitude();
            latitude=getGPS.getLatitude();

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String hasil) {

            if (hasil != null){
                Log.d("conn", "hasil: " + hasil);

            }

            else {
                Toast.makeText(getApplicationContext(), "Tak ada koneksi, tak ada data masuk", Toast.LENGTH_LONG);
                cancel(true);
            }

        }
    }


}

