package com.example.ojan_.remine.Custom_listview;

import org.w3c.dom.Text;

/**
 * Created by ojan_ on 02/12/2016.
 */

public class Alasan_reparasi {

    private int id;
    private boolean isChecked;
    private String Alasan;

    public Alasan_reparasi(int id, boolean isChecked, String alasan) {
        this.id = id;
        this.isChecked = isChecked;
        Alasan = alasan;
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
        return Alasan;
    }

    public void setAlasan(String alasan) {
        Alasan = alasan;
    }
}
