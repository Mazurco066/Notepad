package com.mazurco066.notepad.dao;

import android.content.ContentValues;
import android.content.Context;
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

            return true;

        }
        catch (Exception e) {

            //Registrando erro gerado
            e.printStackTrace();
            return false;
        }
    }

}
