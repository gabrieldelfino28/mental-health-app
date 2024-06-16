package br.edu.fateczl.saudementalapp.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDAO extends SQLiteOpenHelper {
    private static final String DATA_BASE = "HEALTH.DB";
    private static final int DATA_BASE_VER = 1;
    public GenericDAO(Context context) {
        super(context, DATA_BASE, null, DATA_BASE_VER);
    }
    /*Criação das Tabelas do Banco SQLite*/
    private static final String CREATE_USUARIO =
            "CREATE TABLE Usuario( " +
                    "login varchar(30) UNIQUE PRIMARY KEY, " +
                    "nome varchar(100), " +
                    "senha varchar(50) " +
                    ");";
    private static final String CREATE_PESSOA =
            "CREATE TABLE Pessoa( " +
                    "login varchar(30) UNIQUE, " +
                    "email varchar(50)," +
                    "PRIMARY KEY(login)," +
                    "FOREIGN KEY(login) REFERENCES Usuario(login) " +
                    ");";
    private static final String CREATE_COLABORADOR =
            "CREATE TABLE Colaborador( " +
                    "login varchar(30) UNIQUE, " +
                    "especialidade varchar(50), " +
                    "PRIMARY KEY(login), " +
                    "FOREIGN KEY(login) REFERENCES Usuario(login) " +
                    ");";
    private static final String CREATE_PAGINA =
            "CREATE TABLE Pagina( " +
                    "titulo varchar(30) PRIMARY KEY, " +
                    "dataPost varchar(30), " +
                    "userlogin varchar(30) , " +
                    "FOREIGN KEY(userlogin) REFERENCES Usuario(login) " +
                    ");";
    private static final String CREATE_ENTRADA =
            "CREATE TABLE Entrada( " +
                    "titulo varchar(30), " +
                    "conteudo varchar(800), " +
                    "sentimentos varchar(50), " +
                    "PRIMARY KEY(titulo), " +
                    "FOREIGN KEY(titulo) REFERENCES Pagina(titulo) " +
                    ");";
    private static final String CREATE_ARTIGO =
            "CREATE TABLE Artigo( " +
                    "titulo varchar(30), " +
                    "conteudo varchar(800), " +
                    "fonte varchar(100), " +
                    "PRIMARY KEY(titulo), " +
                    "FOREIGN KEY(titulo) REFERENCES Pagina(titulo) " +
                    ");";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USUARIO);
        db.execSQL(CREATE_PESSOA);
        db.execSQL(CREATE_COLABORADOR);
        db.execSQL(CREATE_PAGINA);
        db.execSQL(CREATE_ENTRADA);
        db.execSQL(CREATE_ARTIGO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS Pessoa");
            db.execSQL("DROP TABLE IF EXISTS Colaborador");
            db.execSQL("DROP TABLE IF EXISTS Entrada");
            db.execSQL("DROP TABLE IF EXISTS Artigo");
            db.execSQL("DROP TABLE IF EXISTS Pagina");
            db.execSQL("DROP TABLE IF EXISTS Usuario");
            onCreate(db);
        }
    }
}
