package com.mazurco066.notepad.fragments;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.mazurco066.notepad.R;

public class LanguageFragment extends Fragment {

    //Componentes
    private RadioGroup languageGroup;

    //Atributos
    Resources res;

    public LanguageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_language, container, false);

        // Instanciando Componentes
        this.languageGroup = view.findViewById(R.id.languageRadioGroup);

        // Instanciando Atributos
        this.res = view.getResources();

        // Retornando a view
        return view;

    }

}
