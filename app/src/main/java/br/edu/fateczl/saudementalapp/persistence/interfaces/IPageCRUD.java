package br.edu.fateczl.saudementalapp.persistence.interfaces;

import android.content.ContentValues;

import java.util.List;

public interface IPageCRUD<T> extends ICrudDAO<T> {
    List<T> listByUser(String num) throws Exception;
    ContentValues getPageValues(T t);
}
