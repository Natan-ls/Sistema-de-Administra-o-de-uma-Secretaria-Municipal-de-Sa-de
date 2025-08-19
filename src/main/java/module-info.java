module com.example.sistema_de_saude {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sistema_de_saude to javafx.fxml;
    exports com.example.sistema_de_saude;
}