package com.example.sistema_de_saude.controller.recepcao;

import com.example.sistema_de_saude.dataAccess.PacienteDAO;
import com.example.sistema_de_saude.entity.Paciente;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

public class TabelaPacientesController extends NavegadorPane {

    @FXML
    public AnchorPane painelPrincipal;
    @FXML
    private TableView<Paciente> tablePacientes;
    @FXML
    private TableColumn<Paciente, String> colNome;
    @FXML
    private TableColumn<Paciente, String> colCpf;
    @FXML
    private TableColumn<Paciente, String> colDtNascimento;
    @FXML
    private TableColumn<Paciente, String> colTelefone;
    @FXML
    private TableColumn<Paciente, String> colCartaoSus;
    @FXML
    private TableColumn<Paciente, String> colSexo;



    @FXML
    public void initialize() throws IOException {
        this.setPainel(painelPrincipal);
        colNome.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPessoa().getNome())
        );

        colCpf.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPessoa().getCpf())
        );

        colDtNascimento.setCellValueFactory(cellData -> {
            if (cellData.getValue().getPessoa().getDataNascimento() != null) {
                return new SimpleStringProperty(
                        new SimpleDateFormat("dd/MM/yyyy")
                                .format(cellData.getValue().getPessoa().getDataNascimento())
                );
            } else {
                return new SimpleStringProperty("");
            }
        });

        colTelefone.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getPessoa().getTelefone() != null
                                ? cellData.getValue().getPessoa().getTelefone()
                                : ""
                )
        );

        colCartaoSus.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNumeroSus())
        );

        colSexo.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getPessoa().getSexo() != null
                                ? cellData.getValue().getPessoa().getSexo().toString()
                                : ""
                )
        );

        // Carrega os dados da tabela
        carregarPacientes();
    }

    private void carregarPacientes() {
        List<Paciente> pacientes = PacienteDAO.getInstance().findAll();
        ObservableList<Paciente> data = FXCollections.observableArrayList(pacientes);
        tablePacientes.setItems(data);
    }

    public void voltarPane(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_OPCOES_CRUD);
    }
}
