package com.example.hp.mongtest.dao;

import com.example.hp.mongtest.CallbackMongo;

/**
 * Created by Sergii Varenyk on 30.11.15.
 */
public interface DaoMessage {

    public void deleteMessage(String idMessage, CallbackMongo callbackMongo);
}
