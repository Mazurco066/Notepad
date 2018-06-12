package com.mazurco066.notepad.ui.activity;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
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

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.adapter.TaskAdapter;
import com.mazurco066.notepad.SQLite.methods.ListActions;
import com.mazurco066.notepad.model.ItemList;
import com.mazurco066.notepad.model.TodoList;
import com.mazurco066.notepad.SQLite.DatabaseCreator;
import com.mazurco066.notepad.presenter.ListsPresenter;
import com.mazurco066.notepad.task.ListsTask;
import com.mazurco066.notepad.util.Preferences;
import com.mazurco066.notepad.util.SnackUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity implements ListsTask.View {

    //Componentes
    @BindView(R.id.mainToolbar) Toolbar toolbar;
    @BindView(R.id.taskEdit) EditText taskEdit;
    @BindView(R.id.btnAddItem) Button btnAddTask;
    @BindView(R.id.todoView) ListView _todoView;
    @BindView(R.id.doneView) ListView _doneView;

    //Atributos
    private Preferences preferences;
    private ListsPresenter presenter;
    private ListActions actions;
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

        //Bindando componentes com xml
        ButterKnife.bind(this);

        //Instanciando componentes
        presenter = new ListsPresenter(this, getApplicationContext());

        //Instanciando Atributos
        this.actions = new ListActions(getApplicationContext());

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

        //Recuperando itens da lista se houver
        todoList.setItens(actions.getAllItens(todoList.getId()));

        //Verificando se há itens
        if (todoList.getItens() != null) {
            //Instanciando Arraylists
            this._todo = new ArrayList<>();
            this._done = new ArrayList<>();

            //Adicionando itens na view
            for (ItemList _item : todoList.getItens()) {
                //Verificando se é uma tarefa pendente ou concluída
                if (_item.getDone() == 1) _done.add(_item);
                else _todo.add(_item);
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
                    //Adicionando uma nova tarefa
                    final ItemList itemList = new ItemList();
                    itemList.setTask(taskEdit.getText().toString());
                    itemList.setDone(0);
                    presenter.addItem(todoList.getId(), itemList);
                }
                else {
                    SnackUtil.show(view, getString(R.string.alert_empty_fields), Snackbar.LENGTH_SHORT);
                }
            }
        });

        //Adicionando evento á lista de pendentes
        _todoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Recuperando tarefa que sera atualizada
                ItemList itemList = _todo.get(i);
                presenter.doneItem(todoList.getId(), itemList);
            }
        });

        //Adicionando evento de onlongclick na view de pendencias
        _todoView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Removendo item dos pendentes
                presenter.deleteItem(todoList.getId(), _todo.get(i));
                return true;
            }
        });

        //Adicionando evento de onlongclick na view de conclusões
        _doneView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Removendo item dos pendentes
                presenter.deleteItem(todoList.getId(), _done.get(i));
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
                presenter.shareList(todoList.getTitle(), _todo, _done, this);
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
            public void onClick(DialogInterface dialogInterface, int i) { presenter.delete(todoList); }
        });
        //Adicionando botões negativo e positivo para alertdialog
        alertDialog.setNegativeButton(getResources().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {/*Nada Acontece....*/}
        });
        //Criando e mostrando dialog
        alertDialog.create();
        alertDialog.show();

    }

    @Override
    public void onAddItemSuccess(ItemList itemList) {
        //Retornando mensagem de Sucesso
        View view = findViewById(R.id.list_activity);
        SnackUtil.show(view, getString(R.string.alert_task_added), Snackbar.LENGTH_SHORT);

        //Limpando campo de texto
        taskEdit.setText(null);

        //Atualizando View
        _todo.add(itemList);
        updateViewContent();
    }

    @Override
    public void onItemAddFailed() {
        View view = findViewById(R.id.list_activity);
        SnackUtil.show(view, getString(R.string.alert_failure), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onItemDoneSucess(ItemList itemList) {
        //Atualizando Arrays
        _todo.remove(itemList);
        itemList.setDone(1);
        _done.add(itemList);

        //Atualizando Views
        updateViewContent();
    }

    @Override
    public void onItemDoneFailed() {
        View view = findViewById(R.id.list_activity);
        SnackUtil.show(view, getString(R.string.alert_failure), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onItemDeleteSucess(ItemList itemList) {
        //Removendo da view e atualizando a mesma
        if (itemList.getDone() == 1) _done.remove(itemList);
        else _todo.remove(itemList);
        updateViewContent();

        //Retornando mensagem de sucesso
        View view = findViewById(R.id.list_activity);
        SnackUtil.show(view, getString(R.string.alert_task_deleted), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onDeleteItemFailed() {
        View view = findViewById(R.id.list_activity);
        SnackUtil.show(view, getString(R.string.alert_failure), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onDeleteSuccess() {
        setResult(1010);
        finish();
    }

    @Override
    public void onDeleteFailed() {
        View view = findViewById(R.id.list_activity);
        SnackUtil.show(view, getString(R.string.alert_failure), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onShareFailed() {
        View view = findViewById(R.id.list_activity);
        SnackUtil.show(view, getString(R.string.alert_empty_shared_field), Snackbar.LENGTH_SHORT);
    }

}
