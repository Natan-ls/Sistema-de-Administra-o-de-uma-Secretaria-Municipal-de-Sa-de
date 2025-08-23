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
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

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
    public TextField tfTelefone;

    @FXML
    public void initialize() {
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
                dtDataNascimento.getValue() == null ||
                tfEmail.getText().trim().isEmpty() ||
                tfTelefone.getText().trim().isEmpty()) {
            alert.setTitle("Campo obrigatório");
            alert.setContentText("Alguns valores estão em branco.");
            alert.showAndWait();
            return;
        }

        // Formata CPF e telefone
        String cpfFormatado = formatarCpf(tfCpf.getText());
        String telefoneFormatado = formatarTelefone(tfTelefone.getText());

        if(!Validador.cpfIsValido(cpfFormatado)){
            alert.setTitle("Cpf Inválido");
            alert.setContentText("CPF está inválido.");
            alert.showAndWait();
            tfCpf.setText("");
            return;
        }

        if(!Validador.susIsValido(tfSus.getText())){
            alert.setTitle("Cartão Sus inválido");
            alert.setContentText("O cartão do sus está inválido.");
            alert.showAndWait();
            tfSus.setText("");
            return;
        }

        Pessoa pessoaExistente = PessoaDAO.getInstance().findByCpf(cpfFormatado);
        if(pessoaExistente != null){
            alert.setTitle("Cpf cadastrado");
            alert.setContentText("CPF já está cadastrado no sistema!");
            alert.showAndWait();
            tfCpf.setText("");
            return;
        }

        Paciente pacienteExistente = PacienteDAO.getInstance().findBySus(tfSus.getText());
        if(pacienteExistente != null){
            alert.setTitle("Número do Sus cadastrado");
            alert.setContentText("Número do Sus já está cadastrado no sistema!");
            alert.showAndWait();
            tfSus.setText("");
            return;
        }

        cadastrarPaciente(cpfFormatado, telefoneFormatado);
        limpar();
        trocarPane(CaminhoFXML.PANE_OPCOES_CRUD);
    }

    private void cadastrarPaciente(String cpf, String telefone){
        LocalDate localDate = dtDataNascimento.getValue();
        Date dt = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Character sx = 'O';
        switch (cbSexo.getValue()){
            case "Masculino": sx = 'M'; break;
            case "Feminino": sx = 'F'; break;
            case "Outro": sx = 'O'; break;
        }

        Pessoa pessoa = new Pessoa(
                tfNome.getText(),
                cpf,
                tfEmail.getText(),
                dt,
                sx,
                tfEndereco.getText()
        );
        pessoa.setTelefone(telefone);

        Paciente paciente = new Paciente(tfSus.getText(), pessoa);
        pessoa.setPaciente(paciente);

        PacienteDAO.getInstance().persist(paciente);
    }

    private void limpar(){
        tfNome.setText("");
        tfCpf.setText("");
        tfEndereco.setText("");
        tfSus.setText("");
        cbSexo.setValue("");
        dtDataNascimento.setValue(null);
        tfEmail.setText("");
        tfTelefone.setText("");
    }

    public void voltarPane(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_OPCOES_CRUD);
    }

    // Formata CPF no padrão xxx.xxx.xxx-xx
    private String formatarCpf(String cpf){
        String somenteDigitos = cpf.replaceAll("\\D", "");
        if(somenteDigitos.length() != 11) return cpf;
        return somenteDigitos.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})","$1.$2.$3-$4");
    }

    // Formata telefone no padrão (xx) xxxxx-xxxx
    private String formatarTelefone(String telefone){
        String somenteDigitos = telefone.replaceAll("\\D", "");
        if(somenteDigitos.length() == 11){
            return somenteDigitos.replaceFirst("(\\d{2})(\\d{5})(\\d{4})","($1) $2-$3");
        } else if(somenteDigitos.length() == 10){
            return somenteDigitos.replaceFirst("(\\d{2})(\\d{4})(\\d{4})","($1) $2-$3");
        } else {
            return telefone;
        }
    }
}
