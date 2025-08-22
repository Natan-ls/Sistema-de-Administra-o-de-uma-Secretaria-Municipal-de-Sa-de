package com.example.sistema_de_saude.controller.consulta;

import com.example.sistema_de_saude.entity.Consulta;
import com.example.sistema_de_saude.util.NavegadorPane;
import com.example.sistema_de_saude.util.ReceberDados;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PaneAdicionarController extends NavegadorPane implements ReceberDados<Consulta> {

    public TextField tfRemedio;
    public Label lbNomePaciente;
    public TextField tfDescricao;
    private Consulta consulta;

    @Override
    public void setDados(Consulta paciente) {
        this.consulta = paciente;
        carregarDadosConsulta();
    }

    private void carregarDadosConsulta() {
        // Exemplo: atualizar labels ou campos com dados do paciente
        System.out.println("Consulta selecionado: " + consulta.getPaciente().getPessoa().getNome());
    }

    public void addRemedio(ActionEvent actionEvent) {
    }

    public void finalizarConsulta(ActionEvent actionEvent) {
    }

    public void gerarProtocolo(ActionEvent actionEvent) {
    }
}
