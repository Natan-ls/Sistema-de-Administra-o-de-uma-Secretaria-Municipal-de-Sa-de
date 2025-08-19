package com.example.sistema_de_saude.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Farmaceutico")
public class Farmaceutico extends Funcionario{

    private String crf;

    public String getCrf() {
        return crf;
    }

    public void setCrf(String crf) {
        this.crf = crf;
    }
}