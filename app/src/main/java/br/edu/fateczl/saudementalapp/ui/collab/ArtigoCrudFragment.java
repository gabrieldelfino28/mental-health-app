package br.edu.fateczl.saudementalapp.ui.collab;

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
import br.edu.fateczl.saudementalapp.control.ArtigoController;
import br.edu.fateczl.saudementalapp.control.IArticleControl;
import br.edu.fateczl.saudementalapp.control.IUserControl;
import br.edu.fateczl.saudementalapp.control.UserCollabController;
import br.edu.fateczl.saudementalapp.model.Colaborador;
import br.edu.fateczl.saudementalapp.model.Artigo;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.Pagina;
import br.edu.fateczl.saudementalapp.model.Usuario;
import br.edu.fateczl.saudementalapp.model.factory.ColabFactory;
import br.edu.fateczl.saudementalapp.model.factory.ArtigoFactory;
import br.edu.fateczl.saudementalapp.persistence.ArtigoDAO;
import br.edu.fateczl.saudementalapp.persistence.ColaboradorDAO;
import br.edu.fateczl.saudementalapp.ui.UIOperations;

public class ArtigoCrudFragment extends Fragment implements UIDataMapping, UIOperations {
    private View view;
    private EditText inData, inTitulo, inFonte, inConteudo;
    private Spinner spArtigos;
    private Colaborador usr;
    private final String USR_LOGIN;
    public ArtigoCrudFragment(String login) {
        super();
        this.USR_LOGIN = login;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_artigo_crud, container, false);

        usr = getUserData();
        inData = view.findViewById(R.id.inDataArtigo);
        inTitulo = view.findViewById(R.id.inTituloArtigo);
        inFonte = view.findViewById(R.id.inFonteArtigo);
        inConteudo = view.findViewById(R.id.inConteudoArtigo);
        spArtigos = view.findViewById(R.id.spArtigosCrud);

        FloatingActionButton btnSearch, btnAdd, btnUpdate, btnDelete;
        btnSearch = view.findViewById(R.id.btnSearchArticleCrud);
        btnAdd = view.findViewById(R.id.btnAddArticle);
        btnUpdate = view.findViewById(R.id.btnEditArticle);
        btnDelete = view.findViewById(R.id.btnRemoveArticle);

        btnSearch.setOnClickListener(op -> operacaoBuscar());
        btnAdd.setOnClickListener(op -> operacaoInserir());
        btnUpdate.setOnClickListener(op -> operacaoAtualizar());
        btnDelete.setOnClickListener(op -> operacaoRemover());
        operacaoListar();
        return view;
    }

    @Override
    public void operacaoInserir() {
        try {
            IArticleControl<Artigo> iCont = new ArtigoController(new ArtigoDAO(view.getContext()));
            Artigo art = iCont.montarObjeto(getInputData());
            iCont.inserir(art);
            Toast.makeText(view.getContext(), getString(R.string.art_cad), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        cleanFields();
        operacaoListar();
    }

    @Override
    public void operacaoAtualizar() {
        try {
            int pos = spArtigos.getSelectedItemPosition();
            if (pos > 0) {
                IArticleControl<Artigo> iCont = new ArtigoController(new ArtigoDAO(view.getContext()));
                Artigo art = iCont.montarObjeto(getInputData());
                iCont.atualizar(art);
                Toast.makeText(view.getContext(), getString(R.string.art_upd), Toast.LENGTH_SHORT).show();
            } else {
                throw new Exception("Selecione um Artigo!");
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
            int pos = spArtigos.getSelectedItemPosition();
            if (pos > 0) {
                IArticleControl<Artigo> iCont = new ArtigoController(new ArtigoDAO(view.getContext()));
                Artigo art = iCont.montarObjeto(getInputData());
                iCont.remover(art);
                Toast.makeText(view.getContext(), getString(R.string.art_del), Toast.LENGTH_SHORT).show();
            } else {
                throw new Exception("Selecione um Artigo!");
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
            int pos = spArtigos.getSelectedItemPosition();
            if (pos > 0) {
                Artigo art = (Artigo) spArtigos.getSelectedItem();
                fillFields(art);
            } else {
                throw new Exception("Selecione um Artigo!");
            }
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.wtf("What", e.getMessage() );
        }
    }

    @Override
    public void operacaoListar() {
        try {
            IArticleControl<Artigo> iCont = new ArtigoController(new ArtigoDAO(view.getContext()));
            List<Artigo> list = iCont.listByUser(usr.getLogin());
            fillSpinner(list);
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.wtf("What", e.getMessage() );
        }
    }

    private void fillSpinner(List<Artigo> artigos) throws Exception {
        IFactory<Pagina> factory = new ArtigoFactory();
        Artigo e0 = (Artigo) factory.newInstance();
        e0.setDataPost("0");
        e0.setTitulo("Selecione uma Artigo.");
        e0.setFonte("");
        e0.setConteudo("");
        e0.setUsuario(usr);
        artigos.add(0, e0);
        ArrayAdapter ad = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, artigos);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spArtigos.setAdapter(ad);
    }

    private void cleanFields() {
        inTitulo.setText("");
        inData.setText("");
        inFonte.setText("");
        inConteudo.setText("");
        spArtigos.setSelection(0);
    }

    private Colaborador getUserData() {
        IUserControl<Colaborador> cont = new UserCollabController(new ColaboradorDAO(view.getContext()));
        IFactory<Usuario> factory = new ColabFactory();
        Colaborador usr = (Colaborador) factory.newInstance();
        try {
            usr.setLogin(USR_LOGIN);
            usr = cont.buscar(usr);
            return usr;
        } catch (Exception e) {
            Log.wtf("errXD", e.getMessage());
        }
        return usr;
    }

    private void fillFields(Artigo ent) {
        inData.setText(ent.getDataPost());
        inTitulo.setText(ent.getTitulo());
        inFonte.setText(ent.getFonte());
        inConteudo.setText(ent.getConteudo());
    }

    @Override
    public Bundle getInputData() {
        Bundle inputData = new Bundle();
        //User data mapping
        inputData.putString("userlogin", usr.getLogin());
        //Entry data mapping
        inputData.putString("dataPost", inData.getText().toString());
        inputData.putString("titulo", inTitulo.getText().toString());
        inputData.putString("conteudo", inConteudo.getText().toString());
        inputData.putString("fonte", inFonte.getText().toString());
        return inputData;
    }
}