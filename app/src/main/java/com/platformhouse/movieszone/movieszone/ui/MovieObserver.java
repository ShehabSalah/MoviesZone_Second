package com.platformhouse.movieszone.movieszone.ui;

import android.database.ContentObserver;
import android.net.Uri;

/**
 * Created by Shehab Salah on 8/23/16.
 *
 */
public abstract class MovieObserver extends ContentObserver {
    public MovieObserver(android.os.Handler handler) {
        super(handler);
    }

    @Override
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
    }

    @Override
    public abstract void onChange(boolean selfChange, Uri uri);
}