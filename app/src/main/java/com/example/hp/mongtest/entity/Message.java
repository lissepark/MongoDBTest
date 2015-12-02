package com.example.hp.mongtest.entity;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Sergii Varenyk on 21.10.15.
 */
public class Message {
    private String idMessage;
    private String mMessage;

    public String getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(String idMessage) {
        this.idMessage = idMessage;
    }

    private String mDate;
    private String url;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static Message makeMessage(Object obj) {
        Message msg = null;
        try {
            if (obj instanceof JSONObject) {
                JSONObject dataObj = (JSONObject)obj;
                msg = new Message();
                if (dataObj.has("message")) {
                    msg.setMessage(dataObj.getString("message"));
                }
                if (dataObj.has("_id")) {
                    JSONObject jsobj = dataObj.getJSONObject("_id");
                    msg.setIdMessage(jsobj.getString("$oid"));
                }
                if (dataObj.has("createdDate")) {
                    msg.setDate(dataObj.getString("createdDate"));
                }
            }
        } catch (JSONException e) {
            Log.i("Parse error %s", e.getLocalizedMessage());
        }
        return msg;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static ArrayList<Message> makeMsgList(Object obj) {
        ArrayList<Message> msgList = new ArrayList<Message>();
        JSONArray ja = null;
        try {
            ja = new JSONArray((String) obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = null;
        try {
            if (ja instanceof JSONArray) {
                int size = ja.length();
                for (int i = 0; i < size; i++) {
                    JSONObject dataObj = ja.getJSONObject(i);
                    Log.i("JSONObj", "" + dataObj.toString());
                    Message mm = makeMessage(dataObj);
                    if (mm!=null) {
                        msgList.add(mm);
                    }
                }
            }
        } catch (JSONException e){
            Log.i("Parse JSON error %s", e.getLocalizedMessage());
        }
        return msgList;
    }
}
