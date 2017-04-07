package com.platformhouse.movieszone.movieszone.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Shehab Salah on 8/20/16.
 *
 */
public class MoviesAuthenticatorService extends Service{
    // Instance field that stores the authenticator object
    private MoviesAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new MoviesAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
