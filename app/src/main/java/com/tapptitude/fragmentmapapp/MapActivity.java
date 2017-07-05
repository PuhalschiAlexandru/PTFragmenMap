package com.tapptitude.fragmentmapapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MapActivity extends AppCompatActivity implements FragmentMapScreen.OnCoordinatesListItemListener {
    private static final String COORDINATES_ITEM_TAG = "COORDINATES_ITEM_TAG";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    @Override
    public void onFABAddButtonClicked(CoordinatesListItem coordinatesListItem) {
        Intent intent = new Intent();
        intent.putExtra(COORDINATES_ITEM_TAG, coordinatesListItem);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
