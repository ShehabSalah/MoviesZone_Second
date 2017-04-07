package com.platformhouse.movieszone.movieszone.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.platformhouse.movieszone.movieszone.R;
import com.platformhouse.movieszone.movieszone.data.movie.MovieColumns;
import com.platformhouse.movieszone.movieszone.parser.Parser;
import com.platformhouse.movieszone.movieszone.ui.activities.MoviesActivity;
import com.platformhouse.movieszone.movieszone.util.Constants;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/*
 * Created by Shehab Salah on 8/20/16.
 */
public class MoviesSyncAdapter extends AbstractThreadedSyncAdapter {
    private int requestType = Constants.movieColumn;
    public static final int SYNC_INTERVAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;
    private static final int MOVIE_NOTIFICATION_ID = 3004;
    private String db_id = null;
    private String _id = null;
    ArrayList<int[]> idArray;
    public MoviesSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        //Fetch Popular movies from the server *First Time*
        fetchData(Constants.popular_name);
        //Send notification that the data has been changed
        notifyMovie();
        //Notify the content resolver that the data changed
        getContext().getContentResolver().notifyChange(MovieColumns.CONTENT_URI, null, false);
    }
    public void fetchData(String url){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String listJsonStr = null;
        try{
            //API Name
            final String API_KEY_PARAM = "api_key";
            Uri buildUri = Uri.parse(Constants.MOIVES_LIST_BASE_URL+url+"?").buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, Constants.API_KEY)
                    .build();
            //Convert the uri to url
            URL _url = new URL(buildUri.toString());
            // Create the request to TheMovieDB, and open the connection
            urlConnection = (HttpURLConnection) _url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream != null) {
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    //append each line from input stream into String Buffer
                    buffer.append(line + "\n");
                }
                //if buffer contain data
                if (buffer.length() != 0) {
                    //Convert the StringBuffer to String
                    listJsonStr = buffer.toString();
                    //parse the String and get list of object
                    ArrayList<Object> list = new Parser().JsonParser(listJsonStr,requestType);
                    /*
                    * Insert the ArrayList into the Database.
                    * NOTE:
                    * First Time:
                    *       requestType = 0 (Insert Movie) **Popular Movies**
                    *       db_id = null
                    *       _id = null
                    * Second Time:
                    *       requestType = 0 (Insert Movie) **Top Rated Movies**
                    *       db_id = null
                    *       _id = null
                    * Third Time:
                    *       requestType = 1 (insert Reviews) **Of Popular Movies**
                    *       db_id = movie id from the server API
                    *       _id = inserted movie id from the local database
                    * Forth Time:
                    *       requestType = 2 (insert Trailers) **Of Popular Movies**
                    *       db_id = movie id from the server API
                    *       _id = inserted movie id from the local database
                    * Fifth Time:
                    *       requestType = 1 (insert Reviews) **Of Top Rated Movies**
                    *       db_id = movie id from the server API
                    *       _id = inserted movie id from the local database
                    * Sixth Time:
                    *       requestType = 2 (insert Trailers) **Of Top Rated Movies**
                    *       db_id = movie id from the server API
                    *       _id = inserted movie id from the local database
                    **/
                    new DatabaseSyncData().sync(list,requestType,url,getContext(),db_id,_id);
                    if(requestType == Constants.movieColumn && url.equals(Constants.popular_name)) {
                        // *Second Time*
                        fetchData(Constants.top_rated_name);
                        //Fetch Reviews of the Popular Movies List
                        insertReviews(Constants.popular_list);
                        //Fetch Trailers of the Popular Movies List
                        insertTrailers();
                        //Fetch Reviews of the Top Rated List
                        insertReviews(Constants.top_rated_list);
                        //Fetch Trailers of the Top Rated List
                        insertTrailers();
                        updateMovies(Constants.popular_name,API_KEY_PARAM);
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    private void updateMovies(String url, String API_KEY_PARAM){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String listJsonStr = null;
        Uri buildUri = Uri.parse(Constants.MOIVES_LIST_BASE_URL+url+"?").buildUpon()
                .appendQueryParameter(API_KEY_PARAM, Constants.API_KEY)
                .appendQueryParameter("page","2")
                .build();
        try{
            //Convert the uri to url
            URL _url = new URL(buildUri.toString());
            // Create the request to TheMovieDB, and open the connection
            urlConnection = (HttpURLConnection) _url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream != null) {
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    //append each line from input stream into String Buffer
                    buffer.append(line + "\n");
                }
            }
            if(buffer.length() != 0){
                listJsonStr = buffer.toString();
                //parse the String and get list of object
                ArrayList<Object> list = new Parser().JsonParser(listJsonStr,Constants.movieColumn);
                new DatabaseSyncData().updateList(list,url,getContext());
                if(!url.equals(Constants.top_rated_name))
                    updateMovies(Constants.top_rated_name,API_KEY_PARAM);
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void insertReviews(int type){
        //Change the requestType to 1 (Insert Review)
        requestType = Constants.reviewColumn;
        //Array list that contain the db_id and _id of the movies to use them to fetch the Reviews
        //from the Server
        idArray = new ArrayList<>();
        Cursor res = null;
        // Based on the list type sent, query the movie list
        switch (type){
            case Constants.popular_list:
                res = getContext().getContentResolver().query(MovieColumns.POPULAR_CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
                break;
            case Constants.top_rated_list:
                res = getContext().getContentResolver().query(MovieColumns.TOP_RATED_CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
                break;
        }
        if(res != null && res.moveToFirst()) {
            // add the movie server id and the locat DB id to idArray
            do {
                int[] temp = {res.getInt(res.getColumnIndex(MovieColumns.COL_MOVIEDB_ID)),
                        res.getInt(res.getColumnIndex(MovieColumns._ID))} ;
                idArray.add(temp);
            } while (res.moveToNext());
            res.close();
            for(int i = 0; i < idArray.size(); i++){
                db_id = Integer.toString(idArray.get(i)[0]);
                _id = Integer.toString(idArray.get(i)[1]);
                //send the URL of the Review
                // *Third Time*
                // *Fifth Time*
                fetchData(db_id + Constants.REVIEWS_URL);
            }
        }
    }

    private void insertTrailers(){
        //Change the requestType to 2 (Insert Trailers)
        requestType = Constants.trailerColumn;
        for(int i = 0; i < idArray.size(); i++){
            db_id = Integer.toString(idArray.get(i)[0]);
            _id = Integer.toString(idArray.get(i)[1]);
            //send the URL of the Trailer
            // *Forth Time*
            // *Sixth Time*
            fetchData(db_id + Constants.TRAILERS_URL);
        }

    }

    private void notifyMovie() {
        Context context = getContext();
        int iconId = R.mipmap.ic_launcher;
        Resources resources = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(resources,
                R.mipmap.ic_launcher);
        String title = context.getString(R.string.app_name);

        // Define the text of the forecast.
        String contentText = context.getString(R.string.notification);
        String bigText     = context.getString(R.string.notification_big_text);

        // NotificationCompatBuilder is a very convenient way to build backward-compatible
        // notifications.  Just throw in some data.
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getContext())
                        .setSmallIcon(iconId)
                        .setLargeIcon(largeIcon)
                        .setContentTitle(title)
                        .setContentText(contentText)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(bigText));

        // Make something interesting happen when the user clicks on the notification.
        // In this case, opening the app is sufficient.
        Intent resultIntent = new Intent(context, MoviesActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        // WEATHER_NOTIFICATION_ID allows you to update the notification later on.
        mNotificationManager.notify(MOVIE_NOTIFICATION_ID, mBuilder.build());
    }

    /**
     * Helper method to have the sync adapter sync immediately
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
            onAccountCreated(newAccount, context);

        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        MoviesSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}