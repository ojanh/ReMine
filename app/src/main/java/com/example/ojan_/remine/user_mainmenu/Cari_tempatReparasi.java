package com.example.ojan_.remine.user_mainmenu;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ojan_.remine.Custom_listview.Adapter_cari_reparasi;
import com.example.ojan_.remine.Custom_listview.Toko_reparasi;
import com.example.ojan_.remine.DBcomms;
import com.example.ojan_.remine.Dumbs.GetGPS;
import com.example.ojan_.remine.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


public class Cari_tempatReparasi extends AppCompatActivity implements OnMapReadyCallback, LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    //initial objects and variables
    SharedPreferences shpref;
    String pilihan;

    ProgressBar pgBar;
    ListView tampil_list_reparasi;

    MapFragment mapFragment;
    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;


    double[] posKoordinat = new double[]{0.00000, 0.0000}; //{latitude, Longitude}


    //tampilkan data ke dalam
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_cari_tempat_reparasi);

        pilihan = getIntent().getStringExtra("pilih");
        shpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pgBar = (ProgressBar) findViewById(R.id.progressBar_getListBengkel);
        tampil_list_reparasi = (ListView) findViewById(R.id.list_reparasi); //tampilkan list reparasi

        checkApi();
        checkGPS();
        initMap();

        Log.d("GMAP d2", "GMAP var is ? " + mGoogleMap);


        Intent intent = getIntent();
        String kategori= intent.getStringExtra("pilih");

        Log.d("data", "kategori: " +  kategori);

        new GetInfo().execute(String.valueOf(posKoordinat[0]), String.valueOf(posKoordinat[1]),kategori);






    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;


        mGoogleMap.setMyLocationEnabled(true);
        moveGMap(posKoordinat[0],posKoordinat[1]);

        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this)
                                .addOnConnectionFailedListener(this).build();

        mGoogleApiClient.connect();
    }

    private void checkApi() {
        GoogleApiAvailability gugelAPI = GoogleApiAvailability.getInstance();
        int isAvailable = gugelAPI.isGooglePlayServicesAvailable(this);
        if (!(isAvailable == ConnectionResult.SUCCESS)){
            Log.d("GPS", "no GPlay service");
            Toast.makeText(this, "can't connect to google play services", Toast.LENGTH_SHORT).show();
        }
        else if (gugelAPI.isUserResolvableError(isAvailable)){
            Dialog dialog = gugelAPI.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        }

    }

    private void initMap() {
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void checkGPS() {
        GetGPS getGPS = new GetGPS(getApplicationContext());

        getGPS.getLocation();
        boolean isNotAllowed = getGPS.isNotAllowed();
        /*Log.d("GMAP d2", "GMAP var is ? " + mGoogleMap);*/
        if (isNotAllowed){
            Log.d("GPS", "Not Allowed");

        }
        else {
            posKoordinat[0]=getGPS.getLatitude();
            posKoordinat[1]=getGPS.getLongitude();
            Toast.makeText(this, "Posisi :: Lintang: " + posKoordinat[0] + ",Bujur: " + posKoordinat[1] , Toast.LENGTH_LONG).show();


        }

    }

    private void setListBengkel(String hasil)  {
        int i;

        final ArrayList<String> id_toko = new ArrayList<>();
        final ArrayList<String> nama_bengkel = new ArrayList<>();
        final ArrayList<String> alamat_bengkel = new ArrayList<>();
        final ArrayList<Double> lintang = new ArrayList<>();
        ArrayList<Double> bujur = new ArrayList<>();


        try {
            JSONArray jsonArHasil = new JSONArray(hasil);

            for (i = 0 ; i < jsonArHasil.length(); i++){
                JSONObject jsonObHasil = jsonArHasil.getJSONObject(i);

                id_toko.add(jsonObHasil.getString("toko_id"));
                nama_bengkel.add(jsonObHasil.getString("Nama_Toko"));
                alamat_bengkel.add(jsonObHasil.getString("alamat_toko"));
                lintang.add(Double.parseDouble(jsonObHasil.getString("lokasi_lintang")));
                bujur.add(Double.parseDouble(jsonObHasil.getString("lokasi_bujur")));


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("JSONparse", "toko_id: " + id_toko.toString());
        Log.d("JSONparse", "nama: " + nama_bengkel.toString());
        Log.d("JSONparse", "alamat: " + alamat_bengkel.toString());
        Log.d("JSONparse", "lintang: " + lintang.toString());
        Log.d("JSONparse", "bujur: " + bujur.toString());

        //Declare listview dan array list dari toko
        List<Toko_reparasi> mTokoReparasi = new ArrayList<>();
        Adapter_cari_reparasi adapterCariReparasi;
        ListView listView_reparasi = (ListView) findViewById(R.id.list_reparasi);

        //menampilkan list view
        for (i=0; i < id_toko.size(); i++){
            mTokoReparasi.add(new Toko_reparasi(i, nama_bengkel.get(i), lintang.get(i), bujur.get(i)));
            Log.d("adapter", "hasil adapter2: " + mTokoReparasi.get(i).getNama_toko());
        }

        adapterCariReparasi = new Adapter_cari_reparasi(getApplicationContext(), mTokoReparasi);
        listView_reparasi.setAdapter(adapterCariReparasi);


        //menampilkan marker dari tempat
        for (i=0; i < id_toko.size(); i++){
            mGoogleMap.addMarker(new MarkerOptions().
                    position(new LatLng(lintang.get(i), bujur.get(i))).title(nama_bengkel.get(i)));
        }


        listView_reparasi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int id_clicked = Integer.parseInt(view.getTag().toString());
                Log.d("ClickList", "ID: " + id_clicked);
                Log.d("ClickList", "ID toko yang di click :" + id_toko.get(id_clicked));

                SharedPreferences.Editor shEditor = shpref.edit();
                shEditor.putString("kategori", pilihan);
                shEditor.putString("lintang",String.valueOf(posKoordinat[0]))
                        .putString("bujur",String.valueOf(posKoordinat[1]));
                shEditor.apply();


                pindahActivity(nama_bengkel.get(position), alamat_bengkel.get(position),id_toko.get(position));

            }
        });

    }

    private void moveGMap(double v, double v1) {
        Log.d("GMAP d1", "GMAP var is ? " + mGoogleMap);

        LatLng posLatLng = new LatLng(v, v1);
        CameraUpdate camUpdate = CameraUpdateFactory.newLatLng(posLatLng);
        mGoogleMap.moveCamera(camUpdate);

    }

    private void pindahActivity(String nama_toko, String alamat_toko, String toko_id) {
        Intent pindahActivity = new Intent(getApplicationContext(), alasan_konfirmasi.class);
        pindahActivity.putExtra("nama_toko", nama_toko);
        pindahActivity.putExtra("alamat_toko", alamat_toko);
        pindahActivity.putExtra("toko_id", toko_id);
        startActivity(pindahActivity);

    }

    LocationRequest mLocationReq;

    @Override
    public void onLocationChanged(Location location) {
        if(location == null){
            Toast.makeText(this, "Can't get Current Location", Toast.LENGTH_SHORT).show();
        }
        else {
            posKoordinat[0]=location.getLatitude();
            posKoordinat[1]=location.getLongitude();
            LatLng kordinat = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate update =  CameraUpdateFactory.newLatLngZoom(kordinat, 15);
            mGoogleMap.animateCamera(update);
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationReq = LocationRequest.create();
        mLocationReq.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationReq.setInterval(1000);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationReq, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    //get data dari internet
    class GetInfo extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            //set IP address
            String latitude=params[0];
            String longitude=params[1];
            String kategori_pilihan = params[2];
            String IP_addr = shpref.getString("IP_address", "192.168.8.101");


            //set URL dan dapatkan Lokasi
            String URL = "http://" + IP_addr + "/";
            Log.d("conn", "URL address: " + URL);


            DBcomms getBengkel = new DBcomms();
            getBengkel.setCustomIP(URL);


            String postData = " ";
            String hasil = " ";
            try {
                postData = URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(latitude), "UTF-8")
                        + "&" + URLEncoder.encode("longitude", "UTF-8")+"="+URLEncoder.encode(String.valueOf(longitude), "UTF-8")
                        + "&" + URLEncoder.encode("kategori", "UTF-8")+"="+URLEncoder.encode(kategori_pilihan, "UTF-8");

                Log.d("conn", "postData: " + postData);
                hasil = getBengkel.customQuery("php_file/check_toko.php", postData);




            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("conn", e.getMessage());
            }


            return hasil;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String hasil) {
            pgBar.setVisibility(View.INVISIBLE);

            //jika hasil = null, maka muncul toast tak ada koneksi dan thread di stop
            if (hasil != null){
                Log.d("conn", "hasil: " + hasil);
                setListBengkel(hasil);
            }
            else {
                Toast.makeText(getApplicationContext(), "Tak ada koneksi, tak ada data masuk", Toast.LENGTH_LONG).show();
                cancel(true);


            }

        }



    }




}

