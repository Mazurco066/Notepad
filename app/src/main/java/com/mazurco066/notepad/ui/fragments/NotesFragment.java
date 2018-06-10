package com.mazurco066.notepad.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.ui.activity.NoteActivity;
import com.mazurco066.notepad.adapter.NoteAdapter;
import com.mazurco066.notepad.SQLite.methods.NoteActions;
import com.mazurco066.notepad.model.Note;
import com.mazurco066.notepad.SQLite.DatabaseCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesFragment extends Fragment {

    //Definindo Componentes
    @BindView(R.id.note_recycler) RecyclerView recycler;

    //Definindo Atributos
    private NoteAdapter adapter;
    private List<Note> notes;
    private NoteActions dao;

    public NotesFragment() {} // Required empty public constructor

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        ButterKnife.bind(this, view);

        //Instanciando Atributos
        this.dao = new NoteActions(getActivity());

        //Configurando recycler
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Returning View
        return view;

    }

    @Override
    public void onResume() {

        //Implementação padrão de método do cliclo de vida
        super.onResume();

        //Recuperando notas do banco
        this.notes = dao.readAllNotes();

        //Verificando se ha notas
        if (notes != null) {
            //Atualizando lista de notas
            this.adapter = new NoteAdapter(getActivity(), notes);
            this.recycler.setAdapter(adapter);
        }
        else {
            //Atualizando lista
            this.notes = new ArrayList<>();
            this.adapter = new NoteAdapter(getActivity(), notes);
            this.recycler.setAdapter(adapter);
        }
    }
}
