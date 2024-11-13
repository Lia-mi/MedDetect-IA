import java.time.LocalDate;

public class Paciente {
    private String nome;
    private String cpf;
    private int telefone;
    private LocalDate data_Nascimento;
 
    public Paciente(String nome, String cpf, int telefone, LocalDate data_Nascimento){
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.data_Nascimento = data_Nascimento;
    }
}
