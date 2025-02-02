CREATE DATABASE  IF NOT EXISTS `getdownwithflu` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `getdownwithflu`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: getdownwithflu
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `countries`
--

DROP TABLE IF EXISTS `countries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `countries` (
  `idcountries` int NOT NULL,
  `country_name` varchar(45) NOT NULL,
  `continent` varchar(45) NOT NULL,
  `population` bigint DEFAULT NULL,
  PRIMARY KEY (`idcountries`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `countries`
--

LOCK TABLES `countries` WRITE;
/*!40000 ALTER TABLE `countries` DISABLE KEYS */;
INSERT INTO `countries` VALUES (1,'China','Asia',1425000000),(2,'India','Asia',1420000000),(3,'United States','North America',332000000),(4,'Indonesia','Asia',277000000),(5,'Pakistan','Asia',240000000),(6,'Brazil','South America',216000000),(7,'Nigeria','Africa',223000000),(8,'Bangladesh','Asia',172000000),(9,'Russia','Europe/Asia',144000000),(10,'Mexico','North America',127000000),(11,'Japan','Asia',125000000),(12,'Ethiopia','Africa',126000000),(13,'Philippines','Asia',114000000),(14,'Egypt','Africa',111000000),(15,'Vietnam','Asia',100000000),(16,'Germany','Europe',84000000),(17,'Turkey','Europe/Asia',85000000),(18,'France','Europe',67000000),(19,'United Kingdom','Europe',68000000),(20,'South Africa','Africa',60000000);
/*!40000 ALTER TABLE `countries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diseases`
--

DROP TABLE IF EXISTS `diseases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diseases` (
  `iddiseases` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` text,
  `discovery_date` date NOT NULL,
  PRIMARY KEY (`iddiseases`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diseases`
--

LOCK TABLES `diseases` WRITE;
/*!40000 ALTER TABLE `diseases` DISABLE KEYS */;
INSERT INTO `diseases` VALUES (1,'Alzheimer\'s Diseases','A progressive neurological disorder impairing memory and cognition.','1948-04-04'),(2,'Hepatitis A','A viral infection affecting the liver, potentially chronic.','1933-09-09'),(3,'Malaria','A mosquito-borne disease caused by parasitic infection.','1931-01-27'),(4,'Dengue','A viral illness spread by mosquitoes, causing fever and pain.','1973-04-17'),(5,'Tuberculosi','A bacterial infection primarily affecting the lungs.','1996-08-14'),(6,'COVID-19','A contagious viral respiratory illness.','1947-05-23'),(7,'Tubern2','A bacterial infection primarily affecting the lungs.','2023-04-27'),(8,'Muokarditis','Serious infection in heart','1984-12-22'),(9,'Hepatitis B','A viral infection affecting the liver, potentially chronic.','1934-04-11'),(10,'Influenza','Influenza, commonly known as the flu, is a contagious respiratory illness caused by influenza viruses. It typically affects the nose, throat, and lungs, leading to symptoms such as fever, chills, cough, sore throat, muscle aches, fatigue, and nasal congestion. The flu spreads through respiratory droplets when an infected person coughs, sneezes, or talks, and it can range from mild to severe, sometimes resulting in complications like pneumonia. Vaccination is the primary method of prevention.','1944-11-14');
/*!40000 ALTER TABLE `diseases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diseases_cases`
--

DROP TABLE IF EXISTS `diseases_cases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diseases_cases` (
  `iddiseases_cases` int NOT NULL,
  `fk_id_diseases` int NOT NULL,
  `fk_id_country` int NOT NULL,
  `cases` int NOT NULL,
  `deaths` int NOT NULL,
  `recoverings` int NOT NULL,
  `report_date` date DEFAULT NULL,
  PRIMARY KEY (`iddiseases_cases`),
  KEY `fk_id_diseases_idx` (`fk_id_diseases`),
  KEY `fk_id_country_idx` (`fk_id_country`),
  CONSTRAINT `fk_id_country` FOREIGN KEY (`fk_id_country`) REFERENCES `countries` (`idcountries`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_id_diseases` FOREIGN KEY (`fk_id_diseases`) REFERENCES `diseases` (`iddiseases`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diseases_cases`
--

LOCK TABLES `diseases_cases` WRITE;
/*!40000 ALTER TABLE `diseases_cases` DISABLE KEYS */;
INSERT INTO `diseases_cases` VALUES (1,3,15,250,10,220,'1930-10-05'),(2,7,4,100,5,90,'1933-05-07'),(3,2,8,400,20,350,'1933-10-03'),(4,5,12,320,15,280,'1934-12-14'),(5,1,3,500,25,450,'1935-08-21'),(7,4,10,600,30,550,'1939-10-06'),(8,9,6,200,10,180,'1940-01-05'),(9,10,17,180,9,160,'1942-11-14'),(10,6,2,300,12,270,'1943-01-06'),(11,3,9,450,18,400,'1945-05-24'),(12,7,11,100,4,85,'1946-03-27'),(13,2,14,800,35,740,'1948-01-04'),(14,5,1,120,6,110,'1949-03-30'),(15,1,20,600,25,550,'1950-02-26'),(17,4,16,400,20,370,'1953-04-05'),(18,9,13,250,8,230,'1953-11-17'),(19,10,7,500,22,460,'1955-08-08'),(20,6,18,700,30,650,'1956-08-27'),(21,2,14,180,7,160,'1956-09-27'),(22,5,20,300,13,270,'1956-11-12'),(24,1,6,120,5,100,'1958-12-11'),(25,9,9,500,22,480,'1961-06-14'),(26,10,12,600,25,550,'1963-12-26'),(27,6,19,300,15,270,'1964-03-14'),(28,7,8,250,10,220,'1964-10-27'),(29,4,16,180,8,160,'1965-02-17'),(30,3,2,400,20,370,'1967-02-27'),(31,2,11,700,28,660,'1968-03-07'),(32,5,7,100,4,90,'1970-05-21'),(34,1,18,600,25,550,'1972-08-15'),(35,9,1,450,18,420,'1976-09-11'),(36,10,14,120,6,100,'1979-04-08'),(37,6,4,300,15,280,'1979-07-05'),(38,7,10,180,7,160,'1981-01-28'),(39,4,17,250,9,230,'1981-07-06'),(40,3,5,400,20,370,'1982-07-16'),(41,2,6,500,25,460,'1982-09-10'),(42,5,9,600,30,550,'1984-06-10'),(44,1,3,700,35,650,'1985-12-29'),(45,9,12,250,8,230,'1988-05-15'),(46,10,11,180,7,160,'1990-09-16'),(47,6,2,400,18,370,'1991-03-18'),(48,7,8,450,20,420,'1991-04-22'),(49,4,14,500,22,460,'1991-07-05'),(50,3,16,120,6,100,'1991-12-07'),(51,2,18,300,12,270,'1996-02-21'),(52,5,17,600,28,550,'1997-02-21'),(54,1,4,180,8,160,'1997-06-14'),(55,9,10,250,10,220,'1998-08-14'),(56,10,9,400,18,370,'1999-02-11'),(57,6,1,500,22,460,'2002-11-20'),(58,7,20,450,20,420,'2003-02-19'),(59,4,3,600,25,550,'2003-04-06'),(60,3,5,300,15,280,'2003-07-01'),(61,2,7,700,35,650,'2003-11-21'),(62,5,8,250,9,230,'2005-04-21'),(64,1,13,100,4,90,'2006-04-07'),(65,9,14,500,22,460,'2006-11-22'),(66,10,19,600,30,550,'2007-12-13'),(67,6,12,450,18,420,'2009-07-20'),(68,7,16,300,12,270,'2015-07-07'),(69,4,18,700,35,660,'2015-10-23'),(70,3,2,180,7,160,'2017-01-24');
/*!40000 ALTER TABLE `diseases_cases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reports` (
  `idreport` int NOT NULL,
  `fk_user_id` int DEFAULT NULL,
  `fk_id_deseases_rep` int DEFAULT NULL,
  `fk_id_country_report` int DEFAULT NULL,
  `comment` text NOT NULL,
  `report_date` date NOT NULL,
  PRIMARY KEY (`idreport`),
  KEY `fk_user_id_idx` (`fk_user_id`),
  KEY `fk_id_diseases_idx` (`fk_id_deseases_rep`),
  KEY `fk_id_diseases_report_idx` (`fk_id_deseases_rep`),
  KEY `fk_id_country_idx` (`fk_id_country_report`),
  CONSTRAINT `fk_id_country_report` FOREIGN KEY (`fk_id_country_report`) REFERENCES `countries` (`idcountries`) ON UPDATE CASCADE,
  CONSTRAINT `fk_id_diseases_report` FOREIGN KEY (`fk_id_deseases_rep`) REFERENCES `diseases` (`iddiseases`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`fk_user_id`) REFERENCES `user` (`iduser`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
INSERT INTO `reports` VALUES (2,2,1,19,'Need for immediate international collaboration emphasized!','1936-07-01'),(3,3,2,17,'Steady decline in recoveries reported.','1938-03-27'),(4,1,5,4,'Seasonal pattern observed in outbreak trends.','1940-08-15'),(5,2,7,13,'Emerging treatments reduce mortality rates.','1943-11-08'),(6,3,4,8,'Global mortality rates require close monitoring.','1950-02-20'),(7,2,10,1,'Increased funding in research is critical.','1963-04-17'),(8,1,6,20,'Significant progress made in vaccination rates.','1975-12-05'),(9,3,3,7,'Urban centers experience the highest infection rates.','1980-09-14'),(10,1,8,15,'Quarantine measures significantly slow the spread.','1992-01-01'),(11,2,9,2,'Localized outbreaks need rapid response strategies.','2000-06-25'),(12,3,10,5,'Surveillance systems help early detection of cases.','2010-03-10'),(13,1,1,16,'Rural areas face challenges in healthcare access.','2018-07-04'),(14,2,2,12,'Innovative policies improve recovery outcomes.','2020-11-11'),(15,3,5,10,'Preventative measures are key to disease control.','2023-05-21'),(17,2,4,6,'Data analysis reveals peak trends for infection cycles!!','2025-01-15'),(18,NULL,NULL,NULL,'Je suis malade','1986-03-27');
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `iduser` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`iduser`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'root','Govo1986','admin'),(2,'anal1','dz1','analyst'),(3,'user1','user1','simpleuser');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-02 15:00:20
