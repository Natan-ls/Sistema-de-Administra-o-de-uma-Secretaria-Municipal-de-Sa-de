package com.example.sistema_de_saude;

import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorTela;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        NavegadorTela.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CaminhoFXML.VIEW_TESTE));
        Scene scene = new Scene(fxmlLoader.load(), 1152, 648);
        stage.setTitle("Sistema S.A.S.M.S");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}