package com.mazurco066.notepad.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.mazurco066.notepad.model.Note;
import com.mazurco066.notepad.util.DatabaseCreator;

import java.util.ArrayList;

public class NoteDAO {

    //Atributos
    private SQLiteDatabase db;
    private DatabaseCreator database;
    private Context context;

    //Construtor Padrão
    public NoteDAO(Context context) {

        //Instanciando Banco de dados
        database = new DatabaseCreator(context);
        this.context = context;
    }

    //Métodos de CRUD
    public boolean insertNote(Note note) {

        try {

            //Definindo Valores a ser Inseridos
            ContentValues values;
            long result;

            //Setando dados no objeto com dados prontos para inserção
            db = database.getWritableDatabase();
            values = new ContentValues();
            values.put(DatabaseCreator.FIELD_DATE, note.getDate());
            values.put(DatabaseCreator.FIELD_TITLE, note.getTitle());
            values.put(DatabaseCreator.FIELD_CONTENT, note.getContent());

            //Inserindo os dados
            result = db.insert(DatabaseCreator.DEFAULT_TABLE, null, values);
            db.close();

            //Retornando Sucesso ou Fracasso!
            if (result == -1) return false;
            return true;
        }
        catch (Exception e) {

            //Registrando erro ocorrido
            e.printStackTrace();
            return false;
        }

    }

    //Método de Leitura de todas as notas
    public ArrayList<Note> readAllNotes() {

        //Declarando Lista para retorno
        ArrayList<Note> _return = new ArrayList<>();

        try {

            //Definindo campos a serem recuperados
            Cursor cursor;
            String[] fields = {
                    DatabaseCreator.FIELD_ID,
                    DatabaseCreator.FIELD_DATE,
                    DatabaseCreator.FIELD_TITLE,
                    DatabaseCreator.FIELD_CONTENT
            };

            //Recuperando uma instancia de leitura do banco de dados
            db = database.getReadableDatabase();

            //Recuperando os Dados do banco
            cursor = db.query(
                    DatabaseCreator.DEFAULT_TABLE,
                    fields,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            //Verificando se dados foram encontrados
            cursor.moveToFirst();

            //Fechando conexão com o banco
            db.close();

            //Formatando os dados em uma instancia de ArrayList
            do {

                //Colocando em um objeto de nota
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(DatabaseCreator.FIELD_ID)));
                note.setDate(cursor.getString(cursor.getColumnIndex(DatabaseCreator.FIELD_DATE)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseCreator.FIELD_TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(DatabaseCreator.FIELD_CONTENT)));

                //Adicionando registro encontrado para lista de notas encontradas
                _return.add(note);

            } while (cursor.moveToNext());

            //Fechando cursor
            cursor.close();

        }
        catch (Exception e) {

            //Registrando erro encontrado e retornando nulo
            e.printStackTrace();
            return null;
        }

        //Retornando a lista de notas
        return _return;
    }

}
