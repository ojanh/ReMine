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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by ojan_ on 13/11/2016.
 */

public class DBcomms {

    String LoginIP = "http://192.168.8.101/"; //IP Address


    //set IP  baru kalau IP nya bukan Localhost (127.0.0.1)
    public void setCustomIP(String newIP) {
        LoginIP = newIP;
    }

        //untuk cek data (Buat Login)
    public String checkData(String EncodetoPHP) {

        String result = "";
        //setup koneksi dengan PHP
        try {

            //setup dengan HTTP port dengan metode POST request
            URL linkURL = new URL(LoginIP + "check_data.php"); //setURL
            HttpURLConnection linkcon = (HttpURLConnection) linkURL.openConnection(); //open port 80 connection
            linkcon.setRequestMethod("POST"); //request http method
            linkcon.setDoOutput(true); //set data ke PHP
            linkcon.setDoInput(true); //get data dari PHP

            //kirim data ke PHP buat SQLquerying
            OutputStream outStream = linkcon.getOutputStream(); //set untuk kirim data
            BufferedWriter buffWrite = new BufferedWriter(new OutputStreamWriter(outStream, "UTF-8")); //setup text pengiriman

            //setup POST HTTP Method yang akan dikirm


            //kirim data ke PHP

            buffWrite.write(EncodetoPHP);
            buffWrite.flush(); //hapus buffer
            buffWrite.close(); //close buffer

            //Ambil data dari PHP (Hasil SQLquerying)
            InputStream inStream = linkcon.getInputStream();
            BufferedReader buffRead = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));

            //inisial result data
            String line; //variabel diambil dari bufferreader data dari PHP
            int lineCount = 0;
            StringBuilder sb = new StringBuilder();

            //write data dari php echo ke var line lalu add ke result
            while ((line = buffRead.readLine()) != null) {
                sb.append(line + "\n");

            }
            result = sb.toString();
                    /*
                    buffRead.skip(lineCount);

                    while ((line = buffRead.readLine()) != null){
                        roleLogin += line;

                    }*/

            //close read
            buffRead.close();
            inStream.close();

            //close http Connection
            linkcon.disconnect();


        } catch (IOException e) {
            e.printStackTrace();
        }
        //return hasil result

        return result;
    }

       //getQuery (Buat Request SQL table)
    //result berupa JSON Object
    public String getQuery(String postEncode) {

        String result = "";
        result=HTTPconnection("get_data.php", postEncode);

        return result;
    }


    //setQuery (Data Manipulation (Update dan insert into))
    public void setQuery(String postEncode) {
        String result="";
       result=HTTPconnection("set_data.php", postEncode);


    }

    //koneksi dengan HTTP (Function)
    String HTTPconnection(String PHP_FilePath, String PostData) {
        String result = "";
        try {

            //setup dengan HTTP port dengan metode POST request
            URL linkURL = new URL(LoginIP + PHP_FilePath); //setURL
            HttpURLConnection linkcon = (HttpURLConnection) linkURL.openConnection(); //open port 80 connection
            linkcon.setRequestMethod("POST"); //request http method
            linkcon.setDoOutput(true); //set data ke PHP
            linkcon.setDoInput(true); //get data dari PHP


            //kirim data ke PHP buat SQLquerying
            OutputStream outStream = linkcon.getOutputStream(); //set untuk kirim data
            BufferedWriter buffWrite = new BufferedWriter(new OutputStreamWriter(outStream, "UTF-8")); //setup text pengiriman

            //setup POST HTTP Method yang akan dikirm
            //kirim data ke file PHP
            buffWrite.write(PostData);
            buffWrite.flush(); //hapus buffer
            buffWrite.close(); //close buffer

            //Ambil data dari PHP (Hasil SQLquerying)
            InputStream inStream = linkcon.getInputStream();
            BufferedReader buffRead = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));

            //inisial result data
            String line; //variabel diambil dari bufferreader data dari PHP
            StringBuilder sb = new StringBuilder();

            //write data dari php echo ke var line lalu build di variabel sb
            while ((line = buffRead.readLine()) != null) {
                sb.append(line + "\n");

            }
            result = sb.toString();


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}


