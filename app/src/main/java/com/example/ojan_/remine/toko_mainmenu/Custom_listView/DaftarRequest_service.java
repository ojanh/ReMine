package com.example.ojan_.remine.toko_mainmenu.Custom_listView;

/**
 * Created by ojan_ on 04/12/2016.
 */

public class DaftarRequest_service {
    private String alasan_servis;
    private int harga;
    private double lintang,bujur;
    private String user_id;

    public DaftarRequest_service(String alasan_servis, int harga, double lintang, double bujur, String user_id) {
        this.alasan_servis = alasan_servis;
        this.harga = harga;
        this.lintang = lintang;
        this.bujur = bujur;
        this.user_id = user_id;
    }

    public String getAlasan_servis() {
        return alasan_servis;
    }

    public int getHarga() {
        return harga;
    }

    public double getLintang() {
        return lintang;
    }

    public double getBujur() {
        return bujur;
    }

    public String getUser_id() {
        return user_id;
    }


}
