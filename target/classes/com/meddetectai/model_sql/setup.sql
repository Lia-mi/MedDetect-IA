CREATE DATABASE IF NOT EXISTS medDetectIA;
USE medDetectIA;

CREATE TABLE IF NOT EXISTS Paciente (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(100),
    telefone INT,
    data_nascimento DATE,
    doctor_id INT,
    FOREIGN KEY (doctor_id) REFERENCES Doctor(id)
);

CREATE TABLE IF NOT EXISTS Diagnostico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cpf_paciente VARCHAR(11) UNIQUE,
    tipo ENUM('TUMOR', 'ALZHEIMER'),
    imagem BLOB,
    resultado TEXT,
    FOREIGN KEY (cpf_paciente) REFERENCES Paciente(cpf)
);

CREATE TABLE IF NOT EXISTS Doctor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    password_hash VARCHAR(255) NOT NULL,
    nome VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(15),
    registro_profissional VARCHAR(20) UNIQUE, -- Example for a medical license
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


DELIMITER //

CREATE PROCEDURE deletePaciente(IN cpfPaciente VARCHAR(11))
BEGIN
    DELETE FROM Diagnostico WHERE cpf_paciente = cpfPaciente;
    DELETE FROM Paciente WHERE cpf = cpfPaciente;
END //

DELIMITER ;

