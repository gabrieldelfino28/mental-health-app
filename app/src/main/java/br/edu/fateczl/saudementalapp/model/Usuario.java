package br.edu.fateczl.saudementalapp.model;

import androidx.annotation.NonNull;

public abstract class Usuario {
    protected String login;
    protected String senha;

    protected String nome;
    private boolean isCollab;

    public boolean isCollab() {
        return isCollab;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCollab(boolean collab) {
        isCollab = collab;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @NonNull
    @Override
    public String toString() {
        return '(' + login + " | " + senha + ") - " + nome;
    }
}
