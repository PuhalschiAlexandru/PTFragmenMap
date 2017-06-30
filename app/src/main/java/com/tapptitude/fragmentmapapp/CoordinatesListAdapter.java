package com.tapptitude.fragmentmapapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitleTV.setText(mCoordinateListItems.get(position).getTitle());
        holder.mCoordinates.setText(String.valueOf(mCoordinateListItems.get(position).getCoordinates()));
    }

    @Override
    public int getItemCount() {
        return mCoordinateListItems.size();
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
}
