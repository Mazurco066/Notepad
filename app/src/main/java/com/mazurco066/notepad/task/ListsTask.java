package com.mazurco066.notepad.task;

import android.content.Context;

import com.mazurco066.notepad.model.ItemList;
import com.mazurco066.notepad.model.TodoList;

import java.util.List;

public interface ListsTask {

    //Definindo presenters a ser implementados
    interface Presenter {
        void delete(TodoList list);
        void addItem(int id, ItemList item);
        void doneItem(int id, ItemList item);
        void deleteItem(int id, ItemList item);
        void shareList(String title, List<ItemList> todo, List<ItemList> done, Context context);
    }

    //Definindo métodos que a view deve implementar
    interface View {
        //Fluxos de sucesso
        void onAddItemSuccess(ItemList itemList);
        void onItemDoneSucess(ItemList itemList);
        void onItemDeleteSucess(ItemList itemList);
        void onDeleteSuccess();
        //Fluxos de fracasso
        void onDeleteFailed();
        void onItemAddFailed();
        void onItemDoneFailed();
        void onDeleteItemFailed();
        void onShareFailed();
    }
}
