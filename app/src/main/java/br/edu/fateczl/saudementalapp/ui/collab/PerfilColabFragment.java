package br.edu.fateczl.saudementalapp.ui.collab;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.fateczl.saudementalapp.LoginActivity;
import br.edu.fateczl.saudementalapp.R;
import br.edu.fateczl.saudementalapp.UIDataMapping;
import br.edu.fateczl.saudementalapp.control.UserCollabController;
import br.edu.fateczl.saudementalapp.control.IUserControl;
import br.edu.fateczl.saudementalapp.model.Colaborador;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.model.factory.ColabFactory;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.persistence.ColaboradorDAO;

public class PerfilColabFragment extends Fragment implements UIDataMapping {

    private View view;
    private EditText inLogin, inNome, inEspec, inSenha;
    private final String USR_LOGIN;
    public PerfilColabFragment(String login) {
        super();
        this.USR_LOGIN = login;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_perfil_colab, container, false);
        Button btnUpdate, btnDelete;
        btnUpdate = view.findViewById(R.id.btnUpdatePerfilC);
        btnDelete = view.findViewById(R.id.btnDeletePerfilC);
        inLogin = view.findViewById(R.id.inLoginPerfilC);
        inNome = view.findViewById(R.id.inNomePerfilC);
        inEspec = view.findViewById(R.id.inEspecPerfilC);
        inSenha = view.findViewById(R.id.inSenhaPerfilC);

        operacaoBuscar();
        btnUpdate.setOnClickListener(op -> operacaoAtualizar());
        btnDelete.setOnClickListener(op -> operacaoExcluir());
        return view;
    }

    private void operacaoExcluir() {
        IUserControl<Colaborador> cont = new UserCollabController(new ColaboradorDAO(view.getContext()));
        try {
            Colaborador p = cont.montarObjeto(getInputData());
            cont.remover(p);
            Toast.makeText(view.getContext(), "Perfil Excluído!", Toast.LENGTH_LONG).show();
            goToLogin();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void operacaoAtualizar() {
        IUserControl<Colaborador> cont = new UserCollabController(new ColaboradorDAO(view.getContext()));
        try {
            Colaborador p = cont.montarObjeto(getInputData());
            cont.atualizar(p);
            Toast.makeText(view.getContext(), "Perfil Atualizado!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void operacaoBuscar() {
        IUserControl<Colaborador> cont = new UserCollabController(new ColaboradorDAO(view.getContext()));
        IFactory<Usuario> factory = new ColabFactory();
        Usuario usr =  factory.newInstance();
        try {
            usr.setLogin(USR_LOGIN);
            usr = cont.buscar((Colaborador) usr);
            fillFields((Colaborador) usr);
        } catch (Exception e) {
            Log.wtf("err", e.getMessage());
        }
    }

    @Override
    public Bundle getInputData() {
        Bundle b = new Bundle();
        b.putString("login", inLogin.getText().toString());
        b.putString("nome", inNome.getText().toString());
        b.putString("espec", inEspec.getText().toString());
        b.putString("senha", inSenha.getText().toString());
        return b;
    }

    private void fillFields(Colaborador p) {
        inLogin.setText(p.getLogin());
        inNome.setText(p.getNome());
        inEspec.setText(p.getEspecialidade());
        inSenha.setText(p.getSenha());
    }

    private void goToLogin() {
        Intent in = new Intent(getActivity(), LoginActivity.class);
        startActivity(in);
        getActivity().finish();
    }
}