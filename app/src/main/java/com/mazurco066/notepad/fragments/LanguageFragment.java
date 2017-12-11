package com.mazurco066.notepad.fragments;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mazurco066.notepad.R;

public class LanguageFragment extends Fragment {

    //Componentes
    private RadioGroup languageGroup;
    private RadioButton radioButton;
    private Button btnChange;

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
        this.btnChange = view.findViewById(R.id.btnChangeLanguage);

        // Instanciando Atributos
        this.res = view.getResources();

        //Ouvindo Eventos do botão alterar idioma
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // Retornando a view
        return view;

    }

}
