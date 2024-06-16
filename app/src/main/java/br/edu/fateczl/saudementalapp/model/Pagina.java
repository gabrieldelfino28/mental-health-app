package br.edu.fateczl.saudementalapp.model;

import androidx.annotation.NonNull;

public abstract class Pagina {
    protected String titulo;

    protected String dataPost;

    Usuario usuario;

    public Pagina() {
        super();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDataPost() {
        return dataPost;
    }

    public void setDataPost(String dataPost) {
        this.dataPost = dataPost;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @NonNull
    @Override
    public String toString() {
        return titulo + " - " + dataPost;
    }
}
