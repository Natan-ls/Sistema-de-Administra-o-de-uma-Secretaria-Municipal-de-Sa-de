package com.example.sistema_de_saude.controller.adiministracao;

import com.example.sistema_de_saude.dataAccess.PessoaDAO;
import com.example.sistema_de_saude.entity.*;
import com.example.sistema_de_saude.dataAccess.MedicoDAO;
import com.example.sistema_de_saude.dataAccess.PacienteDAO;
import com.example.sistema_de_saude.dataAccess.UsuarioSistemaDAO;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.HashSenha;
import com.example.sistema_de_saude.util.NavegadorPane;
import com.example.sistema_de_saude.util.ReceberDados;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.sql.Date;

public class CadastroFinalMedicoControlle extends NavegadorPane implements ReceberDados<UsuarioSistema> {

    @FXML public Pane painelPrincipal;
    @FXML public TextField tfMatricula;
    @FXML public DatePicker dtDataAdimissao;
    @FXML public TextField tfSetor;
    @FXML public TextField tfCrm;
    @FXML public TextField tfEspecialidade;
    @FXML public TextField tfNomeFantasia;

    private UsuarioSistema user; // Receber dados do usuário (como já foi feito em outros controllers)

    @FXML
    public void initialize() {
        // Configurar ChoiceBox de sexo
        this.setPainel(painelPrincipal);

    }

    private String gerarMatricula() {
        // Lógica para gerar matrícula única
        return String.valueOf(System.currentTimeMillis() % 1000000);
    }

    public void salvar(ActionEvent actionEvent) {
        if (!validarCampos()) return;

        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            em = Persistence.createEntityManagerFactory("sistemasaudePU").createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();

            // 1. Criar apenas a Pessoa (SEM PACIENTE)
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(user.getFuncionario().getPessoa().getNome());
            pessoa.setCpf(user.getFuncionario().getPessoa().getCpf());
            pessoa.setEmail(user.getFuncionario().getPessoa().getEmail());
            pessoa.setDataNascimento(user.getFuncionario().getPessoa().getDataNascimento());
            pessoa.setSexo(user.getFuncionario().getPessoa().getSexo());
            pessoa.setEndereco(user.getFuncionario().getPessoa().getEndereco());
            pessoa.setTelefone(user.getFuncionario().getPessoa().getTelefone());
            em.persist(pessoa);

            // 2. Criar APENAS o Médico (sem paciente)
            Medico medico = new Medico();
            medico.setPessoa(pessoa);
            medico.setMatricula(Integer.parseInt(tfMatricula.getText()));
            medico.setDataAdimissao(java.sql.Date.valueOf(dtDataAdimissao.getValue()));
            medico.setCrm(tfCrm.getText());
            medico.setEspecialidade(tfEspecialidade.getText());
            medico.setNomeFantasia(tfNomeFantasia.getText());
            em.persist(medico);

            // 3. Criar UsuarioSistema
            UsuarioSistema usuario = new UsuarioSistema();
            usuario.setFuncionario(medico);
            usuario.setLogin(gerarLogin(medico));
            usuario.setSenha(gerarSenhaPadrao());
            usuario.setTipoUser(UsuarioSistema.TipoUser.MEDICO);
            usuario.setAtivo(true);
            em.persist(usuario);

            transaction.commit();
            mostrarAlerta("Sucesso", "Médico cadastrado com sucesso!");
            voltarPane(actionEvent);

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            mostrarAlerta("Erro", "Erro ao cadastrar médico: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    private String gerarLogin(Funcionario funcionario) {
        String nome = funcionario.getPessoa().getNome().toLowerCase();
        return nome.replace(" ", ".") + "@medico";
    }

    private String gerarSenhaPadrao() {
        try {
            String nome = user.getFuncionario().getPessoa().getNome();
            String cpf = user.getFuncionario().getPessoa().getCpf();

            //  Pegar o primeiro nome (até o primeiro espaço)
            String primeiroNome = nome.split(" ")[0].toLowerCase();

            //  Pegar os 3 primeiros dígitos do CPF (apenas números)
            String digitosCpf = cpf.replaceAll("[^0-9]", "").substring(0, 3);

            //  Combinar: primeiroNome + 3 dígitos do CPF
            return HashSenha.gerarHash(primeiroNome + digitosCpf);
        } catch (Exception e) {
            // Fallback em caso de erro
            System.err.println("Erro ao gerar senha: " + e.getMessage());
            return HashSenha.gerarHash(String.valueOf(System.currentTimeMillis() % 1000000));
        }
    }

    private boolean validarCampos() {
        if (dtDataAdimissao.getValue() == null ||
                tfCrm.getText().isEmpty() ||
                tfEspecialidade.getText().isEmpty() ||
                tfNomeFantasia.getText().isEmpty()) {

            mostrarAlerta("Campos obrigatórios", "Preencha todos os campos obrigatórios.");
            return false;
        }
        return true;
    }

    public void voltarPane(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_CADASTRO_FUNCIONARIO);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @Override
    public void setDados(UsuarioSistema dados) {
        this.user = dados;
        tfMatricula.setText(gerarMatricula());
    }
}
