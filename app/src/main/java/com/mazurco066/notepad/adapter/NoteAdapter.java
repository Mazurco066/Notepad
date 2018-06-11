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
import com.mazurco066.notepad.SQLite.methods.NoteActions;
import com.mazurco066.notepad.model.Note;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    //Atributos para recuperação de dados
    private static Context context;
    private List<Note> notes;
    private NoteActions actions;
    private AdapterCallback adapterCallback;

    //Método construtor padrão
    public NoteAdapter(@NonNull Context context, @NonNull List<Note> objects, AdapterCallback adapterCallback) {
        //Recuperando instancias dos Atributos
        this.actions = new NoteActions(context);
        this.adapterCallback = adapterCallback;
        this.context = context;
        this.notes = objects;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    //Ao criar view holder
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Inflando view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

        //Retornando view inflada para o view holder
        return new NoteViewHolder(view);
    }

    @Override
    //Ao bindar view com xml
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        //Recuperando posição da nota
        final Note note = notes.get(position);

        //Adicionando detalhes da nota
        holder.txtTitle.setText(note.getTitle());
        holder.txtDate.setText(context.getResources().getString(R.string.label_created) + " " + note.getDate());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {adapterCallback.onNoteDelete(note);
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {adapterCallback.onNoteClicked(note);
            }
        });
    }

    @Override
    //Ao recuperar contagem de itens
    public int getItemCount() { return notes.size(); }

    //Método para remover nota da lista
    public void removeNote(Note note) {
        notes.remove(note);
        notifyDataSetChanged();
    }

    //Definindo view holder
    class NoteViewHolder extends RecyclerView.ViewHolder {

        //Definindo componentes presentes na view
        @BindView(R.id.editNoteTitle) TextView txtTitle;
        @BindView(R.id.editNoteDate) TextView txtDate;
        @BindView(R.id.img_delete) ImageView imgDelete;
        View view;

        //Implementando um construtor para o view holder
        NoteViewHolder(View itemView) {
            //Implementação padrão de view holder
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    //Definindo interface para possiveis eventos de click
    public interface AdapterCallback {
        void onNoteClicked(Note note);
        void onNoteDelete(Note note);
    }
}
