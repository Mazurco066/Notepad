package com.mazurco066.notepad.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.mazurco066.notepad.R;

public class Preferences {

    //Atributos
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String FILE_NAME = "app-preferences";
    private final int MODE = 0;

    //Keys
    private final String LANGUAGE = "app-language";
    private final String THEME = "app-theme";

    //Construtor padrão
    public Preferences (Context context) {

        //Instanciando Atributos
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(FILE_NAME, MODE);
        editor = sharedPreferences.edit();
    }

    //Método para sallvar idioma nas preferencias
    public void saveLanguage(String language) {

        //Colocando idioma nas preferencias do app
        editor.putString(LANGUAGE, language);
        editor.commit();
    }

    //Método para salvar tema ativo nas preferencias
    public void saveTheme(int theme) {

        //Colocando tema nas preferencias do app
        editor.putInt(THEME, theme);
        editor.commit();
    }

    //Método para recuperar idioma salvo
    public String getLanguage() {

        //Retornando idioma salvo
        return sharedPreferences.getString(LANGUAGE, null);
    }

    //Método para recuperar tema ativo nas preferencias
    public int getTheme() {

        //Retornando Tema ativo
        return sharedPreferences.getInt(THEME, R.style.DarkTheme);
    }

}
