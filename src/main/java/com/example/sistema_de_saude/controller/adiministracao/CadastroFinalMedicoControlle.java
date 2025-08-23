package com.example.sistema_de_saude.controller.adiministracao;

import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class CadastroFinalMedicoControlle extends NavegadorPane {
    public Pane painelPrincipal;
    public TextField tfMatricula;
    public DatePicker dtDataAdimissao;
    public TextField tfSetor;
    public TextField tfCrf;
    public TextField tfEspecialidade;
    public TextField tfNomeFantasia;

    public void salvar(ActionEvent actionEvent) {
    }

    public void voltarPane(ActionEvent actionEvent) {
    }
}
