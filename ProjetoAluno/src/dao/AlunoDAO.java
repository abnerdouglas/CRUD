package dao;

import factory.ConnectionFactory;
import modelo.Aluno;
import java.sql.*;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
	private Connection connection;
	   
    public AlunoDAO(){ 
        this.connection = new ConnectionFactory().getConnection();
    } 
    
    // Create
    public void adicionar(Aluno aluno){ 
        String sql = "INSERT INTO aluno(nome, cpf, dataNascimento, peso, altura) VALUES(?, ?, ?, ?, ?)";
        try { 
            PreparedStatement stmt = connection.prepareStatement(sql);
           
            stmt.setString(1, aluno.getNome());
            stmt.setString(2 ,aluno.getCpf());
            stmt.setDate(3, Date.valueOf(aluno.getDataNascimento()));
            stmt.setDouble(4, aluno.getPeso());
            stmt.setDouble(5, aluno.getAltura());
            stmt.execute();
        } 
        
        
        catch (SQLException u) { 
            throw new RuntimeException(u);
        } 
    } 
    
    // Read
    public List<Aluno> consultar() {
        List<Aluno> alunos = new ArrayList<>();
        try {
            String query = "SELECT * FROM aluno";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    Aluno aluno = criarAlunoDoResultSet(resultSet);
                    alunos.add(aluno);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter todos os alunos.", e);
        }
        return alunos;
    }
    
    // Update
    public void atualizar(Aluno aluno) {
        try {
            String query = "UPDATE aluno SET nome = ?, dataNascimento = ?, peso = ?, altura = ? WHERE cpf = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, aluno.getNome());
                preparedStatement.setDate(2, java.sql.Date.valueOf(aluno.getDataNascimento()));
                preparedStatement.setDouble(3, aluno.getPeso());
                preparedStatement.setDouble(4, aluno.getAltura());
                preparedStatement.setString(5, aluno.getCpf());

                int rowsUpdated = preparedStatement.executeUpdate();
                
            } 
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aluno.", e);
        }
    }

    // Delete
    public void excluir(String cpf) {
        try {
            String query = "DELETE FROM aluno WHERE cpf = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, cpf);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir aluno.", e);
        }
    }
    
    // Método para encontrar o nome no calculo do IMC
    public Aluno obterAlunoPorCPF(String cpf) {
        String sql = "SELECT * FROM aluno WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String nome = resultSet.getString("nome");
                    LocalDate dataNascimento = resultSet.getDate("dataNascimento").toLocalDate();
                    double peso = resultSet.getDouble("peso");
                    double altura = resultSet.getDouble("altura");

                    return new Aluno(nome, cpf, dataNascimento, peso, altura);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Trate adequadamente a exceção
        }
        return null; // Retorna null se não encontrar o aluno com o CPF fornecido
    }
 
    private Aluno criarAlunoDoResultSet(ResultSet resultSet) throws SQLException {
        String nome = resultSet.getString("nome");
        String cpf = resultSet.getString("cpf");
        LocalDate dataNascimento = resultSet.getDate("dataNascimento").toLocalDate();
        double peso = resultSet.getDouble("peso");
        double altura = resultSet.getDouble("altura");

        return new Aluno(nome, cpf, dataNascimento, peso, altura);
    }
	
}
