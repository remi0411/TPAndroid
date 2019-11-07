package com.example.cinelardier;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.cinelardier.data.FilmlistContract;
import com.example.cinelardier.data.FilmlistDbHelper;
import com.example.cinelardier.data.TestUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private FilmListAdapter mAdapter;
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddFilmActivity.class));
            }
        });

        RecyclerView filmlistRecyclerView;
        filmlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_films_list_view);
        filmlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FilmlistDbHelper dbHelper = new FilmlistDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        //TestUtil.insertFakeData(mDb);
        Cursor cursor = getAllFilms();
        mAdapter = new FilmListAdapter(this, cursor);

        filmlistRecyclerView.setAdapter(mAdapter);
    }
    @Override
    public void onResume(){
        super.onResume();

        RecyclerView filmlistRecyclerView;
        filmlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_films_list_view);
        filmlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = getAllFilms();
        mAdapter = new FilmListAdapter(this, cursor);
        filmlistRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_create) {
            startActivity(new Intent(MainActivity.this, AddFilmActivity.class));
        }

        return super.onOptionsItemSelected(item);
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
