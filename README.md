

# Projeto de Controle de Alunos e Histórico de Peso

Este projeto foi desenvolvido em Java, utilizando o Sistema Gerenciador de Banco de Dados MySQL para criar um sistema de controle de alunos e seus históricos de peso. O projeto consiste em duas classes principais: `Aluno` e `HistoricoPeso`.

## Classe Aluno

A classe `Aluno` é responsável por realizar as operações CRUD (Create, Read, Update, Delete) para os alunos. Cada aluno é caracterizado pelos seguintes atributos: CPF, nome, data de nascimento, peso e altura. A classe `Aluno` possui os seguintes métodos:

- **Inserir**: Adiciona um novo aluno ao banco de dados.
- **Excluir**: Remove um aluno existente do banco de dados.
- **Atualizar**: Modifica as informações de um aluno no banco de dados.
- **Consultar**: Recupera informações sobre um aluno a partir do CPF.

Além disso, a classe possui métodos getters, setters e um construtor para facilitar a manipulação dos dados.

## Classe HistoricoPeso

A classe `HistoricoPeso` gerencia o histórico de pesos dos alunos. Cada registro inclui a data em que o peso foi registrado e o próprio peso. Os métodos CRUD nesta classe permitem a manipulação desses registros. Destacamos os seguintes pontos:

- **Inserir**: Adiciona um novo registro de peso ao histórico do aluno.
- **Excluir**: Remove um registro de peso específico.
- **Atualizar**: Modifica as informações de um registro de peso existente.
- **Consultar**: Recupera informações sobre o histórico de peso de um aluno.

Além disso, a classe contém um método para calcular o Índice de Massa Corporal (IMC) com base nos registros de peso. O resultado é armazenado em um arquivo de texto junto com a data do cálculo, CPF, nome do aluno e a interpretação do resultado do IMC. Importante ressaltar que o arquivo não é sobrescrito com novas inserções, garantindo o histórico completo.

## Telas de Interatividade

O projeto inclui telas interativas para permitir que o usuário interaja facilmente com o sistema. Estas telas facilitam a entrada de dados e exibição de informações sobre os alunos e seus históricos de peso.

Esse projeto proporciona uma solução eficiente para o controle e gerenciamento de alunos e seus históricos de peso, facilitando a manutenção e consulta de dados importantes.
