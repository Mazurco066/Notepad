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
import com.mazurco066.notepad.model.ItemList;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<ItemList> {

    //Atributos
    private Context context;
    private List<ItemList> itens;

    //Método construtor padrão
    public TaskAdapter(@NonNull Context context, @NonNull List<ItemList> objects) {

        //Implementação padrão do método
        super(context, 0, objects);

        //Setando Atributos
        this.context = context;
        this.itens = objects;

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
            view = inflater.inflate(R.layout.item_task, parent, false);

        }

        //Instanciando textView para exibição de dados
        TextView editTaskTitle = view.findViewById(R.id.editTaskTitle);

        //Recuperando item para ser exibido
        ItemList itemList = itens.get(position);

        //Definindo Strings para serem exibidas
        String task = itemList.getTask();

        //Exibindo dados na tela
        editTaskTitle.setText(task);

        //Retornando a view
        return view;

    }

}
