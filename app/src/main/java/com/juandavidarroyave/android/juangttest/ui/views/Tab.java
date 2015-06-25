package com.juandavidarroyave.android.juangttest.ui.views;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.*;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.juandavidarroyave.android.juangttest.R;
import com.juandavidarroyave.android.juangttest.adapters.GridAdapter;
import com.juandavidarroyave.android.juangttest.ui.widget.DeckCard;


import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

public class Tab extends Fragment implements GridAdapter.OnGridItemClicked {




    public interface OnImageSelectedListener {
       void onImageSelected(int position);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnImageSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    OnImageSelectedListener mCallback;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private GridAdapter mAdapter;
    private View mView;
    private Context fContext;
    private int numImages;
    private int imagesPerPage;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.tab_1,container,false);
        fContext = getActivity();
        SharedPreferences sharedPref = fContext.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int defaultImagesPerPage = getResources().getInteger(R.integer.saved_images_per_page);
        int defaultImagesTotal = getResources().getInteger(R.integer.saved_total_images);
        imagesPerPage = sharedPref.getInt(getString(R.string.images_per_page), defaultImagesPerPage);
        numImages = sharedPref.getInt(getString(R.string.total_images), defaultImagesTotal);
        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.images_1);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(fContext, 4);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new GridAdapter((Fragment) this);
//        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(mAdapter);
//        scaleAdapter.setDuration(1000);
        mRecyclerView.setAdapter(mAdapter);

        return mView;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateGrid(0);
    }

    public void updateGrid(int position){
        int nextImage = ( position * imagesPerPage ) + 1;
        int offset = nextImage + imagesPerPage - 1;
        offset = offset <= numImages ? offset : numImages;
        List<DeckCard> cards = new ArrayList<>();
        while(nextImage <= offset){
            DeckCard currentCard = new DeckCard();
            currentCard.setThumb(R.drawable.game_icon);
            currentCard.setName("Playing Card");
            cards.add(currentCard);
            nextImage++;
        }
        mAdapter.updateGrid(cards);
    }

    @Override
    @DebugLog
    public void onGridItemClicked(int position) {
            mCallback.onImageSelected(position);
    }






}
