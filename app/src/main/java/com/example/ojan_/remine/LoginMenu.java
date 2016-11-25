package com.example.ojan_.remine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ojan_.remine.toko_mainmenu.toko_menu;
import com.example.ojan_.remine.user_mainmenu.MainMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class LoginMenu extends AppCompatActivity {

    Button loginBtn;
    EditText usernam;
    EditText passw;
    EditText ip_addr;
    String result="s";

    //create Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) { //saat di launch event
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login_menu);

        loginBtn = (Button) findViewById(R.id.loginbutton);
        usernam = (EditText) findViewById(R.id.username);
        passw = (EditText) findViewById(R.id.pass);
        ip_addr = (EditText) findViewById(R.id.ip_addr_set);

        //saat klik loginButton

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check koneksi
                ConnectivityManager connMgr =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                //jika ada koneksi langsung eksekusi kalau ngak tampilkan msg gak ada koneksi
                if (networkInfo != null && networkInfo.isConnected()) {
                    clickListen();
                } else {
                    Toast.makeText(LoginMenu.this, "No connection",Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

    //onclick listen Login Button
    private void clickListen(){
        String URL;
        String role = "";

        //set IP address
        String ip_addr_set = ip_addr.getText().toString();

        //save data ke sharedpreferences
        shpref(usernam.getText().toString(), ip_addr.getText().toString());

        //pindah ke Class Login (Inner Class)
        //melakukan komunikasi ke server untuk cek apa user dan password sesuai dengan di server

        Login login = new Login(LoginMenu.this);

        //jika kondisi URL nya tidak diisi dan diisi
        if (TextUtils.isEmpty(ip_addr_set)) {
            Toast.makeText(LoginMenu.this, "no IP, Set to localhost", Toast.LENGTH_SHORT).show();
        } else {

            URL = "http://" + ip_addr_set + "/";
            Toast.makeText(LoginMenu.this, "set IP to " + URL, Toast.LENGTH_SHORT).show();
            login.URL = URL;
        }

        //get roles

        login.execute(usernam.getText().toString(), passw.getText().toString());



    }

    //save username dan roles
    private void shpref(String username, String IP_addr) {
        SharedPreferences shpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor sheditor = shpref.edit();
        sheditor.putString("username", username);
        sheditor.putString("IP_address", IP_addr);
        sheditor.commit();

    }

    //pindah intent
    private void intent_change(String role){
        Toast.makeText(LoginMenu.this, "pindah fungsi dengan Role: " + role, Toast.LENGTH_SHORT).show();

        //jika rolenya adalah toko
        if (role.compareTo("toko")==0) {
            Toast.makeText(LoginMenu.this, "Berpindah ke toko menu", Toast.LENGTH_SHORT).show();
            Intent pindah = new Intent(getApplicationContext(), toko_menu.class);
            startActivity(pindah);

        }

        //jika role nya adalah users
        if(role.compareTo("klien")==0){
            Intent pindah = new Intent(getApplicationContext(), MainMenu.class);
            startActivity(pindah);
        }


    }

    //bikin thread baru untuk login
    class Login extends AsyncTask<String, Void, Boolean> {


        AlertDialog.Builder dialoggetQuery; //getquery dialog
        String hasil_temp;
        String roles;
        String message;
        public String URL = "1";
        boolean isNoConnection = false;

        public Login (Context ctx){
            dialoggetQuery = new AlertDialog.Builder(ctx);
            dialoggetQuery.setTitle("Login Status");
            dialoggetQuery.setMessage("Lagi check");
            dialoggetQuery.setNegativeButton("Cancel", dialoggetQueryListener);


        }

        DialogInterface.OnClickListener dialoggetQueryListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        cancel(true);

                    }
                };


        @Override
        protected Boolean doInBackground(String... params) {
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


        }

        @Override
        protected void onPreExecute() {
            dialoggetQuery.show();


        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            Log.d("conn", "nilai out = "+ hasil_temp + ", Koneksi : " + isNoConnection + aVoid);

            //jika ada koneksi, cek roles dan pindah intent, kalau tidak ya close thread koneksi
            if (hasil_temp!=null && hasil_temp.length() > 0){

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

                Log.d("out","Roles: " + roles);

                //pindah activity dengan kondisi role toko atau users
                intent_change(roles);
            }

            else {
                Toast.makeText(LoginMenu.this, "tak ada koneksi, coba ulang lagi atau Set IP nya", Toast.LENGTH_SHORT).show();
                cancel(true);
            }








        }


    }


}







