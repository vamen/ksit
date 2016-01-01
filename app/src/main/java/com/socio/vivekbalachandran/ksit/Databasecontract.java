package com.socio.vivekbalachandran.ksit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Vivek Balachandran on 9/30/2015.
 */
public class Databasecontract {
    public static final String TABLE1 = "currencydatabase1";
    public static final String TABLE2 = "currencydatabase2";
    public static final String TABLE3 = "currencydatabase3";
    public static final String DB_NAME = "mydatabase.db";
    public static final String DB_COLOUMN11 = "Name_ID";
    public static final String DB_COLOUMN12 = "value";
    public static final String CREATE_TABLE1 = "CREATE TABLE " + Databasecontract.TABLE1 + "(" + Databasecontract.DB_COLOUMN11 + " VARCHAR ," + Databasecontract.DB_COLOUMN12 + " Real);";
    public static final String DB_COLOUMN13 = "DATE";
    public static final int DB_VERSION = 2;
    static String[] keyArray = new String[32];
    static double value[] = new double[32];
    Context ctx;
    Database db;

    Databasecontract(Context context) {
        ctx = context;
        db = new Database(ctx);
    }

    public void staticjsonformater(String s) throws JSONException {
        SQLiteDatabase mdatabase = db.getWritableDatabase();

        JSONObject JSONdatacastobject = new JSONObject(s);
        JSONObject jsonsubobject = JSONdatacastobject.getJSONObject("rates");
        Iterator<String> jsonkeys = jsonsubobject.keys();
//        Set<String> key = (Set<String>) jsonsubobject.keys();
     //   Log.d("length of key", "*******" + key.size() + "*****");

        for (int i = 0; jsonkeys.hasNext(); i++) {
            keyArray[i] = jsonkeys.next();
            value[i] = jsonsubobject.getDouble(keyArray[i]);
            Log.d("jsondb", keyArray[i]);
            ContentValues contentvalues = new ContentValues();
            contentvalues.put(DB_COLOUMN11, keyArray[i]);
            contentvalues.put(DB_COLOUMN11, jsonsubobject.getDouble(keyArray[i]));
            mdatabase.insert(TABLE1, null, contentvalues);

        }

    }

    public Cursor qeerythedata(int day) {
        SQLiteDatabase mdatabase = db.getReadableDatabase();
        Cursor cursor=mdatabase.query(true, TABLE1, null, null, null, null, null, null, null);
//        Log.d("cursor","**"+cursor.getString(0)+"###" );
        return cursor;
    }

    public void changedatabase() {
        //// TODO: 10/6/2015

    }

}
