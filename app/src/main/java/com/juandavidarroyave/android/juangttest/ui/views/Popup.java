package com.juandavidarroyave.android.juangttest.ui.views;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.juandavidarroyave.android.juangttest.R;

public class Popup extends DialogFragment {

    private Popup mPopup;
    private Button btnClose;
    private int windowX;
    private int windowY;
    public Popup(){

    }

    public static Popup newInstance(int position, int windowX, int windowY){
        Popup frag = new Popup();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putInt("window_x", windowX);
        args.putInt("window_y", windowY);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pop_up_window, container,false);
        int position = getArguments().getInt("position", 0);
        windowX = getArguments().getInt("window_x",0);
        windowY = getArguments().getInt("window_y",0);
        getDialog().setTitle(String.format(getActivity().getString(R.string.pop_up_window_title),position));
        btnClose = (Button) view.findViewById(R.id.btn_close_popup);
        btnClose.setOnClickListener(closeListener);
        mPopup =  this;
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            final float scale = getActivity().getResources().getDisplayMetrics().density;
            int margindp = (int) (50 * scale + 0.5f);
//            dialog.getWindow().setLayout(windowX - 2 * margindp, windowY - 2 * windowY);
            dialog.getWindow().setLayout(windowX - 2 * margindp, windowY - 2 * margindp);
        }
    }
    private View.OnClickListener closeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPopup.dismiss();
        }
    };
}
