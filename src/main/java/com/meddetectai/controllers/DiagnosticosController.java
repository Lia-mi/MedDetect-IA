package com.meddetectai.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.meddetectai.main.MySQL;
import com.meddetectai.main.Paciente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DiagnosticosController {

    @FXML
    private Button MedIA;

    @FXML
    private TableColumn<Paciente, String> cpf;

    @FXML
    private TableColumn<Paciente, LocalDate> data_nasc;

    @FXML
    private Button diagFeitos;

    @FXML
    private TableColumn<Paciente, String> nome;

    @FXML
    private Button novoD;

    @FXML
    private TableColumn<Paciente, Integer> telefone;

    @FXML
    private TextField pesquisaDiag;

    @FXML
    private TableView<Paciente> tableView;

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    void abrePesc(ActionEvent event) {
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
    void mostraDiag(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/seusDiagnosticos.fxml")); // Carrega a próxima tela dos diagnosticos salvos.
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
            Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/home.fxml")); // Carrega a tela de inicio
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }

    @FXML
    void deletar(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        data_nasc.setCellValueFactory(new PropertyValueFactory<>("data_Nascimento"));

        List<Paciente> pacientes = MySQL.getPacientes();
        ObservableList<Paciente> data = FXCollections.observableArrayList(pacientes);
        tableView.setItems(data);

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Paciente selected = tableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/meddetectai/fxml/gerarDiag.fxml"));
                        Parent root = loader.load();
                        gerarDiagController controller = loader.getController();
                        controller.showPacienteDetails(selected.getCpf());
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void showGerarDiagScreen(ActionEvent event) {
        try {
            Paciente selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/meddetectai/fxml/gerarDiag.fxml"));
                Parent root = loader.load();
                gerarDiagController controller = loader.getController();
                controller.showPacienteDetails(selected.getCpf());
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

