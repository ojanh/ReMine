package com.example.ojan_.remine.Custom_listview;

import org.w3c.dom.Text;

/**
 * Created by ojan_ on 02/12/2016.
 */

public class Alasan_reparasi {

    private int id;
    private boolean isChecked;
    private String alasan;
    private int harga;

    public Alasan_reparasi(int id, boolean isChecked, String alasan, int harga) {
        this.id = id;
        this.isChecked = isChecked;
        this.alasan = alasan;
        this.harga = harga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        alasan = alasan;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
