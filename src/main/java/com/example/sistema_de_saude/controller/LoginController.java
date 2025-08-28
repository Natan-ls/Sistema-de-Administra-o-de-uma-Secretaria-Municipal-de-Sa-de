package com.example.sistema_de_saude.controller;

import com.example.sistema_de_saude.dataAccess.UsuarioSistemaDAO;
import com.example.sistema_de_saude.entity.UsuarioSistema;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.HashSenha;
import com.example.sistema_de_saude.util.NavegadorTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

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

            String senhaBanco = user.getSenha();
            boolean senhaValida;

            if (senhaBanco.startsWith("$2a$") || senhaBanco.startsWith("$2b$")) {

                senhaValida = HashSenha.verificarSenha(senha, senhaBanco);
            } else {

                senhaValida = senhaBanco.equals(senha);
            }

            if (!senhaValida) {
                showAlert("Usuário ou senha inválidos!");
                return;
            }
            showAlert("Bem vindo! "+user.getFuncionario().getPessoa().getNome());
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

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    public void abrirTelaEsqueciSenha(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(CaminhoFXML.VIEW_ALTERAR_SENHA));
            Stage novaJanela = new Stage();
            novaJanela.initModality(Modality.APPLICATION_MODAL); // Torna modal
            novaJanela.setTitle("Alterar Senha");
            novaJanela.setScene(new Scene(root));
            novaJanela.showAndWait(); // Espera a nova janela ser fechada para continuar
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
