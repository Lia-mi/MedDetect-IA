package com.meddetectai.controllers;

import com.meddetectai.main.MySQL;

import com.meddetectai.main.Paciente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class EditarDiagnosticosController {

    @FXML
    private Button MedIA;

    @FXML
    private TextField cpf;

    @FXML
    private DatePicker data_nasc;

    @FXML
    private Button diagFeitos;

    @FXML
    private Button diagFeitos1;

    @FXML
    private TextField nome;

    @FXML
    private Label nome_paciente;

    @FXML
    private Button nvoD;

    @FXML
    private TextField telefone;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Paciente currentPaciente;

    public void setPacienteData(Paciente paciente) {
        this.currentPaciente = paciente;
        nome.setText(paciente.getNome());
        cpf.setText(paciente.getCpf());
        telefone.setText(String.valueOf(paciente.getTelefone()));
        data_nasc.setValue(paciente.getData_Nascimento());
        nome_paciente.setText("Editando: " + paciente.getNome());
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
    void salvar(ActionEvent event) {
        try {
            Paciente updatedPaciente = new Paciente(
                nome.getText(),
                cpf.getText(),
                Integer.parseInt(telefone.getText()),
                data_nasc.getValue()
            );

            boolean success = MySQL.updatePaciente(updatedPaciente);

            if (success) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setContentText("Dados atualizados com sucesso!");
                alert.showAndWait();

                root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/seusDiagnosticos.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("Erro ao atualizar os dados!");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Por favor, insira um número válido para o telefone!");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void telaInicial(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/home.fxml"));
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

}
