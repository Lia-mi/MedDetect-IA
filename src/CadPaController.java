import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CadPaController {

    @FXML
    private Button botaoCadPaciente;

    @FXML
    private TextField cpf_Paciente;

    @FXML
    private DatePicker data_Nasc;

    @FXML
    private TextField nome_Paciente;

    @FXML
    private TextField numero_tell;

    private final String DB_URL = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10748017";
    private final String DB_USER = "sql10748017"; // Replace with your DB username
    private final String DB_PASSWORD = "Y93vH89uZV"; // Replace with your DB password

    @FXML
    void cadastrarPaciente(ActionEvent event) {
        String nome = nome_Paciente.getText();
        String cpf = cpf_Paciente.getText();
        LocalDate nasc = data_Nasc.getValue();
        String tell = numero_tell.getText();

        if (nome.isEmpty() || cpf.isEmpty() || nasc == null || tell.isEmpty()) {
            showAlert(AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
            return;
        }

        try {
            int num_tell = Integer.parseInt(tell);

            // Save patient to the database
            savePacienteToDatabase(nome, cpf, num_tell, nasc);

            showAlert(AlertType.INFORMATION, "Sucesso", "Paciente cadastrado com sucesso!");
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Erro", "Número de telefone inválido.");
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Erro no Banco de Dados", "Não foi possível salvar o paciente:\n" + e.getMessage());
        }
    }

    private void savePacienteToDatabase(String nome, String cpf, int telefone, LocalDate dataNascimento) throws SQLException {
        String insertQuery = "INSERT INTO Paciente (nome, cpf, telefone, data_nascimento) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, cpf);
            preparedStatement.setInt(3, telefone);
            preparedStatement.setDate(4, java.sql.Date.valueOf(dataNascimento));

            preparedStatement.executeUpdate();
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
