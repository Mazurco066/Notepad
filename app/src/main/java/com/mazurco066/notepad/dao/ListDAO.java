package com.mazurco066.notepad.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mazurco066.notepad.model.ItemList;
import com.mazurco066.notepad.model.TodoList;
import com.mazurco066.notepad.util.DatabaseCreator;

import java.util.ArrayList;

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
    public boolean createList(String title, String date) {

        try {

            //Definindo valores a serem registrados
            ContentValues values;
            long result;

            //Settando Dados no objeto para realização da inserção
            this.db = database.getWritableDatabase();
            values = new ContentValues();
            values.put(DatabaseCreator.FIELD_TITLE, title);
            values.put(DatabaseCreator.FIELD_DATE, date);

            //db.execSQL("DROP TABLE IF EXISTS itemlist");
            //db.execSQL("DELETE FROM " + DatabaseCreator.TODOLIST_TABLE);
            //db.execSQL("ALTER TABLE " + DatabaseCreator.TODOLIST_TABLE + "  add " + DatabaseCreator.FIELD_DATE + " Varchar(10)");
            //db.execSQL("DROP TABLE itemlist");
            //db.execSQL("CREATE TABLE IF NOT EXISTS itemlist (_noteid integer, _task VARCHAR(50) primary key, _done integer, FOREIGN KEY (_noteid) REFERENCES todolists(_id));");

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


    public boolean AddItem(int TodoListID, ItemList itemList) {

        try {

            //Definindo valores a serem registrados
            ContentValues values;
            long result;

            //Setando dados nos objetos para realizar a inserção
            this.db = database.getWritableDatabase();
            values = new ContentValues();
            values.put(DatabaseCreator.FIELD_NOTEID, TodoListID);
            values.put(DatabaseCreator.FIELD_TASK, itemList.getTask());
            values.put(DatabaseCreator.FIELD_DONE, itemList.getDone());

            //Inserindo dados
            result = db.insert(DatabaseCreator.ITEMLIST_TABLE, null, values);

            //Retornando sucesso ou fracasso
            if (result == -1) return false;
            return true;

        }
        catch (Exception e) {

            //Registrando erro ocorrido
            e.printStackTrace();
            return false;

        }

    }

    public boolean setDone(int _id, String _task) {

        try {

            //Definindo Valorres
            ContentValues values;
            String where = DatabaseCreator.FIELD_NOTEID + " = " + _id + " and " + DatabaseCreator.FIELD_TASK + " = '" + _task + "'";
            long result;

            //Recuperando instancia banco de dados
            this.db = database.getWritableDatabase();

            //Definindo Valores que serão alterados
            values = new ContentValues();
            values.put(DatabaseCreator.FIELD_DONE, 1);

            //Atualizando dados no banco
            result = db.update(
                    DatabaseCreator.ITEMLIST_TABLE,
                    values,
                    where,
                    null
            );

            //Fechando conexão com banco
            db.close();

            //Retornando sucesso ou fracasso
            if (result == -1) return false; else return true;

        }
        catch (Exception e) {

            //Registrando erro ocorrigo
            e.printStackTrace();
            return false;

        }

    }

    public ArrayList<ItemList> getAllItens(int TodoListID) {

        ArrayList<ItemList> _return = new ArrayList<>();

        try {

            //Definindo dados a ser recuperados
            Cursor cursor;
            String[] fields = {
                    DatabaseCreator.FIELD_NOTEID,
                    DatabaseCreator.FIELD_TASK,
                    DatabaseCreator.FIELD_DONE
            };
            String where = DatabaseCreator.FIELD_NOTEID + " = " + TodoListID;

            //Recuperando uma instancia de leitura do banco de dados
            this.db = database.getReadableDatabase();

            //Recuperando dados do banco
            cursor = db.query(
                    DatabaseCreator.ITEMLIST_TABLE,
                    fields,
                    where,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            //Verificando se dados foram encontrados
            cursor.moveToFirst();

            //Fechando conexão com banco de dados
            db.close();

            //Formatando em formato de arraylist
            do {

                //Colocando em um objeto de Item de Lista
                ItemList itemList = new ItemList();
                itemList.setTask(cursor.getString(cursor.getColumnIndex(DatabaseCreator.FIELD_TASK)));
                itemList.setDone(cursor.getInt(cursor.getColumnIndex(DatabaseCreator.FIELD_DONE)));

                //Adicionando registro encontrado no vetor de retorno
                _return.add(itemList);

            } while (cursor.moveToNext());

            //Fechando cursor
            cursor.close();

        }
        catch (Exception e) {

            //Registrando erro ocorrido
            e.printStackTrace();
            return null;
        }

        //Retornando itens encontrados
        return _return;

    }

    public ArrayList<TodoList> getAllLists() {

        ArrayList<TodoList> _return = new ArrayList<>();

        try {

            //Definindo dados a ser recuperados
            Cursor cursor;
            String[] fields = {
                    DatabaseCreator.FIELD_ID,
                    DatabaseCreator.FIELD_TITLE,
                    DatabaseCreator.FIELD_DATE
            };

            //Recuperando uma instancia de leitura do banco de dados
            this.db = database.getReadableDatabase();

            //Recuperando dados do banco
            cursor = db.query(
                    DatabaseCreator.TODOLIST_TABLE,
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

            //Fechando conexão com banco de dados
            db.close();

            //Formatando em formato de arraylist
            do {

                //Colocando em um objeto de Item de Lista
                TodoList todoList = new TodoList();

                todoList.setId(cursor.getInt(cursor.getColumnIndex(DatabaseCreator.FIELD_ID)));
                todoList.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseCreator.FIELD_TITLE)));
                todoList.setDate(cursor.getString(cursor.getColumnIndex(DatabaseCreator.FIELD_DATE)));

                //Adicionando registro encontrado no vetor de retorno
                _return.add(todoList);

            } while (cursor.moveToNext());

            //Fechando cursor
            cursor.close();

        }
        catch (Exception e) {

            //Registrando erro ocorrido
            e.printStackTrace();
            return null;
        }

        //Retornando itens encontrados
        return _return;

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
