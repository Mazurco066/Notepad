package com.mazurco066.notepad.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.adapter.TaskAdapter;
import com.mazurco066.notepad.SQLite.methods.ListActions;
import com.mazurco066.notepad.model.ItemList;
import com.mazurco066.notepad.model.TodoList;
import com.mazurco066.notepad.SQLite.DatabaseCreator;
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
    private ListActions dao;
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
        this.dao = new ListActions(getApplicationContext());

        //Recuperando dados passados por Intent
        final Bundle data = getIntent().getExtras();
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
                    final ItemList itemList = new ItemList();
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
                    itemList.setDone(1);
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

        //Adicionando evento de onlongclick na view de pendencias
        _todoView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Deletando tarefa selecionada
                removeTask(_todo.get(i));

                //Retorno padrão
                return true;

            }
        });

        //Adicionando evento de onlongclick na view de conclusões
        _doneView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Deletando tarefa selecionada
                removeTask(_done.get(i));

                //Retorno padrão
                return false;
            }
        });

    }

    //Sobrescrevendo método on Create do menu para carrega menu customizado
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflando menu na activity
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_menu, menu);
        return  true;
    }

    //Controlador do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Verificando qual botão foi pressionado
        switch (item.getItemId()) {

            //Caso botão deletar
            case R.id.action_delete:
                confirmDelete(todoList);
                break;

            //Caso botão compartilhar
            case R.id.action_share:
                shareList();
                break;

            //Caso botão cancelar
            case R.id.action_back:
                finish();
                break;

        }

        //Retorno padrão da implementação normal
        return super.onOptionsItemSelected(item);

    }

    //Sobrescrevendo método onResume para atualizar a lista
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
            String msg = getResources().getString(R.string.alert_task_added);
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

    //Método para remover uma tarefa
    private void removeTask(ItemList itemList) {

        //Removendo
        if (dao.deleteTask(todoList.getId(), itemList.getTask())) {

            //Removendo da view e atualizando a mesma
            if (itemList.getDone() == 1) _done.remove(itemList);
            else _todo.remove(itemList);
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

    //Método para confirmar exclusão de uma lista
    private void confirmDelete(final TodoList todoList) {

        //Instanciando criador de alert dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListActivity.this);

        //Configurando alert dialog
        alertDialog.setTitle(getResources().getString(R.string.dialog_list_delete_title));
        alertDialog.setMessage(getResources().getString(R.string.dialog_list_delete_content));
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton(getResources().getString(R.string.dialog_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Recuperando mensagens
                String sucess = getResources().getString(R.string.alert_delete_list_sucess);
                String failure = getResources().getString(R.string.alert_failure);

                if (dao.deleteList(todoList)) {

                    //Retornando mensagem de sucesso ao usuário
                    Toast.makeText(ListActivity.this, sucess, Toast.LENGTH_SHORT).show();

                    //Finalizando Activity
                    finish();

                }
                else {

                    //Retornando mensagem de erro ao deletar lista para usuário
                    Toast.makeText(ListActivity.this, failure, Toast.LENGTH_SHORT).show();

                }
            }
        });

        //Adicionando botões negativo e positivo para alertdialog
        alertDialog.setNegativeButton(getResources().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Nada Acontece....
            }
        });

        //Criando e mostrando dialog
        alertDialog.create();
        alertDialog.show();

    }

    //Método para compartilhar nota via redes sociais
    private void shareList() {

        //Verificando se há conteúdo para se compartilhar na lista
        if (this._todo != null || this._done != null) {

            //Montando estrutura da lista para compartilhamento em texto
            StringBuilder finalList = new StringBuilder();
            finalList.append(todoList.getTitle()).append("\n\n");
            finalList.append(getResources().getString(R.string.label_todo)).append("\n\n");
            if (_todo != null) for (ItemList _item: _todo) {
                finalList.append("-> ").append(_item.getTask()).append("\n");
            }
            finalList.append("\n");
            finalList.append(getResources().getString(R.string.label_done)).append("\n\n");
            if (_done != null) for (ItemList _item: _done) {
                finalList.append("-> ").append(_item.getTask()).append("\n");
            }
            finalList.append("\nNotepad App - by Gabriel Mazurco");

            //Recuperando conteúdo a ser compartilhado
            String content = finalList.toString();

            //Instanciando intent para compartilhamento
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, content);
            shareIntent.setType("text/plain");
            startActivity(shareIntent);



        }
        else {

            //Recuperando mensagem para emitir no alerta
            String error = getResources().getString(R.string.alert_empty_shared_field);

            //Emitindo Alerta para usuário
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();

        }

    }

}
