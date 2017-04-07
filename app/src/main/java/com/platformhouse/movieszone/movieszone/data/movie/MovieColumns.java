package com.platformhouse.movieszone.movieszone.data.movie;

import android.net.Uri;
import android.provider.BaseColumns;

import com.platformhouse.movieszone.movieszone.util.Constants;

/**
 * Created by Shehab Salah on 8/13/16.
 * This Class contain the ContentProvider URI's and Database Column names of the Movies
 */
public class MovieColumns implements BaseColumns {
    public static final String TABLE_NAME = "movie";
    //General Content URI
    public static final Uri CONTENT_URI = Constants.CONTENT_URI_BASE.buildUpon().appendPath(TABLE_NAME).build();
    //Content URI of the popular movies
    public static final Uri POPULAR_CONTENT_URI = CONTENT_URI.buildUpon().appendPath(Constants.popular_name).build();
    //Content URI of the top rated movies
    public static final Uri TOP_RATED_CONTENT_URI = CONTENT_URI.buildUpon().appendPath(Constants.top_rated_name).build();
    //Content URI of the favorite movies
    public static final Uri FAVORITE_CONTENT_URI = CONTENT_URI.buildUpon().appendPath(Constants.favorite_name).build();

    //Table Columns
    public static final String COL_MOVIEDB_ID       = "moviedb_id";
    public static final String COL_TITLE            = "title";
    public static final String COL_BACKDROP         = "backdrop_path";
    public static final String COL_ORIGINAL_LAN     = "original_lan";
    public static final String COL_ORIGINAL_TITLE   = "original_title";
    public static final String COL_OVERVIEW         = "overview";
    public static final String COL_RELEASE_DATE     = "release_date";
    public static final String COL_POSTER_PATH      = "poster_path";
    public static final String COL_POPULARITY       = "popularity";
    public static final String COL_VOTE_AVERAGE     = "vote_average";
    public static final String COL_VOTE_COUNT       = "vote_count";
    public static final String COL_TYPE             = "movie_type";     //top rated or most popular
    public static final String COL_FAVORITE         = "favorite";
}
