package com.example.divyanshukumar.yourapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.divyanshukumar.yourapp.data.AppContract.AppEntry;

public class AppDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "appinfo.db";

    /**
     * YOU NEED TO INCREMENT IT , IF YOU CHANGE THE SCHEMA OF DATABASE
     */
    private static final int DATABASE_VERSION = 1;

    public AppDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

//        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_APPS_TABLE = "CREATE TABLE " + AppEntry.TABLE_NAME + "("
                + AppEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AppEntry.COLUMN_APP_NAME + " TEXT, "
                + AppEntry.COLUMN_APP_PACKAGE + " TEXT); ";

        db.execSQL(SQL_CREATE_APPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
