package com.example.hp.mongtest.connect;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Created by Sergii Varenyk on 20.10.15.
 */
public class MyHttpClient {

    /**
     * Performs an HTTP Post request to the specified url with the specified parameters.
     */
    public static String executeHttpPost(String httpUrl, JSONObject json) throws Exception {
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
            c.disconnect();
            return executeHttpGet(httpUrl);
        } finally {
            if (osw != null) {
                osw.close();
            }
            if (c != null) {
                c.disconnect();
            }
        }

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
    public static String executeHttpDelete(String httpUrl, JSONObject jsonObject) throws Exception {
        HttpURLConnection c = null;
        URL url = new URL(httpUrl);
        OutputStreamWriter osw = null;
        try {
            c = (HttpURLConnection) url.openConnection();

            c.setRequestMethod("DELETE");
            c.setReadTimeout(10000);
            c.setRequestProperty("Content-Type", "application/json");
            //c.setDoOutput(true);
            //c.setChunkedStreamingMode(0);

            c.connect();
            OutputStream out = new BufferedOutputStream(c.getOutputStream());
            osw = new OutputStreamWriter(out);
            osw.write(jsonObject.toString());
            osw.flush();
            osw.close();
            c.disconnect();
            return executeHttpGet(httpUrl);
        } finally {
            if (osw != null) {
                osw.close();
            }
            if (c != null) {
                c.disconnect();
            }
        }
    }
}
    /**
     * Performs an HTTP Put request to the specified url with the
     * specified parameters.
     *
     * @param url The web address to post the request to
     * @param putParameters The parameters to send via the request
     * @return The result of the request
     * @throws Exception
     */
 /*   public static String executeHttpPut(String url, JSONObject json) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpPut request = new HttpPut(url);

            request.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF-8")));
            request.setHeader( "Content-Type", "application/json");
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

*/