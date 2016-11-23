package com.example.ojan_.remine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ojan_.remine.toko_mainmenu.toko_menu;
import com.example.ojan_.remine.user_mainmenu.MainMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class LoginMenu extends AppCompatActivity {

    EditText usernam;
    EditText passw;
    EditText ip_addr;


    //create Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) { //saat di launch event
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login_menu);

        Button loginBtn = (Button) findViewById(R.id.loginbutton);
        usernam = (EditText) findViewById(R.id.username);
        passw = (EditText) findViewById(R.id.pass);
        ip_addr = (EditText) findViewById(R.id.ip_addr_set);

        //saat klik loginButton
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL;
                String role = "";

                //set IP address
                String ip_addr_set = ip_addr.getText().toString();

                //save data ke sharedpreferences
                shpref(usernam.getText().toString(), ip_addr.getText().toString());

                //pindah ke Class Login (Inner Class)
                //melakukan komunikasi ke server untuk cek apa user dan password sesuai dengan di server

                Login login = new Login();
                //jika kondisi URL nya diisi
                if (TextUtils.isEmpty(ip_addr_set)) {
                    Toast.makeText(LoginMenu.this, "no IP", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginMenu.this, "ada IP", Toast.LENGTH_SHORT).show();
                    URL = "http://" + ip_addr_set + "/";
                    login.URL = URL;
                    Toast.makeText(LoginMenu.this, "URL : " + URL, Toast.LENGTH_SHORT).show();
                }

                //get roles
                try {
                    role=login.execute(usernam.getText().toString(), passw.getText().toString()).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                //get roles
                Toast.makeText(LoginMenu.this, "Roles: " + role, Toast.LENGTH_SHORT).show();

                //pindah activity dengan kondisi role toko atau users
                intent_change(role);
                //jika roles adalah toko maka pindah ke activity toko

            }

            //fungsi save data dengan shared preferences


            ////jika roles adalah user maka pindah ke activity user


        });

    }

    private void shpref(String username, String IP_addr) {
        SharedPreferences shpref = getSharedPreferences("Init", Context.MODE_PRIVATE);
        SharedPreferences.Editor sheditor = shpref.edit();
        sheditor.putString("username", username);
        sheditor.putString("IP_address", IP_addr);
        sheditor.commit();

    }

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

    class Login extends AsyncTask<String, Void, String> {

        AlertDialog dialoggetQuery; //getquery dialog
        String hasil_temp;
        String roles;
        String message;
        public String URL = "1";

        @Override
        protected String doInBackground(String... params) {
            //Login query
            String username = params[0];
            String password = params[1];


            //eksekusi data dari username dan password
            DBcomms checkLogin = new DBcomms();
            String postEncode = "";
            try {
                postEncode = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
                        + "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("login", "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //Set URL Link jika URL ada (Tidak 1)
            if (URL != "1") {
                checkLogin.setCustomIP(URL);
            }

            hasil_temp = checkLogin.checkData(postEncode);

            //konversi JSON ke string untuk data
            try {
                JSONObject assemb_result = new JSONObject(hasil_temp);
                roles = assemb_result.getString("roles");
                message = assemb_result.getString("info");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return roles;
        }

        @Override
        protected void onPreExecute() {
            dialoggetQuery = new AlertDialog.Builder(LoginMenu.this).create();
            dialoggetQuery.setTitle("Login Status");
            dialoggetQuery.setMessage("Lagi check");
            dialoggetQuery.show();


        }

        @Override
        protected void onPostExecute(String aVoid) {

            //menampilkan dialog sudah dapat
            dialoggetQuery.setMessage(message + ", Role: " + roles);
            dialoggetQuery.show();



        }






        }



}







