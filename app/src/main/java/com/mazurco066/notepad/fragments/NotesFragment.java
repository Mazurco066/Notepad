package com.mazurco066.notepad.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.activity.MainActivity;
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
    private ArrayAdapter<Note> adapter;
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

        //Ouvindo eventos de segurar click em uma nota
        this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Confirmando exclusão da nota selecionada com usuário
                confirmDelete(notes.get(i).getId());

                //Retornando
                return true;
            }
        });

        //Returning View
        return view;

    }

    @Override
    public void onResume() {

        //Implementação padrão de método do cliclo de vida
        super.onResume();

        //Verificando se há itens na lista
        this.notes = dao.readAllNotes();
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

    //Método para confirmar exclusão de uma nota
    private void confirmDelete(final int id) {

        //Instanciando criador de alert dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        //Configurando alert dialog
        alertDialog.setTitle("Delete Confirmation");
        alertDialog.setMessage("Are you sure you want to delete this note?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Recuperando mensagens
                String sucess = getResources().getString(R.string.alert_delete_note_sucess);
                String failure = getResources().getString(R.string.alert_failure);

                if (dao.deleteNote(id)) {

                    //Retornando mensagem de sucesso ao usuário
                    Toast.makeText(getActivity(), sucess, Toast.LENGTH_SHORT).show();

                    //Atualizando Lista
                    onResume();

                }
                else {

                    //Retornando mensagem de erro ao criar nota para usuário
                    Toast.makeText(getActivity(), failure, Toast.LENGTH_SHORT).show();

                }
            }
        });

        //Adicionando botões negativo e positivo para alertdialog
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Nada Acontece....
            }
        });

        //Criando e mostrando dialog
        alertDialog.create();
        alertDialog.show();

    }

}
