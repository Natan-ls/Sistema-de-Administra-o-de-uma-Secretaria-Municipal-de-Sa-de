package com.example.sistema_de_saude.controller;

import com.example.sistema_de_saude.entity.Pessoa;
import com.example.sistema_de_saude.util.Validador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class PacientesController {

    @FXML
    public TableView tbViewPacientes;
    @FXML
    public TableColumn columnNome;
    @FXML
    public TableColumn columnCpf;
    @FXML
    public TableColumn columnDataNascimento;
    @FXML
    public TableColumn columnTelefone;
    @FXML
    public TableColumn columnSus;
    @FXML
    public TableColumn columnSexo;
    @FXML
    public TextField tfNome;
    @FXML
    public TextField tfCpf;
    @FXML
    public DatePicker dtDataNascimento;
    @FXML
    public TextField tfSus;
    @FXML
    public ChoiceBox<String> cbSexo;
    @FXML
    public TextField tfEndereco;

    public void initialize(){
        cbSexo.getItems().addAll("Masculino", "Feminino", "Outro");
    }

    public void adicionarPaciente(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);

        if (tfNome.getText().trim().isEmpty() ||
                tfEndereco.getText().trim().isEmpty() ||
                tfSus.getText().trim().isEmpty() ||
                tfCpf.getText().trim().isEmpty() ||
                cbSexo.getValue() == null ||
                dtDataNascimento.getValue() == null) {
            alert.setTitle("Campo obrigatório");
            alert.setContentText("Alguns valores estão em branco.");
            alert.showAndWait();
        }

        if(!Validador.cpfIsValido(tfCpf.getText())){
            alert.setTitle("Cpf Inválido");
            alert.setContentText("CPF está inválido, atenção ai mano!");
            alert.showAndWait();
        }

    }
}
