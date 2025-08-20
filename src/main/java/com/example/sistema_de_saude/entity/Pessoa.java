package com.example.sistema_de_saude.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private Date dataNascimento;
    private Character sexo;
    private String telefone;

    @Column(unique = true, nullable = true)
    private String cpf;

    @OneToOne(mappedBy = "pessoa")
    private Funcionario funcionario;

    @OneToOne(mappedBy = "pessoa")
    private Paciente paciente;

    public Pessoa(){};

    public Pessoa(String nome, String cpf , String email, Date dataNascimento, Character sexo){
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Character getSexo() {return sexo; }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getTelefone() {return telefone;}

    public void setTelefone(String telefone) {this.telefone = telefone; }
}
