package com.mazurco066.notepad.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.adapter.TaskAdapter;
import com.mazurco066.notepad.dao.ListDAO;
import com.mazurco066.notepad.model.ItemList;
import com.mazurco066.notepad.model.TodoList;
import com.mazurco066.notepad.util.DatabaseCreator;
import com.mazurco066.notepad.util.Preferences;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    //Attributes
    private Toolbar toolbar;
    private EditText taskEdit;
    private Button btnAddTask;
    private ListView _todoView;
    private ListView _doneView;

    private Preferences preferences;
    private ListDAO dao;
    private TodoList todoList;
    private List<ItemList> _todo;
    private List<ItemList> _done;

    //Adapters
    private ArrayAdapter<ItemList> _todoAdapter;
    private ArrayAdapter<ItemList> _doneAdapter;

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
        this._todoView = findViewById(R.id.todoView);
        this._doneView = findViewById(R.id.doneView);

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

        //Verificando se há itens
        if (todoList.getItens() != null) {

            //Instanciando Arraylists
            this._todo = new ArrayList<>();
            this._done = new ArrayList<>();

            //Adicionando itens na view
            for (ItemList _item : todoList.getItens()) {

                //Verificando se é uma tarefa pendente ou concluída
                if (_item.getDone() == 1) {

                    _done.add(_item);
                }
                else {

                    _todo.add(_item);
                }

            }

        }
        else {

            //Instanciando Arraylists
            this._todo = new ArrayList<>();
            this._done = new ArrayList<>();

        }

        //Adicionando evento ao botão Adicionar Tarefa
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Verificando se os campos não estão vazios
                if (isNotEmptyFields()) {

                    //Instanciando uma nova tarefa e a populando
                    ItemList itemList = new ItemList();
                    itemList.setTask(taskEdit.getText().toString());

                        //Adicionando tarefa
                        addTask(itemList);

                }
                else {

                    //Retornando mensagem de Validação
                    String msg = getResources().getString(R.string.alert_empty_fields);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                }

            }
        });

        //Adicionando evento á lista de pendentes
        _todoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Recuperando tarefa que sera atualizada
                ItemList itemList = _todo.get(i);

                //Tentando Atualizar dados sobre a tarefa
                if (dao.setDone(todoList.getId(), itemList.getTask())) {

                    //Atualizando Arrays
                    _todo.remove(itemList);
                    _done.add(itemList);

                    //Atualizando Views
                    updateViewContent();

                }
                else {

                    //Retornando mensagem de Erro
                    String msg = getResources().getString(R.string.alert_failure);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @Override
    protected void onResume() {

        //Implementação padrão
        super.onResume();

        //Atualizando Listas
        updateViewContent();

    }

    //Método para atualizar conteúdo das view
    private void updateViewContent() {

        //Verificando se há itens para exibir na aba de pendentes
        if (this._todo != null) {

            //Atualizando lista de pendentes
            this._todoAdapter = new TaskAdapter(getApplicationContext(), _todo);
            this._todoView.setAdapter(_todoAdapter);

        }
        else {

            //Criando uma liista vazia
            this._todo = new ArrayList<>();
            this._todoAdapter = new TaskAdapter(getApplicationContext(), _todo);
            this._todoView.setAdapter(_todoAdapter);

        }

        //Verificando se há itens para exibir na aba de concluídos
        if (this._done != null) {

            //Atualizando lista de pendentes
            this._doneAdapter = new TaskAdapter(getApplicationContext(), _done);
            this._doneView.setAdapter(_doneAdapter);

        }
        else {

            //Criando uma liista vazia
            this._done = new ArrayList<>();
            this._doneAdapter = new TaskAdapter(getApplicationContext(), _done);
            this._doneView.setAdapter(_doneAdapter);

        }

    }

    //Método para validar adição de tarefas
    private boolean isNotEmptyFields() {

        return !(this.taskEdit.getText().toString().isEmpty());
    }

    //Método para adicionar itens na lista
    private void addTask(ItemList itemList) {

        //Definindo tarefa como pendende
        itemList.setDone(0);

        //Inserindo Tarefa no banco
        if (this.dao.AddItem(todoList.getId(), itemList)) {

            //Retornando mensagem de Sucesso
            String msg = getResources().getString(R.string.alert_create_note_sucess);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

            //Limpando campo de texto
            taskEdit.setText(null);

            //Atualizando View
            _todo.add(itemList);
            updateViewContent();

        }
        else {

            //Retornando mensagem de Erro
            String msg = getResources().getString(R.string.alert_failure);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

        }

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
