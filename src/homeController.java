import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import java.io.IOException;

public class homeController {

    @FXML
    private Hyperlink ajudenos;

    @FXML
    private Button loginEmpresa;

    @FXML
    private Button novoDiag;

    @FXML
    private TextField pesquisaPg;


    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void abrirLogin(ActionEvent event) {

    }

    @FXML
    void abrircadP(ActionEvent event) {
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

}
