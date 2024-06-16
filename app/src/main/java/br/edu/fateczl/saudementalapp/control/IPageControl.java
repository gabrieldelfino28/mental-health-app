package br.edu.fateczl.saudementalapp.control;

import java.util.List;

public interface IPageControl<T> extends IControl<T> {
    List<T> listByUser(String login) throws Exception;
}
