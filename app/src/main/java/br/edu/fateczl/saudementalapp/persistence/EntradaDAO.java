package br.edu.fateczl.saudementalapp.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.saudementalapp.model.Entrada;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.Pagina;
import br.edu.fateczl.saudementalapp.model.Pessoa;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.model.factory.EntradaFactory;
import br.edu.fateczl.saudementalapp.model.factory.PessoaFactory;
import br.edu.fateczl.saudementalapp.persistence.interfaces.IConnections;
import br.edu.fateczl.saudementalapp.persistence.interfaces.IPageCRUD;

public class EntradaDAO implements IConnections<EntradaDAO>, IPageCRUD<Entrada> {
    private final Context context;
    private GenericDAO gDAO;
    private SQLiteDatabase db;

    public EntradaDAO(Context context) {
        this.context = context;
    }

    @Override
    public EntradaDAO open() throws SQLException {
        gDAO = new GenericDAO(context);
        db = gDAO.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDAO.close();
    }

    @Override
    public void insert(Entrada entrada) throws SQLException {
        db.insert("Pagina", null, getPageValues(entrada));
        db.insert("Entrada", null, getEntryValues(entrada));
    }

    @Override
    public int update(Entrada entrada) throws SQLException {
        String[] whereArgs = {entrada.getTitulo()};
        db.update("Pagina", getPageValues(entrada), "titulo = ?", whereArgs);
        db.update("Entrada", getEntryValues(entrada), "titulo = ?", whereArgs);
        return 0;
    }

    @Override
    public void delete(Entrada entrada) throws SQLException {
        String[] whereArgs = {entrada.getTitulo()};
        db.delete("Pagina", "titulo = ?", whereArgs);
        db.delete("Entrada", "titulo = ?", whereArgs);
    }

    @SuppressLint("Range")
    @Override
    public List<Entrada> listByUser(String login) throws SQLException {
        IFactory<Pagina> entFactory = new EntradaFactory();
        IFactory<Usuario> usrFactory = new PessoaFactory();
        List<Entrada> diarioEntradas = new ArrayList<>();
        String query =
                "SELECT p.*, e.sentimentos, e.conteudo, u.nome, u.senha FROM Entrada e INNER JOIN Pagina p ON e.titulo = p.titulo INNER JOIN Usuario u ON u.login = p.userlogin AND p.userlogin = ?";
        String[] args = {login};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            Pessoa usr = (Pessoa) usrFactory.newInstance();
            usr.setLogin(cursor.getString(cursor.getColumnIndex("userlogin")));
            usr.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            usr.setSenha(cursor.getString(cursor.getColumnIndex("senha")));

            Entrada ent = (Entrada) entFactory.newInstance();
            ent.setDataPost(cursor.getString(cursor.getColumnIndex("dataPost")));
            ent.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));
            ent.setConteudo(cursor.getString(cursor.getColumnIndex("conteudo")));
            ent.setSentimentos(cursor.getString(cursor.getColumnIndex("sentimentos")));
            ent.setUsuario(usr);

            diarioEntradas.add(ent);

            cursor.moveToNext();
        }
        cursor.close();
        return diarioEntradas;
    }

    @Override
    public ContentValues getPageValues(Entrada entrada) {
        ContentValues c = new ContentValues();
        c.put("titulo", entrada.getTitulo());
        c.put("dataPost", entrada.getDataPost());
        c.put("userlogin", entrada.getUsuario().getLogin());
        return c;
    }

    private ContentValues getEntryValues(Entrada entrada) {
        ContentValues c = new ContentValues();
        c.put("titulo", entrada.getTitulo());
        c.put("conteudo", entrada.getConteudo());
        c.put("sentimentos", entrada.getSentimentos());
        return c;
    }
}
