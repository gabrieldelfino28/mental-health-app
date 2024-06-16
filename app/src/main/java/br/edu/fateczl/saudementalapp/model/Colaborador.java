package br.edu.fateczl.saudementalapp.model;

import androidx.annotation.NonNull;

public class Colaborador extends Usuario {
    private String especialidade;

    public Colaborador() {
        setCollab(true);
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }


    @NonNull
    @Override
    public String toString() {
        return "(" + login + " | " + senha + ") - " + nome + " - " + especialidade;
    }
}
