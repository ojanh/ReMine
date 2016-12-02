package com.example.ojan_.remine.Custom_listview;

/**
 * Created by Rumahku on 01/12/2016.
 */

public class Toko_reparasi {
    private int id;
    private String nama_toko;
    private double lintang_toko;
    private double bujur_toko;

    public Toko_reparasi(int id, String nama_toko, double lintang_toko, double bujur_toko) {
        this.id = id;
        this.nama_toko = nama_toko;
        this.lintang_toko = lintang_toko;
        this.bujur_toko = bujur_toko;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_toko() {
        return nama_toko;
    }

    public void setNama_toko(String nama_toko) {
        this.nama_toko = nama_toko;
    }

    public double getLintang_toko() {
        return lintang_toko;
    }

    public void setLintang_toko(double lintang_toko) {
        this.lintang_toko = lintang_toko;
    }

    public double getBujur_toko() {
        return bujur_toko;
    }

    public void setBujur_toko(double bujur_toko) {
        this.bujur_toko = bujur_toko;
    }
}
