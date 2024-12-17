import java.io.File;
import java.sql.*;
import java.time.LocalDate;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GerenciamentoContasPaciente {
    // Register user in the database

    private static Connection connectToDatabase() throws SQLException {
        String url = MySQL.getUrl();
        String user = MySQL.getUser();
        String password = MySQL.getPassword();
        
        return DriverManager.getConnection(url, user, password);
    }
    
    public static void registrar(String nome, String cpf, LocalDate nasc, String tell) {
        if (nome.isEmpty() || cpf.isEmpty() || nasc == null || tell.isEmpty()) {
            showAlert(AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
            return;
        }

        private void savePacienteToDatabase(String nome, String cpf, int telefone, LocalDate dataNascimento) throws SQLException {
            String insertQuery = "INSERT INTO Paciente (nome, cpf, telefone, data_nascimento) VALUES (?, ?, ?, ?)";
    
            try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
    
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, cpf);
                preparedStatement.setInt(3, telefone);
                preparedStatement.setDate(4, java.sql.Date.valueOf(dataNascimento));
    
                preparedStatement.executeUpdate();
            }
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
    
        
    
        private void showAlert(AlertType alertType, String title, String message) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

    }
}    