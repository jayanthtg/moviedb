package com.floooh.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "app_db";

    private static final String TABLE_MOVIE = "liked";

    private static final String KEY_TITLE = "title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_DATE = "date";
    private static final String KEY_RATING = "rating";
    private static final String KEY_POSTER = "poster";
    private static final String KEY_GENER = "gener";
    private static final String KEY_RUNTIME = "time";


    public DatabaseHandler(final Context context) {
        super(context, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        final String CREATE_CONTACTS_TABLE = "CREATE TABLE  " + DatabaseHandler.TABLE_MOVIE + "("
                + DatabaseHandler.KEY_TITLE + " TEXT,"
                + DatabaseHandler.KEY_OVERVIEW + " TEXT,"
                + DatabaseHandler.KEY_DATE + " TEXT,"
                + DatabaseHandler.KEY_RATING + " TEXT,"
                + DatabaseHandler.KEY_POSTER + " TEXT,"
                + DatabaseHandler.KEY_GENER + " TEXT,"
                + DatabaseHandler.KEY_RUNTIME + " TEXT" +
                ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseHandler.TABLE_MOVIE);
        this.onCreate(db);
    }

    public boolean addMovie(Movie data) {
        if (getMovie(data.getmTitle()) == null) {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHandler.KEY_TITLE, data.getmTitle());
            cv.put(DatabaseHandler.KEY_OVERVIEW, data.getmOverview());
            cv.put(DatabaseHandler.KEY_DATE, data.getmDate());
            cv.put(DatabaseHandler.KEY_RATING, data.getmRating());
            cv.put(DatabaseHandler.KEY_POSTER, data.getmPoster());
            cv.put(DatabaseHandler.KEY_GENER, data.getmGener());
            cv.put(DatabaseHandler.KEY_RUNTIME, data.getmRuntime());
            return this.getWritableDatabase().insert(DatabaseHandler.TABLE_MOVIE, null, cv) >= 0;
        }
        return false;
    }

    public Movie getMovie(String t) {
        Cursor c = this.getWritableDatabase().rawQuery("select * from " + DatabaseHandler.TABLE_MOVIE + " Where " + DatabaseHandler.KEY_TITLE + " = ?", new String[]{t});
        if (c != null && c.moveToFirst()) {
            String title = c.getString(c.getColumnIndex(KEY_TITLE));
            String over = c.getString(c.getColumnIndex(KEY_OVERVIEW));
            String date = c.getString(c.getColumnIndex(KEY_DATE));
            String rating = c.getString(c.getColumnIndex(KEY_RATING));
            String pos = c.getString(c.getColumnIndex(KEY_POSTER));
            String gen = c.getString(c.getColumnIndex(KEY_GENER));
            String time = c.getString(c.getColumnIndex(KEY_RUNTIME));
            c.close();
            return new Movie(title, date, over, gen, time, pos, rating);
        }
        return null;
    }

    public ArrayList<Movie> getMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            Cursor c = this.getWritableDatabase().rawQuery("select * from " + DatabaseHandler.TABLE_MOVIE, new String[]{});
            if (c != null && c.moveToFirst()) {
                do {
                    String title = c.getString(c.getColumnIndex(KEY_TITLE));
                    String over = c.getString(c.getColumnIndex(KEY_OVERVIEW));
                    String date = c.getString(c.getColumnIndex(KEY_DATE));
                    String rating = c.getString(c.getColumnIndex(KEY_RATING));
                    String pos = c.getString(c.getColumnIndex(KEY_POSTER));
                    String gen = c.getString(c.getColumnIndex(KEY_GENER));
                    String time = c.getString(c.getColumnIndex(KEY_RUNTIME));
                    Movie m = new Movie(title, date, over, gen, time, pos, rating);
                    m.setLiked(true);
                    movies.add(m);
                } while (c.moveToNext());
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    public boolean deleteMovie(String title) {
        try {
//            this.getWritableDatabase().execSQL("delete from " + DatabaseHandler.TABLE_MOVIE + " where " + DatabaseHandler.KEY_ID + " = " + "\"" +KEY_ID url + "\"");
            return this.getWritableDatabase().delete(DatabaseHandler.TABLE_MOVIE, DatabaseHandler.KEY_TITLE + "= ?", new String[]{title}) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
