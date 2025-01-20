package com.meddetectai.controllers;

import com.meddetectai.main.ModelType;
import com.meddetectai.main.MySQL;
import com.meddetectai.main.Paciente;
import com.meddetectai.main.Doctor;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TipoDiagnosticoController {

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

    @FXML
    private ChoiceBox<Paciente> paciente_list;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize() {
        populatePatientList();
    }

    private void populatePatientList() {
        paciente_list.getItems().clear();
        // Get only patients associated with the current doctor
        Doctor currentDoctor = Doctor.getCurrentDoctor();
        if (currentDoctor != null) {
            paciente_list.getItems().addAll(currentDoctor.getPacientes());
        }
    }

    public String getSelectedPacienteCpf() {
        Paciente selected = paciente_list.getValue();
        return (selected != null) ? selected.getCpf() : null;
    }

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
            controller.setTipoDiagController(this);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
