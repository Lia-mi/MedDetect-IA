package com.meddetectai.controllers;

// NOVO DIAGNOSTICO DE CANCER

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import ai.djl.inference.Predictor;
import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.util.NDImageUtils;
import ai.djl.translate.Batchifier;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ai.djl.modality.cv.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class novoDiagCController {

    String simpleMlp;

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
    private ImageView imageView;

    private File selectedImageFile;

    private Stage stage;
    private Scene scene;
    private Parent root;
    

    @FXML
    void abrePesc(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/layout_cadPac.fxml")); // Carrega a próxima tela de cadastro de paciente
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
            Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/seusDiagnosticos.fxml")); // Carrega a tela dos diagnosticos feitos.
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }

    @FXML
    void uploadImageC(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha a sua tomografia");

        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");

        // Add filters to the file chooser
        fileChooser.getExtensionFilters().addAll(pngFilter);

        selectedImageFile = fileChooser.showOpenDialog(null);
        if (selectedImageFile != null) {
            // Load and display the selected image
            javafx.scene.image.Image image = new javafx.scene.image.Image(selectedImageFile.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
        }
    }



    @FXML
    public void diagnosticar_Cancer(ActionEvent event) throws MalformedModelException {
        if (selectedImageFile == null) {
            showAlert("Error", "No image file selected.", AlertType.ERROR);
            return;
        }
    
        String modelDir = "src/main/resources/com/meddetectai/models/model_cancer";
        String imagePath = selectedImageFile.getAbsolutePath();
    
        System.out.println("Model Directory: " + modelDir);
        System.out.println("Image Path: " + imagePath);
    
        try (Model model = Model.newInstance("alzheimer-classifier")) {
            // Load the model from the directory
            model.load(Paths.get(modelDir));
    
            Translator<Image, Classifications> translator = new Translator<>() {
                
                private static final List<String> CLASSES = Arrays.asList(
                    "Tumor Pituitário",   
                    "Tumor Meningioma",    
                    "Sem Tumor",           
                    "Tumor Glioma"         
                );


                @Override
                public NDList processInput(TranslatorContext ctx, Image input) {
                    NDManager manager = ctx.getNDManager();
                    NDArray array = input.toNDArray(manager, Image.Flag.COLOR);
                    array = NDImageUtils.resize(array, 128).div(255.0f);
                    return new NDList(array);
                }

                @Override
                public Classifications processOutput(TranslatorContext ctx, NDList list) {
                    NDArray probabilities = list.singletonOrThrow();
                    return new Classifications(CLASSES, probabilities);
                }

                @Override
                public Batchifier getBatchifier() {
                    return Batchifier.STACK;
                }
            };
    
            try (Predictor<Image, Classifications> predictor = model.newPredictor(translator)) {
                Image image = ImageFactory.getInstance().fromFile(Paths.get(imagePath));
    
                Classifications result = predictor.predict(image);
                System.out.println("Prediction: " + result);
                showAlert("Diagnosis Result", result.best().getClassName(), AlertType.INFORMATION);
            }
    
        } catch (IOException | TranslateException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to diagnose Alzheimer: " + e.getMessage(), AlertType.ERROR);
        }
    } 

    
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    

    @FXML
    void voltarInicio(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/home.fxml")); // Carrega atela de inicio
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();   
        }
    }
}
