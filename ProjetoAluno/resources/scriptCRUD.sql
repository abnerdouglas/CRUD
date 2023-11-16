create database academia;

use academia;

create table aluno
(id int primary key auto_increment,
nome varchar(100) not null,
cpf varchar(15) unique not null,
dataNascimento date not null,
peso double not null,
altura double not null);

create table historico(
id int auto_increment primary key,
cpfAluno varchar(15),
dataCadastro date,
peso double not null);

select * from aluno;
select * from historico;