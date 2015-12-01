package com.example.hp.mongtest;

import android.os.AsyncTask;

import com.example.hp.mongtest.connect.MyHttpClient;
import com.example.hp.mongtest.entity.Message;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sergii Varenyk on 01.12.15.
 */
public class MessageGetAsync extends AsyncTask<String, Void, ArrayList<Message>> {
    private OnTaskComplited listener;
    String result;
    Message msg;
    private ArrayList<Message> docsList = new ArrayList<>();

    public MessageGetAsync(OnTaskComplited listener){
        this.listener=listener;
    }

    @Override
    protected ArrayList<Message> doInBackground(String... voids) {
        try {

            this.result = new MyHttpClient().executeHttpGet(voids[0]);
            msg = new Message();
            docsList = msg.makeMsgList(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return docsList;
    }

    @Override
    protected void onPostExecute(ArrayList<Message> listMessages){
        listener.onTaskComplited(this.docsList);
    }
}