module org.example.oopbtl1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires java.desktop;

    opens org.example.oopbtl1 to javafx.fxml;
    exports org.example.oopbtl1;
}