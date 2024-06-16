package br.edu.fateczl.saudementalapp.ui.user;

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
import br.edu.fateczl.saudementalapp.control.IUserControl;
import br.edu.fateczl.saudementalapp.control.UserPessoaController;
import br.edu.fateczl.saudementalapp.model.Pessoa;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.factory.PessoaFactory;
import br.edu.fateczl.saudementalapp.persistence.PessoaDAO;

public class PerfilPessoaFragment extends Fragment implements UIDataMapping {

    private View view;
    private EditText inLogin, inNome, inEmail, inSenha;
    private final String USR_LOGIN;
    public PerfilPessoaFragment(String login) {
        super();
        this.USR_LOGIN = login;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_perfil_pessoa, container, false);
        Button btnUpdate, btnDelete;
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);
        inLogin = view.findViewById(R.id.inLoginProfile);
        inNome = view.findViewById(R.id.inNomeProfile);
        inEmail = view.findViewById(R.id.inEmailProfile);
        inSenha = view.findViewById(R.id.inSenhaProfile);

        operacaoBuscar();
        btnUpdate.setOnClickListener(op -> operacaoAtualizar());
        btnDelete.setOnClickListener(op -> operacaoExcluir());
        return view;
    }

    private void operacaoExcluir() {
        IUserControl<Pessoa> cont = new UserPessoaController(new PessoaDAO(view.getContext()));
        try {
            Pessoa p = cont.montarObjeto(getInputData());
            cont.remover(p);
            Toast.makeText(view.getContext(), "Perfil Excluído!", Toast.LENGTH_LONG).show();
            goToLogin();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void operacaoAtualizar() {
        IUserControl<Pessoa> cont = new UserPessoaController(new PessoaDAO(view.getContext()));
        try {
            Pessoa p = cont.montarObjeto(getInputData());
            cont.atualizar(p);
            Toast.makeText(view.getContext(), "Perfil Atualizado!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void operacaoBuscar() {
        IUserControl<Pessoa> cont = new UserPessoaController(new PessoaDAO(view.getContext()));
        IFactory<Usuario> factory = new PessoaFactory();
        Pessoa usr =  (Pessoa) factory.newInstance();
        try {
            usr.setLogin(USR_LOGIN);
            usr = cont.buscar(usr);
            fillFields(usr);
        } catch (Exception e) {
            Log.wtf("err", e.getMessage());
        }
    }

    @Override
    public Bundle getInputData() {
        Bundle b = new Bundle();
        b.putString("login", inLogin.getText().toString());
        b.putString("nome", inNome.getText().toString());
        b.putString("email", inEmail.getText().toString());
        b.putString("senha", inSenha.getText().toString());
        return b;
    }

    private void fillFields(Pessoa p) {
        inLogin.setText(p.getLogin());
        inNome.setText(p.getNome());
        inEmail.setText(p.getEmail());
        inSenha.setText(p.getSenha());
    }

    private void goToLogin() {
        Intent in = new Intent(requireActivity(), LoginActivity.class);
        startActivity(in);
        requireActivity().finish();
    }
}