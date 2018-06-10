package com.mazurco066.notepad.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.SQLite.DatabaseCreator;
import com.mazurco066.notepad.SQLite.methods.ListActions;
import com.mazurco066.notepad.model.TodoList;
import com.mazurco066.notepad.ui.activity.ListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    //Atributos
    private static Context context;
    private List<TodoList> lists;
    private ListActions actions;

    //Método construtor padrão
    public ListAdapter(@NonNull Context context, @NonNull List<TodoList> objects) {
        //Setando Atributos
        this.actions = new ListActions(context);
        this.context = context;
        this.lists = objects;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Inflando view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

        //Retornando view inflada para o view holder
        return new ListAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        //Recuperando lista a ser usada
        final TodoList list = lists.get(position);

        //Adicionando detalhes da lista
        holder.txtTitle.setText(list.getTitle());
        holder.txtDate.setText(context.getResources().getString(R.string.label_created) + " " + list.getDate());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete(list);
            }
        });
        holder.setList(list);
    }

    //Método para confirmar exclusão de uma lista
    private void confirmDelete(final TodoList todoList) {

        //Instanciando criador de alert dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //Configurando alert dialog
        alertDialog.setTitle(context.getResources().getString(R.string.dialog_list_delete_title));
        alertDialog.setMessage(context.getResources().getString(R.string.dialog_list_delete_content));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(context.getResources().getString(R.string.dialog_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Recuperando mensagens
                String sucess = context.getResources().getString(R.string.alert_delete_list_sucess);
                String failure = context.getResources().getString(R.string.alert_failure);

                if (actions.deleteList(todoList)) {
                    //Retornando mensagem de sucesso ao usuário
                    Toast.makeText(context, sucess, Toast.LENGTH_SHORT).show();
                    lists.remove(todoList);
                    notifyDataSetChanged();
                }
                else {
                    //Retornando mensagem de erro ao deletar lista para usuário
                    Toast.makeText(context, failure, Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Adicionando botões negativo e positivo para alertdialog
        alertDialog.setNegativeButton(context.getResources().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { /*Nada Acontece....*/ }
        });
        //Criando e mostrando dialog
        alertDialog.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() { return lists.size(); }

    //Definindo View Holder
    class ListViewHolder extends RecyclerView.ViewHolder {

        //Definindo componentes presentes na view
        @BindView(R.id.editNoteTitle) TextView txtTitle;
        @BindView(R.id.editNoteDate) TextView txtDate;
        @BindView(R.id.img_delete) ImageView imgDelete;
        View view;

        //Definindo atributos
        private TodoList list;

        //Definindo método para setar view
        void setList(TodoList list) { this.list = list; }

        //Implementando um construtor para vire holder
        ListViewHolder(View itemView) {
            //Definindo bind de componentes
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);

            //Definindo evento para click nos itens
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Instanciando intent para ir para prox activity
                    Intent listIntent = new Intent(context, ListActivity.class);
                    listIntent.putExtra(DatabaseCreator.FIELD_ID, list.getId());
                    listIntent.putExtra(DatabaseCreator.FIELD_TITLE, list.getTitle());
                    listIntent.putExtra(DatabaseCreator.FIELD_DATE, list.getDate());

                    //Iniciando nova activity
                    context.startActivity(listIntent);
                }
            });
        }
    }
}
