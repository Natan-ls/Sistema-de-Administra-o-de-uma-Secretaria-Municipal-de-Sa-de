package com.example.sistema_de_saude.controller.adiministracao;

import com.example.sistema_de_saude.entity.Funcionario;
import com.example.sistema_de_saude.entity.UsuarioSistema;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import com.example.sistema_de_saude.util.ReceberDados;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class AdministradorController extends NavegadorPane implements ReceberDados<UsuarioSistema> {

    @FXML
    public Label tfUser;
    @FXML
    public Pane painelPrincilpal;

    @FXML
    public void initialize() throws IOException {
        this.setPainel(painelPrincilpal);
        // Carrega um painel inicial padrão (opcional)
        Pane inicial = FXMLLoader.load(getClass().getResource(CaminhoFXML.PANE_CADASTRO_FUNCIONARIO));
        painelPrincilpal.getChildren().setAll(inicial);

        // Atualiza o label do usuário se estiver disponível
        atualizarLabelUsuario();
    }

    private void atualizarLabelUsuario() {
        if (usuario != null && tfUser != null) {
            tfUser.setText("Usuário: " + usuario.getFuncionario().getPessoa().getNome());
        }
    }

    public void areaPacientes(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_OPCOES_CRUD);
    }

    public void areaAgendamento(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_AGENDAMENTO);
    }

    public void areaEstoque(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_ESTOQUE);
    }

    public void areaLoteMedicamento(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_LOTE_MEDICAMENTO);
    }

    public void areaCadastroFuncionario(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_CADASTRO_FUNCIONARIO);
    }

    public void areaFuncionario(ActionEvent actionEvent) {
        // Se tiver um painel específico para gerenciar funcionários
        trocarPane(CaminhoFXML.PANE_CADASTRO_FUNCIONARIO);
    }

    @Override
    public void setDados(UsuarioSistema dados) {
        super.setUsuario(usuario);
        atualizarLabelUsuario();
    }
}