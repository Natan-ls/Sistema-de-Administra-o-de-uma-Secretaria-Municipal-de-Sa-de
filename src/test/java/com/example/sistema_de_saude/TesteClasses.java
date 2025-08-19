package com.example.sistema_de_saude;

import com.example.sistema_de_saude.entity.*;

public class TesteClasses {
    public static void main(String[] args) {
        Pessoa p1 = new Pessoa();
        Pessoa p2 = new Pessoa();
        Paciente pa = new Paciente();
        Consulta c = new Consulta();
        Medico m = new Medico();
        p1.setNome("Paciente");
        p2.setNome("Medico");
        pa.setNumeroSus("123");
        m.setCrm("321");
        m.setPessoa(p2);
        p1.setPaciente(pa);
        p2.setFuncionario(m);
        pa.setPessoa(p1);
        c.setPaciente(pa);
        c.setMedico(m);
        System.out.println(c.toString());
    }
}
