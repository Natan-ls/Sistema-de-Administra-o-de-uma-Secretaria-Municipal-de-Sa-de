package com.example.sistema_de_saude.controller.recepcao;

import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class OpcoesCrudController extends NavegadorPane {

    @FXML
    private Pane painelPrincipal;

    @FXML
    public void initialize() {
        this.setPainel(painelPrincipal);
    }

    public void cadastrarPaciente(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_PACIENTE);
    }

    public void buscarPaciente(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_BUSCAR_PACIENTE);
    }

    public void excluirPaciente(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_EXCLUIR_PACIENTE);
    }

    public void atualizarPaciente(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_ALTERAR_PACIENTE);
    }

    public void mostrarPacientes(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_TABELA_PACIENTES);
    }
}
