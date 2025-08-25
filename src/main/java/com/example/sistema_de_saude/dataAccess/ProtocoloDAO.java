package com.example.sistema_de_saude.dataAccess;

import com.example.sistema_de_saude.entity.Protocolo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

public class ProtocoloDAO {


    private static ProtocoloDAO instance;
    protected EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public static ProtocoloDAO getInstance() {
        if (instance == null) {
            instance = new ProtocoloDAO();
        }
        return instance;
    }
    private ProtocoloDAO() {
        entityManager = Persistence.createEntityManagerFactory("sistemasaudePU").createEntityManager();
    }
    public Protocolo getById(int id) {
        return entityManager.find(Protocolo.class, id);
    }
    @SuppressWarnings("unchecked")
    public List<Protocolo> findAll() {
        return entityManager.createQuery("FROM Protocolo").getResultList();
    }
    public void persist(Protocolo protocolo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(protocolo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void merge(Protocolo protocolo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(protocolo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void remove(Protocolo protocolo) {
        try {
            entityManager.getTransaction().begin();
            protocolo = entityManager.find(Protocolo.class, protocolo.getId());
            entityManager.remove(protocolo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void removeById(int id) {
        try {
            Protocolo protocolo = getById(id);
            remove(protocolo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Protocolo getByCodigo(String codigo) {
        try {
            return entityManager.createQuery(
                            "SELECT p FROM Protocolo p WHERE p.codigo = :codigo", Protocolo.class)
                    .setParameter("codigo", codigo)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Protocolo getProtocoloComMedicamentos(Long id) {
        return entityManager.createQuery(
                        "SELECT p FROM Protocolo p LEFT JOIN FETCH p.medicamentos WHERE p.id = :id",
                        Protocolo.class
                )
                .setParameter("id", id)
                .getSingleResult();
    }


}

