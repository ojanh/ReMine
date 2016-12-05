package com.example.ojan_.remine.toko_mainmenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ojan_.remine.R;
import com.example.ojan_.remine.toko_mainmenu.Custom_listView.DaftarRequest_service;

import org.w3c.dom.Text;

import java.util.List;

public class toko_menu extends AppCompatActivity {

    SharedPreferences shpref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_menu);

        shpref = PreferenceManager.getDefaultSharedPreferences(this);
        ListView listView = (ListView) findViewById(R.id.list_request);




    }


    class AdapterRequestToko extends ArrayAdapter {
        Context ctx;
        List<DaftarRequest_service> list_user;

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

    class GetList_service extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... params) {

            return null;
        }
    }


}

