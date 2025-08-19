package com.example.sistema_de_saude.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

public class NavegarTela {
    private static Stage janela;
    private static Stack<Scene> historico = new Stack<>();

    public static void setJanela(Stage stage) {
        janela = stage;
    }

    public NavegarTela(){};

    public static void navegarPara(String caminho) throws IOException {
        try {
            if (janela != null && janela.getScene() != null) {
                historico.push(janela.getScene());
            }
            Parent tela = FXMLLoader.load(Objects.requireNonNull(NavegarTela.class.getResource(caminho)));
            janela.setScene(new Scene(tela));
            janela.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela: " + caminho);
            e.printStackTrace();
            throw e;
        }

    }

    public static void voltar(){
        if (!historico.isEmpty() && janela != null) {
            janela.setScene(historico.pop());
        }
    }

}
