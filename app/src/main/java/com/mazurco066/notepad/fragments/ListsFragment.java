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

        //Atribuindo evento de on long click para list view de seleção de listas
        this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Recuperando lista e aguardando delete
                TodoList target = lists.get(i);
                confirmDelete(target);

                //Retornando
                return true;

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

    //Método para confirmar exclusão de uma lista
    private void confirmDelete(final TodoList todoList) {

        //Instanciando criador de alert dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        //Configurando alert dialog
        alertDialog.setTitle("Delete Confirmation");
        alertDialog.setMessage("Are you sure you want to delete this List?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Recuperando mensagens
                String sucess = getResources().getString(R.string.alert_delete_list_sucess);
                String failure = getResources().getString(R.string.alert_failure);

                if (dao.deleteList(todoList)) {

                    //Retornando mensagem de sucesso ao usuário
                    Toast.makeText(getActivity(), sucess, Toast.LENGTH_SHORT).show();

                    //Atualizando lista
                    onResume();

                }
                else {

                    //Retornando mensagem de erro ao deletar lista para usuário
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
