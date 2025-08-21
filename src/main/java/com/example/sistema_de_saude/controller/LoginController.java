package com.example.sistema_de_saude.controller;

import com.example.sistema_de_saude.dataAccess.UsuarioSistemaDAO;
import com.example.sistema_de_saude.entity.UsuarioSistema;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    public TextField tfUser;
    @FXML
    public PasswordField tfSenha;

    private UsuarioSistema user;

    public void acessarTela(ActionEvent actionEvent) {
        try {
            user = UsuarioSistemaDAO.getInstance().validarUser("agh", "hgj");
            switch (user.getTipoUser()){
                case MEDICO -> NavegadorTela.navegarPara(CaminhoFXML.VIEW_CONSULTA, user);
                case FARMACEUTICO -> NavegadorTela.navegarPara(CaminhoFXML.VIEW_FARMACIA, user);
                case RECEPCIONISTA -> NavegadorTela.navegarPara(CaminhoFXML.VIEW_RECEPCAO, user);
                //case ADMINISTRADOR -> NavegadorTela.navegarPara(CaminhoFXML.VIEW_ADIMINISTRADOR);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setContentText("Usuário ou senha inválido");
            alert.show();
        }

    }
}
