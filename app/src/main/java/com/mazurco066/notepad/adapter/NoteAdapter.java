package com.mazurco066.notepad.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mazurco066.notepad.R;
import com.mazurco066.notepad.SQLite.methods.NoteActions;
import com.mazurco066.notepad.model.Note;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {

    //Atributos
    private Context context;
    private List<Note> notes;
    private NoteActions dao;

    //Método construtor padrão
    public NoteAdapter(@NonNull Context context, @NonNull List<Note> objects) {

        //Implementação padrão do método
        super(context, 0, objects);

        //Setando Atributos
        this.context = context;
        this.notes = objects;
        this.dao = new NoteActions(context);

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

        //Instanciando botão para exclusão dos dados
        ImageView imgDelete = view.findViewById(R.id.img_delete);

        //Recuperando nota para ser exibida
        final Note note = notes.get(position);

        //Definindo evento para exclusão da nota
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Confirmando exclusão da nota com usuário
                confirmDelete(note);

            }
        });

        //Definindo Strings para serem exibidas
        String title = note.getTitle();
        String date = context.getResources().getString(R.string.label_created) + " " + note.getDate();

        //Exibindo dados na tela
        editNoteTitle.setText(title);
        editNoteData.setText(date);

        //Retornando a view
        return view;

    }

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

                if (dao.deleteNote(note.getId())) {

                    //Retornando mensagem de sucesso ao usuário
                    Toast.makeText(context, sucess, Toast.LENGTH_SHORT).show();
                    remove(note);
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

}
