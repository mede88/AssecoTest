package com.example.mede88.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mede88.data.UrlDataObj;


public class AndroidSQL {



        public static final String KEY_HASH = "hash";
        public static final String KEY_URL = "url";
        public static final String KEY_LOCATION = "db";

        private static final String DATABASE_NAME = "URLDB";
        private static final String DATABASE_TABLE = "urls";
        private static final int DATABASE_VERSION = 1;

        private DbHelper ourHelper;
        private final Context ourContext;
        private SQLiteDatabase ourDatabase;

        private static class DbHelper extends SQLiteOpenHelper {

            public DbHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
                // TODO Auto-generated constructor stub
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                // TODO Auto-generated method stub
                db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                                KEY_HASH + " TEXT PRIMARY KEY NOT NULL, " +
                                KEY_URL + " TEXT NOT NULL, " +
                                KEY_LOCATION + " TEXT NOT NULL);"
                );
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // TODO Auto-generated method stub
                db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
                onCreate(db);
            }
        }

        public AndroidSQL(Context c){
            ourContext = c;
        }

        public AndroidSQL open() throws SQLException {
            ourHelper = new DbHelper(ourContext);
            ourDatabase = ourHelper.getWritableDatabase();
            return this;
        }
        public void close(){
            ourHelper.close();
        }

        public long insertDataToDatabase(String hash, String url, String location) {
            // TODO Auto-generated method stub
            ContentValues cv = new ContentValues();
            cv.put(KEY_HASH, hash);
            cv.put(KEY_URL, url);
            cv.put(KEY_LOCATION, location);
            return ourDatabase.insert(DATABASE_TABLE, null, cv);
        }

        public UrlDataObj getSqlData(String url) {
            // TODO Auto-generated method stub
            String[] columns = new String[]{ KEY_HASH, KEY_URL, KEY_LOCATION};
            Cursor c = ourDatabase.query(DATABASE_TABLE, columns,  KEY_URL + "=" + "\"" + url + "\"", null, null, null, null);
            if (c != null && c.getCount()>0){
                c.moveToFirst();
                UrlDataObj udo = new UrlDataObj(c.getString(1), c.getString(0), UrlDataObj.LocationType.DATABASE);
                return udo;
            }
            return null;
        }

        public boolean ifUrlIsInDatabase(String url) throws SQLException{
            // TODO Auto-generated method stub
            boolean resp = false;
            String[] columns = new String[]{ KEY_HASH, KEY_URL, KEY_LOCATION};
            Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_URL + "=" + "\"" + url + "\"", null, null, null, null);
            if (c != null && c.getCount()>0){
                resp = true;
            }
            return resp;
        }
}
