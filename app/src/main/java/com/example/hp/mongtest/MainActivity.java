package com.example.hp.mongtest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.mongtest.dao.daoImpl.DaoMessageImpl;
import com.example.hp.mongtest.entity.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private EditText mTitleField;
    private TextView mTextAfterType;
    private Button mSendToDB;
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
                try {
                    jsonObject.put("message", mTitleField.getText().toString());
                    jsonObject.put("url","https://api.mongolab.com/api/1/databases/test1/collections/users" +
                            "?apiKey=U1icdnfIyGl0c7BeHPKAlPBvlX8cKvg_");
                    jsonObject.put("createdDate",new Date());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
            }
        });

        mListOfTests = (ListView)findViewById(R.id.list);
        mAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.list_item,mArrayList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View v = convertView;

                if (v == null) {
                    LayoutInflater vi;
                    vi = LayoutInflater.from(getContext());
                    v = vi.inflate(R.layout.list_item, null);
                }
                String message = getItem(position);
                TextView text = (TextView)v.findViewById(R.id.text_item);
                TextView textDate = (TextView)v.findViewById(R.id.text_date);
                text.setText(message);
                String dateFormat = "EEEE, MMM dd, yyyy";
                String dateString = DateFormat.format(dateFormat, new Date()).toString();
                textDate.setText(dateString);
                text.setTextColor(Color.GREEN);
                return v;
            }
        };
        mListOfTests.setAdapter(mAdapter);


        new DaoMessageImpl().getMessages("https://api.mongolab.com/api/1/databases/test1/collections/users" +
                "?apiKey=U1icdnfIyGl0c7BeHPKAlPBvlX8cKvg_",new CallbackMongo(){
            @Override
            public void onTaskComplited(Object result) {
                for (Message message : (ArrayList<Message>)result){
                    mArrayList.add(message.getMessage());
                }
            mAdapter.notifyDataSetChanged();
            }
        });


        mListOfTests.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick (AdapterView parent, View view, int position, long id) {
                System.out.println("Long click");
                view.startActionMode((android.view.ActionMode.Callback) modeCallBack);
                view.setSelected(true);
                return true; } });

    }

    private ActionMode.Callback modeCallBack = new ActionMode.Callback() {
        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("Options");
            mode.getMenuInflater().inflate(R.menu.message_list_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }
    };




    @Override
    protected void onResume() {
        super.onResume();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intentSettings = new Intent(this, SettingsActivity.class);
            startActivity(intentSettings);
        }
        return super.onOptionsItemSelected(item);
    }
}
