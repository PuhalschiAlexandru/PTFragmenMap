package com.tapptitude.fragmentmapapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tapptitude.fragmentmapapp.model.CoordinatesListItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by alexpuhalschi on 03/07/2017.
 */

public class CoordinateItemHelper {
    private static String APPLICATION_PREFERENCES = "APPLICATION_PREFERENCES";
    private static String COORDINATES_KEY = "COORDINATES_KEY";
    private Context mContext;
    private Gson mGson;


    public CoordinateItemHelper(Context context) {
        mGson = new Gson();
        mContext = context;
    }

    public List<CoordinatesListItem> getCoordinatesItems() {
        String json;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(APPLICATION_PREFERENCES,
                Context.MODE_PRIVATE);
        json = sharedPreferences.getString(COORDINATES_KEY, "");
        List<CoordinatesListItem> coordinatesList = mGson.fromJson(json,
                new TypeToken<List<CoordinatesListItem>>() {
                }.getType());
        return coordinatesList == null ? new ArrayList<CoordinatesListItem>() : coordinatesList;
    }

    public CoordinatesListItem getCoordinatesItem(int position) {
        return getCoordinatesItems().get(position);
    }

    public void storeCoordinateItem(CoordinatesListItem coordinatesListItem) {
        List<CoordinatesListItem> coordinatesList = getCoordinatesItems();
        coordinatesList.add(coordinatesListItem);
        String json = mGson.toJson(coordinatesList);
        SharedPreferences.Editor preferenceEditor = getSharedPreferences();
        preferenceEditor.putString(COORDINATES_KEY, json);
        preferenceEditor.apply();
    }

    public void deleteCoordinateItem(int position) {
        List<CoordinatesListItem> coordinatesList = getCoordinatesItems();
        coordinatesList.remove(position);
        String json = mGson.toJson(coordinatesList);
        SharedPreferences.Editor preferenceEditor = getSharedPreferences();
        preferenceEditor.putString(COORDINATES_KEY, json);
        preferenceEditor.apply();
    }

    public void swapCoordinateItem(int oldPosition, int newPosition) {
        List<CoordinatesListItem> coordinatesList = getCoordinatesItems();
        Collections.swap(coordinatesList, oldPosition, newPosition);
        String json = mGson.toJson(coordinatesList);
        SharedPreferences.Editor preferenceEditor = getSharedPreferences();
        preferenceEditor.putString(COORDINATES_KEY, json);
        preferenceEditor.apply();
    }

    public int getCoordinatesListItemsSize() {
        String json;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(APPLICATION_PREFERENCES,
                Context.MODE_PRIVATE);
        json = sharedPreferences.getString(COORDINATES_KEY, "");
        List<CoordinatesListItem> coordinatesList = mGson.fromJson(json,
                new TypeToken<List<CoordinatesListItem>>() {
                }.getType());
        return coordinatesList.size();
    }

    private SharedPreferences.Editor getSharedPreferences() {
        return mContext.getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE).edit();
    }
}
