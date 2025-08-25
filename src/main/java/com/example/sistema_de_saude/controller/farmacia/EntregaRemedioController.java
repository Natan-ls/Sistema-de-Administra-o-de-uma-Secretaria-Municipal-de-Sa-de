package com.example.sistema_de_saude.controller.farmacia;

import com.example.sistema_de_saude.dataAccess.LoteMedicamentoDAO;
import com.example.sistema_de_saude.dataAccess.ProtocoloDAO;
import com.example.sistema_de_saude.entity.*;
import com.example.sistema_de_saude.util.NavegadorPane;
import jakarta.persistence.EntityManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EntregaRemedioController extends NavegadorPane {

    @FXML private TextField tfBuscarProtocolo;
    @FXML private TableView<MedicamentoRow> tableMedicamento;
    @FXML private TableColumn<MedicamentoRow, String> colNome;
    @FXML private TableColumn<MedicamentoRow, MedicamentoRow> colTipo;
    @FXML private TableColumn<MedicamentoRow, MedicamentoRow> colMg;
    @FXML private TableColumn<MedicamentoRow, MedicamentoRow> colQtdCaixa;
    @FXML private TableColumn<MedicamentoRow, String> colQuantidade;
    @FXML private TableColumn<MedicamentoRow, String> colStatus;
    @FXML private Button btnFinalizar;

    private Protocolo protocoloAtual;

    @FXML
    public void initialize() {
        tableMedicamento.setEditable(true);

        // Coluna Nome
        colNome.setCellValueFactory(cell -> cell.getValue().nomeProperty());

        // Coluna Tipo (ChoiceBox)
        colTipo.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()));
        colTipo.setCellFactory(tc -> new TableCell<>() {
            private final ChoiceBox<String> choiceBox = new ChoiceBox<>();
            @Override
            protected void updateItem(MedicamentoRow row, boolean empty) {
                super.updateItem(row, empty);
                if (empty || row == null) {
                    setGraphic(null);
                    return;
                }
                choiceBox.setItems(FXCollections.observableArrayList(
                        row.getTipos().stream().map(TipoMedicamento::getTipo).distinct().collect(Collectors.toList())
                ));
                choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                    row.setTipoSelecionado(newVal);
                    row.atualizarUnidades(); // atualiza unidadeMedida
                });
                setGraphic(choiceBox);
            }
        });

        // Coluna Unidade/Medida (ChoiceBox)
        colMg.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()));
        colMg.setCellFactory(tc -> new TableCell<>() {
            private final ChoiceBox<String> choiceBox = new ChoiceBox<>();
            @Override
            protected void updateItem(MedicamentoRow row, boolean empty) {
                super.updateItem(row, empty);
                if (empty || row == null) {
                    setGraphic(null);
                    return;
                }
                choiceBox.setItems(row.getUnidadesObservable());
                choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                    row.setUnidadeSelecionada(newVal);
                    row.atualizarQtdCaixa(); // atualiza Quantidade/Caixa
                });
                setGraphic(choiceBox);
            }
        });

        // Coluna Quantidade/Caixa (ChoiceBox)
        colQtdCaixa.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()));
        colQtdCaixa.setCellFactory(tc -> new TableCell<>() {
            private final ChoiceBox<Integer> choiceBox = new ChoiceBox<>();
            @Override
            protected void updateItem(MedicamentoRow row, boolean empty) {
                super.updateItem(row, empty);
                if (empty || row == null) {
                    setGraphic(null);
                    return;
                }
                choiceBox.setItems(row.getQtdCaixaObservable());
                choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                    row.setQtdCaixaSelecionada(newVal);
                });
                setGraphic(choiceBox);
            }
        });

        // Coluna Quantidade entregue (TextField editável)
        colQuantidade.setCellValueFactory(cell -> cell.getValue().quantidadeProperty());
        colQuantidade.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(String object) { return object; }
            @Override
            public String fromString(String string) { return string; }
        }));

        // Coluna Status
        colStatus.setCellValueFactory(cell -> cell.getValue().statusProperty());

        btnFinalizar.setOnAction(e -> finalizarEntrega());
    }

    @FXML
    public void buscarProtocolo() {
        String codigo = tfBuscarProtocolo.getText().trim();
        if (codigo.isEmpty()) return;

        Protocolo p = ProtocoloDAO.getInstance().getByCodigo(codigo);
        protocoloAtual = ProtocoloDAO.getInstance().getProtocoloComMedicamentos(p.getId());
        if (protocoloAtual == null) {
            showAlert("Protocolo não encontrado!");
            tableMedicamento.getItems().clear();
            return;
        }
        if (Boolean.FALSE.equals(protocoloAtual.getStatus())) {
            showAlert("Protocolo já finalizado!");
            tableMedicamento.getItems().clear();
            return;
        }

        ObservableList<MedicamentoRow> rows = FXCollections.observableArrayList();
        for (Medicamento m : protocoloAtual.getMedicamentos()) {
            boolean disponivel = m.getTipoMedicamentos().stream()
                    .flatMap(t -> t.getLoteMedicamentos().stream())
                    .anyMatch(l -> l.getQuantidadeEstoque() >= l.getTipoMedicamento().getEstoqueMinimo());

            String status = disponivel ? "Disponível" : "Indisponível";
            rows.add(new MedicamentoRow(m, status));
        }
        tableMedicamento.setItems(rows);
    }

    @FXML
    public void finalizarEntrega() {
        if (protocoloAtual == null) return;

        EntityManager em = LoteMedicamentoDAO.getInstance().getEntityManager();
        em.getTransaction().begin();

        try {
            // Atualiza status do protocolo
            Date hoje = new Date();
            protocoloAtual.finalizarProtocolo((Farmaceutico) usuario.getFuncionario(), hoje);
            protocoloAtual = em.merge(protocoloAtual); // garante que está gerenciado

            for (MedicamentoRow row : tableMedicamento.getItems()) {
                int qtd = row.getQuantidadeSelecionada();
                if (qtd <= 0) continue;

                TipoMedicamento tipoSelecionado = row.getTipoSelecionadoObj();
                if (tipoSelecionado == null) continue;

                // Busca lotes gerenciados pelo EM, ordenando por validade
                List<LoteMedicamento> lotes = em.createQuery(
                                "SELECT l FROM LoteMedicamento l " +
                                        "WHERE l.tipoMedicamento.id = :tipoId AND l.quantidadeEstoque > 0 " +
                                        "ORDER BY l.dataValidade ASC", LoteMedicamento.class)
                        .setParameter("tipoId", tipoSelecionado.getId())
                        .getResultList();

                int restante = qtd;

                for (LoteMedicamento lote : lotes) {

                    lote.atualizarStatus();
                    if (lote.getStatus() == LoteMedicamento.StatusLote.VENCIDO) {

                        em.merge(lote);
                        continue;
                    }

                    int estoque = lote.getQuantidadeEstoque();
                    if (estoque <= 0) continue;

                    int diminuir = Math.min(restante, estoque);
                    lote.diminuirEstoque(diminuir);

                    restante -= diminuir;
                    if (restante == 0) break;
                }

                if (restante > 0) {
                    em.getTransaction().rollback();
                    showAlert("Estoque insuficiente para o medicamento: " );
                    return;
                }
            }

            em.getTransaction().commit();
            showAlert("Entrega finalizada!");
            tableMedicamento.getItems().clear();

        } catch (Exception ex) {
            em.getTransaction().rollback();
            showAlert("Erro ao finalizar entrega: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
