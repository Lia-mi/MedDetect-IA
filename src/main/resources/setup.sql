CREATE DATABASE IF NOT EXISTS medDetectIA;
USE medDetectIA;

CREATE TABLE IF NOT EXISTS Paciente (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(100),
    telefone INT,
    data_nascimento DATE
);

CREATE TABLE IF NOT EXISTS Diagnostico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cpf_paciente VARCHAR(11),
    tipo ENUM('TUMOR', 'ALZHEIMER'),
    imagem BLOB,
    resultado TEXT,
    FOREIGN KEY (cpf_paciente) REFERENCES Paciente(cpf)
);
