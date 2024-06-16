package br.edu.fateczl.saudementalapp.ui.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.edu.fateczl.saudementalapp.R;
import br.edu.fateczl.saudementalapp.control.ArtigoController;
import br.edu.fateczl.saudementalapp.control.IArticleControl;
import br.edu.fateczl.saudementalapp.model.Artigo;
import br.edu.fateczl.saudementalapp.model.IFactory;
import br.edu.fateczl.saudementalapp.model.Pagina;
import br.edu.fateczl.saudementalapp.model.factory.ArtigoFactory;
import br.edu.fateczl.saudementalapp.persistence.ArtigoDAO;

public class ArtigoViewFragment extends Fragment {
    private View view;
    private TextView tvDataPost, tvTitulo, tvConteudo, tvFonte;
    private Spinner spArtigos;

    private final String USR_LOGIN;
    public ArtigoViewFragment(String login) {
        super();
        this.USR_LOGIN = login;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_artigo_view, container, false);

        tvDataPost = view.findViewById(R.id.tvDataPost);
        tvTitulo = view.findViewById(R.id.tvTituloArticle);
        tvConteudo = view.findViewById(R.id.tvConteudoView);
        tvFonte = view.findViewById(R.id.tvFonte);
        spArtigos = view.findViewById(R.id.spViewArticles);
        tvConteudo.setMovementMethod(new ScrollingMovementMethod());
        operacaoListar();

        FloatingActionButton btnSearchArticle = view.findViewById(R.id.btnSearchViewArticle);
        btnSearchArticle.setOnClickListener(op -> searchArticle());
        return view;
    }

    private void searchArticle() {
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
        }
    }

    public void operacaoListar() {
        try {
            IArticleControl<Artigo> iCont = new ArtigoController(new ArtigoDAO(view.getContext()));
            List<Artigo> list = iCont.listAll();
            fillSpinner(list);
        } catch (Exception e) {
            Log.wtf("what", e.getMessage());
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void fillSpinner(List<Artigo> artigos) throws Exception {
        IFactory<Pagina> factory = new ArtigoFactory();
        Artigo e0 = (Artigo) factory.newInstance();
        e0.setDataPost("0");
        e0.setTitulo("Selecione um Artigo");
        e0.setConteudo("");
        e0.setFonte("");
        artigos.add(0, e0);
        ArrayAdapter ad = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, artigos);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spArtigos.setAdapter(ad);
    }

    private void fillFields(Artigo art) {
        tvDataPost.setText(art.getDataPost());
        tvTitulo.setText(art.getTitulo());
        tvConteudo.setText(art.getConteudo());
        tvFonte.setText(String.format(getString(R.string.set_font), art.getFonte()));
    }
}