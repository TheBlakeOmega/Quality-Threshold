-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: MapDb
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Create database MapDB
--

DROP DATABASE IF EXISTS MapDB;
CREATE DATABASE MapDB;

--
-- Create user MapUser and its privileges
--

DROP USER 'MapUser'@'localhost';
flush privileges;
CREATE USER 'MapUser'@'localhost' IDENTIFIED BY 'map';
GRANT CREATE, SELECT, INSERT, DELETE ON MapDB.* TO MapUser@localhost;

--
-- Table structure for table `playtennis`
--

DROP TABLE IF EXISTS `playtennis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playtennis` (
  `outlook` varchar(10) DEFAULT NULL,
  `temperature` float(5,2) DEFAULT NULL,
  `umidity` varchar(10) DEFAULT NULL,
  `wind` varchar(10) DEFAULT NULL,
  `play` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Dumping data for table `playtennis`
--

LOCK TABLES `playtennis` WRITE;
/*!40000 ALTER TABLE `playtennis` DISABLE KEYS */;
INSERT INTO `playtennis` VALUES ('sunny',30.30,'high','weak','no'),
('sunny',30.30,'high','strong','no'),
('overcast',30.00,'high','weak','yes'),
('rain',13.00,'high','weak','yes'),
('rain',0.00,'normal','weak','yes'),
('rain',0.00,'normal','strong','no'),
('overcast',0.10,'normal','strong','yes'),
('sunny',13.00,'high','weak','no'),
('sunny',0.10,'normal','weak','yes'),
('rain',12.00,'normal','weak','yes'),
('sunny',12.50,'normal','strong','yes'),
('overcast',12.50,'high','strong','yes'),
('overcast',29.21,'normal','weak','yes'),
('rain',12.50,'high','strong','no');
/*!40000 ALTER TABLE `playtennis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'mapdb'
--

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

--
-- Table structure for table `playfootball`
-- this table will be use for testing classes
--

DROP TABLE IF EXISTS `playfootball`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playfootball` (
  `outlook` varchar(10) DEFAULT NULL,
  `temperature` float(5,2) DEFAULT NULL,
  `umidity` varchar(10) DEFAULT NULL,
  `wind` varchar(10) DEFAULT NULL,
  `play` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;