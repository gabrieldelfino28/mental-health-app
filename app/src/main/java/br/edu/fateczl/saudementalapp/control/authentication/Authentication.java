package br.edu.fateczl.saudementalapp.control.authentication;

import android.content.Context;

import java.util.List;

import br.edu.fateczl.saudementalapp.control.UsersController;
import br.edu.fateczl.saudementalapp.model.Usuario;
public abstract class Authentication {
    public boolean isRegistered(Usuario usr, Context context) throws Exception {
        UsersController cont = new UsersController();
        List<Usuario> list = cont.listar(context);
        for (Usuario u : list) {
            if (usr.getLogin().contains(u.getLogin())) {
                if (usr.getSenha().contains(u.getSenha())) {
                    //usr.setNome(u.getNome());
                    return true;
                }
            }
        }
        return false;
    }
}
