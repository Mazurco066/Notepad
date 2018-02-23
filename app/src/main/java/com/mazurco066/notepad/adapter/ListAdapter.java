package com.mazurco066.notepad.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.model.TodoList;

import java.util.List;

public class ListAdapter extends ArrayAdapter<TodoList> {

    //Atributos
    private Context context;
    private List<TodoList> lists;

    //Método construtor padrão
    public ListAdapter(@NonNull Context context, @NonNull List<TodoList> objects) {

        //Implementação padrão do método
        super(context, 0, objects);

        //Setando Atributos
        this.context = context;
        this.lists = objects;

    }

    //Sobrescrevendo método de retorno de view
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //View view = super.getView(position, convertView, parent);
        View view = convertView;

        //Verificando se a view ja está criada
        if (view == null) {

            //Iniciando objeto para montar o layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Montando a partir do xml
            view = inflater.inflate(R.layout.item_note, parent, false);

        }

        //Instanciando textView para exibição de dados
        TextView editNoteTitle = view.findViewById(R.id.editNoteTitle);
        TextView editNoteData = view.findViewById(R.id.editNoteDate);

        //Recuperando nota para ser exibida
        TodoList todoList = lists.get(position);

        //Definindo Strings para serem exibidas
        String title = todoList.getTitle();
        String date = context.getResources().getString(R.string.label_created) + " " + todoList.getDate();

        //Exibindo dados na tela
        editNoteTitle.setText(title);
        editNoteData.setText(date);

        //Retornando a view
        return view;

    }

}
