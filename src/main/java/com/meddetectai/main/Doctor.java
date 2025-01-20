package com.meddetectai.main;

import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Doctor {
    private int id;
    private String passwordHash;
    private String nome;
    private String email;
    private String telefone;
    private String registroProfissional;
    private static Doctor currentDoctor;

    public Doctor(int id, String nome, String email, String telefone, String registroProfissional) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.registroProfissional = registroProfissional;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getRegistroProfissional() { return registroProfissional; }

    public static Doctor getCurrentDoctor() {
        return currentDoctor;
    }

    public static void setCurrentDoctor(Doctor doctor) {
        currentDoctor = doctor;
    }

    public List<Paciente> getPacientes() {
        return MySQL.getPacientesByDoctorId(this.id);
    }

    public static Doctor authenticate(String email, String password) {
        String hashedPassword = hashPassword(password);
        return MySQL.authenticateDoctor(email, hashedPassword);
    }

    public static boolean registerNewDoctor(String password, String nome, 
                                          String email, String telefone, String registroProfissional) {
        String hashedPassword = hashPassword(password);
        return MySQL.registerDoctor(hashedPassword, nome, email, telefone, registroProfissional);
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
