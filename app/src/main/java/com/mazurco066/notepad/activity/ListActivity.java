package com.mazurco066.notepad.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.util.Preferences;

public class ListActivity extends AppCompatActivity {

    //Attributes
    private Toolbar toolbar;

    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Implementação padrão do método
        super.onCreate(savedInstanceState);

        //Verificando tema
        setSettingsTheme();

        //Instanciando componentes
        this.toolbar = findViewById(R.id.mainToolbar);

        //Configurando ToolBar
        String title = getResources().getString(R.string.activity_create_list);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        this.setSupportActionBar(toolbar);

    }

    //Método para adaptar tema do app
    private void setSettingsTheme() {

        this.preferences = new Preferences(getApplicationContext());

        if (preferences.getTheme() != R.style.DarkTheme) {

            this.setTheme(preferences.getTheme());
        }
        setContentView(R.layout.activity_list);

    }
}
