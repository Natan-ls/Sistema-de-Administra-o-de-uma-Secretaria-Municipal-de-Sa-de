package com.example.sistema_de_saude.controller.consulta;

import com.example.sistema_de_saude.entity.UsuarioSistema;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import com.example.sistema_de_saude.util.ReceberDados;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ViewConsultaControlle extends NavegadorPane implements ReceberDados<UsuarioSistema> {

    public Pane painelPrincipal;
    private UsuarioSistema user;

    @FXML
    public void initialize() throws IOException {
        this.setPainel(painelPrincipal);
        Pane inicial = FXMLLoader.load(getClass().getResource(CaminhoFXML.PANE_CONSULTAS));
        painelPrincipal.getChildren().setAll(inicial);
    }

    @Override
    public void setDados(UsuarioSistema dados) {
        this.user = dados;
    }
}
