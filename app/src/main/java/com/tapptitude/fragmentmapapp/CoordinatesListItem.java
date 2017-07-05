package com.tapptitude.fragmentmapapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by alexpuhalschi on 30/06/2017.
 */

public class CoordinatesListItem implements Parcelable {
    private String mTitle;
    private LatLng mCoordinates;

    public CoordinatesListItem(String title, LatLng coordinates) {
        mTitle = title;
        mCoordinates = coordinates;
    }

    private CoordinatesListItem(Parcel in) {
        mTitle = in.readString();
        mCoordinates = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<CoordinatesListItem> CREATOR = new Creator<CoordinatesListItem>() {
        @Override
        public CoordinatesListItem createFromParcel(Parcel in) {
            return new CoordinatesListItem(in);
        }

        @Override
        public CoordinatesListItem[] newArray(int size) {
            return new CoordinatesListItem[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public LatLng getCoordinates() {
        return mCoordinates;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeParcelable(mCoordinates, i);
    }
}
