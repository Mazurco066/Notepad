package com.mazurco066.notepad.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mazurco066.notepad.model.Note;
import com.mazurco066.notepad.util.DatabaseCreator;

public class NoteDAO {

    //Atributos
    private SQLiteDatabase db;
    private DatabaseCreator database;

    //Construtor Padrão
    public NoteDAO(Context context) {

        database = new DatabaseCreator(context);
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
            values.put(DatabaseCreator.FIELD_DATE, note.getDate().toString());
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

}
