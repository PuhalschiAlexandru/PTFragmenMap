package Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tapptitude.fragmentmapapp.CoordinatesListItem;

import java.util.List;

/**
 * Created by alexpuhalschi on 03/07/2017.
 */

public class CoordinateItemHelper {
    private static String APPLICATION_PREFERENCES = "APPLICATION_PREFERENCES";
    private static String COORDINATES_KEY = "COORDINATES_KEY";
    private List<CoordinatesListItem> mCoordinatesListItems;
    private Context mContext;
    private Gson mGson;
    private SharedPreferences mSharedPreferences;


    public CoordinateItemHelper(Context context) {
        mGson = new Gson();
        mContext = context;
    }

    public CoordinatesListItem getCoorinatesItem(int position) {
        String json = "";
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.getString(COORDINATES_KEY, json);
        mCoordinatesListItems = mGson.fromJson(json, new TypeToken<List<CoordinatesListItem>>() {
        }.getType());
        return mCoordinatesListItems.get(position);
    }

    public void storeCoordinateItem(CoordinatesListItem coordinatesListItem) {
        mCoordinatesListItems.add(coordinatesListItem);
        String json = mGson.toJson(mCoordinatesListItems);

        SharedPreferences.Editor preferenceEditor = mContext.getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE).edit();
        preferenceEditor.putString(COORDINATES_KEY, json);
        preferenceEditor.apply();
    }
}
