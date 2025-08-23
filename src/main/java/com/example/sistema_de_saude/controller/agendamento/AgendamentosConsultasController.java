package com.example.sistema_de_saude.controller.agendamento;

import com.example.sistema_de_saude.dataAccess.ConsultaDAO;
import com.example.sistema_de_saude.dataAccess.MedicoDAO;
import com.example.sistema_de_saude.dataAccess.PacienteDAO;
import com.example.sistema_de_saude.dataAccess.PessoaDAO;
import com.example.sistema_de_saude.entity.Consulta;
import com.example.sistema_de_saude.entity.Medico;
import com.example.sistema_de_saude.entity.Paciente;
import com.example.sistema_de_saude.entity.Pessoa;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AgendamentosConsultasController extends NavegadorPane {

    // === IDs do FXML ===
    @FXML private AnchorPane painelPrincipal;

    @FXML private TextField tfCpfPaciente;
    @FXML private TextField tfNomePaciente;

    @FXML private ComboBox<Medico> cbMedicos;
    @FXML private TextField tfTipoConsulta;
    @FXML private DatePicker dpDataConsulta;

    // === Estado ===
    private Consulta consultaAtual;
    private Paciente pacienteAtual;
    private ObservableList<Medico> listaMedicos;

    @FXML
    public void initialize() {
        // Registra o painel para navegação
        if (painelPrincipal != null) {
            this.setPainel(painelPrincipal);
        }

        // Carrega médicos do banco
        List<Medico> medicos = MedicoDAO.getInstance().findAll();
        listaMedicos = FXCollections.observableArrayList(medicos);
        cbMedicos.setItems(listaMedicos);

        // Exibir nomeFantasia no ComboBox
        cbMedicos.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Medico item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null :
                        (item.getNomeFantasia() != null ? item.getNomeFantasia() : "CRM " + item.getCrm()));
            }
        });
        cbMedicos.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Medico item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null :
                        (item.getNomeFantasia() != null ? item.getNomeFantasia() : "CRM " + item.getCrm()));
            }
        });

        // Bloquear datas passadas
        dpDataConsulta.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                boolean invalida = empty || date.isBefore(LocalDate.now());
                setDisable(invalida);
                if (invalida) setStyle("-fx-background-color: #ffd6d6;");
            }
        });

        // Buscar paciente ao perder foco do campo
        tfCpfPaciente.focusedProperty().addListener((obs, oldV, newV) -> {
            if (!newV) { // perdeu o foco
                pesquisarPaciente();
            }
        });
    }

    // === Recebe a consulta de outra tela ===
    public void carregarConsulta(Consulta consulta) {
        this.consultaAtual = consulta;

        if (consulta == null) {
            limparCampos();
            return;
        }

        // Paciente
        if (consulta.getPaciente() != null) {
            this.pacienteAtual = consulta.getPaciente();
            tfCpfPaciente.setText(pacienteAtual.getNumeroSus());
            tfNomePaciente.setText(pacienteAtual.getPessoa().getNome());
        } else {
            tfCpfPaciente.clear();
            tfNomePaciente.clear();
            pacienteAtual = null;
        }

        // Médico
        if (consulta.getMedico() != null) {
            Medico medico = consulta.getMedico();
            // Seleciona o mesmo objeto da lista (por id), para evitar problemas de equals/hashCode
            Medico naLista = listaMedicos.stream()
                    .filter(m -> Objects.equals(m.getId(), medico.getId()))
                    .findFirst()
                    .orElse(null);
            if (naLista != null) {
                cbMedicos.getSelectionModel().select(naLista);
            } else {
                // se não estava na lista (edge case), adiciona e seleciona
                listaMedicos.add(medico);
                cbMedicos.getSelectionModel().select(medico);
            }
        } else {
            cbMedicos.getSelectionModel().clearSelection();
        }

        // Tipo
        tfTipoConsulta.setText(consulta.getTipoConsulta() != null ? consulta.getTipoConsulta() : "");

        // Data deve ficar em branco para o usuário escolher
        dpDataConsulta.setValue(null);
    }

    // Alias se preferir chamar de setConsulta em outra classe
    public void setConsulta(Consulta consulta) {
        carregarConsulta(consulta);
    }

    @FXML
    public void pesquisarPaciente() {
        String valor = tfCpfPaciente.getText();
        if (valor == null || valor.trim().isEmpty()) {
            return;
        }

        valor = valor.trim();

        // Remove caracteres não numéricos para análise
        String valorNumerico = valor.replaceAll("[^0-9]", "");

        Paciente pacienteEncontrado = null;

        // Verifica se é um CPF (11 dígitos)
        if (valorNumerico.length() == 11) {
            // Formata o CPF com pontos e traço
            String cpfFormatado = formatarCPF(valorNumerico);
            tfCpfPaciente.setText(cpfFormatado); // Atualiza o campo com CPF formatado

            // Pesquisa por CPF usando PessoaDAO
            Pessoa pessoa = PessoaDAO.getInstance().findByCpf(cpfFormatado);
            if (pessoa != null && pessoa.getPaciente() != null) {
                pacienteEncontrado = pessoa.getPaciente();
            }
        }
        // Verifica se é um número do SUS (15 dígitos)
        else if (valorNumerico.length() == 15) {
            // Pesquisa por número do SUS usando PacienteDAO
            pacienteEncontrado = PacienteDAO.getInstance().findBySus(valorNumerico);
        }

        if (pacienteEncontrado == null) {
            // Abre tela de cadastro
            try {
                trocarPane(CaminhoFXML.PANE_PACIENTE);
            } catch (Exception e) {
                System.err.println("Falha ao abrir cadastro de paciente: " + e.getMessage());
                mostrarAlerta(Alert.AlertType.WARNING,
                        "Paciente não encontrado",
                        "Nenhum paciente encontrado com o documento informado. Abra a tela de cadastro.");
            }
            return;
        }

        this.pacienteAtual = pacienteEncontrado;
        tfNomePaciente.setText(pacienteEncontrado.getPessoa().getNome());

        // Opcional: mostrar mensagem de sucesso
        mostrarAlerta(Alert.AlertType.INFORMATION,
                "Paciente encontrado",
                "Paciente " + pacienteEncontrado.getPessoa().getNome() + " localizado com sucesso!");
    }

    private String formatarCPF(String cpfNumerico) {
        if (cpfNumerico == null || cpfNumerico.length() != 11) {
            return cpfNumerico;
        }

        return cpfNumerico.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    @FXML
    public void salvarConsulta() {
        // Valida paciente
        if (pacienteAtual == null) {
            new Alert(Alert.AlertType.ERROR, "Paciente não selecionado/encontrado.").show();
            return;
        }

        // Valida médico
        Medico medico = cbMedicos.getSelectionModel().getSelectedItem();
        if (medico == null) {
            new Alert(Alert.AlertType.ERROR, "Selecione um médico.").show();
            return;
        }

        // Valida data
        LocalDate ld = dpDataConsulta.getValue();
        if (ld == null || ld.isBefore(LocalDate.now())) {
            new Alert(Alert.AlertType.ERROR, "Selecione uma data válida (não no passado).").show();
            return;
        }
        Date data = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (consultaAtual == null) {
            consultaAtual = new Consulta();
        }

        // Preenche e PERSISTE
        consultaAtual.setPaciente(pacienteAtual);
        consultaAtual.setMedico(medico);
        consultaAtual.setTipoConsulta(tfTipoConsulta.getText());
        consultaAtual.setDataConsulta(data);

        if (consultaAtual.getId() == null) {
            ConsultaDAO.getInstance().persist(consultaAtual);   // <<<<< PERSIST
        } else {
            ConsultaDAO.getInstance().merge(consultaAtual);     // <<<<< MERGE (UPDATE)
        }

        new Alert(Alert.AlertType.INFORMATION, "Consulta salva com sucesso!").show();
        trocarPane(CaminhoFXML.PANE_TABELA_CONSULTAS);
    }

    // === Util ===
    private void limparCampos() {
        tfCpfPaciente.clear();
        tfNomePaciente.clear();
        tfTipoConsulta.clear();
        cbMedicos.getSelectionModel().clearSelection();
        dpDataConsulta.setValue(null);
        pacienteAtual = null;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public void voltarPane(javafx.event.ActionEvent actionEvent) {
        try {
            // Volta para a tela anterior (tabela de consultas)
            trocarPane(CaminhoFXML.PANE_TABELA_CONSULTAS);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.WARNING,"Error","Erro ao voltar: " + e.getMessage());
        }
    }
}
