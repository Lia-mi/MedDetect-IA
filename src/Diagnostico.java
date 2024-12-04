public class Diagnostico {
    private Item item;
    private String imagem;

    public Diagnostico(Item item, String imagem){
        this.item = item;
        this.imagem = imagem;
    }

    public Item getItem(){
        return item;
    }

    public String getImagem(){
        return imagem;
    }
}
