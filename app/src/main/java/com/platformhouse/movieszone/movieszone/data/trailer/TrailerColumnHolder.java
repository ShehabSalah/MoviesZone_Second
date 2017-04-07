package com.platformhouse.movieszone.movieszone.data.trailer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shehab Salah on 8/18/16.
 * This Class is responsible on holding one Trailer of the one movie
 */
public class TrailerColumnHolder implements Parcelable {
    private String key;
    private String name;
    private String size;
    private String type;

    public TrailerColumnHolder(String key, String name, String size, String type) {
        this.key = key;
        this.name = name;
        this.size = size;
        this.type = type;
    }

    // Parcelling part
    public TrailerColumnHolder(Parcel in){
        String[] data = new String[4];
        in.readStringArray(data);
        this.key        = data[0];
        this.name       = data[1];
        this.size       = data[2];
        this.type       = data[3];
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.key,
                this.name,
                this.size,
                this.type,
        });
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TrailerColumnHolder createFromParcel(Parcel in) {
            return new TrailerColumnHolder(in);
        }
        public TrailerColumnHolder[] newArray(int size) {
            return new TrailerColumnHolder[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
