package br.edu.fateczl.saudementalapp.model;

import androidx.annotation.NonNull;

public class Pessoa extends Usuario {

    private String email;

    public Pessoa() {
        setCollab(false);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + login + " | " + senha + ") - " + nome + " - " + email;
    }
}
