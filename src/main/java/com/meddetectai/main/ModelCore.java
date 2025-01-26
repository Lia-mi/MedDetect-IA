package com.meddetectai.main;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.io.File;

import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.MalformedModelException;
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
import ai.djl.modality.cv.Image;

public class ModelCore {

    static String modelDir;
    static List<String> classes;

    private static final String MODEL_DIR_ALZHEIMER = "src/main/resources/com/meddetectai/models/model_alzheimer";
    private static final String MODEL_DIR_TUMOR = "src/main/resources/com/meddetectai/models/model_cancer";

    private static final List<String> CLASSES_ALZHEIMER = Arrays.asList(
        "Leve Demência",    
        "Demência Moderada", 
        "Sem Demência",       
        "Demência Muito Leve"
    );

    private static final List<String> CLASSES_TUMOR = Arrays.asList(
        "Tumor Pituitário",   
        "Tumor Meningioma",    
        "Sem Tumor",           
        "Tumor Glioma"
    );

    public static String diagnose(File selectedImageFile, ModelType modelType) throws MalformedModelException {
        if (selectedImageFile == null) {
            showAlert("Erro", "Nenhuma imagem selecionada", AlertType.ERROR);
            return "error";
        }
        switch (modelType) {
            case ALZHEIMER:
                modelDir = MODEL_DIR_ALZHEIMER;
                classes = CLASSES_ALZHEIMER;
                break;
            case TUMOR:
                modelDir = MODEL_DIR_TUMOR;
                classes = CLASSES_TUMOR;
                break;
            default:
                showAlert("Erro", "Tipo de modelo não reconhecido", AlertType.ERROR);
                return "error";
        }

        try (Model model = Model.newInstance("classifier-disease")) {
            model.load(Paths.get(modelDir));
            Translator<Image, Classifications> translator = createTranslator(classes);
            try (Predictor<Image, Classifications> predictor = model.newPredictor(translator)) {
                Image image = ImageFactory.getInstance().fromFile(selectedImageFile.toPath());
                Classifications result = predictor.predict(image);
                showAlert("Diagnosis Result", result.best().getClassName(), AlertType.INFORMATION);

                return result.best().getClassName();

            }
        } catch (IOException | TranslateException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to diagnose: " + e.getMessage(), AlertType.ERROR);
        }
        return "error";
    }

    private static Translator<Image, Classifications> createTranslator(List<String> classes) {
        return new Translator<>() {
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
                return new Classifications(classes, probabilities);
            }

            @Override
            public Batchifier getBatchifier() {
                return Batchifier.STACK;
            }
        };
    }

    private static void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
