package com.mazurco066.notepad.presenter;

import android.content.Context;

import com.mazurco066.notepad.SQLite.methods.ListActions;
import com.mazurco066.notepad.model.ItemList;
import com.mazurco066.notepad.model.TodoList;
import com.mazurco066.notepad.task.ListsTask;

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

    }

    @Override
    public void addItem(ItemList item) {

    }

    @Override
    public void doneItem(ItemList item) {

    }

    @Override
    public void deleteItem(ItemList item) {

    }
}
