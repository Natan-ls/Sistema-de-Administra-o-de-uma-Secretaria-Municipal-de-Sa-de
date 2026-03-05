# Sistema de Administração da Secretaria Municipal de Saúde

O **Sistema de Administração da Secretaria Municipal de Saúde** é uma aplicação desenvolvida em **Java com JavaFX**, voltada para a informatização de processos administrativos de uma secretaria municipal de saúde.

O sistema permite o gerenciamento de:

- pacientes
- consultas médicas
- medicamentos
- funcionários
- usuários do sistema

O objetivo é **organizar e centralizar as informações do atendimento à população**, facilitando o controle de consultas, medicamentos e protocolos administrativos.

---

# Tecnologias Utilizadas

O projeto utiliza tecnologias comuns no desenvolvimento de aplicações backend Java:

- Java 21
- JavaFX
- Hibernate (JPA)
- PostgreSQL
- Docker
- Maven

A aplicação utiliza o padrão **DAO (Data Access Object)** para organização da camada de persistência.

---

# Documentação

A documentação completa do projeto está disponível no link abaixo:

📄 https://docs.google.com/document/d/1k_4-9Hlp13zN1V7pjtICOaSWSPFn-GHsY7ihz2S0kUE

Ela contém:

- requisitos do sistema
- modelagem de dados
- descrição dos módulos
- arquitetura do projeto

---

# Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

- Java 21
- Maven
- Docker
- Docker Compose

---

# Configuração do Ambiente

O projeto utiliza **variáveis de ambiente** para configurar a conexão com o banco de dados.

Primeiro copie o arquivo de exemplo:

```bash
cp .env.example .env
````

Depois preencha as variáveis no arquivo `.env`.

Exemplo:

```env
POSTGRES_USER=seu_usuario
POSTGRES_PASSWORD=sua_senha
POSTGRES_DB=nome_db
POSTGRES_PORT=5432
POSTGRES_HOST=localhost
```

Essas variáveis são carregadas na aplicação utilizando a biblioteca **dotenv-java**.

---

# Subindo o Banco de Dados com Docker

O banco de dados PostgreSQL é executado em um container Docker.

Na raiz do projeto execute:

```bash
docker compose up -d
```

Esse comando irá:

* baixar a imagem do PostgreSQL
* criar o container
* iniciar o banco automaticamente
* executar o script de inicialização do banco

Para verificar se o container está rodando:

```bash
docker ps
```

---

# Inicialização do Banco

O banco de dados é inicializado automaticamente através do script:

```
init.sql
```

Esse script cria:

* tabelas do sistema
* relacionamentos
* dados iniciais para testes

---

# Compilando o Projeto

Na raiz do projeto execute:

```bash
mvn clean install
```

Isso irá:

* baixar as dependências
* compilar o projeto
* preparar a aplicação para execução

---

# Executando a Aplicação

Para iniciar o sistema:

```bash
mvn javafx:run
```

---

# Estrutura de Configuração do Banco

O projeto utiliza **dotenv** para carregar as variáveis de ambiente no Java.

Arquivos importantes dessa configuração:

```
.env
.env.example
docker-compose.yml
init.sql
persistence.xml
Conexao.java
EntityManagerUtil.java
```

Essa abordagem evita **credenciais hardcoded no código** e facilita a configuração do ambiente.

---

# Dados de Teste

Alguns dados de teste (logins, senhas e protocolos) estão disponíveis no arquivo:

```
sql/dados.txt
```

Eles podem ser utilizados para navegar pelas funcionalidades do sistema.

---

# Funcionalidades do Sistema

O sistema possui módulos para:

* cadastro de pacientes
* agendamento e controle de consultas
* controle de medicamentos
* gerenciamento de funcionários
* administração do sistema

---


# Aprendizados com o Projeto

Durante o desenvolvimento e refatoração do projeto foram trabalhados conceitos como:

* arquitetura em camadas
* DAO Pattern
* integração com banco relacional
* uso de ORM com Hibernate
* configuração via variáveis de ambiente
* containerização com Docker

---

# Observações

* Todos os comandos devem ser executados a partir da **raiz do projeto**.
* Certifique-se de que o **Docker está rodando** antes de iniciar o banco.
* O banco de dados será criado automaticamente ao subir o container.

