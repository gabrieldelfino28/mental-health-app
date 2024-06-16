package br.edu.fateczl.saudementalapp.model.factory;

import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.Pessoa;
import br.edu.fateczl.saudementalapp.model.Usuario;

public class PessoaFactory implements IFactory<Usuario> {
    @Override
    public Usuario newInstance() {
        return new Pessoa();
    }
}
