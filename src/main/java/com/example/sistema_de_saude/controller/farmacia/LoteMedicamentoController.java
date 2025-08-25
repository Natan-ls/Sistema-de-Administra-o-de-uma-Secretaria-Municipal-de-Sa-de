package com.example.sistema_de_saude.controller.farmacia;

import com.example.sistema_de_saude.dataAccess.LoteMedicamentoDAO;
import com.example.sistema_de_saude.dataAccess.TipoMedicamentoDAO;
import com.example.sistema_de_saude.entity.LoteMedicamento;
import com.example.sistema_de_saude.entity.TipoMedicamento;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class LoteMedicamentoController extends NavegadorPane {

    @FXML private ChoiceBox<String> cbNomeRemedio;
    @FXML private ChoiceBox<String> cbTipoMedicamento;
    @FXML private ChoiceBox<String> cbUnidadeMedida;
    @FXML private ChoiceBox<Integer> cbQuantidadeCaixa;
    @FXML private DatePicker dtFabricacao;
    @FXML private DatePicker dtValidade;
    @FXML private TextField tfQuantidade;
    @FXML private AnchorPane painelPrincipal;

    @FXML
    public void initialize() {
        this.setPainel(painelPrincipal);
        TipoMedicamentoDAO tipoDAO = TipoMedicamentoDAO.getInstance();

        // Popular nomes de medicamentos
        List<String> nomesRemedios = tipoDAO.findAll().stream()
                .map(tm -> tm.getMedicamento().getNome())
                .distinct()
                .toList();
        cbNomeRemedio.getItems().addAll(nomesRemedios);

        // Atualizar ChoiceBoxes ao selecionar medicamento
        cbNomeRemedio.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) atualizarTiposPorMedicamento(newVal);
        });
    }

    private void atualizarTiposPorMedicamento(String nomeRemedio) {
        TipoMedicamentoDAO tipoDAO = TipoMedicamentoDAO.getInstance();
        List<TipoMedicamento> tipos = tipoDAO.findAll().stream()
                .filter(tm -> tm.getMedicamento().getNome().equals(nomeRemedio))
                .toList();

        cbTipoMedicamento.getItems().clear();
        tipos.stream().map(TipoMedicamento::getTipo).distinct().forEach(cbTipoMedicamento.getItems()::add);

        cbUnidadeMedida.getItems().clear();
        tipos.stream().map(TipoMedicamento::getUnidadeMedida).distinct().forEach(cbUnidadeMedida.getItems()::add);

        cbQuantidadeCaixa.getItems().clear();
        tipos.stream().map(TipoMedicamento::getQuantidadeCaixa).distinct().forEach(cbQuantidadeCaixa.getItems()::add);
    }

    @FXML
    public void cadastrarLoteMedicamento(ActionEvent actionEvent) {
        try {
            String nomeRemedio = cbNomeRemedio.getValue();
            String tipoNome = cbTipoMedicamento.getValue();
            String unidade = cbUnidadeMedida.getValue();
            Integer qtdCaixa = cbQuantidadeCaixa.getValue();
            int quantidade = Integer.parseInt(tfQuantidade.getText().trim());
            java.time.LocalDate dataFab = dtFabricacao.getValue();
            java.time.LocalDate dataVal = dtValidade.getValue();

            if (nomeRemedio == null || tipoNome == null || unidade == null || qtdCaixa == null) {
                mostrarAlerta("Erro", "Todos os campos devem ser selecionados.", Alert.AlertType.ERROR);
                return;
            }
            if (quantidade <= 0) {
                mostrarAlerta("Erro", "Quantidade deve ser maior que zero.", Alert.AlertType.ERROR);
                return;
            }
            java.time.LocalDate hoje = java.time.LocalDate.now();
            if (dataFab == null || dataFab.isAfter(hoje)) {
                mostrarAlerta("Erro", "Data de fabricação inválida.", Alert.AlertType.ERROR);
                return;
            }
            if (dataVal == null || dataVal.isBefore(hoje)) {
                mostrarAlerta("Erro", "Data de validade inválida.", Alert.AlertType.ERROR);
                return;
            }

            // Buscar TipoMedicamento filtrando todos os critérios
            TipoMedicamento tipoMedicamento = TipoMedicamentoDAO.getInstance().findAll().stream()
                    .filter(tm -> tm.getMedicamento().getNome().equals(nomeRemedio))
                    .filter(tm -> tm.getTipo().equals(tipoNome))
                    .filter(tm -> tm.getUnidadeMedida().equals(unidade))
                    .filter(tm -> tm.getQuantidadeCaixa() == qtdCaixa)
                    .findFirst()
                    .orElse(null);

            if (tipoMedicamento == null) {
                mostrarAlerta("Erro", "Tipo de medicamento não encontrado.", Alert.AlertType.ERROR);
                return;
            }

            LoteMedicamento lote = new LoteMedicamento();
            lote.setTipoMedicamento(tipoMedicamento);
            lote.setQuantidadeEntrada(quantidade);
            lote.setQuantidadeEstoque(quantidade);
            lote.setDataFabricacao(java.sql.Date.valueOf(dataFab));
            lote.setDataValidade(java.sql.Date.valueOf(dataVal));
            lote.atualizarStatus();

            LoteMedicamentoDAO.getInstance().persist(lote);

            mostrarAlerta("Sucesso", "Lote cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            trocarPane(CaminhoFXML.PANE_ESTOQUE, null, ctrl -> {
                if (ctrl instanceof EstoqueController estoqueCtrl) {
                    estoqueCtrl.atualizarTabela();
                }
            });



        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Quantidade deve ser um número inteiro.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Ocorreu um erro ao cadastrar o lote.", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
