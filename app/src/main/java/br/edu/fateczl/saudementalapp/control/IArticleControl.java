package br.edu.fateczl.saudementalapp.control;

import java.sql.SQLException;
import java.util.List;

public interface IArticleControl<T> extends IControl<T>{
    List<T> listByUser(String userLogin) throws Exception;
    List<T> listAll() throws SQLException;
}
