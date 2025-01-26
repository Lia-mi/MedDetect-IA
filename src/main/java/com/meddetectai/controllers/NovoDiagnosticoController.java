package com.meddetectai.controllers;

import java.io.File;
import java.io.IOException;

import com.meddetectai.main.ModelCore;
import com.meddetectai.main.ModelType;
import com.meddetectai.main.Paciente;
import com.meddetectai.main.Diagnostico;
import com.meddetectai.main.MySQL;

import ai.djl.MalformedModelException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    @FXML
    private ProgressBar progress_bar;

    private Stage stage;
    
    private Scene scene;
    
    private Parent root;

    private ModelType modelType;

    private TipoDiagnosticoController tipoDiagController;

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

    public void setModelType(ModelType modelType) {
        this.modelType = modelType;
    }

    public void setTipoDiagController(TipoDiagnosticoController tipoDiagController) {
        this.tipoDiagController = tipoDiagController;
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
        if (selectedImageFile == null) {
            Platform.runLater(() -> resultado.setText("Por favor, selecione uma imagem primeiro."));
            return;
        }

        Platform.runLater(() -> {
            resultado.setText("");
            progress_bar.setVisible(true);
            progress_bar.setProgress(0);
        });
        
        Timeline progressTimeline = new Timeline();
        int frames = 50;
        double timePerFrame = 3.0 / frames;
        
        for (int i = 0; i <= frames; i++) {
            final double progress = (double) i / frames;
            progressTimeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(i * timePerFrame), 
                    e -> progress_bar.setProgress(progress))
            );
        }
        
        Timeline diagnosticTimeline = new Timeline(
            new KeyFrame(Duration.seconds(0.1), e -> {
                progress_bar.setVisible(false);
                Platform.runLater(this::performDiagnostic);
            })
        );
        
        progressTimeline.setOnFinished(e -> diagnosticTimeline.play());
        progressTimeline.play();
    }

    private void performDiagnostic() {
        try {
            String result = ModelCore.diagnose(selectedImageFile, modelType);
            Diagnostico diagnostico = new Diagnostico(modelType, selectedImageFile.getPath(), result);
            Paciente.getCurrentPaciente().setDiagnostico(diagnostico);
            String cpf = tipoDiagController.getSelectedPacienteCpf();
            MySQL.insertDiagnostico(diagnostico, cpf);
            Platform.runLater(() -> resultado.setText(result));
        } catch (Exception ex) {
            Platform.runLater(() -> {
                resultado.setText("Erro ao realizar diagnóstico");
                progress_bar.setVisible(false);
                ex.printStackTrace();
            });
        }
    }

    @FXML
    void telaInicial(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/home.fxml")); // Volta pro inicio
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
