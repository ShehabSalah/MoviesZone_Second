package com.platformhouse.movieszone.movieszone.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/*
 * Created by Shehab Salah on 8/20/16.
 */
public class MoviesSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static MoviesSyncAdapter mMoviesSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (mMoviesSyncAdapter == null) {
                mMoviesSyncAdapter = new MoviesSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMoviesSyncAdapter.getSyncAdapterBinder();
    }
}
