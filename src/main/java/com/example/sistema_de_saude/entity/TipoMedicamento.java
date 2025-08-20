package com.example.sistema_de_saude.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TipoMedicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkMedico", nullable = false)
    private Medicamento medicamento;

    @OneToMany(mappedBy = "tipoMedicamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoteMedicamento> loteMedicamentos = new ArrayList<>();

    private String tipo;
    private String descricao;
    private String unidadeMedida;
    private int quantidadeCaixa;
    private int estoqueMinimo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public int getQuantidadeCaixa() {
        return quantidadeCaixa;
    }

    public void setQuantidadeCaixa(int quantidadeCaixa) {
        this.quantidadeCaixa = quantidadeCaixa;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public List<LoteMedicamento> getLoteMedicamentos() {
        return loteMedicamentos;
    }

    public void setLoteMedicamentos(List<LoteMedicamento> loteMedicamentos) {
        this.loteMedicamentos = loteMedicamentos;
    }

    public void addLote(LoteMedicamento lote) {
        lote.setTipoMedicamento(this);
        this.loteMedicamentos.add(lote);
    }

}
