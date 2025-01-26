package com.meddetectai.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Diagnostico {
    private ModelType tipo;
    private byte[] imagem;
    private String resultado;

    public Diagnostico(ModelType tipo, byte[] imagem, String resultado) {
        this.tipo = tipo;
        this.imagem = imagem;
        this.resultado = resultado;
    }

    public Diagnostico(ModelType tipo, String imagePath, String resultado) {
        this(tipo, loadFileAsBytes(imagePath), resultado);
    }

    private static byte[] loadFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public ModelType getTipo() {
        return tipo;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
