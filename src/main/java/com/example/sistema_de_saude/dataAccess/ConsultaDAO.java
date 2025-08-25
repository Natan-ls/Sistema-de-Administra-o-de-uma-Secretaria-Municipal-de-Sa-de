package com.example.sistema_de_saude.dataAccess;

import com.example.sistema_de_saude.entity.Consulta;
import com.example.sistema_de_saude.entity.TipoMedicamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

public class ConsultaDAO {


    private static ConsultaDAO instance;
    protected EntityManager entityManager;
    public static ConsultaDAO getInstance() {
        if (instance == null) {
            instance = new ConsultaDAO();
        }
        return instance;
    }
    private ConsultaDAO() {
        entityManager = Persistence.createEntityManagerFactory("sistemasaudePU").createEntityManager();
    }
    public Consulta getById(int id) {
        return entityManager.find(Consulta.class, id);
    }
    @SuppressWarnings("unchecked")
    public List<Consulta> findAll() {
        return entityManager.createQuery("FROM Consulta").getResultList();
    }
    public void persist(Consulta consulta) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(consulta);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void merge(Consulta consulta) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(consulta);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void remove(Consulta consulta) {
        try {
            entityManager.getTransaction().begin();
            consulta = entityManager.find(Consulta.class, consulta.getId());
            entityManager.remove(consulta);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void removeById(int id) {
        try {
            Consulta consulta = getById(id);
            remove(consulta);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<TipoMedicamento> findAllFresh() {
        entityManager.clear();
        return entityManager.createQuery("FROM TipoMedicamento", TipoMedicamento.class)
                .getResultList();
    }

}

