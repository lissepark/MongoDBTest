package com.example.hp.mongtest.connect;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sergii Varenyk on 20.10.15.
 */
public class MyHttpClient {

    /**
     * Performs an HTTP Post request to the specified url with the specified parameters.
     */
    public static boolean executeHttpPost(String httpUrl, JSONObject json) throws Exception {
        Boolean bool = false;
        HttpURLConnection c = null;
        URL url = new URL(httpUrl);
        OutputStreamWriter osw = null;
        try {
            c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("POST");
            c.setReadTimeout(10000);
            c.setRequestProperty("Content-Type", "application/json");
            c.setDoOutput(true);
            c.setChunkedStreamingMode(0);
            c.connect();
            OutputStream out = new BufferedOutputStream(c.getOutputStream());
            osw = new OutputStreamWriter(out);
            osw.write(json.toString());
            osw.flush();
            osw.close();
            if (c.getResponseCode() == 200){
                bool = true;
            }
            c.disconnect();
        } finally {
            if (osw != null) {
                osw.close();
            }
            if (c != null) {
                c.disconnect();
            }
        }
        return bool;
    }

    /**
     * Performs an HTTP GET request to the specified url.
     */
    public static String executeHttpGet(String httpUrl) throws Exception {
        HttpURLConnection c = null;
        URL url = new URL(httpUrl);
        BufferedReader reader = null;
        try {
            c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setReadTimeout(10000);
            c.connect();
            reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder buf = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buf.append(line + "\n");
            }
            return (buf.toString());

        } finally {
            if (reader != null) {
                reader.close();
            }
            c.disconnect();
        }
    }


    /**
     * Performs an HTTP DELETE request to the specified url.
     */
    public static boolean executeHttpDelete(String httpUrl) throws Exception {
        Boolean bool = false;
        HttpURLConnection c = null;
        URL url = new URL(httpUrl);
        OutputStreamWriter osw = null;
        try {
            c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("DELETE");
            c.setReadTimeout(10000);
            c.setRequestProperty("Content-Type", "application/json");
            c.connect();
            if (c.getResponseCode() == 200){
                bool = true;
            }
            c.disconnect();
        } finally {
            if (osw != null) {
                osw.close();
            }
            if (c != null) {
                c.disconnect();
            }
        }
        return bool;
    }
}