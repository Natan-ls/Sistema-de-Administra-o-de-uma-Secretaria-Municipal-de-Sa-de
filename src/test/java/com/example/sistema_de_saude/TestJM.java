package com.example.sistema_de_saude;

import com.example.sistema_de_saude.dataAccess.MedicoDAO;
import com.example.sistema_de_saude.entity.Medico;
import com.example.sistema_de_saude.entity.Pessoa;

public class TestJM {
    public static void main(String[] args){
        Pessoa p1 = new Pessoa();
        //Pessoa p2 = new Pessoa();

        Medico m1 = new Medico();

        p1.setCpf("15154545645");
        p1.setNome("JhonnyBoy");

        p1.setFuncionario(m1);

        m1.setCrm("124567");
        m1.setEspecialidade("Cardio");

        //MedicoDAO.getInstance().persist(m1);

    }



}
