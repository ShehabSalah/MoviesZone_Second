package com.platformhouse.movieszone.movieszone.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.platformhouse.movieszone.movieszone.R;
import com.platformhouse.movieszone.movieszone.data.movie.MovieColumnHolder;
import com.platformhouse.movieszone.movieszone.ui.fragments.DetailsFragment;

/*
 * Created by Shehab Salah on 8/19/16.
 */
public class DetailsActivity extends AppCompatActivity {

    public static Intent getDetailsIntent(Context context, MovieColumnHolder entity) {
        return new Intent(context, DetailsActivity.class).putExtra(context.getString(R.string.movie_list), entity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle(getString(R.string.details_title));
        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putParcelable(getString(R.string.movie_list), getIntent().getParcelableExtra(getString(R.string.movie_list)));
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(args);
            getFragmentManager().beginTransaction()
                    .replace(R.id.details_container, detailsFragment).commit();
        }

    }
}
