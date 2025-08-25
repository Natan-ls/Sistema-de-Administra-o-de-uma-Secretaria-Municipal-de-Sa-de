package com.example.sistema_de_saude.controller;

import com.example.sistema_de_saude.dataAccess.UsuarioSistemaDAO;
import com.example.sistema_de_saude.entity.UsuarioSistema;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.HashSenha;
import com.example.sistema_de_saude.util.NavegadorTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField tfUser;
    @FXML private PasswordField tfSenha;

    private UsuarioSistema user;

    @FXML
    public void acessarTela(ActionEvent actionEvent) {
        String login = tfUser.getText().trim();
        String senha = tfSenha.getText().trim();

        if(login.isEmpty() || senha.isEmpty()){
            showAlert("Preencha login e senha!");
            return;
        }

        try {
            user = UsuarioSistemaDAO.getInstance().validarUser(login);

            if (user == null) {
                showAlert("Usuário ou senha inválidos!");
                return;
            }

            // Verifica se a senha é hash ou texto puro
            String senhaBanco = user.getSenha();
            boolean senhaValida;

            if (senhaBanco.startsWith("$2a$") || senhaBanco.startsWith("$2b$")) {
                // senha em hash
                senhaValida = HashSenha.verificarSenha(senha, senhaBanco);
            } else {
                // senha antiga em texto puro
                senhaValida = senhaBanco.equals(senha);
            }

            if (!senhaValida) {
                showAlert("Usuário ou senha inválidos!");
                return;
            }

            // Redireciona conforme tipo de usuário
            switch (user.getTipoUser()){
                case MEDICO -> NavegadorTela.navegarPara(CaminhoFXML.VIEW_CONSULTA, user);
                case FARMACEUTICO -> NavegadorTela.navegarPara(CaminhoFXML.VIEW_FARMACIA, user);
                case RECEPCIONISTA -> NavegadorTela.navegarPara(CaminhoFXML.VIEW_RECEPCAO, user);
                case ADMINISTRADOR -> NavegadorTela.navegarPara(CaminhoFXML.VIEW_ADIMINISTRADOR, user);
            }


        } catch (Exception e) {
            showAlert("Erro ao validar usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirAlterarSenha() {
        // Navega para a tela de alteração de senha
        NavegadorTela.navegarPara(CaminhoFXML.VIEW_ALTERAR_SENHA, user);
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
