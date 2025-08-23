package com.example.sistema_de_saude.controller.adiministracao;

import com.example.sistema_de_saude.entity.*;
import com.example.sistema_de_saude.dataAccess.*;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import com.example.sistema_de_saude.util.ReceberDados;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class CadastroFinalFuncionarioController extends NavegadorPane implements ReceberDados<UsuarioSistema> {

    @FXML public TextField tfMatricula;
    @FXML public DatePicker dtDataAdimissao;
    @FXML public TextField tfSetor;
    @FXML public TextField tfCrf;
    @FXML public Pane painelPrincipal;
    @FXML public Label lblCrf;

    private UsuarioSistema user;

    @FXML
    public void initialize() {
        // Configurar ChoiceBox de sexo
        this.setPainel(painelPrincipal);

    }

    public void proximoPane(ActionEvent actionEvent) {
        if (validarCampos()) {
            try {

                PacienteDAO.getInstance().persist(user.getFuncionario().getPessoa().getPaciente());

                // Atualizar dados do funcionário
                Funcionario funcionario = user.getFuncionario();
                funcionario.setPessoa(user.getFuncionario().getPessoa());
                funcionario.setMatricula(Integer.parseInt(tfMatricula.getText()));
                funcionario.setDataAdimissao(java.sql.Date.valueOf(dtDataAdimissao.getValue()));

                // Persistir conforme tipo específico
                switch (user.getTipoUser()) {
                    case FARMACEUTICO -> {
                        Farmaceutico farmaceutico = (Farmaceutico) funcionario;
                        farmaceutico.setCrf(tfCrf.getText());
                        FarmaceuticoDAO.getInstance().persist(farmaceutico);
                    }
                    case ADMINISTRADOR -> {
                        Administrador admin = (Administrador) funcionario;
                        admin.setSetor(tfSetor.getText());
                        AdministradorDAO.getInstance().persist(admin);
                    }
                    case RECEPCIONISTA -> {
                        Recepcionista recepcionista = (Recepcionista) funcionario;
                        recepcionista.setSetor(tfSetor.getText());
                        RecepcionistaDAO.getInstance().persist(recepcionista);
                    }
                    default -> {
                        throw new IllegalArgumentException("Tipo de funcionário inválido para esta tela.");
                    }
                }

                // Criar usuário do sistema
                criarUsuarioSistema();

                mostrarAlerta("Sucesso", "Funcionário cadastrado com sucesso!");
                voltarPane(actionEvent);

            } catch (Exception e) {
                mostrarAlerta("Erro", "Erro ao cadastrar funcionário: " + e.getMessage());
                e.printStackTrace();
            }
        }
        trocarPane(CaminhoFXML.PANE_OPCOES_CRUD);
    }

    public void voltarPane(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_CADASTRO_FUNCIONARIO);
    }

    private String gerarMatricula() {
        // Lógica para gerar matrícula única
        return String.valueOf(System.currentTimeMillis() % 1000000);
    }

    private void criarUsuarioSistema() {
        user.setLogin(user.getFuncionario().getPessoa().getCpf()); // CPF como login
        user.setSenha(user.getFuncionario().getPessoa().getCpf()); // Senha padrão
        user.setAtivo(true);
        UsuarioSistemaDAO.getInstance().persist(user);
    }


    private boolean validarCampos() {
        if (dtDataAdimissao.getValue() == null || tfSetor.getText().isEmpty()) {
            mostrarAlerta("Campos obrigatórios", "Preencha data de admissão e setor.");
            return false;
        }
        return true;
    }

    @Override
    public void setDados(UsuarioSistema dados) {
        this.user = dados;
        // Gerar matrícula automática
        tfMatricula.setText(gerarMatricula());
        if(!user.getTipoUser().equals(UsuarioSistema.TipoUser.FARMACEUTICO)){
            lblCrf.setVisible(false);
            tfCrf.setVisible(false);
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

}