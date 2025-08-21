package com.example.sistema_de_saude.util;

import com.example.sistema_de_saude.entity.UsuarioSistema;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public abstract class NavegadorPane {

    protected Pane painel;                 // container onde os panes serão trocados
    protected UsuarioSistema usuario;      // usuário logado disponível para panes

    /** Deve ser chamado no initialize() do controller concreto */
    protected void setPainel(Pane painel) {
        this.painel = painel;
    }

    /** Chamado pelo NavegadorTela logo após o load() da tela principal */
    public void setUsuario(UsuarioSistema usuario) {
        this.usuario = usuario;
        // Se a tela principal quiser, pode reagir aqui (ex.: atualizar labels)
        // ou no initialize() verificar se usuario != null.
    }

    /** Troca o conteúdo do painel por um FXML de pane */
    protected void trocarPane(String caminhoFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
            Parent conteudo = loader.load();

            // Propaga o usuário para o controller do novo pane, se ele também herdar NavegadorPane
            Object controller = loader.getController();
            if (controller instanceof NavegadorPane np) {
                np.setUsuario(this.usuario);
                // Se esse pane também gerenciar sub-panes, ele chamará setPainel(...)
                // no initialize dele; não fazemos aqui.
            }

            painel.getChildren().setAll(conteudo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
