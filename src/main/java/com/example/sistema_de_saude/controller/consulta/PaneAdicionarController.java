package com.example.sistema_de_saude.controller.consulta;

import com.example.sistema_de_saude.dataAccess.ConsultaDAO;
import com.example.sistema_de_saude.dataAccess.MedicamentoDAO;
import com.example.sistema_de_saude.dataAccess.ProtocoloDAO;
import com.example.sistema_de_saude.entity.Consulta;
import com.example.sistema_de_saude.entity.Medicamento;
import com.example.sistema_de_saude.entity.Protocolo;
import com.example.sistema_de_saude.entity.Paciente;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import com.example.sistema_de_saude.util.ReceberDados;
import jakarta.persistence.EntityManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaneAdicionarController extends NavegadorPane implements ReceberDados<Consulta> {

    @FXML public TextField tfRemedio;
    @FXML public Label lbNomePaciente;
    @FXML public TextField tfDescricao;
    @FXML public AnchorPane pane;
    @FXML public VBox vboxRemedios;

    private Consulta consulta;
    private Protocolo protocolo;

    @FXML
    public void initialize(){
        this.setPainel(pane);
    }

    @Override
    public void setDados(Consulta dados) {
        this.consulta = dados;
        carregarDadosConsulta();
    }

    private void carregarDadosConsulta() {
        lbNomePaciente.setText(consulta.getPaciente().getPessoa().getNome());
    }

    /**
     * Adiciona um novo TextField de remédio ao VBox, com botão para remover
     */
    public void addRemedio(ActionEvent actionEvent) {
        HBox hbox = new HBox(5); // espaçamento entre TextField e botão
        TextField novoRemedio = new TextField();
        novoRemedio.setPromptText("Pesquisar remédio");
        novoRemedio.setPrefWidth(tfRemedio.getPrefWidth());

        javafx.scene.control.Button btnRemover = new javafx.scene.control.Button("X");
        btnRemover.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        btnRemover.setOnAction(e -> vboxRemedios.getChildren().remove(hbox));

        hbox.getChildren().addAll(novoRemedio, btnRemover);
        vboxRemedios.getChildren().add(hbox);
    }

    /**
     * Gera o protocolo e associa à consulta atual
     */
    public void gerarProtocolo(ActionEvent actionEvent) {
        if (vboxRemedios.getChildren().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText("Nenhum remédio adicionado");
            alert.setContentText("Adicione pelo menos um remédio para gerar o protocolo.");
            alert.showAndWait();
            return;
        }

        Paciente paciente = consulta.getPaciente();
        protocolo = consulta.gerarProtocolo(paciente, consulta);

        ProtocoloDAO.getInstance().persist(protocolo);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Protocolo Gerado");
        alert.setHeaderText(null);
        alert.setContentText("Protocolo gerado com sucesso! Código: " + protocolo.getCodigo());
        alert.showAndWait();
    }

    /**
     * Finaliza a consulta, associando protocolo e remédios, salvando no banco
     */
    public void finalizarConsulta(ActionEvent actionEvent) {
        // Se protocolo foi gerado, garante que existe pelo menos um remédio
        if (protocolo != null && vboxRemedios.getChildren().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText("Protocolo incompleto");
            alert.setContentText("Adicione pelo menos um remédio antes de finalizar a consulta.");
            alert.showAndWait();
            return;
        }

        // Atualiza consulta com descrição
        String descricao = tfDescricao.getText().trim();
        if (!descricao.isEmpty()) {
            consulta.setDescricao(descricao);
        }

        // Adiciona remédios ao protocolo
        if (protocolo != null) {
            if (protocolo.getId() == null) {
                ProtocoloDAO.getInstance().persist(protocolo);
            }

            EntityManager em = ProtocoloDAO.getInstance().getEntityManager();

            List<Medicamento> medicamentos = new ArrayList<>();
            for (var node : vboxRemedios.getChildren()) {
                if (node instanceof HBox hbox && !hbox.getChildren().isEmpty()) {
                    if (hbox.getChildren().get(0) instanceof TextField tf) {
                        String nome = tf.getText().trim();
                        if (!nome.isEmpty()) {
                            Medicamento med = MedicamentoDAO.getInstance().getByNomeMedicamento(nome);
                            if (med != null) {
                                medicamentos.add(em.merge(med)); // garante que está gerenciado
                            }
                        }
                    }
                }
            }

            protocolo.setMedicamentos(medicamentos);
            protocolo.setConsulta(consulta);
            protocolo.setStatus(true);
            ProtocoloDAO.getInstance().merge(protocolo);

            consulta.setProtocolo(protocolo);
            ConsultaDAO.getInstance().merge(consulta);
        }


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Consulta Finalizada");
        alert.setHeaderText(null);
        alert.setContentText("Consulta finalizada com sucesso!");
        alert.showAndWait();

        // Volta para a tela de consultas
        trocarPane(CaminhoFXML.PANE_CONSULTAS);
    }

    public void voltar(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_CONSULTAS);
    }
}
