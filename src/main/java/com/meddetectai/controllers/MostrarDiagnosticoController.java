package com.meddetectai.controllers;

import com.meddetectai.main.Paciente;
import com.meddetectai.main.MySQL;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MostrarDiagnosticoController {

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
    private Parent root;

    @FXML
    void cadastrarPaciente(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/layout_cadPac.fxml"));
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
            root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/seusDiagnosticos.fxml"));
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
            root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/tipoDiagnostico.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }

    public void showPacienteDetails(String cpf) {
        String queryPaciente = "SELECT nome, data_nascimento FROM Paciente WHERE cpf = ?";
        String queryDiagnostico = "SELECT tipo, resultado, imagem FROM Diagnostico WHERE cpf_paciente = ?";

        try (Connection conn = MySQL.getConnection();
             PreparedStatement stmtPaciente = conn.prepareStatement(queryPaciente);
             PreparedStatement stmtDiagnostico = conn.prepareStatement(queryDiagnostico)) {

            stmtPaciente.setString(1, cpf);
            ResultSet rsPaciente = stmtPaciente.executeQuery();

            if (rsPaciente.next()) {
                String nomePaciente = rsPaciente.getString("nome");
                java.sql.Date dataNascimento = rsPaciente.getDate("data_nascimento");

                nome.setText(nomePaciente);
                int age = java.time.Period.between(dataNascimento.toLocalDate(), java.time.LocalDate.now()).getYears();
                idade.setText(String.valueOf(age));
            }

            stmtDiagnostico.setString(1, cpf);
            ResultSet rsDiagnostico = stmtDiagnostico.executeQuery();

            if (rsDiagnostico.next()) {
                String tipo = rsDiagnostico.getString("tipo");
                String resultado = rsDiagnostico.getString("resultado");
                byte[] imagemBytes = rsDiagnostico.getBytes("imagem");

                doenca.setText(tipo);
                diagnostico.setText(resultado);

                if (imagemBytes != null) {
                    Image imagem = new Image(new ByteArrayInputStream(imagemBytes));
                    tomografia.setImage(imagem);
                }
            }

        } catch (SQLException e) {
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

