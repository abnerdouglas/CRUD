package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.AlunoDAO;
import modelo.Aluno;
import GUI.HistoricoPesoGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AlunoGUI {
    private AlunoDAO alunoDAO;

    private JFrame frame;
    private JTextArea textArea;
    private JTextField nomeField, cpfField, dataNascimentoField, pesoField, alturaField;
    private JPanel tablePanel;
    
    public AlunoGUI() {
        alunoDAO = new AlunoDAO();
        
        frame = new JFrame("Cadastro Aluno");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        inputPanel.add(nomeField);

        inputPanel.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        inputPanel.add(cpfField);

        inputPanel.add(new JLabel("Data de Nascimento (DD/MM/YYYY):"));
        dataNascimentoField = new JTextField();
        inputPanel.add(dataNascimentoField);

        inputPanel.add(new JLabel("Peso:"));
        pesoField = new JTextField();
        inputPanel.add(pesoField);

        inputPanel.add(new JLabel("Altura:"));
        alturaField = new JTextField();
        inputPanel.add(alturaField);

        frame.add(inputPanel, BorderLayout.NORTH);

        JButton addButton = new JButton("Cadastrar");
        JButton viewAllButton = new JButton("Consultar");
        JButton updateButton = new JButton("Atualizar");
        JButton deleteButton = new JButton("Deletar");
        JButton limpaTela = new JButton("Limpar");
        JButton historicoPeso = new JButton("Historico de Peso");
        
        addButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	
				if ((nomeField.getText().isEmpty() || cpfField.getText().isEmpty()
						|| dataNascimentoField.getText().isEmpty() || pesoField.getText().isEmpty()
						|| alturaField.getText().isEmpty())) {
					JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
				} else {

					String nome = nomeField.getText();
					String dataNascimentoTexto = dataNascimentoField.getText();
					LocalDate dataNascimento = LocalDate.parse(dataNascimentoTexto,
					DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					double peso = Double.parseDouble(pesoField.getText());
					double altura = Double.parseDouble(alturaField.getText());
					String cpf = cpfField.getText();

					Aluno aluno = new Aluno(nome, cpf, dataNascimento, peso, altura);

					alunoDAO.adicionar(aluno);
					JOptionPane.showMessageDialog(null, "Aluno " + nomeField.getText() + " inserido com sucesso! ");
				}
			}
        });

        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Aluno> alunos = alunoDAO.consultar();
                displayAlunos(alunos);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	
				if ((nomeField.getText().isEmpty() || cpfField.getText().isEmpty()
						|| dataNascimentoField.getText().isEmpty() || pesoField.getText().isEmpty()
						|| alturaField.getText().isEmpty())) {
					JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
				} else {

					String nome = nomeField.getText();
					String dataNascimentoTexto = dataNascimentoField.getText();
					LocalDate dataNascimento = LocalDate.parse(dataNascimentoTexto,
					DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					double peso = Double.parseDouble(pesoField.getText());
					double altura = Double.parseDouble(alturaField.getText());
					String cpf = cpfField.getText();

					Aluno aluno = new Aluno(nome, cpf, dataNascimento, peso, altura);

					alunoDAO.atualizar(aluno);

					JOptionPane.showMessageDialog(null, "Aluno " + nomeField.getText() + " alterado com sucesso! ");
				}
			}
		});

        deleteButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
					String cpf = cpfField.getText();
					alunoDAO.excluir(cpf);
					clearFields();
					displayMessage("Aluno excluído com sucesso!");
				}
			
		});
        
        limpaTela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	clearFields();
            }
        });
        
        historicoPeso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	HistoricoPesoGUI historicoPesoGUI = new HistoricoPesoGUI();
            	historicoPesoGUI.show(); 
            	frame.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(viewAllButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(limpaTela);
        buttonPanel.add(historicoPeso);
        
        frame.add(buttonPanel, BorderLayout.SOUTH);

        tablePanel = new JPanel(new BorderLayout());
        frame.add(tablePanel, BorderLayout.CENTER);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        frame.setVisible(true);
    }
    

    private Aluno criarAlunoFromFields() {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        LocalDate dataNascimento = LocalDate.parse(dataNascimentoField.getText());
        double peso = Double.parseDouble(pesoField.getText());
        double altura = Double.parseDouble(alturaField.getText());

        return new Aluno(nome, cpf, dataNascimento, peso, altura);
    }
    
    private void displayAlunos(List<Aluno> alunos) {
        String[] columns = {"Nome", "CPF", "Data de Nascimento", "Peso", "Altura"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Aluno aluno : alunos) {
            Object[] row = {
                    aluno.getNome(),
                    aluno.getCpf(),
                    aluno.getDataNascimento(),
                    aluno.getPeso(),
                    aluno.getAltura()
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        tablePanel.removeAll();
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.revalidate();
        tablePanel.repaint();
    }

    private void clearFields() {
        nomeField.setText("");
        cpfField.setText("");
        dataNascimentoField.setText("");
        pesoField.setText("");
        alturaField.setText("");
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AlunoGUI();
            }
        });
    }
}
