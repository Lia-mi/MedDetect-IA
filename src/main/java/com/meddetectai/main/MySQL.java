package com.meddetectai.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQL {
    private static String url = "jdbc:mysql://t2alq.h.filess.io:3307/medDetectIA_topwavetip";
    private static String user = "medDetectIA_topwavetip";
    private static String password = "45e1af78f95a83f5407012be62330d9e24ebad2e";

    public static String getUrl() {
        return url;
    }
    public static String setUrl() {
        return MySQL.url;
    }

    public static String getUser() {
        return user;
    }
    public static void setUser(String user) {
        MySQL.user = user;
    }

    public static String getPassword() {
        return password;
    }
    public static void setPassword(String password) {
        MySQL.password = password;
    }

    public static List<Paciente> getPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nome, cpf, telefone, data_nascimento FROM Paciente")) {

            while (rs.next()) {
                Paciente paciente = new Paciente(
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getInt("telefone"),
                    rs.getDate("data_nascimento").toLocalDate()
                );
                pacientes.add(paciente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pacientes;
    }

    public static Paciente getPacienteByCpf(String cpf) {
        Paciente paciente = null;
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT nome, cpf, telefone, data_nascimento FROM Paciente WHERE cpf = '" + cpf + "'")) {
            if (rs.next()) {
                paciente = new Paciente(
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getInt("telefone"),
                    rs.getDate("data_nascimento").toLocalDate()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paciente;
    }
}