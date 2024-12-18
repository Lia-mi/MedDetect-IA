import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class layout_CadPaController {

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

    private Stage stage;
    private Scene scene;
    private Parent root;

    private static Connection connectToDatabase() throws SQLException {
        String url = MySQL.getUrl();
        String user = MySQL.getUser();
        String password = MySQL.getPassword();
    
        return DriverManager.getConnection(url, user, password);
    }


    @FXML
    void cadastrarPaciente(ActionEvent event) throws IOException {
        
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

            Parent root = FXMLLoader.load(getClass().getResource("tipoDiagnostico.fxml")); // Carrega a próxima tela a carteira.
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Erro", "Número de telefone inválido.");
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Erro no Banco de Dados", "Não foi possível salvar o paciente:\n" + e.getMessage());
        }
    }

    private void savePacienteToDatabase(String nome, String cpf, int telefone, LocalDate dataNascimento) throws SQLException {
        String insertQuery = "INSERT INTO Paciente (nome, cpf, telefone, data_nascimento) VALUES (?, ?, ?, ?)";

        try (Connection conn = connectToDatabase()){
            try (PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, cpf);
                preparedStatement.setInt(3, telefone);
                preparedStatement.setDate(4, java.sql.Date.valueOf(dataNascimento));
    
                preparedStatement.executeUpdate();
            }
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
