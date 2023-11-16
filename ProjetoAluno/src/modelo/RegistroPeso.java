package modelo;

import java.time.LocalDate;

public class RegistroPeso {
	
	private String cpfAluno;
    private LocalDate data;
    private double peso;

    public RegistroPeso(String cpfAluno, LocalDate data, double peso) {
        this.cpfAluno = cpfAluno;
        this.data = data;
        this.peso = peso;
    }

	public String getCpfAluno() {
		return cpfAluno;
	}

	public void setCpfAluno(String cpfAluno) {
		this.cpfAluno = cpfAluno;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}
    
    
}
