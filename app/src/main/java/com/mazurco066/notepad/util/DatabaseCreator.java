package com.mazurco066.notepad.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseCreator extends SQLiteOpenHelper {

    //Atributos
    private final static String DATABASE = "notepad.dp";
    private final static String DEFAULT_TABLE = "notes";
    private final static String FIELD_ID = "_id";
    private final static String FIELD_DATE = "_date";
    private final static String FIELD_TITLE = "_title";
    private final static String FIELD_CONTENT = "_content";
    private final static int VERSION = 1;

    //construtor padrão
    public DatabaseCreator(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    //Métodos de implementação requerida
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Criando String de  Criação do Banco
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS ").append(DEFAULT_TABLE).append("( ");
        sql.append(FIELD_ID).append(" integer primary key autoincrement,");
        sql.append(FIELD_DATE).append(" VARCHAR(10),");
        sql.append(FIELD_TITLE).append(" VARCHAR(18),");
        sql.append(FIELD_CONTENT).append(" text )");

        //Recuperando comando sql gerado
        String sqlCommand = sql.toString();

        //Criando a tabela
        sqLiteDatabase.execSQL(sqlCommand);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DEFAULT_TABLE);
        onCreate(sqLiteDatabase);
    }

}
