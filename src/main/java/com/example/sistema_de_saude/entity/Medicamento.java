package com.example.sistema_de_saude.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = true)
    private String nome;

    @OneToMany(mappedBy = "medicamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TipoMedicamento> tipoMedicamentos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<TipoMedicamento> getTipoMedicamentos() {
        return tipoMedicamentos;
    }

    public void setTipoMedicamentos(List<TipoMedicamento> tipoMedicamentos) {
        this.tipoMedicamentos = tipoMedicamentos;
    }

    public void addTipoMedicamento(TipoMedicamento tipo) {
        tipo.setMedicamento(this);
        this.tipoMedicamentos.add(tipo);
    }

}
