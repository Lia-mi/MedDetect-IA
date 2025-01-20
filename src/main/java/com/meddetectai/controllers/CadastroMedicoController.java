package com.meddetectai.controllers;

import java.io.IOException;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroMedicoController {
    
    @FXML
    private TextField email;

    @FXML
    private TextField nome;

    @FXML
    private TextField telefone;

    @FXML
    private TextField registroProfissional;

    @FXML
    private PasswordField senha;

    @FXML
    private PasswordField confirmaSenha;

    @FXML
    private Parent root;

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @FXML
    void botaoCadastrar(ActionEvent event) {
        if (!senha.getText().equals(confirmaSenha.getText())) {
            showAlert("Erro", "As senhas não coincidem!");
            return;
        }

        if (Doctor.registerNewDoctor(
                senha.getText(),
                nome.getText(),
                email.getText(),
                telefone.getText(),
                registroProfissional.getText())) {
            
            showAlert("Sucesso", "Médico cadastrado com sucesso!");
            clearFields();
            
            // Navigate to login screen
            try {
                root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/login.fxml")); // Carrega a tela de cadastro de paciente
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();   
            }
        } else {
            showAlert("Erro", "Erro ao cadastrar médico!");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        email.clear();
        nome.clear();
        telefone.clear();
        registroProfissional.clear();
        senha.clear();
        confirmaSenha.clear();
    }

    @FXML
    void botaoVoltar(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/login.fxml")); 
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }
}
