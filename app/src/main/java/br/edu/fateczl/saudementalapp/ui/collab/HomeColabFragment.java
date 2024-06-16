package br.edu.fateczl.saudementalapp.ui.collab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.edu.fateczl.saudementalapp.R;
import br.edu.fateczl.saudementalapp.control.UserCollabController;
import br.edu.fateczl.saudementalapp.control.IUserControl;
import br.edu.fateczl.saudementalapp.model.Colaborador;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.model.factory.ColabFactory;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.persistence.ColaboradorDAO;

public class HomeColabFragment extends Fragment {

    private View view;
    private final String USR_LOGIN;
    private TextView tvWelcome;
    public HomeColabFragment(String login) {
        super();
        USR_LOGIN = login;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_colab, container, false);
        tvWelcome = view.findViewById(R.id.tvWelcome);
        operacaoBuscar();
        return view;
    }

    private void operacaoBuscar() {
        IUserControl<Colaborador> cont = new UserCollabController(new ColaboradorDAO(view.getContext()));
        IFactory<Usuario> factory = new ColabFactory();
        Colaborador usr = (Colaborador) factory.newInstance();
        try {
            usr.setLogin(USR_LOGIN);
            usr = cont.buscar(usr);
        } catch (Exception e) {
            Log.wtf("errXD", e.getMessage());
        }
        tvWelcome.setText(String.format(getString(R.string.welcome),usr.getNome()));
    }
}