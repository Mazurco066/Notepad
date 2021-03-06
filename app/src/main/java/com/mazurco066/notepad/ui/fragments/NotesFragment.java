package com.mazurco066.notepad.ui.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.SQLite.DatabaseCreator;
import com.mazurco066.notepad.adapter.NoteAdapter;
import com.mazurco066.notepad.SQLite.methods.NoteActions;
import com.mazurco066.notepad.model.Note;
import com.mazurco066.notepad.ui.activity.NoteActivity;
import com.mazurco066.notepad.ui.dialog.NoteDeleteDialog;
import com.mazurco066.notepad.ui.layout.SimpleDividerItemDecoration;
import com.mazurco066.notepad.util.SnackUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesFragment extends Fragment implements NoteAdapter.AdapterCallback,
                                                       NoteDeleteDialog.NoteDialogCallback {

    //Definindo Componentes
    @BindView(R.id.note_recycler) RecyclerView recycler;

    //Definindo Atributos
    private NoteAdapter adapter;
    private List<Note> notes;
    private NoteActions actions;

    public NotesFragment() {} // Required empty public constructor

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        ButterKnife.bind(this, view);

        //Instanciando Atributos
        this.actions = new NoteActions(getActivity());

        //Configurando recycler
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        //Returning View
        return view;
    }

    @Override
    public void onResume() {

        //Implementação padrão de método do cliclo de vida
        super.onResume();

        //Recuperando notas do banco
        this.notes = actions.readAllNotes();

        //Verificando se ha notas
        if (notes != null) {
            //Atualizando lista de notas
            this.adapter = new NoteAdapter(getActivity(), notes, this);
            this.recycler.setAdapter(adapter);
        }
        else {
            //Atualizando lista
            this.notes = new ArrayList<>();
            this.adapter = new NoteAdapter(getActivity(), notes, this);
            this.recycler.setAdapter(adapter);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Implementação padrão
        super.onActivityResult(requestCode, resultCode, data);

        //Notificação de nota deletada
        if (requestCode == 102 && resultCode == 1011) {
            View view = getActivity().findViewById(R.id.appDrawer);
            SnackUtil.show(view, getString(R.string.alert_delete_note_sucess), Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    //Definindo evendo de onClick sobre uma nota
    public void onNoteClicked(Note note) {
        Intent noteIntent = new Intent(getActivity() , NoteActivity.class);
        noteIntent.putExtra(DatabaseCreator.FIELD_ID, note.getId());
        noteIntent.putExtra(DatabaseCreator.FIELD_DATE, note.getDate());
        noteIntent.putExtra(DatabaseCreator.FIELD_TITLE, note.getTitle());
        noteIntent.putExtra(DatabaseCreator.FIELD_CONTENT, note.getContent());
        startActivityForResult(noteIntent, 102);
    }

    @Override
    //Definindo evento de onClick sobre excluir de uma nota
    public void onNoteDelete(final Note note) {
        //Criando e emitindo dialog de remoção para nota
        NoteDeleteDialog dialog = new NoteDeleteDialog(getActivity(), note, this);
        dialog.show();
    }

    @Override
    public void onPositiveClicked(Note note) {
        View view = getActivity().findViewById(R.id.appDrawer);
        if (actions.deleteNote(note.getId())) {
            SnackUtil.show(view, getString(R.string.alert_delete_note_sucess), Snackbar.LENGTH_SHORT);
            adapter.removeNote(note);
        }
        else {
            SnackUtil.show(view, getString(R.string.alert_failure), Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    public void onNegativeClicked() {
        //Nada acontece
    }
}
