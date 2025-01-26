package com.meddetectai.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQL {
    private static String url;
    private static String user;
    private static String password;
    
    static {
        try {
            Properties props = new Properties();
            String configPath = "config.properties";
            props.load(new FileInputStream(configPath));
            
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
        } catch (IOException e) {
            System.err.println("Error loading database configuration: " + e.getMessage());
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }

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
             ResultSet rs = stmt.executeQuery("SELECT nome, cpf, telefone, data_nascimento, doctor_id FROM Paciente")) {

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

    public static void deletePaciente(String cpf) {
        String query = "CALL deletePaciente(?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Doctor authenticateDoctor(String email, String hashedPassword) {
        String query = "SELECT * FROM Doctor WHERE email = ? AND password_hash = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, hashedPassword);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Doctor(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("registro_profissional")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Paciente> getPacientesByDoctorId(int doctorId) {
        List<Paciente> pacientes = new ArrayList<>();
        String query = "SELECT * FROM Paciente WHERE doctor_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Paciente paciente = new Paciente(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getInt("telefone"),
                        rs.getDate("data_nascimento").toLocalDate()
                    );
                    pacientes.add(paciente);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }

    public static void assignPacienteToDoctor(String cpf, int doctorId) {
        String query = "UPDATE Paciente SET doctor_id = ? WHERE cpf = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.setString(2, cpf);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean registerDoctor(String hashedPassword, String nome, 
                                       String email, String telefone, String registroProfissional) {
        String query = "INSERT INTO Doctor (password_hash, nome, email, telefone, registro_profissional) " +
                      "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, hashedPassword);
            stmt.setString(2, nome);
            stmt.setString(3, email);
            stmt.setString(4, telefone);
            stmt.setString(5, registroProfissional);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePaciente(Paciente paciente) {
        String query = "UPDATE Paciente SET nome = ?, telefone = ?, data_nascimento = ? WHERE cpf = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, paciente.getNome());
            stmt.setInt(2, paciente.getTelefone());
            stmt.setDate(3, java.sql.Date.valueOf(paciente.getData_Nascimento()));
            stmt.setString(4, paciente.getCpf());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void insertDiagnostico(Diagnostico diagnostico, String cpfPaciente) {
        String sql = "INSERT INTO Diagnostico (cpf_paciente, tipo, imagem, resultado) VALUES (?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE tipo = VALUES(tipo), imagem = VALUES(imagem), resultado = VALUES(resultado)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpfPaciente);
            ps.setString(2, diagnostico.getTipo().name());
            ps.setBytes(3, diagnostico.getImagem());
            ps.setString(4, diagnostico.getResultado());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}