module com.example.sistema_de_saude{
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires mysql.connector.j;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires javafx.graphics;

    // Permite acesso reflexivo do Hibernate às entidades
    opens com.example.sistema_de_saude.entity to org.hibernate.orm.core;
    exports com.example.sistema_de_saude.entity;

    // Exporta o pacote principal para o JavaFX
    //opens com.example.sistema_de_saude. to javafx.graphics;

    // Exporta os pacotes necessários para FXML
    opens com.example.sistema_de_saude.controller to javafx.fxml;
    //exports com.example.sistema_de_saude.controller;

    // Exporta o pacote principal se necessário
    exports com.example.sistema_de_saude;
}