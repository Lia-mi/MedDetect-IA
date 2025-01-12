package com.meddetectai.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private Label label;

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
    }
}

 /*  private static String url = "jdbc:mysql://uotyb.h.filess.io:3307/medDetectIA_writtenpie";
    private static String user = "medDetectIA_writtenpie";
    private static String password = "c04f420403bf2ac311d0524a768e627fd3f52069";
 */