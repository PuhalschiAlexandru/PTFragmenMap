package com.tapptitude.fragmentmapapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by alexpuhalschi on 03/07/2017.
 */

public class FragmentMapScreen extends Fragment implements OnMapReadyCallback {
    private static double MAP_START_LONGITUDE = 46.7667;
    private static double MAP_START_LATITUDE = 23.6;
    private GoogleMap mGoogleMap;
    @BindView(R.id.fms_fab_add_button)
    FloatingActionButton mFloatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fms_f_map_fragment);
        mMapFragment.getMapAsync(FragmentMapScreen.this);
        ButterKnife.bind(view);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(MAP_START_LONGITUDE, MAP_START_LATITUDE)).zoom(15).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        initializeOnCameraMoveListener();
    }

    private void initializeOnCameraMoveListener() {
        mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                // mCenterCamera = mGoogleMap.getCameraPosition().target;
            }
        });
    }

    @OnClick(R.id.fms_fab_add_button)
    private void floatingButtonClicked() {

    }


}
