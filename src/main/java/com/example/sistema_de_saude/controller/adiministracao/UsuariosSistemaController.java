package com.example.sistema_de_saude.controller.adiministracao;

import com.example.sistema_de_saude.dataAccess.UsuarioSistemaDAO;
import com.example.sistema_de_saude.entity.UsuarioSistema;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class UsuariosSistemaController extends NavegadorPane {

    @FXML private AnchorPane painelPrincipal;
    @FXML private TableView<UsuarioSistema> tableUsuarios;
    @FXML private TableColumn<UsuarioSistema, String> colNome;
    @FXML private TableColumn<UsuarioSistema, String> colLogin;
    @FXML private TableColumn<UsuarioSistema, String> colSituacao;
    @FXML private TableColumn<UsuarioSistema, Void> colAcao;
    @FXML private TextField tfBuscarUsuario;

    private ObservableList<UsuarioSistema> listaUsuarios;

    @FXML
    public void initialize() {
        this.setPainel(painelPrincipal);
        configurarColunas();
        carregarUsuarios();
    }

    private void configurarColunas() {
        // Nome do funcionário
        colNome.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFuncionario().getPessoa().getNome())
        );

        // Login
        colLogin.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLogin())
        );

        // Situação (ativo/inativo)
        colSituacao.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isAtivo() ? "Ativo" : "Inativo")
        );

        // Botões de ação
        colAcao.setCellFactory(param -> new TableCell<>() {
            private final Button btnAtivar = new Button();
            private final Button btnEditar = new Button("Editar");
            private final Button btnRemover = new Button("🗑");

            {
                btnAtivar.setOnAction(event -> {
                    UsuarioSistema user = getTableView().getItems().get(getIndex());
                    user.setAtivo(!user.isAtivo());
                    // Usa merge para atualizar no banco
                    UsuarioSistemaDAO.getInstance().merge(user);
                    tableUsuarios.refresh();
                });

                btnEditar.setOnAction(event -> {
                    UsuarioSistema user = getTableView().getItems().get(getIndex());
                    // Troca para o Pane de atualizar funcionário, passando o usuário selecionado
                    trocarPane(CaminhoFXML.PANE_ATUALIZAR_FUNCIONARIO, user);
                });


                btnRemover.setOnAction(event -> {
                    UsuarioSistema user = getTableView().getItems().get(getIndex());
                    // Remove usando DAO
                    UsuarioSistemaDAO.getInstance().remove(user);
                    listaUsuarios.remove(user);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    UsuarioSistema user = getTableView().getItems().get(getIndex());
                    btnAtivar.setText(user.isAtivo() ? "Desativar" : "Ativar");
                    btnAtivar.setStyle("-fx-background-radius: 5;");
                    btnEditar.setStyle("-fx-background-radius: 5; -fx-background-color: #e0e0e0;");
                    btnRemover.setStyle("-fx-background-radius: 5; -fx-background-color: #ffcccc;");
                    setGraphic(new HBox(5, btnAtivar, btnEditar, btnRemover));
                }
            }
        });
    }

    private void carregarUsuarios() {
        listaUsuarios = FXCollections.observableArrayList(UsuarioSistemaDAO.getInstance().findAll());
        tableUsuarios.setItems(listaUsuarios);
    }

    @FXML
    public void buscarUsuario(ActionEvent actionEvent) {
        String texto = tfBuscarUsuario.getText().trim().toLowerCase();
        if (texto.isEmpty()) {
            carregarUsuarios();
            return;
        }

        listaUsuarios = FXCollections.observableArrayList(
                UsuarioSistemaDAO.getInstance().findAll().stream()
                        .filter(u -> u.getFuncionario().getPessoa().getNome().toLowerCase().contains(texto)
                                || u.getLogin().toLowerCase().contains(texto))
                        .toList()
        );
        tableUsuarios.setItems(listaUsuarios);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
