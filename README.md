# Sistema de Administração da Secretaria Municipal de Saúde

O **Sistema de Administração da Secretaria Municipal de Saúde** é uma ferramenta para modernizar e informatizar os serviços prestados à população, com foco na gestão de pacientes, atendimentos médicos e controle de medicamentos.

---

## Documentação

A documentação completa do projeto está disponível [aqui](https://docs.google.com/document/d/1k_4-9Hlp13zN1V7pjtICOaSWSPFn-GHsY7ihz2S0kUE/edit?tab=t.0). Consulte para entender os requisitos, arquitetura e funcionamento de cada módulo.

---

## Pré-requisitos

Antes de rodar o sistema, certifique-se de ter instalado:

* **MySQL** no Linux.
* **Maven** para compilar e executar o projeto JavaFX.
* Acesso **root** ao MySQL.

---

## Configuração do Banco de Dados

### 1. Acessar o MySQL como root

```bash
sudo mysql
```

### 2. Criar o banco de dados e o usuário

No prompt do MySQL, execute:

```sql
-- Cria o banco de dados
CREATE DATABASE dbsistemasaude CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Cria o usuário 'admin' com senha 'admin'
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';

-- Concede todos os privilégios no banco 'dbsistemasaude' ao usuário 'admin'
GRANT ALL PRIVILEGES ON dbsistemasaude.* TO 'admin'@'localhost';

-- Aplica as alterações
FLUSH PRIVILEGES;

-- Opcional: verifica os privilégios do usuário
SHOW GRANTS FOR 'admin'@'localhost';
```

### 3. Sair do MySQL

```sql
EXIT;
```

### 4. Importar o backup do banco de dados

O backup do banco está localizado na pasta `sql` do projeto. Execute:

```bash
mysql -u admin -p dbsistemasaude < sql/dbsistemasaude.sql
```

Digite a senha do usuário `admin` quando solicitado.

---

## Configuração do Projeto JavaFX

### 5. Compilar o projeto e baixar dependências

No terminal, na raiz do projeto, execute:

```bash
mvn clean install
```

### 6. Executar a aplicação

```bash
mvn javafx:run
```

---
### 7. Dados de teste

Os logins, senhas e números de protocolos para testes estão disponíveis no arquivo:

```bash
sql/dados.txt
```

---

## Observações

* Todos os comandos devem ser executados a partir da raiz do projeto.
* Certifique-se de que o MySQL está rodando antes de importar o banco ou iniciar a aplicação.
* O usuário criado (`admin`) possui todos os privilégios no banco `dbsistemasaude`.
