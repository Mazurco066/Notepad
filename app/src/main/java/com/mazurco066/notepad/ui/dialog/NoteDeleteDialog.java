package com.mazurco066.notepad.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.model.Note;

public class NoteDeleteDialog extends AlertDialog.Builder {

    //Definindo atributos
    private Note note;
    private NoteDialogCallback callback;

    //Definindo interface para definição dos eventos
    public interface NoteDialogCallback {
        void onPositiveClicked(Note note);
        void onNegativeClicked();
    }

    //Definindo construtor para dialog
    public NoteDeleteDialog(Context context, final Note note, final NoteDialogCallback callback) {

        //Implementação padrão do construtor
        super(context);

        //Recuperando instancias
        this.note = note;
        this.callback = callback;

        //Customizando a dialog
        setTitle(context.getString(R.string.dialog_note_delete_title))
                .setMessage(context.getString(R.string.dialog_note_delete_content))
                .setPositiveButton(context.getString(R.string.dialog_delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onPositiveClicked(note);
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
