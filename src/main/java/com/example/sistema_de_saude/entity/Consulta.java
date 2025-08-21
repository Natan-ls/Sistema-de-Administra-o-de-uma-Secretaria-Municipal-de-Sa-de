package com.example.sistema_de_saude.entity;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dataConsulta;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusConsulta status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkPaciente", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkMedico", nullable = false)
    private Medico medico;

    @OneToOne
    @JoinColumn(name = "fkProtocolo", referencedColumnName = "id")
    private Protocolo protocolo;

    public StatusConsulta getStatus() {return status; }

    public void setStatus(StatusConsulta status) {this.status = status; }

    public enum StatusConsulta {EM_ATENDIMENTO, CONFIRMADA, REALIZADA, CANCELADA  }

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

    public void setProtocolo(Protocolo protocolo) {
        this.protocolo = protocolo;
    }

    public Protocolo getProtocolo() {
        return protocolo;
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

    public void alterarStatusConsulta(StatusConsulta status){this.status = status; }

    public Protocolo gerarProtocolo(Paciente paciente, Consulta consulta) {
        try {
            Protocolo p = new Protocolo();

            // Validações
            if (paciente == null) {
                throw new IllegalArgumentException("Paciente não pode ser nulo");
            }
            if (consulta.getDataConsulta() == null) {
                throw new IllegalArgumentException("Data da consulta não pode ser nula");
            }

            // Gera código único
            String codigo = gerarCodigoProtocolo(paciente, consulta);
            p.setCodigo(codigo);
            p.setDataGerada(new Date());

            this.protocolo = p;
            return p;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar protocolo: " + e.getMessage(), e);
        }
    }

    private String gerarCodigoProtocolo(Paciente paciente, Consulta consulta) {
        // Validação adicional
        if (paciente.getNumeroSus() == null) {
            throw new IllegalArgumentException("Número SUS do paciente não pode ser nulo");
        }

        // Extrai e formata número SUS (mínimo 6 dígitos)
        String numeroSus = paciente.getNumeroSus().replaceAll("[^0-9]", ""); // Remove não-numéricos

        if (numeroSus.length() < 6) {
            numeroSus = String.format("%06d", Long.parseLong(numeroSus));
        }
        String susReduzido = numeroSus.substring(numeroSus.length() - 6);

        // Formata data
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String dataFormatada = sdf.format(consulta.getDataConsulta());

        return "CONS" + susReduzido + dataFormatada;
    }

}
