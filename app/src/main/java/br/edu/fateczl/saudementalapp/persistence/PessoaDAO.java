package br.edu.fateczl.saudementalapp.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.Pessoa;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.model.factory.PessoaFactory;
import br.edu.fateczl.saudementalapp.persistence.interfaces.IConnections;
import br.edu.fateczl.saudementalapp.persistence.interfaces.IUserCRUD;

public class PessoaDAO implements IConnections<PessoaDAO>, IUserCRUD<Pessoa> {

    private final Context context;
    private GenericDAO gDAO;
    private SQLiteDatabase db;
    public PessoaDAO(Context context) {
        this.context = context;
    }

    @Override
    public PessoaDAO open() throws SQLException {
        gDAO = new GenericDAO(context);
        db = gDAO.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDAO.close();
    }

    @Override
    public void insert(Pessoa pessoa) throws SQLException {
        db.insert("Usuario", null, getUserValues(pessoa));
        db.insert("Pessoa", null, getPessoaValues(pessoa));
    }

    @Override
    public int update(Pessoa pessoa) throws SQLException {
        String[] whereArgs = {pessoa.getLogin()};
        db.update("Usuario", getUserValues(pessoa), "login = ?", whereArgs);
        db.update("Pessoa", getPessoaValues(pessoa), "login = ?", whereArgs);
        return 0;
    }

    @Override
    public void delete(Pessoa pessoa) throws SQLException {
        String[] whereArgs = {pessoa.getLogin()};
        db.delete("Pessoa", "login = ?", whereArgs);
        db.delete("Usuario", "login = ?", whereArgs);
    }

    @SuppressLint("Range")
    @Override
    public Pessoa findOne(Pessoa pessoa) throws SQLException {
        String[] whereArgs = {pessoa.getLogin()};
        String query = "SELECT u.*, p.email FROM Usuario u INNER JOIN Pessoa p ON u.login = p.login AND u.login = ?";
        Cursor cursor = db.rawQuery(query,whereArgs);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()){
            pessoa.setLogin(cursor.getString(cursor.getColumnIndex("login")));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            pessoa.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            pessoa.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
        }
        cursor.close();
        return pessoa;
    }

    @SuppressLint("Range")
    @Override
    public List<Pessoa> findAll() throws SQLException {
        IFactory<Usuario> iFac = new PessoaFactory();
        List<Pessoa> pessoas = new ArrayList<>();
        String query = "SELECT u.*, p.email FROM Usuario u INNER JOIN Pessoa p ON u.login = p.login";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            Pessoa pessoa = (Pessoa) iFac.newInstance();
            pessoa.setLogin(cursor.getString(cursor.getColumnIndex("login")));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            pessoa.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            pessoa.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            pessoas.add(pessoa);
            cursor.moveToNext();
        }
        cursor.close();
        return pessoas;
    }

    @Override
    public ContentValues getUserValues(Pessoa pessoa) {
        ContentValues c = new ContentValues();
        c.put("login", pessoa.getLogin());
        c.put("nome", pessoa.getNome());
        c.put("senha", pessoa.getSenha());
        return c;
    }

    private ContentValues getPessoaValues(Pessoa pessoa) {
        ContentValues c = new ContentValues();
        c.put("login", pessoa.getLogin());
        c.put("email", pessoa.getEmail());
        return c;
    }
}
