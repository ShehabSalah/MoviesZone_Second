package com.platformhouse.movieszone.movieszone.data.review;

import android.net.Uri;
import android.provider.BaseColumns;

import com.platformhouse.movieszone.movieszone.util.Constants;

/**
 * Created by Shehab Salah on 8/13/16.
 * This Class contain the ContentProvider URI's and Database Column names of the Reviews
 */
public class ReviewColumns implements BaseColumns {
    public static final String TABLE_NAME = "review";
    public static final Uri CONTENT_URI = Constants.CONTENT_URI_BASE.buildUpon().appendPath(TABLE_NAME).build();

    //Table Columns
    public static final String COL_MOVIE_ID     = "movie_id";       //FK
    public static final String COL_MOVIEDB_ID   = "moviedb_id";
    public static final String COL_AUTHOR       = "author";
    public static final String COL_CONTENT      = "content";
    public static final String COL_URL          = "url";
}
