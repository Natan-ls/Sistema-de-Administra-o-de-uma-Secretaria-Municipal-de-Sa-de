package com.example.sistema_de_saude.controller.farmacia;

import com.example.sistema_de_saude.dataAccess.MedicamentoDAO;
import com.example.sistema_de_saude.dataAccess.TipoMedicamentoDAO;
import com.example.sistema_de_saude.entity.Medicamento;
import com.example.sistema_de_saude.entity.TipoMedicamento;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CadastroRemedioController extends NavegadorPane {

    @FXML
    public AnchorPane painelPrincipal;
    @FXML
    private VBox containerTipos;

    @FXML
    private ChoiceBox<String> cbTipo;

    @FXML
    private ChoiceBox<String> cbUnidadeMedida;

    @FXML
    private TextField tfQtdMinima;

    @FXML
    private TextField tfNomeRemedio;

    @FXML
    private TextField tfQtdCaixa;

    @FXML
    public void initialize() {
        this.setPainel(painelPrincipal);
        if (containerTipos.getChildren().isEmpty()) {
            addContainer(null);
        }
    }

    public void addContainer(ActionEvent actionEvent) {
        // Cria novo HBox para o tipo
        HBox novoContainer = new HBox(10);

        ChoiceBox<String> cbTipo = new ChoiceBox<>();
        cbTipo.getItems().addAll("Comprimido", "Xarope", "Injetável");

        ChoiceBox<String> cbUnidade = new ChoiceBox<>();
        cbUnidade.getItems().addAll("mg", "ml", "g", "unidade");

        TextField tfQtdMinima = new TextField();
        tfQtdMinima.setPromptText("Qtd mínima");

        TextField tfQtdCaixa = new TextField();
        tfQtdCaixa.setPromptText("Qtd por caixa");

        novoContainer.getChildren().addAll(cbTipo, cbUnidade, tfQtdMinima, tfQtdCaixa);

        containerTipos.getChildren().add(novoContainer);
    }

    public void cadastrarRemedio(ActionEvent actionEvent) {
        String nome = tfNomeRemedio.getText().trim();
        if (nome.isEmpty()) {
            mostrarAlerta("Erro", "Informe o nome do medicamento.", Alert.AlertType.ERROR);
            return;
        }

        MedicamentoDAO medicamentoDAO = MedicamentoDAO.getInstance();
        TipoMedicamentoDAO tipoDAO = TipoMedicamentoDAO.getInstance();

        Medicamento medicamento = medicamentoDAO.getByNomeMedicamento(nome);

        boolean isNovoMedicamento = false;

        if (medicamento == null) {
            medicamento = new Medicamento();
            medicamento.setNome(nome);
            isNovoMedicamento = true;
        }

        // Itera sobre os containers de tipos
        for (Node node : containerTipos.getChildren()) {
            if (node instanceof HBox hbox) {
                @SuppressWarnings("unchecked")
                ChoiceBox<String> tipoChoice = (ChoiceBox<String>) hbox.getChildren().get(0);
                @SuppressWarnings("unchecked")
                ChoiceBox<String> unidadeChoice = (ChoiceBox<String>) hbox.getChildren().get(1);
                TextField qtdMinimaField = (TextField) hbox.getChildren().get(2);
                TextField qtdCaixaField = (TextField) hbox.getChildren().get(3);

                if (tipoChoice.getValue() == null || unidadeChoice.getValue() == null ||
                        qtdMinimaField.getText().isEmpty() || qtdCaixaField.getText().isEmpty()) {
                    mostrarAlerta("Erro", "Preencha todos os campos para cada tipo.", Alert.AlertType.ERROR);
                    return;
                }

                try {
                    int qtdMinima = Integer.parseInt(qtdMinimaField.getText());
                    int qtdCaixa = Integer.parseInt(qtdCaixaField.getText());

                    TipoMedicamento tipoMedicamento = new TipoMedicamento();
                    tipoMedicamento.setTipo(tipoChoice.getValue());
                    tipoMedicamento.setUnidadeMedida(unidadeChoice.getValue());
                    tipoMedicamento.setEstoqueMinimo(qtdMinima);
                    tipoMedicamento.setQuantidadeCaixa(qtdCaixa);
                    tipoMedicamento.setMedicamento(medicamento);

                    // Se quiser persistir cada tipo separadamente
                    tipoDAO.persist(tipoMedicamento);

                    // Adiciona na lista do Medicamento (opcional se tiver cascade)
                    medicamento.getTipoMedicamentos().add(tipoMedicamento);

                } catch (NumberFormatException e) {
                    mostrarAlerta("Erro", "Valores de quantidade devem ser números inteiros.", Alert.AlertType.ERROR);
                    return;
                }
            }
        }

        try {
            // Persiste o medicamento se for novo
            if (isNovoMedicamento) {
                medicamentoDAO.persist(medicamento);
            } else {
                medicamentoDAO.merge(medicamento);
            }

            mostrarAlerta("Sucesso", "Cadastro realizado com sucesso!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Ocorreu um erro ao salvar no banco de dados.", Alert.AlertType.ERROR);
        }


        //trocarPane(CaminhoFXML.PANE_ESTOQUE);
    }


    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}