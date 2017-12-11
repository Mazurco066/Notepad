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
import android.widget.Toast;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.util.Preferences;

public class ThemeFragment extends Fragment {

    //Componentes
    private RadioGroup themeGroup;

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
        Button btnChangeTheme = view.findViewById(R.id.btnChangeTheme);

        // Instanciando Atributos
        this.context = view.getContext();
        preferences = new Preferences(this.context);

        //Declarando um radio button
        RadioButton radioButton;

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

            //Setando preferences caso não esteja setado
            preferences.saveTheme(R.style.DarkTheme);

        }

        //Ouvindo Eventos do botão de alterar tema
        btnChangeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Verificando qual botão está ativo
                switch (themeGroup.getCheckedRadioButtonId()) {

                    //Caso tema dark
                    case R.id.darkRadioBtn:
                        //Alterando tema nas preferencias
                        preferences.saveTheme(R.style.DarkTheme);
                        break;

                    //Caso tema light
                    case R.id.lightRadioBtn:
                        //Alterando tema nas preferencias
                        preferences.saveTheme(R.style.LightTheme);
                        break;

                    //Caso tema blue
                    case R.id.blueRadioBtn:
                        //Alterando tema nas preferencias
                        preferences.saveTheme(R.style.BlueTheme);
                        break;

                    //Caso tema red
                    case R.id.redRadioBtn:
                        //Alterando tema nas preferencias
                        preferences.saveTheme(R.style.RedTheme);
                        break;

                }

                //Retornando mensagem mandando usuário reiniciar o app para ver novo tema
                String msg = context.getResources().getString(R.string.alert_theme_change_sucess);
                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

            }

        });

        // Retornando view
        return view;

    }


}
