package com.example.hp.mongtest.dao.daoImpl;


import com.example.hp.mongtest.CallbackMongo;
import com.example.hp.mongtest.EventAsync;
import com.example.hp.mongtest.MessageAddAsync;
import com.example.hp.mongtest.MessageGetAsync;
import com.example.hp.mongtest.dao.DaoMessage;
import com.example.hp.mongtest.entity.Message;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Sergii Varenyk on 30.11.15.
 */
public class DaoMessageImpl implements DaoMessage {

    @Override
    public void deleteMessage(String url,CallbackMongo callbackMongo) {
        new EventAsync(callbackMongo).execute(url);
    }

    //example for using
    /*
    new DaoMessageImpl().deleteMessage(String url,new CallbackMongo(){
            @Override
            public void onTaskComplited(Object result) {
                if ((Boolean)result){
                    Toast.makeText(getApplicationContext(),"Message was deleted successfuly",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Message deleting Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
     */

    @Override
    public void insertMessage(JSONObject jsonObject,CallbackMongo callbackMongo) {
        new MessageAddAsync(callbackMongo).execute(jsonObject);
    }

    //example for using
    /*
    new DaoMessageImpl().insertMessage(jsonObject,new CallbackMongo(){
                    @Override
                    public void onTaskComplited(Object result) {
                        if ((Boolean)result){
                            Toast.makeText(getApplicationContext(),"Message was added successfuly",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Message adding Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
     */

    @Override
    public void getMessages(String url,CallbackMongo callbackMongo) {
        new MessageGetAsync(callbackMongo).execute(url);
    }

}
