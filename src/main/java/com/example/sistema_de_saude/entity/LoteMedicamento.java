package com.example.sistema_de_saude.entity;

import jakarta.persistence.*;
import javafx.scene.chart.PieChart;

import java.util.Date;

@Entity
public class LoteMedicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkTipoMedicamento", nullable = false)
    private TipoMedicamento tipoMedicamento;

    private Date dataFabricacao;
    private Date dataValidade;
    private int quantidadeEntrada;
    private int quantidadeEstoque;

    @Enumerated(EnumType.STRING)
    private StatusLote status;

    public enum StatusLote {
        VENCIDO,      // Data de validade expirada
        DISPONIVEL,   // Dentro da validade e com estoque
        ESGOTADO      // Dentro da validade mas sem estoque
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTipoMedicamento(TipoMedicamento tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
    }

    public TipoMedicamento getTipoMedicamento() {
        return tipoMedicamento;
    }

    public Date getDataFabricacao() {
        return dataFabricacao;
    }

    public void setDataFabricacao(Date dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public int getQuantidadeEntrada() {
        return quantidadeEntrada;
    }

    public void setQuantidadeEntrada(int quantidadeEntrada) {
        this.quantidadeEntrada = quantidadeEntrada;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public StatusLote getStatus() {return status; }

    public void setStatus(StatusLote status) {this.status = status; }

    public void atualizarStatus() {
        Date hoje = new Date();

        if (dataValidade != null && dataValidade.before(hoje)) {
            this.status = StatusLote.VENCIDO;
        } else if (quantidadeEstoque <= 0) {
            this.status = StatusLote.ESGOTADO;
        } else {
            this.status = StatusLote.DISPONIVEL;
        }
    }

    public void diminuirEstoque(int quantidade) {
        if (quantidade > 0 && this.quantidadeEstoque >= quantidade) {
            this.quantidadeEstoque -= quantidade;
            this.atualizarStatus();
        } else {
            throw new IllegalArgumentException("Quantidade inválida ou estoque insuficiente");
        }
    }

}
