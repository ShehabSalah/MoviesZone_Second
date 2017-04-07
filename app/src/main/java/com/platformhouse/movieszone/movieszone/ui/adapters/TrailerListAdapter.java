package com.platformhouse.movieszone.movieszone.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.platformhouse.movieszone.movieszone.R;
import com.platformhouse.movieszone.movieszone.data.trailer.TrailerColumnHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * Created by Shehab Salah on 8/19/16.
 */
public class TrailerListAdapter extends BaseAdapter {
    private ArrayList<Object> trailerList;
    private Context activity;
    private LayoutInflater inflater = null;
    PlaceHolder placeHolder;
    TrailerColumnHolder trailerColumnHolder;
    public TrailerListAdapter(Context context, ArrayList<Object> trailerList) {
        activity = context;
        this.trailerList = trailerList;

    }
    @Override
    public int getCount() {
        return trailerList.size() == 0 ? 0 : trailerList.size();
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
        if (itemView == null){
            inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.trailer_list_item, parent, false);
            placeHolder = new PlaceHolder(itemView);
            itemView.setTag(placeHolder);
        }else{
            placeHolder = (PlaceHolder) itemView.getTag();
        }
        trailerColumnHolder = (TrailerColumnHolder) trailerList.get(position);
        placeHolder.TrailerName.setText(trailerColumnHolder.getName());
        return itemView;
    }
    class PlaceHolder {
        @BindView(R.id.trailer_item_name) TextView TrailerName;

        PlaceHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
