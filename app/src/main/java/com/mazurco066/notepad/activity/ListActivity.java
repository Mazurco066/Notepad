package com.mazurco066.notepad.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.dao.ListDAO;
import com.mazurco066.notepad.model.TodoList;
import com.mazurco066.notepad.util.DatabaseCreator;
import com.mazurco066.notepad.util.Preferences;

public class ListActivity extends AppCompatActivity {

    //Attributes
    private Toolbar toolbar;
    private EditText taskEdit;
    private Button btnAddTask;

    private Preferences preferences;
    private ListDAO dao;
    private TodoList todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Implementação padrão do método
        super.onCreate(savedInstanceState);

        //Verificando tema
        setSettingsTheme();

        //Instanciando componentes
        this.toolbar = findViewById(R.id.mainToolbar);
        this.taskEdit = findViewById(R.id.taskEdit);
        this.btnAddTask = findViewById(R.id.btnAddItem);

        //Instanciando Atributos
        this.dao = new ListDAO(getApplicationContext());

        //Recuperando dados passados por Intent
        Bundle data = getIntent().getExtras();
        if (data != null) {

            todoList = new TodoList();
            todoList.setId(data.getInt(DatabaseCreator.FIELD_ID));
            todoList.setTitle(data.getString(DatabaseCreator.FIELD_TITLE));
        }

        //Configurando ToolBar
        toolbar.setTitle(todoList.getTitle());
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        this.setSupportActionBar(toolbar);

        //Toast.makeText(getApplicationContext(), "ID: " + todoList.getId(), Toast.LENGTH_SHORT).show();

        //Recuperando itens da lista se houver
        todoList.setItens(dao.getAllItens(todoList.getId()));

        Toast.makeText(getApplicationContext(), "ID: " + todoList.getId(), Toast.LENGTH_SHORT).show();

        //Verificando se há itens
        if (todoList.getItens() != null) {

            //Adicionando itens nas views

        }

        //Adicionando evento ao botãoAdicionar Tarefa
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


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
