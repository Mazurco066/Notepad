package com.mazurco066.notepad.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseCreator extends SQLiteOpenHelper {

    //Atributos
    private final static String DATABASE = "notepad.dp";
    public final static String DEFAULT_TABLE = "notes";
    public final static String TODOLIST_TABLE = "todolists";
    public final static String ITEMLIST_TABLE = "itemlist";
    public final static String FIELD_ID = "_id";
    public final static String FIELD_NOTEID = "_noteid";
    public final static String FIELD_TASK = "_task";
    public final static String FIELD_DONE = "_done";
    public final static String FIELD_DATE = "_date";
    public final static String FIELD_TITLE = "_title";
    public final static String FIELD_CONTENT = "_content";
    private final static int VERSION = 1;

    //construtor padrão
    public DatabaseCreator(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    //Métodos de implementação requerida
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {

            //Criando String de Criação do Banco
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

            //Criando String para nova tabela da update de Todo Lists: TodoList
            sql = new StringBuilder();
            sql.append("CREATE TABLE IF NOT EXISTS ").append(TODOLIST_TABLE).append("( ");
            sql.append(FIELD_ID).append(" integer primary key autoincrement,");
            sql.append(FIELD_TITLE).append(" VARCHAR(23))");

            //Recuperando comando sql gerado
            sqlCommand = sql.toString();

            //Criando a Tabela
            sqLiteDatabase.execSQL(sqlCommand);

            //Criando String para nova tabela da update de Todo Lists: ItemList
            sql = new StringBuilder();
            sql.append("CREATE TABLE If NOT EXISTS ").append(ITEMLIST_TABLE).append("( ");
            sql.append(FIELD_NOTEID).append(" integer,");
            sql.append(FIELD_TASK).append(" VARCHAR(50),");
            sql.append(FIELD_DONE).append(" integer,");
            sql.append("FOREIGN KEY (" +  FIELD_NOTEID + ") REFERENCES  " + TODOLIST_TABLE + " (" + FIELD_ID +"))");

            //Recuperando comando sql gerado
            sqlCommand = sql.toString();

            //Criando a Tabela
            sqLiteDatabase.execSQL(sqlCommand);

        }
        catch (Exception e) {

            //Registrando Erro Capturado
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try {

            //Removendo tabela pois versão do app mudou
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DEFAULT_TABLE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TODOLIST_TABLE);
            onCreate(sqLiteDatabase);

        }
        catch (Exception e) {

            //Regitrando erro encontrado
            e.printStackTrace();
        }


    }

}
