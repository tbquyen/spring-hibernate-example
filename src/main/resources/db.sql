CREATE schema jBpsPO7FJ6;
USE jBpsPO7FJ6;

-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: jbpspo7fj6
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `status` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Java',0),(2,'Python',0),(3,'Cshap',0),(4,'VBA',0);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `categoryID` int NOT NULL,
  `question` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `anser` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `optionA` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `optionB` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `optionC` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `optionD` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `duration` int NOT NULL,
  `explanation` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,1,'1 + 1 = ?','B','1','2','3','4',30,NULL),(2,1,'2 + 1 = ?','B','4','3','2','6',30,NULL),
(3,1,'3 + 1 = ?','C','6','2','4','1',30,NULL),(4,1,'4 + 1 = ?','B','6','5','4','1',30,NULL),(5,1,'5 + 1 = ?','A','6','2','7','3',30,NULL),
(6,1,'6 + 1 = ?','C','6','2','7','3',30,NULL),(7,1,'7 + 1 = ?','D','6','2','7','8',30,NULL),(8,1,'8 + 1 = ?','A','9','2','7','8',30,NULL),
(9,1,'9 + 1 = ?','B','9','10','7','8',30,NULL),(10,1,'10 + 1 = ?','C','9','10','11','8',30,NULL),(11,1,'11 + 1 = ?','D','9','10','13','12',30,NULL),
(12,1,'12 + 1 = ?','C','9','10','13','6',30,NULL),(13,1,'13 + 1 = ?','B','15','14','13','1',30,NULL),
(14,1,'14 + 1 = ?','A','15','10','18','1',30,NULL),(15,1,'15 + 1 = ?','A','16','10','18','3',30,NULL),
(16,1,'16 + 1 = ?','B','9','17','10','8',30,NULL),(17,1,'17 + 1 = ?','C','9','10','18','8',30,NULL),
(18,2,'1 + 1 = ?','B','1','2','3','4',30,NULL),(19,2,'2 + 1 = ?','B','4','3','2','6',30,NULL),
(20,2,'3 + 1 = ?','C','6','2','4','1',30,NULL),(21,2,'4 + 1 = ?','B','6','5','4','1',30,NULL),
(22,2,'5 + 1 = ?','A','6','2','7','3',30,NULL),(23,2,'6 + 1 = ?','C','6','2','7','3',30,NULL),
(24,2,'7 + 1 = ?','D','6','2','7','8',30,NULL),(25,2,'8 + 1 = ?','A','9','2','7','8',30,NULL),
(26,2,'9 + 1 = ?','B','9','10','7','8',30,NULL),(27,2,'10 + 1 = ?','C','9','10','11','8',30,NULL),
(28,2,'11 + 1 = ?','D','9','10','13','12',30,NULL),(29,2,'12 + 1 = ?','C','9','10','13','6',30,NULL),
(30,2,'13 + 1 = ?','B','15','14','13','1',30,NULL),(31,2,'14 + 1 = ?','A','15','10','18','1',30,NULL),
(32,2,'15 + 1 = ?','A','16','10','18','3',30,NULL),(33,2,'16 + 1 = ?','B','9','17','10','8',30,NULL),
(34,2,'17 + 1 = ?','C','9','10','18','8',30,NULL);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quiz` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(60) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `duration` int DEFAULT NULL,
  `timestart` datetime DEFAULT NULL,
  `timeend` datetime DEFAULT NULL,
  `questions` int DEFAULT NULL,
  `categoryID` int DEFAULT NULL,
  `passed` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

LOCK TABLES `quiz` WRITE;
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
INSERT INTO `quiz` VALUES (1,'mentor',150,'2021-10-28 19:29:36','2021-10-28 19:29:53',5,NULL,5,0);
/*!40000 ALTER TABLE `quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizinformation`
--

DROP TABLE IF EXISTS `quizinformation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quizinformation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quizID` int NOT NULL,
  `questionID` int NOT NULL,
  `anser` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `timeanser` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizinformation`
--

LOCK TABLES `quizinformation` WRITE;
/*!40000 ALTER TABLE `quizinformation` DISABLE KEYS */;
INSERT INTO `quizinformation` VALUES (1,1,13,'B','2021-10-28 19:29:41'),(2,1,4,'B','2021-10-28 19:29:44'),
(3,1,11,'D','2021-10-28 19:29:48'),(4,1,27,'C','2021-10-28 19:29:50'),(5,1,34,'C','2021-10-28 19:29:53');
/*!40000 ALTER TABLE `quizinformation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(60) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(60) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '$2a$10$ZGrzT6aCBOsh9C3JKlbGh.mN5augmOxe4zvlvzuh/3tGASFO3/b5C',
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `status` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('member','$2a$10$ZGrzT6aCBOsh9C3JKlbGh.mN5augmOxe4zvlvzuh/3tGASFO3/b5C','ROLE_MEMBER',1),
('mentor','$2a$10$ZGrzT6aCBOsh9C3JKlbGh.mN5augmOxe4zvlvzuh/3tGASFO3/b5C','ROLE_MENTOR',1);
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
