package com.example.sistema_de_saude.controller.consulta;

import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PaneConsultasController extends NavegadorPane {
    public TableView tableConsulta;
    public TableColumn colPaciente;
    public TableColumn colMedico;
    public TableColumn colTipo;
    public TableColumn colData;

    public void iniciarConsulta(ActionEvent actionEvent) {
    }
}
