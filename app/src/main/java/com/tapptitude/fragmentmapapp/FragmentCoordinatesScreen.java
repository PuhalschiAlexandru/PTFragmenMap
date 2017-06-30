package com.tapptitude.fragmentmapapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexpuhalschi on 29/06/2017.
 */

public class FragmentCoordinatesScreen extends Fragment {
    @BindView(R.id.fcs_rv_list)
    RecyclerView mRecyclerView;
    private CoordinatesListAdapter mCoordinatesAdapter;

    private List<CoordinatesListItem> mCoordinatesListItemsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coordinates_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mCoordinatesListItemsList = new ArrayList<>();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
//        mCoordinatesAdapter = new CoordinatesListAdapter(testData());
        mRecyclerView.setAdapter(mCoordinatesAdapter);
    }
}
