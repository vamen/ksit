package com.socio.vivekbalachandran.ksit;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Vivek Balachandran on 9/30/2015.
 */
public class Database extends SQLiteOpenHelper {

    Context ctx;

    public Database(Context context) {

        super(context, Databasecontract.DB_NAME, null, Databasecontract.DB_VERSION);
        ctx = context;
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        try {

            db.execSQL(Databasecontract.CREATE_TABLE1);
            Log.d("SQL", "tablecreated");

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("SQL", "table not created ");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Databasecontract.TABLE1);
        onCreate(db);
    }
}
