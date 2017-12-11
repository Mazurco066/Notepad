package com.mazurco066.notepad.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    //Atributos
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String FILE_NAME = "app-preferences";
    private final int MODE = 0;

    //Keys
    private final String LANGUAGE = "app-language";

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

    //Método para recuperar idioma salvo
    public String getLanguage() {

        //Retornando idioma salvo
        return sharedPreferences.getString(LANGUAGE, null);
    }

}
