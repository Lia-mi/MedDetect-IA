package com.meddetectai.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.transform.Normalize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.translator.ImageClassificationTranslator;
import ai.djl.modality.cv.util.NDImageUtils;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.Batchifier;
import ai.djl.translate.Pipeline;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import ai.djl.translate.TranslatorFactory;
import ai.djl.translate.TranslatorOptions;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ai.djl.modality.cv.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class novoDiagAController {

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
            Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/layout_cadPac.fxml")); // Carrega a próxima tela a carteira.
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
            Parent root = FXMLLoader.load(getClass().getResource("/com/meddetectai/fxml/seusDiagnosticos.fxml")); // Carrega a próxima tela a carteira.
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
    public void diagnosticar_Alzheimer(ActionEvent event) throws MalformedModelException {
        if (selectedImageFile == null) {
            showAlert("Error", "No image file selected.", AlertType.ERROR);
            return;
        }
    
        String modelDir = "src/main/resources/com/meddetectai/models/model_alzheimer";
        String imagePath = selectedImageFile.getAbsolutePath();
    
        System.out.println("Model Directory: " + modelDir);
        System.out.println("Image Path: " + imagePath);
    
        try (Model model = Model.newInstance("alzheimer-classifier")) {
            // Load the model from the directory
            model.load(Paths.get(modelDir));
    
            Translator<Image, Classifications> translator = new Translator<>() {

                private static final List<String> CLASSES = Arrays.asList("Não há Alzheimer", "Com  Alzheimer");
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
