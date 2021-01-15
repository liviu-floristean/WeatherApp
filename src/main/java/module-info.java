module ro.mta.se.lab {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.security.jgss;
    requires json.simple;

    opens ro.mta.se.lab.Controller to javafx.fxml;
    exports ro.mta.se.lab;
}