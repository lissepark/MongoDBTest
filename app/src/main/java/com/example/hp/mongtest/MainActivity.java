package com.example.hp.mongtest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hp.mongtest.connect.MyHttpClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class MainActivity extends Activity {
    private EditText mTitleField;
    private TextView mTextAfterType;
    private Button mSendToDB;
    String result = null;
    String mn;
    JSONObject jsonObject = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextAfterType = (TextView)findViewById(R.id.text_after_type);

        mTitleField=(EditText)findViewById(R.id.text_type);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                mTextAfterType.setText("...");
            }
            @Override
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mTextAfterType.setText(c.toString());
            }
            @Override
            public void afterTextChanged(Editable c) {
            }
        });

        mSendToDB = (Button)findViewById(R.id.send_to_db);
        mSendToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mn = mTitleField.getText().toString();
                new LoadEvent().execute();
            }
        });





        //https://api.mongolab.com/api/1/databases?apiKey=2E81PUmPFI84t7UIc_5YdldAp1ruUPKye
        //https://api.mongolab.com/api/1
    }


    private class LoadEvent extends AsyncTask<Void, Void, ArrayList<String>> {

        ArrayList<String> mCollectionsNames = new ArrayList<>();

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            try {
                jsonObject.put("name",mn);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                result = new MyHttpClient().executeHttpPost("https://api.mongolab.com/api/1/databases/test1/collections/users" +
                        "?apiKey=U1icdnfIyGl0c7BeHPKAlPBvlX8cKvg_",jsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("Ura: it works ", "" + result);

            //MongoCredential mCredent = MongoCredential.createCredential("sergii", "test1", "2".toCharArray());
            //MongoClient mongoClient = new MongoClient(new ServerAddress("ds033734.mongolab.com",33734), Arrays.asList(mCredent));

            //MongoClientURI uri = new MongoClientURI("mongodb://sergii:2@ds033734.mongolab.com:33734/test1");
            //MongoClient mongoClient = new MongoClient(uri);
            //DB db = mongoClient.getDB(uri.getDatabase());
            //MongoDatabase db = mongoClient.getDatabase("test1");
            //db.getCollection("users");

            //Log.d("DBName: ",""+db.getCollection("users").count());

            //mDB = mongoClient.getDB("test1");
            //long myDoc = mDB.getCollection("users").count();
/*
                List<String> databases = mongoClient.getDatabaseNames();

                for (String dbName : databases) {
                    System.out.println("- Database: " + dbName);

                    DB db = mongoClient.getDB(dbName);

                    Set<String> collections = db.getCollectionNames();
                    for (String colName : collections) {
                        mCollectionsNames.add(colName);
                        System.out.println("\t + Collection: " + colName);
                    }
                }
                */
            //Log.d("DBName: ",""+myDoc);
            return mCollectionsNames;
        }

    }
}
