package com.meddetectai.controllers;

import com.meddetectai.main.ModelType;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class tipoDiagController {

    @FXML
    private Button MedIA;

    @FXML
    private Button alzheimer;

    @FXML
    private Hyperlink artigos;

    @FXML
    private Button cancer;

    @FXML
    private Button diagFeitos;

    @FXML
    private Button nvoD;

    @FXML
    private TextField pesquisaPg;

    private Stage stage;
    private Scene scene;

    

    
    @FXML
    void abrePesc(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/layout_cadPac.fxml")); // Carrega a próxima tela cadastro do paciente.
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }

    @FXML
    void abrirAlzheimer(ActionEvent event) {
        abrirDiagnostico(event, ModelType.ALZHEIMER);
    }

    @FXML
    void abrirCancer(ActionEvent event) {
        abrirDiagnostico(event, ModelType.TUMOR);
    }

    private void abrirDiagnostico(ActionEvent event, ModelType modelType) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/meddetectai/fxml/novoDiagnostico.fxml"));
            Parent root = loader.load();
            NovoDiagnosticoController controller = loader.getController();
            controller.setModelType(modelType);
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
            Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/seusDiagnosticos.fxml")); 
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
            Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/home.fxml")); 
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }
}
