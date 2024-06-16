package br.edu.fateczl.saudementalapp.control;

import android.os.Bundle;

import java.util.List;

import br.edu.fateczl.saudementalapp.model.Entrada;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.Pagina;
import br.edu.fateczl.saudementalapp.model.Pessoa;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.model.factory.ColabFactory;
import br.edu.fateczl.saudementalapp.model.factory.EntradaFactory;
import br.edu.fateczl.saudementalapp.model.factory.PessoaFactory;
import br.edu.fateczl.saudementalapp.persistence.EntradaDAO;

public class EntradaController implements IPageControl<Entrada> {

    private final EntradaDAO eDAO;
    public EntradaController(EntradaDAO eDAO) {
        this.eDAO = eDAO;
    }

    @Override
    public void inserir(Entrada entrada) throws Exception {
        if (eDAO.open() != null) {
            eDAO.open();
        }
        eDAO.insert(entrada);
        eDAO.close();
    }

    @Override
    public void atualizar(Entrada entrada) throws Exception {
        if (eDAO.open() != null) {
            eDAO.open();
        }
        eDAO.update(entrada);
        eDAO.close();
    }

    @Override
    public void remover(Entrada entrada) throws Exception {
        if (eDAO.open() != null) {
            eDAO.open();
        }
        eDAO.delete(entrada);
        eDAO.close();
    }


    public List<Entrada> listByUser(String login) throws Exception {
        if (eDAO.open() != null) {
            eDAO.open();
        }
        List<Entrada> myEntries = eDAO.listByUser(login);
        eDAO.close();
        return myEntries;
    }

    @Override
    public Entrada montarObjeto(Bundle args) throws Exception {
        checkEmptyFields(args);
        IFactory<Usuario> usrFactory = new PessoaFactory();
        Pessoa pessoa = (Pessoa) usrFactory.newInstance();
        pessoa.setLogin(args.getString("userlogin"));

        IFactory<Pagina> pagFactory = new EntradaFactory();
        Entrada ent = (Entrada) pagFactory.newInstance();
        ent.setDataPost(args.getString("dataPost"));
        ent.setTitulo(args.getString("titulo"));
        ent.setConteudo(args.getString("conteudo"));
        ent.setSentimentos(args.getString("sentimento"));
        ent.setUsuario(pessoa);
        return ent;
    }

    @Override
    public void checkEmptyFields(Bundle args) throws Exception {
        String errMsg = "Campos Vazios!";
        if (args.getString("userlogin") == null || args.getString("userlogin").isEmpty()) throw new Exception(errMsg);
        if (args.getString("dataPost") == null || args.getString("dataPost").isEmpty()) throw new Exception(errMsg);
        if (args.getString("titulo") == null || args.getString("titulo").isEmpty()) throw new Exception(errMsg);
        if (args.getString("conteudo") == null || args.getString("conteudo").isEmpty()) throw new Exception(errMsg);
        if (args.getString("sentimento") == null || args.getString("sentimento").isEmpty()) throw new Exception(errMsg);
    }
}
