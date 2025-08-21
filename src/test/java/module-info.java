module com.example.sistema_de_saude {
    // Módulos Java necessários
    requires java.sql;
    requires java.naming;

    // Módulos JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // Módulos JPA/Hibernate
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    // MySQL Connector
    requires mysql.connector.j;

    // ABERTURAS PARA REFLECTION (CRÍTICO para Hibernate)
    opens com.example.sistema_de_saude to
            org.hibernate.orm.core,
            javafx.fxml;

    // EXPORTS
    exports com.example.sistema_de_saude;
}