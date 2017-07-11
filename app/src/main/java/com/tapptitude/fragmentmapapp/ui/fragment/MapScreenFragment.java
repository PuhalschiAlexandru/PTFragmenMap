package com.tapptitude.fragmentmapapp.ui.fragment;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.tapptitude.fragmentmapapp.model.CoordinatesListItem;
import com.tapptitude.fragmentmapapp.R;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by alexpuhalschi on 03/07/2017.
 */

public class MapScreenFragment extends Fragment implements OnMapReadyCallback {
    public static final String COORDINATE_DATE_FORMAT = "dd MMMM yyyy";
    public static final int DEFAULT_MAP_ZOOM = 15;
    private static double MAP_START_LONGITUDE = 46.7667;
    private static double MAP_START_LATITUDE = 23.6;

    @BindView(R.id.fms_tv_coordinates_text)
    TextView mCoordinatesTV;
    @BindView(R.id.fms_fab_add_button)
    FloatingActionButton mAddFAB;
    @BindView(R.id.fms_iv_marker)
    ImageView mMarkerIV;

    private GoogleMap mGoogleMap;
    private boolean mIsFABHidden;
    private Animator.AnimatorListener mFABAnimationListener;
    private OnCoordinatesListItemListener mCoordinateListItemListener;
    private CoordinatesListItem mCoordinateListItem;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCoordinatesListItemListener) {
            mCoordinateListItemListener = (OnCoordinatesListItemListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initializeAnimationListener();
        initializeMapFragment();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (mCoordinateListItem != null) {
            setCameraPositionOnItemCoordinates();
        } else if (mMarkerIV.getVisibility() == View.GONE) {
            displayDefaultMap();
        } else {
            displayInitialCameraPosition();
        }
    }

    private void initializeMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fms_f_map_fragment);
        mapFragment.getMapAsync(this);
    }

    private void initializeAnimationListener() {
        mFABAnimationListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (mAddFAB.getTranslationY() != 0 && mCoordinateListItem == null) {
                    mAddFAB.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (mAddFAB.getTranslationY() != 0) {
                    mAddFAB.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };
    }

    private void initializeOnCameraMoveListener() {
        mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                if (!mIsFABHidden) {
                    showFAB();
                    mIsFABHidden = true;
                }
            }
        });

        mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                hideFAB();
                mIsFABHidden = false;
            }
        });
    }

    private void showFAB() {
        mAddFAB.animate()
                .translationY(mAddFAB.getHeight())
                .alpha(0)
                .setListener(mFABAnimationListener);
    }

    private void hideFAB() {
        mAddFAB.animate()
                .translationY(0)
                .alpha(1.0f)
                .setListener(mFABAnimationListener);
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(COORDINATE_DATE_FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    public CoordinatesListItem getCurrentCoordinatesListItem() {
        return new CoordinatesListItem(getCurrentDate(), mGoogleMap.getCameraPosition().target);
    }

    public void showCoordinatesDetails(CoordinatesListItem coordinatesListItem) {
        mCoordinateListItem = coordinatesListItem;
        mAddFAB.setVisibility(View.GONE);
        if (mGoogleMap != null) {
            setCameraPositionOnItemCoordinates();
        }
    }

    private void setCameraPositionOnItemCoordinates() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mCoordinateListItem.getCoordinates()).zoom(DEFAULT_MAP_ZOOM).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
        mCoordinatesTV.setText(String.valueOf(mCoordinateListItem.getTitle()));
    }

    public void displayInitialCameraPosition() {
        mAddFAB.setVisibility(View.VISIBLE);
        mMarkerIV.setVisibility(View.VISIBLE);
        mCoordinateListItem = null;
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(MAP_START_LONGITUDE, MAP_START_LATITUDE))
                .zoom(DEFAULT_MAP_ZOOM)
                .build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
        mCoordinatesTV.setText("");
        initializeOnCameraMoveListener();
    }

    private void displayDefaultMap() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(MAP_START_LONGITUDE, MAP_START_LATITUDE))
                .zoom(DEFAULT_MAP_ZOOM)
                .build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
    }

    public void stopMapInteraction() {
        mMarkerIV.setVisibility(View.GONE);
        mAddFAB.setVisibility(View.GONE);
        mCoordinatesTV.setText("");
        if (mGoogleMap != null) {
            displayDefaultMap();
        }
    }

    @OnClick(R.id.fms_fab_add_button)
    public void onFABClicked() {
        if (mCoordinateListItemListener != null) {
            mCoordinateListItemListener.onAddButtonClicked(getCurrentCoordinatesListItem());
        }
    }

    public interface OnCoordinatesListItemListener {
        void onAddButtonClicked(CoordinatesListItem coordinatesListItem);
    }
}
