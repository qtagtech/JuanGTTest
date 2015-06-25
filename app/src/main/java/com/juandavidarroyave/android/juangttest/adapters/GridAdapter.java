package com.juandavidarroyave.android.juangttest.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.juandavidarroyave.android.juangttest.utilities.ViewHolder;


import com.juandavidarroyave.android.juangttest.R;
import com.juandavidarroyave.android.juangttest.ui.widget.DeckCard;
import java.util.Collections;
import java.util.List;

import hugo.weaving.DebugLog;


public class GridAdapter extends RecyclerView.Adapter<ViewHolder> {

    public interface OnGridItemClicked {
         void onGridItemClicked(int position);
    }

    private List<DeckCard> mItems = Collections.emptyList();
    private OnGridItemClicked gridClicked;
    private Fragment mFragment;
    public GridAdapter(Fragment fragment) {
        super();
        mFragment = fragment;
        try {
            gridClicked = (OnGridItemClicked) mFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(mFragment.toString()
                    + " must implement onGridItemClicked");
        }
    }

    public void updateGrid(List<DeckCard> data){
        mItems = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);
        return new ViewHolder(v);

    }



    @Override
    @DebugLog
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        DeckCard current = mItems.get(position);
        viewHolder.imgThumbnail.setImageResource(current.getThumb());
        viewHolder.setOnClickListener(new ViewHolder.MyViewHolderClicks() {
            @Override
            public void clickOnImage(ImageView caller) {
                gridClicked.onGridItemClicked(position);
            }
            @Override
            public void clickOnLayout(View caller) {
                //Do nothing to avoid tapping on margins
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    /*public void addItem(int position, DeckCard card) {
        mItems.add(position, card);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }*/



}
