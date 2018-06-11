package com.mazurco066.notepad.ui.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.SQLite.DatabaseCreator;
import com.mazurco066.notepad.adapter.ListAdapter;
import com.mazurco066.notepad.SQLite.methods.ListActions;
import com.mazurco066.notepad.model.TodoList;
import com.mazurco066.notepad.ui.activity.ListActivity;
import com.mazurco066.notepad.ui.layout.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListsFragment extends Fragment implements ListAdapter.AdapterCallback {

    //Components
    @BindView(R.id.list_recycler) RecyclerView recycler;

    //Attb
    private ListAdapter adapter;
    private List<TodoList> lists;
    private ListActions actions;

    public ListsFragment() {} // Required empty public constructor

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lists, container, false);
        ButterKnife.bind(this, view);

        // Instanciando atributos
        this.actions = new ListActions(getActivity());

        //Configurando recycler
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        // Returning inflated view
        return view;
    }

    //Sobrescrevendo método on resume para atualizar a listview
    @Override
    public void onResume() {

        //Implementação padrão
        super.onResume();

        //Verificando se há itens na lista
        this.lists = actions.getAllLists();

        if (lists != null) {
            //Atualizando lista de notas
            adapter = new ListAdapter(getActivity(), lists, this);
            recycler.setAdapter(adapter);
        }
        else {
            //Atualizando lista
            lists = new ArrayList<>();
            adapter = new ListAdapter(getActivity(), lists, this);
            recycler.setAdapter(adapter);
        }
    }

    @Override
    public void onClickList(TodoList list) {
        //Instanciando intent para ir para prox activity
        Intent listIntent = new Intent(getActivity(), ListActivity.class);
        listIntent.putExtra(DatabaseCreator.FIELD_ID, list.getId());
        listIntent.putExtra(DatabaseCreator.FIELD_TITLE, list.getTitle());
        listIntent.putExtra(DatabaseCreator.FIELD_DATE, list.getDate());
        //Iniciando nova activity
        startActivity(listIntent);
    }

    @Override
    public void onDeleteList(final TodoList list) {
        //Instanciando criador de alert dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        //Configurando alert dialog
        alertDialog.setTitle(getString(R.string.dialog_list_delete_title));
        alertDialog.setMessage(getString(R.string.dialog_list_delete_content));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getString(R.string.dialog_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Recuperando mensagens
                String sucess = getString(R.string.alert_delete_list_sucess);
                String failure = getString(R.string.alert_failure);

                if (actions.deleteList(list)) {
                    //Retornando mensagem de sucesso ao usuário
                    Toast.makeText(getActivity(), sucess, Toast.LENGTH_SHORT).show();
                    adapter.removeList(list);
                }
                else { Toast.makeText(getActivity(), failure, Toast.LENGTH_SHORT).show(); }
            }
        });
        //Adicionando botões negativo e positivo para alertdialog
        alertDialog.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { /*Nada Acontece....*/ }
        });
        //Criando e mostrando dialog
        alertDialog.create();
        alertDialog.show();
    }
}
