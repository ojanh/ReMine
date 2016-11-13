package com.example.ojan_.remine;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class LoginMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //saat di launch event
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login_menu);

        Button loginBtn = (Button) findViewById(R.id.loginbutton);
        EditText usernam = (EditText) findViewById(R.id.username);
        final EditText passw = (EditText) findViewById(R.id.pass);

        final String Username=usernam.getText().toString();
        String Password= passw.getText().toString();

        loginBtn.setOnClickListener(new View.OnClickListener() { //saat logi
            @Override
            public void onClick(View v) {
                DBcomms checkLogin = new DBcomms(
                        "SELECT 1 FROM //nama_Tabel WHERE username= " + usernam + "AND passsword= " +passw);
                checkLogin.checkData();

            }
        }
    }
    }


