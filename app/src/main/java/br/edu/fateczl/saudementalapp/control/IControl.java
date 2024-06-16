package br.edu.fateczl.saudementalapp.control;

import android.os.Bundle;

public interface IControl<T> {
    void inserir(T t) throws Exception;

    void atualizar(T t) throws Exception;

    void remover(T t) throws Exception;
    T montarObjeto(Bundle args) throws Exception;

    void checkEmptyFields(Bundle args) throws Exception;
}
