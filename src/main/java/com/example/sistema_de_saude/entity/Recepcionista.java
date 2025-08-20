package com.example.sistema_de_saude.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@DiscriminatorValue("Recepcionista")
public class Recepcionista extends Funcionario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String setor;

    public Recepcionista(){};

    public Recepcionista(Date dataAdimissao, int matricula, String setor){
        super(dataAdimissao, matricula);
        this.setor = setor;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public Consulta agendarConsulta(Paciente paciente, Medico medico, Date data) {
        if (paciente == null || medico == null || data == null) {
            throw new IllegalArgumentException("Paciente, médico e data são obrigatórios para agendar consulta.");
        }

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataConsulta(data);
        consulta.setDescricao("Consulta agendada pelo recepcionista");
        consulta.setStatus(Consulta.StatusConsulta.CONFIRMADA);
        return consulta;
    }

    public void cancelarConsulta(Consulta consulta) {
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não pode ser nula.");
        }

        if (consulta.getStatus() == Consulta.StatusConsulta.REALIZADA ||
                consulta.getStatus() == Consulta.StatusConsulta.EM_ATENDIMENTO) {
            throw new IllegalStateException("Não é possível cancelar uma consulta em andamento ou já realizada.");
        }

        consulta.setStatus(Consulta.StatusConsulta.CANCELADA);
        consulta.setDescricao("Consulta cancelada pelo recepcionista");
    }

}
