package br.edu.fateczl.saudementalapp.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.saudementalapp.model.Artigo;
import br.edu.fateczl.saudementalapp.model.Colaborador;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.Pagina;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.model.factory.ArtigoFactory;
import br.edu.fateczl.saudementalapp.model.factory.ColabFactory;
import br.edu.fateczl.saudementalapp.persistence.interfaces.IArticleCRUD;
import br.edu.fateczl.saudementalapp.persistence.interfaces.IConnections;

public class ArtigoDAO implements IConnections<ArtigoDAO>, IArticleCRUD<Artigo> {
    private final Context context;
    private GenericDAO gDAO;
    private SQLiteDatabase db;
    public ArtigoDAO(Context context) {
        this.context = context;
    }

    @Override
    public ArtigoDAO open() throws SQLException {
        gDAO = new GenericDAO(context);
        db = gDAO.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDAO.close();
    }


    @Override
    public void insert(Artigo artigo) throws SQLException {
        db.insert("Pagina", null, getPageValues(artigo));
        db.insert("Artigo", null, getArticleValues(artigo));
    }

    @Override
    public int update(Artigo artigo) throws SQLException {
        String[] whereArgs = {artigo.getTitulo()};
        db.update("Pagina", getPageValues(artigo), "titulo = ?", whereArgs);
        db.update("Artigo", getArticleValues(artigo), "titulo = ?", whereArgs);
        return 0;
    }

    @Override
    public void delete(Artigo artigo) throws SQLException {
        String[] whereArgs = {artigo.getTitulo()};
        db.delete("Pagina", "titulo = ?", whereArgs);
        db.delete("Artigo", "titulo = ?", whereArgs);
    }

    @SuppressLint("Range")
    public List<Artigo> listByUser(String userLogin) throws Exception {
        IFactory<Pagina> entFactory = new ArtigoFactory();
        IFactory<Usuario> usrFactory = new ColabFactory();
        List<Artigo> meusArtigos = new ArrayList<>();
        String sql = "SELECT p.*, a.fonte, a.conteudo, u.nome, u.senha FROM Pagina p INNER JOIN Artigo a ON a.titulo = p.titulo INNER JOIN Usuario u ON p.userlogin = u.login WHERE p.userlogin = ?";
        String[] args = {userLogin};
        Cursor cursor = db.rawQuery(sql,args);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            Artigo art = (Artigo) entFactory.newInstance();
            Colaborador usr = (Colaborador) usrFactory.newInstance();
            art.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));
            art.setDataPost(cursor.getString(cursor.getColumnIndex("dataPost")));
            usr.setLogin(cursor.getString(cursor.getColumnIndex("userlogin")));
            usr.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            usr.setSenha(cursor.getString(cursor.getColumnIndex("senha")));

            art.setConteudo(cursor.getString(cursor.getColumnIndex("conteudo")));
            art.setFonte(cursor.getString(cursor.getColumnIndex("fonte")));
            art.setUsuario(usr);
            meusArtigos.add(art);

            cursor.moveToNext();
        }
        cursor.close();
        return meusArtigos;
    }

    @SuppressLint("Range")
    @Override
    public List<Artigo> listAll() throws SQLException {
        IFactory<Pagina> entFactory = new ArtigoFactory();
        IFactory<Usuario> usrFactory = new ColabFactory();
        List<Artigo> todosArtigos = new ArrayList<>();
        String query =
                "SELECT p.*, a.fonte, a.conteudo, u.nome, u.senha FROM " +
                "Artigo a INNER JOIN Pagina p ON a.titulo = p.titulo INNER JOIN Usuario u ON " +
                "u.login = p.userLogin";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            Usuario usr = usrFactory.newInstance();
            usr.setLogin(cursor.getString(cursor.getColumnIndex("userlogin")));
            usr.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            usr.setSenha(cursor.getString(cursor.getColumnIndex("senha")));

            Artigo art = (Artigo) entFactory.newInstance();
            art.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));
            art.setDataPost(cursor.getString(cursor.getColumnIndex("dataPost")));
            art.setConteudo(cursor.getString(cursor.getColumnIndex("conteudo")));
            art.setFonte(cursor.getString(cursor.getColumnIndex("fonte")));
            art.setUsuario(usr);
            todosArtigos.add(art);
            cursor.moveToNext();
        }
        cursor.close();
        return todosArtigos;
    }

    @Override
    public ContentValues getPageValues(Artigo artigo) {
        ContentValues c = new ContentValues();
        c.put("titulo", artigo.getTitulo());
        c.put("dataPost", artigo.getDataPost());
        c.put("userlogin", artigo.getUsuario().getLogin());
        return c;
    }

    private ContentValues getArticleValues(Artigo artigo) {
        ContentValues c = new ContentValues();
        c.put("titulo", artigo.getTitulo());
        c.put("conteudo", artigo.getConteudo());
        c.put("fonte", artigo.getFonte());
        return c;
    }
}
