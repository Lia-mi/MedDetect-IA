package com.meddetectai.controllers;

import java.io.IOException;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import com.meddetectai.main.Doctor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.application.Platform;

public class LoginController {
    @FXML
    private TextField email;

    @FXML
    private Button login;

    @FXML
    private PasswordField senha;

    @FXML
    private Button cadastrar;

    @FXML
    private ProgressBar progress_bar;

    private Stage stage;
    private Scene scene;
    private Parent root;
    

    @FXML
    void botaoLogin(ActionEvent event) {
        String userEmail = email.getText();
        String password = senha.getText();
        
        progress_bar.setVisible(true);
        progress_bar.setProgress(0);
        
        // First timeline for progress animation
        Timeline progressTimeline = new Timeline();
        int frames = 50;
        double timePerFrame = 2.0 / frames;
        
        for (int i = 0; i <= frames; i++) {
            final double progress = (double) i / frames;
            progressTimeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(i * timePerFrame), 
                    e -> progress_bar.setProgress(progress))
            );
        }
        
        // Second timeline for authentication
        Timeline authTimeline = new Timeline(
            new KeyFrame(Duration.seconds(0.1), e -> {
                progress_bar.setVisible(false);
                performAuthentication(userEmail, password);
            })
        );
        
        progressTimeline.setOnFinished(e -> authTimeline.play());
        progressTimeline.play();
    }

    private void performAuthentication(String userEmail, String password) {
        Doctor doctor = Doctor.authenticate(userEmail, password);
        
        if (doctor != null) {
            Doctor.setCurrentDoctor(doctor);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/home.fxml"));
                Stage stage = (Stage) login.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro de Login");
                alert.setHeaderText(null);
                alert.setContentText("Email ou senha inválidos.");
                alert.showAndWait();
                progress_bar.setProgress(0);
                progress_bar.setVisible(false);
            });
        }
    }

    @FXML
    void botaoCadastrar(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/cadastroMedico.fxml")); 
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }
}
