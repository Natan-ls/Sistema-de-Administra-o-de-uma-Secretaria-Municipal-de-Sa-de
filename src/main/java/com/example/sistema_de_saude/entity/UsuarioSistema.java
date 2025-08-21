package com.example.sistema_de_saude.entity;

import jakarta.persistence.*;

@Entity
public class UsuarioSistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String senha;
    private boolean ativo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fkUsuario", referencedColumnName = "id")
    Funcionario funcionario;

    @Enumerated(EnumType.STRING)
    private TipoUser tipoUser;

    public enum TipoUser{ MEDICO, FARMACEUTICO, RECEPCIONISTA, ADMINISTRADOR}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public TipoUser getTipoUser() {return tipoUser; }

    public void setTipoUser(TipoUser tipoUser) {this.tipoUser = tipoUser; }

}
