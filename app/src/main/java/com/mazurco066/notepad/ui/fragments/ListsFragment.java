package com.mazurco066.notepad.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.adapter.ListAdapter;
import com.mazurco066.notepad.SQLite.methods.ListActions;
import com.mazurco066.notepad.model.TodoList;
import com.mazurco066.notepad.ui.layout.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListsFragment extends Fragment {

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
            adapter = new ListAdapter(getActivity(), lists);
            recycler.setAdapter(adapter);
        }
        else {
            //Atualizando lista
            lists = new ArrayList<>();
            adapter = new ListAdapter(getActivity(), lists);
            recycler.setAdapter(adapter);
        }
    }
}
