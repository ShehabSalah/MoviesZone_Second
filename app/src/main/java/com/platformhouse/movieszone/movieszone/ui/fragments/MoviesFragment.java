package com.platformhouse.movieszone.movieszone.ui.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.platformhouse.movieszone.movieszone.R;
import com.platformhouse.movieszone.movieszone.data.movie.MovieColumnHolder;
import com.platformhouse.movieszone.movieszone.data.movie.MovieColumns;
import com.platformhouse.movieszone.movieszone.ui.MovieObserver;
import com.platformhouse.movieszone.movieszone.ui.adapters.MoviesListAdapter;
import com.platformhouse.movieszone.movieszone.util.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shehab Salah on 8/19/16.
 *
 */
public class MoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    //global variables

    @BindView(R.id.movies_list_items)
    GridView moviesListGridView;
    ArrayList<Object> moviesList = null;
    MoviesListAdapter moviesListCustomAdapter;
    MovieObserver mObserver;
    //Send data to details fragment after Loader finish loading the data
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle args = new Bundle();
            if (Constants.lastPosition > -1 && Constants.lastPosition <= moviesList.size() - 1) {
                args.putParcelable(getString(R.string.movie_list), (MovieColumnHolder) moviesList.get(Constants.lastPosition));
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(args);
                getFragmentManager().beginTransaction()
                        .replace(R.id.details_container, detailsFragment).commit();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getLoaderManager().initLoader(Constants.MOVIE_LOADER, null, this);
        //Looper.prepare();
        mObserver = new MovieObserver(new Handler(Looper.getMainLooper())) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                if (Constants.mTwoPane)
                    getLoaderManager().restartLoader(Constants.MOVIE_LOADER, null, MoviesFragment.this);
            }
        };
        getActivity().getContentResolver().registerContentObserver(MovieColumns.CONTENT_URI, true, mObserver);
    }

    @Override
    public void onPause() {
        getActivity().getContentResolver().unregisterContentObserver(mObserver);
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.order_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Constants.lastPosition = 0;
        if (id == R.id.popular) {
            Constants.Order = Constants.popular_name;
        }
        if (id == R.id.top_rated) {
            Constants.Order = Constants.top_rated_name;
        }
        if (id == R.id.favorite) {
            Constants.Order = Constants.favorite_name;
        }
        getLoaderManager().restartLoader(Constants.MOVIE_LOADER, null, this);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Save the user current state
        super.onSaveInstanceState(outState);
        //get the last GridView position
        if (!Constants.mTwoPane)
            Constants.lastPosition = moviesListGridView.getFirstVisiblePosition();
        //Save the last GridView position with the position key
        outState.putInt(Constants.POSITION_KEY, Constants.lastPosition);
        outState.putString(Constants.ORDER_KEY, Constants.Order);
    }

    //Create view: used to inflate movies_list.xml in the movies activity which contain the movies items list
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the movies_list view into movies Activity
        View mainView = inflater.inflate(R.layout.movies_list, container, false);
        //Connect the grideView variable with the GridView in the XML
        ButterKnife.bind(this, mainView);

        //Making the gridView clickable
        moviesListGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (moviesList != null) {
                    ((Callback) getActivity()).onItemSelected((MovieColumnHolder) moviesList.get(position));
                    Constants.lastPosition = position;
                }
            }
        });
        //Check whether we're recreating a previously destroyed application instance
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.POSITION_KEY)) {
            //If (YES) restore value of members from saved state
            Constants.lastPosition = savedInstanceState.getInt(Constants.POSITION_KEY);
            if (savedInstanceState.containsKey(Constants.ORDER_KEY)) {
                Constants.Order = savedInstanceState.getString(Constants.ORDER_KEY);
                switch (Constants.Order) {
                    case Constants.popular_name:
                        getActivity().setTitle(getResources().getString(R.string.popular_title));
                        break;
                    case Constants.top_rated_name:
                        getActivity().setTitle(getResources().getString(R.string.top_rated_title));
                        break;
                    case Constants.favorite_name:
                        getActivity().setTitle(getResources().getString(R.string.favorite_title));
                        break;
                }
            }
        }
        //return the view
        return mainView;
    }

    //This method is responsible on update the UI front Grid list with the coming data from API
    private void UpdateAdapter(ArrayList<Object> moviesList) {
        //If the connection done successfully and the movies list not equal null
        if (moviesList != null) {
            //Update the global moviesList with the new movies list
            this.moviesList = moviesList;
            //Send movies list to the customAdpter
            moviesListCustomAdapter = new MoviesListAdapter(getActivity(), moviesList);
            //Set the result Adapter in the the GridView Adapter
            moviesListGridView.setAdapter(moviesListCustomAdapter);
            //If we're recreating a previously destroyed application instance
            if (Constants.lastPosition != GridView.INVALID_POSITION) {
                while (Constants.lastPosition >= this.moviesList.size()) {
                    if (Constants.lastPosition < 0)
                        break;
                    Constants.lastPosition--;
                }
                //Go to the position of the previous destroyed application + the different column number on the GridView
                moviesListGridView.smoothScrollToPosition(Constants.lastPosition == 0 ? Constants.lastPosition : Constants.lastPosition + 4);
            }
            if (Constants.mTwoPane) {
                Constants.lastPosition = Constants.lastPosition != GridView.INVALID_POSITION ?
                        Constants.lastPosition : 0;
                moviesListGridView.setItemChecked(Constants.lastPosition, true);
                handler.sendEmptyMessage(0);
            }

            moviesListGridView.deferNotifyDataSetChanged();
        } else { //If there is any kind of miss connection in the middle of retrieving the data
            //Display message Error
            Toast.makeText(getActivity().getApplicationContext(), R.string.no_data, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (Constants.Order) {
            case Constants.popular_name:
                getActivity().setTitle(getResources().getString(R.string.popular_title));
                return new CursorLoader(getActivity(),
                        MovieColumns.POPULAR_CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                );
            case Constants.top_rated_name:
                getActivity().setTitle(getResources().getString(R.string.top_rated_title));
                return new CursorLoader(getActivity(),
                        MovieColumns.TOP_RATED_CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                );
            case Constants.favorite_name:
                getActivity().setTitle(getResources().getString(R.string.favorite_title));
                return new CursorLoader(getActivity(),
                        MovieColumns.FAVORITE_CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                );

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        ArrayList<Object> dbList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                MovieColumnHolder movieColumnHolder = new MovieColumnHolder(
                        cursor.getString(cursor.getColumnIndex(MovieColumns.COL_POSTER_PATH)),
                        cursor.getString(cursor.getColumnIndex(MovieColumns.COL_OVERVIEW)),
                        cursor.getString(cursor.getColumnIndex(MovieColumns.COL_RELEASE_DATE)),
                        cursor.getInt(cursor.getColumnIndex(MovieColumns.COL_MOVIEDB_ID)),
                        cursor.getString(cursor.getColumnIndex(MovieColumns.COL_ORIGINAL_TITLE)),
                        cursor.getString(cursor.getColumnIndex(MovieColumns.COL_ORIGINAL_LAN)),
                        cursor.getString(cursor.getColumnIndex(MovieColumns.COL_TITLE)),
                        cursor.getString(cursor.getColumnIndex(MovieColumns.COL_BACKDROP)),
                        cursor.getFloat(cursor.getColumnIndex(MovieColumns.COL_POPULARITY)),
                        cursor.getInt(cursor.getColumnIndex(MovieColumns.COL_VOTE_COUNT)),
                        cursor.getFloat(cursor.getColumnIndex(MovieColumns.COL_VOTE_AVERAGE))
                );
                movieColumnHolder.set_id(cursor.getInt(cursor.getColumnIndex(MovieColumns._ID)));
                movieColumnHolder.setFavorite(cursor.getInt(cursor.getColumnIndex(MovieColumns.COL_FAVORITE)));
                dbList.add(movieColumnHolder);


            } while (cursor.moveToNext());
        }
        UpdateAdapter(dbList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public interface Callback {
        void onItemSelected(MovieColumnHolder movie);
    }
}