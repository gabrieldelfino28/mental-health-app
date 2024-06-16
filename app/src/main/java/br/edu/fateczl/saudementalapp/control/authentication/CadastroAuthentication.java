package br.edu.fateczl.saudementalapp.control.authentication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import br.edu.fateczl.saudementalapp.control.UserCollabController;
import br.edu.fateczl.saudementalapp.control.IUserControl;
import br.edu.fateczl.saudementalapp.control.UserPessoaController;
import br.edu.fateczl.saudementalapp.control.UsersController;
import br.edu.fateczl.saudementalapp.model.Colaborador;
import br.edu.fateczl.saudementalapp.model.Pessoa;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.persistence.ColaboradorDAO;
import br.edu.fateczl.saudementalapp.persistence.PessoaDAO;

@SuppressWarnings("all")
public class CadastroAuthentication extends Authentication {
    private final Context context;
    public CadastroAuthentication(Context context) {
        this.context = context;
    }
    public void sendUser(@NonNull Bundle args) throws Exception {
        if (args.get("isCollab").toString().contains("true")) {
            IUserControl<Colaborador> userCont = new UserCollabController(new ColaboradorDAO(context));
            Colaborador colaborador = userCont.montarObjeto(args);
            checkUser(colaborador);
            userCont.inserir(colaborador);
        }
        if (args.get("isCollab").toString().contains("false")) {
            IUserControl<Pessoa> userCont = new UserPessoaController(new PessoaDAO(context));
            Pessoa pessoa = userCont.montarObjeto(args);
            checkUser(pessoa);
            userCont.inserir(pessoa);
        }
    }

    private void checkUser(Usuario usr) throws Exception{
        if (isRegistered(usr, context)) throw new Exception("Usuario já Cadastrado!");
    }
}
