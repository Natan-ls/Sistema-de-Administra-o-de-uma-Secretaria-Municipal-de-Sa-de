package com.example.sistema_de_saude.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("Funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected Date dataAdimissao;
    protected String matricula;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fkPessoa", referencedColumnName = "id")
    protected Pessoa pessoa;

    @OneToOne(mappedBy = "funcionario")
    private UsuarioSistema user;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDataAdimissao(Date dataAdimissao) {
        this.dataAdimissao = dataAdimissao;
    }

    public Date getDataAdimissao() {
        return dataAdimissao;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }
}
