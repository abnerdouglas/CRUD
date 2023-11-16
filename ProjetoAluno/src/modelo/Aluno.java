package modelo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Aluno {

	private Integer id;
	private String nome;
	private String cpf;
	private LocalDate dataNascimento;
	private double peso;
	private double altura;

	
	public Aluno(String nome, String cpf, LocalDate dataNascimento, double peso, double altura) {
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.peso = peso;
		this.altura = altura;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}
	
	@Override
	public String toString() {
	  // TODO Auto-generated method stub
	  return getNome();
	}
}
