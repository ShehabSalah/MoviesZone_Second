package com.platformhouse.movieszone.movieszone.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.platformhouse.movieszone.movieszone.R;
import com.platformhouse.movieszone.movieszone.data.movie.MovieColumnHolder;
import com.platformhouse.movieszone.movieszone.sync.MoviesSyncAdapter;
import com.platformhouse.movieszone.movieszone.ui.fragments.DetailsFragment;
import com.platformhouse.movieszone.movieszone.ui.fragments.MoviesFragment;
import com.platformhouse.movieszone.movieszone.util.Constants;

/**
 * Created by Shehab Salah on 8/19/16.
 *
 */
public class MoviesActivity extends AppCompatActivity implements MoviesFragment.Callback{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        if(findViewById(R.id.details_container) != null){
            Constants.mTwoPane = true;
            if (savedInstanceState == null) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.details_container, new DetailsFragment(), Constants.DETAILFRAGMENT_TAG)
                        .commit();
            }
        }else{
            Constants.mTwoPane = false;
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new MoviesFragment(),Constants.MAINFRAGMENT_TAG)
                    .commit();
        }
        MoviesSyncAdapter.initializeSyncAdapter(getApplicationContext());

    }

    @Override
    public void onItemSelected(MovieColumnHolder movie) {
        if (Constants.mTwoPane){
            Bundle args = new Bundle();
            args.putParcelable(getString(R.string.movie_list),movie);
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(args);
            getFragmentManager().beginTransaction()
                    .replace(R.id.details_container, detailsFragment, Constants.DETAILFRAGMENT_TAG)
                    .commit();
        }else{
            startActivity(DetailsActivity.getDetailsIntent(this, movie));
        }
    }
}