package com.mazurco066.notepad.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.model.TodoList;

public class ListDeleteDialog extends AlertDialog.Builder {

    //Definindo atributos
    private TodoList list;
    private ListDialogCallback callback;

    //Definindo interface para definição dos eventos
    public interface ListDialogCallback {
        void onPositiveClicked(TodoList list);
        void onNegativeClicked();
    }

    //Definindo construtor para dialog
    public ListDeleteDialog(Context context, final TodoList list, final ListDialogCallback callback) {

        //Implementação padrão do construtor
        super(context);

        //Recuperando instancias
        this.list = list;
        this.callback = callback;

        //Customizando a dialog
        setTitle(context.getString(R.string.dialog_list_delete_title))
                .setMessage(context.getString(R.string.dialog_list_delete_content))
                .setPositiveButton(context.getString(R.string.dialog_delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onPositiveClicked(list);
                    }
                })
                .setNegativeButton(context.getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onNegativeClicked();
                    }
                })
                .setCancelable(false);
    }
}
