-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: us-cdbr-iron-east-05.cleardb.net    Database: ad_29c3973ecda51e6
-- ------------------------------------------------------
-- Server version	5.6.42-log

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
-- Table structure for table `drink`
--

DROP TABLE IF EXISTS `drink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drink` (
  `id` int(11) NOT NULL,
  `chill_type` int(11) DEFAULT NULL,
  `clipart` varchar(255) DEFAULT NULL,
  `glass_type` int(11) DEFAULT NULL,
  `instructions` varchar(255) DEFAULT NULL,
  `mix_type` int(11) DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drink`
--

LOCK TABLES `drink` WRITE;
/*!40000 ALTER TABLE `drink` DISABLE KEYS */;
INSERT INTO `drink` VALUES (53,1,'/img/coupe.jpg',1,'Shake and strain to chilled glass, garnish with Luxardo Cherry.',0,'Corpse Reviver #2'),(55,0,'/img/rocks.jpg',3,'Muddle sugar, lemon peel, and bitters with a splash of water. Add ice and bourbon. Stir.',2,'Bevo Old Fashioned'),(57,0,'/img/rocks.jpg',3,'Muddle sugar, lemon peel, and bitters with a splash of water. Add ice and rye whiskey. Stir.',2,'Really Rye Old Fashioned'),(58,1,'/img/coupe.jpg',1,'Stir and strain to chilled glass. Garnish with lemon peel.',1,'Rye Manhattan'),(59,1,'/img/coupe.jpg',1,'Stir and strain to chilled glass. Garnish with cherry.',1,'Midtowner Manhattan'),(62,0,'/img/pint.jpg',5,'',2,'Suffering Bastard'),(65,0,'/img/rocks.jpg',3,'Pour ginger beer over ice, leaving 1 inch of room at the top of the glass. Float a shot of Dark Rum on top. Garnish with a lime wedge.',2,'Dark and Stormy'),(77,1,'/img/shot.jpg',4,'Shake and strain Vanilla Vodka and Pineapple Juice. Sink a splash of grenadine to the bottom. Can be served as a shooter in a shooter glass or a small rocks glass, or as a cocktail in a martini glass.',0,'Pineapple Upside-Down Cake'),(79,2,'/img/shot.jpg',4,'',2,'Starry Night'),(80,0,'/img/pint.jpg',5,'Add all ingredients in a pint with ice, add a splash of coke for color.  Garnish with a lemon wedge while you silently judge whoever ordered this.',2,'Long Island Iced Tea');
/*!40000 ALTER TABLE `drink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `drink_recipe`
--

DROP TABLE IF EXISTS `drink_recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drink_recipe` (
  `drink_id` int(11) NOT NULL,
  `measurement` varchar(255) DEFAULT NULL,
  `ingredient` varchar(255) NOT NULL,
  PRIMARY KEY (`drink_id`,`ingredient`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drink_recipe`
--

LOCK TABLES `drink_recipe` WRITE;
/*!40000 ALTER TABLE `drink_recipe` DISABLE KEYS */;
INSERT INTO `drink_recipe` VALUES (53,'0.75 oz','10'),(53,'0.75 oz','45'),(53,'0.75 oz','33'),(53,'0.75 oz','40'),(53,'1','46'),(55,'2 oz','21'),(55,'2 dashes','35'),(55,'1 cube','54'),(55,'1','48'),(57,'2 oz','23'),(57,'2 dashes','35'),(57,'1 cube','54'),(57,'1','48'),(58,'2 oz','24'),(58,'0.75 oz','32'),(58,'1 dash','36'),(58,'1','48'),(59,'2 oz','21'),(59,'0.75 oz','32'),(59,'1 dash','34'),(59,'1','46'),(62,'1 oz','8'),(62,'1 oz','60'),(62,'0.5 oz','41'),(62,'fill','61'),(62,'1','52'),(62,'1','63'),(65,'fill','61'),(65,'1.5 oz','16'),(65,'1','51'),(77,'1.5 oz','68'),(77,'1.5 oz','66'),(77,'splash','67'),(79,'0.75 oz','71'),(79,'0.75 oz','78'),(80,'3/4 oz','2'),(80,'3/4 oz','8'),(80,'3/4 oz','13'),(80,'3/4 oz','17'),(80,'fill','39'),(80,'splash','38'),(80,'1','49');
/*!40000 ALTER TABLE `drink_recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (81),(81),(81),(81);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredient` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient`
--

LOCK TABLES `ingredient` WRITE;
/*!40000 ALTER TABLE `ingredient` DISABLE KEYS */;
INSERT INTO `ingredient` VALUES (2,'Vodka',0),(3,'Tito\'s Vodka',0),(4,'Grey Goose',0),(5,'Absolut',0),(6,'Absolut Citron',0),(7,'Ketel One',0),(8,'Gin',1),(9,'Tanqueray',1),(10,'Plymouth',1),(11,'Hendrick\'s',1),(12,'Bombay Sapphire',1),(13,'Rum',2),(14,'Captain Morgan',2),(15,'Bacardi',2),(16,'Dark Rum',2),(17,'Tequila',3),(18,'Sauza Reposado',3),(19,'Cazadores Reposado',3),(20,'Cazadores Blanco',3),(21,'Buffalo Trace Bourbon',4),(22,'Bulleit Bourbon',4),(23,'Bulleit Rye',4),(24,'Rittenhouse Rye',4),(25,'Jack Daniels',4),(26,'Bourbon',4),(27,'Triple Sec',5),(28,'Absinthe',5),(29,'Rothman\'s Peach Liqueur',5),(30,'Agavero Agave Liqueur',5),(31,'Dry Vermouth',6),(32,'Sweet Vermouth',6),(33,'Cocchi Americano',6),(34,'Angostura Bitters',8),(35,'Fee\'s Old Fashioned Bitters',8),(36,'Orange Bitters',8),(37,'Peychaud\'s Bitters',8),(38,'Coke',7),(39,'Sour Mix',7),(40,'Lemon Juice',7),(41,'Lime Juice',7),(42,'Orange Juice',7),(43,'Tonic',7),(44,'Club Soda',7),(45,'Cointreau',5),(46,'Cherry',9),(48,'Lemon Peel',9),(49,'Lemon Wedge',9),(50,'Like Wedge',9),(51,'Lime Wedge',9),(52,'Orange Slice',9),(54,'Sugar',7),(60,'Pierre Ferrand 1850 Cognac',5),(61,'Ginger Beer',7),(63,'Mint Sprig',9),(64,'Mint Leaf',9),(66,'Pineapple Juice',7),(67,'Grenadine',7),(68,'Vanilla Vodka',0),(69,'Fernet Branca',5),(70,'Campari',5),(71,'Jagermeister',5),(72,'Rumple Minze',5),(73,'Fireball Cinnamon Whiskey',4),(74,'Rumchata',5),(75,'Peach Schnapps',5),(78,'Goldschlager',5);
/*!40000 ALTER TABLE `ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rating` (
  `id` int(11) NOT NULL,
  `score` int(11) DEFAULT NULL,
  `drink_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq222q1r4fmko638gx8bvptpq1` (`drink_id`),
  KEY `FKpn05vbx6usw0c65tcyuce4dw5` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` VALUES (56,1,53,1);
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(12) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_gj2fy3dcix7ph7k8684gka40c` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'peter.lokey1@gmail.com','ploke','password'),(47,'Shannonluc@gmail.com','Shannonluc','newpyn-cutheF-qurhu4');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_drinks`
--

DROP TABLE IF EXISTS `user_drinks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_drinks` (
  `users_id` int(11) NOT NULL,
  `drinks_id` int(11) NOT NULL,
  KEY `FKn73cmnt0mtsdedfegj11wixtf` (`drinks_id`),
  KEY `FKsnjq1ho8e9ay7ycjmd0eg4ej8` (`users_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_drinks`
--

LOCK TABLES `user_drinks` WRITE;
/*!40000 ALTER TABLE `user_drinks` DISABLE KEYS */;
INSERT INTO `user_drinks` VALUES (1,53);
/*!40000 ALTER TABLE `user_drinks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_my_bar`
--

DROP TABLE IF EXISTS `user_my_bar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_my_bar` (
  `users_id` int(11) NOT NULL,
  `my_bar_id` int(11) NOT NULL,
  KEY `FK48y6b2f50sm7x0ysdqst3wvfw` (`my_bar_id`),
  KEY `FK2pcpw7k7fdtdvj9f84gv9vwji` (`users_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_my_bar`
--

LOCK TABLES `user_my_bar` WRITE;
/*!40000 ALTER TABLE `user_my_bar` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_my_bar` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-12 16:51:29
