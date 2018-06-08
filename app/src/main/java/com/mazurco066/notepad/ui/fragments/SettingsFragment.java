package com.mazurco066.notepad.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.ui.activity.MainActivity;
import com.mazurco066.notepad.util.Preferences;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends Fragment {

    //Componentes
    @BindView(R.id.languageRadioGroup) RadioGroup languageGroup;
    @BindView(R.id.themeRadioGroup) RadioGroup themeGroup;
    @BindView(R.id.btnSaveSettings) Button btnSaveSettings;
    private RadioButton radioButton;

    //Atributos
    private Locale myLocale;
    private Preferences preferences;
    private Context context;

    public SettingsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflando o layout ao fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        // Instanciando Atributos
        this.context = view.getContext();
        preferences = new Preferences(this.context);

        //Verificando se ja há preferencias de tema ou idioma
        verifyLanguage(view);
        verifyTheme(view);

        //Definindo evento de click para salvar configurações
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Salvando configurações referentes a idioma e tema
                saveLanguage(view);
                saveTheme();

                //Retornando mensagem de sucesso ao usuário e atualizando view
                Snackbar.make(
                        view,
                        getResources().getString(R.string.alert_settings_changed),
                        Snackbar.LENGTH_SHORT).show();
                refresh();
            }
        });

        //Retornando view inflada
        return view;
    }

    //Método para verificar se já existe preferencia de tema
    private void verifyTheme(View view) {

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
    }

    //Métdo para verificar se ja existe preferência de idioma
    private void verifyLanguage(View view) {

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
    }

    //Método para salvar configurações de tema
    private void saveTheme() {

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
    }

    //Método para salvar configurações de idioma
    private void saveLanguage(View view) {

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
        }
    }

    //Método para recuperar localização
    private String getLocale() {
        //Recuperando local do dispositivo
        Resources res = context.getResources();
        Configuration conf = res.getConfiguration();
        return conf.locale.toString();
    }

    //Método para alterar localização
    private void setLocale(String lang) {
        //Alterando local do idioma
        myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    //Método para atualizar activity
    private void refresh() {
        //Instanciando intent da msm acitivity
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}
