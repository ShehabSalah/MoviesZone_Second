package com.platformhouse.movieszone.movieszone.sync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.platformhouse.movieszone.movieszone.data.movie.MovieColumnHolder;
import com.platformhouse.movieszone.movieszone.data.movie.MovieColumns;
import com.platformhouse.movieszone.movieszone.data.movie.MovieContentValues;
import com.platformhouse.movieszone.movieszone.data.review.ReviewColumnHolder;
import com.platformhouse.movieszone.movieszone.data.review.ReviewColumns;
import com.platformhouse.movieszone.movieszone.data.review.ReviewContentValues;
import com.platformhouse.movieszone.movieszone.data.trailer.TrailerColumnHolder;
import com.platformhouse.movieszone.movieszone.data.trailer.TrailerColumns;
import com.platformhouse.movieszone.movieszone.data.trailer.TrailerContentValues;
import com.platformhouse.movieszone.movieszone.util.Constants;

import java.util.ArrayList;

/**
 * Created by Shehab Salah on 8/20/16.
 * This Class responsible on insert the parsing data to the Database using the ContentProvider
 */
public class DatabaseSyncData {
    public void sync(ArrayList<Object> arrayList, int type, String type_name, @NonNull Context context, String db_id, String _id) {
        switch (type) {
            case Constants.movieColumn:
                for (int i = 0; i < arrayList.size(); i++) {
                    MovieContentValues movieContentValues = new MovieContentValues();
                    MovieColumnHolder movie = (MovieColumnHolder) arrayList.get(i);
                    ContentValues values = movieContentValues.contentValues_NewMovie(movie.getId(),
                            movie.getTitle(), movie.getBackdrop_path(), movie.getOriginal_language(),
                            movie.getOriginal_title(), movie.getOverview(), movie.getRelease_date(),
                            movie.getPoster_path(), movie.getPopularity(), movie.getVote_average(), movie.getVote_count(),
                            type_name);
                    Cursor cursor = context.getContentResolver().query(MovieColumns.CONTENT_URI,
                            null,
                            MovieColumns.COL_MOVIEDB_ID + " = ? ",
                            new String[]{Integer.toString(movie.getId())},
                            null);
                    if(cursor != null && cursor.getCount() > 0){
                        int x = context.getContentResolver().update(MovieColumns.CONTENT_URI,
                                values,
                                MovieColumns.COL_MOVIEDB_ID + " = ?",
                                new String[]{Integer.toString(movie.getId())});
                        cursor.close();
                    }
                    else {
                        context.getContentResolver().insert(MovieColumns.CONTENT_URI, values);
                    }
                }
                break;
            case Constants.trailerColumn:
                for (int i = 0; i < arrayList.size(); i++) {
                    TrailerContentValues trailerContentValues = new TrailerContentValues();
                    TrailerColumnHolder trailer = (TrailerColumnHolder) arrayList.get(i);
                    ContentValues values = trailerContentValues.contentValues_NewTrailer(Integer.parseInt(_id),
                            Integer.parseInt(db_id),
                            trailer.getName(),
                            trailer.getSize(),
                            trailer.getKey(),
                            trailer.getType());
                    if(trailer.getName() != null){
                        context.getContentResolver().insert(TrailerColumns.CONTENT_URI, values);
                    }
                }
                break;
            case Constants.reviewColumn:
                for (int i = 0; i < arrayList.size(); i++) {
                    ReviewContentValues reviewContentValues = new ReviewContentValues();
                    ReviewColumnHolder review = (ReviewColumnHolder) arrayList.get(i);
                    ContentValues values = reviewContentValues.contentValues_NewReview(Integer.parseInt(_id),
                            Integer.parseInt(db_id),
                            review.getAuthor(),
                            review.getContent(),
                            review.getUrl());
                    context.getContentResolver().insert(ReviewColumns.CONTENT_URI, values);
                }
                break;
        }
    }
    public void updateList(ArrayList<Object> arrayList,String type_name,Context context){
        for(int i = 0; i < arrayList.size(); i++){
            MovieContentValues movieContentValues = new MovieContentValues();
            MovieColumnHolder movie = (MovieColumnHolder) arrayList.get(i);
            ContentValues values = movieContentValues.contentValues_NewMovie(movie.getId(),
                    movie.getTitle(), movie.getBackdrop_path(), movie.getOriginal_language(),
                    movie.getOriginal_title(), movie.getOverview(), movie.getRelease_date(),
                    movie.getPoster_path(), movie.getPopularity(), movie.getVote_average(), movie.getVote_count(),
                    type_name);
            Cursor cursor = context.getContentResolver().query(MovieColumns.CONTENT_URI,
                    null,
                    MovieColumns.COL_MOVIEDB_ID + " = ? ",
                    new String[]{Integer.toString(movie.getId())},
                    null);
            if(cursor != null && cursor.getCount() > 0){
                int x = context.getContentResolver().update(MovieColumns.CONTENT_URI,
                        values,
                        MovieColumns.COL_MOVIEDB_ID + " = ?",
                        new String[]{Integer.toString(movie.getId())});
                cursor.close();
            }
        }
    }
}