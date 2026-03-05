module com.example.sistema_de_saude {

    // Java base
    requires java.sql;
    requires java.naming;
    requires java.desktop;
    requires java.rmi;

    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // JPA / Hibernate
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    // PostgreSQL driver
    requires org.postgresql.jdbc;

    // BCrypt
    requires jbcrypt;

    // Dotenv
    requires io.github.cdimascio.dotenv.java;


    // =========================
    // OPENS (reflection)
    // =========================

    opens com.example.sistema_de_saude to
            javafx.fxml,
            org.hibernate.orm.core;

    opens com.example.sistema_de_saude.entity to
            org.hibernate.orm.core,
            jakarta.persistence;

    opens com.example.sistema_de_saude.controller to javafx.fxml;

    opens com.example.sistema_de_saude.controller.agendamento to javafx.fxml;
    opens com.example.sistema_de_saude.controller.recepcao to javafx.fxml;
    opens com.example.sistema_de_saude.controller.consulta to javafx.fxml;
    opens com.example.sistema_de_saude.controller.farmacia to javafx.fxml;
    opens com.example.sistema_de_saude.controller.adiministracao to javafx.fxml;
    opens com.example.sistema_de_saude.util to javafx.fxml;
    opens com.example.sistema_de_saude.views to javafx.fxml;


    // =========================
    // EXPORTS
    // =========================

    exports com.example.sistema_de_saude;
    exports com.example.sistema_de_saude.entity;
    exports com.example.sistema_de_saude.dataAccess;
    exports com.example.sistema_de_saude.controller;
    exports com.example.sistema_de_saude.controller.recepcao;
    exports com.example.sistema_de_saude.controller.consulta;
    exports com.example.sistema_de_saude.controller.farmacia;
    exports com.example.sistema_de_saude.controller.adiministracao;
    exports com.example.sistema_de_saude.controller.agendamento;
    exports com.example.sistema_de_saude.util;
}