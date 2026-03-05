package com.example.sistema_de_saude.util;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class EntityManagerUtil {

    private static final EntityManagerFactory emf;

    static {
        try {
            Dotenv dotenv = Dotenv.load();

            String host = dotenv.get("POSTGRES_HOST");
            String port = dotenv.get("POSTGRES_PORT");
            String db = dotenv.get("POSTGRES_DB");
            String user = dotenv.get("POSTGRES_USER");
            String password = dotenv.get("POSTGRES_PASSWORD");

            String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

            Map<String, String> properties = new HashMap<>();

            properties.put("jakarta.persistence.jdbc.url", url);
            properties.put("jakarta.persistence.jdbc.user", user);
            properties.put("jakarta.persistence.jdbc.password", password);
            properties.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");

            properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            properties.put("hibernate.show_sql", "true");
            properties.put("hibernate.format_sql", "true");

            emf = Persistence.createEntityManagerFactory("sistemasaudePU", properties);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao inicializar EntityManagerFactory");
        }
    }

    public static EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public static void close(){
        if (emf != null && emf.isOpen())
            emf.close();
    }

}
