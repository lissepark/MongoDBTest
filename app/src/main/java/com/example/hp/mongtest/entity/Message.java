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
    private String mMessage;
    private Date mDate;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }


    public static Message makeMessage(Object obj) {
        Message msg = null;
        try {
            if (obj instanceof JSONObject) {
                JSONObject dataObj = (JSONObject)obj;
                if (dataObj.has("message")) {
                    msg = new Message();
                    msg.setMessage(dataObj.getString("message"));
                }
                //if (dataObj.has("date")) {
                //    msg.setDate(dataObj.getString("date"));
               // }
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
/*
        try {
            jsonObject = new JSONObject((String) obj);
            Log.i("JSONObj",""+jsonObject.toString());
            ja = (JSONArray) jsonObject.get("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/
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
