package com.meddetectai.main;

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
}
