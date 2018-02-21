package com.mazurco066.notepad.dao;

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

}
