package com.example.sistema_de_saude.util;

import com.example.sistema_de_saude.Main;
import com.example.sistema_de_saude.entity.UsuarioSistema;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavegadorTela {

    private static Stage stage;

    public static void setStage(Stage s) {
        stage = s;
    }

    public static void navegarPara(String caminhoFXML, UsuarioSistema usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(NavegadorTela.class.getResource(caminhoFXML));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof NavegadorPane np) {
                np.setUsuario(usuario);
            }

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}