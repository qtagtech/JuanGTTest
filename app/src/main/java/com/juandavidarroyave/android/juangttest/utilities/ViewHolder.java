package com.juandavidarroyave.android.juangttest.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.juandavidarroyave.android.juangttest.R;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public interface MyViewHolderClicks{
        void clickOnImage(ImageView caller);
        void clickOnLayout(View caller);
    }

    public ImageView imgThumbnail;
    public MyViewHolderClicks mListener;
    public View itemView;

    public ViewHolder(View itemView) {
        super(itemView);
        imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
        this.itemView = itemView;

    }

    public void setOnClickListener(MyViewHolderClicks listener){
        mListener = listener;
        imgThumbnail.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v instanceof ImageView){
            mListener.clickOnImage((ImageView) v);
        }
        else{
            mListener.clickOnLayout(v);
        }
    }

}
