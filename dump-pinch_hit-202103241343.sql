-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: toy-homerun-database.czar10ydbeap.ap-northeast-2.rds.amazonaws.com    Database: pinch_hit
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.13-MariaDB-log

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(10) NOT NULL,
  `gu` varchar(10) NOT NULL,
  `sub` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'서울시','서초구','서초대로77길 55'),(2,'서울시','강남구','강남대로 456, 한석타워 2층 102호 (역삼동)'),(3,'서울시','강남구','봉은사로2길 39 (역삼동)'),(4,'서울시','강남구','강남대로 390 (역삼동)'),(5,'서울시','강남구','테헤란로 125 (역삼동)'),(6,'서울시','강남구','테헤란로 134, P&S TOWER (역삼동)'),(7,'서울시','강남구','테헤란로 142 (역삼동)'),(8,'서울시','강남구','테헤란로2길 27, 1층 101호 (역삼동)'),(9,'서울시','강남구','강남대로 328 (역삼동)'),(10,'서울시','강남구','역삼로 123 (역삼동)'),(11,'서울시','강남구','논현로 401 (역삼동)'),(12,'서울시','강남구','강남대로 282 (도곡동)'),(13,'서울시','강남구','도곡로 183 (역삼동)'),(14,'서울시','강남구','테헤란로1길 16'),(15,'서울시','강남구','테헤란로7길 8'),(16,'서울시','강남구','테헤란로 138 1층'),(17,'서울시','강남구','강남대로84길 13 1층'),(18,'서울시','강남구','논현로 419'),(19,'서울시','강남구','강남대로 362-2 가로판매대'),(20,'서울시','강남구','역삼로 118 1층'),(21,'서울시','서초구','서초대로77길 24'),(22,'서울시','강남구','테헤란로 116 동경빌딩'),(23,'서울시','강남구','강남대로 402'),(24,'서울시','강남구','테헤란로 131 지식센터 1층'),(25,'서울시','강남구','테헤란로26길 12'),(26,'서울시','서초구','강남대로 305 서초현대렉시온'),(27,'서울시','강남구','테헤란로 116 동경빌딩'),(28,'서울시','강남구','강남대로 334 SM타워'),(29,'서울시','강남구','강남대로 406 GLASS TOWER'),(30,'서울시','강남구','테헤란로 101, 강남역구내상가 번호(역삼동)'),(31,'서울시','강남구','테헤란로 156, (역삼동)'),(32,'서울시','강남구','강남대로 388, 역삼동 지하(역삼동)'),(33,'서울시','강남구','선릉로93길 50, (역삼동)'),(34,'서울시','강남구','테헤란로4길 6, 강남역센트럴푸르지오시티 (역삼동) B1층 110호'),(35,'서울시','강남구','테헤란로8길 11, (역삼동)'),(36,'서울시','강남구','강남대로94길 23, (역삼동)'),(37,'서울시','강남구','강남대로84길 33, 지하 대우디오빌플러스(역삼동, 대우디오빌플러스) 1층 112호'),(38,'서울시','강남구','도곡로7길 8, 송현빌딩 주 역삼동(역삼동)'),(39,'서울시','강남구','역삼로9길 28, (역삼동)'),(40,'서울시','강남구','테헤란로7길 7, (역삼동) 1층'),(41,'서울시','강남구','테헤란로20길 19, (역삼동, 엘지에클라트)'),(42,'서울시','강남구','강남대로84길 8, (역삼동) 1층'),(43,'서울시','강남구','논현로87길 41, 역삼동 (역삼동) 101호'),(44,'서울시','강남구','강남대로94길 88, (역삼동)'),(45,'서울시','강남구','강남대로66길 8, (역삼동)'),(46,'서울시','강남구','강남대로102길 28, (역삼동)'),(47,'서울시','강남구','테헤란로20길 10, (역삼동)'),(48,'서울시','강남구','도곡로1길 7, (역삼동)'),(49,'서울시','강남구','테헤란로25길 59, (역삼동)'),(50,'서울시','강남구','논현로 327, (역삼동)'),(51,'서울시','강남구','역삼로20길 9, (역삼동)'),(52,'서울시','강남구','강남대로390, 지하1층 9호 (역삼동 825, 미진프라자)'),(53,'서울시','강남구','강남대로390, B3층 38호 (역삼동 825)'),(54,'서울시','강남구','강남대로390, 지하3층 (역삼동 825, 신분당선)'),(55,'서울시','강남구','테헤란로129, 강남N타워 GF층 7호 (역삼동 648-17)'),(56,'서울시','강남구','강남대로94길9 (역삼동 818-14)'),(57,'서울시','강남구','논현로106길42, 1층 (역삼동 656-3)'),(58,'서울시','강남구','강남대로110길26 (역삼동 811-4)'),(59,'서울시','강남구','테헤란로101, A동 10호(역삼1동 821-1, 대우쇼핑타운)'),(60,'서울시','강남구','테헤란로2길8, 1층 (역삼동 825-1)'),(61,'서울시','강남구','역삼로15길20 (역삼동 749-5)'),(62,'서울시','강남구','강남대로328, 111호 (역삼1동 832-3, 강남역쉐르빌오피스텔)'),(63,'서울시','강남구','강남대로98길17, 지하1층 (역삼동 620-9, 시티힐빌딩)'),(64,'서울시','강남구','테헤란로4길46, 109호(역삼1동 826-37, 쌍용플래티넘)'),(65,'서울시','강남구','강남대로102길40, 1층 (역삼동 618-3)'),(66,'서울시','강남구','테헤란로115, 1층(역삼1동 649-10, 서림빌딩)'),(67,'서울시','강남구','테헤란로10길14, 1층(역삼1동 823-8, 태강빌딩)'),(68,'서울시','강남구','봉은사로4길29, 1층 (역삼1동 812)'),(69,'서울시','강남구','역삼로14길7(역삼1동 789-30)'),(70,'서울시','강남구','테헤란로13길16(역삼1동 637-19)'),(71,'서울시','강남구','역삼로18길22, 1층(역삼1동 792-19)'),(72,'서울시','강남구','역삼로4길16, 109호(역삼1동 832-33, 성우스타우스)'),(73,'서울시','강남구','논현로85길57, 1층 (역삼1동 828-23)'),(74,'서울시','강남구','역삼로3길10, 1층 (역삼동 830-28)'),(75,'서울시','강남구','테헤란로16길15 (역삼동 735-24)'),(76,'서울시','강남구','테헤란로4길46, B110호(역삼1동 826-37, 쌍용플래티넘밸류)'),(77,'서울시','강남구','도곡로13길19, 상가 102호 (역삼1동 835-68, 롯데캐슬노블아파트)'),(78,'서울시','강남구','논현로79길56, 101호 (역삼1동 741-41)'),(79,'서울시','강남구','강남대로84길23 (역삼1동 824-11, 한라클래식107,108,109호)'),(80,'서울시','강남구','역삼로11길5(역삼1동 752-23)'),(81,'서울시','강남구','강남대로96길 5'),(82,'서울시','강남구','테헤란로7길 22'),(83,'서울시','강남구','테헤란로1길 10 1층 1호'),(84,'서울시','강남구','강남대로84길 16 제이스타워'),(85,'서울시','강남구','테헤란로6길 47'),(86,'서울시','강남구','강남대로78길 8'),(87,'서울시','강남구','강남대로 지하 396 F-2호'),(88,'서울시','강남구','강남대로 428'),(89,'서울시','강남구','도곡로3길 19 1층 109호'),(90,'서울시','강남구','강남대로94길 55'),(91,'서울시','강남구','논현로85길 70'),(92,'서울시','강남구','역삼로8길 21'),(93,'서울시','강남구','테헤란로14길 36'),(94,'서울시','강남구','논현로85길 52 역삼푸르지오오피스텔110호'),(95,'서울시','강남구','강남대로 438 (역삼동, 스타플렉스)'),(96,'서울시','강남구','강남대로 422'),(97,'서울시','서초구','서초대로77길 3 아라타워'),(98,'서울시','강남구','강남대로 320 황화빌딩 1층 일부'),(99,'서울시','강남구','강남대로 372 올리브영 강남 타운'),(100,'서울시','강남구','테헤란로4길 6 강남역 센트럴 푸르지오 시티'),(101,'서울시','서초구','서초대로77길 3 아라타워'),(102,'서울시','강남구','테헤란로 115 서림빌딩'),(103,'서울시','강남구','테헤란로1길 30'),(104,'서울시','서초구','강남대로 429 올리브영 강남 플래그십'),(105,'서울시','강남구','강남대로 456'),(106,'서울시','강남구','강남대로 396 강남역'),(107,'서울시','강남구','강남대로 438'),(108,'서울시','강남구','봉은사로2길 31 1층'),(109,'서울시','강남구','강남대로 396 강남역'),(110,'서울시','강남구','테헤란로 101');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) NOT NULL COMMENT '작성자',
  `brand_id` bigint(20) NOT NULL,
  `branch_id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL COMMENT '제목',
  `content` text NOT NULL COMMENT '내용',
  `pay_type` enum('Hour','Day') NOT NULL COMMENT '시급, 일급',
  `pay` bigint(20) NOT NULL COMMENT '페이 단위는 원',
  `match_type` enum('Waiting','Matching','Matched') NOT NULL DEFAULT 'Waiting' COMMENT '대기, 매칭 중, 매칭 됨',
  `start_date` timestamp NULL DEFAULT NULL COMMENT '대타 시작 시간',
  `end_date` timestamp NULL DEFAULT NULL COMMENT '대타 종료 시간',
  `created_date` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '작성 시간',
  `updated_date` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp() COMMENT '수정 시간',
  `deleted_date` timestamp NULL DEFAULT NULL COMMENT '삭제 시간',
  `matched_date` timestamp NULL DEFAULT NULL COMMENT '매칭된 시간',
  PRIMARY KEY (`id`),
  KEY `board_FK` (`member_id`),
  CONSTRAINT `board_FK` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_id` bigint(20) NOT NULL DEFAULT 0,
  `name` varchar(255) NOT NULL,
  `address_id` bigint(20) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE current_timestamp(),
  `deleted_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `branch_FK` (`brand_id`),
  KEY `branch_FK_1` (`address_id`),
  CONSTRAINT `branch_FK` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `branch_FK_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` VALUES (1,1,'강남바로세움점',1,'2020-10-07 13:47:39','2020-11-05 10:08:59','2020-11-05 10:08:59'),(2,2,'강남대로점',2,'2020-10-07 14:38:21','2020-11-05 10:08:59','2020-11-05 10:08:59'),(3,2,'강남오거리점',3,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(4,2,'강남R점',4,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(5,2,'국기원사거리점',5,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(6,2,'역삼포스코점',6,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(7,2,'아크플레이스점',7,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(8,2,'강남비젼타워점',8,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(9,2,'강남우성점',9,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(10,2,'역삼초교사거리점',10,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(11,2,'구역삼사거리점',11,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(12,2,'뱅뱅사거리점',12,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(13,2,'역삼럭키점',13,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(14,3,'강남역중앙점',14,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(15,3,'강남IBC점',15,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(16,3,'역삼성홍타워점',16,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(17,3,'강남역KR타워점',17,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(18,3,'역삼점',18,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(19,3,'강남대륭타워점',19,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(20,3,'강남역삼로점',20,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(21,4,'강남역점',21,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(22,4,'강남역사거리점',22,'2020-10-07 15:39:11','2020-11-05 10:08:59','2020-11-05 10:08:59'),(23,5,'강남역점',23,'2020-10-07 15:44:44','2020-11-05 10:08:59','2020-11-05 10:08:59'),(24,5,'역삼테헤란점',24,'2020-10-07 15:39:55','2020-11-05 10:08:59','2020-11-05 10:08:59'),(25,5,'역삼스타점',25,'2020-10-07 15:39:55','2020-11-05 10:08:59','2020-11-05 10:08:59'),(26,6,'서초뱅뱅점',26,'2020-10-07 15:41:14','2020-11-05 10:08:59','2020-11-05 10:08:59'),(27,7,'역삼점',27,'2020-10-07 15:45:44','2020-11-05 10:08:59','2020-11-05 10:08:59'),(28,7,'강남우성사거리점',28,'2020-10-07 15:45:44','2020-11-05 10:08:59','2020-11-05 10:08:59'),(29,8,'강남대로점',29,'2020-10-07 15:46:21','2020-11-05 10:08:59','2020-11-05 10:08:59'),(30,9,'M강남역점',30,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(31,9,'M역삼역점',31,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(32,9,'강남센타점',32,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(33,9,'강남시티점',33,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(34,9,'강남푸르지오점',34,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(35,9,'국기원사거리점',35,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(36,9,'역삼강남점',36,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(37,9,'역삼디오빌점',37,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(38,9,'역삼벚꽃길점',38,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(39,9,'역삼센타점',39,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(40,9,'역삼시티점',40,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(41,9,'역삼에클라트점',41,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(42,9,'역삼우인점',42,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(43,9,'역삼유토빌점',43,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(44,9,'역삼진미점',44,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(45,9,'역삼카이로스점',45,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(46,9,'역삼태양점',46,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(47,9,'역삼포스틸점',47,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(48,9,'역삼푸른점',48,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(49,9,'역삼하나점',49,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(50,9,'역삼형지점',50,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(51,9,'역삼화랑점',51,'2020-10-07 16:10:33','2020-11-05 10:08:59','2020-11-05 10:08:59'),(52,10,'강남역1호점',52,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(53,10,'강남역2호점',53,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(54,10,'강남역3호점',54,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(55,10,'강남N타워점',55,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(56,10,'강남YBM점',56,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(57,10,'강남갤러리점',57,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(58,10,'강남리츠칼튼점',58,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(59,10,'강남메트로점',59,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(60,10,'강남본점',60,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(61,10,'강남사랑점',61,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(62,10,'강남쉐르빌점',62,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(63,10,'강남시티힐점',63,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(64,10,'강남쌍용점',64,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(65,10,'강남아트점',65,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(66,10,'강남타운점',66,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(67,10,'강남태강점',67,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(68,10,'강남태양점',68,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(69,10,'역삼2점',69,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(70,10,'역삼덕원점',70,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(71,10,'역삼명진점',71,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(72,10,'역삼성우점',72,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(73,10,'역삼쌍마점',73,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(74,10,'역삼아워홈점',74,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(75,10,'역삼이담점',75,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(76,10,'역삼태극점',76,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(77,10,'역삼하나점',77,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(78,10,'역삼행운점',78,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(79,10,'역삼효성점',79,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(80,10,'역삼흑룡점',80,'2020-10-07 16:25:45','2020-11-05 10:08:59','2020-11-05 10:08:59'),(81,11,'강남센터점',81,'2020-10-07 16:28:41','2020-11-05 10:08:59','2020-11-05 10:08:59'),(82,11,'국기원점',82,'2020-10-07 16:28:41','2020-11-05 10:08:59','2020-11-05 10:08:59'),(83,11,'강남스퀘어점',83,'2020-10-07 16:28:41','2020-11-05 10:08:59','2020-11-05 10:08:59'),(84,11,'역삼타워점',84,'2020-10-07 16:28:41','2020-11-05 10:08:59','2020-11-05 10:08:59'),(85,11,'역삼만남점',85,'2020-10-07 16:28:41','2020-11-05 10:08:59','2020-11-05 10:08:59'),(86,11,'역삼태극점',86,'2020-10-07 16:28:41','2020-11-05 10:08:59','2020-11-05 10:08:59'),(87,12,'강남역점',87,'2020-10-07 16:33:15','2020-11-05 10:08:59','2020-11-05 10:08:59'),(88,12,'R강남역삼점',88,'2020-10-07 16:33:15','2020-11-05 10:08:59','2020-11-05 10:08:59'),(89,12,'역삼서희점',89,'2020-10-07 16:33:15','2020-11-05 10:08:59','2020-11-05 10:08:59'),(90,12,'역삼승리점',90,'2020-10-07 16:33:15','2020-11-05 10:08:59','2020-11-05 10:08:59'),(91,12,'역삼중앙점',91,'2020-10-07 16:33:15','2020-11-05 10:08:59','2020-11-05 10:08:59'),(92,12,'역삼초교점',92,'2020-10-07 16:33:15','2020-11-05 10:08:59','2020-11-05 10:08:59'),(93,12,'역삼테헤란점',93,'2020-10-07 16:33:15','2020-11-05 10:08:59','2020-11-05 10:08:59'),(94,12,'역삼푸르지오점',94,'2020-10-07 16:33:15','2020-11-05 10:08:59','2020-11-05 10:08:59'),(95,14,'강남점',95,'2020-10-07 16:35:54','2020-11-05 10:08:59','2020-11-05 10:08:59'),(96,15,'씨티점',96,'2020-10-07 16:37:04','2020-11-05 10:08:59','2020-11-05 10:08:59'),(97,15,'강남점',97,'2020-10-07 16:37:04','2020-11-05 10:08:59','2020-11-05 10:08:59'),(98,16,'강남우성점',98,'2020-10-07 16:41:39','2020-11-05 10:08:59','2020-11-05 10:08:59'),(99,16,'강남타운점',99,'2020-10-07 16:41:39','2020-11-05 10:08:59','2020-11-05 10:08:59'),(100,16,'강남역센트럴점',100,'2020-10-07 16:41:39','2020-11-05 10:08:59','2020-11-05 10:08:59'),(101,16,'강남역사거리점',101,'2020-10-07 16:41:39','2020-11-05 10:08:59','2020-11-05 10:08:59'),(102,16,'역삼점',102,'2020-10-07 16:41:39','2020-11-05 10:08:59','2020-11-05 10:08:59'),(103,16,'강남시티점',103,'2020-10-07 16:41:39','2020-11-05 10:08:59','2020-11-05 10:08:59'),(104,16,'강남플래그십점',104,'2020-10-07 16:41:39','2020-11-05 10:08:59','2020-11-05 10:08:59'),(105,16,'강남교보사거리점',105,'2020-10-07 16:41:39','2020-11-05 10:08:59','2020-11-05 10:08:59'),(106,17,'강남역지하점',106,'2020-10-07 16:56:29','2020-11-05 10:08:59','2020-11-05 10:08:59'),(107,17,'강남스타점',107,'2020-10-07 16:56:29','2020-11-05 10:08:59','2020-11-05 10:08:59'),(108,18,'강남점',108,'2020-10-07 16:56:59','2020-11-05 10:08:59','2020-11-05 10:08:59'),(109,19,'강남점',109,'2020-10-07 16:57:54','2020-11-05 10:08:59','2020-11-05 10:08:59'),(110,19,'강남지하2호점',110,'2020-10-07 16:57:54','2020-11-05 10:08:59','2020-11-05 10:08:59');
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch_request`
--

DROP TABLE IF EXISTS `branch_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch_request` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) NOT NULL,
  `branch_id` bigint(20) NOT NULL,
  `accept_type` enum('Accept','Deny') DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_date` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
  `deleted_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=255 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch_request`
--

LOCK TABLES `branch_request` WRITE;
/*!40000 ALTER TABLE `branch_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `branch_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) NOT NULL DEFAULT 0,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `brand_FK` (`category_id`),
  CONSTRAINT `brand_FK` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (1,1,'탐앤탐스'),(2,1,'스타벅스'),(3,1,'투썸플레이스'),(4,1,'엔제리너스'),(5,1,'할리스'),(6,2,'맥도날드'),(7,2,'롯데리아'),(8,2,'버거킹'),(9,3,'CU'),(10,3,'GS25'),(11,3,'미니스톱'),(12,3,'이마트24'),(13,4,'롯데시네마'),(14,4,'CGV'),(15,4,'메가박스'),(16,5,'올리브영'),(17,5,'아리따움'),(18,5,'롭스'),(19,5,'토니모리');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'카페'),(2,'패스트 푸드'),(3,'편의점'),(4,'영화관'),(5,'뷰티');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hint_code`
--

DROP TABLE IF EXISTS `hint_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hint_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hint_code`
--

LOCK TABLES `hint_code` WRITE;
/*!40000 ALTER TABLE `hint_code` DISABLE KEYS */;
INSERT INTO `hint_code` VALUES (1,'none'),(2,'자신의 보물 제 1호는?'),(3,'아버지의 성함은?'),(4,'어머니의 성함은?'),(5,'내가 가장 존경하는 인물은?'),(6,'나의 별명은?');
/*!40000 ALTER TABLE `hint_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logger`
--

DROP TABLE IF EXISTS `logger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logger` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `request` text NOT NULL,
  `url` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logger`
--

LOCK TABLES `logger` WRITE;
/*!40000 ALTER TABLE `logger` DISABLE KEYS */;
/*!40000 ALTER TABLE `logger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_id` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `is_admin` tinyint(1) NOT NULL DEFAULT 0,
  `sns` enum('None','Kakao','Google','Facebook') NOT NULL DEFAULT 'None',
  `name` varchar(10) NOT NULL,
  `birth_day` varchar(6) NOT NULL,
  `sex` enum('Male','Female') NOT NULL,
  `phone` varchar(15) NOT NULL,
  `branch_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) NOT NULL DEFAULT 1,
  `last_login_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_date` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
  `deleted_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `member_FK` (`branch_id`),
  KEY `member_FK_1` (`role_id`),
  CONSTRAINT `member_FK` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `member_FK_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2508 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_hint`
--

DROP TABLE IF EXISTS `member_hint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_hint` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) NOT NULL DEFAULT 0,
  `hint_id` bigint(20) NOT NULL DEFAULT 0,
  `answer` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `member_hint_FK` (`member_id`),
  KEY `member_hint_FK_1` (`hint_id`),
  CONSTRAINT `member_hint_FK` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `member_hint_FK_1` FOREIGN KEY (`hint_id`) REFERENCES `hint_code` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_hint`
--

LOCK TABLES `member_hint` WRITE;
/*!40000 ALTER TABLE `member_hint` DISABLE KEYS */;
/*!40000 ALTER TABLE `member_hint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL DEFAULT '',
  `content` text NOT NULL DEFAULT '',
  `is_main` tinyint(1) NOT NULL DEFAULT 0,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_date` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
  `deleted_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `notification_FK` (`member_id`),
  CONSTRAINT `notification_FK` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'none','none'),(2,'master','점장'),(3,'employee','알바생');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'pinch_hit'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-24 13:43:18
