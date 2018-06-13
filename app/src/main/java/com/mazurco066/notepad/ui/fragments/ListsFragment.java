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
import com.mazurco066.notepad.adapter.ListAdapter;
import com.mazurco066.notepad.SQLite.methods.ListActions;
import com.mazurco066.notepad.model.TodoList;
import com.mazurco066.notepad.ui.activity.ListActivity;
import com.mazurco066.notepad.ui.dialog.ListDeleteDialog;
import com.mazurco066.notepad.ui.layout.SimpleDividerItemDecoration;
import com.mazurco066.notepad.util.SnackUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListsFragment extends Fragment implements ListAdapter.AdapterCallback,
                                                       ListDeleteDialog.ListDialogCallback {

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Implementação padrão
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == 1010) {
            View view = getActivity().findViewById(R.id.appDrawer);
            SnackUtil.show(view, getString(R.string.alert_delete_list_sucess), Snackbar.LENGTH_SHORT);
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
        startActivityForResult(listIntent, 101);
    }

    @Override
    public void onDeleteList(final TodoList list) {
        //Instanciando criador de alert dialog
        ListDeleteDialog dialog = new ListDeleteDialog(getActivity(), list, this);
        dialog.show();
    }

    @Override
    public void onPositiveClicked(TodoList list) {
        View view = getActivity().findViewById(R.id.appDrawer);
        if (actions.deleteList(list)) {
            SnackUtil.show(view, getString(R.string.alert_delete_list_sucess), Snackbar.LENGTH_SHORT);
            adapter.removeList(list);
        }
        else
            SnackUtil.show(view, getString(R.string.alert_failure), Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onNegativeClicked() {
        //Nada Acontece
    }
}
