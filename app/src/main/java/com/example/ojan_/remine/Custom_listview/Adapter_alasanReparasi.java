package com.example.ojan_.remine.Custom_listview;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.ojan_.remine.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ojan_ on 02/12/2016.
 *
 * Class untuk Adapter di List Alasan Reparasi pada activity alasan_konfirmasi
 */

public class Adapter_alasanReparasi extends ArrayAdapter<Alasan_reparasi>{
    Context ctx;
    CheckBox checkBox_contreng;

    List<Alasan_reparasi> listObject;
    ArrayList<String> alasan_build= new ArrayList<>();

    int lViewId;
    int total = 0;


    public Adapter_alasanReparasi(Context context, int resource, List<Alasan_reparasi> objects) {
        super(context, resource, objects);
        this.ctx=context;
        this.listObject=objects;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v==null){
            Log.d("ViewLog", "view is null");
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_alasan_reparasi,null);

        }

        TextView alasan_text = (TextView) v.findViewById(R.id.alasan_tulisan);
        checkBox_contreng= (CheckBox) v.findViewById(R.id.checkBox_pilih);
        TextView harga_text = (TextView) v.findViewById(R.id.listview_alasanReparasi_harga);



        checkBox_contreng.setTag(position);

        alasan_text.setText(listObject.get(position).getAlasan());
        harga_text.setText(String.valueOf(listObject.get(position).getHarga()));

        //kondisi saat contrengan diklik
        checkBox_contreng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (int) buttonView.getTag();
                Log.d("Click", "Position clicked: " + position);
                Log.d("Click", "checkbox changed to? " + isChecked);


                if(isChecked){
                    total+=listObject.get(position).getHarga();
                    Log.d("tambah", "nilai total skrg adalah : " + total);
                    alasan_build.add(listObject.get(position).getAlasan());
                }
                else {
                    if(total>0 && alasan_build.size()>0){
                        total-=listObject.get(position).getHarga();
                        alasan_build.remove(listObject.get(position).getAlasan());
                    }
                }

                TextView setTotal = (TextView)
                        ((Activity)ctx).findViewById(R.id.alasanReparasi_totalHarga);

                setTotal.setText(String.valueOf(total));

                Log.d("Click", "Data Alasan: " + alasan_build.toString());
            }
        });


        return v;
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<String> getAlasan_build() {
        return alasan_build;
    }
}

