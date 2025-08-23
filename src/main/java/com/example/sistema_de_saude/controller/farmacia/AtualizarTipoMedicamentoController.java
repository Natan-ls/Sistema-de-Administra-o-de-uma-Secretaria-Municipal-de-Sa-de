package com.example.sistema_de_saude.controller.farmacia;

import com.example.sistema_de_saude.dataAccess.TipoMedicamentoDAO;
import com.example.sistema_de_saude.entity.TipoMedicamento;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import com.example.sistema_de_saude.util.ReceberDados;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AtualizarTipoMedicamentoController extends NavegadorPane implements ReceberDados<TipoMedicamento> {

    @FXML public Label labelNome;
    @FXML public AnchorPane painelPrincipal;
    @FXML private TextField tfQtdMinima;
    @FXML private TextField tfQtdCaixa;
    @FXML private TextField tfDescricao;
    @FXML private Label lbNomeRemedio;
    @FXML private Label labelTipo;

    private TipoMedicamento tipoSelecionado;

    @FXML
    public void initialize() {
        this.setPainel(painelPrincipal);
    }


    @Override
    public void setDados(TipoMedicamento dados) {
        this.tipoSelecionado = dados;
        if (tipoSelecionado != null) {
            // Preenche os campos apenas quando os dados chegam
            labelNome.setText("Nome: "+tipoSelecionado.getMedicamento().getNome());
            labelTipo.setText("Tipo: "+tipoSelecionado.getTipo());
            tfQtdMinima.setText(String.valueOf(tipoSelecionado.getEstoqueMinimo()));
            tfQtdCaixa.setText(String.valueOf(tipoSelecionado.getQuantidadeCaixa()));
            tfDescricao.setText(tipoSelecionado.getDescricao());
        }
    }

    @FXML
    public void atualizarRemedio(ActionEvent actionEvent) {
        if (tipoSelecionado == null) {
            mostrarAlerta("Erro", "Nenhum Tipo de Medicamento carregado.", AlertType.WARNING);
            return;
        }

        try {
            boolean alterado = false;

            int qtdMinima = Integer.parseInt(tfQtdMinima.getText());
            if (qtdMinima != tipoSelecionado.getEstoqueMinimo()) {
                tipoSelecionado.setEstoqueMinimo(qtdMinima);
                alterado = true;
            }

            int qtdCaixa = Integer.parseInt(tfQtdCaixa.getText());
            if (qtdCaixa != tipoSelecionado.getQuantidadeCaixa()) {
                tipoSelecionado.setQuantidadeCaixa(qtdCaixa);
                alterado = true;
            }

            String descricao = tfDescricao.getText();
            if (!descricao.equals(tipoSelecionado.getDescricao())) {
                tipoSelecionado.setDescricao(descricao);
                alterado = true;
            }

            if (alterado) {
                TipoMedicamentoDAO.getInstance().merge(tipoSelecionado);
                mostrarAlerta("Sucesso", "Tipo de medicamento atualizado com sucesso!", AlertType.INFORMATION);
            } else {
                mostrarAlerta("Informação", "Nenhuma alteração detectada.", AlertType.INFORMATION);
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Quantidade mínima e quantidade na caixa devem ser números inteiros.", AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Ocorreu um erro ao atualizar o tipo de medicamento.", AlertType.ERROR);
        }
        trocarPane(CaminhoFXML.PANE_ESTOQUE);
    }

    private void mostrarAlerta(String titulo, String mensagem, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
