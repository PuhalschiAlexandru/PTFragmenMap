package com.tapptitude.fragmentmapapp.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.tapptitude.fragmentmapapp.model.CoordinatesListItem;
import com.tapptitude.fragmentmapapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexpuhalschi on 30/06/2017.
 */

public class CoordinatesListAdapter extends RecyclerView.Adapter<CoordinatesListAdapter.ViewHolder> {
    private final int mClickedItemColor;
    private final int mDefaultItemColor;
    private List<CoordinatesListItem> mCoordinateListItems;
    private CoordinateItemClickListener mCoordinateItemClickListener;
    private int mClickedItemPosition = -1;

    public CoordinatesListAdapter(Context context, List<CoordinatesListItem> mCoordinateListItems,
                                  CoordinateItemClickListener mCoordinateItemClickListener) {
        this.mCoordinateListItems = mCoordinateListItems;
        this.mCoordinateItemClickListener = mCoordinateItemClickListener;
        this.mClickedItemColor = ContextCompat.getColor(context, R.color.light_gray);
        this.mDefaultItemColor = ContextCompat.getColor(context, R.color.white);
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
        if (position == mClickedItemPosition) {
            holder.mItemCV.setBackgroundColor(mClickedItemColor);
        } else {
            holder.mItemCV.setBackgroundColor(mDefaultItemColor);
        }
    }

    @Override
    public int getItemCount() {
        return mCoordinateListItems == null ? 0 : mCoordinateListItems.size();
    }

    public void addCoordinateItem(CoordinatesListItem coordinatesListItem, int position) {
        if (mCoordinateListItems == null) {
            mCoordinateListItems = new ArrayList<>();
        }
        mCoordinateListItems.add(position - 1, coordinatesListItem);
        notifyItemInserted(position - 1);
    }

    public void setClickedItemPosition(int position) {
        mClickedItemPosition = position;
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.im_tv_title)
        TextView mTitleTV;

        @BindView(R.id.im_tv_coordinates)
        TextView mCoordinates;

        @BindView(R.id.im_cv_main_view)
        CardView mItemCV;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mCoordinateItemClickListener != null) {
                mCoordinateItemClickListener.onCoordinateItemClicked(ViewHolder.this.getAdapterPosition());
            }
        }
    }

    public interface CoordinateItemClickListener {
        void onCoordinateItemClicked(int position);
    }
}
