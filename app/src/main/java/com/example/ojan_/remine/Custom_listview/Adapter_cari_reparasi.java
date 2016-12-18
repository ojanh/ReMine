package com.example.ojan_.remine.Custom_listview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ojan_.remine.R;

import java.util.List;

/**
 * Created by Rumahku on 01/12/2016.
 * Class untuk objek dari isi Alasan Reparasi
 */

public class Adapter_cari_reparasi extends BaseAdapter {
    private Context mContext;
    private List<Toko_reparasi> mToko_reparasi;

    public Adapter_cari_reparasi(Context mContext, List<Toko_reparasi> mToko_reparasi) {
        this.mContext = mContext;
        this.mToko_reparasi = mToko_reparasi;
    }


    @Override
    public int getCount() {
        return mToko_reparasi.size();
    }

    @Override
    public Object getItem(int position) {
        return mToko_reparasi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.listview_cari_reparasi, null);
        TextView nama_toko = (TextView) v.findViewById(R.id.nama_toko);
        TextView lintang_toko = (TextView) v.findViewById(R.id.lintang_toko);
        TextView bujur_toko = (TextView) v.findViewById(R.id.bujur_toko);

        String lintang = String.valueOf(mToko_reparasi.get(position).getLintang_toko());
        String bujur = String.valueOf(mToko_reparasi.get(position).getBujur_toko());


        //set nama toko
        nama_toko.setText(mToko_reparasi.get(position).getNama_toko());
        lintang_toko.setText(lintang);
        bujur_toko.setText(bujur);

        v.setTag(mToko_reparasi.get(position).getId());
        return v;
    }
}
