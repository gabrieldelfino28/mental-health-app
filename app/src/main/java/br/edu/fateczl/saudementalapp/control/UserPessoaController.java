package br.edu.fateczl.saudementalapp.control;

import android.os.Bundle;

import java.util.List;

import br.edu.fateczl.saudementalapp.model.Pessoa;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.factory.PessoaFactory;
import br.edu.fateczl.saudementalapp.persistence.PessoaDAO;

public class UserPessoaController implements IUserControl<Pessoa> {

    private final PessoaDAO pDAO;
    public UserPessoaController(PessoaDAO pDAO) {
        this.pDAO = pDAO;
    }
    @Override
    public void inserir(Pessoa pessoa) throws Exception {
        if (pDAO.open() != null) {
            pDAO.open();
        }
        pDAO.insert(pessoa);
        pDAO.close();
    }

    @Override
    public void atualizar(Pessoa pessoa) throws Exception {
        if (pDAO.open() != null) {
            pDAO.open();
        }
        pDAO.update(pessoa);
        pDAO.close();
    }

    @Override
    public void remover(Pessoa pessoa) throws Exception {
        if (pDAO.open() != null) {
            pDAO.open();
        }
        pDAO.delete(pessoa);
        pDAO.close();
    }

    @Override
    public Pessoa buscar(Pessoa pessoa) throws Exception {
        if (pDAO.open() != null) {
            pDAO.open();
        }
        Pessoa p = pDAO.findOne(pessoa);
        pDAO.close();
        return p;
    }

    @Override
    public List<Pessoa> listar() throws Exception {
        if (pDAO.open() != null) {
            pDAO.open();
        }
        List<Pessoa> list = pDAO.findAll();
        pDAO.close();
        return list;
    }

    @Override
    public Pessoa montarObjeto(Bundle args) throws Exception {
        checkEmptyFields(args);
        IFactory<?> factory = new PessoaFactory();
        Pessoa pessoa = (Pessoa) factory.newInstance();
        pessoa.setLogin(args.getString("login"));
        pessoa.setNome(args.getString("nome"));
        pessoa.setEmail(args.getString("email"));
        pessoa.setSenha(args.getString("senha"));
        pessoa.setCollab(false);
        return pessoa;
    }

    public void checkEmptyFields(Bundle args) throws Exception{
        String errMsg = "Campos Vazios!";
        if (args.getString("login") == null || args.getString("login").isEmpty()) throw new Exception(errMsg);
        if (args.getString("nome") == null || args.getString("nome").isEmpty()) throw new Exception(errMsg);
        if (args.getString("email") == null || args.getString("email").isEmpty()) throw new Exception(errMsg);
        if (args.getString("senha") == null || args.getString("senha").isEmpty()) throw new Exception(errMsg);
    }

}
