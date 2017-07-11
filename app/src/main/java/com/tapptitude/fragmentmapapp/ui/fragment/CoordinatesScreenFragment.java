package com.tapptitude.fragmentmapapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tapptitude.fragmentmapapp.ui.adapter.CoordinatesListAdapter;
import com.tapptitude.fragmentmapapp.model.CoordinatesListItem;
import com.tapptitude.fragmentmapapp.R;
import com.tapptitude.fragmentmapapp.helper.CoordinateItemHelper;
import com.tapptitude.fragmentmapapp.helper.CoordinatesItemTouchHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexpuhalschi on 29/06/2017.
 */

public class CoordinatesScreenFragment extends Fragment implements
        CoordinatesListAdapter.CoordinateItemClickListener {
    @BindView(R.id.fcs_rv_list)
    RecyclerView mFragmentCoordinatesScreenRV;
    private CoordinatesListAdapter mCoordinatesAdapter;
    private CoordinateScreenItemListener mCoordinateScreenItemListener;
    private CoordinateItemHelper mCoordinateItemHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoordinateItemHelper = new CoordinateItemHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coordinates_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initRecyclerView();
        initItemTouchHelper();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CoordinateScreenItemListener) {
            mCoordinateScreenItemListener = (CoordinateScreenItemListener) context;
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mFragmentCoordinatesScreenRV.setLayoutManager(mLinearLayoutManager);
        mCoordinatesAdapter = new CoordinatesListAdapter(getActivity(), mCoordinateItemHelper
                .getCoordinatesItems(), this);
        mFragmentCoordinatesScreenRV.setAdapter(mCoordinatesAdapter);
    }

    private void initItemTouchHelper() {
        ItemTouchHelper.Callback itemTouchHelperCallBack = new CoordinatesItemTouchHelper(mCoordinatesAdapter,
                new CoordinatesItemTouchHelper.ItemTouchHelperListener() {
                    @Override
                    public void onItemRemoved(final int position) {
                        showUndoSnackBar(position);
                    }

                    @Override
                    public void onItemSwapped(int oldPosition, int newPosition) {
                        mCoordinateItemHelper.swapCoordinateItem(oldPosition, newPosition);
                    }
                });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallBack);
        itemTouchHelper.attachToRecyclerView(mFragmentCoordinatesScreenRV);
    }

    public void onNewCoordinatesItemAdded(CoordinatesListItem coordinatesListItem) {
        mCoordinateItemHelper.storeCoordinateItem(coordinatesListItem);
        mCoordinatesAdapter.addCoordinateItem(coordinatesListItem, mCoordinateItemHelper
                .getCoordinatesListItemsSize());
    }

    private void showUndoSnackBar(final int position) {
        Snackbar.make(mFragmentCoordinatesScreenRV, R.string.undo_snack_bar_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                switch (event) {
                    case Snackbar.Callback.DISMISS_EVENT_ACTION:
                        mCoordinatesAdapter.undoDelete(position, mCoordinateItemHelper
                                .getCoordinatesItem(position));
                        break;
                    default:
                        mCoordinateItemHelper.deleteCoordinateItem(position);
                        if (mCoordinateItemHelper.getCoordinatesListItemsSize() == 0) {
                            mCoordinateScreenItemListener.onCoordinateScreenIsEmpty();
                        }
                        break;
                }
            }
        }).show();
    }

    @Override
    public void onCoordinateItemClicked(int position) {
        if (mCoordinateScreenItemListener != null) {
            mCoordinateScreenItemListener.onCoordinateScreenItemSelected(mCoordinateItemHelper
                    .getCoordinatesItem(position), position);
        }
    }

    public void changeItemColor(int position) {
        mCoordinatesAdapter.setClickedItemPosition(position);
        mCoordinatesAdapter.notifyDataSetChanged();
    }
    public void scrollToPossition(int position){
        mFragmentCoordinatesScreenRV.smoothScrollToPosition(position);
    }

    public interface CoordinateScreenItemListener {
        void onCoordinateScreenItemSelected(CoordinatesListItem coordinatesListItem, int position);

        void onCoordinateScreenIsEmpty();
    }
}
