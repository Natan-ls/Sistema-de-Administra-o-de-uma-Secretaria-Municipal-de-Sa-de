module com.example.sistema_de_saude {
    // Módulos Java necessários
    requires java.sql;
    requires java.naming;
    requires java.desktop;

    // Módulos JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // JPA / Hibernate
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    // MySQL Connector
    requires mysql.connector.j;

    // ABERTURAS PARA REFLECTION (Hibernate e JavaFX FXMLLoader)
    opens com.example.sistema_de_saude to
            org.hibernate.orm.core,
            javafx.fxml;

    opens com.example.sistema_de_saude.entity to
            org.hibernate.orm.core,
            jakarta.persistence;

    opens com.example.sistema_de_saude.controller to
            javafx.fxml;

    opens com.example.sistema_de_saude.controller.recepcao to javafx.fxml;
    opens com.example.sistema_de_saude.controller.consulta to javafx.fxml;
    opens com.example.sistema_de_saude.controller.farmacia to javafx.fxml;

    opens com.example.sistema_de_saude.views to javafx.fxml;

    // EXPORTS - pacotes públicos do módulo
    exports com.example.sistema_de_saude;
    exports com.example.sistema_de_saude.entity;
    exports com.example.sistema_de_saude.dataAccess;
    exports com.example.sistema_de_saude.controller;
    exports com.example.sistema_de_saude.controller.recepcao;
    exports com.example.sistema_de_saude.controller.consulta;
    exports com.example.sistema_de_saude.controller.farmacia;
    exports com.example.sistema_de_saude.util;
    exports com.example.sistema_de_saude.controller.adiministracao;
    opens com.example.sistema_de_saude.controller.adiministracao to javafx.fxml;
}
