package com.example.sistema_de_saude.controller.farmacia;

import com.example.sistema_de_saude.entity.Medicamento;
import com.example.sistema_de_saude.entity.TipoMedicamento;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class MedicamentoRow {

    private final Medicamento medicamento;
    private TipoMedicamento tipoSelecionadoObj;
    private String tipoSelecionado;
    private String unidadeSelecionada;
    private Integer qtdCaixaSelecionada;
    private final SimpleStringProperty nome;
    private final SimpleStringProperty status;

    private final ObservableList<TipoMedicamento> tipos;
    private final ObservableList<String> unidades = FXCollections.observableArrayList();
    private final ObservableList<Integer> qtdCaixas = FXCollections.observableArrayList();

    private final SimpleStringProperty quantidade = new SimpleStringProperty("0");

    public MedicamentoRow(Medicamento m, String status) {
        this.medicamento = m;
        this.nome = new SimpleStringProperty(m.getNome());
        this.status = new SimpleStringProperty(status);
        this.tipos = FXCollections.observableArrayList(m.getTipoMedicamentos());
    }

    public SimpleStringProperty nomeProperty() { return nome; }
    public SimpleStringProperty statusProperty() { return status; }
    public SimpleStringProperty quantidadeProperty() { return quantidade; }

    public List<TipoMedicamento> getTipos() { return tipos; }

    public void setTipoSelecionado(String tipo) {
        this.tipoSelecionado = tipo;
        tipoSelecionadoObj = tipos.stream()
                .filter(t -> t.getTipo().equals(tipo))
                .findFirst()
                .orElse(null);
    }

    public TipoMedicamento getTipoSelecionadoObj() { return tipoSelecionadoObj; }

    public void atualizarUnidades() {
        unidades.clear();
        if (tipoSelecionadoObj != null) {
            unidades.addAll(
                    tipos.stream()
                            .filter(t -> t.getTipo().equals(tipoSelecionado))
                            .map(TipoMedicamento::getUnidadeMedida)
                            .distinct()
                            .collect(Collectors.toList())
            );
        }
    }

    public ObservableList<String> getUnidadesObservable() { return unidades; }

    public void setUnidadeSelecionada(String unidade) { this.unidadeSelecionada = unidade; }

    public void atualizarQtdCaixa() {
        qtdCaixas.clear();
        if (tipoSelecionadoObj != null && unidadeSelecionada != null) {
            qtdCaixas.addAll(
                    tipos.stream()
                            .filter(t -> t.getTipo().equals(tipoSelecionado))
                            .filter(t -> t.getUnidadeMedida().equals(unidadeSelecionada))
                            .map(TipoMedicamento::getQuantidadeCaixa)
                            .distinct()
                            .collect(Collectors.toList())
            );
        }
    }

    public ObservableList<Integer> getQtdCaixaObservable() { return qtdCaixas; }

    public void setQtdCaixaSelecionada(Integer qtd) { this.qtdCaixaSelecionada = qtd; }

    public int getQuantidadeSelecionada() {
        try { return Integer.parseInt(quantidade.get()); }
        catch (Exception e) { return 0; }
    }
}
