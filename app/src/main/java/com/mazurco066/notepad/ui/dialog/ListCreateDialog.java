package com.mazurco066.notepad.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.model.TodoList;

public class ListCreateDialog extends AlertDialog.Builder {

    //Definindo atributos
    private ListCreateDialogCallback callback;

    //Definindo interface para definição dos eventos
    public interface ListCreateDialogCallback {
        void onPositiveClicked(TodoList list);
        void onNegativeClicked();
        void onEmptyFields();
    }

    public ListCreateDialog(Context context, final ListCreateDialogCallback callback) {

        //Implementação padrão
        super(context);

        //Recuperando instancias
        this.callback = callback;

        final EditText input = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        //Customizando a dialog
        setTitle(context.getString(R.string.dialog_list_create_title))
                .setMessage(context.getString(R.string.dialog_list_create_content))
                .setView(input)
                .setPositiveButton(context.getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!input.getText().toString().isEmpty()) {
                            TodoList list = new TodoList();
                            list.setId(-1);
                            list.setTitle(input.getText().toString());
                            callback.onPositiveClicked(list);
                        }
                        else callback.onEmptyFields();
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
