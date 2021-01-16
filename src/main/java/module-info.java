module ro.mta.se.lab {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.security.jgss;
    requires json.simple;
    requires junit;
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;

    opens ro.mta.se.lab.Controller to javafx.fxml, junit;
    exports ro.mta.se.lab;
    exports ro.mta.se.lab.Controller to junit;
}