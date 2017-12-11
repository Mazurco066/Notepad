package com.mazurco066.notepad.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.util.Preferences;

public class ThemeFragment extends Fragment {

    //Atributos
    private Preferences preferences;

    public ThemeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_theme, container, false);

        // Instanciando Componentes

        // Instanciando Atributos

        // Retornando view
        return view;

    }

}
