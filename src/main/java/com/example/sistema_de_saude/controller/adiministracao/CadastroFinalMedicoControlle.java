package com.example.sistema_de_saude.controller.adiministracao;

import com.example.sistema_de_saude.entity.Medico;
import com.example.sistema_de_saude.entity.Paciente;
import com.example.sistema_de_saude.entity.UsuarioSistema;
import com.example.sistema_de_saude.dataAccess.MedicoDAO;
import com.example.sistema_de_saude.dataAccess.PacienteDAO;
import com.example.sistema_de_saude.dataAccess.UsuarioSistemaDAO;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import com.example.sistema_de_saude.util.ReceberDados;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.sql.Date;

public class CadastroFinalMedicoControlle extends NavegadorPane implements ReceberDados<UsuarioSistema> {

    @FXML public Pane painelPrincipal;
    @FXML public TextField tfMatricula;
    @FXML public DatePicker dtDataAdimissao;
    @FXML public TextField tfSetor;
    @FXML public TextField tfCrm;
    @FXML public TextField tfEspecialidade;
    @FXML public TextField tfNomeFantasia;

    private UsuarioSistema user; // Receber dados do usuário (como já foi feito em outros controllers)

    @FXML
    public void initialize() {
        // Configurar ChoiceBox de sexo
        this.setPainel(painelPrincipal);

    }

    private String gerarMatricula() {
        // Lógica para gerar matrícula única
        return String.valueOf(System.currentTimeMillis() % 1000000);
    }

    public void salvar(ActionEvent actionEvent) {
        if (!validarCampos()) return;

        try {
            // Criar objeto médico
            Medico medico = (Medico) user.getFuncionario();
            medico.setPessoa(user.getFuncionario().getPessoa());
            medico.setMatricula(Integer.parseInt(tfMatricula.getText()));
            medico.setDataAdimissao(Date.valueOf(dtDataAdimissao.getValue()));

            // Campos específicos do médico
            medico.setCrm(tfCrm.getText());
            medico.setEspecialidade(tfEspecialidade.getText());
            medico.setNomeFantasia(tfNomeFantasia.getText());

            // Persistir no banco
            MedicoDAO.getInstance().persist(medico);

            // Criar usuário do sistema
            criarUsuarioSistema();

            mostrarAlerta("Sucesso", "Médico cadastrado com sucesso!");
            voltarPane(actionEvent);

        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao cadastrar médico: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        if (dtDataAdimissao.getValue() == null ||
                tfCrm.getText().isEmpty() ||
                tfEspecialidade.getText().isEmpty() ||
                tfNomeFantasia.getText().isEmpty()) {

            mostrarAlerta("Campos obrigatórios", "Preencha todos os campos obrigatórios.");
            return false;
        }
        return true;
    }

    public void voltarPane(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_CADASTRO_FUNCIONARIO);
    }

    private void criarUsuarioSistema() {
        user.setLogin(user.getFuncionario().getPessoa().getCpf());
        user.setSenha(user.getFuncionario().getPessoa().getCpf());
        user.setAtivo(true);
        UsuarioSistemaDAO.getInstance().persist(user);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @Override
    public void setDados(UsuarioSistema dados) {
        this.user = dados;
        tfMatricula.setText(gerarMatricula());
    }
}
