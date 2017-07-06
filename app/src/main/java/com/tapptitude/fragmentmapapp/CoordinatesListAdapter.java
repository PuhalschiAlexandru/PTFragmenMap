package com.tapptitude.fragmentmapapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexpuhalschi on 30/06/2017.
 */

public class CoordinatesListAdapter extends RecyclerView.Adapter<CoordinatesListAdapter.ViewHolder> {
    private List<CoordinatesListItem> mCoordinateListItems;


    public CoordinatesListAdapter(List<CoordinatesListItem> mCoordinateListItems) {
        this.mCoordinateListItems = mCoordinateListItems;
    }

    @Override
    public CoordinatesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_map, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CoordinatesListItem current = mCoordinateListItems.get(position);
        holder.mTitleTV.setText(current.getTitle());
        holder.mCoordinates.setText(String.valueOf(current.getCoordinates()));
    }

    @Override
    public int getItemCount() {
        return mCoordinateListItems == null ? 0 : mCoordinateListItems.size();
    }

    public void addCoordinateItem(CoordinatesListItem coordinatesListItem) {
        if (mCoordinateListItems == null) {
            mCoordinateListItems = new ArrayList<>();
        }
        mCoordinateListItems.add(0, coordinatesListItem);
        notifyItemInserted(0);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.im_tv_title)
        TextView mTitleTV;

        @BindView(R.id.im_tv_coordinates)
        TextView mCoordinates;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void onItemMoved(int oldPosition, int newPosition) {
        Collections.swap(mCoordinateListItems, oldPosition, newPosition);
        notifyItemMoved(oldPosition, newPosition);
    }

    public void onItemDeleted(int position) {
        mCoordinateListItems.remove(position);
        notifyItemRemoved(position);
    }

    public void undoDelete(int position, CoordinatesListItem coordinatesListItem) {
        mCoordinateListItems.add(position, coordinatesListItem);
        notifyItemInserted(position);
    }
}
