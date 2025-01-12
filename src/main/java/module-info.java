module com.meddetectai {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires ai.djl.api;
    requires javafx.graphics;
    requires org.slf4j;

    opens com.meddetectai.main to javafx.fxml;
    opens com.meddetectai.controllers to javafx.fxml;

    exports com.meddetectai.main;
    exports com.meddetectai.controllers;

}