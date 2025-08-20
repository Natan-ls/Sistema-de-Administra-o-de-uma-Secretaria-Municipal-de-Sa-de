package com.example.sistema_de_saude;

import com.example.sistema_de_saude.entity.*;

public class TesteClasses {
    public static void main(String[] args) {
// Criando pessoas
        Pessoa p1 = new Pessoa();
        Pessoa p2 = new Pessoa();

        // Criando paciente e médico
        Paciente paciente = new Paciente();
        Medico medico = new Medico();

        // Atribuindo dados básicos
        p1.setNome("Paciente João");
        p2.setNome("Dr. Carlos");
        paciente.setNumeroSus("123456");
        medico.setCrm("CRM123");

        // Ligando pessoa com paciente e médico
        paciente.setPessoa(p1);
        medico.setPessoa(p2);
        p1.setPaciente(paciente);
        p2.setFuncionario(medico);

        // Criando consulta
        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setStatus(Consulta.StatusConsulta.EM_ATENDIMENTO);

        // Criando medicamentos
        Medicamento med1 = new Medicamento();
        med1.setNome("Dipirona");
        Medicamento med2 = new Medicamento();
        med2.setNome("Amoxicilina");

        // Criando protocolo e associando à consulta
        Protocolo protocolo = new Protocolo();
        protocolo.setCodigo("PROTO123");
        protocolo.setDataGerada(new java.util.Date());

        // Adicionando medicamentos ao protocolo
        protocolo.addMedicamento(med1);
        protocolo.addMedicamento(med2);

        // Ligando protocolo à consulta
        consulta.setProtocolo(protocolo);

        // Exibindo os dados
        System.out.println("Consulta: " + consulta.getId());
        System.out.println("Paciente: " + consulta.getPaciente().getPessoa().getNome());
        System.out.println("Médico: " + consulta.getMedico().getPessoa().getNome());
        System.out.println("Status: " + consulta.getStatus());
        System.out.println("Protocolo: " + consulta.getProtocolo().getCodigo());
        System.out.println("Medicamentos no Protocolo:");
        for (Medicamento m : consulta.getProtocolo().getMedicamentos()) {
            System.out.println("- " + m.getNome());
        }
    }
}
