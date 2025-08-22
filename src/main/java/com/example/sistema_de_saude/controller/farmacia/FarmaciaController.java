package com.example.sistema_de_saude.controller.farmacia;

import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class FarmaciaController extends NavegadorPane {

    @FXML
    public Pane painelPrincipal;
    public MenuButton medicamentosCrud;

    @FXML
    public void initialize() throws IOException {
        this.setPainel(painelPrincipal);
        Pane inicial = FXMLLoader.load(getClass().getResource(CaminhoFXML.PANE_ESTOQUE));
        painelPrincipal.getChildren().setAll(inicial);
    }

    public void areaEstoque(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_ESTOQUE);
    }

    public void areaEntregarMedicamentos(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_ENTREGA_REMEDIO);
    }
}
