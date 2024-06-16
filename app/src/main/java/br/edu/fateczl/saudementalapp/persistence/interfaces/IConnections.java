package br.edu.fateczl.saudementalapp.persistence.interfaces;

import java.sql.SQLException;

public interface IConnections<T> {
    T open() throws SQLException;

    void close();
}
