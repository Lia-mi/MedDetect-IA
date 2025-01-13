package com.meddetectai.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class gerarDiagController {

    @FXML
    private Button MedIA;

    @FXML
    private Hyperlink artigos;

    @FXML
    private Button diagFeitos;

    @FXML
    private Label diagnostico;

    @FXML
    private Label doenca;

    @FXML
    private Label idade;

    @FXML
    private Label nome;

    @FXML
    private Button novoD;

    @FXML
    private TextField pesquisaDiag;

    @FXML
    private ImageView tomografia;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void abrePesc(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/src/main/resources/com/meddetectai/fxml/layout_cadPac.fxml")); // Carrega a tela de cadastro de paciente
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
            root = FXMLLoader.load(getClass().getResource("/src/main/resources/com/meddetectai/fxml/seusDiagnosticos.fxml")); // Carrega a tela dos diagnosticos feitos.
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
            root = FXMLLoader.load(getClass().getResource("/src/main/resources/com/meddetectai/fxml/home.fxml")); // Volta pro inicio
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }

}

