package com.mazurco066.notepad.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.activity.ListActivity;
import com.mazurco066.notepad.adapter.ListAdapter;
import com.mazurco066.notepad.dao.ListDAO;
import com.mazurco066.notepad.model.TodoList;
import com.mazurco066.notepad.util.DatabaseCreator;

import java.util.ArrayList;
import java.util.List;

public class ListsFragment extends Fragment {

    //Components
    private ListView listView;

    //Attb
    private ArrayAdapter<TodoList> adapter;
    private List<TodoList> lists;
    private ListDAO dao;

    public ListsFragment() {} // Required empty public constructor

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lists, container, false);

        // Instanciando componentes
        this.listView = view.findViewById(R.id.createdListsView);

        // Instanciando atributos
        this.dao = new ListDAO(getActivity());

        //Atribuindo evento de on click para list view de seleçãod e listas
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Instanciando Todo List
                TodoList todoList = lists.get(i);

                //Abrindo activity para edição da mesma
                openListActivity(todoList);

            }
        });

        // Returning inflated view
        return view;

    }

    //Sobrescrevendo método on resume para atualizar a listview
    @Override
    public void onResume() {

        //Implementação padrão
        super.onResume();

        //Verificando se há itens na lista
        this.lists = dao.getAllLists();
        if (lists != null) {

            //Atualizando lista de notas
            this.adapter = new ListAdapter(getActivity(), lists);
            this.listView.setAdapter(adapter);

        }
        else {

            //Atualizando lista
            this.lists = new ArrayList<>();
            this.adapter = new ListAdapter(getActivity(), lists);
            this.listView.setAdapter(adapter);

        }
    }

    //Método para Abrir Activity de Escrever/Visualizar Nota
    private void openListActivity(TodoList todoList) {

        //Instanciando intent para ir para prox activity
        Intent listActivity = new Intent(getActivity(), ListActivity.class);
        listActivity.putExtra(DatabaseCreator.FIELD_ID, todoList.getId());
        listActivity.putExtra(DatabaseCreator.FIELD_TITLE, todoList.getTitle());
        listActivity.putExtra(DatabaseCreator.FIELD_DATE, todoList.getDate());

        //Iniciando nova activity
        startActivity(listActivity);

    }

}
