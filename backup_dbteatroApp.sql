-- MySQL dump 10.13  Distrib 9.6.0, for macos26.2 (arm64)
--
-- Host: 127.0.0.1    Database: dbteatroapp
-- ------------------------------------------------------
-- Server version	9.6.0

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ 'ae6152b4-1491-11f1-ab8e-e65909239582:1-1278';

--
-- Table structure for table `effettua`
--

DROP TABLE IF EXISTS `effettua`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `effettua` (
  `Utente` varchar(45) NOT NULL,
  `Codice_Prenotazione` char(8) NOT NULL,
  PRIMARY KEY (`Utente`,`Codice_Prenotazione`),
  KEY `effettua-prenotazione_idx` (`Codice_Prenotazione`),
  CONSTRAINT `effettua-prenotazione` FOREIGN KEY (`Codice_Prenotazione`) REFERENCES `prenotazione` (`Codice_Prenotazione`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `utente-effettua` FOREIGN KEY (`Utente`) REFERENCES `utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `effettua`
--

LOCK TABLES `effettua` WRITE;
/*!40000 ALTER TABLE `effettua` DISABLE KEYS */;
INSERT INTO `effettua` VALUES ('mario.rossi','PR000001'),('mario.rossi','PR000002'),('luigi.verdi','PR000003'),('anna.bianchi','PR000004'),('giuseppe.russo','PR000005'),('maria.ferrari','PR000006'),('maria.ferrari','PR000007'),('f.esposito','PR000008'),('f.esposito','PR000009'),('antonio.romano','PR000010'),('antonio.romano','PR000011'),('antonio.romano','PR000012'),('sofiapampolini','PR071599'),('laura.bruno','PR072181'),('sofiapampolini','PR345C73'),('sofiapampolini','PR4BDC30'),('sofiapampolini','PR501DED'),('f.deluca','PR5A38E6'),('sofiapampolini','PR7ED08F'),('sofiapampolini','PRC0A96D'),('f.deluca','PRC53D75'),('silvia.conti','PRE35891'),('stefano.pampolini','PRF67FFE'),('sofiapampolini','PRFD36A2');
/*!40000 ALTER TABLE `effettua` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifica`
--

DROP TABLE IF EXISTS `notifica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifica` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) DEFAULT NULL,
  `Messaggio` text,
  `Letta` tinyint DEFAULT '0',
  `Data_Creazione` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `notifica-utente_idx` (`Username`),
  CONSTRAINT `notifica-utente` FOREIGN KEY (`Username`) REFERENCES `utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifica`
--

LOCK TABLES `notifica` WRITE;
/*!40000 ALTER TABLE `notifica` DISABLE KEYS */;
INSERT INTO `notifica` VALUES (1,'maria.ferrari','La tua prenotazione PR000007 è stata modificata: nuova data 2026-11-15 nuova ora 20:30',0,'2026-08-28 11:41:34'),(2,'sofiapampolini','La tua prenotazione PR4BDC30 è stata modificata: nuova data 2026-11-15 nuova ora 20:30',1,'2026-08-28 11:41:34'),(3,'sofiapampolini','Attenzione: la fascia di punteggio Argento scadrà il 2026-09-06. I tuoi punti verranno azzerati al termine della validità.',1,'2026-08-31 06:39:52'),(4,'mario.rossi','Attenzione: la fascia di punteggio Platino scadrà il 2026-09-01. I tuoi punti verranno azzerati al termine della validità.',1,'2026-08-31 06:44:39'),(5,'mario.rossi','Attenzione: la fascia di punteggio Platino scadrà il 2026-08-31. I tuoi punti verranno azzerati al termine della validità.',1,'2026-08-31 06:57:56'),(6,'stefano.pampolini','Attenzione: la fascia di punteggio Platino scadrà il 2026-08-31. I tuoi punti verranno azzerati al termine della validità.',1,'2026-08-31 13:34:08'),(7,'mario.rossi','La fascia di punteggi Platino è scaduta in data 2026-08-31. I tuoi punti accumulati sono stati azzerati.',1,'2026-09-01 05:19:01');
/*!40000 ALTER TABLE `notifica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posto`
--

DROP TABLE IF EXISTS `posto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posto` (
  `Numero` int NOT NULL,
  `Codice_Teatro` char(8) NOT NULL,
  `Posizione` varchar(45) NOT NULL,
  PRIMARY KEY (`Numero`,`Codice_Teatro`),
  KEY `posto-teatro_idx` (`Codice_Teatro`),
  KEY `posto-tariffa_idx` (`Codice_Teatro`,`Posizione`),
  CONSTRAINT `posto-tariffa` FOREIGN KEY (`Codice_Teatro`, `Posizione`) REFERENCES `tariffa_posto` (`Codice_Teatro`, `Posizione`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `posto-teatro` FOREIGN KEY (`Codice_Teatro`) REFERENCES `teatro` (`Codice_Teatro`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posto`
--

LOCK TABLES `posto` WRITE;
/*!40000 ALTER TABLE `posto` DISABLE KEYS */;
INSERT INTO `posto` VALUES (11,'TE000001','Galleria'),(12,'TE000001','Galleria'),(21,'TE000001','Loggione'),(22,'TE000001','Loggione'),(1,'TE000001','Platea'),(2,'TE000001','Platea'),(3,'TE000001','Platea'),(4,'TE000001','Platea'),(5,'TE000001','Platea'),(11,'TE000002','Galleria'),(12,'TE000002','Galleria'),(13,'TE000002','Galleria'),(21,'TE000002','Loggione'),(22,'TE000002','Loggione'),(1,'TE000002','Platea'),(2,'TE000002','Platea'),(3,'TE000002','Platea'),(4,'TE000002','Platea'),(11,'TE000003','Galleria'),(12,'TE000003','Galleria'),(21,'TE000003','Loggione'),(1,'TE000003','Platea'),(2,'TE000003','Platea'),(11,'TE000004','Galleria'),(21,'TE000004','Loggione'),(1,'TE000004','Platea'),(2,'TE000004','Platea'),(11,'TE000005','Galleria'),(21,'TE000005','Loggione'),(1,'TE000005','Platea'),(11,'TE000006','Galleria'),(21,'TE000006','Loggione'),(1,'TE000006','Platea'),(11,'TE000007','Galleria'),(21,'TE000007','Loggione'),(1,'TE000007','Platea'),(11,'TE000008','Galleria'),(21,'TE000008','Loggione'),(1,'TE000008','Platea'),(11,'TE000009','Galleria'),(21,'TE000009','Loggione'),(1,'TE000009','Platea'),(11,'TE000010','Galleria'),(21,'TE000010','Loggione'),(1,'TE000010','Platea'),(11,'TE000011','Galleria'),(12,'TE000011','Galleria'),(13,'TE000011','Galleria'),(14,'TE000011','Galleria'),(15,'TE000011','Galleria'),(16,'TE000011','Galleria'),(17,'TE000011','Galleria'),(18,'TE000011','Galleria'),(19,'TE000011','Galleria'),(20,'TE000011','Galleria'),(21,'TE000011','Loggione'),(22,'TE000011','Loggione'),(23,'TE000011','Loggione'),(24,'TE000011','Loggione'),(25,'TE000011','Loggione'),(26,'TE000011','Loggione'),(27,'TE000011','Loggione'),(28,'TE000011','Loggione'),(29,'TE000011','Loggione'),(30,'TE000011','Loggione'),(1,'TE000011','Platea'),(2,'TE000011','Platea'),(3,'TE000011','Platea'),(4,'TE000011','Platea'),(5,'TE000011','Platea'),(6,'TE000011','Platea'),(7,'TE000011','Platea'),(8,'TE000011','Platea'),(9,'TE000011','Platea'),(10,'TE000011','Platea'),(31,'TE000012','Loggione'),(32,'TE000012','Loggione'),(33,'TE000012','Loggione'),(34,'TE000012','Loggione'),(35,'TE000012','Loggione'),(36,'TE000012','Loggione'),(37,'TE000012','Loggione'),(38,'TE000012','Loggione'),(39,'TE000012','Loggione'),(40,'TE000012','Loggione'),(41,'TE000012','Loggione'),(42,'TE000012','Loggione'),(43,'TE000012','Loggione'),(44,'TE000012','Loggione'),(45,'TE000012','Loggione'),(46,'TE000012','Loggione'),(47,'TE000012','Loggione'),(48,'TE000012','Loggione'),(49,'TE000012','Loggione'),(50,'TE000012','Loggione'),(1,'TE000012','Platea'),(2,'TE000012','Platea'),(3,'TE000012','Platea'),(4,'TE000012','Platea'),(5,'TE000012','Platea'),(6,'TE000012','Platea'),(7,'TE000012','Platea'),(8,'TE000012','Platea'),(9,'TE000012','Platea'),(10,'TE000012','Platea'),(11,'TE000012','Platea'),(12,'TE000012','Platea'),(13,'TE000012','Platea'),(14,'TE000012','Platea'),(15,'TE000012','Platea'),(16,'TE000012','Platea'),(17,'TE000012','Platea'),(18,'TE000012','Platea'),(19,'TE000012','Platea'),(20,'TE000012','Platea'),(21,'TE000012','Prima Galleria'),(22,'TE000012','Prima Galleria'),(23,'TE000012','Prima Galleria'),(24,'TE000012','Prima Galleria'),(25,'TE000012','Prima Galleria'),(26,'TE000012','Prima Galleria'),(27,'TE000012','Prima Galleria'),(28,'TE000012','Prima Galleria'),(29,'TE000012','Prima Galleria'),(30,'TE000012','Prima Galleria'),(51,'TE000012','Seconda Galleria'),(52,'TE000012','Seconda Galleria'),(53,'TE000012','Seconda Galleria'),(54,'TE000012','Seconda Galleria'),(55,'TE000012','Seconda Galleria'),(56,'TE000012','Seconda Galleria'),(57,'TE000012','Seconda Galleria'),(58,'TE000012','Seconda Galleria'),(59,'TE000012','Seconda Galleria'),(60,'TE000012','Seconda Galleria');
/*!40000 ALTER TABLE `posto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prenotazione`
--

DROP TABLE IF EXISTS `prenotazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prenotazione` (
  `Codice_Prenotazione` char(8) NOT NULL,
  `Stato` varchar(45) DEFAULT 'Attiva',
  `Id_Spettacolo` char(8) DEFAULT NULL,
  `Codice_Teatro` char(8) DEFAULT NULL,
  `Data_Prenotazione` timestamp NULL DEFAULT NULL,
  `N_Spettatori` int NOT NULL,
  `Data_Spettacolo` date DEFAULT NULL,
  PRIMARY KEY (`Codice_Prenotazione`),
  KEY `prenotazione-rappresenta_idx` (`Codice_Teatro`,`Id_Spettacolo`,`Data_Spettacolo`),
  CONSTRAINT `prenotazione-rappresenta` FOREIGN KEY (`Codice_Teatro`, `Id_Spettacolo`, `Data_Spettacolo`) REFERENCES `rappresenta` (`Codice_Teatro`, `Id_Spettacolo`, `Data`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prenotazione`
--

LOCK TABLES `prenotazione` WRITE;
/*!40000 ALTER TABLE `prenotazione` DISABLE KEYS */;
INSERT INTO `prenotazione` VALUES ('PR000001','Attiva','SPET0001','TE000001','2026-07-10 06:00:00',2,'2026-12-20'),('PR000002','Cancellata','SPET0006','TE000001','2026-07-11 07:30:00',1,'2026-11-22'),('PR000003','Attiva','SPET0002','TE000010','2026-07-12 11:15:00',3,'2026-11-16'),('PR000004','Attiva','SPET0003','TE000003','2026-07-13 05:00:00',1,'2026-11-17'),('PR000005','Attiva','SPET0003','TE000003','2026-07-14 14:20:00',2,'2026-11-17'),('PR000006','Attiva','SPET0005','TE000008','2026-07-15 10:00:00',1,'2026-11-19'),('PR000007','Cancellata','SPET0001','TE000001','2026-07-16 06:30:00',1,'2026-11-15'),('PR000008','Attiva','SPET0002','TE000010','2026-07-16 08:00:00',1,'2026-11-16'),('PR000009','Attiva','SPET0006','TE000001','2026-07-16 12:45:00',1,'2026-11-22'),('PR000010','Attiva','SPET0006','TE000001','2026-07-17 05:15:00',2,'2026-11-22'),('PR000011','Attiva','SPET0007','TE000002','2026-07-17 07:00:00',2,'2026-11-29'),('PR000012','Attiva','SPET0007','TE000002','2026-07-17 10:30:00',1,'2026-11-29'),('PR071599','Attiva','SPET0001','TE000001','2026-08-23 11:52:00',1,'2026-12-20'),('PR072181','Attiva','SPET0013','TE000012','2026-09-09 07:56:45',1,'2027-01-15'),('PR345C73','Attiva','SPET0003','TE000003','2026-08-23 12:35:14',1,'2026-11-17'),('PR4BDC30','Cancellata','SPET0001','TE000001','2026-08-25 07:02:57',1,'2026-11-15'),('PR501DED','Attiva','SPET0011','TE000011','2026-08-27 08:14:03',3,'2026-12-02'),('PR5A38E6','Cancellata','SPET0011','TE000011','2026-08-27 08:28:36',1,'2026-12-02'),('PR7ED08F','Attiva','SPET0013','TE000012','2026-09-01 06:52:07',2,'2027-01-15'),('PRC0A96D','Attiva','SPET0002','TE000010','2026-08-23 12:29:45',1,'2026-11-16'),('PRC53D75','Attiva','SPET0011','TE000011','2026-08-27 08:27:14',2,'2026-12-02'),('PRE35891','Attiva','SPET0011','TE000011','2026-08-27 08:17:43',2,'2026-12-02'),('PRF67FFE','Attiva','SPET0007','TE000002','2026-08-31 13:34:03',3,'2026-11-29'),('PRFD36A2','Attiva','SPET0006','TE000001','2026-08-23 12:44:13',2,'2026-11-22');
/*!40000 ALTER TABLE `prenotazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `punteggio`
--

DROP TABLE IF EXISTS `punteggio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `punteggio` (
  `Livello` varchar(45) NOT NULL,
  `Punteggio_Minimo` int NOT NULL,
  `Percentuale_Sconto` decimal(3,1) DEFAULT NULL,
  `Data_Scadenza` date DEFAULT NULL,
  PRIMARY KEY (`Livello`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `punteggio`
--

LOCK TABLES `punteggio` WRITE;
/*!40000 ALTER TABLE `punteggio` DISABLE KEYS */;
INSERT INTO `punteggio` VALUES ('Argento',300,3.0,'2027-01-01'),('Bronzo',0,0.0,'2027-01-01'),('Oro',700,5.0,'2027-01-01'),('Platino',1000,7.0,'2027-01-01');
/*!40000 ALTER TABLE `punteggio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rappresenta`
--

DROP TABLE IF EXISTS `rappresenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rappresenta` (
  `Codice_Teatro` char(8) NOT NULL,
  `Id_Spettacolo` char(8) NOT NULL,
  `Data` date NOT NULL,
  `Ora` time DEFAULT NULL,
  `Stato` varchar(45) DEFAULT 'Confermato',
  PRIMARY KEY (`Codice_Teatro`,`Id_Spettacolo`,`Data`),
  KEY `spettacolo-rappresenta_idx` (`Id_Spettacolo`),
  CONSTRAINT `spettacolo-rappresenta` FOREIGN KEY (`Id_Spettacolo`) REFERENCES `spettacolo` (`Id_Spettacolo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `teatro-rappresenta` FOREIGN KEY (`Codice_Teatro`) REFERENCES `teatro` (`Codice_Teatro`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rappresenta`
--

LOCK TABLES `rappresenta` WRITE;
/*!40000 ALTER TABLE `rappresenta` DISABLE KEYS */;
INSERT INTO `rappresenta` VALUES ('TE000001','SPET0001','2026-11-15','20:30:00','Annullato'),('TE000001','SPET0001','2026-12-20','20:30:00','Confermato'),('TE000001','SPET0006','2026-11-22','17:00:00','Confermato'),('TE000002','SPET0007','2026-11-29','20:00:00','Confermato'),('TE000003','SPET0003','2026-11-17','20:00:00','Confermato'),('TE000006','SPET0008','2026-11-11','21:00:00','Confermato'),('TE000006','SPET0009','2026-11-11','20:00:00','Annullato'),('TE000007','SPET0004','2026-11-18','20:45:00','Confermato'),('TE000007','SPET0012','2026-11-07','20:30:00','Confermato'),('TE000008','SPET0005','2026-11-19','21:00:00','Confermato'),('TE000009','SPET0012','2026-10-17','20:30:00','Confermato'),('TE000010','SPET0002','2026-11-16','21:00:00','Confermato'),('TE000010','SPET0010','2026-11-11','20:00:00','Confermato'),('TE000011','SPET0008','2027-01-15','21:00:00','Confermato'),('TE000011','SPET0011','2026-12-02','20:30:00','Confermato'),('TE000012','SPET0013','2027-01-15','21:00:00','Confermato');
/*!40000 ALTER TABLE `rappresenta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specifica_posto`
--

DROP TABLE IF EXISTS `specifica_posto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `specifica_posto` (
  `Codice_Prenotazione` char(8) NOT NULL,
  `Numero_Posto` int NOT NULL,
  `Codice_Teatro` char(8) NOT NULL,
  PRIMARY KEY (`Codice_Prenotazione`,`Numero_Posto`,`Codice_Teatro`),
  UNIQUE KEY `uk_posto_prenotazione` (`Codice_Teatro`,`Numero_Posto`,`Codice_Prenotazione`),
  KEY `posto_prenotato_idx` (`Numero_Posto`,`Codice_Teatro`),
  CONSTRAINT `posto_prenotazione` FOREIGN KEY (`Codice_Prenotazione`) REFERENCES `prenotazione` (`Codice_Prenotazione`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `posto_specificato` FOREIGN KEY (`Numero_Posto`, `Codice_Teatro`) REFERENCES `posto` (`Numero`, `Codice_Teatro`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specifica_posto`
--

LOCK TABLES `specifica_posto` WRITE;
/*!40000 ALTER TABLE `specifica_posto` DISABLE KEYS */;
INSERT INTO `specifica_posto` VALUES ('PR000001',1,'TE000001'),('PR000003',1,'TE000002'),('PRF67FFE',1,'TE000002'),('PR000004',1,'TE000003'),('PR000002',1,'TE000004'),('PR000006',1,'TE000005'),('PRC53D75',1,'TE000011'),('PR000001',2,'TE000001'),('PRFD36A2',2,'TE000001'),('PR000003',2,'TE000002'),('PRF67FFE',2,'TE000002'),('PRC53D75',2,'TE000011'),('PR000010',3,'TE000001'),('PR000003',3,'TE000002'),('PRF67FFE',3,'TE000002'),('PR000010',4,'TE000001'),('PR000008',4,'TE000002'),('PRFD36A2',5,'TE000001'),('PR000007',11,'TE000001'),('PR000011',11,'TE000002'),('PR000005',11,'TE000003'),('PRC0A96D',11,'TE000010'),('PR501DED',11,'TE000011'),('PR071599',12,'TE000001'),('PR000011',12,'TE000002'),('PR000005',12,'TE000003'),('PR501DED',12,'TE000011'),('PR501DED',13,'TE000011'),('PRE35891',14,'TE000011'),('PRE35891',15,'TE000011'),('PR5A38E6',17,'TE000011'),('PR000009',21,'TE000001'),('PR4BDC30',21,'TE000001'),('PR000012',21,'TE000002'),('PR345C73',21,'TE000003'),('PR7ED08F',21,'TE000012'),('PR7ED08F',22,'TE000012'),('PR072181',31,'TE000012');
/*!40000 ALTER TABLE `specifica_posto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spettacolo`
--

DROP TABLE IF EXISTS `spettacolo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spettacolo` (
  `Id_Spettacolo` char(8) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Genere` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id_Spettacolo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spettacolo`
--

LOCK TABLES `spettacolo` WRITE;
/*!40000 ALTER TABLE `spettacolo` DISABLE KEYS */;
INSERT INTO `spettacolo` VALUES ('SPET0001','La Traviata','Opera'),('SPET0002','Aida','Opera'),('SPET0003','Il Barbiere di Siviglia','Opera'),('SPET0004','Romeo e Giulietta','Prosa'),('SPET0005','Amleto','Prosa'),('SPET0006','Sogno di una Notte di Mezza Estate','Prosa'),('SPET0007','Tosca','Opera'),('SPET0008','Re Lear','Opera'),('SPET0009','Enrico VIII','Prosa'),('SPET0010','Macbeth','Prosa'),('SPET0011','Sei Personaggi in Cerca d\'Autore','Prosa'),('SPET0012','Hamilton','Musical'),('SPET0013','Wicked','Musical');
/*!40000 ALTER TABLE `spettacolo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariffa_posto`
--

DROP TABLE IF EXISTS `tariffa_posto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tariffa_posto` (
  `Codice_Teatro` char(8) NOT NULL,
  `Posizione` varchar(45) NOT NULL,
  `Prezzo` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`Codice_Teatro`,`Posizione`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariffa_posto`
--

LOCK TABLES `tariffa_posto` WRITE;
/*!40000 ALTER TABLE `tariffa_posto` DISABLE KEYS */;
INSERT INTO `tariffa_posto` VALUES ('TE000001','Galleria',35.00),('TE000001','Loggione',20.00),('TE000001','Platea',50.00),('TE000002','Galleria',45.00),('TE000002','Loggione',30.00),('TE000002','Platea',65.00),('TE000003','Galleria',40.00),('TE000003','Loggione',25.00),('TE000003','Platea',55.00),('TE000004','Galleria',30.00),('TE000004','Loggione',18.00),('TE000004','Platea',45.00),('TE000005','Galleria',28.00),('TE000005','Loggione',15.00),('TE000005','Platea',40.00),('TE000006','Galleria',32.00),('TE000006','Loggione',20.00),('TE000006','Platea',48.00),('TE000007','Galleria',25.00),('TE000007','Loggione',15.00),('TE000007','Platea',35.00),('TE000008','Galleria',30.00),('TE000008','Loggione',18.00),('TE000008','Platea',42.00),('TE000009','Galleria',50.00),('TE000009','Loggione',35.00),('TE000009','Platea',70.00),('TE000010','Galleria',85.00),('TE000010','Loggione',50.00),('TE000010','Platea',120.00),('TE000011','Galleria',40.00),('TE000011','Loggione',30.00),('TE000011','Platea',50.00),('TE000012','Loggione',25.00),('TE000012','Platea',45.00),('TE000012','Prima Galleria',30.00),('TE000012','Seconda Galleria',27.00);
/*!40000 ALTER TABLE `tariffa_posto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teatro`
--

DROP TABLE IF EXISTS `teatro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teatro` (
  `Codice_Teatro` char(8) NOT NULL,
  `Nome` varchar(45) NOT NULL,
  `Capacita` int NOT NULL,
  `Via` varchar(45) DEFAULT NULL,
  `Civico` varchar(45) DEFAULT NULL,
  `Cap` varchar(10) DEFAULT NULL,
  `Telefono` varchar(20) NOT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Citta` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Codice_Teatro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teatro`
--

LOCK TABLES `teatro` WRITE;
/*!40000 ALTER TABLE `teatro` DISABLE KEYS */;
INSERT INTO `teatro` VALUES ('TE000001','Teatro Storchi',900,'Largo Garibaldi','15','41100','059123456','storchi@teatro.it','Modena'),('TE000002','Teatro Regio',1200,'Strada Garibaldi','16','43100','052123456','regio@teatro.it','Parma'),('TE000003','Teatro Valli',1000,'Piazza Martiri','7','42100','052212345','valli@teatro.it','Reggio Emilia'),('TE000004','Teatro Alighieri',800,'Via Mariani','2','48100','054412345','alighieri@teatro.it','Ravenna'),('TE000005','Teatro Galli',700,'Piazza Cavour','22','47900','054112345','galli@teatro.it','Rimini'),('TE000006','Teatro Comunale',950,'Corso Giovecca','38','44100','053212345','comunale.fe@teatro.it','Ferrara'),('TE000007','Teatro Diego Fabbri',550,'Corso Diaz','47','47100','054312345','fabbri@teatro.it','Forlì'),('TE000008','Teatro Bonci',850,'Piazza Guidazzi','8','47500','054712345','bonci@teatro.it','Cesena'),('TE000009','Teatro Massimo',1300,'Piazza Verdi','1','90100','091123456','massimo@teatro.it','Palermo'),('TE000010','Teatro alla Scala',2000,'Via Filodrammatici','2','20100','021234567','scala@teatro.it','Milano'),('TE000011','Teatro Regio',30,'P.za Castello','215','10124','0118815241','regio@teatro.it','Torino'),('TE000012','Teatro Nazionale',60,'P.za Borgo Pila','42','16129','01053421','nazionale@teatro.it','Genova');
/*!40000 ALTER TABLE `teatro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `Username` varchar(45) NOT NULL,
  `Nome` varchar(45) NOT NULL,
  `Cognome` varchar(45) NOT NULL,
  `Via` varchar(45) DEFAULT NULL,
  `Civico` varchar(10) DEFAULT NULL,
  `Cap` varchar(10) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Email` varchar(45) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Punteggio` int DEFAULT '0',
  `Tipo` tinyint NOT NULL DEFAULT '0',
  `Citta` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES ('admin','Amministratore','Amministratore',NULL,NULL,NULL,NULL,'admin@gmail.com','adminn',0,1,NULL),('anna.bianchi','Anna','Bianchi','Corso Garibaldi','5','20100','3333333333','anna.bianchi@email.com','$2a$10$e8e...hashPass123!',550,0,'Milano'),('antonio.romano','Antonio','Romano','Via Napoli','101','40100','3337777777','antonio.romano@email.com','$2a$10$e8e...hashPass123!',220,0,'Bologna'),('elena.colombo','Elena','Colombo','Via Genova','3','20100','3338888888','elena.colombo@email.com','$2a$10$e8e...hashPass123!',350,0,'Milano'),('f.deluca','Federico','De Luca','Via Verona','31','80100','3346666666','f.deluca@email.com','$2a$10$e8e...hashPass123!',140,0,'Napoli'),('f.esposito','Francesca','Esposito','Via Torino','8','50100','3336666666','f.esposito@email.com','$2a$10$e8e...hashPass123!',850,0,'Firenze'),('g.ricci','Giovanni','Ricci','Via Bologna','56','00100','3339999999','g.ricci@email.com','$2a$10$e8e...hashPass123!',720,0,'Roma'),('giuseppe.russo','Giuseppe','Russo','Via Dante','45','80100','3334444444','giuseppe.russo@email.com','$2a$10$e8e...hashPass123!',800,0,'Napoli'),('jessiebaby','Lucia','Poletti','Bologna','1012','44124','1234567890','lucia.polietti@email.it','luciaa',0,0,'Ferrara'),('laura.bruno','Laura','Bruno','Via Palermo','4','20100','3343333333','laura.bruno@email.com','$2a$10$e8e...hashPass123!',1425,0,'Milano'),('luigi.verdi','Luigi','Verdi','Via Milano','22','00100','3332222222','luigi.verdi@email.com','$2a$10$e8e...hashPass123!',195,0,'Roma'),('maria.ferrari','Maria','Ferrari','Via Mazzini','12','10100','3335555555','maria.ferrari@email.com','$2a$10$e8e...hashPass123!',750,0,'Torino'),('mario.rossi','Mario','Rossi','Via Roma','10','40100','3331111111','mario.rossi@email.com','$2a$10$e8e...hashPass123!',0,0,'Bologna'),('paola.marino','Paola','Marino','Via Firenze','17','80100','3341111111','paola.marino@email.com','$2a$10$e8e...hashPass123!',0,0,'Napoli'),('silvia.conti','Silvia','Conti','Via Padova','88','50100','3345555555','silvia.conti@email.com','$2a$10$e8e...hashPass123!',80,0,'Firenze'),('sofiapampolini','Sofia','Pampolini','Bologna','1011','44124','1234567890','sofia.pampolini@edu.unife.it','sofiaa',443,0,'Ferrara'),('stefano.greco','Stefano','Greco','Via Venezia','99','40100','3342222222','stefano.greco@email.com','$2a$10$e8e...hashPass123!',0,0,'Bologna'),('stefano.pampolini','Stefano','Pampolini','Bologna','1011','44124','2345718908','stefano.pampolini@email.it','stefano',195,0,'Ferrara');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-10  9:40:13
