package com.hache.appentrepatas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class EntrePatasDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EntrePatas.db";

    public EntrePatasDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DbContract.ConfigurationEntry.TABLE_NAME + "(" +
                    DbContract.ConfigurationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DbContract.ConfigurationEntry.COLUMN_USUARIO+ " TEXT," +
                    DbContract.ConfigurationEntry.COLUMN_ACTIVO + " TEXT," +
                    DbContract.ConfigurationEntry.COLUMNS_FECHA + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DbContract.ConfigurationEntry.TABLE_NAME;

    private static final String SQL_SEARCH_ENTRIES =
            "SELECT * FROM " + DbContract.ConfigurationEntry.TABLE_NAME;
}
