package br.edu.fateczl.saudementalapp.model;

import androidx.annotation.NonNull;

public class Entrada extends Pagina{
    private String sentimentos;
    protected String conteudo;

    public String getSentimentos() {
        return sentimentos;
    }

    public void setSentimentos(String sentimentos) {
        this.sentimentos = sentimentos;
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
        return "* " + dataPost + " - " +  titulo;
    }
}
