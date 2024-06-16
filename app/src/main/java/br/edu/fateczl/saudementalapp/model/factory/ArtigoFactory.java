package br.edu.fateczl.saudementalapp.model.factory;

import br.edu.fateczl.saudementalapp.model.Artigo;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.Pagina;

public class ArtigoFactory implements IFactory<Pagina> {
    @Override
    public Pagina newInstance() {
        return new Artigo();
    }
}
