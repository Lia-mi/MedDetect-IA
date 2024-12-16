import javafx.scene.image.Image;

public class Diagnostico {
    private Item item;
    private String imagem;
    private String resultado;

    public Diagnostico(Item item, String imagem){
        this.item = item;
        this.imagem = imagem;
        this.resultado = gerarDiagnostico();
    }

    public Item getItem(){
        return item;
    }

    public String getImagem(){
        return imagem;
    }

    public String getResultado() {
        return resultado;
    }
}
