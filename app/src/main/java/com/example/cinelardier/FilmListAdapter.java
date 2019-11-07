package com.example.cinelardier;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cinelardier.data.FilmlistContract;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.FilmViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    /**
     * Constructor using the context and the db cursor
     *
     * @param context the calling context/activity
     */
    public FilmListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.film_list_item, parent, false);
        return new FilmViewHolder(view);
    }

    public void swapCursor(Cursor newCursor) {
        // COMPLETED (16) Inside, check if the current cursor is not null, and close it if so
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        // COMPLETED (17) Update the local mCursor to be equal to  newCursor
        mCursor = newCursor;
        // COMPLETED (18) Check if the newCursor is not null, and call this.notifyDataSetChanged() if so
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(FilmViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        //mCursor.get
        //String name = "aze";
        String name = mCursor.getString(mCursor.getColumnIndex(FilmlistContract.FilmlistEntry.COLUMN_FILM_TITLE));
        Integer noteScena = mCursor.getInt(mCursor.getColumnIndex(FilmlistContract.FilmlistEntry.COLUMN_FILM_SCENA_NOTE));

        holder.titleTextView.setText(name);
        holder.noteScenaTextView.setText(String.valueOf(noteScena));
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class FilmViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView noteScenaTextView;

        public FilmViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            noteScenaTextView = (TextView) itemView.findViewById(R.id.note_scena_text_view);
        }

    }
}
