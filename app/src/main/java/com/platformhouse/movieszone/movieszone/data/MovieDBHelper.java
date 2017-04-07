package com.platformhouse.movieszone.movieszone.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.platformhouse.movieszone.movieszone.data.movie.MovieColumns;
import com.platformhouse.movieszone.movieszone.data.review.ReviewColumns;
import com.platformhouse.movieszone.movieszone.data.trailer.TrailerColumns;

/**
 * Created by Shehab Salah on 8/11/16.
 * This Class is responsible on creating the Database schema and it's Tables
 */
public class MovieDBHelper extends SQLiteOpenHelper {
    //DATABASE_VERSION is containing the version number of the current DB schema.
    //If the Database schema changed, the DATABASE_VERSION must change also.
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "movieszone.db";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create movie Table
        final String CREATE_MOVIE_TABLE             = "CREATE TABLE " + MovieColumns.TABLE_NAME + " ( " +
                MovieColumns._ID                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieColumns.COL_MOVIEDB_ID         + " INTEGER NOT NULL, " +
                MovieColumns.COL_TITLE              + " TEXT NOT NULL, " +
                MovieColumns.COL_BACKDROP           + " TEXT NOT NULL, " +
                MovieColumns.COL_ORIGINAL_LAN       + " TEXT NOT NULL, " +
                MovieColumns.COL_ORIGINAL_TITLE     + " TEXT NOT NULL, " +
                MovieColumns.COL_OVERVIEW           + " TEXT NOT NULL, " +
                MovieColumns.COL_RELEASE_DATE       + " TEXT NOT NULL, " +
                MovieColumns.COL_POSTER_PATH        + " TEXT NOT NULL, " +
                MovieColumns.COL_POPULARITY         + " REAL NOT NULL, " +
                MovieColumns.COL_VOTE_AVERAGE       + " REAL NOT NULL, " +
                MovieColumns.COL_VOTE_COUNT         + " INTEGER NOT NULL, " +
                MovieColumns.COL_TYPE               + " TEXT NOT NULL, " +
                MovieColumns.COL_FAVORITE           + " INTEGER NOT NULL DEFAULT 0, " +
                "UNIQUE ("                          + MovieColumns.COL_MOVIEDB_ID + ") ON CONFLICT REPLACE);";

        //Create Trailer Table
        final String CREATE_TRAILER_TABLE           = "CREATE TABLE " + TrailerColumns.TABLE_NAME + " ( " +
                TrailerColumns._ID                  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TrailerColumns.COL_MOVIE_ID         + " INTEGER NOT NULL, " +
                TrailerColumns.COL_MOVIEDB_ID       + " INTEGER NOT NULL, " +
                TrailerColumns.COL_NAME             + " TEXT NOT NULL, " +
                TrailerColumns.COL_SIZE             + " TEXT NOT NULL, " +
                TrailerColumns.COL_KEY              + " TEXT NOT NULL, " +
                TrailerColumns.COL_TYPE             + " TEXT NOT NULL, " +
                " FOREIGN KEY ( "                   + TrailerColumns.COL_MOVIE_ID + " ) REFERENCES " +
                MovieColumns.TABLE_NAME             + "( " + MovieColumns._ID + " ), " +
                "UNIQUE ("                          + TrailerColumns.COL_KEY + ") ON CONFLICT REPLACE); ";

        //Create Review Table
        final String CREATE_REVIEW_TABLE            = " CREATE TABLE " + ReviewColumns.TABLE_NAME + " ( " +
                ReviewColumns._ID                   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ReviewColumns.COL_MOVIE_ID          + " INTEGER NOT NULL, " +
                ReviewColumns.COL_MOVIEDB_ID        + " INTEGER NOT NULL, " +
                ReviewColumns.COL_AUTHOR            + " TEXT NOT NULL, " +
                ReviewColumns.COL_CONTENT           + " TEXT NOT NULL, " +
                ReviewColumns.COL_URL               + " TEXT NOT NULL, " +
                "FOREIGN KEY ( "                    + ReviewColumns.COL_MOVIE_ID + " ) REFERENCES " +
                MovieColumns.TABLE_NAME             + " ( " + MovieColumns._ID + " ), " +
                "UNIQUE ("                          + ReviewColumns.COL_URL + ") ON CONFLICT REPLACE);";

        //Execute the SQL of creating table movie
        db.execSQL(CREATE_MOVIE_TABLE);
        //Execute the SQL of creating table trailer
        db.execSQL(CREATE_TRAILER_TABLE);
        //Execute the SQL of creating table review
        db.execSQL(CREATE_REVIEW_TABLE);
    }

    //This method is Called only if the
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop database tables DATABASE_VERSION number is changed
        db.execSQL("DROP TABLE IF EXISTS " + MovieColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TrailerColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ReviewColumns.TABLE_NAME);
        //Create New Database
        onCreate(db);

    }
}
