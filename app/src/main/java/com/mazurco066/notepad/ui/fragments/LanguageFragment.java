package com.mazurco066.notepad.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.ui.activity.SettingsActivity;
import com.mazurco066.notepad.util.Preferences;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LanguageFragment extends Fragment {

    //Componentes
    @BindView(R.id.languageRadioGroup) RadioGroup languageGroup;
    @BindView(R.id.btnChangeLanguage) Button btnChange;
    private RadioButton radioButton;

    //Atributos
    private Locale myLocale;
    private Preferences preferences;
    private Context context;

    public LanguageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_language, container, false);
        ButterKnife.bind(this, view);

        // Instanciando Atributos
        this.context = view.getContext();
        preferences = new Preferences(this.context);

        if (preferences.getLanguage() != null) {

            //Recuperando Localização salva
            String appLanguage = preferences.getLanguage();

            //Verificando qual idioma se encontra
            if (appLanguage.equals("pt")) {

                //Setando botão de idioma portugues para ficar ativo
                radioButton = view.findViewById(R.id.portugueseButton);
                radioButton.setChecked(true);

            }
            else if (appLanguage.equals("en")) {

                //Setando botão de idioma inglês para ficar ativo
                radioButton = view.findViewById(R.id.englishButton);
                radioButton.setChecked(true);

            }
            else {

                //Setando botão de idioma espanhol para ficar ativo
                radioButton = view.findViewById(R.id.spanishButton);
                radioButton.setChecked(true);

            }

        }
        else {

            //Recuperando idioma diretamente do app
            String location = getLocale();

            //Verificando idioma atual
            if (location.equals("pt_BR")) {

                //Setando botão de idioma portugues para ficar ativo
                radioButton = view.findViewById(R.id.portugueseButton);
                radioButton.setChecked(true);

            }
            else if (location.equals("en_US")) {

                //Setando botão de idioma inglês para ficar ativo
                radioButton = view.findViewById(R.id.englishButton);
                radioButton.setChecked(true);

            }
            else if (location.equals("es_ES")) {

                //Setando botão de idioma espanhol para ficar ativo
                radioButton = view.findViewById(R.id.spanishButton);
                radioButton.setChecked(true);

            }
            else {

                //Setando botão de idioma ingles para ficar ativo
                radioButton = view.findViewById(R.id.englishButton);
                radioButton.setChecked(true);

            }

        }

        //Ouvindo Eventos do botão alterar idioma
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Verificando se há um botão selecionado
                if (languageGroup.getCheckedRadioButtonId() > 0) {

                    //Recuperando botão selecionado
                    radioButton = view.findViewById(languageGroup.getCheckedRadioButtonId());

                    //Definindo string que definirá locale
                    String local = "";

                    //Verificando qual idioma foi selecionado
                    switch (languageGroup.getCheckedRadioButtonId()) {

                        //Caso lingua portuguesa
                        case R.id.portugueseButton:
                            local = "pt";
                            break;

                        //Caso lingua inglesa
                        case R.id.englishButton:
                            local = "en";
                            break;

                        //Caso lingua espanhola
                        case R.id.spanishButton:
                            local = "es";
                            break;

                    }

                    //Verificando se local não é o msm que atual
                    if (!local.equals(getLocale())) {

                        //Salvando novo idioma nas preferencias
                        preferences.saveLanguage(local);

                        //Setando novo local
                        setLocale(local);

                    }
                    else {

                        //Recuperando mensagem de erro
                        String error = getResources().getString(R.string.alert_language_same);

                        //Retornando mensagem de erro
                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    //Recuperando mensagem de erro
                    String error = getResources().getString(R.string.alert_unselected_language);

                    //Retornando mensagem de erro
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

                }

            }
        });

        // Retornando a view
        return view;

    }

    //Método para recuperar localização
    private String getLocale() {

        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        return conf.locale.toString();

    }

    //Método para alterar localização
    private void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(context.getApplicationContext(), SettingsActivity.class);
        startActivity(refresh);
        getActivity().finish();

    }

}
