package com.example.cinelardier.data;


import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestUtil {

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_TITLE, "Film 1");
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_DESC, "Description 1");
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_TIME, new Date().toString());
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_MUSIQUE_NOTE, 1);
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_REAL_NOTE, 2);
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_SCENA_NOTE, 3);
        list.add(cv);

        cv = new ContentValues();
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_TITLE, "Film 2");
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_DESC, "Description 2");
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_TIME, new Date().toString());
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_MUSIQUE_NOTE, 2);
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_REAL_NOTE, 3);
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_SCENA_NOTE, 4);
        list.add(cv);

        cv = new ContentValues();
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_TITLE, "Film 3");
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_DESC, "Description 3");
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_TIME, new Date().toString());
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_MUSIQUE_NOTE, 3);
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_REAL_NOTE, 4);
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_SCENA_NOTE, 5);
        list.add(cv);
        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (FilmlistContract.FilmlistEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(FilmlistContract.FilmlistEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            String ex = e.getMessage();
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }
}