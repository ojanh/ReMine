package com.example.ojan_.remine.toko_mainmenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ojan_.remine.DBcomms;
import com.example.ojan_.remine.Dumbs.GetGPS;
import com.example.ojan_.remine.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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

public class toko_menu extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    SharedPreferences shpref;
    String URL;
    List<DataInput_toko_menu> requestIn = new ArrayList<>();

    ListView list_request_show;
    GoogleMap gMaps;
    GoogleApiClient mGoogleAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_menu);

        shpref = PreferenceManager.getDefaultSharedPreferences(this);
        initMap();
        String postGen = generatePostData1();

        GetList_service getData = new GetList_service();
        getData.execute(postGen,"php_file/check_toko_request.php");

        TextView username = (TextView) findViewById(R.id.tokoMenu_username);
        username.setText(shpref.getString("username", "Guest"));

        list_request_show = (ListView) findViewById(R.id.tokoMenu_listRequest);

    }

    /*implement methods*/

    //method implement saat map sudah dijalankan
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMaps = googleMap;
        gMaps.setMyLocationEnabled(true);

        mGoogleAPI = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        mGoogleAPI.connect();
    }

    //method implement saat googleAPI sudah terkoneksi
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        GetGPS getGPS = new GetGPS(this);
        getGPS.getLocation();

        gotoMap(getGPS.getLatitude(),getGPS.getLongitude());

    }

    //method implement saat googleAPI koneksi dimatikan
    @Override
    public void onConnectionSuspended(int i) {

    }

    //method implement saat googleAPI tak terkoneksi
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    /*private method*/

    //method saat inisialisasi maps
    private void initMap() {
        MapFragment maps = (MapFragment) getFragmentManager().findFragmentById(R.id.tokoMenu_map);
        maps.getMapAsync(this);
    }

    //method untuk generate POST method
    private String generatePostData1() {
        String postData = null;
        String user_id = shpref.getString("username","none");
        try {
            postData= URLEncoder.encode("userid","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8");
            Log.d("postData","PostData: " + postData);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return postData;
    }

    //method untuk membuat list Request service
    private void showListRequest(String result) {

        try {
            JSONArray jArr = new JSONArray(result);

            for (int i = 0; i < jArr.length(); i++) {
                JSONObject jObj = jArr.getJSONObject(i);

                String a_userID = jObj.getString("user_id");
                String b_kategori = jObj.getString("kategori");
                int c_harga = jObj.getInt("harga");
                double d_lintang = jObj.getDouble("userlokasi_lintang");
                double e_bujur = jObj.getDouble("userlokasi_bujur");

                requestIn.add(new DataInput_toko_menu(a_userID, b_kategori, c_harga, d_lintang, e_bujur));
                createMarker(d_lintang,e_bujur,a_userID);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AdapterRequestToko adapterListService =
                new AdapterRequestToko(this, R.layout.listview_request_service, requestIn);
        list_request_show.setAdapter(adapterListService);

        list_request_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoMap(requestIn.get(position).getLintang(),requestIn.get(position).getBujur());

            }
        });
    }

    //method untuk membuat marker
    private void createMarker(double lintang, double bujur, String userID) {
        gMaps.addMarker(new MarkerOptions().position(new LatLng(lintang,bujur)).title(userID));
    }

    //method untuk memindahkan lokasi maps
    private void gotoMap(double lintang, double bujur) {
        LatLng latlng = new LatLng(lintang, bujur);
        CameraUpdate camUp = CameraUpdateFactory.newLatLng(latlng);
        gMaps.animateCamera(camUp);
    }



    /*class*/

    //class untuk mengambil data request service dari internet
    private class GetList_service extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... params) {
            String postData = params[0];
            String php_file = params[1];

            String URL = "http://" + shpref.getString("IP_address", "192.168.8.100") + "/";
            Log.d("URLtk", "URL: "+ URL);

            DBcomms comData = new DBcomms();

            comData.setCustomIP(URL);
            String result = null;
            try {
                result = comData.customQuery(php_file,postData);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result==null){
                Toast.makeText(toko_menu.this, "Tak ada Koneksi", Toast.LENGTH_LONG).show();
                cancel(true);
            }

            else {
                Log.d("inStream","data yang didapat: " + result);
                showListRequest(result);
            }
        }
    }

    //class untuk adapter listview dari request services
    private class AdapterRequestToko extends ArrayAdapter {
        Context ctx;
        List<DataInput_toko_menu> list_user;

        public AdapterRequestToko(Context context, int resource, List objects) {
            super(context, resource, objects);
            this.ctx=context;
            this.list_user=objects;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v==null){
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.listview_request_service, null);
            }

            TextView user_id = (TextView) v.findViewById(R.id.lView_tokoMenu_text_user_id);
            TextView harga = (TextView) v.findViewById(R.id.lView_tokoMenu_text_hargaView);

            user_id.setText(list_user.get(position).getUser_id());
            harga.setText(String.valueOf(list_user.get(position).getHarga()));


            return v;
        }
    }

    //class untuk mmebuat object dari yang direquest
    private class DataInput_toko_menu {
        String user_id, kategori;
        int harga;
        double lintang, bujur;

        //constructor dari class datainput_tokomenu
        public DataInput_toko_menu(String user_id, String kategori, int harga, double lintang, double bujur) {
            this.user_id = user_id;
            this.kategori = kategori;
            this.harga = harga;
            this.lintang = lintang;
            this.bujur = bujur;
        }

        //method untuk get data
        public String getUser_id() {
            return user_id;
        }

        public String getKategori() {
            return kategori;
        }

        public int getHarga() {
            return harga;
        }

        public double getLintang() {
            return lintang;
        }

        public double getBujur() {
            return bujur;
        }


    }



}

