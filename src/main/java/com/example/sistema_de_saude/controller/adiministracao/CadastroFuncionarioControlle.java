package com.example.sistema_de_saude.controller.adiministracao;

import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class CadastroFuncionarioControlle extends NavegadorPane {
    public Pane painelPrincipal;
    public TextField tfNome;
    public TextField tfCpf;
    public DatePicker dtDataNascimento;
    public TextField tfSus;
    public ChoiceBox cbSexo;
    public TextField tfEndereco;
    public TextField tfEmail;
    public TextField tfTelefone;
    public ToggleGroup tgTipoFuncionario;

    public void proximoPane(ActionEvent actionEvent) {
    }

    public void voltarPane(ActionEvent actionEvent) {
    }
}
