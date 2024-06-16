package br.edu.fateczl.saudementalapp.model.factory;

import br.edu.fateczl.saudementalapp.model.Entrada;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.Pagina;

public class EntradaFactory implements IFactory<Pagina> {
    @Override
    public Pagina newInstance() {
        return new Entrada();
    }
}
