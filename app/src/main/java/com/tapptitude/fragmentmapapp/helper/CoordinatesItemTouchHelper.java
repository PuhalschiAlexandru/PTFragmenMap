package com.tapptitude.fragmentmapapp.helper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.tapptitude.fragmentmapapp.ui.adapter.CoordinatesListAdapter;

/**
 * Created by alexpuhalschi on 05/07/2017.
 */

public class CoordinatesItemTouchHelper extends ItemTouchHelper.Callback {
    private CoordinatesListAdapter mCoordinateAdapter;
    private ItemTouchHelperListener mListener;

    public CoordinatesItemTouchHelper(CoordinatesListAdapter mCoordinateAdapter,
                                      ItemTouchHelperListener mListener) {
        this.mCoordinateAdapter = mCoordinateAdapter;
        this.mListener = mListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        int oldPos = viewHolder.getAdapterPosition();
        int newPos = target.getAdapterPosition();
        if (mListener != null) {
            mListener.onItemSwapped(oldPos, newPos);
        }
        mCoordinateAdapter.onItemMoved(oldPos, newPos);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();
        if (mListener != null) {
            mListener.onItemRemoved(pos);
        }
        mCoordinateAdapter.onItemDeleted(pos);
    }

    public interface ItemTouchHelperListener {
        void onItemRemoved(int position);

        void onItemSwapped(int oldPosition, int newPosition);
    }
}
