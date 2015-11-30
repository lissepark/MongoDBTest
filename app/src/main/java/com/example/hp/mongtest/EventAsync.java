package com.example.hp.mongtest;

import android.os.AsyncTask;

import com.example.hp.mongtest.connect.MyHttpClient;

import java.io.IOException;

/**
 * Created by Sergii Varenyk on 30.11.15.
 */
public class EventAsync extends AsyncTask<String, Void, Boolean> {
    private OnTaskComplited listener;
    private Boolean result;

    public EventAsync(OnTaskComplited listener){
        this.listener=listener;
    }
    @Override
    protected Boolean doInBackground(String... voids) {
        try {
            this.result = new MyHttpClient().executeHttpDelete(voids[0]);
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
    protected void onPostExecute(Boolean aBoolean) {
        listener.onTaskComplited(this.result);
    }
}
