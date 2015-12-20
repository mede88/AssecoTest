package com.example.mede88.myapplication1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mede88.data.UrlDataObj;
import com.example.mede88.database.AndroidSQL;
import com.example.mede88.database.ExecuteSql;
import com.example.mede88.utils.Utils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText url;
    private Button gpButton;
    private String strUrl;           // url that was entered by user
    private String sHashValue;             // hash code from web site content
    private String sLocationValue;    // where are data saved SharedPreferences or DB
    private TextView tvUrl, tvHash, tvLocation, tvUrlValue, tvHashValue, tvLocationValue;
    private final static int DISABLE_BT_TIME = 5000;
    private UrlDataObj urlDataObj;
    public static String filename = "MyPREFERENCES";
    SharedPreferences someData;
    AndroidSQL aSQL;
    private OkHttpClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();


    }

    @Override
    public void onClick(View v) {
        strUrl = url.getText().toString();

        //if url is correct
        if (Utils.checkUrlRegEx(strUrl)) {
            if (ExecuteSql.ifUrlIsInDatabaseExecute(this, strUrl)) {
                //ExecuteSql.getSqlDataExecute();
            } else {
                getWebContent(strUrl);

            }

        } else {
            Toast.makeText(getApplicationContext(), "Incorrect URL format.", Toast.LENGTH_LONG).show();
        }
    }

    public String getStrUrl() {
        return strUrl;
    }

    private void initWidgets() {
        url = (EditText) findViewById(R.id.url);
        gpButton = (Button) findViewById(R.id.getPageButton);
        gpButton.setOnClickListener(this);
        tvUrl = (TextView) findViewById(R.id.tvUrl);
        tvHash = (TextView) findViewById(R.id.tvHash);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvUrlValue = (TextView) findViewById(R.id.tvUrlValue);
        tvHashValue = (TextView) findViewById(R.id.tvHashValue);
        tvLocationValue = (TextView) findViewById(R.id.tvLocationValue);
    }

    public void getWebContent(final String url) {
        client = new OkHttpClient();
        new Thread(new Runnable() {
            public void run() {

                Request request = new Request.Builder()
                        .url(url)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    manageHash(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();

    }

    private void manageHash(Response response) {
        try {
            sHashValue = Utils.getHash(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (sHashValue != null) {
            ExecuteSql.insertDataToDatabaseExecute(this, sHashValue, strUrl, UrlDataObj.LocationType.DATABASE);
        }
        // IF hash already exists in database or SharedPreferences
       /* if((urlDataObj = Utils.urlIsUsed(MainActivity.this, sHashValue))!=null) {
            Thread th =  new Thread(){
                @Override
                public void run(){
                    try {
                        synchronized(this){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                            displayUrlData(urlDataObj);
                                }
                            });
                            gpButton.setEnabled(false);
                            wait(DISABLE_BT_TIME);
                        }
                    }
                    catch(InterruptedException ex){
                        ex.printStackTrace();
                    }
                    gpButton.setEnabled(true);
                }
            };
            th.start();
    }*/
        // hash code doesn't exist in database or SharedPreferences
        else {
            // if first byte of hash code is even number store it to a database
            if ((Utils.firstHashByte(sHashValue) % 2) == 0) {
                urlDataObj = new UrlDataObj(strUrl, sHashValue, UrlDataObj.LocationType.DATABASE);
            }
            // first byte of hash code is odd number store it to a SharedPreferences
            else {
                urlDataObj = new UrlDataObj(strUrl, sHashValue, UrlDataObj.LocationType.SHAREDPREFERENCES);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    displayUrlData(urlDataObj);
                }
            });
        }
    }


    private void displayUrlData(UrlDataObj urlDataObj) {
        tvUrlValue.setText(urlDataObj.getUrl());
        tvHashValue.setText(urlDataObj.getHash());
        tvLocationValue.setText(urlDataObj.getLocationType().toString());
    }

}




