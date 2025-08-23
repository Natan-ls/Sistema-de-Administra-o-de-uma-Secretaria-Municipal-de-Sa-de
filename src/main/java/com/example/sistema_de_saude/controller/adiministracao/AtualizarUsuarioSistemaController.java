package com.example.sistema_de_saude.controller.adiministracao;

import com.example.sistema_de_saude.dataAccess.*;
import com.example.sistema_de_saude.entity.*;
import com.example.sistema_de_saude.util.CaminhoFXML;
import com.example.sistema_de_saude.util.NavegadorPane;
import com.example.sistema_de_saude.util.ReceberDados;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.rmi.dgc.Lease;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AtualizarUsuarioSistemaController extends NavegadorPane implements ReceberDados<UsuarioSistema> {

    @FXML private TextField tfLogin;
    @FXML private TextField tfSenha;
    @FXML private TextField tfEmail;
    @FXML private TextField tfTelefone;
    @FXML private TextField tfEndereco;
    @FXML private TextField tfSetor;
    @FXML private DatePicker dtDataAdmissao;

    private UsuarioSistema usuarioSelecionado;

    @Override
    public void setDados(UsuarioSistema dados) {
        this.usuarioSelecionado = dados;
        if (usuarioSelecionado != null) {
            tfLogin.setText(usuarioSelecionado.getLogin());
            tfSenha.setText(usuarioSelecionado.getSenha());
            Funcionario f = usuarioSelecionado.getFuncionario();
            tfEmail.setText(f.getPessoa().getEmail());
            tfTelefone.setText(f.getPessoa().getTelefone());
            tfEndereco.setText(f.getPessoa().getEndereco());
            tfSetor.setText(f.getClass().getSimpleName().equals("Administrador") ? ((Administrador) f).getSetor() :
                    f.getClass().getSimpleName().equals("Recepcionista") ? ((Recepcionista) f).getSetor() : "");
            if (f.getDataAdimissao() != null) {
                dtDataAdmissao.setValue(f.getDataAdimissao().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            }
        }
    }

    @FXML
    public void atualizarUsuario(ActionEvent event) {
        try {
            // Atualiza dados básicos do usuário
            usuarioSelecionado.setLogin(tfLogin.getText());
            usuarioSelecionado.setSenha(tfSenha.getText());

            // Merge no usuário
            UsuarioSistemaDAO.getInstance().merge(usuarioSelecionado);

            // Atualiza dados do funcionário
            Funcionario funcionario = usuarioSelecionado.getFuncionario();
            LocalDate localDate = dtDataAdmissao.getValue();
            if (localDate != null) {
                Date dataAdmissao = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                funcionario.setDataAdimissao(dataAdmissao);
            }
            // Atualiza setor apenas se o funcionário tiver
            if (funcionario instanceof Administrador admin) {
                admin.setSetor(tfSetor.getText());
                AdministradorDAO.getInstance().merge(admin);
            } else if (funcionario instanceof Recepcionista recep) {
                recep.setSetor(tfSetor.getText());
                RecepcionistaDAO.getInstance().merge(recep);
            } else if (funcionario instanceof Medico medico) {
                MedicoDAO.getInstance().merge(medico);
            } else if (funcionario instanceof Farmaceutico farmaceutico) {
                FarmaceuticoDAO.getInstance().merge(farmaceutico);
            }

            // Merge do funcionário
            FuncionarioDAO.getInstance().merge(funcionario);

            // Atualiza dados da pessoa vinculada ao funcionário
            Pessoa pessoa = funcionario.getPessoa();
            pessoa.setEmail(tfEmail.getText());
            pessoa.setTelefone(tfTelefone.getText());
            pessoa.setEndereco(tfEndereco.getText());

            PessoaDAO.getInstance().merge(pessoa);

            mostrarAlerta("Sucesso", "Usuário atualizado com sucesso!", Alert.AlertType.CONFIRMATION);

            // Retorna para a tela de usuários
            trocarPane(CaminhoFXML.PANE_TABELA_USUARIOS);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Falha ao atualizar usuário.", Alert.AlertType.ERROR);
        }
    }



    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
