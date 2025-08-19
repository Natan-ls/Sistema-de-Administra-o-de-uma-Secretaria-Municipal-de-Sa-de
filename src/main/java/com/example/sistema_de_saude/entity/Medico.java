package com.example.sistema_de_saude.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Medico")
public class Medico extends Funcionario{

    private String crm;

    private String especialidade;

    @OneToMany(mappedBy = "medico")
    private List<Consulta> consultas = new ArrayList<>();

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "crm='" + crm + '\'' +
                ", especialidade='" + especialidade + '\'' +
                ", consultas=" + consultas +
                ", dataAdimissao=" + dataAdimissao +
                ", matricula='" + matricula + '\'' +
                ", pessoa=" + pessoa +
                '}';
    }
}
