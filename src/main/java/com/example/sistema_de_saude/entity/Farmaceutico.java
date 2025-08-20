package com.example.sistema_de_saude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("Farmaceutico")
public class Farmaceutico extends Funcionario{

    @Column(unique = true, nullable = false)
    private String crf;

    @OneToMany(mappedBy = "farmaceutico")
    private List<Protocolo> protocolos = new ArrayList<>();

    public Farmaceutico(){};

    public Farmaceutico(Date dataAdimissao, int matricula, String crf){
        super(dataAdimissao, matricula);
        this.crf = crf;
    };

    public String getCrf() {
        return crf;
    }

    public void setCrf(String crf) {
        this.crf = crf;
    }

    public void registrarEntrega(Protocolo protocolo){
        protocolos.add(protocolo);
    };

    public List<Protocolo> getProtocolos() {return protocolos; }

    public void setProtocolos(List<Protocolo> protocolos) {this.protocolos = protocolos; }
}