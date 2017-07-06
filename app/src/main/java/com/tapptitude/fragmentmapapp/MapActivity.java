package com.tapptitude.fragmentmapapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MapActivity extends AppCompatActivity implements FragmentMapScreen.OnCoordinatesListItemListener {
    private static final String COORDINATES_ITEM_TAG = "COORDINATES_ITEM_TAG";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeMapShow();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeMapShow() {
        Intent intent = this.getIntent();
        if (intent.hasExtra(COORDINATES_ITEM_TAG)) {
            CoordinatesListItem coordinatesListItem = intent.getParcelableExtra(COORDINATES_ITEM_TAG);
            FragmentMapScreen fragmentMapScreen = (FragmentMapScreen) getSupportFragmentManager().findFragmentById(R.id.am_f_main_fragment);
            fragmentMapScreen.showCoordinatesDetails(coordinatesListItem);
        }
    }

    @Override
    public void onFABAddButtonClicked(CoordinatesListItem coordinatesListItem) {
        Intent intent = new Intent();
        intent.putExtra(COORDINATES_ITEM_TAG, coordinatesListItem);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
