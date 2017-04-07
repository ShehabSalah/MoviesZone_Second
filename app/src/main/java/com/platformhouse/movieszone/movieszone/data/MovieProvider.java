package com.platformhouse.movieszone.movieszone.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.platformhouse.movieszone.movieszone.data.movie.MovieColumns;
import com.platformhouse.movieszone.movieszone.data.review.ReviewColumns;
import com.platformhouse.movieszone.movieszone.data.trailer.TrailerColumns;
import com.platformhouse.movieszone.movieszone.util.Constants;

/**
 * Created by Shehab Salah on 8/13/16.
 * This class is responsible on separating the underline Data Storage from the Application.
 * This Class also responsible on making the underline Data Storage of the Application shared
 * among all other applications
 */
public class MovieProvider extends ContentProvider {
    private SQLiteDatabase db;
    //Favorite Movies Uri Number
    private static final int TYPE_FAVORITE = 0;
    //General Movie Uri Number
    private static final int TYPE_MOVIE = 1;
    //Popular Movies Uri Number
    private static final int TYPE_MOVIE_POPULAR = 2;
    //Top Rated Movies Uri Number
    private static final int TYPE_MOVIE_TOP_RATED = 3;
    //Reviews Uri Number
    private static final int TYPE_REVIEW = 4;
    //Trailers Uri Number
    private static final int TYPE_TRAILER = 5;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    //Match each previous number with the corresponding URI
    static {
        URI_MATCHER.addURI(Constants.AUTHORITY, MovieColumns.TABLE_NAME, TYPE_MOVIE);
        URI_MATCHER.addURI(Constants.AUTHORITY, MovieColumns.TABLE_NAME + "/" + Constants.popular_name, TYPE_MOVIE_POPULAR);
        URI_MATCHER.addURI(Constants.AUTHORITY, MovieColumns.TABLE_NAME + "/" + Constants.top_rated_name, TYPE_MOVIE_TOP_RATED);
        URI_MATCHER.addURI(Constants.AUTHORITY, MovieColumns.TABLE_NAME + "/" + Constants.favorite_name, TYPE_FAVORITE);
        URI_MATCHER.addURI(Constants.AUTHORITY, ReviewColumns.TABLE_NAME, TYPE_REVIEW);
        URI_MATCHER.addURI(Constants.AUTHORITY, TrailerColumns.TABLE_NAME, TYPE_TRAILER);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        MovieDBHelper dbHelper = new MovieDBHelper(context);
        db = dbHelper.getWritableDatabase();
        return db != null;
    }


    @Override
    public Cursor query(@Nullable Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //Switch the coming Uri with its corresponding number
        int match = URI_MATCHER.match(uri);
        //Return the cursor result based on the uri number
        switch (match) {
            case TYPE_MOVIE:
                return db.query(
                        MovieColumns.TABLE_NAME,
                        null,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );
            case TYPE_FAVORITE:
                return db.query(MovieColumns.TABLE_NAME,
                        null,
                        MovieColumns.COL_FAVORITE + " = ?",
                        new String[]{Integer.toString(Constants.favorite)},
                        null,
                        null,
                        null);
            case TYPE_MOVIE_POPULAR:
                return db.query(MovieColumns.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        MovieColumns.COL_POPULARITY + " DESC",
                        "20");
            case TYPE_MOVIE_TOP_RATED:
                return db.query(MovieColumns.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        MovieColumns.COL_VOTE_AVERAGE + " DESC",
                        "20");
            case TYPE_REVIEW:
                return db.query(ReviewColumns.TABLE_NAME,
                        null,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
            case TYPE_TRAILER:
                return db.query(TrailerColumns.TABLE_NAME,
                        null,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public String getType(@Nullable Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case TYPE_MOVIE:
            case TYPE_FAVORITE:
            case TYPE_MOVIE_POPULAR:
            case TYPE_MOVIE_TOP_RATED:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + MovieColumns.TABLE_NAME;
            case TYPE_REVIEW:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + ReviewColumns.TABLE_NAME;
            case TYPE_TRAILER:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + TrailerColumns.TABLE_NAME;
        }
        return null;
    }

    @Override
    public Uri insert(@Nullable Uri uri, ContentValues values) {
        //Switch the coming Uri with its corresponding number
        int match = URI_MATCHER.match(uri);
        //Database row id
        long rowID = 0;
        //This Uri is changed based on the inserted Database Table
        Uri _uri = null;
        switch (match) {
            case TYPE_MOVIE:
                rowID = db.insert(MovieColumns.TABLE_NAME, "", values);
                _uri = MovieColumns.CONTENT_URI;
                break;
            case TYPE_REVIEW:
                rowID = db.insert(ReviewColumns.TABLE_NAME, "", values);
                _uri = ReviewColumns.CONTENT_URI;
                break;
            case TYPE_TRAILER:
                rowID = db.insert(TrailerColumns.TABLE_NAME, "", values);
                _uri = TrailerColumns.CONTENT_URI;
                break;
        }
        if (rowID > 0) {
            Uri _uri2 = ContentUris.withAppendedId(_uri, rowID);
            getContext().getContentResolver().notifyChange(_uri2, null);
            return _uri;
        } else {
            return null;
        }
    }

    @Override
    public int delete(@Nullable Uri uri, String selection, String[] selectionArgs) {
        int match = URI_MATCHER.match(uri);
        int count = 0;
        switch (match) {
            case TYPE_MOVIE:
                count = db.delete(MovieColumns.TABLE_NAME, selection, selectionArgs);
                break;
            case TYPE_REVIEW:
                count = db.delete(ReviewColumns.TABLE_NAME, selection, selectionArgs);
                break;
            case TYPE_TRAILER:
                count = db.delete(TrailerColumns.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        db.close();
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@Nullable Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int match = URI_MATCHER.match(uri);
        int count = 0;
        switch (match) {
            case TYPE_MOVIE:
                count = db.update(MovieColumns.TABLE_NAME, values, selection, selectionArgs);
                break;
            case TYPE_REVIEW:
                count = db.update(ReviewColumns.TABLE_NAME, values, selection, selectionArgs);
                break;
            case TYPE_TRAILER:
                count = db.update(TrailerColumns.TABLE_NAME, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
