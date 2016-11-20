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

        //saat klik loginButton
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialoggetQuery;
                dialoggetQuery= new AlertDialog.Builder(LoginMenu.this).create();
                dialoggetQuery.setTitle("Login Status");
                dialoggetQuery.setMessage("Lagi check");
                dialoggetQuery.show();

                //pindah ke background tasking
                Login login = new Login();
                login.execute(usernam.getText().toString(), passw.getText().toString());



                //pindah activity ke MainMenu (Temp ke UserView)
                Intent pindah = new Intent(getApplicationContext(),MainMenu.class);
                pindah.putExtra("username",usernam.getText().toString());
                startActivity(pindah);
            }

        });
    }








    //fungsi
    class Login extends AsyncTask<String,Void,Void> {


        AlertDialog dialoggetQuery; //getquery dialog
        String hasil;

        @Override
        protected Void doInBackground(String... params) {
            //Login query
            String username=params[0];
            String password=params[1];

            DBcomms checkLogin = new DBcomms
                    ("SELECT * FROM users WHERE user_id '" + username + "' AND passsword='" + password + "'");

            hasil= checkLogin.checkData("login");


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
            dialoggetQuery.setMessage("Login " + hasil);
            dialoggetQuery.show();

            }
    }
}



