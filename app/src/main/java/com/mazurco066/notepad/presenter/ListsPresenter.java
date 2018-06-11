package com.mazurco066.notepad.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.SQLite.methods.ListActions;
import com.mazurco066.notepad.model.ItemList;
import com.mazurco066.notepad.model.TodoList;
import com.mazurco066.notepad.task.ListsTask;

import java.util.List;

public class ListsPresenter implements ListsTask.Presenter {

    //Definindo view que sera manipulada
    ListsTask.View view;

    //Definindo objeto para manipulação de dados no banco
    ListActions actions;

    //Definindo  um construtor para o presenter
    public  ListsPresenter(ListsTask.View view, Context context) {
        this.actions = new ListActions(context);
        this.view = view;
    }

    @Override
    public void delete(TodoList list) {
        //Tentando deletar uma lista
        if (actions.deleteList(list)) view.onDeleteSuccess();
        else view.onDeleteFailed();
    }

    @Override
    public void addItem(int id,ItemList item) {
        //Tentando adicionar item a lista
        if (actions.AddItem(id, item)) view.onAddItemSuccess(item);
        else view.onItemAddFailed();
    }

    @Override
    public void doneItem(int id, ItemList item) {
        //Tentando adicionar tarefa a lista
        if (actions.setDone(id, item.getTask())) view.onItemDoneSucess(item);
        else view.onItemDoneFailed();
    }

    @Override
    public void deleteItem(int id, ItemList item) {
        //Tentando remover item da lista
        if (actions.deleteTask(id, item.getTask())) view.onItemDeleteSucess(item);
        else view.onDeleteItemFailed();
    }

    @Override
    public void shareList(String title, List<ItemList> todo, List<ItemList> done, Context context) {
        //Verificando se há conteúdo para se compartilhar na lista
        if (!todo.isEmpty() || !done.isEmpty()) {

            //Montando estrutura da lista para compartilhamento em texto
            StringBuilder finalList = new StringBuilder();
            finalList.append(title).append("\n\n");
            finalList.append(context.getString(R.string.label_todo)).append("\n\n");
            if (!todo.isEmpty()) for (ItemList _item: todo) {
                finalList.append("-> ").append(_item.getTask()).append("\n");
            }
            finalList.append("\n");
            finalList.append(context.getString(R.string.label_done)).append("\n\n");
            if (!done.isEmpty()) for (ItemList _item: done) {
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
            context.startActivity(shareIntent);
        }
        else { view.onShareFailed(); }
    }
}
