package com.example.sistema_de_saude.controller.recepcao;

import com.example.sistema_de_saude.dataAccess.PacienteDAO;
import com.example.sistema_de_saude.dataAccess.PessoaDAO;
import com.example.sistema_de_saude.entity.Paciente;
import com.example.sistema_de_saude.entity.Pessoa;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class BuscarPacienteController extends NavegadorPane {

    @FXML
    private TableView<Paciente> tablePaciente;
    @FXML
    private TableColumn<Paciente, String> colNome;
    @FXML
    private TableColumn<Paciente, String> colSexo;
    @FXML
    private TableColumn<Paciente, String> colCpf;
    @FXML
    private TableColumn<Paciente, String> colDtNascimento;
    @FXML
    private TableColumn<Paciente, String> colTelefone;
    @FXML
    private TableColumn<Paciente, String> colCartaoSus;

    @FXML
    private TextField tfBusca;

    @FXML
    private AnchorPane painelPrincipal;

    private final ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws IOException {
        this.setPainel(painelPrincipal);
        colNome.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPessoa().getNome()));

        colSexo.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getPessoa().getSexo() != null
                                ? cellData.getValue().getPessoa().getSexo().toString()
                                : ""
                ));

        colCpf.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPessoa().getCpf()));

        colDtNascimento.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getPessoa().getDataNascimento() != null
                                ? cellData.getValue().getPessoa().getDataNascimento().toString()
                                : ""
                ));

        colTelefone.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPessoa().getTelefone()));

        colCartaoSus.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNumeroSus()));

        tablePaciente.setItems(listaPacientes);
    }

    @FXML
    public void buscarPaciente(ActionEvent actionEvent) {
        String cpf = tfBusca.getText().trim();

        if (cpf.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Busca inválida");
            alert.setHeaderText(null);
            alert.setContentText("Digite um CPF para buscar.");
            alert.showAndWait();
            return;
        }

        Pessoa pessoa = PessoaDAO.getInstance().findByCpf(cpf);

        listaPacientes.clear();
        if (pessoa != null && pessoa.getPaciente() != null) {
            listaPacientes.add(pessoa.getPaciente());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nenhum resultado");
            alert.setHeaderText(null);
            alert.setContentText("Nenhum paciente encontrado para o CPF: " + cpf);
            alert.showAndWait();
        }
    }

    public void voltarPane(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_OPCOES_CRUD);
    }

}
