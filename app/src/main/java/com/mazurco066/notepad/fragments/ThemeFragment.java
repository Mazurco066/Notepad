package com.mazurco066.notepad.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.util.Preferences;

public class ThemeFragment extends Fragment {

    //Componentes
    private RadioGroup themeGroup;
    private RadioButton radioButton;
    private Button btnChangeTheme;

    //Atributos
    private Preferences preferences;
    private Context context;

    public ThemeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_theme, container, false);

        // Instanciando Componentes
        themeGroup = view.findViewById(R.id.themeRadioGroup);
        btnChangeTheme = view.findViewById(R.id.btnChangeTheme);

        // Instanciando Atributos
        this.context = view.getContext();
        preferences = new Preferences(this.context);

        //Verificando se usuário está com tema padrão ou personalizado
        if (preferences.getTheme() != R.style.DarkTheme) {

            //Recuperando tema Ativo
            switch (preferences.getTheme()) {

                //Caso tema light
                case R.style.LightTheme:
                    radioButton = view.findViewById(R.id.lightRadioBtn);
                    radioButton.setChecked(true);
                    break;

                //Caso tema blue
                case R.style.BlueTheme:
                    radioButton = view.findViewById(R.id.blueRadioBtn);
                    radioButton.setChecked(true);
                    break;

                //Caso tema red
                case R.style.RedTheme:
                    radioButton = view.findViewById(R.id.redRadioBtn);
                    radioButton.setChecked(true);
                    break;
            }
        }
        else {

            //Setando botão do tema padrão para ficar ativo
            radioButton = view.findViewById(R.id.darkRadioBtn);
            radioButton.setChecked(true);

        }

        //Ouvindo Eventos do botão de alterar tema
        btnChangeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setActiveTheme();

            }
        });

        // Retornando view
        return view;

    }

    //Método para recuperar tema que está ativo
    private String getActiveTheme() {

        //Retornando nome do tema ativo
        String theme = this.context.getTheme().toString();
        return theme;
    }

    //Método para alterar tema do app
    private void setActiveTheme() {

        preferences.saveTheme(R.style.LightTheme);

    }

}
