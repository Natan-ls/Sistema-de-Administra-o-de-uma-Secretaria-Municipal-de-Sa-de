package com.example.sistema_de_saude;

import com.example.sistema_de_saude.controller.NavegarTela;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sistema_de_saude/views/ViewLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1152, 648);
        stage.setTitle("Sistema S.A.S.M.S");
        stage.setScene(scene);
        stage.show();
        //NavegarTela.setJanela(stage);
        //NavegarTela.navegarPara("/com/example/sistema_de_saude/views/ViewLogin.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}