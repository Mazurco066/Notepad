package com.mazurco066.notepad.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.adapter.MainAdapter;
import com.mazurco066.notepad.adapter.NoteAdapter;
import com.mazurco066.notepad.dao.NoteDAO;
import com.mazurco066.notepad.model.Note;
import com.mazurco066.notepad.util.DatabaseCreator;
import com.mazurco066.notepad.util.Preferences;
import com.mazurco066.notepad.util.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Definindo os Componentes
    private Toolbar toolbar;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;

    //Definindo Atributos
    private Preferences preferences;

    private boolean settingsOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Implementação padrão do método onCreate
        super.onCreate(savedInstanceState);

        //Verificando tema e linguagem
        setSettingsTheme();
        setLanguageTheme();

        //Instanciando os Componentes
        toolbar = findViewById(R.id.mainToolbar);
        viewPager = findViewById(R.id.notesViewPager);
        slidingTabLayout = findViewById(R.id.slidingTabNotes);

        //Definindo Toolbar a ser usada na activity
        this.setSupportActionBar(toolbar);

        //Configurando Adapter/ViewPager
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(mainAdapter);

        //Configurando o SlidingTabs
        slidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.viewTextTab);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent));
        slidingTabLayout.setViewPager(viewPager);

    }

    //Sobrescrevendo método de ao retomar a activity
    @Override
    protected void onResume() {

        //Implementação padrão do método de onResume do ciclo de vida
        super.onResume();

        //Verficando se há temas novos a serem aplicados
        if (settingsOpened) {

            //Retornando boolena a estado falso se bem que n é necessário pois outra activity sera instanciada
            settingsOpened = false;

            //Reiniciando activity
            Intent main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(main);
            finish();

        }

    }

    //Sobrescrevendo método de inflar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflando menu no app
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    //Sobrescrevento método de capturar ações feitas na toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Verificando qual item de menu foi pressionado
        switch (item.getItemId()) {

            //Criar nova nota
            case R.id.action_write:
                writeNote();
                break;

            //Configurações do app
            case R.id.action_settings:
                openSettingsActivity();
                break;

            //Sair do App
            case R.id.action_exit:
                finish();
                break;

        }

        //Retornando retorno padrão do método implementado na superclasse
        return super.onOptionsItemSelected(item);

    }

    //Método para Abrir Activity de Escrever/Visualizar Nota
    private void openNoteActivity(Note note) {

        //Instanciando intent para ir para prox activity
        Intent noteActivity = new Intent(getApplicationContext(), NoteActivity.class);
        noteActivity.putExtra(DatabaseCreator.FIELD_ID, note.getId());
        noteActivity.putExtra(DatabaseCreator.FIELD_DATE, note.getDate());
        noteActivity.putExtra(DatabaseCreator.FIELD_TITLE, note.getTitle());
        noteActivity.putExtra(DatabaseCreator.FIELD_CONTENT, note.getContent());

        //Iniciando nova activity
        startActivity(noteActivity);

    }

    //Método que será responsável por executar as ações de recuperar dados de uma nota
    private void writeNote() {

        //Instanciando uma nova nota com id de não salva para validação
        Note note = new Note();
        note.setId(-1);

        //Abrindo Activity de edição para nova nota
        openNoteActivity(note);

    }

    //Método para abrir activity de configurações
    private void openSettingsActivity() {

        //Notificando essa activity que usuário foi para activity de config
        settingsOpened = true;

        //Instanciando intent e indo para settings
        Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(settings);

    }

    //Método para adaptar tema do app
    private void setSettingsTheme() {

        this.preferences = new Preferences(getApplicationContext());

        if (preferences.getTheme() != R.style.DarkTheme) {

            this.setTheme(preferences.getTheme());
        }
        setContentView(R.layout.activity_main);

    }

    //Método para adaptar linguagem dep app
    private void setLanguageTheme() {

        //Instanciando um novo objeto para recuperar preferencias
        this.preferences = new Preferences(getApplicationContext());

        //Verificando se ja foi configurado um idioma
        if (this.preferences.getLanguage() != null) {

            //Recuperando local
            String local = this.preferences.getLanguage();

            //Recuperando local e o configurando
            Locale myLocale = new Locale(local);
            Resources res = getApplicationContext().getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);

        }

    }

}