module com.example.fqquiz {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.fqquiz to javafx.fxml;
    exports com.example.fqquiz;
}