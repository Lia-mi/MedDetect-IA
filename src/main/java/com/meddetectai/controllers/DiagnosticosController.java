package com.meddetectai.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.meddetectai.main.MySQL;
import com.meddetectai.main.Paciente;
import com.meddetectai.main.Doctor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    void deletar(ActionEvent event) {
        Paciente selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Tem certeza que deseja apagar?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                MySQL.deletePaciente(selected.getCpf());
                tableView.getItems().remove(selected);
            }
        }
    }

    @FXML
    public void initialize() {
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        data_nasc.setCellValueFactory(new PropertyValueFactory<>("data_Nascimento"));

        Doctor currentDoctor = Doctor.getCurrentDoctor();
        if (currentDoctor != null) {
            List<Paciente> pacientes = currentDoctor.getPacientes();
            ObservableList<Paciente> data = FXCollections.observableArrayList(pacientes);
            tableView.setItems(data);
        }

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Paciente selected = tableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/meddetectai/fxml/gerarDiag.fxml"));
                        Parent root = loader.load();
                        MostrarDiagnosticoController controller = loader.getController();
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
                MostrarDiagnosticoController controller = loader.getController();
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
            root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/login.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }

    @FXML
    void editar(ActionEvent event) {
        try {
            Paciente selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/meddetectai/fxml/editarDiagnostico.fxml"));
                root = loader.load();
                EditarDiagnosticosController controller = loader.getController();
                controller.setPacienteData(selected);
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Nenhuma Seleção");
                alert.setContentText("Por favor, selecione um paciente para editar.");
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }
}

