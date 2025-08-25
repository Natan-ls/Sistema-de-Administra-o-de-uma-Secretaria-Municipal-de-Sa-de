package com.example.sistema_de_saude.controller.farmacia;

import com.example.sistema_de_saude.dataAccess.TipoMedicamentoDAO;
import com.example.sistema_de_saude.entity.LoteMedicamento;
import com.example.sistema_de_saude.entity.TipoMedicamento;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EstoqueController extends NavegadorPane {

    @FXML public AnchorPane painelPrincipal;
    @FXML private TableView<LoteMedicamento> tableMedicamento;
    @FXML private TableColumn<LoteMedicamento, String> colNome;
    @FXML private TableColumn<LoteMedicamento, String> colTipo;
    @FXML private TableColumn<LoteMedicamento, Integer> colQuantidade;
    @FXML private TableColumn<LoteMedicamento, Integer> colQuantidadeMinima;
    @FXML private TableColumn<LoteMedicamento, LocalDate> colValidade;
    @FXML private TextField tfNomeRemedio;
    @FXML private ChoiceBox<String> cbFiltro;

    private ObservableList<LoteMedicamento> listaLotes;

    @FXML
    public void initialize() {
        this.setPainel(painelPrincipal);

        // Configura ChoiceBox
        cbFiltro.getItems().addAll("Todos", "Vencidos", "Não vencidos", "Agrupar por tipo");
        cbFiltro.setValue("Todos");
        cbFiltro.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> aplicarFiltro());

        atualizarTabela();
    }

    public void atualizarTabela(){
        configurarColunas();
        carregarTodosLotes();
    }

    private void configurarColunas() {
        colNome.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipoMedicamento().getMedicamento().getNome()));

        colTipo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipoMedicamento().getTipo()));

        colQuantidade.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getQuantidadeEstoque()).asObject());

        colQuantidadeMinima.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getTipoMedicamento().getEstoqueMinimo()).asObject());

        colValidade.setCellValueFactory(cellData -> {
            LoteMedicamento lote = cellData.getValue();
            LocalDate validade = lote.getDataValidade().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return new SimpleObjectProperty<>(validade);
        });

        colQuantidade.setCellFactory(column -> new TableCell<LoteMedicamento, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    LoteMedicamento lote = getTableView().getItems().get(getIndex());
                    setText(item.toString());
                    if (item < lote.getTipoMedicamento().getEstoqueMinimo()) {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        colValidade.setCellFactory(column -> new TableCell<LoteMedicamento, LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(formatter.format(item));
                    if (item.isBefore(LocalDate.now())) {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
    }

    private void carregarTodosLotes() {
        List<LoteMedicamento> todosLotes = TipoMedicamentoDAO.getInstance().findAllFresh().stream()
                .flatMap(tipo -> tipo.getLoteMedicamentos().stream())
                .collect(Collectors.toList());

        listaLotes = FXCollections.observableArrayList(todosLotes);
        tableMedicamento.setItems(listaLotes);
    }

    @FXML
    public void buscar(ActionEvent actionEvent) {
        String nome = tfNomeRemedio.getText().trim();
        if (nome.isEmpty()) {
            aplicarFiltro();
            return;
        }

        List<LoteMedicamento> filtrados = TipoMedicamentoDAO.getInstance().findAll().stream()
                .filter(tipo -> tipo.getMedicamento().getNome().equalsIgnoreCase(nome))
                .flatMap(tipo -> tipo.getLoteMedicamentos().stream())
                .collect(Collectors.toList());

        listaLotes = FXCollections.observableArrayList(filtrados);
        aplicarFiltro();
    }

    private void aplicarFiltro() {
        if (listaLotes == null) return;

        String filtro = cbFiltro.getValue();
        List<LoteMedicamento> filtrados;

        switch (filtro) {
            case "Vencidos":
                filtrados = listaLotes.stream().filter(l -> l.getDataValidade().before(new java.util.Date())).collect(Collectors.toList());
                tableMedicamento.setItems(FXCollections.observableArrayList(filtrados));
                break;
            case "Não vencidos":
                filtrados = listaLotes.stream().filter(l -> !l.getDataValidade().before(new java.util.Date())).collect(Collectors.toList());
                tableMedicamento.setItems(FXCollections.observableArrayList(filtrados));
                break;
            case "Agrupar por tipo":
                List<LoteMedicamento> agrupados = TipoMedicamentoDAO.getInstance().findAll().stream()
                        .map(tipo -> {
                            int total = tipo.getLoteMedicamentos().stream()
                                    .filter(l -> !l.getDataValidade().before(new java.util.Date()))
                                    .mapToInt(LoteMedicamento::getQuantidadeEstoque)
                                    .sum();
                            LoteMedicamento resumo = new LoteMedicamento();
                            resumo.setTipoMedicamento(tipo);
                            resumo.setQuantidadeEstoque(total);
                            resumo.setQuantidadeEntrada(total);
                            resumo.setDataValidade(tipo.getLoteMedicamentos().stream()
                                    .map(LoteMedicamento::getDataValidade)
                                    .min(java.util.Date::compareTo).orElse(null));
                            return resumo;
                        }).collect(Collectors.toList());
                tableMedicamento.setItems(FXCollections.observableArrayList(agrupados));
                break;
            default: // Todos
                tableMedicamento.setItems(listaLotes);
        }
    }

    @FXML
    public void adicionarTipoMedicamento(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_CADASTRAR_REMEDIO);
    }

    @FXML
    public void atualizarTipoMedicamento(ActionEvent actionEvent) {
        LoteMedicamento selecionado = tableMedicamento.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            trocarPane(CaminhoFXML.PANE_ATUALIZAR_TIPO_MEDICAMENTO, selecionado.getTipoMedicamento());
        } else {
            mostrarAlerta("Seleção necessária", "Selecione um lote para atualizar.");
        }
    }

    @FXML
    public void removerTipoMedicamento(ActionEvent actionEvent) {
        LoteMedicamento selecionado = tableMedicamento.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            TipoMedicamento tipo = selecionado.getTipoMedicamento();
            tipo.getLoteMedicamentos().remove(selecionado);
            listaLotes.remove(selecionado);
        } else {
            mostrarAlerta("Seleção necessária", "Selecione um lote para remover.");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
