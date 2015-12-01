package com.example.hp.mongtest.dao;

import com.example.hp.mongtest.CallbackMongo;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Sergii Varenyk on 30.11.15.
 */
public interface DaoMessage {

    public void deleteMessage(String idMessage, CallbackMongo callbackMongo);

    public void insertMessage(JSONObject jsonObject, CallbackMongo callbackMongo) throws JSONException;

    public void getMessages(String url, CallbackMongo callbackMongo);
}
