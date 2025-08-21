package com.example.sistema_de_saude.controller;

import com.example.sistema_de_saude.entity.UsuarioSistema;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import com.example.sistema_de_saude.util.NavegadorTela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TesteController extends NavegadorPane {

    @FXML
    public void initialize() {
        this.usuario = new UsuarioSistema();
        usuario.setTipoUser(UsuarioSistema.TipoUser.ADMINISTRADOR); // ou um default
    }

    public void moduloFarmacia(ActionEvent actionEvent) {
        usuario.setTipoUser(UsuarioSistema.TipoUser.FARMACEUTICO);
        NavegadorTela.navegarPara(CaminhoFXML.VIEW_FARMACIA, usuario);
    }

    public void moduloAdmin(ActionEvent actionEvent) {
        usuario.setTipoUser(UsuarioSistema.TipoUser.ADMINISTRADOR);
        NavegadorTela.navegarPara(CaminhoFXML.VIEW_ADIMINISTRADOR, usuario);
    }

    public void moduloMedico(ActionEvent actionEvent) {
        usuario.setTipoUser(UsuarioSistema.TipoUser.MEDICO);
        NavegadorTela.navegarPara(CaminhoFXML.VIEW_CONSULTA, usuario);
    }

    public void moduloRecepcao(ActionEvent actionEvent) {
        usuario.setTipoUser(UsuarioSistema.TipoUser.RECEPCIONISTA);
        NavegadorTela.navegarPara(CaminhoFXML.VIEW_RECEPCAO, usuario);
    }
}
