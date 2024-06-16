package br.edu.fateczl.saudementalapp.control;

import java.util.List;

public interface IUserControl<T> extends IControl<T>{
    T buscar(T t) throws Exception;

    List<T> listar() throws Exception;
}
