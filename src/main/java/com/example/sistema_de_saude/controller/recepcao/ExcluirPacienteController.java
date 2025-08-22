package com.example.sistema_de_saude.controller.recepcao;

import com.example.sistema_de_saude.dataAccess.PacienteDAO;
import com.example.sistema_de_saude.dataAccess.PessoaDAO;
import com.example.sistema_de_saude.entity.Paciente;
import com.example.sistema_de_saude.entity.Pessoa;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.beans.property.SimpleStringProperty;

public class ExcluirPacienteController extends NavegadorPane {

    @FXML
    private AnchorPane painelPrincipal;
    @FXML
    private TableView<Paciente> tablePaciente;
    @FXML
    private TableColumn<Paciente, String> colNome;
    @FXML
    private TableColumn<Paciente, String> colCpf;
    @FXML
    private TableColumn<Paciente, String> colDtNascimento;
    @FXML
    private TableColumn<Paciente, String> colTelefone;
    @FXML
    private TableColumn<Paciente, String> colSus;
    @FXML
    private TableColumn<Paciente, String> colSexo;
    @FXML
    private javafx.scene.control.TextField tfCpf;

    @FXML
    public void initialize() {
        this.setPainel(painelPrincipal);
    }

    @FXML
    public void pesquisar(ActionEvent actionEvent) {
        String cpf = tfCpf.getText().trim();

        // Limpa a tabela antes de popular
        tablePaciente.getItems().clear();

        ObservableList<Paciente> lista = FXCollections.observableArrayList();

        if (cpf.isEmpty()) {
            // Se nenhum CPF for digitado, mostra todos os pacientes
            lista.addAll(PacienteDAO.getInstance().findAll());
        } else {
            // Busca por CPF específico
            Pessoa pessoa = PessoaDAO.getInstance().findByCpf(cpf);
            if (pessoa != null && pessoa.getPaciente() != null) {
                lista.add(pessoa.getPaciente());
            } else {
                showAlert("Nenhum resultado", "Nenhum paciente encontrado para o CPF: " + cpf, Alert.AlertType.INFORMATION);
            }
        }

        // Configura as colunas
        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPessoa().getNome()));
        colCpf.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPessoa().getCpf()));
        colDtNascimento.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getPessoa().getDataNascimento() != null
                        ? cellData.getValue().getPessoa().getDataNascimento().toString()
                        : ""
        ));
        colTelefone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPessoa().getTelefone()));
        colSus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroSus()));
        colSexo.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getPessoa().getSexo() != null
                        ? cellData.getValue().getPessoa().getSexo().toString()
                        : ""
        ));

        tablePaciente.setItems(lista);
    }

    @FXML
    public void excluirPaciente(ActionEvent actionEvent) {
        Paciente selecionado = tablePaciente.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            showAlert("Erro", "Selecione um paciente na tabela para excluir.", Alert.AlertType.WARNING);
            return;
        }

        // Remove paciente do banco
        PacienteDAO.getInstance().removeById(selecionado.getId());

        // Atualiza tabela
        tablePaciente.getItems().remove(selecionado);

        showAlert("Paciente excluído", "Paciente " + selecionado.getPessoa().getNome() + " foi excluído com sucesso.", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void voltarPane(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_OPCOES_CRUD);
    }

    private void showAlert(String title, String content, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
