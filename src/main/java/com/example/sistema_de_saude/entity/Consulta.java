package com.example.sistema_de_saude.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dataConsulta;
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkPaciente", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkMedico")
    private Medico medico;

    public enum StatusConsulta {
        EM_ATENDIMENTO, REALIZADA, CANCELADA
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(Date dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "dataConsulta=" + dataConsulta +
                ", descricao='" + descricao + '\'' +
                ", paciente=" + paciente +
                ", medico=" + medico +
                '}';
    }
}
