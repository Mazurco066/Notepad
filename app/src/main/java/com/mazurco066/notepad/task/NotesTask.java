package com.mazurco066.notepad.task;

import android.content.Context;

import com.mazurco066.notepad.model.Note;

public interface NotesTask {

    //Definindo presenters a ser implementados
    interface Presenter {
        void save(Note note);
        void delete(int noteId);
        void share(Note note, Context context);
    }

    //Definindo métodos que a view deve implementar
    interface View {
        //Fluxos de sucesso
        void onSaveSuccess();
        void onUpdateSucess();
        void onDeleteSuccess();
        //Fluxos de fracasso
        void onSaveFailed();
        void onDeleteFailed(int code);
        void onShareFailed();
    }
}
