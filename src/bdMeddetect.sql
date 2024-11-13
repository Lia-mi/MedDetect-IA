create database meddetect;

use meddetect;

create table paciente
(
	paciente_id int primary key auto_increment,
    paciente_nome varchar(128) not null,
    paciente_nasc date not null,
    paciente_cpf varchar(64) not null,
    paciente_tel int not null
);

create table diagnostico
(
	diag_id int primary key auto_increment,
	diag_imgURL varchar(128) not null,
	diagnostico_IA text not null,
	tipo varchar(80) not null,
	paciente_id int,
    foreign key(paciente_id) references paciente(paciente_id)
);