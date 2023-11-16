package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.AlunoDAO;
import dao.HistoricoPesoDAO;
import modelo.Aluno;
import modelo.RegistroPeso;
import GUI.AlunoGUI;

import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoricoPesoGUI {
	
	private AlunoDAO alunoDAO;
    private HistoricoPesoDAO historicoPesoDAO;
    private JTable table;
    private DefaultTableModel tableModel;

    private JFrame frame;
    private JTextField cpfField, dataField, pesoField;

    public HistoricoPesoGUI() {
    	alunoDAO = new AlunoDAO();
        historicoPesoDAO = new HistoricoPesoDAO();

        frame = new JFrame("Histórico de Peso");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        cpfField = new JTextField();
        dataField = new JTextField();
        pesoField = new JTextField();
        
        JPanel textFieldPanel = new JPanel(new GridLayout(3, 2, 5, 5)); // GridLayout para os campos de texto
        textFieldPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // Margem à esquerda

        textFieldPanel.add(new JLabel("CPF do Aluno:", SwingConstants.LEFT));
        textFieldPanel.add(cpfField);

        textFieldPanel.add(new JLabel("Data (DD/MM/AAAA):", SwingConstants.LEFT));
        textFieldPanel.add(dataField);

        textFieldPanel.add(new JLabel("Peso:", SwingConstants.LEFT));
        textFieldPanel.add(pesoField);


        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((cpfField.getText().isEmpty()|| dataField.getText().isEmpty() || pesoField.getText().isEmpty())) {
					JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
				} else {
					adicionarRegistro();
				}
			}
		});

        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if ((cpfField.getText().isEmpty()|| dataField.getText().isEmpty() || pesoField.getText().isEmpty())) {
					JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
				} else {
					atualizarRegistro();
				}
            }
        });

        JButton deleteButton = new JButton("Excluir");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				excluirRegistro();
				
            }
        });

        JButton consultarButton = new JButton("Consultar");
        consultarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consultarRegistros();
            }
        });
        
        JButton calcularIMCButton = new JButton("Calcular IMC");
        calcularIMCButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcularIMC(); 
            }
        });
        
        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); 
                AlunoGUI alunoGUI = new AlunoGUI();
                alunoGUI.show(); 
            }
        });

       
        
        // Tabela para exibir os registros
        String[] columns = {"CPF do Aluno", "Data", "Peso"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(consultarButton);
        buttonPanel.add(calcularIMCButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // Adicionando o painel das caixas de texto, dos botões, e da tabela ao painel principal
        panel.add(textFieldPanel);
        panel.add(buttonPanel);
        panel.add(scrollPane);

        frame.getContentPane().add(panel);
        frame.setVisible(true);

    }

    private void adicionarRegistro() {
        String cpf = cpfField.getText();
        String dataTexto = dataField.getText();
		LocalDate data = LocalDate.parse(dataTexto,
		DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        double peso = Double.parseDouble(pesoField.getText());

        historicoPesoDAO.adicionar(new RegistroPeso(cpf, data, peso));
        JOptionPane.showMessageDialog(frame, "Registro de peso adicionado com sucesso!");
    }

    private void atualizarRegistro() {
    	String cpf = cpfField.getText();
    	String dataTexto = dataField.getText();
		LocalDate data = LocalDate.parse(dataTexto,
		DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        double peso = Double.parseDouble(pesoField.getText());

        historicoPesoDAO.atualizar(new RegistroPeso(cpf, data, peso));
        JOptionPane.showMessageDialog(frame, "Registro de peso atualizado com sucesso!");
    }

    private void excluirRegistro() {
        String cpf = cpfField.getText();
        historicoPesoDAO.excluir(cpf);
        JOptionPane.showMessageDialog(frame, "Registro de peso excluído com sucesso!");
    }

    private void consultarRegistros() {
    	tableModel.setRowCount(0);

        List<RegistroPeso> registros = historicoPesoDAO.consultar();
        for (RegistroPeso registro : registros) {
            Object[] row = {registro.getCpfAluno(), registro.getData(), registro.getPeso()};
            tableModel.addRow(row);
        }
    }
    
    private void calcularIMC() {
    	
    	String nomeArquivo;
        String cpf = cpfField.getText();
        String dataTexto = dataField.getText();
		LocalDate data = LocalDate.parse(dataTexto,
		DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        double peso = Double.parseDouble(pesoField.getText());
        
        // Obter as informações do aluno a partir do CPF
        Aluno aluno = alunoDAO.obterAlunoPorCPF(cpf); 

        RegistroPeso registroPeso = new RegistroPeso(cpf, data, peso);
        nomeArquivo = historicoPesoDAO.calcularIMC(registroPeso, aluno);
        
        try {
            File file = new File(nomeArquivo);
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (IOException e) { 
            e.printStackTrace(); // Tratar a exceção adequadamente
        }
    }
    
    
    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HistoricoPesoGUI();
            }
        });
    }
}
