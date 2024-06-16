package br.edu.fateczl.saudementalapp.control;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.saudementalapp.model.Colaborador;
import br.edu.fateczl.saudementalapp.model.Pessoa;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.persistence.ColaboradorDAO;
import br.edu.fateczl.saudementalapp.persistence.PessoaDAO;

public class UsersController {
    //Importante ser estático na memória para não limpar a lista na chamada
    private final static List<Usuario> usuarios = new ArrayList<>();
    public UsersController() {
        super();
    }
    public List<Usuario> listar(Context context) throws Exception{
        IUserControl<Pessoa> pCont = new UserPessoaController(new PessoaDAO(context));
        IUserControl<Colaborador> cCont = new UserCollabController(new ColaboradorDAO(context));
        append(pCont.listar());
        append(cCont.listar());
        return usuarios;
    }


    private void append(List<? extends Usuario> list) {
        usuarios.addAll(list);
    }
}
