package com.mazurco066.notepad.presenter;

import android.content.Context;
import android.content.Intent;

import com.mazurco066.notepad.SQLite.methods.NoteActions;
import com.mazurco066.notepad.model.Note;
import com.mazurco066.notepad.task.NotesTask;

public class NotesPresenter implements NotesTask.Presenter {

    //Definindo view que usa os métodos implementados
    private NotesTask.View view;

    //Definindo objeto para interação com banco de dados
    private NoteActions actions;

    public  NotesPresenter(NotesTask.View view, Context context) {
        this.view = view;
        this.actions = new NoteActions(context);
    }

    @Override
    public void save(Note note) {
        //Verificando se é uma nova nota ou se é uma nota existente
        if (note.getId() == -1) {
            //Tentando inserir nota
            if (actions.insertNote(note)) view.onSaveSuccess();
            else view.onSaveFailed();
        }
        else {
            if (actions.editNote(note)) view.onUpdateSucess();
            else view.onSaveFailed();
        }
    }

    @Override
    public void delete(int noteId) {
        //Verificando se é uma nota existente
        if (noteId != -1) {
            if (actions.deleteNote(noteId)) view.onDeleteSuccess();
            else view.onDeleteFailed(101);
        }
        else {
            view.onDeleteFailed(102);
        }
    }

    @Override
    public void share(Note note, Context context) {
        //Verificando se há texto para ser compartilhado
        if (!note.getContent().isEmpty()) {
            //Recuperando conteúdo a ser compartilhado
            String content = note.getContent();
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, content);
            shareIntent.setType("text/plain");
            context.startActivity(shareIntent);
        }
        else { view.onShareFailed(); }
    }
}
