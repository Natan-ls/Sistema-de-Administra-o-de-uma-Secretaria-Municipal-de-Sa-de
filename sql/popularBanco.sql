-- Desativa checagem de FK temporariamente
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE protocolo_medicamento;
TRUNCATE TABLE LoteMedicamento;
TRUNCATE TABLE TipoMedicamento;
TRUNCATE TABLE Protocolo;
TRUNCATE TABLE Medicamento;
TRUNCATE TABLE Consulta;
TRUNCATE TABLE Administrador;
TRUNCATE TABLE Farmaceutico;
TRUNCATE TABLE Medico;
TRUNCATE TABLE Recepcionista;
TRUNCATE TABLE Funcionario;
TRUNCATE TABLE Paciente;
TRUNCATE TABLE Pessoa;
TRUNCATE TABLE UsuarioSistema;

SET FOREIGN_KEY_CHECKS = 0;

-- =========================
-- PESSOAS (10 funcionários + 10 pacientes)
-- =========================
INSERT INTO Pessoa (nome, cpf, dataNascimento, email, endereco, sexo, telefone) VALUES
('João Silva', '111.222.333-44', '1980-05-15', 'joao.silva@email.com', 'Rua A, 123', 'M', '(11) 9999-8888'),
('Maria Santos', '222.333.444-55', '1985-08-20', 'maria.santos@email.com', 'Rua B, 456', 'F', '(11) 9888-7777'),
('Pedro Oliveira', '333.444.555-66', '1978-12-10', 'pedro.oliveira@email.com', 'Rua C, 789', 'M', '(11) 9777-6666'),
('Ana Costa', '444.555.666-77', '1990-03-25', 'ana.costa@email.com', 'Rua D, 101', 'F', '(11) 9666-5555'),
('Carlos Pereira', '555.666.777-88', '1982-07-30', 'carlos.pereira@email.com', 'Rua E, 202', 'M', '(11) 9555-4444'),
('Lucas Almeida', '111.555.888-99', '1990-01-15', 'lucas.almeida@email.com', 'Rua K, 808', 'M', '(11) 9001-1111'),
('Carla Ribeiro', '222.666.999-00', '1991-02-20', 'carla.ribeiro@email.com', 'Rua L, 909', 'F', '(11) 9002-2222'),
('Bruno Lima', '333.777.000-11', '1989-03-05', 'bruno.lima@email.com', 'Rua M, 1010', 'M', '(11) 9003-3333'),
('Renata Costa', '444.888.111-22', '1987-04-10', 'renata.costa@email.com', 'Rua N, 1111', 'F', '(11) 9004-4444'),
('Tiago Ferreira', '555.999.222-33', '1992-05-12', 'tiago.ferreira@email.com', 'Rua O, 1212', 'M', '(11) 9005-5555'),

('Paulo Souza', '666.777.888-99', '1995-01-10', 'paulo.souza@email.com', 'Rua F, 303', 'M', '(11) 9444-3333'),
('Juliana Lima', '777.888.999-00', '1988-06-15', 'juliana.lima@email.com', 'Rua G, 404', 'F', '(11) 9333-2222'),
('Ricardo Martins', '888.999.000-11', '1975-09-20', 'ricardo.martins@email.com', 'Rua H, 505', 'M', '(11) 9222-1111'),
('Fernanda Alves', '999.000.111-22', '1992-11-05', 'fernanda.alves@email.com', 'Rua I, 606', 'F', '(11) 9111-0000'),
('Marcos Ferreira', '000.111.222-33', '1987-04-18', 'marcos.ferreira@email.com', 'Rua J, 707', 'M', '(11) 9000-9999'),
('Larissa Rocha', '111.111.111-11', '1993-03-22', 'larissa.rocha@email.com', 'Rua P, 1313', 'F', '(11) 9441-1111'),
('Fernando Gomes', '222.222.222-22', '1986-07-30', 'fernando.gomes@email.com', 'Rua Q, 1414', 'M', '(11) 9442-2222'),
('Mariana Costa', '333.333.333-33', '1990-11-12', 'mariana.costa@email.com', 'Rua R, 1515', 'F', '(11) 9443-3333'),
('Gabriel Lima', '444.444.444-44', '1985-05-05', 'gabriel.lima@email.com', 'Rua S, 1616', 'M', '(11) 9444-4444'),
('Beatriz Alves', '555.555.555-55', '1991-08-08', 'beatriz.alves@email.com', 'Rua T, 1717', 'F', '(11) 9445-5555');

-- =========================
-- FUNCIONÁRIOS
-- =========================
INSERT INTO Funcionario (dataAdimissao, matricula, fkPessoa) VALUES
('2020-01-15', 1001, 1),
('2019-03-20', 1002, 2),
('2021-05-10', 1003, 3),
('2018-08-25', 1004, 4),
('2022-02-15', 1005, 5),
('2021-06-15', 1006, 6),
('2022-07-20', 1007, 7),
('2020-08-25', 1008, 8),
('2019-09-30', 1009, 9),
('2023-01-05', 1010, 10);

-- =========================
-- MÉDICOS, FARMACÊUTICOS, RECEPCIONISTAS, ADMINISTRADORES
-- =========================
INSERT INTO Medico (crm, especialidade, nomeFantasia, id) VALUES
('CRM/SP 123456', 'Cardiologia', 'Dr. João Silva', 1),
('CRM/SP 654321', 'Pediatria', 'Dra. Maria Santos', 2),
('CRM/SP 789012', 'Ortopedia', 'Dr. Pedro Oliveira', 3),
('CRM/SP 234567', 'Dermatologia', 'Dr. Lucas Almeida', 6),
('CRM/SP 345678', 'Neurologia', 'Dra. Carla Ribeiro', 7);

INSERT INTO Farmaceutico (crf, id) VALUES
('CRF/SP 12345', 4),
('CRF/SP 54321', 5),
('CRF/SP 23456', 8),
('CRF/SP 34567', 9);

INSERT INTO Recepcionista (setor, id) VALUES
('Atendimento', 10),
('Triagem', 11);

INSERT INTO Administrador (setor, id) VALUES
('TI', 12),
('RH', 13);

-- =========================
-- PACIENTES
-- =========================
INSERT INTO Paciente (numeroSus, fkPessoa) VALUES
('SUS123456789', 11),
('SUS987654321', 12),
('SUS456789123', 13),
('SUS789123456', 14),
('SUS321654987', 15),
('SUS147852369', 16),
('SUS258963147', 17),
('SUS369741258', 18),
('SUS741852963', 19),
('SUS852963741', 20);

-- =========================
-- MEDICAMENTOS
-- =========================
INSERT INTO Medicamento (nome) VALUES
('Paracetamol'),('Amoxicilina'),('Losartana'),('Metformina'),('Omeprazol'),
('Atenolol'),('Dipirona'),('Hidroclorotiazida'),('Sinvastatina'),('Insulina');

-- =========================
-- TIPOS DE MEDICAMENTO
-- =========================
INSERT INTO TipoMedicamento (descricao, estoqueMinimo, quantidadeCaixa, tipo, unidadeMedida, fkMedicamento) VALUES
('Analgésico e antitérmico',50,20,'Comprimido','500mg',1),
('Antibiótico',30,15,'Comprimido','500mg',2),
('Anti-hipertensivo',40,30,'Comprimido','50mg',3),
('Antidiabético',60,30,'Comprimido','850mg',4),
('Protetor gástrico',45,28,'Cápsula','20mg',5),
('Beta-bloqueador',35,30,'Comprimido','50mg',6),
('Analgésico',70,10,'Comprimido','500mg',7),
('Diurético',25,30,'Comprimido','25mg',8),
('Estatina',20,30,'Comprimido','20mg',9),
('Hormônio',15,1,'Injetável','100UI/ml',10);

-- =========================
-- LOTES DE MEDICAMENTO (50, 10 vencidos)
-- =========================
INSERT INTO LoteMedicamento (dataFabricacao, dataValidade, quantidadeEntrada, quantidadeEstoque, status, fkTipoMedicamento) VALUES
('2023-01-01','2024-01-01',100,0,'VENCIDO',1),
('2023-02-01','2024-02-01',100,0,'VENCIDO',2),
('2023-03-01','2024-03-01',100,0,'VENCIDO',3),
('2023-04-01','2024-04-01',100,0,'VENCIDO',4),
('2023-05-01','2024-05-01',100,0,'VENCIDO',5),
('2023-06-01','2024-06-01',100,0,'VENCIDO',6),
('2023-07-01','2024-07-01',100,0,'VENCIDO',7),
('2023-08-01','2024-08-01',100,0,'VENCIDO',8),
('2023-09-01','2024-09-01',100,0,'VENCIDO',9),
('2023-10-01','2024-10-01',100,0,'VENCIDO',10),
-- demais 40 lotes DISPONÍVEL com datas variadas
('2024-01-01','2025-11-01',120,110,'DISPONIVEL',1),
('2024-01-05','2025-11-05',130,125,'DISPONIVEL',2),
('2024-02-01','2025-12-01',140,130,'DISPONIVEL',3),
('2024-02-05','2025-12-05',150,140,'DISPONIVEL',4),
('2024-03-01','2025-08-01',160,150,'DISPONIVEL',5),
('2024-03-05','2025-09-05',170,160,'DISPONIVEL',6),
('2024-04-01','2025-08-01',180,170,'DISPONIVEL',7),
('2024-04-05','2025-09-05',190,180,'DISPONIVEL',8),
('2024-05-01','2025-10-01',200,190,'DISPONIVEL',9),
('2024-05-05','2025-10-05',210,200,'DISPONIVEL',10),
('2024-06-01','2025-10-01',220,210,'DISPONIVEL',1),
('2024-06-05','2025-08-05',230,220,'DISPONIVEL',2),
('2024-07-01','2025-09-01',240,230,'DISPONIVEL',3),
('2024-07-05','2025-09-05',250,240,'DISPONIVEL',4),
('2024-08-01','2025-08-01',260,250,'DISPONIVEL',5),
('2024-08-05','2025-08-05',270,260,'DISPONIVEL',6),
('2024-09-01','2025-09-01',280,270,'DISPONIVEL',7),
('2024-09-05','2025-09-05',290,280,'DISPONIVEL',8),
('2024-10-01','2025-10-01',300,290,'DISPONIVEL',9),
('2024-10-05','2025-10-05',310,300,'DISPONIVEL',10),
('2024-11-01','2025-11-01',320,310,'DISPONIVEL',1),
('2024-11-05','2025-11-05',330,320,'DISPONIVEL',2),
('2024-12-01','2025-12-01',340,330,'DISPONIVEL',3),
('2024-12-05','2025-12-05',350,340,'DISPONIVEL',4),
('2025-01-01','2026-01-01',360,350,'DISPONIVEL',5),
('2025-01-05','2026-01-05',370,360,'DISPONIVEL',6),
('2025-02-01','2026-02-01',380,370,'DISPONIVEL',7),
('2025-02-05','2026-02-05',390,380,'DISPONIVEL',8),
('2025-03-01','2026-03-01',400,390,'DISPONIVEL',9),
('2025-03-05','2026-03-05',410,400,'DISPONIVEL',10),
('2025-04-01','2026-04-01',420,410,'DISPONIVEL',1),
('2025-04-05','2026-04-05',430,420,'DISPONIVEL',2),
('2025-05-01','2026-05-01',440,430,'DISPONIVEL',3),
('2025-05-05','2026-05-05',450,440,'DISPONIVEL',4),
('2025-06-01','2026-06-01',460,450,'DISPONIVEL',5),
('2025-06-05','2026-06-05',470,460,'DISPONIVEL',6),
('2025-07-01','2026-07-01',480,470,'DISPONIVEL',7),
('2025-07-05','2026-07-05',490,480,'DISPONIVEL',8),
('2025-08-01','2026-08-01',500,490,'DISPONIVEL',9),
('2025-08-05','2026-08-05',510,500,'DISPONIVEL',10);

-- =========================
-- PROTOCOLOS (50, 15 FALSE)
-- =========================
INSERT INTO Protocolo (codigo, dataGerada, status, fkFarmaceutico) VALUES
('PROTO0001', NOW(), FALSE, 4), ('PROTO0002', NOW(), FALSE, 4), ('PROTO0003', NOW(), FALSE, 5),
('PROTO0004', NOW(), FALSE, 5), ('PROTO0005', NOW(), FALSE, 4), ('PROTO0006', NOW(), FALSE, 4),
('PROTO0007', NOW(), FALSE, 5), ('PROTO0008', NOW(), FALSE, 5), ('PROTO0009', NOW(), FALSE, 4),
('PROTO0010', NOW(), FALSE, 4), ('PROTO0011', NOW(), FALSE, 5), ('PROTO0012', NOW(), FALSE, 5),
('PROTO0013', NOW(), FALSE, 4), ('PROTO0014', NOW(), FALSE, 4), ('PROTO0015', NOW(), FALSE, 5),
('PROTO0016', NOW(), TRUE, 4), ('PROTO0017', NOW(), TRUE, 5), ('PROTO0018', NOW(), TRUE, 4),
('PROTO0019', NOW(), TRUE, 5), ('PROTO0020', NOW(), TRUE, 4), ('PROTO0021', NOW(), TRUE, 5),
('PROTO0022', NOW(), TRUE, 4), ('PROTO0023', NOW(), TRUE, 5), ('PROTO0024', NOW(), TRUE, 4),
('PROTO0025', NOW(), TRUE, 5), ('PROTO0026', NOW(), TRUE, 4), ('PROTO0027', NOW(), TRUE, 5),
('PROTO0028', NOW(), TRUE, 4), ('PROTO0029', NOW(), TRUE, 5), ('PROTO0030', NOW(), TRUE, 4),
('PROTO0031', NOW(), TRUE, 5), ('PROTO0032', NOW(), TRUE, 4), ('PROTO0033', NOW(), TRUE, 5),
('PROTO0034', NOW(), TRUE, 4), ('PROTO0035', NOW(), TRUE, 5), ('PROTO0036', NOW(), TRUE, 4),
('PROTO0037', NOW(), TRUE, 5), ('PROTO0038', NOW(), TRUE, 4), ('PROTO0039', NOW(), TRUE, 5),
('PROTO0040', NOW(), TRUE, 4), ('PROTO0041', NOW(), TRUE, 5), ('PROTO0042', NOW(), TRUE, 4),
('PROTO0043', NOW(), TRUE, 5), ('PROTO0044', NOW(), TRUE, 4), ('PROTO0045', NOW(), TRUE, 5),
('PROTO0046', NOW(), TRUE, 4), ('PROTO0047', NOW(), TRUE, 5), ('PROTO0048', NOW(), TRUE, 4),
('PROTO0049', NOW(), TRUE, 5), ('PROTO0050', NOW(), TRUE, 4);

-- =========================
-- CONSULTAS (50 datas desta semana)
-- =========================
INSERT INTO Consulta (dataConsulta, descricao, status, tipoConsulta, fkMedico, fkPaciente, fkProtocolo) VALUES
(NOW(), 'Consulta 1', 'REALIZADA', 'CONSULTA', 1, 11, 1),
(DATE_ADD(NOW(), INTERVAL 1 DAY), 'Consulta 2', 'CONFIRMADA', 'CONSULTA', 2, 12, 2),
(DATE_ADD(NOW(), INTERVAL 2 DAY), 'Consulta 3', 'EM_ATENDIMENTO', 'CONSULTA', 3, 13, 3),
(DATE_ADD(NOW(), INTERVAL 3 DAY), 'Consulta 4', 'CANCELADA', 'CONSULTA', 4, 14, 4),
(DATE_ADD(NOW(), INTERVAL 4 DAY), 'Consulta 5', 'REALIZADA', 'CONSULTA', 5, 15, 5),
(DATE_ADD(NOW(), INTERVAL 5 DAY), 'Consulta 6', 'REALIZADA', 'CONSULTA', 1, 16, 6),
(DATE_ADD(NOW(), INTERVAL 6 DAY), 'Consulta 7', 'CONFIRMADA', 'CONSULTA', 2, 17, 7),
(DATE_ADD(NOW(), INTERVAL 7 DAY), 'Consulta 8', 'EM_ATENDIMENTO', 'CONSULTA', 3, 18, 8),
(DATE_ADD(NOW(), INTERVAL 1 DAY), 'Consulta 9', 'CANCELADA', 'CONSULTA', 4, 19, 9),
(DATE_ADD(NOW(), INTERVAL 2 DAY), 'Consulta 10', 'REALIZADA', 'CONSULTA', 5, 20, 10),
(DATE_ADD(NOW(), INTERVAL 3 DAY), 'Consulta 11', 'CONFIRMADA', 'CONSULTA', 1, 11, 11),
(DATE_ADD(NOW(), INTERVAL 4 DAY), 'Consulta 12', 'EM_ATENDIMENTO', 'CONSULTA', 2, 12, 12),
(DATE_ADD(NOW(), INTERVAL 5 DAY), 'Consulta 13', 'CANCELADA', 'CONSULTA', 3, 13, 13),
(DATE_ADD(NOW(), INTERVAL 6 DAY), 'Consulta 14', 'REALIZADA', 'CONSULTA', 4, 14, 14),
(DATE_ADD(NOW(), INTERVAL 7 DAY), 'Consulta 15', 'REALIZADA', 'CONSULTA', 5, 15, 15),
(DATE_ADD(NOW(), INTERVAL 1 DAY), 'Consulta 16', 'CONFIRMADA', 'CONSULTA', 1, 16, 16),
(DATE_ADD(NOW(), INTERVAL 2 DAY), 'Consulta 17', 'EM_ATENDIMENTO', 'CONSULTA', 2, 17, 17),
(DATE_ADD(NOW(), INTERVAL 3 DAY), 'Consulta 18', 'CANCELADA', 'CONSULTA', 3, 18, 18),
(DATE_ADD(NOW(), INTERVAL 4 DAY), 'Consulta 19', 'REALIZADA', 'CONSULTA', 4, 19, 19),
(DATE_ADD(NOW(), INTERVAL 5 DAY), 'Consulta 20', 'REALIZADA', 'CONSULTA', 5, 20, 20),
(DATE_ADD(NOW(), INTERVAL 6 DAY), 'Consulta 21', 'CONFIRMADA', 'CONSULTA', 1, 11, 21),
(DATE_ADD(NOW(), INTERVAL 7 DAY), 'Consulta 22', 'EM_ATENDIMENTO', 'CONSULTA', 2, 12, 22),
(DATE_ADD(NOW(), INTERVAL 1 DAY), 'Consulta 23', 'CANCELADA', 'CONSULTA', 3, 13, 23),
(DATE_ADD(NOW(), INTERVAL 2 DAY), 'Consulta 24', 'REALIZADA', 'CONSULTA', 4, 14, 24),
(DATE_ADD(NOW(), INTERVAL 3 DAY), 'Consulta 25', 'REALIZADA', 'CONSULTA', 5, 15, 25),
(DATE_ADD(NOW(), INTERVAL 4 DAY), 'Consulta 26', 'CONFIRMADA', 'CONSULTA', 1, 16, 26),
(DATE_ADD(NOW(), INTERVAL 5 DAY), 'Consulta 27', 'EM_ATENDIMENTO', 'CONSULTA', 2, 17, 27),
(DATE_ADD(NOW(), INTERVAL 6 DAY), 'Consulta 28', 'CANCELADA', 'CONSULTA', 3, 18, 28),
(DATE_ADD(NOW(), INTERVAL 7 DAY), 'Consulta 29', 'REALIZADA', 'CONSULTA', 4, 19, 29),
(DATE_ADD(NOW(), INTERVAL 1 DAY), 'Consulta 30', 'REALIZADA', 'CONSULTA', 5, 20, 30),
(DATE_ADD(NOW(), INTERVAL 2 DAY), 'Consulta 31', 'CONFIRMADA', 'CONSULTA', 1, 11, 31),
(DATE_ADD(NOW(), INTERVAL 3 DAY), 'Consulta 32', 'EM_ATENDIMENTO', 'CONSULTA', 2, 12, 32),
(DATE_ADD(NOW(), INTERVAL 4 DAY), 'Consulta 33', 'CANCELADA', 'CONSULTA', 3, 13, 33),
(DATE_ADD(NOW(), INTERVAL 5 DAY), 'Consulta 34', 'REALIZADA', 'CONSULTA', 4, 14, 34),
(DATE_ADD(NOW(), INTERVAL 6 DAY), 'Consulta 35', 'REALIZADA', 'CONSULTA', 5, 15, 35),
(DATE_ADD(NOW(), INTERVAL 7 DAY), 'Consulta 36', 'CONFIRMADA', 'CONSULTA', 1, 16, 36),
(DATE_ADD(NOW(), INTERVAL 1 DAY), 'Consulta 37', 'EM_ATENDIMENTO', 'CONSULTA', 2, 17, 37),
(DATE_ADD(NOW(), INTERVAL 2 DAY), 'Consulta 38', 'CANCELADA', 'CONSULTA', 3, 18, 38),
(DATE_ADD(NOW(), INTERVAL 3 DAY), 'Consulta 39', 'REALIZADA', 'CONSULTA', 4, 19, 39),
(DATE_ADD(NOW(), INTERVAL 4 DAY), 'Consulta 40', 'REALIZADA', 'CONSULTA', 5, 20, 40),
(DATE_ADD(NOW(), INTERVAL 5 DAY), 'Consulta 41', 'CONFIRMADA', 'CONSULTA', 1, 11, 41),
(DATE_ADD(NOW(), INTERVAL 6 DAY), 'Consulta 42', 'EM_ATENDIMENTO', 'CONSULTA', 2, 12, 42),
(DATE_ADD(NOW(), INTERVAL 7 DAY), 'Consulta 43', 'CANCELADA', 'CONSULTA', 3, 13, 43),
(DATE_ADD(NOW(), INTERVAL 1 DAY), 'Consulta 44', 'REALIZADA', 'CONSULTA', 4, 14, 44),
(DATE_ADD(NOW(), INTERVAL 2 DAY), 'Consulta 45', 'REALIZADA', 'CONSULTA', 5, 15, 45),
(DATE_ADD(NOW(), INTERVAL 3 DAY), 'Consulta 46', 'CONFIRMADA', 'CONSULTA', 1, 16, 46),
(DATE_ADD(NOW(), INTERVAL 4 DAY), 'Consulta 47', 'EM_ATENDIMENTO', 'CONSULTA', 2, 17, 47),
(DATE_ADD(NOW(), INTERVAL 5 DAY), 'Consulta 48', 'CANCELADA', 'CONSULTA', 3, 18, 48),
(DATE_ADD(NOW(), INTERVAL 6 DAY), 'Consulta 49', 'REALIZADA', 'CONSULTA', 4, 19, 49),
(DATE_ADD(NOW(), INTERVAL 7 DAY), 'Consulta 50', 'REALIZADA', 'CONSULTA', 5, 20, 50);
-- =========================
-- 50 PROTOCOLOS COM MEDICAMENTOS
-- =========================
INSERT INTO protocolo_medicamento (fkProtocolo, fkMedicamento) VALUES
(1,1),(1,2),(1,3),
(2,2),(2,5),
(3,1),(3,4),(3,6),
(4,3),(4,7),
(5,5),(5,8),(5,10),
(6,2),(6,6),(6,9),
(7,1),(7,3),(7,7),
(8,4),(8,5),(8,10),
(9,2),(9,8),(9,9),
(10,1),(10,6),(10,7),
(11,3),(11,5),(11,9),
(12,2),(12,4),(12,6),
(13,1),(13,7),(13,10),
(14,3),(14,5),(14,8),
(15,2),(15,4),(15,9),
(16,1),(16,6),(16,10),
(17,3),(17,5),(17,7),
(18,2),(18,4),(18,8),
(19,1),(19,6),(19,9),
(20,3),(20,5),(20,10),
(21,2),(21,4),(21,7),
(22,1),(22,6),(22,8),
(23,3),(23,5),(23,9),
(24,2),(24,4),(24,10),
(25,1),(25,6),(25,7),
(26,3),(26,5),(26,8),
(27,2),(27,4),(27,9),
(28,1),(28,6),(28,10),
(29,3),(29,5),(29,7),
(30,2),(30,4),(30,8),
(31,1),(31,6),(31,9),
(32,3),(32,5),(32,10),
(33,2),(33,4),(33,7),
(34,1),(34,6),(34,8),
(35,3),(35,5),(35,9),
(36,2),(36,4),(36,10),
(37,1),(37,6),(37,7),
(38,3),(38,5),(38,8),
(39,2),(39,4),(39,9),
(40,1),(40,6),(40,10),
(41,3),(41,5),(41,7),
(42,2),(42,4),(42,8),
(43,1),(43,6),(43,9),
(44,3),(44,5),(44,10),
(45,2),(45,4),(45,7),
(46,1),(46,6),(46,8),
(47,3),(47,5),(47,9),
(48,2),(48,4),(48,10),
(49,1),(49,6),(49,7),
(50,3),(50,5),(50,8);

-- =========================
-- USUÁRIOS DO SISTEMA
-- =========================
INSERT INTO UsuarioSistema (ativo, login, senha, tipoUser, fkUsuario) VALUES
-- Médicos
(TRUE, 'joao.medico', 'senha123', 'MEDICO', 1),
(TRUE, 'maria.medico', 'senha123', 'MEDICO', 2),
(TRUE, 'pedro.medico', 'senha123', 'MEDICO', 3),
(TRUE, 'lucas.medico', 'senha123', 'MEDICO', 6),
(TRUE, 'carla.medico', 'senha123', 'MEDICO', 7),

-- Farmacêuticos
(TRUE, 'ana.farmaceutico', 'senha123', 'FARMACEUTICO', 4),
(TRUE, 'carlos.farmaceutico', 'senha123', 'FARMACEUTICO', 5),
(TRUE, 'bruno.farmaceutico', 'senha123', 'FARMACEUTICO', 8),
(TRUE, 'renata.farmaceutico', 'senha123', 'FARMACEUTICO', 9),

-- Recepcionistas
(TRUE, 'tiago.recepcionista', 'senha123', 'RECEPCIONISTA', 10),
(TRUE, 'aline.recepcionista', 'senha123', 'RECEPCIONISTA', 11),

-- Administradores
(TRUE, 'admin.ti', 'senha123', 'ADMINISTRADOR', 12),
(TRUE, 'admin.rh', 'senha123', 'ADMINISTRADOR', 13);


SET FOREIGN_KEY_CHECKS = 1;
