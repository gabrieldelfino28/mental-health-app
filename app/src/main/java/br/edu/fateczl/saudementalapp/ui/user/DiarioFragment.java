package br.edu.fateczl.saudementalapp.ui.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.edu.fateczl.saudementalapp.R;
import br.edu.fateczl.saudementalapp.UIDataMapping;
import br.edu.fateczl.saudementalapp.control.EntradaController;
import br.edu.fateczl.saudementalapp.control.IPageControl;
import br.edu.fateczl.saudementalapp.control.IUserControl;
import br.edu.fateczl.saudementalapp.control.UserPessoaController;
import br.edu.fateczl.saudementalapp.persistence.EntradaDAO;
import br.edu.fateczl.saudementalapp.persistence.PessoaDAO;
import br.edu.fateczl.saudementalapp.ui.UIOperations;
import br.edu.fateczl.saudementalapp.model.Entrada;
import br.edu.fateczl.saudementalapp.model.Pagina;
import br.edu.fateczl.saudementalapp.model.Pessoa;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.model.factory.EntradaFactory;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.factory.PessoaFactory;

public class DiarioFragment extends Fragment implements UIDataMapping, UIOperations {

    IPageControl<Entrada> iCont;
    private View view;
    private EditText inData, inTitulo, inSentimento, inConteudo;
    private Spinner spEntry;
    private Pessoa usr;
    private final String USR_LOGIN;

    public DiarioFragment(String login) {
        super();
        this.USR_LOGIN = login;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_diario, container, false);
        iCont = new EntradaController(new EntradaDAO(view.getContext()));

        inData = view.findViewById(R.id.inDataEntrada);
        inTitulo = view.findViewById(R.id.inTituloEntrada);
        inSentimento = view.findViewById(R.id.inSentimento);
        inConteudo = view.findViewById(R.id.inConteudoEntrada);
        spEntry = view.findViewById(R.id.spEntradas);

        usr = getUserData();
        operacaoListar(); //<- fill the spinner with all the usr's diary entries

        FloatingActionButton btnSearch, btnAdd, btnUpdate, btnDelete;
        btnSearch = view.findViewById(R.id.btnSearchEntry);
        btnAdd = view.findViewById(R.id.btnNewEntry);
        btnUpdate = view.findViewById(R.id.btnUpdateEntry);
        btnDelete = view.findViewById(R.id.btnRemoveEntry);

        btnSearch.setOnClickListener(op -> operacaoBuscar());
        btnAdd.setOnClickListener(op -> operacaoInserir());
        btnUpdate.setOnClickListener(op -> operacaoAtualizar());
        btnDelete.setOnClickListener(op -> operacaoRemover());
        return view;
    }

    @Override
    public void operacaoInserir() {
        try {
            Entrada ent = iCont.montarObjeto(getInputData());
            iCont.inserir(ent);
            Toast.makeText(view.getContext(), getString(R.string.msg_cad), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        cleanFields();
        operacaoListar();
    }

    @Override
    public void operacaoAtualizar() {
        try {
            int pos = spEntry.getSelectedItemPosition();
            if (pos > 0) {
                Entrada ent = iCont.montarObjeto(getInputData());
                iCont.atualizar(ent);
                Toast.makeText(view.getContext(), getString(R.string.msg_upd), Toast.LENGTH_SHORT).show();
            } else {
                throw new Exception("Selecione uma entrada!");
            }
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        cleanFields();
        operacaoListar();
    }

    @Override
    public void operacaoRemover() {
        try {
            int pos = spEntry.getSelectedItemPosition();
            if (pos > 0) {
                Entrada ent = iCont.montarObjeto(getInputData());
                iCont.remover(ent);
                Toast.makeText(view.getContext(), getString(R.string.msg_del), Toast.LENGTH_SHORT).show();
            } else {
                throw new Exception("Selecione uma entrada!");
            }
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        cleanFields();
        operacaoListar();
    }

    @Override
    public void operacaoBuscar() {
        try {
            int pos = spEntry.getSelectedItemPosition();
            if (pos > 0) {
                Entrada ent = (Entrada) spEntry.getSelectedItem();
                fillFields(ent);
            } else {
                throw new Exception("Selecione uma entrada!");
            }
        } catch (Exception e) {
            Log.wtf("What", e.getMessage() );
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void operacaoListar() {
        try {
            List<Entrada> entradas = iCont.listByUser(usr.getLogin());
            fillSpinner(entradas);
        } catch (Exception e) {
            Log.wtf("What", e.getMessage() );
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void fillSpinner(List<Entrada> entradas) throws Exception {
        IFactory<Pagina> factory = new EntradaFactory();
        Entrada e0 = (Entrada) factory.newInstance();
        e0.setDataPost("0");
        e0.setTitulo("Selecione uma Entrada");
        e0.setSentimentos("-");
        e0.setConteudo("-");
        e0.setUsuario(usr);
        entradas.add(0, e0);
        ArrayAdapter ad = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, entradas);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEntry.setAdapter(ad);
    }

    private void cleanFields() {
        inData.setText("");
        inTitulo.setText("");
        inSentimento.setText("");
        inConteudo.setText("");
        spEntry.setSelection(0);
    }

    private Pessoa getUserData() {
        IUserControl<Pessoa> cont = new UserPessoaController(new PessoaDAO(view.getContext()));
        IFactory<Usuario> factory = new PessoaFactory();
        Pessoa usr = (Pessoa) factory.newInstance();
        try {
            usr.setLogin(USR_LOGIN);
            usr = cont.buscar(usr);
            return usr;
        } catch (Exception e) {
            Log.wtf("errXD", e.getMessage());
        }
        return usr;
    }

    private void fillFields(Entrada ent) {
        inData.setText(ent.getDataPost());
        inTitulo.setText(ent.getTitulo());
        inSentimento.setText(ent.getSentimentos());
        inConteudo.setText(ent.getConteudo());
    }

    @Override
    public Bundle getInputData() {
        Bundle inputData = new Bundle();
        //User data mapping
        inputData.putString("userlogin", usr.getLogin());
        inputData.putString("nome", usr.getNome());
        inputData.putString("senha", usr.getSenha());
        //Entry data mapping
        inputData.putString("dataPost", inData.getText().toString());
        inputData.putString("titulo", inTitulo.getText().toString());
        inputData.putString("conteudo", inConteudo.getText().toString());
        inputData.putString("sentimento", inSentimento.getText().toString());
        return inputData;
    }
}