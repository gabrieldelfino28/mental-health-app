package br.edu.fateczl.saudementalapp.model;

import androidx.annotation.NonNull;

public class Artigo extends Pagina{
    private String fonte;

    private String conteudo;

    public Artigo() {
        super();
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    @NonNull
    @Override
    public String toString() {
        return "* " + dataPost + " - " + titulo;
    }
}
