package com.platformhouse.movieszone.movieszone.util;

import android.net.Uri;
import android.widget.GridView;

/**
 * Created by Shehab Salah on 8/12/16.
 *
 */
public class Constants {
    //Favorites
    public static final int favorite                = 1;
    public static final int unFavorite              = 0;
    //Movie Types
    public static final int popular_list            = 100;
    public static final int top_rated_list          = 101;
    public static final String popular_name         = "popular";
    public static final String top_rated_name       = "top_rated";
    public static final String favorite_name        = "favorite";
    //Providers
    public static final String AUTHORITY            = "com.platformhouse.movieszone.movieszone";
    public static final Uri CONTENT_URI_BASE        = Uri.parse("content://" + AUTHORITY);
    //Column Types
    public static final int movieColumn             = 0;
    public static final int reviewColumn            = 1;
    public static final int trailerColumn           = 2;


    //API KEY
    public static final String API_KEY              = "API_KEY"; // <== TODO! (Add TheMovieDB API KEY HERE)
    //LINKS
    public static final String MOIVES_LIST_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String IMAGE_URL            = "http://image.tmdb.org/t/p/w342";
    public static final String TRAILERS_URL         = "/videos";
    public static final String REVIEWS_URL          = "/reviews";
    public static final String YOUTUBE_LINK         = "https://www.youtube.com/watch?v=";

    //This Variable used to save last position of the grid view before the screen rotate
    public static int lastPosition                  = GridView.INVALID_POSITION;
    //Order Constant Variables
    public static String Order                      = Constants.popular_name;
    //Key to save the last position in SaveInstanceState
    public static final String POSITION_KEY         = "position";
    //Key to save the last order in SaveInstanceState
    public static final String ORDER_KEY            = "order";

    //Loaders
    public static final int MOVIE_LOADER            = 0;
    public static final int TRAILER_LOADER          = 1;
    public static final int REVIEW_LOADER           = 2;

    //Fragment TAG
    public static final String DETAILFRAGMENT_TAG   = "detail_tag";
    public static final String MAINFRAGMENT_TAG     = "main_tag";
    public static boolean mTwoPane;

}