package com.example.spectrus.stockide;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spectrus.stockide.AppFunction.AppGraphics;

public class OptionsFragment extends Fragment implements View.OnClickListener {

    // Variables for Stock View Fragment
    AppGraphics optionsFragGraphics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set fragment
        View v = inflater.inflate(R.layout.fragment_options, container, false);
        optionsFragGraphics = new AppGraphics(getActivity());

        // Button onClick Inputs
        //Button button_00 = (Button) v.findViewById(R.id.activity_00);
        //button_00.setOnClickListener(this);
        //button_00.setTypeface(font_0);
        //button_00.setTextColor(Color.WHITE);

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Flashcard Menu Popup
            //case R.id.activity_00:
            //    break;
            default:
                break;
        }
    }

}
