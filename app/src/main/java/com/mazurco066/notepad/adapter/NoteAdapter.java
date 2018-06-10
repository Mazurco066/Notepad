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
import com.mazurco066.notepad.SQLite.methods.NoteActions;
import com.mazurco066.notepad.model.Note;
import com.mazurco066.notepad.ui.activity.NoteActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    //Atributos para recuperação de dados
    private static Context context;
    private List<Note> notes;
    private NoteActions actions;

    //Método construtor padrão
    public NoteAdapter(@NonNull Context context, @NonNull List<Note> objects) {
        //Recuperando instancias dos Atributos
        this.actions = new NoteActions(context);
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
            public void onClick(View v) {
                confirmDelete(note);
            }
        });
        holder.setNote(note);
    }

    @Override
    //Ao recuperar contagem de itens
    public int getItemCount() { return notes.size(); }

    //Método para confirmar exclusão de uma nota
    private void confirmDelete(final Note note) {

        //Instanciando criador de alert dialog
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //Configurando alert dialog
        alertDialog.setTitle(context.getResources().getString(R.string.dialog_note_delete_title));
        alertDialog.setMessage(context.getResources().getString(R.string.dialog_note_delete_content));
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton(context.getResources().getString(R.string.dialog_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Recuperando mensagens
                String sucess = context.getResources().getString(R.string.alert_delete_note_sucess);
                String failure = context.getResources().getString(R.string.alert_failure);

                if (actions.deleteNote(note.getId())) {
                    //Retornando mensagem de sucesso ao usuário
                    Toast.makeText(context, sucess, Toast.LENGTH_SHORT).show();
                    notes.remove(note);
                    notifyDataSetChanged();
                }
                else {
                    //Retornando mensagem de erro ao criar nota para usuário
                    Toast.makeText(context, failure, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Adicionando botões negativo e positivo para alertdialog
        alertDialog.setNegativeButton(context.getResources().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Nada Acontece....
            }
        });

        //Criando e mostrando dialog
        alertDialog.create();
        alertDialog.show();
    }

    //Definindo view holder
    class NoteViewHolder extends RecyclerView.ViewHolder {

        //Definindo componentes presentes na view
        @BindView(R.id.editNoteTitle) TextView txtTitle;
        @BindView(R.id.editNoteDate) TextView txtDate;
        @BindView(R.id.img_delete) ImageView imgDelete;
        View view;

        //Definindo atributos
        private Note note;

        void setNote(Note note) { this.note = note; }

        //Implementando um construtor para o view holder
        NoteViewHolder(View itemView) {
            //Implementação padrão de view holder
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);

            //Definindo evento de onClick para item da lista
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent noteIntent = new Intent(context , NoteActivity.class);
                    noteIntent.putExtra(DatabaseCreator.FIELD_ID, note.getId());
                    noteIntent.putExtra(DatabaseCreator.FIELD_DATE, note.getDate());
                    noteIntent.putExtra(DatabaseCreator.FIELD_TITLE, note.getTitle());
                    noteIntent.putExtra(DatabaseCreator.FIELD_CONTENT, note.getContent());
                    context.startActivity(noteIntent);
                }
            });
        }
    }
}
