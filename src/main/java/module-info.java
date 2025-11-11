module org.example.oopbtl1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    opens org.example.oopbtl1 to javafx.fxml;
    exports org.example.oopbtl1;
}