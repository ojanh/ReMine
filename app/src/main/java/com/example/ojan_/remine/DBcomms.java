package com.example.ojan_.remine;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

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


/**
 * Created by ojan_ on 13/11/2016.
 */

public class DBcomms {
    String Query;
    String type;

    public DBcomms(String type,String query) {
        this.type = type;
        Query = query;
    }




    public String checkData(){
        String LoginURL = ""; //URL link ke php belom ada cuy
        String result="";
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

                    //setup POST HTTP Method yang akan dikirm
                    String Checkdata_inPHP = URLEncoder.encode("sQuery","UTF-8")+"="+URLEncoder.encode(Query,"UTF-8")
                            +"&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8");

                    //kirim data ke PHP
                    buffWrite.write(Checkdata_inPHP);
                    buffWrite.flush(); //hapus buffer
                    buffWrite.close(); //close buffer

                    //Ambil data dari PHP (Hasil SQLquerying)
                    InputStream inStream=linkcon.getInputStream();
                    BufferedReader buffRead = new BufferedReader(new InputStreamReader(inStream, "iso-8859-1"));

                    //inisial result data
                    
                    String line=""; //variabel diambil dari bufferreader data dari PHP


                    //write data dari php echo ke var line lalu add ke result
                    while((line = buffRead.readLine()) != null){
                        result += line;
                    }

                    //close read
                    buffRead.close();
                    inStream.close();

                    //close http Connection
                    linkcon.disconnect();

                    //return hasil result
                    


                } catch (IOException e){
                    e.printStackTrace();
                }

            return result;       
            }





    public String getQuery(){
        String LoginURL = ""; //URL link ke php belom ada cuy
        String result="";
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

            //setup POST HTTP Method yang akan dikirm
            String Checkdata_inPHP = URLEncoder.encode("sQuery","UTF-8")+"="+URLEncoder.encode(Query,"UTF-8")
                    +"&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8");

            //kirim data ke PHP
            buffWrite.write(Checkdata_inPHP);
            buffWrite.flush(); //hapus buffer
            buffWrite.close(); //close buffer

            //Ambil data dari PHP (Hasil SQLquerying)
            InputStream inStream=linkcon.getInputStream();
            BufferedReader buffRead = new BufferedReader(new InputStreamReader(inStream, "iso-8859-1"));

            //inisial result data

            String line=""; //variabel diambil dari bufferreader data dari PHP


            //write data dari php echo ke var line lalu add ke result
            while((line = buffRead.readLine()) != null){
                result += line;
            }

            //close read
            buffRead.close();
            inStream.close();

            //close http Connection
            linkcon.disconnect();

            //return hasil result



        } catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }





    public void setQuery(){



    }



}



