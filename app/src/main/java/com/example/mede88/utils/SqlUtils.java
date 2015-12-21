package com.example.mede88.utils;

import android.content.Context;
import com.example.mede88.data.UrlDataObj;
import com.example.mede88.database.AndroidSQL;


public class SqlUtils {

    public static void insertDataToDatabaseExecute(Context context, String hash, String url, UrlDataObj.LocationType location) {
        AndroidSQL entry = new AndroidSQL(context);
        entry.open();
        entry.insertDataToDatabase(hash, url, location.toString());
        entry.close();
    }

    public static UrlDataObj getSqlDataExecute(Context context, String url) {
        UrlDataObj udo = null;
        AndroidSQL entry = new AndroidSQL(context);
        entry.open();
        udo = entry.getSqlData(url);
        entry.close();
        return udo;
    }

    public static boolean ifUrlIsInDatabaseExecute(Context context, String url) {
        boolean existsInDatabase = false;
        AndroidSQL entry = new AndroidSQL(context);
        entry.open();
        existsInDatabase = entry.ifUrlIsInDatabase(url);
        entry.close();
        return existsInDatabase;
    }

}
