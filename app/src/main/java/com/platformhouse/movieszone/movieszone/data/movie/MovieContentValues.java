package com.platformhouse.movieszone.movieszone.data.movie;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * Created by Shehab Salah on 8/14/16.
 * This Class take the (Movie) data and convert it to ContentValues
 */
public class MovieContentValues {
    public ContentValues contentValues_NewMovie(int moviedb_id, @NonNull String title,
                                                @NonNull String backdrop, @NonNull String original_language,
                                                @NonNull String original_title, @NonNull String overview,
                                                @NonNull String release_date, @NonNull String poster_path,
                                                float popularity, float vote_average,
                                                int vote_count, @NonNull String movie_type) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieColumns.COL_MOVIEDB_ID, moviedb_id);
        contentValues.put(MovieColumns.COL_TITLE, title);
        contentValues.put(MovieColumns.COL_BACKDROP, backdrop);
        contentValues.put(MovieColumns.COL_ORIGINAL_LAN, original_language);
        contentValues.put(MovieColumns.COL_ORIGINAL_TITLE, original_title);
        contentValues.put(MovieColumns.COL_OVERVIEW, overview);
        contentValues.put(MovieColumns.COL_RELEASE_DATE, release_date);
        contentValues.put(MovieColumns.COL_POSTER_PATH, poster_path);
        contentValues.put(MovieColumns.COL_POPULARITY, popularity);
        contentValues.put(MovieColumns.COL_VOTE_AVERAGE, vote_average);
        contentValues.put(MovieColumns.COL_VOTE_COUNT, vote_count);
        contentValues.put(MovieColumns.COL_TYPE, movie_type);
        return contentValues;
    }

}
