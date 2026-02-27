-- Script de migração de MySQL para PostgreSQL
-- Banco de dados: dbsistemasaude

-- Desabilitar verificação de chaves estrangeiras temporariamente
SET session_replication_role = 'replica';

-- =====================================================
-- ESTRUTURA DAS TABELAS (CREATE TABLES)
-- =====================================================

-- Tabela Pessoa
DROP TABLE IF EXISTS Pessoa CASCADE;
CREATE TABLE Pessoa (
    id BIGSERIAL PRIMARY KEY,
    cpf VARCHAR(255) UNIQUE,
    dataNascimento TIMESTAMP,
    email VARCHAR(255),
    endereco VARCHAR(255),
    nome VARCHAR(255),
    sexo CHAR(1),
    telefone VARCHAR(255)
);

-- Tabela Funcionario
DROP TABLE IF EXISTS Funcionario CASCADE;
CREATE TABLE Funcionario (
    id BIGSERIAL PRIMARY KEY,
    dataAdimissao TIMESTAMP,
    matricula INTEGER NOT NULL UNIQUE,
    fkPessoa BIGINT UNIQUE REFERENCES Pessoa(id)
);

-- Tabela Paciente
DROP TABLE IF EXISTS Paciente CASCADE;
CREATE TABLE Paciente (
    id BIGSERIAL PRIMARY KEY,
    numeroSus VARCHAR(255) NOT NULL UNIQUE,
    fkPessoa BIGINT UNIQUE REFERENCES Pessoa(id)
);

-- Tabela Administrador
DROP TABLE IF EXISTS Administrador CASCADE;
CREATE TABLE Administrador (
    id BIGINT PRIMARY KEY REFERENCES Funcionario(id),
    setor VARCHAR(255)
);

-- Tabela Farmaceutico
DROP TABLE IF EXISTS Farmaceutico CASCADE;
CREATE TABLE Farmaceutico (
    id BIGINT PRIMARY KEY REFERENCES Funcionario(id),
    crf VARCHAR(255) NOT NULL UNIQUE
);

-- Tabela Medico
DROP TABLE IF EXISTS Medico CASCADE;
CREATE TABLE Medico (
    id BIGINT PRIMARY KEY REFERENCES Funcionario(id),
    crm VARCHAR(255) NOT NULL UNIQUE,
    especialidade VARCHAR(255),
    nomeFantasia VARCHAR(255)
);

-- Tabela Recepcionista
DROP TABLE IF EXISTS Recepcionista CASCADE;
CREATE TABLE Recepcionista (
    id BIGINT PRIMARY KEY REFERENCES Funcionario(id),
    setor VARCHAR(255)
);

-- Tabela UsuarioSistema
DROP TABLE IF EXISTS UsuarioSistema CASCADE;
CREATE TABLE UsuarioSistema (
    id BIGSERIAL PRIMARY KEY,
    ativo BOOLEAN NOT NULL,
    login VARCHAR(255),
    senha VARCHAR(255),
    tipoUser VARCHAR(50) CHECK (tipoUser IN ('MEDICO', 'FARMACEUTICO', 'RECEPCIONISTA', 'ADMINISTRADOR')),
    fkUsuario BIGINT UNIQUE REFERENCES Funcionario(id)
);

-- Tabela Medicamento
DROP TABLE IF EXISTS Medicamento CASCADE;
CREATE TABLE Medicamento (
     id BIGSERIAL PRIMARY KEY,
     nome VARCHAR(255) UNIQUE
);

-- Tabela TipoMedicamento
DROP TABLE IF EXISTS TipoMedicamento CASCADE;
CREATE TABLE TipoMedicamento (
     id BIGSERIAL PRIMARY KEY,
     descricao VARCHAR(255),
     estoqueMinimo INTEGER NOT NULL,
     quantidadeCaixa INTEGER NOT NULL,
     tipo VARCHAR(255),
     unidadeMedida VARCHAR(255),
     fkMedicamento BIGINT NOT NULL REFERENCES Medicamento(id)
);

-- Tabela LoteMedicamento
DROP TABLE IF EXISTS LoteMedicamento CASCADE;
CREATE TABLE LoteMedicamento (
     id BIGSERIAL PRIMARY KEY,
     dataFabricacao TIMESTAMP,
     dataValidade TIMESTAMP,
     quantidadeEntrada INTEGER NOT NULL,
     quantidadeEstoque INTEGER NOT NULL,
     status VARCHAR(20) CHECK (status IN ('VENCIDO', 'DISPONIVEL', 'ESGOTADO')),
     fkTipoMedicamento BIGINT NOT NULL REFERENCES TipoMedicamento(id)
);

-- Tabela Protocolo
DROP TABLE IF EXISTS Protocolo CASCADE;
CREATE TABLE Protocolo (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(255),
    dataEntrega TIMESTAMP,
    dataGerada TIMESTAMP,
    status BOOLEAN,
    fkFarmaceutico BIGINT REFERENCES Farmaceutico(id)
);

-- Tabela Consulta
DROP TABLE IF EXISTS Consulta CASCADE;
CREATE TABLE Consulta (
    id BIGSERIAL PRIMARY KEY,
    dataConsulta TIMESTAMP,
    descricao VARCHAR(255),
    tipoConsulta VARCHAR(255),
    status VARCHAR(20) CHECK (status IN ('EM_ATENDIMENTO', 'CONFIRMADA', 'REALIZADA', 'CANCELADA')),
    fkMedico BIGINT NOT NULL REFERENCES Medico(id),
    fkPaciente BIGINT NOT NULL REFERENCES Paciente(id),
    fkProtocolo BIGINT UNIQUE REFERENCES Protocolo(id)
);

-- Tabela de relacionamento protocolo_medicamento
DROP TABLE IF EXISTS protocolo_medicamento CASCADE;
CREATE TABLE protocolo_medicamento (
       fkMedicamento BIGINT NOT NULL REFERENCES Medicamento(id),
       fkProtocolo BIGINT NOT NULL REFERENCES Protocolo(id)
);

-- =====================================================
-- INSERÇÃO DOS DADOS
-- =====================================================

-- Inserir dados na tabela Pessoa
INSERT INTO Pessoa (id, cpf, dataNascimento, email, endereco, nome, sexo, telefone) VALUES
(1, '111.222.333-44', '1980-05-15 00:00:00', 'joao.silva@email.com', 'Rua A, 123', 'João Silva', 'M', '(11) 9999-8888'),
(2, '222.333.444-55', '1985-08-20 00:00:00', 'maria.santos@email.com', 'Rua B, 456', 'Maria Santos', 'F', '(11) 9888-7777'),
(3, '333.444.555-66', '1978-12-10 00:00:00', 'pedro.oliveira@email.com', 'Rua C, 789', 'Pedro Oliveira', 'M', '(11) 9777-6666'),
(4, '444.555.666-77', '1990-03-25 00:00:00', 'ana.costa@email.com', 'Rua D, 101', 'Ana Costa', 'F', '(11) 9666-5555'),
(5, '555.666.777-88', '1982-07-30 00:00:00', 'carlos.pereira@email.com', 'Rua E, 202', 'Carlos Pereira', 'M', '(11) 9555-4444'),
(6, '111.555.888-99', '1990-01-15 00:00:00', 'lucas.almeida@email.com', 'Rua K, 808', 'Lucas Almeida', 'M', '(11) 9001-1111'),
(7, '222.666.999-00', '1991-02-20 00:00:00', 'carla.ribeiro@email.com', 'Rua L, 909', 'Carla Ribeiro', 'F', '(11) 9002-2222'),
(8, '333.777.000-11', '1989-03-05 00:00:00', 'bruno.lima@email.com', 'Rua M, 1010', 'Bruno Lima', 'M', '(11) 9003-3333'),
(9, '444.888.111-22', '1987-04-10 00:00:00', 'renata.costa@email.com', 'Rua N, 1111', 'Renata Costa', 'F', '(11) 9004-4444'),
(10, '555.999.222-33', '1992-05-12 00:00:00', 'tiago.ferreira@email.com', 'Rua O, 1212', 'Tiago Ferreira', 'M', '(11) 9005-5555'),
(11, '666.777.888-99', '1995-01-10 00:00:00', 'paulo.souza@email.com', 'Rua F, 303', 'Paulo Souza', 'M', '(11) 9444-3333'),
(12, '777.888.999-00', '1988-06-15 00:00:00', 'juliana.lima@email.com', 'Rua G, 404', 'Juliana Lima', 'F', '(11) 9333-2222'),
(13, '888.999.000-11', '1975-09-20 00:00:00', 'ricardo.martins@email.com', 'Rua H, 505', 'Ricardo Martins', 'M', '(11) 9222-1111'),
(14, '999.000.111-22', '1992-11-05 00:00:00', 'fernanda.alves@email.com', 'Rua I, 606', 'Fernanda Alves', 'F', '(11) 9111-0000'),
(15, '000.111.222-33', '1987-04-18 00:00:00', 'marcos.ferreira@email.com', 'Rua J, 707', 'Marcos Ferreira', 'M', '(11) 9000-9999'),
(16, '111.111.111-11', '1993-03-22 00:00:00', 'larissa.rocha@email.com', 'Rua P, 1313', 'Larissa Rocha', 'F', '(11) 9441-1111'),
(17, '222.222.222-22', '1986-07-30 00:00:00', 'fernando.gomes@email.com', 'Rua Q, 1414', 'Fernando Gomes', 'M', '(11) 9442-2222'),
(18, '333.333.333-33', '1990-11-12 00:00:00', 'mariana.costa@email.com', 'Rua R, 1515', 'Mariana Costa', 'F', '(11) 9443-3333'),
(19, '444.444.444-44', '1985-05-05 00:00:00', 'gabriel.lima@email.com', 'Rua S, 1616', 'Gabriel Lima', 'M', '(11) 9444-4444'),
(20, '555.555.555-55', '1991-08-08 00:00:00', 'beatriz.alves@email.com', 'Rua T, 1717', 'Beatriz Alves', 'F', '(11) 9445-5555'),
(21, '666.777.888-00', '1994-07-15 00:00:00', 'aline.souza@email.com', 'Rua U, 1818', 'Aline Souza', 'F', '(11) 9555-6666'),
(22, '777.888.999-11', '1983-04-22 00:00:00', 'tiago.silva@email.com', 'Rua V, 1919', 'Tiago Silva', 'M', '(11) 9666-7777'),
(23, '888.999.000-22', '1985-09-10 00:00:00', 'carla.ferreira@email.com', 'Rua W, 2020', 'Carla Ferreira', 'F', '(11) 9777-8888');

-- Inserir dados na tabela Funcionario
INSERT INTO Funcionario (id, dataAdimissao, matricula, fkPessoa) VALUES
(1, '2020-01-15 00:00:00', 1001, 1),
(2, '2019-03-20 00:00:00', 1002, 2),
(3, '2021-05-10 00:00:00', 1003, 3),
(4, '2018-08-25 00:00:00', 1004, 4),
(5, '2022-02-15 00:00:00', 1005, 5),
(6, '2021-06-15 00:00:00', 1006, 6),
(7, '2022-07-20 00:00:00', 1007, 7),
(8, '2020-08-25 00:00:00', 1008, 8),
(9, '2019-09-30 00:00:00', 1009, 9),
(10, '2023-01-05 00:00:00', 1010, 10),
(11, '2025-08-27 23:21:27', 1011, 21),
(12, '2025-08-27 23:21:27', 1012, 22),
(13, '2025-08-27 23:21:27', 1013, 23);


-- Inserir dados na tabela Paciente
INSERT INTO Paciente (id, numeroSus, fkPessoa) VALUES
(1, 'SUS111222333', 1),
(2, 'SUS222333444', 2),
(3, 'SUS333444555', 3),
(4, 'SUS444555666', 4),
(5, 'SUS555666777', 5),
(6, 'SUS111555888', 6),
(7, 'SUS222666999', 7),
(8, 'SUS333777000', 8),
(9, 'SUS444888111', 9),
(10, 'SUS555999222', 10),
(11, 'SUS123456789', 11),
(12, 'SUS987654321', 12),
(13, 'SUS456789123', 13),
(14, 'SUS789123456', 14),
(15, 'SUS321654987', 15),
(16, 'SUS147852369', 16),
(17, 'SUS258963147', 17),
(18, 'SUS369741258', 18),
(19, 'SUS741852963', 19),
(20, 'SUS852963741', 20),
(21, 'SUS000021', 21),
(22, 'SUS000022', 22),
(23, 'SUS000023', 23);

-- Inserir dados na tabela Administrador
INSERT INTO Administrador (id, setor) VALUES
(12, 'TI'),
(13, 'RH');

-- Inserir dados na tabela Farmaceutico
INSERT INTO Farmaceutico (id, crf) VALUES
(4, 'CRF/SP 12345'),
(8, 'CRF/SP 23456'),
(9, 'CRF/SP 34567'),
(5, 'CRF/SP 54321');

-- Inserir dados na tabela Medico
INSERT INTO Medico (id, crm, especialidade, nomeFantasia) VALUES
(1, 'CRM/SP 123456', 'Cardiologia', 'Dr. João Silva'),
(2, 'CRM/SP 654321', 'Pediatria', 'Dra. Maria Santos'),
(3, 'CRM/SP 789012', 'Ortopedia', 'Dr. Pedro Oliveira'),
(6, 'CRM/SP 234567', 'Dermatologia', 'Dr. Lucas Almeida'),
(7, 'CRM/SP 345678', 'Neurologia', 'Dra. Carla Ribeiro');

-- Inserir dados na tabela Recepcionista
INSERT INTO Recepcionista (id, setor) VALUES
(10, 'Atendimento'),
(11, 'Triagem');

-- Inserir dados na tabela UsuarioSistema
INSERT INTO UsuarioSistema (id, ativo, login, senha, tipoUser, fkUsuario) VALUES
(1, true, 'joao.medico', 'senha123', 'MEDICO', 1),
(2, true, 'maria.medico', 'senha123', 'MEDICO', 2),
(3, true, 'pedro.medico', 'senha123', 'MEDICO', 3),
(4, true, 'lucas.medico', 'senha123', 'MEDICO', 6),
(5, true, 'carla.medico', 'senha123', 'MEDICO', 7),
(6, true, 'ana.farmaceutico', 'senha123', 'FARMACEUTICO', 4),
(7, true, 'carlos.farmaceutico', 'senha123', 'FARMACEUTICO', 5),
(8, true, 'bruno.farmaceutico', 'senha123', 'FARMACEUTICO', 8),
(9, true, 'renata.farmaceutico', 'senha123', 'FARMACEUTICO', 9),
(10, true, 'tiago.recepcionista', 'senha123', 'RECEPCIONISTA', 10),
(11, true, 'aline.recepcionista', 'senha123', 'RECEPCIONISTA', 11),
(12, true, 'admin.ti', 'senha123', 'ADMINISTRADOR', 12),
(13, true, 'admin.rh', 'senha123', 'ADMINISTRADOR', 13);


-- Inserir dados na tabela Medicamento
INSERT INTO Medicamento (id, nome) VALUES
(1, 'Paracetamol'),
(2, 'Amoxicilina'),
(3, 'Losartana'),
(4, 'Metformina'),
(5, 'Omeprazol'),
(6, 'Atenolol'),
(7, 'Dipirona'),
(8, 'Hidroclorotiazida'),
(9, 'Sinvastatina'),
(10, 'Insulina');


-- Inserir dados na tabela TipoMedicamento
INSERT INTO TipoMedicamento (id, descricao, estoqueMinimo, quantidadeCaixa, tipo, unidadeMedida, fkMedicamento) VALUES
(1, 'Analgésico e antitérmico', 50, 20, 'Comprimido', '500mg', 1),
(2, 'Antibiótico', 30, 15, 'Comprimido', '500mg', 2),
(3, 'Anti-hipertensivo', 40, 30, 'Comprimido', '50mg', 3),
(4, 'Antidiabético', 60, 30, 'Comprimido', '850mg', 4),
(5, 'Protetor gástrico', 45, 28, 'Cápsula', '20mg', 5),
(6, 'Beta-bloqueador', 35, 30, 'Comprimido', '50mg', 6),
(7, 'Analgésico', 70, 10, 'Comprimido', '500mg', 7),
(8, 'Diurético', 25, 30, 'Comprimido', '25mg', 8),
(9, 'Estatina', 20, 30, 'Comprimido', '20mg', 9),
(10, 'Hormônio', 15, 1, 'Injetável', '100UI/ml', 10);


-- Inserir dados na tabela LoteMedicamento
INSERT INTO LoteMedicamento (id, dataFabricacao, dataValidade, quantidadeEntrada, quantidadeEstoque, status, fkTipoMedicamento) VALUES
(1, '2023-01-01 00:00:00', '2024-01-01 00:00:00', 100, 0, 'VENCIDO', 1),
(2, '2023-02-01 00:00:00', '2024-02-01 00:00:00', 100, 0, 'VENCIDO', 2),
(3, '2023-03-01 00:00:00', '2024-03-01 00:00:00', 100, 0, 'VENCIDO', 3),
(4, '2023-04-01 00:00:00', '2024-04-01 00:00:00', 100, 0, 'VENCIDO', 4),
(5, '2023-05-01 00:00:00', '2024-05-01 00:00:00', 100, 0, 'VENCIDO', 5),
(6, '2023-06-01 00:00:00', '2024-06-01 00:00:00', 100, 0, 'VENCIDO', 6),
(7, '2023-07-01 00:00:00', '2024-07-01 00:00:00', 100, 0, 'VENCIDO', 7),
(8, '2023-08-01 00:00:00', '2024-08-01 00:00:00', 100, 0, 'VENCIDO', 8),
(9, '2023-09-01 00:00:00', '2024-09-01 00:00:00', 100, 0, 'VENCIDO', 9),
(10, '2023-10-01 00:00:00', '2024-10-01 00:00:00', 100, 0, 'VENCIDO', 10),
(11, '2024-01-01 00:00:00', '2025-11-01 00:00:00', 120, 110, 'DISPONIVEL', 1),
(12, '2024-01-05 00:00:00', '2025-11-05 00:00:00', 130, 125, 'DISPONIVEL', 2),
(13, '2024-02-01 00:00:00', '2025-12-01 00:00:00', 140, 130, 'DISPONIVEL', 3),
(14, '2024-02-05 00:00:00', '2025-12-05 00:00:00', 150, 140, 'DISPONIVEL', 4),
(15, '2024-03-01 00:00:00', '2025-12-01 00:00:00', 160, 150, 'DISPONIVEL', 5),
(16, '2024-03-05 00:00:00', '2025-09-05 00:00:00', 170, 160, 'DISPONIVEL', 6),
(17, '2024-04-01 00:00:00', '2025-12-01 00:00:00', 180, 170, 'DISPONIVEL', 7),
(18, '2024-04-05 00:00:00', '2025-09-05 00:00:00', 190, 180, 'DISPONIVEL', 8),
(19, '2024-05-01 00:00:00', '2025-10-01 00:00:00', 200, 190, 'DISPONIVEL', 9),
(20, '2024-05-05 00:00:00', '2025-10-05 00:00:00', 210, 200, 'DISPONIVEL', 10),
(21, '2024-06-01 00:00:00', '2025-10-01 00:00:00', 220, 210, 'DISPONIVEL', 1),
(22, '2024-06-05 00:00:00', '2025-09-05 00:00:00', 230, 220, 'DISPONIVEL', 2),
(23, '2024-07-01 00:00:00', '2025-09-01 00:00:00', 240, 230, 'DISPONIVEL', 3),
(24, '2024-07-05 00:00:00', '2025-09-05 00:00:00', 250, 240, 'DISPONIVEL', 4),
(25, '2024-08-01 00:00:00', '2025-09-01 00:00:00', 260, 250, 'DISPONIVEL', 5),
(26, '2024-08-05 00:00:00', '2025-11-05 00:00:00', 270, 260, 'DISPONIVEL', 6),
(27, '2024-09-01 00:00:00', '2025-09-01 00:00:00', 280, 270, 'DISPONIVEL', 7),
(28, '2024-09-05 00:00:00', '2025-09-05 00:00:00', 290, 280, 'DISPONIVEL', 8),
(29, '2024-10-01 00:00:00', '2025-10-01 00:00:00', 300, 290, 'DISPONIVEL', 9),
(30, '2024-10-05 00:00:00', '2025-10-05 00:00:00', 310, 300, 'DISPONIVEL', 10),
(31, '2024-11-01 00:00:00', '2025-11-01 00:00:00', 320, 310, 'DISPONIVEL', 1),
(32, '2024-11-05 00:00:00', '2025-11-05 00:00:00', 330, 320, 'DISPONIVEL', 2),
(33, '2024-12-01 00:00:00', '2025-12-01 00:00:00', 340, 330, 'DISPONIVEL', 3),
(34, '2024-12-05 00:00:00', '2025-12-05 00:00:00', 350, 340, 'DISPONIVEL', 4),
(35, '2025-01-01 00:00:00', '2026-01-01 00:00:00', 360, 350, 'DISPONIVEL', 5),
(36, '2025-01-05 00:00:00', '2026-01-05 00:00:00', 370, 360, 'DISPONIVEL', 6),
(37, '2025-02-01 00:00:00', '2026-02-01 00:00:00', 380, 370, 'DISPONIVEL', 7),
(38, '2025-02-05 00:00:00', '2026-02-05 00:00:00', 390, 380, 'DISPONIVEL', 8),
(39, '2025-03-01 00:00:00', '2026-03-01 00:00:00', 400, 390, 'DISPONIVEL', 9),
(40, '2025-03-05 00:00:00', '2026-03-05 00:00:00', 410, 400, 'DISPONIVEL', 10),
(41, '2025-04-01 00:00:00', '2026-04-01 00:00:00', 420, 410, 'DISPONIVEL', 1),
(42, '2025-04-05 00:00:00', '2026-04-05 00:00:00', 430, 420, 'DISPONIVEL', 2),
(43, '2025-05-01 00:00:00', '2026-05-01 00:00:00', 440, 430, 'DISPONIVEL', 3),
(44, '2025-05-05 00:00:00', '2026-05-05 00:00:00', 450, 440, 'DISPONIVEL', 4),
(45, '2025-06-01 00:00:00', '2026-06-01 00:00:00', 460, 450, 'DISPONIVEL', 5),
(46, '2025-06-05 00:00:00', '2026-06-05 00:00:00', 470, 460, 'DISPONIVEL', 6),
(47, '2025-07-01 00:00:00', '2026-07-01 00:00:00', 480, 470, 'DISPONIVEL', 7),
(48, '2025-07-05 00:00:00', '2026-07-05 00:00:00', 490, 480, 'DISPONIVEL', 8),
(49, '2025-08-01 00:00:00', '2026-12-01 00:00:00', 500, 490, 'DISPONIVEL', 9),
(50, '2025-08-05 00:00:00', '2026-08-05 00:00:00', 510, 500, 'DISPONIVEL', 10);


-- Inserir dados na tabela Protocolo
INSERT INTO Protocolo (id, codigo, dataEntrega, dataGerada, status, fkFarmaceutico) VALUES
(1, 'PROTO0001', NULL, '2025-08-27 17:09:25', false, NULL),
(2, 'PROTO0002', NULL, '2025-08-27 17:09:25', false, NULL),
(3, 'PROTO0003', NULL, '2025-08-27 17:09:25', false, NULL),
(4, 'PROTO0004', NULL, '2025-08-27 17:09:25', false, NULL),
(5, 'PROTO0005', NULL, '2025-08-27 17:09:25', false, NULL),
(6, 'PROTO0006', NULL, '2025-08-27 17:09:25', false, NULL),
(7, 'PROTO0007', NULL, '2025-08-27 17:09:25', false, NULL),
(8, 'PROTO0008', NULL, '2025-08-27 17:09:25', false, NULL),
(9, 'PROTO0009', NULL, '2025-08-27 17:09:25', false, NULL),
(10, 'PROTO0010', NULL, '2025-08-27 17:09:25', false, NULL),
(11, 'PROTO0011', NULL, '2025-08-27 17:09:25', false, NULL),
(12, 'PROTO0012', NULL, '2025-08-27 17:09:25', false, NULL),
(13, 'PROTO0013', NULL, '2025-08-27 17:09:25', false, NULL),
(14, 'PROTO0014', NULL, '2025-08-27 17:09:25', false, NULL),
(15, 'PROTO0015', NULL, '2025-08-27 17:09:25', false, NULL),
(16, 'PROTO0016', NULL, '2025-08-27 17:09:25', true, NULL),
(17, 'PROTO0017', NULL, '2025-08-27 17:09:25', true, NULL),
(18, 'PROTO0018', NULL, '2025-08-27 17:09:25', true, NULL),
(19, 'PROTO0019', NULL, '2025-08-27 17:09:25', true, NULL),
(20, 'PROTO0020', NULL, '2025-08-27 17:09:25', true, NULL),
(21, 'PROTO0021', NULL, '2025-08-27 17:09:25', true, NULL),
(22, 'PROTO0022', NULL, '2025-08-27 17:09:25', true, NULL),
(23, 'PROTO0023', NULL, '2025-08-27 17:09:25', true, NULL),
(24, 'PROTO0024', NULL, '2025-08-27 17:09:25', true, NULL),
(25, 'PROTO0025', NULL, '2025-08-27 17:09:25', true, NULL),
(26, 'PROTO0026', NULL, '2025-08-27 17:09:25', true, NULL),
(27, 'PROTO0027', NULL, '2025-08-27 17:09:25', true, NULL),
(28, 'PROTO0028', NULL, '2025-08-27 17:09:25', true, NULL),
(29, 'PROTO0029', NULL, '2025-08-27 17:09:25', true, NULL),
(30, 'PROTO0030', NULL, '2025-08-27 17:09:25', true, NULL),
(31, 'PROTO0031', NULL, '2025-08-27 17:09:25', true, NULL),
(32, 'PROTO0032', NULL, '2025-08-27 17:09:25', true, NULL),
(33, 'PROTO0033', NULL, '2025-08-27 17:09:25', true, NULL),
(34, 'PROTO0034', NULL, '2025-08-27 17:09:25', true, NULL),
(35, 'PROTO0035', NULL, '2025-08-27 17:09:25', true, NULL),
(36, 'PROTO0036', NULL, '2025-08-27 17:09:25', true, NULL),
(37, 'PROTO0037', NULL, '2025-08-27 17:09:25', true, NULL),
(38, 'PROTO0038', NULL, '2025-08-27 17:09:25', true, NULL),
(39, 'PROTO0039', NULL, '2025-08-27 17:09:25', true, NULL),
(40, 'PROTO0040', NULL, '2025-08-27 17:09:25', true, NULL),
(41, 'PROTO0041', NULL, '2025-08-27 17:09:25', true, NULL),
(42, 'PROTO0042', NULL, '2025-08-27 17:09:25', true, NULL),
(43, 'PROTO0043', NULL, '2025-08-27 17:09:25', true, NULL),
(44, 'PROTO0044', NULL, '2025-08-27 17:09:25', true, NULL),
(45, 'PROTO0045', NULL, '2025-08-27 17:09:25', true, NULL),
(46, 'PROTO0046', NULL, '2025-08-27 17:09:25', true, NULL),
(47, 'PROTO0047', NULL, '2025-08-27 17:09:25', true, NULL),
(48, 'PROTO0048', NULL, '2025-08-27 17:09:25', true, NULL),
(49, 'PROTO0049', NULL, '2025-08-27 17:09:25', true, NULL),
(50, 'PROTO0050', NULL, '2025-08-27 17:09:25', true, NULL);


-- Inserir dados na tabela Consulta
INSERT INTO Consulta (id, dataConsulta, descricao, tipoConsulta, status, fkMedico, fkPaciente, fkProtocolo) VALUES
(1, '2025-08-27 17:09:26', 'Consulta 1', 'CONSULTA', 'REALIZADA', 1, 11, 1),
(2, '2025-08-28 17:09:26', 'Consulta 2', 'CONSULTA', 'CONFIRMADA', 2, 12, 2),
(3, '2025-08-29 17:09:26', 'Consulta 3', 'CONSULTA', 'EM_ATENDIMENTO', 3, 13, 3),
(4, '2025-08-30 17:09:26', 'Consulta 4', 'CONSULTA', 'CANCELADA', 1, 14, 4),
(5, '2025-08-31 17:09:26', 'Consulta 5', 'CONSULTA', 'REALIZADA', 1, 15, 5),
(6, '2025-09-01 17:09:26', 'Consulta 6', 'CONSULTA', 'REALIZADA', 1, 16, 6),
(7, '2025-09-02 17:09:26', 'Consulta 7', 'CONSULTA', 'CONFIRMADA', 2, 17, 7),
(8, '2025-09-03 17:09:26', 'Consulta 8', 'CONSULTA', 'EM_ATENDIMENTO', 3, 18, 8),
(9, '2025-08-28 17:09:26', 'Consulta 9', 'CONSULTA', 'CANCELADA', 1, 19, 9),
(10, '2025-08-29 17:09:26', 'Consulta 10', 'CONSULTA', 'REALIZADA', 1, 20, 10),
(11, '2025-08-30 17:09:26', 'Consulta 11', 'CONSULTA', 'CONFIRMADA', 1, 11, 11),
(12, '2025-08-31 17:09:26', 'Consulta 12', 'CONSULTA', 'EM_ATENDIMENTO', 2, 12, 12),
(13, '2025-09-01 17:09:26', 'Consulta 13', 'CONSULTA', 'CANCELADA', 3, 13, 13),
(14, '2025-09-02 17:09:26', 'Consulta 14', 'CONSULTA', 'REALIZADA', 1, 14, 14),
(15, '2025-09-03 17:09:26', 'Consulta 15', 'CONSULTA', 'REALIZADA', 1, 15, 15),
(16, '2025-08-28 17:09:26', 'Consulta 16', 'CONSULTA', 'CONFIRMADA', 1, 16, 16),
(17, '2025-08-29 17:09:26', 'Consulta 17', 'CONSULTA', 'EM_ATENDIMENTO', 2, 17, 17),
(18, '2025-08-30 17:09:26', 'Consulta 18', 'CONSULTA', 'CANCELADA', 3, 18, 18),
(19, '2025-08-31 17:09:26', 'Consulta 19', 'CONSULTA', 'REALIZADA', 1, 19, 19),
(20, '2025-09-01 17:09:26', 'Consulta 20', 'CONSULTA', 'REALIZADA', 1, 20, 20),
(21, '2025-09-02 17:09:26', 'Consulta 21', 'CONSULTA', 'CONFIRMADA', 1, 11, 21),
(22, '2025-09-03 17:09:26', 'Consulta 22', 'CONSULTA', 'EM_ATENDIMENTO', 2, 12, 22),
(23, '2025-08-28 17:09:26', 'Consulta 23', 'CONSULTA', 'CANCELADA', 3, 13, 23),
(24, '2025-08-29 17:09:26', 'Consulta 24', 'CONSULTA', 'REALIZADA', 1, 14, 24),
(25, '2025-08-30 17:09:26', 'Consulta 25', 'CONSULTA', 'REALIZADA', 1, 15, 25),
(26, '2025-08-31 17:09:26', 'Consulta 26', 'CONSULTA', 'CONFIRMADA', 1, 16, 26),
(27, '2025-09-01 17:09:26', 'Consulta 27', 'CONSULTA', 'EM_ATENDIMENTO', 2, 17, 27),
(28, '2025-09-02 17:09:26', 'Consulta 28', 'CONSULTA', 'CANCELADA', 3, 18, 28),
(29, '2025-09-03 17:09:26', 'Consulta 29', 'CONSULTA', 'REALIZADA', 1, 19, 29),
(30, '2025-08-28 17:09:26', 'Consulta 30', 'CONSULTA', 'REALIZADA', 1, 20, 30),
(31, '2025-08-29 17:09:26', 'Consulta 31', 'CONSULTA', 'CONFIRMADA', 1, 11, 31),
(32, '2025-08-30 17:09:26', 'Consulta 32', 'CONSULTA', 'EM_ATENDIMENTO', 2, 12, 32),
(33, '2025-08-31 17:09:26', 'Consulta 33', 'CONSULTA', 'CANCELADA', 3, 13, 33),
(34, '2025-09-01 17:09:26', 'Consulta 34', 'CONSULTA', 'REALIZADA', 1, 14, 34),
(35, '2025-09-02 17:09:26', 'Consulta 35', 'CONSULTA', 'REALIZADA', 1, 15, 35),
(36, '2025-09-03 17:09:26', 'Consulta 36', 'CONSULTA', 'CONFIRMADA', 1, 16, 36),
(37, '2025-08-28 17:09:26', 'Consulta 37', 'CONSULTA', 'EM_ATENDIMENTO', 2, 17, 37),
(38, '2025-08-29 17:09:26', 'Consulta 38', 'CONSULTA', 'CANCELADA', 3, 18, 38),
(39, '2025-08-30 17:09:26', 'Consulta 39', 'CONSULTA', 'REALIZADA', 1, 19, 39),
(40, '2025-08-31 17:09:26', 'Consulta 40', 'CONSULTA', 'REALIZADA', 1, 20, 40),
(41, '2025-09-01 17:09:26', 'Consulta 41', 'CONSULTA', 'CONFIRMADA', 1, 11, 41),
(42, '2025-09-02 17:09:26', 'Consulta 42', 'CONSULTA', 'EM_ATENDIMENTO', 2, 12, 42),
(43, '2025-09-03 17:09:26', 'Consulta 43', 'CONSULTA', 'CANCELADA', 3, 13, 43),
(44, '2025-08-28 17:09:26', 'Consulta 44', 'CONSULTA', 'REALIZADA', 1, 14, 44),
(45, '2025-08-29 17:09:26', 'Consulta 45', 'CONSULTA', 'REALIZADA', 1, 15, 45),
(46, '2025-08-30 17:09:26', 'Consulta 46', 'CONSULTA', 'CONFIRMADA', 1, 16, 46),
(47, '2025-08-31 17:09:26', 'Consulta 47', 'CONSULTA', 'EM_ATENDIMENTO', 2, 17, 47),
(48, '2025-09-01 17:09:26', 'Consulta 48', 'CONSULTA', 'CANCELADA', 3, 18, 48),
(49, '2025-09-02 17:09:26', 'Consulta 49', 'CONSULTA', 'REALIZADA', 1, 19, 49),
(50, '2025-09-03 17:09:26', 'Consulta 50', 'CONSULTA', 'REALIZADA', 1, 20, 50);


-- Inserir dados na tabela de relacionamento protocolo_medicamento
INSERT INTO protocolo_medicamento (fkMedicamento, fkProtocolo) VALUES
(1, 1), (2, 1), (3, 1), (2, 2), (5, 2), (1, 3), (4, 3), (6, 3), (3, 4), (7, 4), (5, 5), (8, 5), (10, 5),
(2, 6), (6, 6), (9, 6), (1, 7), (3, 7), (7, 7), (4, 8), (5, 8), (10, 8), (2, 9), (8, 9), (9, 9),
(1, 10), (6, 10), (7, 10), (3, 11), (5, 11), (9, 11), (2, 12), (4, 12), (6, 12), (1, 13), (7, 13), (10, 13),
(3, 14), (5, 14), (8, 14), (2, 15), (4, 15), (9, 15), (1, 16), (6, 16), (10, 16), (3, 17), (5, 17), (7, 17),
(2, 18), (4, 18), (8, 18), (1, 19), (6, 19), (9, 19), (3, 20), (5, 20), (10, 20), (2, 21), (4, 21), (7, 21),
(1, 22), (6, 22), (8, 22), (3, 23), (5, 23), (9, 23), (2, 24), (4, 24), (10, 24), (1, 25), (6, 25), (7, 25),
(3, 26), (5, 26), (8, 26), (2, 27), (4, 27), (9, 27), (1, 28), (6, 28), (10, 28), (3, 29), (5, 29), (7, 29),
(2, 30), (4, 30), (8, 30), (1, 31), (6, 31), (9, 31), (3, 32), (5, 32), (10, 32), (2, 33), (4, 33), (7, 33),
(1, 34), (6, 34), (8, 34), (3, 35), (5, 35), (9, 35), (2, 36), (4, 36), (10, 36), (1, 37), (6, 37), (7, 37),
(3, 38), (5, 38), (8, 38), (2, 39), (4, 39), (9, 39), (1, 40), (6, 40), (10, 40), (3, 41), (5, 41), (7, 41),
(2, 42), (4, 42), (8, 42), (1, 43), (6, 43), (9, 43), (3, 44), (5, 44), (10, 44), (2, 45), (4, 45), (7, 45),
(1, 46), (6, 46), (8, 46), (3, 47), (5, 47), (9, 47), (2, 48), (4, 48), (10, 48), (1, 49), (6, 49), (7, 49),
(3, 50), (5, 50), (8, 50);

-- Criar índices para melhor performance (opcional)
CREATE INDEX idx_pessoa_cpf ON Pessoa(cpf);
CREATE INDEX idx_pessoa_nome ON Pessoa(nome);
CREATE INDEX idx_funcionario_matricula ON Funcionario(matricula);
CREATE INDEX idx_funcionario_fk_pessoa ON Funcionario(fkPessoa);
CREATE INDEX idx_paciente_sus ON Paciente(numeroSus);
CREATE INDEX idx_paciente_fk_pessoa ON Paciente(fkPessoa);
CREATE INDEX idx_consulta_data ON Consulta(dataConsulta);
CREATE INDEX idx_consulta_medico ON Consulta(fkMedico);
CREATE INDEX idx_consulta_paciente ON Consulta(fkPaciente);
CREATE INDEX idx_consulta_protocolo ON Consulta(fkProtocolo);
CREATE INDEX idx_lote_validade ON LoteMedicamento(dataValidade);
CREATE INDEX idx_lote_status ON LoteMedicamento(status);
CREATE INDEX idx_usuario_login ON UsuarioSistema(login);

-- Reabilitar verificação de chaves estrangeiras
SET session_replication_role = 'origin';

-- Mensagem de conclusão
DO $$
BEGIN
    RAISE NOTICE 'Migração do MySQL para PostgreSQL concluída com sucesso!';
    RAISE NOTICE 'Total de registros:';
    RAISE NOTICE 'Pessoa: %', (SELECT COUNT(*) FROM Pessoa);
    RAISE NOTICE 'Funcionario: %', (SELECT COUNT(*) FROM Funcionario);
    RAISE NOTICE 'Paciente: %', (SELECT COUNT(*) FROM Paciente);
    RAISE NOTICE 'Medico: %', (SELECT COUNT(*) FROM Medico);
    RAISE NOTICE 'Consulta: %', (SELECT COUNT(*) FROM Consulta);
END $$;