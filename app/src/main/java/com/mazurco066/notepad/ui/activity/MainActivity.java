package com.mazurco066.notepad.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.SQLite.methods.ListActions;
import com.mazurco066.notepad.model.Note;
import com.mazurco066.notepad.model.TodoList;
import com.mazurco066.notepad.SQLite.DatabaseCreator;
import com.mazurco066.notepad.ui.fragments.ListsFragment;
import com.mazurco066.notepad.ui.fragments.NotesFragment;
import com.mazurco066.notepad.ui.fragments.SettingsFragment;
import com.mazurco066.notepad.util.Preferences;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //Definindo os Componentes
    @BindView(R.id.mainToolbar) Toolbar toolbar;
    @BindView(R.id.appDrawer) DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;

    //Definindo Atributos
    private Preferences preferences;

    //Definindo constantes
    public static String NOTIFICATION_ID = "notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Implementação padrão do método onCreate
        super.onCreate(savedInstanceState);

        //Verificando tema e linguagem
        setSettingsTheme();
        setLanguageTheme();

        //Interligando componentes com xml
        ButterKnife.bind(this);


        //Instanciando componentes
        setSupportActionBar(toolbar);
        mToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        );

        //Configurando drawer
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //Configurando Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Definindo Toolbar a ser usada na activity
        this.setSupportActionBar(toolbar);

        //Definindo evento da navigation para controlar ações do menu
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //Definindo objetos para manipular fragments
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft;

                //Verificando qual item foi selecionado
                switch (item.getItemId()) {

                    //Criar nova nota
                    case R.id.item_new_note:
                        writeNote();
                        break;

                    //Criar nova lista
                    case R.id.item_new_list:
                        writeList();
                        break;

                    //Acessar lista de notas
                    case R.id.item_notes:
                        ft = fm.beginTransaction();
                        ft.replace(R.id.content_frame, new NotesFragment());
                        ft.commit();
                        mDrawerLayout.closeDrawers();
                        break;

                    //Acessar lista de listas
                    case R.id.item_lists:
                        ft = fm.beginTransaction();
                        ft.replace(R.id.content_frame, new ListsFragment());
                        ft.commit();
                        mDrawerLayout.closeDrawers();
                        break;

                    //Acessar menu de configurações
                    case R.id.item_settings:
                        ft = fm.beginTransaction();
                        ft.add(R.id.content_frame, new SettingsFragment());
                        ft.commit();
                        mDrawerLayout.closeDrawers();
                        break;

                    //Sair do app
                    case  R.id.item_exit:
                        finish();
                        break;

                    //Nenhuma das opções
                    default:
                        //Nada acontece
                }

                //Retornando falso
                return false;
            }
        });

        //Definindo fragmento inicial
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.content_frame, new NotesFragment());
        ft.commit();

        //Verificando se há notificações a ser lançadas
        Bundle data = getIntent().getExtras();
        if (data != null) {
            //Recuperando view para disparar snackbar
            View view = findViewById(R.id.appDrawer);
            //Verificando qual notificação precisa ser disparada
            switch (data.getInt(NOTIFICATION_ID)) {
                //Configurações alteradas
                case 100:
                    Snackbar snackbar = Snackbar.make(view, "Configurações alteradas", Snackbar.LENGTH_SHORT);
                    //Customizando a snackbar
                    TextView snackActionView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                    snackActionView.setTextColor(Color.WHITE);
                    snackActionView.setTextSize(16);
                    snackbar.getView().setBackground(getDrawable(R.drawable.themed_snackbar));
                    //Mostrando a snackbar
                    snackbar.show();
                    break;
            }
        }
    }

    //Sobrescrevendo método de inflar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflando menu no app
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        //Usando implementação padrão ao inflar menu
        return super.onCreateOptionsMenu(menu);

    }

    //Sobrescrevento método de capturar ações feitas na toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Verificando se foi pressionado botão toggle
        if (mToggle.onOptionsItemSelected(item)) return true;

        //Verificando qual item de menu foi pressionado
        switch (item.getItemId()) {

            //Criar nova nota
            case R.id.action_write:
                writeNote();
                break;

            //Criar nova lista
            case R.id.action_newList:
                writeList();
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

    //Método para Abrir Activity de Escrever/Visualizar Nota
    private void openListActivity(TodoList todoList) {

        //Verificando se é uma lista existente ou nova
        if (todoList.getId() == -1) {   //Se for nova

            //Instanciando DAO de lista para inserir nova lista
            ListActions dao = new ListActions(getApplicationContext());

            //Inserindo no banco nova lista
            if (dao.createList(todoList.getTitle(), todoList.getDate())){

                //Recuperando id do ultimo autoincrement
                todoList.setId(dao.getLastListId());

                //Abrindo Activity de Edição de listas para adicionar ou remover
                Intent listActivity = new Intent(getApplicationContext(), ListActivity.class);
                listActivity.putExtra(DatabaseCreator.FIELD_ID, todoList.getId());
                listActivity.putExtra(DatabaseCreator.FIELD_TITLE, todoList.getTitle());

                startActivity(listActivity);

            }
            else {

                //Recuperando mensagem de erro
                String msg = getResources().getString(R.string.alert_failure);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

            }

        }

    }

    //Método que será responsável por executar as ações de recuperar dados de uma nota
    private void writeNote() {

        //Instanciando uma nova nota com id de não salva para validação
        Note note = new Note();
        note.setId(-1);

        //Abrindo Activity de edição para nova nota
        openNoteActivity(note);

    }

    //Método que será responsável por executar as ações de recuperar dados de uma lista
    private void writeList() {

        //Abrindo um dialog para recolher título da lista
        getTodoListTitle();

    }

    //Método para recuperar nome da lista que usuário deseja criar
    private void getTodoListTitle() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        //Configurando a AlertDialog
        builder.setTitle(getResources().getString(R.string.dialog_list_create_title));
        builder.setMessage(getResources().getString(R.string.dialog_list_create_content));
        builder.setCancelable(false);

        //Criando EditText para coleta do título
        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        //Setando botões de avançar ou cancelar
        builder.setPositiveButton(getResources().getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (!input.getText().toString().isEmpty()) {

                    //Recuperando texto digitado
                    String title = input.getText().toString();

                    //Instanciando nova Lista para insersão
                    TodoList todoList = new TodoList();
                    todoList.setId(-1);
                    todoList.setTitle(title);

                    //Validando e se possível partindo para activity de listas
                    openListActivity(todoList);

                }
                else {

                    //Retornando Mensagem de erro
                    String msg = getApplicationContext().getResources().getString(R.string.alert_empty_fields);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                }
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Nada Acontece.....
            }
        });

        //Criando e mostrando a Dialog
        builder.create();
        builder.show();

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