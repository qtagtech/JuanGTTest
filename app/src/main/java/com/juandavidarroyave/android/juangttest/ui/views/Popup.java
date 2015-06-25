package com.juandavidarroyave.android.juangttest.ui.views;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.juandavidarroyave.android.juangttest.R;

public class Popup extends DialogFragment {

    private Popup mPopup;
    private Button btnClose;

    public Popup(){

    }

    public static Popup newInstance(int position){
        Popup frag = new Popup();
        Bundle args = new Bundle();
        args.putInt("position", position);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pop_up_window, container,false);
        int position = getArguments().getInt("position", 0);
        getDialog().setTitle(String.format(getActivity().getString(R.string.pop_up_window_title),position));
        btnClose = (Button) view.findViewById(R.id.btn_close_popup);
        btnClose.setOnClickListener(closeListener);
        mPopup =  this;
        return view;
    }
    private View.OnClickListener closeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPopup.dismiss();
        }
    };
}
