package com.mazurco066.notepad.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mazurco066.notepad.R;

import butterknife.ButterKnife;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflando o layout ao fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        //Retornando view inflada
        return view;
    }

}
