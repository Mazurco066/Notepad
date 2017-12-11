package com.mazurco066.notepad.activity;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.adapter.TabAdapter;
import com.mazurco066.notepad.util.Preferences;
import com.mazurco066.notepad.util.SlidingTabLayout;

public class SettingsActivity extends AppCompatActivity {

    //Componentes
    private Toolbar toolbar;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;

    //Atributos
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Implementação padrão do método onCreate
        super.onCreate(savedInstanceState);

        //Verificando Tema
        setSettingsTheme();

        //Instanciando componentes
        this.toolbar = findViewById(R.id.mainToolbar);
        this.viewPager = findViewById(R.id.mainViewPager);
        this.slidingTabLayout = findViewById(R.id.slidingTab);

        //Recuperando título para toolbar
        String title = getResources().getString(R.string.action_settings);

        //Configurando Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        toolbar.setTitle(title);
        this.setSupportActionBar(toolbar);

        //Configurando Adapter, ViewPager
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(),getApplicationContext());
        viewPager.setAdapter(tabAdapter);

        //Configurando o SlidingTab
        slidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.viewTextTab);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent));
        slidingTabLayout.setViewPager(viewPager);

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

    //Método para adaptar tema do app
    private void setSettingsTheme() {

        this.preferences = new Preferences(getApplicationContext());

        if (preferences.getTheme() != R.style.DarkTheme) {

            this.setTheme(preferences.getTheme());
        }
        setContentView(R.layout.activity_settings);

    }

}
