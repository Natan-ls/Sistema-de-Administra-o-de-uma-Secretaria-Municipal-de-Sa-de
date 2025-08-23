package com.example.sistema_de_saude.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Administrador")
public class Administrador extends Funcionario{

    private String setor;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
}
