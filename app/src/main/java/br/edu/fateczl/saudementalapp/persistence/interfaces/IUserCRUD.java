package br.edu.fateczl.saudementalapp.persistence.interfaces;

import android.content.ContentValues;

import java.sql.SQLException;
import java.util.List;

public interface IUserCRUD<T> extends ICrudDAO<T> {
    T findOne(T t) throws SQLException;

    List<T> findAll() throws SQLException;

    ContentValues getUserValues(T t);
}
