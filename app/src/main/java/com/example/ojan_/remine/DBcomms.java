package com.example.ojan_.remine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;


/**
 * Created by ojan_ on 13/11/2016.
 */

public class DBcomms {
    String Query;
    Context ctxt;
    AlertDialog loading = new AlertDialog.Builder(ctxt).create();



    public DBcomms(String query, Context ctx) { //constructor object
        Query = query;
        ctxt = ctx;
    }

    public void checkData(){
         abstract class checkData extends AsyncTask<String,Void,String> {
            private String LoginURL; //=(Belum Tau)
            Dialog
            @Override
            protected Void doInBackground(String... params) {
                //set parameter dalam background execute
                String type=params[0];
                String queIns=params[1];

                //setup koneksi dengan PHP
                try {
                    URL linkURL = new URL(LoginURL); //setURL
                    HttpURLConnection linkcon = (HttpURLConnection) linkURL.openConnection(); //open port 80 connection
                    linkcon.setRequestMethod("POST"); //request http method
                    linkcon.setDoOutput(true); //set data ke PHP
                    linkcon.setDoInput(true); //get data dari PHP

                    //kirim data ke PHP buat SQLquerying
                    OutputStream outStream=linkcon.getOutputStream(); //set untuk kirim data
                    BufferedWriter buffWrite = new BufferedWriter(new OutputStreamWriter(outStream,"UTF-8")); //setup text pengiriman

                    //setup text query yang akan dikirm
                    String Checkdata_inPHP = URLEncoder.encode("sQuery","UTF-8")+"="+URLEncoder.encode(Query,"UTF-8")+&+
                            ;

                    buffWrite.write(Checkdata_inPHP);  //kirim data ke PHP
                    buffWrite.flush();
                    buffWrite.close();

                    //Ambil data dari PHP (Hasil SQLquerying)
                    InputStream inStream=linkcon.getInputStream();
                    BufferedReader buffRead = new BufferedReader(new InputStreamReader(inStream, "iso-8859-1"));
                    String result=""; //inisial result data
                    String line=""; //variabel diambil dari bufferreader data dari PHP

                    while((line = buffRead.readLine()) != null){
                        result += line;

                    }

                    //close read
                    buffRead.close();
                    inStream.close();

                    //close http Connection
                    linkcon.disconnect();




                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPreExecute() { //sebelum dieksekusi dialog loading munculin dulu
                loading.setTitle("Login to DB");
                loading.setMessage("Lagi Login");
                loading.show();
            }

             @Override
             protected void onPostExecute(String result) {
                 loading.setMessage(result);
                 loading.show();
             }
         }


    }
    public void getQuery(){




    }

    public void setQuery(){



    }



}



