package br.edu.fateczl.saudementalapp.control.authentication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.List;

import br.edu.fateczl.saudementalapp.control.UsersController;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.factory.PessoaFactory;

public class LoginAuthentication extends Authentication {
    public Usuario getUser(@NonNull Bundle args) {
        IFactory<Usuario> iFactory = new PessoaFactory();
        Usuario p = iFactory.newInstance();
        p.setLogin(args.getString("login"));
        p.setSenha(args.getString("senha")); //collabs empre fica como falso xD
        return p;
    }

    public Usuario getAllUserData(Usuario usr, Context context) throws Exception {
        UsersController cont = new UsersController();
        List<Usuario> usuarios = cont.listar(context);
        for (Usuario user : usuarios) {
            if (usr.getLogin().equals(user.getLogin())) {
                if (usr.getSenha().equals(user.getSenha())) {
                    return user;
                }
            }
        }
        return usr;
    }
}
