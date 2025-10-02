-- MySQL dump 10.13  Distrib 8.0.42, for macos15 (x86_64)
--
-- Host: 127.0.0.1    Database: osahaneat
-- ------------------------------------------------------
-- Server version	9.3.0-commercial

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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name_cate` varchar(100) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food`
--

DROP TABLE IF EXISTS `food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `image` text,
  `time_ship` varchar(10) DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `cate_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_food_cate_id` (`cate_id`),
  CONSTRAINT `fk_food_cate_id` FOREIGN KEY (`cate_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food`
--

LOCK TABLES `food` WRITE;
/*!40000 ALTER TABLE `food` DISABLE KEYS */;
/*!40000 ALTER TABLE `food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_restaurant`
--

DROP TABLE IF EXISTS `menu_restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu_restaurant` (
  `cate_id` int NOT NULL,
  `res_id` int NOT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`cate_id`,`res_id`),
  KEY `fk_menures_res_id` (`res_id`),
  CONSTRAINT `fk_menures_cate_id` FOREIGN KEY (`cate_id`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_menures_res_id` FOREIGN KEY (`res_id`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_restaurant`
--

LOCK TABLES `menu_restaurant` WRITE;
/*!40000 ALTER TABLE `menu_restaurant` DISABLE KEYS */;
/*!40000 ALTER TABLE `menu_restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `order_id` int NOT NULL,
  `food_id` int NOT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`,`food_id`),
  KEY `fk_orderitem_food_id` (`food_id`),
  CONSTRAINT `fk_orderitem_food_id` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`),
  CONSTRAINT `fk_orderitem_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `res_id` int DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_orders_user_id` (`user_id`),
  KEY `fk_orders_res_id` (`res_id`),
  CONSTRAINT `fk_orders_res_id` FOREIGN KEY (`res_id`) REFERENCES `restaurant` (`id`),
  CONSTRAINT `fk_orders_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promo`
--

DROP TABLE IF EXISTS `promo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `res_id` int DEFAULT NULL,
  `percent` int DEFAULT NULL,
  `start_date` timestamp NULL DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_promo_res_id` (`res_id`),
  CONSTRAINT `fk_promo_res_id` FOREIGN KEY (`res_id`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promo`
--

LOCK TABLES `promo` WRITE;
/*!40000 ALTER TABLE `promo` DISABLE KEYS */;
/*!40000 ALTER TABLE `promo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating_food`
--

DROP TABLE IF EXISTS `rating_food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rating_food` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `food_id` int DEFAULT NULL,
  `content` text,
  `rate_point` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ratingfood_user_id` (`user_id`),
  KEY `fk_ratingfood_food_id` (`food_id`),
  CONSTRAINT `fk_ratingfood_food_id` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`),
  CONSTRAINT `fk_ratingfood_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating_food`
--

LOCK TABLES `rating_food` WRITE;
/*!40000 ALTER TABLE `rating_food` DISABLE KEYS */;
/*!40000 ALTER TABLE `rating_food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating_restaurant`
--

DROP TABLE IF EXISTS `rating_restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rating_restaurant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `res_id` int DEFAULT NULL,
  `content` text,
  `rate_point` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ratingrestaurant_user_id` (`user_id`),
  KEY `fk_ratingrestaurant_res_id` (`res_id`),
  CONSTRAINT `fk_ratingrestaurant_res_id` FOREIGN KEY (`res_id`) REFERENCES `restaurant` (`id`),
  CONSTRAINT `fk_ratingrestaurant_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating_restaurant`
--

LOCK TABLES `rating_restaurant` WRITE;
/*!40000 ALTER TABLE `rating_restaurant` DISABLE KEYS */;
/*!40000 ALTER TABLE `rating_restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `subtitle` varchar(255) DEFAULT NULL,
  `description` text,
  `image` text,
  `is_freeship` tinyint(1) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `open_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `fullname` varchar(50) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_users_role_id` (`role_id`),
  CONSTRAINT `fk_users_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-01 23:17:53
