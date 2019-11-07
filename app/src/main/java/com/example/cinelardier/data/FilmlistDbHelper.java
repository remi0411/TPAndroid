package com.example.cinelardier.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.cinelardier.data.FilmlistContract.*;


public class FilmlistDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "filmslist.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 2;

    // Constructor
    public FilmlistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold filmlist data
        final String SQL_CREATE_FILMLIST_TABLE = "CREATE TABLE " + FilmlistEntry.TABLE_NAME + " (" +
                FilmlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FilmlistEntry.COLUMN_FILM_TITLE + " TEXT NOT NULL, " +
                FilmlistEntry.COLUMN_FILM_TIME + " TEXT NOT NULL, " +
                FilmlistEntry.COLUMN_FILM_SCENA_NOTE + " INTEGER NOT NULL, " +
                FilmlistEntry.COLUMN_FILM_REAL_NOTE + " INTEGER NOT NULL, " +
                FilmlistEntry.COLUMN_FILM_MUSIQUE_NOTE + " INTEGER NOT NULL, " +
                FilmlistEntry.COLUMN_FILM_DESC + " TEXT NOT NULL, " +
                FilmlistEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FILMLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FilmlistEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}