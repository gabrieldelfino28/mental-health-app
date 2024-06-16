package br.edu.fateczl.saudementalapp.persistence.interfaces;

import android.content.ContentValues;

import java.sql.SQLException;
import java.util.List;

public interface IArticleCRUD<T> extends ICrudDAO<T>{
    List<T> listAll() throws SQLException;
    ContentValues getPageValues(T t);
}
