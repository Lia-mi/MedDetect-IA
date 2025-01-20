package com.meddetectai.main;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PacienteModel {

    public static void insertDiagnostico(Diagnostico diagnostico, String cpfPaciente) {
        try (Connection conn = DriverManager.getConnection(MySQL.getUrl(), MySQL.getUser(), MySQL.getPassword())) {
            String sql = "INSERT INTO Diagnostico (cpf_paciente, tipo, imagem, resultado) VALUES (?, ?, ?, ?)";
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

    // ...existing code...
}
