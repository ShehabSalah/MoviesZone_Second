package com.platformhouse.movieszone.movieszone.data.trailer;

import android.net.Uri;
import android.provider.BaseColumns;

import com.platformhouse.movieszone.movieszone.util.Constants;

/**
 * Created by Shehab Salah on 8/13/16.
 * This Class contain the ContentProvider URI's and Database Column names of the Trailers
 */
public class TrailerColumns implements BaseColumns {
    public static final String TABLE_NAME = "trailer";
    public static final Uri CONTENT_URI = Constants.CONTENT_URI_BASE.buildUpon().appendPath(TABLE_NAME).build();

    //Table Columns
    public static final String COL_MOVIE_ID         = "movie_id";       //FK
    public static final String COL_MOVIEDB_ID       = "moviedb_id";
    public static final String COL_NAME             = "name";
    public static final String COL_SIZE             = "size";
    public static final String COL_KEY              = "key";
    public static final String COL_TYPE             = "type";
}
