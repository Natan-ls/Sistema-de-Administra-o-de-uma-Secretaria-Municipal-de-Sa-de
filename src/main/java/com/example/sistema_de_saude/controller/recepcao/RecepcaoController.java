package com.example.sistema_de_saude.controller.recepcao;

import com.example.sistema_de_saude.entity.Recepcionista;
import com.example.sistema_de_saude.entity.UsuarioSistema;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import com.example.sistema_de_saude.util.ReceberDados;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class RecepcaoController extends NavegadorPane {

    @FXML
    public Label tfUser;
    @FXML
    private Button btnPaciente;
    @FXML
    private Button btnAgendamento;
    @FXML
    private Pane painelPrincipal;

    @FXML
    public void initialize() throws IOException {
        this.setPainel(painelPrincipal);
        Pane inicial = FXMLLoader.load(getClass().getResource(CaminhoFXML.PANE_OPCOES_CRUD));
        painelPrincipal.getChildren().setAll(inicial);
    }

    public void moduloPaciente(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_PACIENTE);
    }


    public void moduloAgendamento(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_AGENDAMENTO);
    }

}
