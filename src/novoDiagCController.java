import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class novoDiagCController {

    @FXML
    private Button MedIA;

    @FXML
    private Button diagFeitos;

    @FXML
    private Button nvoD;

    @FXML
    private TextField pesquisaPg;

    @FXML
    private Button upload_image;

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
    void uploadImageCA(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha a sua tumografia");

        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");

        // Add os dois filtros pro file chooser
        fileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter);

        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null){
            Image image1 = new Image("selectedFile");

            ImageView ip = new ImageView(image1);
        }
    }

    @FXML
    void diagnoticarCancer(ActionEvent event) {

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
