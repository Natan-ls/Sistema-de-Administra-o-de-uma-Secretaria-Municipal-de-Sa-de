package com.example.sistema_de_saude.controller;

import com.example.sistema_de_saude.dataAccess.PessoaDAO;
import com.example.sistema_de_saude.dataAccess.UsuarioSistemaDAO;
import com.example.sistema_de_saude.entity.Pessoa;
import com.example.sistema_de_saude.entity.UsuarioSistema;
import com.example.sistema_de_saude.util.HashSenha;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AlterarSenhaController {

    @FXML private TextField tfCPF;
    @FXML private PasswordField pfNovaSenha;
    @FXML private PasswordField pfConfirmarSenha;

    private UsuarioSistema usuario;

    @FXML
    public void verificarCPF() {
        String cpf = tfCPF.getText().trim();
        if(cpf.isEmpty()){
            showAlert("Digite o CPF!");
            return;
        }

        Pessoa pessoa = PessoaDAO.getInstance().findByCpf(cpf);

        if(pessoa == null){
            showAlert("CPF não encontrado!");
        } else {
            usuario = pessoa.getFuncionario().getUser();
            pfNovaSenha.setDisable(false);
            pfConfirmarSenha.setDisable(false);
        }
    }

    @FXML
    public void atualizarSenha() {
        if (usuario == null) {
            showAlert("Primeiro verifique o CPF!");
            return;
        }

        String novaSenha = pfNovaSenha.getText().trim();
        String confirmar = pfConfirmarSenha.getText().trim();

        if (novaSenha.isEmpty() || confirmar.isEmpty()) {
            showAlert("Preencha todos os campos!");
            return;
        }

        if (!novaSenha.equals(confirmar)) {
            showAlert("Senhas não coincidem!");
            return;
        }

        try {
            usuario.setSenha(HashSenha.gerarHash(novaSenha));
            UsuarioSistemaDAO.getInstance().merge(usuario);
            showAlert("Senha atualizada com sucesso!");

            Stage stage = (Stage) tfCPF.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            showAlert("Erro ao atualizar senha: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void showAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
