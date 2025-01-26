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
    void cadastrarPaciente(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/layout_cadPac.fxml")); // Carrega a tela de cadastro de paciente
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }

    @FXML
    void listarDiagnosticos(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/seusDiagnosticos.fxml")); // Carrega a tela dos diagnosticos feitos.
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }

    @FXML
    void novoDiagnostico(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/tipoDiagnostico.fxml")); // Volta pro inicio
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }

    @FXML
    void sair(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/login.fxml")); // Carrega a tela de cadastro de paciente
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }

    
}
