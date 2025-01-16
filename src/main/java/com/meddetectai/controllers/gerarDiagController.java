package com.meddetectai.controllers;

import com.meddetectai.main.Paciente;
import com.meddetectai.main.MySQL;

import java.io.IOException;

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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class gerarDiagController {

    @FXML
    private Button diagFeitos;

    @FXML
    private Label diagnostico;

    @FXML
    private Label doenca;

    @FXML
    private GridPane grid_result;

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
    void voltarInicio(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/home.fxml")); // Volta pro inicio
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }

    public void showPacienteDetails(String cpf) {
        Paciente paciente = MySQL.getPacienteByCpf(cpf);
        if (paciente != null) {
            nome.setText(paciente.getNome());
            int age = java.time.Period
                .between(paciente.getData_Nascimento(), java.time.LocalDate.now())
                .getYears();
            idade.setText(String.valueOf(age));
            doenca.setText("Exemplo de doença");
            diagnostico.setText("Exemplo de diagnóstico");
        }
    }

}

