package com.meddetectai.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private Button MedIA;

    @FXML
    private Button diagFeitos;

    @FXML
    private Button novoDiag;

    @FXML
    private TextField pesquisaPg;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void abrircadP(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/layout_cadPac.fxml")); // Corrected path
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }

    @FXML
    void mostraDiag(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/seusDiagnosticos.fxml")); // Corrected path
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }

    @FXML
    void voltarInicio(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/home.fxml")); // Corrected path
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }


}