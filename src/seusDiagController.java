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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class seusDiagController {

    @FXML
    private Button MedIA;

    @FXML
    private Hyperlink artigos;

    @FXML
    private Button diagFeitos;

    @FXML
    private Button novoD;

    @FXML
    private ImageView pdfDF1;

    @FXML
    private ImageView pdfDF2;

    @FXML
    private ImageView pdfDF3;

    @FXML
    private ImageView pdfDF4;

    @FXML
    private ImageView pdfDF5;

    @FXML
    private TextField pesquisaDiag;

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    void abrePesc(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("layout_cadPac.fxml")); // Carrega a próxima tela a carteira.
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
            Parent root = FXMLLoader.load(getClass().getResource("seusDiagnosticos.fxml")); // Carrega a próxima tela a carteira.
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
            Parent root = FXMLLoader.load(getClass().getResource("home.fxml")); // Carrega a próxima tela a carteira.
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }
}
