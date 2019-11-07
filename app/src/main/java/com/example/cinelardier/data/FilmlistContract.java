package com.example.cinelardier.data;

import android.provider.BaseColumns;

public class FilmlistContract {

    public static final class FilmlistEntry implements BaseColumns {
        public static final String TABLE_NAME = "filmlist";
        public static final String COLUMN_FILM_TITLE = "title";
        public static final String COLUMN_FILM_TIME = "time";
        public static final String COLUMN_FILM_SCENA_NOTE = "note_scena";
        public static final String COLUMN_FILM_REAL_NOTE = "note_real";
        public static final String COLUMN_FILM_MUSIQUE_NOTE = "note_musique";
        public static final String COLUMN_FILM_DESC = "description";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}