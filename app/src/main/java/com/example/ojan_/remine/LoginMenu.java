package com.example.ojan_.remine;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
        ip_addr= (EditText) findViewById(R.id.ip_addr_set);

        //saat klik loginButton
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save data ke sharedpreferences
                String URL = "http://" + ip_addr.getText().toString() + "/";
                shpref(usernam.getText().toString(), ip_addr.getText().toString());

                //pindah ke Class Login (Inner Class)
                //melakukan komunikasi ke server untuk cek apa user dan password sesuai dengan di server
                Login login = new Login();
                login.execute(usernam.getText().toString(), passw.getText().toString(), URL);

                String role=login.getRoles();


                if (role=="toko"){
                    Intent pindah = new Intent(getApplicationContext(),toko_menu.class);
                    pindah.putExtra("username",usernam.getText().toString());
                    startActivity(pindah);
                }




                //pindah activity ke MainMenu (Temp ke UserView)
                /*
                Intent pindah = new Intent(getApplicationContext(),MainMenu.class);
                pindah.putExtra("username",usernam.getText().toString());
                startActivity(pindah);*/
            }

        });
    }


    //fungsi bentuk class Login
    class Login extends AsyncTask<String,Void,Void> {

        AlertDialog dialoggetQuery; //getquery dialog
        String hasil_temp;
        String roles;
        String message;


        @Override
        protected Void doInBackground(String... params) {
            //Login query
            String username=params[0];
            String password=params[1];
            String URL =params[2];

            //eksekusi data dari username dan password
            DBcomms checkLogin = new DBcomms();
            String postEncode = "";
            try {
                postEncode = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")
                        +"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password, "UTF-8")
                        +"&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode("login","UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            checkLogin.setCustomIP(URL);
            hasil_temp=checkLogin.checkData(postEncode);

            //konversi JSON ke string untuk data
            try {
                JSONObject assemb_result = new JSONObject(hasil_temp);
                roles=assemb_result.getString("roles");
                message=assemb_result.getString("info");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            dialoggetQuery= new AlertDialog.Builder(LoginMenu.this).create();
            dialoggetQuery.setTitle("Login Status");
            dialoggetQuery.setMessage("Lagi check");
            dialoggetQuery.show();


        }

        @Override
        protected void onPostExecute(Void aVoid) {

            dialoggetQuery.setMessage(message);
            dialoggetQuery.show();
            dialoggetQuery.setCanceledOnTouchOutside(true);

        }

        public String getRoles(){
            return roles;
        }

    }

    //fungsi save data dengan shared preferences
    private void shpref (String username, String IP_addr){
        SharedPreferences shpref = getSharedPreferences("Init", Context.MODE_PRIVATE);
        SharedPreferences.Editor sheditor = shpref.edit();
        sheditor.putString("username", username);
        sheditor.putString("IP_address", IP_addr);
        sheditor.commit();

    }
}


