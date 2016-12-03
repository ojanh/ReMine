package com.example.ojan_.remine.Custom_listview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.ojan_.remine.R;

import org.w3c.dom.Text;

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
        View listV = View.inflate(mContext, R.layout.listview_alasan_reparasi, null);
        TextView text_alasan = (TextView) listV.findViewById(R.id.alasan_tulisan);
        CheckBox checkBox_pilih = (CheckBox) listV.findViewById(R.id.checkBox_pilih);

        text_alasan.setText(mAlasan_reparasi.get(position).getAlasan());

        listV.setTag(mAlasan_reparasi.get(position).getId());

        return listV;
    }
}
