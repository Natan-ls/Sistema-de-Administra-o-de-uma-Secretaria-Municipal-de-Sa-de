package com.example.sistema_de_saude.controller.farmacia;

import com.example.sistema_de_saude.dataAccess.LoteMedicamentoDAO;
import com.example.sistema_de_saude.dataAccess.TipoMedicamentoDAO;
import com.example.sistema_de_saude.entity.LoteMedicamento;
import com.example.sistema_de_saude.entity.TipoMedicamento;
import com.example.sistema_de_saude.entity.UsuarioSistema;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import com.example.sistema_de_saude.util.ReceberDados;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class LoteMedicamentoController extends NavegadorPane {

    @FXML public TextField tfNomeRemedio;
    @FXML public ChoiceBox cbTipoMedicamento;
    @FXML public DatePicker dtFabricacao;
    @FXML public DatePicker dtValidade;
    @FXML public TextField tfQuantidade;
    @FXML public AnchorPane painelPrincipal;

    @FXML
    public void initialize(){
        this.setPainel(painelPrincipal);
        TipoMedicamentoDAO tipoDAO = TipoMedicamentoDAO.getInstance();
        List<TipoMedicamento> tipos = tipoDAO.findAll();
        List<String> nomesTipos = tipos.stream()
                .map(TipoMedicamento::getTipo)
                .distinct()
                .toList();
        cbTipoMedicamento.getItems().addAll(nomesTipos);
    }

    @FXML
    public void cadastrarLoteMedicamento(ActionEvent actionEvent) {
        try {
            String nomeRemedio = tfNomeRemedio.getText().trim();
            String tipoNome = cbTipoMedicamento.getValue() != null ? cbTipoMedicamento.getValue().toString() : null;
            int quantidade = Integer.parseInt(tfQuantidade.getText().trim());
            java.time.LocalDate dataFab = dtFabricacao.getValue();
            java.time.LocalDate dataVal = dtValidade.getValue();

            // Validações
            if (nomeRemedio.isEmpty() || tipoNome == null) {
                mostrarAlerta("Erro", "Nome do medicamento e tipo são obrigatórios.", Alert.AlertType.ERROR);
                return;
            }

            if (quantidade <= 0) {
                mostrarAlerta("Erro", "Quantidade deve ser maior que zero.", Alert.AlertType.ERROR);
                return;
            }

            if (dataFab == null) {
                mostrarAlerta("Erro", "Data de fabricação não pode estar vazia.", Alert.AlertType.ERROR);
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

            // Buscar TipoMedicamento pelo nome do remédio e tipo
            TipoMedicamento tipoMedicamento = TipoMedicamentoDAO.getInstance()
                    .getByNomeTipo(nomeRemedio, tipoNome);

            if (tipoMedicamento == null) {
                mostrarAlerta("Erro", "Tipo de medicamento não encontrado.", Alert.AlertType.ERROR);
                return;
            }

            // Criar e preencher o LoteMedicamento
            LoteMedicamento lote = new LoteMedicamento();
            lote.setTipoMedicamento(tipoMedicamento);
            lote.setQuantidadeEntrada(quantidade);
            lote.setQuantidadeEstoque(quantidade);
            lote.setDataFabricacao(java.sql.Date.valueOf(dataFab));
            lote.setDataValidade(java.sql.Date.valueOf(dataVal));
            lote.atualizarStatus();

            // Salvar no banco
            LoteMedicamentoDAO.getInstance().merge(lote);

            mostrarAlerta("Sucesso", "Lote cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            trocarPane(CaminhoFXML.PANE_ESTOQUE);

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
