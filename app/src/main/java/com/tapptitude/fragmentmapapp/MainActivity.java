package com.tapptitude.fragmentmapapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import Helper.CoordinateItemHelper;

public class MainActivity extends AppCompatActivity implements FragmentCoordinatesScreen.CoordinateScreenItemListener {
    private static final int REQUEST_CODE = 1;
    private static final String COORDINATES_ITEM_TAG = "COORDINATES_ITEM_TAG";
    private FragmentCoordinatesScreen mCoordinatesFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeCoordinatesFragment();
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

                if(mCoordinatesFragment != null) {
                    mCoordinatesFragment.onNewCoordinatesItemAdded(coordinatesListItem);
                }
            }
        }
    }

    private void initializeCoordinatesFragment() {
        mCoordinatesFragment = (FragmentCoordinatesScreen) getSupportFragmentManager()
                .findFragmentById(R.id.am_f_screen_fragment);
    }

    private void onAddClicked() {
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onCoordinateScreenItemSelected(CoordinatesListItem coordinatesListItem) {
        // TODO: 05/07/2017 Implement details display
    }
}
