package com.platformhouse.movieszone.movieszone.data.trailer;

import android.content.ContentValues;
import android.support.annotation.Nullable;

/**
 * Created by Shehab Salah on 8/18/16.
 * This Class take the (Trailer) data and convert it to ContentValues
 */
public class TrailerContentValues {
    public ContentValues contentValues_NewTrailer(@Nullable int movie_id, @Nullable int moviedb_id,
                                                 @Nullable String name, @Nullable String size,
                                                 @Nullable String KEY, @Nullable String type) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TrailerColumns.COL_MOVIE_ID, movie_id);
        contentValues.put(TrailerColumns.COL_MOVIEDB_ID, moviedb_id);
        contentValues.put(TrailerColumns.COL_NAME, name);
        contentValues.put(TrailerColumns.COL_SIZE, size);
        contentValues.put(TrailerColumns.COL_KEY, KEY);
        contentValues.put(TrailerColumns.COL_TYPE, type);
        return contentValues;
    }

}
