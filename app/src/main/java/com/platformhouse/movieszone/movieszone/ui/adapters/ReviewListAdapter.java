package com.platformhouse.movieszone.movieszone.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.platformhouse.movieszone.movieszone.R;
import com.platformhouse.movieszone.movieszone.data.review.ReviewColumnHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * Created by Shehab Salah on 8/23/16.
 */
public class ReviewListAdapter extends BaseAdapter{
    private ArrayList<Object> reviewList;
    private Context activity;
    private LayoutInflater inflater = null;
    PlaceHolder placeHolder;
    ReviewColumnHolder reviewColumnHolder;
    public ReviewListAdapter(Context context, ArrayList<Object> reviewList) {
        activity = context;
        this.reviewList = reviewList;

    }
    @Override
    public int getCount() {
        return reviewList.size() == 0 ? 0 : reviewList.size();
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
            itemView = inflater.inflate(R.layout.review_list_item, parent, false);
            placeHolder = new PlaceHolder(itemView);
            itemView.setTag(placeHolder);
        }else{
            placeHolder = (PlaceHolder) itemView.getTag();
        }
        reviewColumnHolder = (ReviewColumnHolder) reviewList.get(position);
        placeHolder.reviewAuthor.setText(reviewColumnHolder.getAuthor());
        placeHolder.reviewContent.setText(reviewColumnHolder.getContent());
        return itemView;
    }
    class PlaceHolder {
        @BindView(R.id.review_author) TextView reviewAuthor;
        @BindView(R.id.review_content) TextView reviewContent;

        PlaceHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
