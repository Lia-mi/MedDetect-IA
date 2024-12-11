import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class novoDiagAController {

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

    @FXML
    void abrePesc(ActionEvent event) {

    }

    @FXML
    void mostraDiag(ActionEvent event) {

    }

    private Stage stage;
    @FXML
    void uploadImageC(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
    }

    @FXML
    void voltarInicio(ActionEvent event) {

    }

}
