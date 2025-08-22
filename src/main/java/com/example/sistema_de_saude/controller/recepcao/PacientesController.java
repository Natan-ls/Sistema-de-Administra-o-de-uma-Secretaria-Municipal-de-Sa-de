package com.example.sistema_de_saude.controller.recepcao;

import com.example.sistema_de_saude.dataAccess.PacienteDAO;
import com.example.sistema_de_saude.dataAccess.PessoaDAO;
import com.example.sistema_de_saude.entity.Paciente;
import com.example.sistema_de_saude.entity.Pessoa;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import com.example.sistema_de_saude.util.Validador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class PacientesController extends NavegadorPane {

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
    @FXML
    public TextField tfEmail;
    @FXML
    public Pane painelPrincipal;

    @FXML
    public void initialize() throws IOException {
        this.setPainel(painelPrincipal);
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
            return;
        }

        if(!Validador.cpfIsValido(tfCpf.getText())){
            alert.setTitle("Cpf Inválido");
            alert.setContentText("CPF está inválido, atenção ai mano!");
            alert.showAndWait();
            tfCpf.setText("");
            return;
        }

        if(!Validador.susIsValido(tfSus.getText())){
            alert.setTitle("Cartão Sus inválido");
            alert.setContentText("O cartão do sus está inválido, atenção ai mano!");
            alert.showAndWait();
            tfSus.setText("");
            return;
        }

        Pessoa pessoaExistente = PessoaDAO.getInstance().findByCpf(tfCpf.getText());
        if(pessoaExistente != null && pessoaExistente.getCpf().equals(tfCpf.getText())){
            alert.setTitle("Cpf cadastrado");
            alert.setContentText("CPF já está cadastrado no sistema!");
            alert.showAndWait();
            tfCpf.setText("");
            return;
        }

        Paciente pacienteExistente = PacienteDAO.getInstance().findBySus(tfSus.getText());
        if(pacienteExistente != null && pacienteExistente.getNumeroSus().equals(tfSus.getText())){
            alert.setTitle("Número do Sus cadastrado");
            alert.setContentText("Número do Sus já está cadastrado no sistema!");
            alert.showAndWait();
            tfCpf.setText("");
            return;
        }

        cadastrarPaciente();
        limpar();
        trocarPane(CaminhoFXML.PANE_OPCOES_CRUD);
    }

    private void cadastrarPaciente(){
        LocalDate localDate = dtDataNascimento.getValue();
        Date dt = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Character sx = 'O';
        switch (cbSexo.getValue()){
            case "Masculino" : sx = 'M' ;
                break;
            case "Feminino"  : sx = 'F' ;
                break;
            case "Outros"    : sx = 'O' ;
                break;
        }

        Pessoa pessoa = new Pessoa(tfNome.getText(), tfCpf.getText(),(tfNome.getText()+"@gmail.com"), dt, sx, tfEndereco.getText());
        Paciente paciente = new Paciente(tfSus.getText(), pessoa);
        pessoa.setPaciente(paciente);
        PacienteDAO.getInstance().persist(paciente, pessoa);
    }

    private void limpar(){
        tfNome.setText("");
        tfCpf.setText("");
        tfEndereco.setText("");
        tfSus.setText("");
        cbSexo.setValue("");
        dtDataNascimento.setValue(null);
    }

    public void voltarPane(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_OPCOES_CRUD);
    }
}
