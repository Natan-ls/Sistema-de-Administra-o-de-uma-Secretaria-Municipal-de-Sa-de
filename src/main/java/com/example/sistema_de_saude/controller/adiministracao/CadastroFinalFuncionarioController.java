package com.example.sistema_de_saude.controller.adiministracao;

import com.example.sistema_de_saude.entity.*;
import com.example.sistema_de_saude.dataAccess.*;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class CadastroFinalFuncionarioController extends NavegadorPane implements ReceberDados<UsuarioSistema> {

    @FXML public TextField tfMatricula;
    @FXML public DatePicker dtDataAdimissao;
    @FXML public TextField tfSetor;
    @FXML public TextField tfCrf;
    @FXML public Pane painelPrincipal;
    @FXML public Label lblCrf;

    private UsuarioSistema user;

    @FXML
    public void initialize() {
        // Configurar ChoiceBox de sexo
        this.setPainel(painelPrincipal);

    }

    public void proximoPane(ActionEvent actionEvent) {
        if (!validarCampos()) return;

        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            // 1. Obter o EntityManager e iniciar transação
            em = Persistence.createEntityManagerFactory("sistemasaudePU").createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();

            // 2. Criar a Pessoa
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(user.getFuncionario().getPessoa().getNome());
            pessoa.setCpf(user.getFuncionario().getPessoa().getCpf());
            pessoa.setEmail(user.getFuncionario().getPessoa().getEmail());
            pessoa.setDataNascimento(user.getFuncionario().getPessoa().getDataNascimento());
            pessoa.setSexo(user.getFuncionario().getPessoa().getSexo());
            pessoa.setEndereco(user.getFuncionario().getPessoa().getEndereco());
            pessoa.setTelefone(user.getFuncionario().getPessoa().getTelefone());

            // 3. Persistir a Pessoa
            em.persist(pessoa);

            // 4. Criar e persistir o Paciente
            Paciente paciente = new Paciente();
            paciente.setNumeroSus(user.getFuncionario().getPessoa().getPaciente().getNumeroSus());
            paciente.setPessoa(pessoa);
            em.persist(paciente);

            // 5. Atualizar a pessoa com referência ao paciente
            pessoa.setPaciente(paciente);
            em.merge(pessoa);

            // 6. Criar e persistir o Funcionário específico
            Funcionario funcionario;
            UsuarioSistema.TipoUser tipoUser = user.getTipoUser();

            switch (tipoUser) {
                case FARMACEUTICO -> {
                    Farmaceutico farmaceutico = new Farmaceutico();
                    farmaceutico.setPessoa(pessoa);
                    farmaceutico.setMatricula(Integer.parseInt(tfMatricula.getText()));
                    farmaceutico.setDataAdimissao(java.sql.Date.valueOf(dtDataAdimissao.getValue()));
                    farmaceutico.setCrf(tfCrf.getText());
                    em.persist(farmaceutico);
                    funcionario = farmaceutico;
                }
                case ADMINISTRADOR -> {
                    Administrador admin = new Administrador();
                    admin.setPessoa(pessoa);
                    admin.setMatricula(Integer.parseInt(tfMatricula.getText()));
                    admin.setDataAdimissao(java.sql.Date.valueOf(dtDataAdimissao.getValue()));
                    admin.setSetor(tfSetor.getText());
                    em.persist(admin);
                    funcionario = admin;
                }
                case RECEPCIONISTA -> {
                    Recepcionista recepcionista = new Recepcionista();
                    recepcionista.setPessoa(pessoa);
                    recepcionista.setMatricula(Integer.parseInt(tfMatricula.getText()));
                    recepcionista.setDataAdimissao(java.sql.Date.valueOf(dtDataAdimissao.getValue()));
                    recepcionista.setSetor(tfSetor.getText());
                    em.persist(recepcionista);
                    funcionario = recepcionista;
                }
                default -> throw new IllegalArgumentException("Tipo de funcionário inválido.");
            }

            // 7. Criar e persistir o UsuarioSistema
            UsuarioSistema usuarioSistema = new UsuarioSistema();
            usuarioSistema.setFuncionario(funcionario);
            usuarioSistema.setLogin(pessoa.getCpf());
            usuarioSistema.setSenha(gerarSenhaPadrao());
            usuarioSistema.setTipoUser(tipoUser);
            usuarioSistema.setAtivo(true);
            em.persist(usuarioSistema);

            // 8. Commit da transação
            transaction.commit();

            mostrarAlerta("Sucesso", "Funcionário cadastrado com sucesso!");
            voltarPane(actionEvent);

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            mostrarAlerta("Erro", "Erro ao cadastrar funcionário: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        trocarPane(CaminhoFXML.PANE_OPCOES_CRUD);
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

    public void voltarPane(ActionEvent actionEvent) {
        trocarPane(CaminhoFXML.PANE_CADASTRO_FUNCIONARIO);
    }

    private String gerarMatricula() {
        // Lógica para gerar matrícula única
        return String.valueOf(System.currentTimeMillis() % 1000000);
    }

    private void criarUsuarioSistema() {
        user.setLogin(user.getFuncionario().getPessoa().getCpf()); // CPF como login
        user.setSenha(user.getFuncionario().getPessoa().getCpf()); // Senha padrão
        user.setAtivo(true);
        UsuarioSistemaDAO.getInstance().persist(user);
    }


    private boolean validarCampos() {
        if (dtDataAdimissao.getValue() == null || tfSetor.getText().isEmpty()) {
            mostrarAlerta("Campos obrigatórios", "Preencha data de admissão e setor.");
            return false;
        }
        return true;
    }

    @Override
    public void setDados(UsuarioSistema dados) {
        this.user = dados;
        // Gerar matrícula automática
        tfMatricula.setText(gerarMatricula());
        if(!user.getTipoUser().equals(UsuarioSistema.TipoUser.FARMACEUTICO)){
            lblCrf.setVisible(false);
            tfCrf.setVisible(false);
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

}