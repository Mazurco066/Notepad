package com.mazurco066.notepad.task;

import com.mazurco066.notepad.model.ItemList;
import com.mazurco066.notepad.model.TodoList;

public interface ListsTask {

    //Definindo presenters a ser implementados
    interface Presenter {
        void delete(TodoList list);
        void addItem(ItemList item);
        void doneItem(ItemList item);
        void deleteItem(ItemList item);
    }

    //Definindo métodos que a view deve implementar
    interface View {
        //Fluxos de sucesso
        void onAddItemSuccess();
        void onItemDoneSucess();
        void onDeleteSuccess();
        //Fluxos de fracasso
        void onCreateFailed();
        void onDeleteFailed();
        void onItemDoneFailed();
        void onDeleteItemFailed();
    }
}
