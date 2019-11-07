package com.example.cinelardier;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.cinelardier.data.FilmlistContract;
import com.example.cinelardier.data.FilmlistDbHelper;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class AddFilmActivity extends AppCompatActivity {

    private EditText mTitleEditText;
    private EditText mDescEditText;
    private EditText mTimeEditText;
    private SeekBar mNoteScenaSeekBar;
    private SeekBar mNoteRealSeekBar;
    private SeekBar mNoteMusiqueSeekBar;
    private SQLiteDatabase mDb;
    private FilmListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SeekBar scenaSeekBar = (SeekBar)this.findViewById(R.id.scenario_seekbar);
        scenaSeekBar.incrementProgressBy(1);
        scenaSeekBar.setMax(10);
        scenaSeekBar.setProgress(5);
        final TextView scenaSeekBarValue = (TextView)this.findViewById(R.id.note_scenario_text_view);
        scenaSeekBarValue.setText(String.valueOf(scenaSeekBar.getProgress()));
        scenaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                scenaSeekBarValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        SeekBar realSeekBar = (SeekBar)this.findViewById(R.id.realisation_seekbar);
        realSeekBar.incrementProgressBy(1);
        realSeekBar.setMax(10);
        realSeekBar.setProgress(5);
        final TextView realSeekBarValue = (TextView)this.findViewById(R.id.note_realisation_text_view);
        realSeekBarValue.setText(String.valueOf(realSeekBar.getProgress()));
        realSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                realSeekBarValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar musiqueSeekBar = (SeekBar)this.findViewById(R.id.musique_seekbar);
        musiqueSeekBar.incrementProgressBy(1);
        musiqueSeekBar.setMax(10);
        musiqueSeekBar.setProgress(5);
        final TextView musiqueSeekBarValue = (TextView)this.findViewById(R.id.note_musique_text_view);
        musiqueSeekBarValue.setText(String.valueOf(musiqueSeekBar.getProgress()));
        musiqueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                musiqueSeekBarValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        FilmlistDbHelper dbHelper = new FilmlistDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        Cursor cursor = getAllFilms();
        mAdapter = new FilmListAdapter(this, cursor);

        mTitleEditText= (EditText) this.findViewById(R.id.title_edit_text);
        mDescEditText= (EditText) this.findViewById(R.id.desc_edit_text);
        mTimeEditText = (EditText) this.findViewById(R.id.time_edit_text);
        mNoteScenaSeekBar = (SeekBar) this.findViewById(R.id.scenario_seekbar);
        mNoteRealSeekBar = (SeekBar) this.findViewById(R.id.realisation_seekbar);
        mNoteMusiqueSeekBar = (SeekBar) this.findViewById(R.id.musique_seekbar);
    }

    public void addToFilmslist(View view) {
        if (mTitleEditText.getText().length() == 0 || mDescEditText.getText().length() == 0 || mTimeEditText.getText().length() == 0) {
            Snackbar.make(view, "Données non complètes", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        // COMPLETED (11) Use Integer.parseInt to parse mNewPartySizeEditText.getText to an integer
        try {
            //mNewPartyCountEditText inputType="number", so this should always work
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime dt = LocalDateTime.parse(mTimeEditText.getText().toString(),f);
        } catch (DateTimeParseException ex) {

            Snackbar.make(view, "Format de date invalide", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
            // COMPLETED (12) Make sure you surround the Integer.parseInt with a try catch and log any exception
        }

        addNewFilm(
                mTitleEditText.getText().toString(),
                mDescEditText.getText().toString(),
                mTimeEditText.getText().toString(),
                mNoteScenaSeekBar.getProgress(),
                mNoteRealSeekBar.getProgress(),
                mNoteMusiqueSeekBar.getProgress()
        );

        mAdapter.swapCursor(getAllFilms());

        mTitleEditText.clearFocus();
        mTimeEditText.getText().clear();
        mTitleEditText.getText().clear();
        mDescEditText.getText().clear();
        finish();
    }

    private long addNewFilm(String title, String desc, String time, int scena, int real, int musique) {
        ContentValues cv = new ContentValues();
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_TITLE, title);
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_DESC, desc);
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_TIME, time);
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_SCENA_NOTE, scena);
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_REAL_NOTE, real);
        cv.put(FilmlistContract.FilmlistEntry.COLUMN_FILM_MUSIQUE_NOTE, musique);
        return mDb.insert(FilmlistContract.FilmlistEntry.TABLE_NAME, null, cv);
    }

    private Cursor getAllFilms() {
        // COMPLETED (6) Inside, call query on mDb passing in the table name and projection String [] order by COLUMN_TIMESTAMP
        return mDb.query(
                FilmlistContract.FilmlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                FilmlistContract.FilmlistEntry.COLUMN_TIMESTAMP
        );
    }

}
