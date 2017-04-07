package com.platformhouse.movieszone.movieszone.data.review;

import android.content.ContentValues;
import android.support.annotation.Nullable;

/**
 * Created by Shehab Salah on 8/18/16.
 * This Class take the (Review) data and convert it to ContentValues
 */
public class ReviewContentValues {
    public ContentValues contentValues_NewReview(@Nullable int movie_id, @Nullable int moviedb_id,
                                                @Nullable String author, @Nullable String content,
                                                @Nullable String url) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ReviewColumns.COL_MOVIE_ID, movie_id);
        contentValues.put(ReviewColumns.COL_MOVIEDB_ID, moviedb_id);
        contentValues.put(ReviewColumns.COL_AUTHOR, author);
        contentValues.put(ReviewColumns.COL_CONTENT, content);
        contentValues.put(ReviewColumns.COL_URL, url);
        return contentValues;
    }
}
