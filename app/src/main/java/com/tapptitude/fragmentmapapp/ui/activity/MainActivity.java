package com.tapptitude.fragmentmapapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import com.tapptitude.fragmentmapapp.model.CoordinatesListItem;
import com.tapptitude.fragmentmapapp.ui.fragment.CoordinatesScreenFragment;
import com.tapptitude.fragmentmapapp.ui.fragment.MapScreenFragment;
import com.tapptitude.fragmentmapapp.R;
import com.tapptitude.fragmentmapapp.helper.CoordinateItemHelper;


public class MainActivity extends AppCompatActivity implements
        CoordinatesScreenFragment.CoordinateScreenItemListener,
        MapScreenFragment.OnCoordinatesListItemListener {
    private static final int REQUEST_CODE = 1;
    private static final String COORDINATES_ITEM_TAG = "COORDINATES_ITEM_TAG";

    private CoordinatesScreenFragment mCoordinatesFragment;
    private MapScreenFragment mMapFragment;
    private CoordinateItemHelper mCoordinateItemHelper;
    private boolean mIsTwoPaneScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIsTwoPaneScreen = (int) getResources().getDimension(R.dimen.two_pane_screen) == 1;

        if (mIsTwoPaneScreen) {
            initializeMapScreen();
            initializeCoordinatesFragment();
            displayFirstItemCoordinates();
        } else {
            initializeCoordinatesFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mai_i_add:
                onAddClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                CoordinatesListItem coordinatesListItem = data.getParcelableExtra(COORDINATES_ITEM_TAG);
                if (mCoordinatesFragment != null) {
                    mCoordinatesFragment.onNewCoordinatesItemAdded(coordinatesListItem);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initializeMapScreen() {
        mMapFragment = (MapScreenFragment) getSupportFragmentManager()
                .findFragmentById(R.id.am_f_large_map_fragment);
    }

    private void initializeCoordinatesFragment() {
        mCoordinatesFragment = (CoordinatesScreenFragment) getSupportFragmentManager()
                .findFragmentById(mIsTwoPaneScreen ?
                        R.id.am_f_large_coordinate_fragment : R.id.am_f_screen_fragment);
    }

    private void onAddClicked() {
        if (mIsTwoPaneScreen) {
            mMapFragment.displayInitialCameraPosition();
            mCoordinatesFragment.changeItemColor(-1);
        } else {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    public void onCoordinateScreenItemSelected(CoordinatesListItem coordinatesListItem, int position) {
        if (mIsTwoPaneScreen) {
            mMapFragment.showCoordinatesDetails(coordinatesListItem);
            mCoordinatesFragment.changeItemColor(position);
        } else {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            intent.putExtra(COORDINATES_ITEM_TAG, coordinatesListItem);
            startActivity(intent);
        }
    }

    @Override
    public void onCoordinateScreenIsEmpty() {
        if (mIsTwoPaneScreen) {
            mMapFragment.stopMapInteraction();
        }
    }

    private void displayFirstItemCoordinates() {
        if (mIsTwoPaneScreen) {
            CoordinatesListItem firstCoordinateListItem = null;
            mCoordinateItemHelper = new CoordinateItemHelper(this);
            if (mCoordinateItemHelper.getCoordinatesListItemsSize() >= 1) {
                List<CoordinatesListItem> coordinatesListItems = mCoordinateItemHelper.getCoordinatesItems();
                firstCoordinateListItem = coordinatesListItems.get(0);
            } else {
                mMapFragment.stopMapInteraction();
            }
            if (firstCoordinateListItem != null) {
                mMapFragment.showCoordinatesDetails(firstCoordinateListItem);
                mCoordinatesFragment.changeItemColor(0);
            }
        }
    }

    @Override
    public void onAddButtonClicked(CoordinatesListItem coordinatesListItem) {
        if (mCoordinatesFragment != null) {
            mCoordinatesFragment.onNewCoordinatesItemAdded(mMapFragment.getCurrentCoordinatesListItem());
            mMapFragment.showCoordinatesDetails(coordinatesListItem);
            mCoordinatesFragment.changeItemColor(mCoordinateItemHelper.getCoordinatesListItemsSize() - 1);
            mCoordinatesFragment.scrollToPossition(mCoordinateItemHelper.getCoordinatesListItemsSize() - 1);
        }
    }
}
