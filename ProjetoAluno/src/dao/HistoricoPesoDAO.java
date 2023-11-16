package dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import modelo.Aluno;
import modelo.RegistroPeso;

public class HistoricoPesoDAO {
	
private Connection connection;
    
    public HistoricoPesoDAO(){ 
        this.connection = new ConnectionFactory().getConnection();
    } 
    
    // Create
    public void adicionar(RegistroPeso peso) {
        String sql = "INSERT INTO historico (dataCadastro, peso, cpfAluno) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(peso.getData()));
            stmt.setDouble(2, peso.getPeso());
            stmt.setString(3, peso.getCpfAluno());
            stmt.execute();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }
    
    // Read
    public List<RegistroPeso> consultar() {
        List<RegistroPeso> historico = new ArrayList<>();
        try {
            String query = "SELECT * FROM historico";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    RegistroPeso registro = criarHistoricoDoResultSet(resultSet);
                    historico.add(registro);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter todos os registros de peso.", e);
        }
        return historico;
    }
    
    // Update
    public void atualizar(RegistroPeso peso) {
        try {
            String query = "UPDATE historico SET dataCadastro = ?, peso = ? WHERE cpfAluno = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setDate(1, Date.valueOf(peso.getData()));
                stmt.setDouble(2, peso.getPeso());
                stmt.setString(3, peso.getCpfAluno());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar registro de peso.", e);
        }
    }

    // Delete
    public void excluir(String cpfAluno) {
        try {
            String query = "DELETE FROM historico WHERE cpfAluno = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, cpfAluno);
                stmt.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir registro de peso.", e);
        }
    }
 
    private RegistroPeso criarHistoricoDoResultSet(ResultSet resultSet) throws SQLException {
        String cpfAluno = resultSet.getString("cpfAluno");
        LocalDate data = resultSet.getDate("dataCadastro").toLocalDate();
        double peso = resultSet.getDouble("peso");

        return new RegistroPeso(cpfAluno, data, peso);
    }
    
    public void calcularIMC(RegistroPeso registroPeso, Aluno aluno) {
        double altura = aluno.getAltura();
        double peso = registroPeso.getPeso();
        LocalDate dataCalculo = registroPeso.getData();
        double imc = peso / (altura * altura);

        String interpretacao = interpretarIMC(imc);
        String dadosIMC = String.format("Data do cálculo: %s%nCPF: %s%nNome: %s%nIMC: %.2f%nInterpretação: %s%n%n",
        		dataCalculo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), aluno.getCpf(), aluno.getNome(), imc, interpretacao);

        escreverArquivo(dadosIMC);
    }

    private String interpretarIMC(double imc) {
        
        if (imc < 18.5) {
            return "Abaixo do peso";
        } else if (imc >= 18.5 && imc < 25) {
            return "Peso normal";
        } else if (imc >= 25 && imc < 30) {
            return "Sobrepeso";
        } else {
            return "Obesidade";
        }
    }

    private void escreverArquivo(String dadosIMC) {
        String nomeArquivo = "registroIMC.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
            writer.write(dadosIMC);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            // Tratar a exceção
        }
    }
	
}

