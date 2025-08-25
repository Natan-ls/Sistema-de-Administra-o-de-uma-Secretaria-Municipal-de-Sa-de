package com.example.sistema_de_saude.controller.consulta;

import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ViewConsultaControlle extends NavegadorPane {

    public Pane painelPrincipal;

    @FXML
    public void initialize() throws IOException {
        this.setPainel(painelPrincipal);
        Pane inicial = FXMLLoader.load(getClass().getResource(CaminhoFXML.PANE_CONSULTAS));
        painelPrincipal.getChildren().setAll(inicial);
    }

}
