package br.edu.fateczl.saudementalapp.ui.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.edu.fateczl.saudementalapp.R;
import br.edu.fateczl.saudementalapp.control.IUserControl;
import br.edu.fateczl.saudementalapp.control.UserPessoaController;
import br.edu.fateczl.saudementalapp.model.Pessoa;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.factory.PessoaFactory;
import br.edu.fateczl.saudementalapp.persistence.PessoaDAO;


public class HomePessoaFragment extends Fragment {

    private View view;
    private TextView tvWelcome;
    private final String USR_LOGIN;
    public HomePessoaFragment(String login) {
        super();
        this.USR_LOGIN = login;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_pessoa, container, false);
        tvWelcome = view.findViewById(R.id.tvWelcome);
        operacaoBuscar();
        return view;
    }

    private void operacaoBuscar() {
        IUserControl<Pessoa> cont = new UserPessoaController(new PessoaDAO(view.getContext()));
        IFactory<Usuario> factory = new PessoaFactory();
        Pessoa usr = (Pessoa) factory.newInstance();
        try {
            usr.setLogin(USR_LOGIN);
            usr = cont.buscar(usr);
        } catch (Exception e) {
            Log.wtf("errXD", e.getMessage());
        }
        tvWelcome.setText(String.format(getString(R.string.welcome),usr.getNome()));
    }
}