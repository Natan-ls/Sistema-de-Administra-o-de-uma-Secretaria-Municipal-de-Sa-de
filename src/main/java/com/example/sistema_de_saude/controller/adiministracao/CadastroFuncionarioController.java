package com.example.sistema_de_saude.controller.adiministracao;

import com.example.sistema_de_saude.entity.*;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class CadastroFuncionarioController extends NavegadorPane {

    @FXML public Pane painelPrincipal;
    @FXML public TextField tfNome;
    @FXML public TextField tfCpf;
    @FXML public DatePicker dtDataNascimento;
    @FXML public TextField tfSus;
    @FXML public ChoiceBox<String> cbSexo;
    @FXML public TextField tfEndereco;
    @FXML public TextField tfEmail;
    @FXML public TextField tfTelefone;
    @FXML public ToggleGroup tgTipoFuncionario;

    private Pessoa pessoaTemporaria;

    @FXML
    public void initialize() {
        // Configurar ChoiceBox de sexo
        this.setPainel(painelPrincipal);
        cbSexo.getItems().addAll("Masculino", "Feminino", "Outro");
    }

    public void proximoPane(ActionEvent actionEvent) {
        if (validarCampos()) {
            // Criar pessoa temporária para passar para o próximo painel
            Character sx = 'O';
            switch (cbSexo.getValue()){
                case "Masculino": sx = 'M'; break;
                case "Feminino": sx = 'F'; break;
                case "Outro": sx = 'O'; break;
            }
            pessoaTemporaria = new Pessoa();
            Paciente paciente = new Paciente(tfSus.getText(), pessoaTemporaria);
            pessoaTemporaria.setPaciente(paciente);
            pessoaTemporaria.setNome(tfNome.getText());
            pessoaTemporaria.setCpf(tfCpf.getText());
            pessoaTemporaria.setDataNascimento(java.sql.Date.valueOf(dtDataNascimento.getValue()));
            pessoaTemporaria.setSexo(sx);
            pessoaTemporaria.setEndereco(tfEndereco.getText());
            pessoaTemporaria.setEmail(tfEmail.getText());
            pessoaTemporaria.setTelefone(tfTelefone.getText());

            // Determinar qual próximo painel abrir baseado no tipo selecionado
            RadioButton selected = (RadioButton) tgTipoFuncionario.getSelectedToggle();
            if (selected != null) {
                String tipo = selected.getText();
                UsuarioSistema user = new UsuarioSistema();
                switch (tipo) {
                    case "Médico":
                        Medico medico = new Medico();
                        medico.setPessoa(pessoaTemporaria);
                        user.setFuncionario(medico);
                        user.setTipoUser(UsuarioSistema.TipoUser.MEDICO);
                        trocarPane(CaminhoFXML.PANE_CADASTRO_MEDICO, user);
                        break;
                    case "Farmaceutico":
                        Farmaceutico farmaceutico = new Farmaceutico();
                        farmaceutico.setPessoa(pessoaTemporaria);
                        user.setFuncionario(farmaceutico);
                        user.setTipoUser(UsuarioSistema.TipoUser.FARMACEUTICO);
                        trocarPane(CaminhoFXML.PANE_CADASTRO_FUNCIONARIO_FINAL, user);
                        break;
                    case "Recepcionista":
                        Recepcionista recepcionista = new Recepcionista();
                        recepcionista.setPessoa(pessoaTemporaria);
                        user.setFuncionario(recepcionista);
                        user.setTipoUser(UsuarioSistema.TipoUser.RECEPCIONISTA);
                        trocarPane(CaminhoFXML.PANE_CADASTRO_FUNCIONARIO_FINAL, user);
                        break;
                    case "Administrador":
                        Administrador administrador = new Administrador();
                        administrador.setPessoa(pessoaTemporaria);
                        user.setFuncionario(administrador);
                        user.setTipoUser(UsuarioSistema.TipoUser.ADMINISTRADOR);
                        trocarPane(CaminhoFXML.PANE_CADASTRO_FUNCIONARIO_FINAL, user);
                        break;
                }
            }
        }
    }

    public void voltarPane(ActionEvent actionEvent) {
        // Voltar para o painel anterior
        trocarPane(CaminhoFXML.PANE_CADASTRO_FUNCIONARIO);
    }

    private boolean validarCampos() {
        // Validação básica dos campos
        if (tfNome.getText().isEmpty() || tfCpf.getText().isEmpty() ||
                dtDataNascimento.getValue() == null || tfSus.getText().isEmpty()) {
            mostrarAlerta("Campos obrigatórios", "Preencha todos os campos obrigatórios.");
            return false;
        }

        if (tgTipoFuncionario.getSelectedToggle() == null) {
            mostrarAlerta("Seleção necessária", "Selecione o tipo de funcionário.");
            return false;
        }

        return true;
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}