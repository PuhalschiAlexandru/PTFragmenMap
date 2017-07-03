package com.tapptitude.fragmentmapapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by alexpuhalschi on 03/07/2017.
 */

public class FragmentMapScreen extends Fragment implements OnMapReadyCallback {
    SupportMapFragment mMapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fms_f_map_fragment);
        mMapFragment.getMapAsync(FragmentMapScreen.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng mClujLocation = new LatLng(46.7667, 23.6);
        googleMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).position(mClujLocation));
    }
}
