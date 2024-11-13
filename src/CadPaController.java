import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CadPaController {

    @FXML
    private Button botaoCadPaciente;

    @FXML
    private TextField cpf_Paciente;

    @FXML
    private DatePicker data_Nasc;

    @FXML
    private TextField nome_Paciente;

    @FXML
    private TextField numero_tell;

    @FXML
    void cadastrarPaciente(ActionEvent event) {
        String nome = nome_Paciente.getText();
        String cpf = cpf_Paciente.getText();
        LocalDate nasc = data_Nasc.getValue();
        String tell = numero_tell.getText();
        Integer num_tell = Integer.parseInt(tell);

        Paciente p1 = new Paciente(nome, cpf, num_tell, nasc);


    }

}