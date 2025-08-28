-- MySQL dump 10.13  Distrib 8.0.42, for Linux (x86_64)
--
-- Host: localhost    Database: dbsistemasaude
-- ------------------------------------------------------
-- Server version	8.0.42-0ubuntu0.24.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Administrador`
--

DROP TABLE IF EXISTS `Administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Administrador` (
  `id` bigint NOT NULL,
  `setor` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKm9xsbvhd1rlu9ce067pn43tgs` FOREIGN KEY (`id`) REFERENCES `Funcionario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Administrador`
--

LOCK TABLES `Administrador` WRITE;
/*!40000 ALTER TABLE `Administrador` DISABLE KEYS */;
INSERT INTO `Administrador` VALUES (12,'TI'),(13,'RH');
/*!40000 ALTER TABLE `Administrador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Consulta`
--

DROP TABLE IF EXISTS `Consulta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Consulta` (
  `dataConsulta` datetime(6) DEFAULT NULL,
  `fkMedico` bigint NOT NULL,
  `fkPaciente` bigint NOT NULL,
  `fkProtocolo` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tipoConsulta` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` enum('EM_ATENDIMENTO','CONFIRMADA','REALIZADA','CANCELADA') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bvj95bvhvby6ktmd1uk1na2sm` (`fkProtocolo`),
  KEY `FKi3ix3d8n2i6vrypfua1wxrx1f` (`fkMedico`),
  KEY `FK8ye5x4f7csogb7b73jyhl3g6k` (`fkPaciente`),
  CONSTRAINT `FK2grim9nfcra0yomggdddv5b8f` FOREIGN KEY (`fkProtocolo`) REFERENCES `Protocolo` (`id`),
  CONSTRAINT `FK8ye5x4f7csogb7b73jyhl3g6k` FOREIGN KEY (`fkPaciente`) REFERENCES `Paciente` (`id`),
  CONSTRAINT `FKi3ix3d8n2i6vrypfua1wxrx1f` FOREIGN KEY (`fkMedico`) REFERENCES `Medico` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Consulta`
--

LOCK TABLES `Consulta` WRITE;
/*!40000 ALTER TABLE `Consulta` DISABLE KEYS */;
INSERT INTO `Consulta` VALUES ('2025-08-27 17:09:26.000000',1,11,1,1,'Consulta 1','CONSULTA','REALIZADA'),('2025-08-28 17:09:26.000000',2,12,2,2,'Consulta 2','CONSULTA','CONFIRMADA'),('2025-08-29 17:09:26.000000',3,13,3,3,'Consulta 3','CONSULTA','EM_ATENDIMENTO'),('2025-08-30 17:09:26.000000',1,14,4,4,'Consulta 4','CONSULTA','CANCELADA'),('2025-08-31 17:09:26.000000',1,15,5,5,'Consulta 5','CONSULTA','REALIZADA'),('2025-09-01 17:09:26.000000',1,16,6,6,'Consulta 6','CONSULTA','REALIZADA'),('2025-09-02 17:09:26.000000',2,17,7,7,'Consulta 7','CONSULTA','CONFIRMADA'),('2025-09-03 17:09:26.000000',3,18,8,8,'Consulta 8','CONSULTA','EM_ATENDIMENTO'),('2025-08-28 17:09:26.000000',1,19,9,9,'Consulta 9','CONSULTA','CANCELADA'),('2025-08-29 17:09:26.000000',1,20,10,10,'Consulta 10','CONSULTA','REALIZADA'),('2025-08-30 17:09:26.000000',1,11,11,11,'Consulta 11','CONSULTA','CONFIRMADA'),('2025-08-31 17:09:26.000000',2,12,12,12,'Consulta 12','CONSULTA','EM_ATENDIMENTO'),('2025-09-01 17:09:26.000000',3,13,13,13,'Consulta 13','CONSULTA','CANCELADA'),('2025-09-02 17:09:26.000000',1,14,14,14,'Consulta 14','CONSULTA','REALIZADA'),('2025-09-03 17:09:26.000000',1,15,15,15,'Consulta 15','CONSULTA','REALIZADA'),('2025-08-28 17:09:26.000000',1,16,16,16,'Consulta 16','CONSULTA','CONFIRMADA'),('2025-08-29 17:09:26.000000',2,17,17,17,'Consulta 17','CONSULTA','EM_ATENDIMENTO'),('2025-08-30 17:09:26.000000',3,18,18,18,'Consulta 18','CONSULTA','CANCELADA'),('2025-08-31 17:09:26.000000',1,19,19,19,'Consulta 19','CONSULTA','REALIZADA'),('2025-09-01 17:09:26.000000',1,20,20,20,'Consulta 20','CONSULTA','REALIZADA'),('2025-09-02 17:09:26.000000',1,11,21,21,'Consulta 21','CONSULTA','CONFIRMADA'),('2025-09-03 17:09:26.000000',2,12,22,22,'Consulta 22','CONSULTA','EM_ATENDIMENTO'),('2025-08-28 17:09:26.000000',3,13,23,23,'Consulta 23','CONSULTA','CANCELADA'),('2025-08-29 17:09:26.000000',1,14,24,24,'Consulta 24','CONSULTA','REALIZADA'),('2025-08-30 17:09:26.000000',1,15,25,25,'Consulta 25','CONSULTA','REALIZADA'),('2025-08-31 17:09:26.000000',1,16,26,26,'Consulta 26','CONSULTA','CONFIRMADA'),('2025-09-01 17:09:26.000000',2,17,27,27,'Consulta 27','CONSULTA','EM_ATENDIMENTO'),('2025-09-02 17:09:26.000000',3,18,28,28,'Consulta 28','CONSULTA','CANCELADA'),('2025-09-03 17:09:26.000000',1,19,29,29,'Consulta 29','CONSULTA','REALIZADA'),('2025-08-28 17:09:26.000000',1,20,30,30,'Consulta 30','CONSULTA','REALIZADA'),('2025-08-29 17:09:26.000000',1,11,31,31,'Consulta 31','CONSULTA','CONFIRMADA'),('2025-08-30 17:09:26.000000',2,12,32,32,'Consulta 32','CONSULTA','EM_ATENDIMENTO'),('2025-08-31 17:09:26.000000',3,13,33,33,'Consulta 33','CONSULTA','CANCELADA'),('2025-09-01 17:09:26.000000',1,14,34,34,'Consulta 34','CONSULTA','REALIZADA'),('2025-09-02 17:09:26.000000',1,15,35,35,'Consulta 35','CONSULTA','REALIZADA'),('2025-09-03 17:09:26.000000',1,16,36,36,'Consulta 36','CONSULTA','CONFIRMADA'),('2025-08-28 17:09:26.000000',2,17,37,37,'Consulta 37','CONSULTA','EM_ATENDIMENTO'),('2025-08-29 17:09:26.000000',3,18,38,38,'Consulta 38','CONSULTA','CANCELADA'),('2025-08-30 17:09:26.000000',1,19,39,39,'Consulta 39','CONSULTA','REALIZADA'),('2025-08-31 17:09:26.000000',1,20,40,40,'Consulta 40','CONSULTA','REALIZADA'),('2025-09-01 17:09:26.000000',1,11,41,41,'Consulta 41','CONSULTA','CONFIRMADA'),('2025-09-02 17:09:26.000000',2,12,42,42,'Consulta 42','CONSULTA','EM_ATENDIMENTO'),('2025-09-03 17:09:26.000000',3,13,43,43,'Consulta 43','CONSULTA','CANCELADA'),('2025-08-28 17:09:26.000000',1,14,44,44,'Consulta 44','CONSULTA','REALIZADA'),('2025-08-29 17:09:26.000000',1,15,45,45,'Consulta 45','CONSULTA','REALIZADA'),('2025-08-30 17:09:26.000000',1,16,46,46,'Consulta 46','CONSULTA','CONFIRMADA'),('2025-08-31 17:09:26.000000',2,17,47,47,'Consulta 47','CONSULTA','EM_ATENDIMENTO'),('2025-09-01 17:09:26.000000',3,18,48,48,'Consulta 48','CONSULTA','CANCELADA'),('2025-09-02 17:09:26.000000',1,19,49,49,'Consulta 49','CONSULTA','REALIZADA'),('2025-09-03 17:09:26.000000',1,20,50,50,'Consulta 50','CONSULTA','REALIZADA');
/*!40000 ALTER TABLE `Consulta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Farmaceutico`
--

DROP TABLE IF EXISTS `Farmaceutico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Farmaceutico` (
  `id` bigint NOT NULL,
  `crf` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_h3ekh9l9sxmkqhkafljkksgwm` (`crf`),
  CONSTRAINT `FK764lixfvcf28gtofe069hw9t1` FOREIGN KEY (`id`) REFERENCES `Funcionario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Farmaceutico`
--

LOCK TABLES `Farmaceutico` WRITE;
/*!40000 ALTER TABLE `Farmaceutico` DISABLE KEYS */;
INSERT INTO `Farmaceutico` VALUES (4,'CRF/SP 12345'),(8,'CRF/SP 23456'),(9,'CRF/SP 34567'),(5,'CRF/SP 54321');
/*!40000 ALTER TABLE `Farmaceutico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Funcionario`
--

DROP TABLE IF EXISTS `Funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Funcionario` (
  `matricula` int NOT NULL,
  `dataAdimissao` datetime(6) DEFAULT NULL,
  `fkPessoa` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_finloe8x47gcj9htaxqphqcb6` (`matricula`),
  UNIQUE KEY `UK_kpirpsf49vslor3crieltnq0o` (`fkPessoa`),
  CONSTRAINT `FKql2wmg4x4ely9mv7926bimu8w` FOREIGN KEY (`fkPessoa`) REFERENCES `Pessoa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Funcionario`
--

LOCK TABLES `Funcionario` WRITE;
/*!40000 ALTER TABLE `Funcionario` DISABLE KEYS */;
INSERT INTO `Funcionario` VALUES (1001,'2020-01-15 00:00:00.000000',1,1),(1002,'2019-03-20 00:00:00.000000',2,2),(1003,'2021-05-10 00:00:00.000000',3,3),(1004,'2018-08-25 00:00:00.000000',4,4),(1005,'2022-02-15 00:00:00.000000',5,5),(1006,'2021-06-15 00:00:00.000000',6,6),(1007,'2022-07-20 00:00:00.000000',7,7),(1008,'2020-08-25 00:00:00.000000',8,8),(1009,'2019-09-30 00:00:00.000000',9,9),(1010,'2023-01-05 00:00:00.000000',10,10),(1011,'2025-08-27 23:21:27.000000',21,11),(1012,'2025-08-27 23:21:27.000000',22,12),(1013,'2025-08-27 23:21:27.000000',23,13);
/*!40000 ALTER TABLE `Funcionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LoteMedicamento`
--

DROP TABLE IF EXISTS `LoteMedicamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LoteMedicamento` (
  `quantidadeEntrada` int NOT NULL,
  `quantidadeEstoque` int NOT NULL,
  `dataFabricacao` datetime(6) DEFAULT NULL,
  `dataValidade` datetime(6) DEFAULT NULL,
  `fkTipoMedicamento` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` enum('VENCIDO','DISPONIVEL','ESGOTADO') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdogpmsd69r8r4u35eujjxnpfk` (`fkTipoMedicamento`),
  CONSTRAINT `FKdogpmsd69r8r4u35eujjxnpfk` FOREIGN KEY (`fkTipoMedicamento`) REFERENCES `TipoMedicamento` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LoteMedicamento`
--

LOCK TABLES `LoteMedicamento` WRITE;
/*!40000 ALTER TABLE `LoteMedicamento` DISABLE KEYS */;
INSERT INTO `LoteMedicamento` VALUES (100,0,'2023-01-01 00:00:00.000000','2024-01-01 00:00:00.000000',1,1,'VENCIDO'),(100,0,'2023-02-01 00:00:00.000000','2024-02-01 00:00:00.000000',2,2,'VENCIDO'),(100,0,'2023-03-01 00:00:00.000000','2024-03-01 00:00:00.000000',3,3,'VENCIDO'),(100,0,'2023-04-01 00:00:00.000000','2024-04-01 00:00:00.000000',4,4,'VENCIDO'),(100,0,'2023-05-01 00:00:00.000000','2024-05-01 00:00:00.000000',5,5,'VENCIDO'),(100,0,'2023-06-01 00:00:00.000000','2024-06-01 00:00:00.000000',6,6,'VENCIDO'),(100,0,'2023-07-01 00:00:00.000000','2024-07-01 00:00:00.000000',7,7,'VENCIDO'),(100,0,'2023-08-01 00:00:00.000000','2024-08-01 00:00:00.000000',8,8,'VENCIDO'),(100,0,'2023-09-01 00:00:00.000000','2024-09-01 00:00:00.000000',9,9,'VENCIDO'),(100,0,'2023-10-01 00:00:00.000000','2024-10-01 00:00:00.000000',10,10,'VENCIDO'),(120,110,'2024-01-01 00:00:00.000000','2025-11-01 00:00:00.000000',1,11,'DISPONIVEL'),(130,125,'2024-01-05 00:00:00.000000','2025-11-05 00:00:00.000000',2,12,'DISPONIVEL'),(140,130,'2024-02-01 00:00:00.000000','2025-12-01 00:00:00.000000',3,13,'DISPONIVEL'),(150,140,'2024-02-05 00:00:00.000000','2025-12-05 00:00:00.000000',4,14,'DISPONIVEL'),(160,150,'2024-03-01 00:00:00.000000','2025-12-01 00:00:00.000000',5,15,'DISPONIVEL'),(170,160,'2024-03-05 00:00:00.000000','2025-09-05 00:00:00.000000',6,16,'DISPONIVEL'),(180,170,'2024-04-01 00:00:00.000000','2025-12-01 00:00:00.000000',7,17,'DISPONIVEL'),(190,180,'2024-04-05 00:00:00.000000','2025-09-05 00:00:00.000000',8,18,'DISPONIVEL'),(200,190,'2024-05-01 00:00:00.000000','2025-10-01 00:00:00.000000',9,19,'DISPONIVEL'),(210,200,'2024-05-05 00:00:00.000000','2025-10-05 00:00:00.000000',10,20,'DISPONIVEL'),(220,210,'2024-06-01 00:00:00.000000','2025-10-01 00:00:00.000000',1,21,'DISPONIVEL'),(230,220,'2024-06-05 00:00:00.000000','2025-09-05 00:00:00.000000',2,22,'DISPONIVEL'),(240,230,'2024-07-01 00:00:00.000000','2025-09-01 00:00:00.000000',3,23,'DISPONIVEL'),(250,240,'2024-07-05 00:00:00.000000','2025-09-05 00:00:00.000000',4,24,'DISPONIVEL'),(260,250,'2024-08-01 00:00:00.000000','2025-09-01 00:00:00.000000',5,25,'DISPONIVEL'),(270,260,'2024-08-05 00:00:00.000000','2025-11-05 00:00:00.000000',6,26,'DISPONIVEL'),(280,270,'2024-09-01 00:00:00.000000','2025-09-01 00:00:00.000000',7,27,'DISPONIVEL'),(290,280,'2024-09-05 00:00:00.000000','2025-09-05 00:00:00.000000',8,28,'DISPONIVEL'),(300,290,'2024-10-01 00:00:00.000000','2025-10-01 00:00:00.000000',9,29,'DISPONIVEL'),(310,300,'2024-10-05 00:00:00.000000','2025-10-05 00:00:00.000000',10,30,'DISPONIVEL'),(320,310,'2024-11-01 00:00:00.000000','2025-11-01 00:00:00.000000',1,31,'DISPONIVEL'),(330,320,'2024-11-05 00:00:00.000000','2025-11-05 00:00:00.000000',2,32,'DISPONIVEL'),(340,330,'2024-12-01 00:00:00.000000','2025-12-01 00:00:00.000000',3,33,'DISPONIVEL'),(350,340,'2024-12-05 00:00:00.000000','2025-12-05 00:00:00.000000',4,34,'DISPONIVEL'),(360,350,'2025-01-01 00:00:00.000000','2026-01-01 00:00:00.000000',5,35,'DISPONIVEL'),(370,360,'2025-01-05 00:00:00.000000','2026-01-05 00:00:00.000000',6,36,'DISPONIVEL'),(380,370,'2025-02-01 00:00:00.000000','2026-02-01 00:00:00.000000',7,37,'DISPONIVEL'),(390,380,'2025-02-05 00:00:00.000000','2026-02-05 00:00:00.000000',8,38,'DISPONIVEL'),(400,390,'2025-03-01 00:00:00.000000','2026-03-01 00:00:00.000000',9,39,'DISPONIVEL'),(410,400,'2025-03-05 00:00:00.000000','2026-03-05 00:00:00.000000',10,40,'DISPONIVEL'),(420,410,'2025-04-01 00:00:00.000000','2026-04-01 00:00:00.000000',1,41,'DISPONIVEL'),(430,420,'2025-04-05 00:00:00.000000','2026-04-05 00:00:00.000000',2,42,'DISPONIVEL'),(440,430,'2025-05-01 00:00:00.000000','2026-05-01 00:00:00.000000',3,43,'DISPONIVEL'),(450,440,'2025-05-05 00:00:00.000000','2026-05-05 00:00:00.000000',4,44,'DISPONIVEL'),(460,450,'2025-06-01 00:00:00.000000','2026-06-01 00:00:00.000000',5,45,'DISPONIVEL'),(470,460,'2025-06-05 00:00:00.000000','2026-06-05 00:00:00.000000',6,46,'DISPONIVEL'),(480,470,'2025-07-01 00:00:00.000000','2026-07-01 00:00:00.000000',7,47,'DISPONIVEL'),(490,480,'2025-07-05 00:00:00.000000','2026-07-05 00:00:00.000000',8,48,'DISPONIVEL'),(500,490,'2025-08-01 00:00:00.000000','2026-12-01 00:00:00.000000',9,49,'DISPONIVEL'),(510,500,'2025-08-05 00:00:00.000000','2026-08-05 00:00:00.000000',10,50,'DISPONIVEL');
/*!40000 ALTER TABLE `LoteMedicamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Medicamento`
--

DROP TABLE IF EXISTS `Medicamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Medicamento` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_n7rdw7xjmuw7fo39dkeut7rg3` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Medicamento`
--

LOCK TABLES `Medicamento` WRITE;
/*!40000 ALTER TABLE `Medicamento` DISABLE KEYS */;
INSERT INTO `Medicamento` VALUES (2,'Amoxicilina'),(6,'Atenolol'),(7,'Dipirona'),(8,'Hidroclorotiazida'),(10,'Insulina'),(3,'Losartana'),(4,'Metformina'),(5,'Omeprazol'),(1,'Paracetamol'),(9,'Sinvastatina');
/*!40000 ALTER TABLE `Medicamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Medico`
--

DROP TABLE IF EXISTS `Medico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Medico` (
  `id` bigint NOT NULL,
  `crm` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `especialidade` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nomeFantasia` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4mxaf7akak8hrpgyr1u46yw1k` (`crm`),
  CONSTRAINT `FKbur5cjowqxj8qa09tx2r44dvw` FOREIGN KEY (`id`) REFERENCES `Funcionario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Medico`
--

LOCK TABLES `Medico` WRITE;
/*!40000 ALTER TABLE `Medico` DISABLE KEYS */;
INSERT INTO `Medico` VALUES (1,'CRM/SP 123456','Cardiologia','Dr. João Silva'),(2,'CRM/SP 654321','Pediatria','Dra. Maria Santos'),(3,'CRM/SP 789012','Ortopedia','Dr. Pedro Oliveira'),(6,'CRM/SP 234567','Dermatologia','Dr. Lucas Almeida'),(7,'CRM/SP 345678','Neurologia','Dra. Carla Ribeiro');
/*!40000 ALTER TABLE `Medico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Paciente`
--

DROP TABLE IF EXISTS `Paciente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Paciente` (
  `fkPessoa` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `numeroSus` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_h51qsasgm9pw7a78wsk5l1bv8` (`numeroSus`),
  UNIQUE KEY `UK_myrvvkv9h3vu98y6okuaybnhe` (`fkPessoa`),
  CONSTRAINT `FKou3r334ew6bfr34jef8dk5n84` FOREIGN KEY (`fkPessoa`) REFERENCES `Pessoa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Paciente`
--

LOCK TABLES `Paciente` WRITE;
/*!40000 ALTER TABLE `Paciente` DISABLE KEYS */;
INSERT INTO `Paciente` VALUES (1,1,'SUS111222333'),(2,2,'SUS222333444'),(3,3,'SUS333444555'),(4,4,'SUS444555666'),(5,5,'SUS555666777'),(6,6,'SUS111555888'),(7,7,'SUS222666999'),(8,8,'SUS333777000'),(9,9,'SUS444888111'),(10,10,'SUS555999222'),(11,11,'SUS123456789'),(12,12,'SUS987654321'),(13,13,'SUS456789123'),(14,14,'SUS789123456'),(15,15,'SUS321654987'),(16,16,'SUS147852369'),(17,17,'SUS258963147'),(18,18,'SUS369741258'),(19,19,'SUS741852963'),(20,20,'SUS852963741'),(21,21,'SUS000021'),(22,22,'SUS000022'),(23,23,'SUS000023');
/*!40000 ALTER TABLE `Paciente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pessoa`
--

DROP TABLE IF EXISTS `Pessoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Pessoa` (
  `sexo` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dataNascimento` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cpf` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `endereco` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nome` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `telefone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gej40f8jfd5efnwlggtpwjloo` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pessoa`
--

LOCK TABLES `Pessoa` WRITE;
/*!40000 ALTER TABLE `Pessoa` DISABLE KEYS */;
INSERT INTO `Pessoa` VALUES ('M','1980-05-15 00:00:00.000000',1,'111.222.333-44','joao.silva@email.com','Rua A, 123','João Silva','(11) 9999-8888'),('F','1985-08-20 00:00:00.000000',2,'222.333.444-55','maria.santos@email.com','Rua B, 456','Maria Santos','(11) 9888-7777'),('M','1978-12-10 00:00:00.000000',3,'333.444.555-66','pedro.oliveira@email.com','Rua C, 789','Pedro Oliveira','(11) 9777-6666'),('F','1990-03-25 00:00:00.000000',4,'444.555.666-77','ana.costa@email.com','Rua D, 101','Ana Costa','(11) 9666-5555'),('M','1982-07-30 00:00:00.000000',5,'555.666.777-88','carlos.pereira@email.com','Rua E, 202','Carlos Pereira','(11) 9555-4444'),('M','1990-01-15 00:00:00.000000',6,'111.555.888-99','lucas.almeida@email.com','Rua K, 808','Lucas Almeida','(11) 9001-1111'),('F','1991-02-20 00:00:00.000000',7,'222.666.999-00','carla.ribeiro@email.com','Rua L, 909','Carla Ribeiro','(11) 9002-2222'),('M','1989-03-05 00:00:00.000000',8,'333.777.000-11','bruno.lima@email.com','Rua M, 1010','Bruno Lima','(11) 9003-3333'),('F','1987-04-10 00:00:00.000000',9,'444.888.111-22','renata.costa@email.com','Rua N, 1111','Renata Costa','(11) 9004-4444'),('M','1992-05-12 00:00:00.000000',10,'555.999.222-33','tiago.ferreira@email.com','Rua O, 1212','Tiago Ferreira','(11) 9005-5555'),('M','1995-01-10 00:00:00.000000',11,'666.777.888-99','paulo.souza@email.com','Rua F, 303','Paulo Souza','(11) 9444-3333'),('F','1988-06-15 00:00:00.000000',12,'777.888.999-00','juliana.lima@email.com','Rua G, 404','Juliana Lima','(11) 9333-2222'),('M','1975-09-20 00:00:00.000000',13,'888.999.000-11','ricardo.martins@email.com','Rua H, 505','Ricardo Martins','(11) 9222-1111'),('F','1992-11-05 00:00:00.000000',14,'999.000.111-22','fernanda.alves@email.com','Rua I, 606','Fernanda Alves','(11) 9111-0000'),('M','1987-04-18 00:00:00.000000',15,'000.111.222-33','marcos.ferreira@email.com','Rua J, 707','Marcos Ferreira','(11) 9000-9999'),('F','1993-03-22 00:00:00.000000',16,'111.111.111-11','larissa.rocha@email.com','Rua P, 1313','Larissa Rocha','(11) 9441-1111'),('M','1986-07-30 00:00:00.000000',17,'222.222.222-22','fernando.gomes@email.com','Rua Q, 1414','Fernando Gomes','(11) 9442-2222'),('F','1990-11-12 00:00:00.000000',18,'333.333.333-33','mariana.costa@email.com','Rua R, 1515','Mariana Costa','(11) 9443-3333'),('M','1985-05-05 00:00:00.000000',19,'444.444.444-44','gabriel.lima@email.com','Rua S, 1616','Gabriel Lima','(11) 9444-4444'),('F','1991-08-08 00:00:00.000000',20,'555.555.555-55','beatriz.alves@email.com','Rua T, 1717','Beatriz Alves','(11) 9445-5555'),('F','1994-07-15 00:00:00.000000',21,'666.777.888-00','aline.souza@email.com','Rua U, 1818','Aline Souza','(11) 9555-6666'),('M','1983-04-22 00:00:00.000000',22,'777.888.999-11','tiago.silva@email.com','Rua V, 1919','Tiago Silva','(11) 9666-7777'),('F','1985-09-10 00:00:00.000000',23,'888.999.000-22','carla.ferreira@email.com','Rua W, 2020','Carla Ferreira','(11) 9777-8888');
/*!40000 ALTER TABLE `Pessoa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Protocolo`
--

DROP TABLE IF EXISTS `Protocolo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Protocolo` (
  `status` bit(1) DEFAULT NULL,
  `dataEntrega` datetime(6) DEFAULT NULL,
  `dataGerada` datetime(6) DEFAULT NULL,
  `fkFarmaceutico` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8sgk28e8xm5x7oilpgcr5shh9` (`fkFarmaceutico`),
  CONSTRAINT `FK8sgk28e8xm5x7oilpgcr5shh9` FOREIGN KEY (`fkFarmaceutico`) REFERENCES `Farmaceutico` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Protocolo`
--

LOCK TABLES `Protocolo` WRITE;
/*!40000 ALTER TABLE `Protocolo` DISABLE KEYS */;
INSERT INTO `Protocolo` VALUES (_binary '\0',NULL,'2025-08-27 17:09:25.000000',NULL,1,'PROTO0001'),(_binary '\0',NULL,'2025-08-27 17:09:25.000000',NULL,2,'PROTO0002'),(_binary '\0',NULL,'2025-08-27 17:09:25.000000',NULL,3,'PROTO0003'),(_binary '\0',NULL,'2025-08-27 17:09:25.000000',NULL,4,'PROTO0004'),(_binary '\0',NULL,'2025-08-27 17:09:25.000000',NULL,5,'PROTO0005'),(_binary '\0',NULL,'2025-08-27 17:09:25.000000',NULL,6,'PROTO0006'),(_binary '\0',NULL,'2025-08-27 17:09:25.000000',NULL,7,'PROTO0007'),(_binary '\0',NULL,'2025-08-27 17:09:25.000000',NULL,8,'PROTO0008'),(_binary '\0',NULL,'2025-08-27 17:09:25.000000',NULL,9,'PROTO0009'),(_binary '\0',NULL,'2025-08-27 17:09:25.000000',NULL,10,'PROTO0010'),(_binary '\0',NULL,'2025-08-27 17:09:25.000000',NULL,11,'PROTO0011'),(_binary '\0',NULL,'2025-08-27 17:09:25.000000',NULL,12,'PROTO0012'),(_binary '\0',NULL,'2025-08-27 17:09:25.000000',NULL,13,'PROTO0013'),(_binary '\0',NULL,'2025-08-27 17:09:25.000000',NULL,14,'PROTO0014'),(_binary '\0',NULL,'2025-08-27 17:09:25.000000',NULL,15,'PROTO0015'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,16,'PROTO0016'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,17,'PROTO0017'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,18,'PROTO0018'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,19,'PROTO0019'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,20,'PROTO0020'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,21,'PROTO0021'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,22,'PROTO0022'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,23,'PROTO0023'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,24,'PROTO0024'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,25,'PROTO0025'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,26,'PROTO0026'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,27,'PROTO0027'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,28,'PROTO0028'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,29,'PROTO0029'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,30,'PROTO0030'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,31,'PROTO0031'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,32,'PROTO0032'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,33,'PROTO0033'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,34,'PROTO0034'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,35,'PROTO0035'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,36,'PROTO0036'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,37,'PROTO0037'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,38,'PROTO0038'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,39,'PROTO0039'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,40,'PROTO0040'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,41,'PROTO0041'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,42,'PROTO0042'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,43,'PROTO0043'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,44,'PROTO0044'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,45,'PROTO0045'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,46,'PROTO0046'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,47,'PROTO0047'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,48,'PROTO0048'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,49,'PROTO0049'),(_binary '',NULL,'2025-08-27 17:09:25.000000',NULL,50,'PROTO0050');
/*!40000 ALTER TABLE `Protocolo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Recepcionista`
--

DROP TABLE IF EXISTS `Recepcionista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Recepcionista` (
  `id` bigint NOT NULL,
  `setor` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKowf04l5fq6igg5fjnmliandgu` FOREIGN KEY (`id`) REFERENCES `Funcionario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Recepcionista`
--

LOCK TABLES `Recepcionista` WRITE;
/*!40000 ALTER TABLE `Recepcionista` DISABLE KEYS */;
INSERT INTO `Recepcionista` VALUES (10,'Atendimento'),(11,'Triagem');
/*!40000 ALTER TABLE `Recepcionista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TipoMedicamento`
--

DROP TABLE IF EXISTS `TipoMedicamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TipoMedicamento` (
  `estoqueMinimo` int NOT NULL,
  `quantidadeCaixa` int NOT NULL,
  `fkMedicamento` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tipo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `unidadeMedida` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3i962c2xo1p5sbjh9tkoskqvs` (`fkMedicamento`),
  CONSTRAINT `FK3i962c2xo1p5sbjh9tkoskqvs` FOREIGN KEY (`fkMedicamento`) REFERENCES `Medicamento` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TipoMedicamento`
--

LOCK TABLES `TipoMedicamento` WRITE;
/*!40000 ALTER TABLE `TipoMedicamento` DISABLE KEYS */;
INSERT INTO `TipoMedicamento` VALUES (50,20,1,1,'Analgésico e antitérmico','Comprimido','500mg'),(30,15,2,2,'Antibiótico','Comprimido','500mg'),(40,30,3,3,'Anti-hipertensivo','Comprimido','50mg'),(60,30,4,4,'Antidiabético','Comprimido','850mg'),(45,28,5,5,'Protetor gástrico','Cápsula','20mg'),(35,30,6,6,'Beta-bloqueador','Comprimido','50mg'),(70,10,7,7,'Analgésico','Comprimido','500mg'),(25,30,8,8,'Diurético','Comprimido','25mg'),(20,30,9,9,'Estatina','Comprimido','20mg'),(15,1,10,10,'Hormônio','Injetável','100UI/ml');
/*!40000 ALTER TABLE `TipoMedicamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UsuarioSistema`
--

DROP TABLE IF EXISTS `UsuarioSistema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UsuarioSistema` (
  `ativo` bit(1) NOT NULL,
  `fkUsuario` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `senha` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tipoUser` enum('MEDICO','FARMACEUTICO','RECEPCIONISTA','ADMINISTRADOR') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qtywy49wa87dwupnvfi1s5aab` (`fkUsuario`),
  CONSTRAINT `FK3923hx2apbnh4hrygip4x51j2` FOREIGN KEY (`fkUsuario`) REFERENCES `Funcionario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UsuarioSistema`
--

LOCK TABLES `UsuarioSistema` WRITE;
/*!40000 ALTER TABLE `UsuarioSistema` DISABLE KEYS */;
INSERT INTO `UsuarioSistema` VALUES (_binary '',1,1,'joao.medico','senha123','MEDICO'),(_binary '',2,2,'maria.medico','senha123','MEDICO'),(_binary '',3,3,'pedro.medico','senha123','MEDICO'),(_binary '',6,4,'lucas.medico','senha123','MEDICO'),(_binary '',7,5,'carla.medico','senha123','MEDICO'),(_binary '',4,6,'ana.farmaceutico','senha123','FARMACEUTICO'),(_binary '',5,7,'carlos.farmaceutico','senha123','FARMACEUTICO'),(_binary '',8,8,'bruno.farmaceutico','senha123','FARMACEUTICO'),(_binary '',9,9,'renata.farmaceutico','senha123','FARMACEUTICO'),(_binary '',10,10,'tiago.recepcionista','senha123','RECEPCIONISTA'),(_binary '',11,11,'aline.recepcionista','senha123','RECEPCIONISTA'),(_binary '',12,12,'admin.ti','senha123','ADMINISTRADOR'),(_binary '',13,13,'admin.rh','senha123','ADMINISTRADOR');
/*!40000 ALTER TABLE `UsuarioSistema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `protocolo_medicamento`
--

DROP TABLE IF EXISTS `protocolo_medicamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `protocolo_medicamento` (
  `fkMedicamento` bigint NOT NULL,
  `fkProtocolo` bigint NOT NULL,
  KEY `FKdrxoyduefven8w7tlba99jnol` (`fkMedicamento`),
  KEY `FK5q92mcadswrit5di09u60sjy0` (`fkProtocolo`),
  CONSTRAINT `FK5q92mcadswrit5di09u60sjy0` FOREIGN KEY (`fkProtocolo`) REFERENCES `Protocolo` (`id`),
  CONSTRAINT `FKdrxoyduefven8w7tlba99jnol` FOREIGN KEY (`fkMedicamento`) REFERENCES `Medicamento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `protocolo_medicamento`
--

LOCK TABLES `protocolo_medicamento` WRITE;
/*!40000 ALTER TABLE `protocolo_medicamento` DISABLE KEYS */;
INSERT INTO `protocolo_medicamento` VALUES (1,1),(2,1),(3,1),(2,2),(5,2),(1,3),(4,3),(6,3),(3,4),(7,4),(5,5),(8,5),(10,5),(2,6),(6,6),(9,6),(1,7),(3,7),(7,7),(4,8),(5,8),(10,8),(2,9),(8,9),(9,9),(1,10),(6,10),(7,10),(3,11),(5,11),(9,11),(2,12),(4,12),(6,12),(1,13),(7,13),(10,13),(3,14),(5,14),(8,14),(2,15),(4,15),(9,15),(1,16),(6,16),(10,16),(3,17),(5,17),(7,17),(2,18),(4,18),(8,18),(1,19),(6,19),(9,19),(3,20),(5,20),(10,20),(2,21),(4,21),(7,21),(1,22),(6,22),(8,22),(3,23),(5,23),(9,23),(2,24),(4,24),(10,24),(1,25),(6,25),(7,25),(3,26),(5,26),(8,26),(2,27),(4,27),(9,27),(1,28),(6,28),(10,28),(3,29),(5,29),(7,29),(2,30),(4,30),(8,30),(1,31),(6,31),(9,31),(3,32),(5,32),(10,32),(2,33),(4,33),(7,33),(1,34),(6,34),(8,34),(3,35),(5,35),(9,35),(2,36),(4,36),(10,36),(1,37),(6,37),(7,37),(3,38),(5,38),(8,38),(2,39),(4,39),(9,39),(1,40),(6,40),(10,40),(3,41),(5,41),(7,41),(2,42),(4,42),(8,42),(1,43),(6,43),(9,43),(3,44),(5,44),(10,44),(2,45),(4,45),(7,45),(1,46),(6,46),(8,46),(3,47),(5,47),(9,47),(2,48),(4,48),(10,48),(1,49),(6,49),(7,49),(3,50),(5,50),(8,50);
/*!40000 ALTER TABLE `protocolo_medicamento` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-27 23:22:53
