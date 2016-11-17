package com.example.ojan_.remine;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class LoginMenu extends AppCompatActivity {

    EditText usernam;
    EditText passw;

    //create Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) { //saat di launch event
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login_menu);

        Button loginBtn = (Button) findViewById(R.id.loginbutton);
        usernam = (EditText) findViewById(R.id.username);
        passw = (EditText) findViewById(R.id.pass);


        loginBtn.setOnClickListener(new View.OnClickListener() { //saat logi
            @Override
            public void onClick(View v) {
               String user_nama=usernam.getText().toString(); //ubah data text ke string

                GetData(usernam.getText().toString(),passw.getText().toString());

                //pindah activity ke MainMenu
                Intent pindah = new Intent(getApplicationContext(),MainMenu.class);
                pindah.putExtra("username",user_nama);
                startActivity(pindah);
            }

        });
    }








    //fungsi GetData
    public void GetData(final String U1, String U2){
        class Login extends AsyncTask<String,Void,Void> {
            String username=LoginMenu.this.usernam.getText().toString(); //set username
            String password=LoginMenu.this.passw.getText().toString(); //set pass
            AlertDialog dialoggetQuery; //getquery dialog

            @Override
            protected Void doInBackground(String... params) {
                DBcomms checkLogin = new DBcomms
                        ("SELECT 1 FROM //nama_Tabel WHERE username=" + username + " AND passsword=" + password + ";");

                String hasil = checkLogin.checkData();


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
                dialoggetQuery.setMessage("Login dengan ID" + username);
                dialoggetQuery.show();

            }
        }
    }
}


