package br.edu.fateczl.saudementalapp.control;

import android.os.Bundle;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.saudementalapp.model.Artigo;
import br.edu.fateczl.saudementalapp.model.Colaborador;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.Pagina;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.model.factory.ArtigoFactory;
import br.edu.fateczl.saudementalapp.model.factory.ColabFactory;
import br.edu.fateczl.saudementalapp.persistence.ArtigoDAO;

public class ArtigoController implements IArticleControl<Artigo> {
    private final ArtigoDAO aDAO;
    public ArtigoController(ArtigoDAO aDAO) {
        this.aDAO = aDAO;
    }
    @Override
    public void inserir(Artigo artigo) throws Exception {
        if (aDAO.open() != null) {
            aDAO.open();
        }
        aDAO.insert(artigo);
        aDAO.close();
    }

    @Override
    public void atualizar(Artigo artigo) throws Exception {
        if (aDAO.open() != null) {
            aDAO.open();
        }
        aDAO.update(artigo);
        aDAO.close();
    }

    @Override
    public void remover(Artigo artigo) throws Exception {
        if (aDAO.open() != null) {
            aDAO.open();
        }
        aDAO.delete(artigo);
        aDAO.close();
    }

    @Override
    public List<Artigo> listByUser(String userLogin) throws Exception {
        if (aDAO.open() != null) {
            aDAO.open();
        }
        List<Artigo> myArticles = aDAO.listByUser(userLogin);
        aDAO.close();
        return myArticles;
    }

    @Override
    public List<Artigo> listAll() throws SQLException {
        if (aDAO.open() != null) {
            aDAO.open();
        }
        List<Artigo> allArticles = aDAO.listAll();
        aDAO.close();
        return allArticles;
    }

    @Override
    public Artigo montarObjeto(Bundle args) throws Exception {
        checkEmptyFields(args);
        IFactory<Usuario> usrFactory = new ColabFactory();
        Colaborador col = (Colaborador) usrFactory.newInstance();
        col.setLogin(args.getString("userlogin"));

        IFactory<Pagina> pagFactory = new ArtigoFactory();
        Artigo art = (Artigo) pagFactory.newInstance();
        art.setTitulo(args.getString("titulo"));
        art.setDataPost(args.getString("dataPost"));
        art.setConteudo(args.getString("conteudo"));
        art.setFonte(args.getString("fonte"));
        art.setUsuario(col);
        return art;
    }

    @Override
    public void checkEmptyFields(Bundle args) throws Exception {
        String errMsg = "Campos Vazios!";

        if (args.getString("userlogin") == null || args.getString("userlogin").isEmpty()) throw new Exception(errMsg);
        if (args.getString("titulo") == null || args.getString("titulo").isEmpty()) throw new Exception(errMsg);
        if (args.getString("dataPost") == null || args.getString("dataPost").isEmpty()) throw new Exception(errMsg);
        if (args.getString("conteudo") == null || args.getString("conteudo").isEmpty()) throw new Exception(errMsg);
        if (args.getString("fonte") == null || args.getString("fonte").isEmpty()) throw new Exception(errMsg);
    }
}
