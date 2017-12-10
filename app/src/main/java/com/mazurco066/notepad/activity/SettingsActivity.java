package com.mazurco066.notepad.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mazurco066.notepad.R;

public class SettingsActivity extends AppCompatActivity {

    //Componentes
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Implementação padrão do método onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Instanciando componentes
        this.toolbar = findViewById(R.id.mainToolbar);

        //Recuperando título para toolbar
        String title = getResources().getString(R.string.action_settings);

        //Configurando Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        toolbar.setTitle(title);
        this.setSupportActionBar(toolbar);

    }

    //sobrescrevendo método de inflar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflando menu da activity
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    //Sobrescrevendo método de recuperar botão pressionado na toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Verificando qual botão foi pressionado
        switch (item.getItemId()) {

            //Botão voltar
            case R.id.action_back:
                finish();
                break;
            
        }

        //Retorno pardão do método
        return super.onOptionsItemSelected(item);
    }
}
