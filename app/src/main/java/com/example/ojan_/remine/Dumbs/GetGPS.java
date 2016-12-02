package com.example.ojan_.remine.Dumbs;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ojan_ on 25/11/2016.
 */

/**
 * Algoritma Nya adalah :
 * pertama cek gps dan network nya enable
 * kalau tidak, minta user set, kalau sudah di set langsung get lokasi
 * dalam request lokasi ada 4 variabel yaitu time
 */
public class GetGPS extends Service implements LocationListener {

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    boolean isNotAllowed = false;
    // hitung jarak minimum buat get data lagi
    private static final long update_berdasarkanJarak = 10; // 10 meters

    // hitung waktu minimum untuk get data gps lagi
    private static final long update_berdasrkanWaktu = 1000 * 60 * 1; // 1 minute

    // mendapatkan data location manager
    protected LocationManager locationManager;
    Context ctx;

    //constructor GetGPS
    public GetGPS(Context ctx) {
        this.ctx = ctx;
        locationManager = (LocationManager) ctx.getSystemService(LOCATION_SERVICE);
    }


    public Location getLocation() {
        boolean networkEn, gpsHiEn;
        Criteria criteria = new Criteria();

        //cek permissions apakah di granted atau tidak
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Permissons", "Permit Deny atau yang lain untuk ACCESS_FINE_LOCATION DAN ACCESS_COARSE_LOCATION");
            isNotAllowed = true;
        } else {

            //set criteria untuk akurasi dan kecepatan


            //get gps data
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                GPSdata(LocationManager.NETWORK_PROVIDER);

                if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                    GPSdata(LocationManager.GPS_PROVIDER);
                }

            } else {
                Toast.makeText(ctx, "Maaf, GPS anda belum dinyalakan", Toast.LENGTH_LONG).show();
            }

        }

        return location;


    }

    private void GPSdata(String provider) {
        location = locationManager.getLastKnownLocation(provider);

        if (location != null){
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.d("GPS", "latitude: " + String.valueOf(latitude) + ", longitude: " + String.valueOf(longitude));

        }
        else {
            Log.d("GPS ", "GPS data tidak ada");
        }

    }

    public boolean isNotAllowed() {
        return isNotAllowed;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }


    @Override
    public void onLocationChanged(Location location) {


    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        final AlertDialog.Builder noGPSDialog = new AlertDialog.Builder(ctx);
        noGPSDialog.setTitle("GPS belum dinyalakan").
                setMessage("Coba nyalakan GPS terlebih dahulu di settings")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

        noGPSDialog.show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
