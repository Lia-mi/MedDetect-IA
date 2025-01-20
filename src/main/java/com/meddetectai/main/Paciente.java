package com.meddetectai.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Paciente {

    private String nome;
    private String cpf;
    private int telefone;
    private LocalDate data_Nascimento;
    private Diagnostico diagnostico;
    private static volatile Paciente currentPaciente;

    public Paciente() {
    }

    public Paciente(String nome, String cpf, int telefone, LocalDate data_Nascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.data_Nascimento = data_Nascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public LocalDate getData_Nascimento() {
        return data_Nascimento;
    }

    public void setData_Nascimento(LocalDate data_Nascimento) {
        this.data_Nascimento = data_Nascimento;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public static Paciente getCurrentPaciente() {
        Paciente instance = currentPaciente;
        if(instance == null){
            synchronized (Paciente.class){
                if(currentPaciente == null){
                    currentPaciente = new Paciente();
                }
            }
        }
        return currentPaciente;
    }

    public static void insertDiagnostico(Diagnostico diagnostico, String cpfPaciente) {
        try (Connection conn = DriverManager.getConnection(MySQL.getUrl(), MySQL.getUser(), MySQL.getPassword())) {
            String sql = "INSERT INTO Diagnostico (cpf_paciente, tipo, imagem, resultado) VALUES (?, ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE tipo = VALUES(tipo), imagem = VALUES(imagem), resultado = VALUES(resultado)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cpfPaciente);
            ps.setString(2, diagnostico.getTipo().name());
            ps.setBytes(3, diagnostico.getImagem());
            ps.setString(4, diagnostico.getResultado());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.getNome(); 
    }
}
