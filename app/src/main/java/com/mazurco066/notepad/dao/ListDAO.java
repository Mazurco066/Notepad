package com.mazurco066.notepad.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mazurco066.notepad.util.DatabaseCreator;

public class ListDAO {

    //Attb
    private SQLiteDatabase db;
    private DatabaseCreator database;

    //Public Constructors
    public ListDAO(Context context) {

        //Instancing database instance
        this.database = new DatabaseCreator(context);

    }

    //Méthods

    //Todo List: Create Method
    public boolean createList(String title) {

        try {

            //Definindo valores a serem registrados
            ContentValues values;
            long result;

            //Settando Dados no objeto para realização da inserção
            this.db = database.getWritableDatabase();
            values = new ContentValues();
            values.put(DatabaseCreator.FIELD_TITLE, title);

            //Inserindo os Dados
            result = db.insert(DatabaseCreator.TODOLIST_TABLE, null, values);
            db.close();

            //Retornando sucesso ou fracasso
            if (result == -1) return false;
            return true;

        }
        catch (Exception e) {

            //Registrando erro gerado
            e.printStackTrace();
            return false;
        }
    }

    public int getLastListId() {

        try {

            //Definindo dados a serem recuperados
            Cursor cursor;
            String fields[] = {"seq"};
            String dbname = "sqlite_sequence";
            String where = "name = '" + DatabaseCreator.TODOLIST_TABLE + "'";
            int recoveredID;

            //Recuperando uma instancia do banco de dados
            db = database.getReadableDatabase();
            cursor = db.query(
                    dbname,
                    fields,
                    where,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            //Verificando se foram encontrados dados
            cursor.moveToFirst();

            //Fechando conexão com banco
            db.close();

            //Recuperando id
            recoveredID = cursor.getInt(cursor.getColumnIndex("seq"));

            //Fechando cursor
            cursor.close();

            return recoveredID;

        }
        catch (Exception e) {

            //Registrando erro ocorrigo
            e.printStackTrace();
            return  -1;

        }

    }

}
