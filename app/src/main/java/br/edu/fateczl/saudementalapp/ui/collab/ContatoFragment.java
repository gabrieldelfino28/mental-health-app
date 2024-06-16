package br.edu.fateczl.saudementalapp.ui.collab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.fateczl.saudementalapp.R;

public class ContatoFragment extends Fragment {
    private View view;
    private final String USR_LOGIN;
    public ContatoFragment(String usrLogin) {
        super();
        this.USR_LOGIN = usrLogin;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contato, container, false);
        return view;
    }
}