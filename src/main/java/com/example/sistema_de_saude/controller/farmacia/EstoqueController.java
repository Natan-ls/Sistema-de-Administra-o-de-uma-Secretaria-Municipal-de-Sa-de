package com.example.sistema_de_saude.controller.farmacia;

import com.example.sistema_de_saude.dataAccess.MedicamentoDAO;
import com.example.sistema_de_saude.dataAccess.TipoMedicamentoDAO;
import com.example.sistema_de_saude.entity.LoteMedicamento;
import com.example.sistema_de_saude.entity.Medicamento;
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
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class EstoqueController extends NavegadorPane {

    @FXML public AnchorPane painelPrincipal;
    @FXML private TableView<TipoMedicamento> tableMedicamento;
    @FXML private TableColumn<TipoMedicamento, String> colNome;
    @FXML private TableColumn<TipoMedicamento, String> colTipo;
    @FXML private TableColumn<TipoMedicamento, Integer> colQuantidade;
    @FXML private TableColumn<TipoMedicamento, Integer> colQuantidadeMinima;
    @FXML private TableColumn<TipoMedicamento, LocalDate> colValidade;
    @FXML private TextField tfNomeRemedio;

    private ObservableList<TipoMedicamento> listaMedicamentos;

    @FXML
    public void initialize() {
        this.setPainel(painelPrincipal);
        configurarColunas();
        carregarTodosTipos();
    }

    private void configurarColunas() {
        // Coluna Nome
        colNome.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMedicamento().getNome()));

        // Coluna Tipo
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        // Coluna Quantidade
        colQuantidade.setCellValueFactory(cellData -> {
            int total = cellData.getValue().getLoteMedicamentos().stream()
                    .mapToInt(LoteMedicamento::getQuantidadeEstoque)
                    .sum();
            return new SimpleIntegerProperty(total).asObject();
        });

        // Coluna Quantidade Mínima
        colQuantidadeMinima.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getEstoqueMinimo()).asObject());

        // Coluna Validade - CELL VALUE FACTORY
        colValidade.setCellValueFactory(cellData -> {
            Optional<LocalDate> menorValidade = cellData.getValue().getLoteMedicamentos().stream()
                    .filter(l -> l.getDataValidade() != null) // Filtra valores nulos
                    .map(l -> l.getDataValidade().toInstant()
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDate())
                    .min(LocalDate::compareTo);
            return new SimpleObjectProperty<>(menorValidade.orElse(null));
        });

        // ✅ CELL FACTORY AJUSTADO - MOSTRA "N/A" APENAS PARA LOTES VAZIOS
        colValidade.setCellFactory(column -> new TableCell<TipoMedicamento, LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                    setStyle("");
                } else if (item == null) {
                    // Verifica se realmente não tem lotes
                    TipoMedicamento tipoMed = getTableView().getItems().get(getIndex());
                    if (tipoMed.getLoteMedicamentos() == null || tipoMed.getLoteMedicamentos().isEmpty()) {
                        setText("Sem lotes");
                    } else {
                        setText("N/A"); // Tem lotes mas sem data de validade
                    }
                    setStyle("");
                } else {
                    setText(formatter.format(item));

                    // Destacar validades próximas
                    if (item.isBefore(LocalDate.now().plusMonths(3))) {
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
    }

    private void carregarTodosTipos() {
        listaMedicamentos = FXCollections.observableArrayList(TipoMedicamentoDAO.getInstance().findAll());
        tableMedicamento.setItems(listaMedicamentos);
    }

    @FXML
    public void adicionarTipoMedicamento(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_CADASTRAR_REMEDIO);
    }

    @FXML
    public void atualizarTipoMedicamento(ActionEvent actionEvent) {
        TipoMedicamento selecionado = tableMedicamento.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            trocarPane(CaminhoFXML.PANE_ATUALIZAR_TIPO_MEDICAMENTO, selecionado);
        } else {
            mostrarAlerta("Seleção necessária", "Selecione um tipo de medicamento para atualizar.");
        }
    }

    @FXML
    public void removerTipoMedicamento(ActionEvent actionEvent) {
        TipoMedicamento selecionado = tableMedicamento.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            TipoMedicamentoDAO.getInstance().remove(selecionado);
            listaMedicamentos.remove(selecionado);
        } else {
            mostrarAlerta("Seleção necessária", "Selecione um tipo de medicamento para remover.");
        }
    }

    @FXML
    public void buscar(ActionEvent actionEvent) {
        String nome = tfNomeRemedio.getText().trim();
        if (nome.isEmpty()) {
            carregarTodosTipos();
            return;
        }

        Medicamento medicamento = MedicamentoDAO.getInstance().getByNomeMedicamento(nome);
        if (medicamento != null) {
            listaMedicamentos = FXCollections.observableArrayList(medicamento.getTipoMedicamentos());
            tableMedicamento.setItems(listaMedicamentos);
        } else {
            tableMedicamento.getItems().clear();
            mostrarAlerta("Não encontrado", "Nenhum medicamento encontrado com este nome.");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
