package br.edu.fateczl.saudementalapp.model.factory;

import br.edu.fateczl.saudementalapp.model.Colaborador;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.Usuario;

public class ColabFactory implements IFactory<Usuario> {
    @Override
    public Usuario newInstance() {
        return new Colaborador();
    }
}
