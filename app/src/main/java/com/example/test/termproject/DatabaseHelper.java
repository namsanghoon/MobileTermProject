package com.example.test.termproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

public class DatabaseHelper extends SQLiteOpenHelper {
    final  static  String TAG = "DatabaseHelper";
    String sql;
    public DatabaseHelper(Context context) {
        super(context, "lostRegister.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"onCreate()");
        sql = "CREATE TABLE lost_list(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, tel TEXT, location TEXT, detail TEXT, photo TEXT);";
        db.execSQL(sql);
    }
    /*public void onOpen(SQLiteDatabase db){

    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        Log.d(TAG,"onUpgrade() 호출");
        String sql = "DROP TABLE IF EXISTS lost_list";
        db.execSQL(sql);
        onCreate(db);
    }
}
