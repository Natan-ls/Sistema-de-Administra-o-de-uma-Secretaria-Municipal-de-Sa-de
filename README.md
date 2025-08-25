# Sistema de Administração de uma Secretaria Municipal de Saúde

O **Sistema de Administração da Secretaria Municipal de Saúde** é uma ferramenta para modernizar e informatizar os serviços prestados à população, com foco na gestão de pacientes, atendimentos médicos e controle de medicamentos.

---

##  ​ Documentação

A documentação completa do projeto está disponível [aqui](https://docs.google.com/document/d/1k_4-9Hlp13zN1V7pjtICOaSWSPFn-GHsY7ihz2S0kUE/edit?tab=t.0). Consulte para entender os requisitos, arquitetura e funcionamento de cada módulo.

---

##  Configuração do Banco de Dados

### Pré-requisitos
- MySQL instalado no Linux.
- Acesso como root no MySQL.

### Passos para criar o banco de dados:

```bash
# 1. Acesse o MySQL como root
mysql -u root -p

# 2. No prompt do MySQL, execute:
CREATE DATABASE dbsistemasaude CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'Admin'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON dbsistemasaude.* TO 'Admin'@'localhost';
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'Admin'@'localhost';
SOURCE ~/sistema_de_saude/sql/popularBanco.sql;
EXIT;

#3. Compile o projeto e baixe dependências
mvn clean install

# 4. Execute a aplicação JavaFX
mvn javafx:run