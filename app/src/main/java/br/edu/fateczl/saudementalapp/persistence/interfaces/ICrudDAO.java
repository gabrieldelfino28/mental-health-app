package br.edu.fateczl.saudementalapp.persistence.interfaces;

import android.content.ContentValues;

import java.sql.SQLException;
import java.util.List;

public interface ICrudDAO<T> {
    void insert(T t) throws SQLException;

    int update(T t) throws SQLException;

    void delete(T t) throws SQLException;

}
