package com.platformhouse.movieszone.movieszone.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.platformhouse.movieszone.movieszone.R;
import com.platformhouse.movieszone.movieszone.data.movie.MovieColumnHolder;
import com.platformhouse.movieszone.movieszone.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shehab Salah on 8/18/16.
 *
 */
public class MoviesListAdapter extends BaseAdapter {
    private ArrayList<Object> moviesList;
    private Context activity;
    private LayoutInflater inflater;
    private PlaceHolder placeHolder;

    public MoviesListAdapter(Context context, ArrayList<Object> moviesList) {
        activity = context;
        this.moviesList = moviesList;
        inflater = null;
    }
    @Override
    public int getCount() {
        return moviesList.size() == 0 ? 0 : moviesList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.movies_list_item, parent, false);
            placeHolder = new PlaceHolder(itemView);
            itemView.setTag(placeHolder);
        } else {
            placeHolder = (PlaceHolder) itemView.getTag();
        }
        MovieColumnHolder movieColumnHolder = (MovieColumnHolder) moviesList.get(position);
        Picasso.with(activity)
                .load(Constants.IMAGE_URL + movieColumnHolder.getPoster_path())
                .error(R.mipmap.error_background)
                .placeholder(R.mipmap.placeholder_background)
                .into(placeHolder.moviePoster);
        return itemView;
    }
    public class PlaceHolder {
        @BindView(R.id.movie_item_poster_MainActivity) ImageView moviePoster;

        PlaceHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}