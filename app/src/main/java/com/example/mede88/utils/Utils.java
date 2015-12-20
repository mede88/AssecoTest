package com.example.mede88.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.example.mede88.data.UrlDataObj;
import com.example.mede88.database.ExecuteSql;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class Utils {

    public static String filename = "MyPREFERENCES";

    public static boolean checkUrlRegEx(String url){
        if(url.matches("^((http(s)?://))(\\w+\\.\\w+)(\\.\\w+)?")){
            return true;
        }
        else {
            return false;
        }
    }

    public static UrlDataObj urlIsUsed(Context context, String hash){
        UrlDataObj urlDataObj;
        //urlDataObj = checkSharedPreferences(hash);
        //if (urlDataObj == null) {
            urlDataObj = checkDB(context, hash);
       // }
        return urlDataObj;
    }



    public static UrlDataObj checkSharedPreferences(String url){
        UrlDataObj obj = null;

        return obj;
    }

    public static UrlDataObj checkDB(Context context, String hash){
        UrlDataObj obj = null;
        ExecuteSql es = new ExecuteSql();
//        obj = es.selectRawSQL(context, hash);
        return obj;
    }

    //returns a first byte of a hash string
    public static int firstHashByte(String hash){
        byte[] ba=null;
        try {
            ba = hash.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ba[0];
    }

    //Creates a hash string from a string and returns a hash string
    public static String getHash(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
            //str.hashCode();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = str.getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();

            String hexStr = "";
            for (int i = 0; i < digest.length; i++) {
                hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
            return hexStr;
        }


}

