package com.example.sistema_de_saude.controller.agendamento;

import com.example.sistema_de_saude.dataAccess.ConsultaDAO;
import com.example.sistema_de_saude.entity.Consulta;
import com.example.sistema_de_saude.entity.Consulta.StatusConsulta;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TabelaConsultasController extends NavegadorPane {

    @FXML
    private TableView<Consulta> tableAgendamentos;
    @FXML
    private TableColumn<Consulta, String> colPaciente;
    @FXML
    private TableColumn<Consulta, String> colMedico;
    @FXML
    private TableColumn<Consulta, String> colTipoConsulta;
    @FXML
    private TableColumn<Consulta, String> colData;
    @FXML
    private TableColumn<Consulta, Void> colAcoes;
    @FXML
    private DatePicker dpFiltroData;
    @FXML
    private AnchorPane painelPrincipal;

    private ObservableList<Consulta> listaConsultas;
    private ObservableList<Consulta> listaConsultasCompleta; // Lista completa para resetar filtros

    @FXML
    public void initialize() {
        this.setPainel(painelPrincipal);
        configurarColunas();
        carregarConsultas();
        configurarFiltroData();
    }

    private void carregarConsultas() {
        List<Consulta> consultas = ConsultaDAO.getInstance().findAll();
        listaConsultasCompleta = FXCollections.observableArrayList(consultas);

        // Filtra automaticamente pela data de hoje
        filtrarPorDataHoje();
    }

    private void configurarColunas() {
        colPaciente.setCellValueFactory(cellData -> {
            if (cellData.getValue().getPaciente() != null &&
                    cellData.getValue().getPaciente().getPessoa() != null) {
                return new SimpleStringProperty(cellData.getValue().getPaciente().getPessoa().getNome());
            }
            return new SimpleStringProperty("N/A");
        });

        colMedico.setCellValueFactory(cellData -> {
            if (cellData.getValue().getMedico() != null) {
                return new SimpleStringProperty(cellData.getValue().getMedico().getNomeFantasia());
            }
            return new SimpleStringProperty("N/A");
        });

        colTipoConsulta.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipoConsulta()));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        colData.setCellValueFactory(cellData -> {
            if (cellData.getValue().getDataConsulta() != null) {
                return new SimpleStringProperty(sdf.format(cellData.getValue().getDataConsulta()));
            }
            return new SimpleStringProperty("Data não definida");
        });

        // Botões cancelar / remarcar
        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnCancelar = new Button("Cancelar");
            private final Button btnRemarcar = new Button("Remarcar");

            {
                btnCancelar.getStyleClass().add("btn-cancelar");
                btnRemarcar.getStyleClass().add("btn-remarcar");

                btnCancelar.setOnAction(event -> {
                    Consulta consulta = getTableView().getItems().get(getIndex());
                    if (consulta != null) {
                        cancelarConsulta(consulta);
                    }
                });

                btnRemarcar.setOnAction(event -> {
                    Consulta consulta = getTableView().getItems().get(getIndex());
                    if (consulta != null) {
                        abrirPaneAgendamento(consulta);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Consulta consulta = getTableView().getItems().get(getIndex());
                    // Mostra botões apenas para consultas futuras
                    if (consulta != null && consulta.getDataConsulta() != null &&
                            consulta.getDataConsulta().after(new Date())) {
                        setGraphic(new HBox(5, btnCancelar, btnRemarcar));
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });
    }

    private void configurarFiltroData() {
        // Configura o datepicker para hoje por padrão
        dpFiltroData.setValue(LocalDate.now());

        // Adiciona listener para filtrar automaticamente quando a data mudar
        dpFiltroData.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null) {
                filtrarPorData(newDate);
            }
        });
    }

    private void filtrarPorDataHoje() {
        filtrarPorData(LocalDate.now());
    }

    @FXML
    private void filtrarPorData() {
        LocalDate dataSelecionada = dpFiltroData.getValue();
        if (dataSelecionada != null) {
            filtrarPorData(dataSelecionada);
        }
    }

    private void filtrarPorData(LocalDate dataFiltro) {
        try {
            List<Consulta> filtradas = listaConsultasCompleta.stream()
                    .filter(c -> c != null && c.getDataConsulta() != null)
                    .filter(c -> {
                        // Converte java.util.Date para LocalDate para comparação
                        LocalDate dataConsulta = c.getDataConsulta().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        return dataConsulta.equals(dataFiltro);
                    })
                    .collect(Collectors.toList());

            listaConsultas = FXCollections.observableArrayList(filtradas);
            tableAgendamentos.setItems(listaConsultas);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro ao filtrar consultas por data.");
        }
    }

    @FXML
    private void limparFiltro() {
        dpFiltroData.setValue(null);
        listaConsultas = FXCollections.observableArrayList(listaConsultasCompleta);
        tableAgendamentos.setItems(listaConsultas);
    }

    private void cancelarConsulta(Consulta consulta) {
        try {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmar Cancelamento");
            confirmacao.setHeaderText("Deseja cancelar esta consulta?");
            confirmacao.setContentText("Paciente: " + consulta.getPaciente().getPessoa().getNome() +
                    "\nData: " + consulta.getDataConsulta());

            confirmacao.showAndWait().ifPresent(resposta -> {
                if (resposta == ButtonType.OK) {
                    consulta.alterarStatusConsulta(StatusConsulta.CANCELADA);
                    ConsultaDAO.getInstance().merge(consulta);
                    tableAgendamentos.refresh();
                    mostrarAlertaSucesso("Consulta cancelada com sucesso!");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro ao cancelar consulta.");
        }
    }

    @FXML
    private void agendarConsulta() {
        Consulta consulta = new Consulta();
        trocarPane(CaminhoFXML.PANE_AGENDAMENTO, consulta);
    }

    private void abrirPaneAgendamento(Consulta consulta) {
        trocarPane(CaminhoFXML.PANE_AGENDAMENTO, consulta);
    }

    private void mostrarAlertaErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarAlertaSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}