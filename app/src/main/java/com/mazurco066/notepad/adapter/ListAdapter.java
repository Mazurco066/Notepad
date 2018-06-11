package com.mazurco066.notepad.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.SQLite.methods.ListActions;
import com.mazurco066.notepad.model.TodoList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    //Atributos
    private Context context;
    private List<TodoList> lists;
    private ListActions actions;
    private AdapterCallback callback;

    //Método construtor padrão
    public ListAdapter(@NonNull Context context, @NonNull List<TodoList> objects, AdapterCallback callback) {
        //Setando Atributos
        this.actions = new ListActions(context);
        this.context = context;
        this.lists = objects;
        this.callback = callback;
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
            public void onClick(View v) { callback.onDeleteList(list);
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { callback.onClickList(list); }
        });
    }

    @Override
    public int getItemCount() { return lists.size(); }

    //Método para remover lista deletada
    public void removeList(TodoList list) {
        lists.remove(list);
        notifyDataSetChanged();
    }

    //Definindo View Holder
    class ListViewHolder extends RecyclerView.ViewHolder {

        //Definindo componentes presentes na view
        @BindView(R.id.editNoteTitle) TextView txtTitle;
        @BindView(R.id.editNoteDate) TextView txtDate;
        @BindView(R.id.img_delete) ImageView imgDelete;
        View view;

        //Implementando um construtor para vire holder
        ListViewHolder(View itemView) {
            //Definindo bind de componentes
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    //Definindo interface de callback para eventos
    public interface AdapterCallback {
        void onClickList(TodoList list);
        void onDeleteList(TodoList list);
    }
}
