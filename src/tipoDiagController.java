import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class tipoDiagController {

    @FXML
    private Button alzhaimer;

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
    void abrePesc(ActionEvent event) {

    }

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    void abrirEscolherfileA(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("novoDiagnostico.fxml")); // Carrega a próxima tela a carteira.
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }

    @FXML
    void abrirEscolherfileC(ActionEvent event) {

    }

    @FXML
    void mostraDiag(ActionEvent event) {

    }

}
