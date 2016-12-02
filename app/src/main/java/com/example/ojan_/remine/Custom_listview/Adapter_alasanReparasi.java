package com.example.ojan_.remine.Custom_listview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ojan_ on 02/12/2016.
 */

public class Adapter_alasanReparasi extends BaseAdapter{
    private Context mContext;
    private List<Alasan_reparasi> mAlasan_reparasi;

    public Adapter_alasanReparasi(Context mContext, List<Alasan_reparasi> mAlasan_reparasi) {
        this.mContext = mContext;
        this.mAlasan_reparasi = mAlasan_reparasi;
    }

    @Override
    public int getCount() {
        return mAlasan_reparasi.size();
    }

    @Override
    public Object getItem(int position) {
        return mAlasan_reparasi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }
}
