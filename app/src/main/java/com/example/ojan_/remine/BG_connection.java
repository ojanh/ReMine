package com.example.ojan_.remine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ojan_ on 25/11/2016.
 */

public class BG_connection extends AsyncTask<String, Void, Boolean> {
    ProgressDialog progress; //getquery dialog
    String hasil_temp;
    String roles;
    String message;
    public String URL = "1";
    boolean isNoConnection = false;
    Context ctx;

    public BG_connection(Context ctx) {
        this.ctx=ctx;
        progress = new ProgressDialog(ctx);
        progress.setMessage("Lagi check");
        progress.setButton(DialogInterface.BUTTON_NEGATIVE, "keluar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancel(true);
                progress.dismiss();
            }
        });


    }




    @Override
    protected Boolean doInBackground(String... params) {
        /*

        //Login query
        String username = params[0];
        String password = params[1];


        //eksekusi data dari username dan password
        DBcomms checkLogin = new DBcomms();
        String postEncode = "";

        //buat httpPOST data
        try {
            postEncode = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                    + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
                    + "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("login", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Set URL Link jika URL ada (Tidak 1)
        if (URL != "1") {
            Log.d("URL", "tidak ada URL");
            checkLogin.setCustomIP(URL);
        }


        //eksekusi http communication dan dapatkan data nya


        try {
            hasil_temp = checkLogin.login_check(postEncode);
            Log.d("Conn", "input stream: " + hasil_temp);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
       */
        return false;
    }

    @Override
    protected void onPreExecute() {
        progress.setMessage("berhasil");
        progress.show();


    }

    @Override
    protected void onPostExecute(Boolean aVoid) {
        /*
        Log.d("conn", "nilai out = " + hasil_temp + ", Koneksi : " + isNoConnection + aVoid);

        //jika ada koneksi, cek roles dan pindah intent, kalau tidak ya close thread koneksi
        if (hasil_temp != null && hasil_temp.length() > 0) {

            //konversi JSON ke message tertentu
            try {
                JSONObject assemb_result = new JSONObject(hasil_temp);
                roles = assemb_result.getString("roles");
                message = assemb_result.getString("info");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //menampilkan dialog sudah dapat
            dialoggetQuery.setMessage(message + ", Role: " + roles);
            dialoggetQuery.show();

            Log.d("out", "Roles: " + roles);

            //pindah activity dengan kondisi role toko atau users

        } else {
            Toast.makeText(LoginMenu.this, "tak ada koneksi, coba ulang lagi atau Set IP nya", Toast.LENGTH_SHORT).show();
            cancel(true);
        }
        */

    }


}


