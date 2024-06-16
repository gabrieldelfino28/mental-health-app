package br.edu.fateczl.saudementalapp.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.saudementalapp.model.Colaborador;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.model.factory.ColabFactory;
import br.edu.fateczl.saudementalapp.persistence.interfaces.IConnections;
import br.edu.fateczl.saudementalapp.persistence.interfaces.IUserCRUD;

public class ColaboradorDAO implements IConnections<ColaboradorDAO>, IUserCRUD<Colaborador> {

    private final Context context;
    private GenericDAO gDAO;
    private SQLiteDatabase db;

    public ColaboradorDAO(Context context) {
        this.context = context;
    }

    @Override
    public ColaboradorDAO open() throws SQLException {
        gDAO = new GenericDAO(context);
        db = gDAO.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDAO.close();
    }

    @Override
    public void insert(Colaborador colaborador) throws SQLException {
        db.insert("Usuario", null, getUserValues(colaborador));
        db.insert("Colaborador", null, getColabValues(colaborador));
    }

    @Override
    public int update(Colaborador colaborador) throws SQLException {
        String[] whereArgs = {colaborador.getLogin()};
        db.update("Usuario", getUserValues(colaborador), "login = ?", whereArgs);
        db.update("Colaborador", getColabValues(colaborador), "login = ?", whereArgs);
        return 0;
    }

    @Override
    public void delete(Colaborador colaborador) throws SQLException {
        String[] whereArgs = {colaborador.getLogin()};
        db.delete("Colaborador", "login = ?", whereArgs);
        db.delete("Usuario", "login = ?", whereArgs);
    }

    @SuppressLint("Range")
    @Override
    public Colaborador findOne(Colaborador colaborador) throws SQLException {
        String[] whereArgs = {colaborador.getLogin()};
        String query = "SELECT u.*, c.especialidade FROM Usuario u INNER JOIN Colaborador c ON u.login = c.login AND u.login LIKE ?";
        Cursor cursor = db.rawQuery(query,whereArgs);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()){
            colaborador.setLogin(cursor.getString(cursor.getColumnIndex("login")));
            colaborador.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            colaborador.setEspecialidade(cursor.getString(cursor.getColumnIndex("especialidade")));
            colaborador.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
        }
        cursor.close();
        return colaborador;
    }

    @SuppressLint("Range")
    @Override
    public List<Colaborador> findAll() throws SQLException {
        IFactory<Usuario> iFac = new ColabFactory();
        List<Colaborador> lista = new ArrayList<>();
        String query = "SELECT u.*, c.especialidade FROM Usuario u INNER JOIN Colaborador c ON u.login = c.login";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            Colaborador colaborador = (Colaborador) iFac.newInstance();
            colaborador.setLogin(cursor.getString(cursor.getColumnIndex("login")));
            colaborador.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            colaborador.setEspecialidade(cursor.getString(cursor.getColumnIndex("especialidade")));
            colaborador.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            lista.add(colaborador);
            cursor.moveToNext();
        }
        cursor.close();
        return lista;
    }

    @Override
    public ContentValues getUserValues(Colaborador colaborador) {
        ContentValues c = new ContentValues();
        c.put("login", colaborador.getLogin());
        c.put("nome", colaborador.getNome());
        c.put("senha", colaborador.getSenha());
        return c;
    }
    
    private ContentValues getColabValues(Colaborador colaborador) {
        ContentValues c = new ContentValues();
        c.put("login", colaborador.getLogin());
        c.put("especialidade", colaborador.getEspecialidade());
        return c;
    }
}
