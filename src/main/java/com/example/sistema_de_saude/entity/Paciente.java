package com.example.sistema_de_saude.entity;

import jakarta.persistence.*;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("Paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroSus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fkPessoa", referencedColumnName = "id")
    private Pessoa pessoa;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consulta> consultas = new ArrayList<>();

    public Paciente(){}

    public Paciente(String numeroSus, Pessoa pessoa){
        this.numeroSus = numeroSus;
        this.pessoa = pessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroSus() {
        return numeroSus;
    }

    public void setNumeroSus(String numeroSus) {
        this.numeroSus = numeroSus;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "numeroSus='" + numeroSus + '\'' +
                ", pessoa=" + pessoa +
                ", consultas=" + consultas +
                '}';
    }
}
