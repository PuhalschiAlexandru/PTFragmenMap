package com.tapptitude.fragmentmapapp;

import android.animation.Animator;
import android.content.Context;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by alexpuhalschi on 03/07/2017.
 */

public class FragmentMapScreen extends Fragment implements OnMapReadyCallback {
    private static double MAP_START_LONGITUDE = 46.7667;
    private static double MAP_START_LATITUDE = 23.6;

    @BindView(R.id.fms_fab_add_button)
    FloatingActionButton mFloatingActionButton;
    private GoogleMap mGoogleMap;
    private boolean mIsFABHidden;
    private Animator.AnimatorListener mFABAnimationListener;
    private OnCoordinatesListItemListener mCoordinateListItemListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCoordinatesListItemListener) {
            mCoordinateListItemListener = (OnCoordinatesListItemListener) context;
        } else {
            throw new RuntimeException(context.toString());
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
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(MAP_START_LONGITUDE, MAP_START_LATITUDE)).zoom(15).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        initializeOnCameraMoveListener();
    }

    private void initializeMapFragment() {
        SupportMapFragment mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fms_f_map_fragment);
        mMapFragment.getMapAsync(this);
    }

    private void initializeAnimationListener() {
        mFABAnimationListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (mFloatingActionButton.getTranslationY() != 0) {
                    mFloatingActionButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (mFloatingActionButton.getTranslationY() != 0) {
                    mFloatingActionButton.setVisibility(View.GONE);
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
        mFloatingActionButton.animate()
                .translationY(mFloatingActionButton.getHeight())
                .alpha(0)
                .setListener(mFABAnimationListener);
    }

    private void hideFAB() {
        mFloatingActionButton.animate()
                .translationY(0)
                .alpha(1.0f)
                .setListener(mFABAnimationListener);
    }

    public interface OnCoordinatesListItemListener {
        void onFABAddButtonClicked(CoordinatesListItem coordinatesListItem);
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        return dateFormat.format(calendar.getTime());
    }

    @OnClick(R.id.fms_fab_add_button)
    public void onFABClicked() {
        CoordinatesListItem item = new CoordinatesListItem(getCurrentDate(), mGoogleMap.getCameraPosition().target);
        mCoordinateListItemListener.onFABAddButtonClicked(item);
    }
}
