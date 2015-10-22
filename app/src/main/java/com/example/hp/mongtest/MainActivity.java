package com.example.hp.mongtest;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.mongtest.connect.MyHttpClient;
import com.example.hp.mongtest.entity.Message;
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
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> mArrayList = new ArrayList<>();
    private ListView mListOfTests;

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
                Log.d("array size", "" + mArrayList.size());


            }
        });

        mListOfTests = (ListView)findViewById(R.id.list);
        mAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,mArrayList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position,convertView,parent);
                TextView text = (TextView)view.findViewById(android.R.id.text1);

                text.setTextColor(Color.GREEN);
                return view;
            }
        };
        mListOfTests.setAdapter(mAdapter);
    }


    private class LoadEvent extends AsyncTask<Void, Void, ArrayList<String>> {
        ArrayList<Message> docsList = new ArrayList<>();
        Message msg;

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            try {
                jsonObject.put("message",mn);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                result = new MyHttpClient().executeHttpDelete("https://api.mongolab.com/api/1/databases/test1/collections/users" +
                        "?apiKey=U1icdnfIyGl0c7BeHPKAlPBvlX8cKvg_",jsonObject);

                msg = new Message();
                docsList = msg.makeMsgList(result);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayList<String> imagesNamesList = new ArrayList<String>();
            for(Message lp : docsList) {
                imagesNamesList.add(lp.getMessage());
            }
            return imagesNamesList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> namesList){
            for (String st:namesList){
                mArrayList.add(st);
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}
