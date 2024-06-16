package br.edu.fateczl.saudementalapp.control;

import android.os.Bundle;

import java.util.List;

import br.edu.fateczl.saudementalapp.model.Colaborador;
import br.edu.fateczl.saudementalapp.model.Pessoa;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.model.factory.ColabFactory;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.persistence.ColaboradorDAO;

public class UserCollabController implements IUserControl<Colaborador> {
    private final ColaboradorDAO cDAO;
    public UserCollabController(ColaboradorDAO cDAO) {
        this.cDAO = cDAO;
    }
    @Override
    public void inserir(Colaborador colaborador) throws Exception {
        if (cDAO.open() != null) {
            cDAO.open();
        }
        cDAO.insert(colaborador);
        cDAO.close();
    }

    @Override
    public void atualizar(Colaborador colaborador) throws Exception {
        if (cDAO.open() != null) {
            cDAO.open();
        }
        cDAO.update(colaborador);
        cDAO.close();
    }

    @Override
    public void remover(Colaborador colaborador) throws Exception {
        if (cDAO.open() != null) {
            cDAO.open();
        }
        cDAO.delete(colaborador);
        cDAO.close();
    }

    @Override
    public Colaborador buscar(Colaborador colaborador) throws Exception {
        if (cDAO.open() != null) {
            cDAO.open();
        }
        Colaborador colab = cDAO.findOne(colaborador);
        cDAO.close();
        return colab;
    }

    @Override
    public List<Colaborador> listar() throws Exception {
        if (cDAO.open() != null) {
            cDAO.open();
        }
        List<Colaborador> colabs = cDAO.findAll();
        cDAO.close();
        return colabs;
    }

    @Override
    public Colaborador montarObjeto(Bundle args) throws Exception {
        checkEmptyFields(args);
        IFactory<Usuario> factory = new ColabFactory();
        Colaborador colaborador = (Colaborador) factory.newInstance();
        colaborador.setLogin(args.getString("login"));
        colaborador.setNome(args.getString("nome"));
        colaborador.setEspecialidade(args.getString("espec"));
        colaborador.setSenha(args.getString("senha"));
        colaborador.setCollab(true);
        return colaborador;
    }

    @Override
    public void checkEmptyFields(Bundle args) throws Exception {
        String errMsg = "Campos Vazios!";
        if (args.getString("login") == null || args.getString("login").isEmpty()) throw new Exception(errMsg);
        if (args.getString("nome") == null || args.getString("nome").isEmpty()) throw new Exception(errMsg);
        if (args.getString("espec") == null || args.getString("espec").isEmpty()) throw new Exception(errMsg);
        if (args.getString("senha") == null || args.getString("senha").isEmpty()) throw new Exception(errMsg);
    }
}
