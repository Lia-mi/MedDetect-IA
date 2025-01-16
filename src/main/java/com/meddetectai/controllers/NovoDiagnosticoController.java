package com.meddetectai.controllers;

import java.io.File;
import java.io.IOException;

import com.meddetectai.main.ModelCore;
import com.meddetectai.main.ModelType;
import com.meddetectai.main.Paciente;
import com.meddetectai.main.Diagnostico;

import ai.djl.MalformedModelException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class NovoDiagnosticoController {

    String simpleMlp;

    @FXML
    private Button MedIA;

    @FXML
    private Button diagFeitos;

    @FXML
    private Button nvoD;

    @FXML
    private TextField pesquisaPg;

    @FXML
    private Button upload_image;

    @FXML
    private ImageView imageView;

    @FXML
    private Label resultado;

    private File selectedImageFile;

    private Stage stage;
    
    private Scene scene;    

    private ModelType modelType;

    public void setModelType(ModelType modelType) {
        this.modelType = modelType;
    }

    @FXML
    void abrePesc(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/layout_cadPac.fxml")); // Carrega a tela de cadastro de paciente
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
            Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/seusDiagnosticos.fxml")); // Carrega a tela dos diagnosticos feitos.
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }

    @FXML
    public File uploadImageC(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha a sua tomografia");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(pngFilter);

        selectedImageFile = fileChooser.showOpenDialog(null);
        if (selectedImageFile != null) {
            javafx.scene.image.Image image = new javafx.scene.image.Image(selectedImageFile.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(400);
            imageView.setPreserveRatio(true);
        }

        return selectedImageFile;
    }

    @FXML
    public void diagnosticar(ActionEvent event) throws MalformedModelException {
        String result = ModelCore.diagnose(selectedImageFile, modelType);
        resultado.setText(result);

        Diagnostico diagnostico = new Diagnostico(modelType, selectedImageFile.getPath(), result);
        Paciente.getCurrentPaciente().setDiagnostico(diagnostico);
    }
    

    @FXML
    void voltarInicio(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/home.fxml")); // Carrega atela de inicio
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }
}
