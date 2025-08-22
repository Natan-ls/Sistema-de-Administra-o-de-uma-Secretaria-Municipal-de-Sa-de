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
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.time.ZoneId;
import java.util.Date;

public class AlterarPacienteController extends NavegadorPane {

    @FXML
    public AnchorPane painelPrincipal;

    @FXML
    public TextField tfCpf, tfNome, tfEmail, tfEndereco, tfSus;
    public TextField tfTelefone;

    @FXML
    public void initialize() {
        this.setPainel(painelPrincipal);
    }

    private Paciente pacienteAtual; // guarda o paciente carregado para alteração

    @FXML
    public void buscarPaciente(ActionEvent actionEvent) {
        String cpf = tfCpf.getText().trim();

        if (cpf.isEmpty()) {
            showAlert("Busca inválida", "Digite um CPF para buscar.", Alert.AlertType.WARNING);
            return;
        }

        Pessoa pessoa = PessoaDAO.getInstance().findByCpf(cpf);

        if (pessoa == null || pessoa.getPaciente() == null) {
            showAlert("Paciente não encontrado", "Nenhum paciente encontrado com CPF " + cpf, Alert.AlertType.INFORMATION);
            return;
        }

        pacienteAtual = pessoa.getPaciente();

        // Preenche os campos
        tfNome.setText(pacienteAtual.getPessoa().getNome());
        tfEmail.setText(pacienteAtual.getPessoa().getEmail());
        tfEndereco.setText(pacienteAtual.getPessoa().getEndereco());
        tfSus.setText(pacienteAtual.getNumeroSus());
    }

    @FXML
    public void alterarPaciente(ActionEvent actionEvent) {
        if (pacienteAtual == null) {
            showAlert("Erro", "Busque um paciente antes de alterar.", Alert.AlertType.WARNING);
            return;
        }

        // Verifica alterações
        boolean alterou = false;

        Pessoa pessoa = pacienteAtual.getPessoa();

        if (!tfNome.getText().trim().equals(pessoa.getNome())) {
            pessoa.setNome(tfNome.getText().trim());
            alterou = true;
        }
        if (!tfEmail.getText().trim().equals(pessoa.getEmail())) {
            pessoa.setEmail(tfEmail.getText().trim());
            alterou = true;
        }
        if (!tfEndereco.getText().trim().equals(pessoa.getEndereco())) {
            pessoa.setEndereco(tfEndereco.getText().trim());
            alterou = true;
        }
        if (!tfSus.getText().trim().equals(pacienteAtual.getNumeroSus())) {
            pacienteAtual.setNumeroSus(tfSus.getText().trim());
            alterou = true;
        }

        if (!alterou) {
            showAlert("Nada alterado", "Nenhum campo foi modificado.", Alert.AlertType.INFORMATION);
            return;
        }

        // Atualiza no banco
        PacienteDAO.getInstance().merge(pacienteAtual);

        showAlert("Paciente atualizado", "Os dados do paciente foram atualizados com sucesso.", Alert.AlertType.INFORMATION);
        limparCampos();
    }

    @FXML
    public void voltarPane(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_OPCOES_CRUD);
    }

    private void limparCampos() {
        tfCpf.clear();
        tfNome.clear();
        tfEmail.clear();
        tfEndereco.clear();
        tfSus.clear();
        pacienteAtual = null;
    }

    private void showAlert(String title, String content, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void pesquisar(ActionEvent actionEvent) {
        buscarPaciente(actionEvent);
    }
}
