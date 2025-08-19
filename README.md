# Sistema de Administração de uma Secretaria Municipal de Saúde

O Sistema de Administração da Secretaria Municipal de Saúde será uma plataforma voltada para a modernização e informatização dos serviços prestados à população, com foco no gerenciamento de pacientes, atendimentos médicos e controle de medicamentos.

---

## 📋 Configuração do Banco de Dados

### Pré-requisitos
- Servidor MySQL instalado
- Acesso de administrador ao MySQL (usuário root)

### 🛠 Comandos para Configuração do Banco

Execute os seguintes comandos em sequência:

```bash
# 1. Acessar o MySQL como root
mysql -u root -p

# 2. Dentro do MySQL, execute:
CREATE DATABASE dbsistemasaude CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER 'Admin'@'localhost' IDENTIFIED BY 'admin';

GRANT ALL PRIVILEGES ON dbsistemasaude.* TO 'Admin'@'localhost';

FLUSH PRIVILEGES;

SHOW GRANTS FOR 'Admin'@'localhost';

EXIT;