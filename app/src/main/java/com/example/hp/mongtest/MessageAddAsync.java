package com.example.hp.mongtest;

import android.os.AsyncTask;

import com.example.hp.mongtest.connect.MyHttpClient;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Sergii Varenyk on 01.12.15.
 */
public class MessageAddAsync extends AsyncTask<JSONObject, Void, Boolean> {
    private OnTaskComplited listener;
    private Boolean result;

    public MessageAddAsync(OnTaskComplited listener){
        this.listener=listener;
    }

        @Override
        protected Boolean doInBackground(JSONObject... voids) {
            try {
                String url = voids[0].getString("url");
                voids[0].remove("url");
                this.result = new MyHttpClient().executeHttpPost(url, voids[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean bool){
            listener.onTaskComplited(this.result);
        }

}
