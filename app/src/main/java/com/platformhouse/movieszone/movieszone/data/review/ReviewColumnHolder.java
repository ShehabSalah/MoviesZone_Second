package com.platformhouse.movieszone.movieszone.data.review;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shehab Salah on 8/18/16.
 * This Class is responsible on holding one review of the one movie
 */
public class ReviewColumnHolder implements Parcelable {
    private String author;
    private String content;
    private String url;

    public ReviewColumnHolder(String author, String content, String url) {
        this.author = author;
        this.content = content;
        this.url = url;
    }

    // Parcelling part
    public ReviewColumnHolder(Parcel in){
        String[] data = new String[3];
        in.readStringArray(data);
        this.author         = data[0];
        this.content        = data[1];
        this.url            = data[2];
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.author,
                this.content,
                this.url,
        });
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ReviewColumnHolder createFromParcel(Parcel in) {
            return new ReviewColumnHolder(in);
        }
        public ReviewColumnHolder[] newArray(int size) {
            return new ReviewColumnHolder[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
