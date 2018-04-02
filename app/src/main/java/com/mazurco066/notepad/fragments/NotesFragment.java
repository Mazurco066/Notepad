package com.mazurco066.notepad.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.activity.NoteActivity;
import com.mazurco066.notepad.adapter.NoteAdapter;
import com.mazurco066.notepad.dao.NoteDAO;
import com.mazurco066.notepad.model.Note;
import com.mazurco066.notepad.util.DatabaseCreator;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    //Definindo Componentes
    private ListView listView;

    //Definindo Atributos
    private NoteAdapter adapter;
    private List<Note> notes;
    private NoteDAO dao;

    public NotesFragment() {} // Required empty public constructor

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        //Instanciando Componentes
        this.listView = view.findViewById(R.id.noteListView);

        //Instanciando Atributos
        this.dao = new NoteDAO(getActivity());

        //Ouvindo eventos de clicar em uma nota
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Instanciando nota
                Note note = notes.get(i);

                //Iniciando Activity de Edição de nota
                openNoteActivity(note);

            }
        });

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
            this.listView.setAdapter(adapter);

        }
        else {

            //Atualizando lista
            this.notes = new ArrayList<>();
            this.adapter = new NoteAdapter(getActivity(), notes);
            this.listView.setAdapter(adapter);

        }

    }

    //Método para Abrir Activity de Escrever/Visualizar Nota
    private void openNoteActivity(Note note) {

        //Instanciando intent para ir para prox activity
        Intent noteActivity = new Intent(getActivity(), NoteActivity.class);
        noteActivity.putExtra(DatabaseCreator.FIELD_ID, note.getId());
        noteActivity.putExtra(DatabaseCreator.FIELD_DATE, note.getDate());
        noteActivity.putExtra(DatabaseCreator.FIELD_TITLE, note.getTitle());
        noteActivity.putExtra(DatabaseCreator.FIELD_CONTENT, note.getContent());

        //Iniciando nova activity
        startActivity(noteActivity);

    }

}
