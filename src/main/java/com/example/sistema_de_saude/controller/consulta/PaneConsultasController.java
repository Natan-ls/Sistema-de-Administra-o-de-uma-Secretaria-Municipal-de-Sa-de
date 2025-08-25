package com.example.sistema_de_saude.controller.consulta;

import com.example.sistema_de_saude.dataAccess.ConsultaDAO;
import com.example.sistema_de_saude.entity.Consulta;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PaneConsultasController extends NavegadorPane {

    @FXML
    private TableView<Consulta> tableConsulta;

    @FXML
    private TableColumn<Consulta, String> colPaciente;

    @FXML
    private TableColumn<Consulta, String> colMedico;

    @FXML
    private TableColumn<Consulta, String> colTipo;

    @FXML
    private TableColumn<Consulta, String> colData;

    @FXML
    private AnchorPane painel;

    private final ObservableList<Consulta> consultasList = FXCollections.observableArrayList();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @FXML
    public void initialize() {
        this.setPainel(painel);
        carregarConsultas();
        configurarColunas();
    }

    private void carregarConsultas() {
        List<Consulta> consultas = ConsultaDAO.getInstance().findAll();

        // filtra apenas consultas futuras
        List<Consulta> futuras = consultas.stream()
                .filter(c -> c.getDataConsulta() != null && !c.getDataConsulta().before(new Date()))
                .collect(Collectors.toList());

        consultasList.setAll(futuras);
        tableConsulta.setItems(consultasList);
    }

    private void configurarColunas() {
        colPaciente.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getPaciente() != null
                                ? data.getValue().getPaciente().getPessoa().getNome()
                                : "Desconhecido"));

        colMedico.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getMedico() != null
                                ? data.getValue().getMedico().getPessoa().getNome()
                                : "Desconhecido"));

        colTipo.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getTipoConsulta() != null
                                ? data.getValue().getTipoConsulta()
                                : "N/A"));

        colData.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getDataConsulta() != null
                                ? sdf.format(data.getValue().getDataConsulta())
                                : "Sem data"));
    }

    @FXML
    public void iniciarConsulta(ActionEvent actionEvent) {
        Consulta selecionada = tableConsulta.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            // Troca o pane e passa a consulta selecionada
            trocarPane(CaminhoFXML.PANE_ADICIONAR_REMEDIO, selecionada);
        }
    }
}
