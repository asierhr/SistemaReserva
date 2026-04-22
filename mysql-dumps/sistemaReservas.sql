-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: sistemareserva
-- ------------------------------------------------------
-- Server version	9.4.0

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
-- Table structure for table `airport_employees`
--

DROP TABLE IF EXISTS `airport_employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `airport_employees` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `airport_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpke33bs2dluhlm6lgkk0mwixq` (`user_id`),
  KEY `FKq6mx6nlt8u5ih897esuj4ttr4` (`airport_id`),
  CONSTRAINT `FK20ko6x9qryepvf6rxfd4e107n` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKq6mx6nlt8u5ih897esuj4ttr4` FOREIGN KEY (`airport_id`) REFERENCES `airports` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `airport_employees`
--

LOCK TABLES `airport_employees` WRITE;
/*!40000 ALTER TABLE `airport_employees` DISABLE KEYS */;
INSERT INTO `airport_employees` VALUES (1,1,10),(2,1,11);
/*!40000 ALTER TABLE `airport_employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `airports`
--

DROP TABLE IF EXISTS `airports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `airports` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `direction` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  `airport_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK4ft4jts6m5glpq86lt8iavf8v` (`airport_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `airports`
--

LOCK TABLES `airports` WRITE;
/*!40000 ALTER TABLE `airports` DISABLE KEYS */;
INSERT INTO `airports` VALUES (1,'Madrid','España','Avenida de la Hispanidad s/n','28042','Aeropuerto Adolfo Suárez Madrid-Barajas'),(2,'Barcelona','España','El Prat de Llobregat, Sector Aeroport','08820','Aeropuerto Josep Tarradellas Barcelona-El Prat');
/*!40000 ALTER TABLE `airports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) NOT NULL,
  `rating` double DEFAULT NULL,
  `hotel_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKma0kaoi4i5kng08gdv5i065f6` (`hotel_id`),
  KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`),
  CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKma0kaoi4i5kng08gdv5i065f6` FOREIGN KEY (`hotel_id`) REFERENCES `hotels` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_outbox`
--

DROP TABLE IF EXISTS `email_outbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email_outbox` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `attempts` int NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `last_attempt_at` datetime(6) DEFAULT NULL,
  `next_retry_at` datetime(6) DEFAULT NULL,
  `outbox_status` enum('FAILED','PENDING','PROCESSING','SENT') NOT NULL,
  `qr_code_base64` text NOT NULL,
  `sent_at` datetime(6) DEFAULT NULL,
  `notification_id` bigint DEFAULT NULL,
  `reservation_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKi20oka6ujtejlax97k9m3ldwo` (`notification_id`),
  UNIQUE KEY `UK5jqy8bawlck8h30134j24elko` (`reservation_id`),
  KEY `FKqd27lpx0ucnuyf0arh4ow0axl` (`user_id`),
  CONSTRAINT `FK4lycg3mayk9ilf236wyubsyn1` FOREIGN KEY (`notification_id`) REFERENCES `notifications` (`id`),
  CONSTRAINT `FKc51uxd7bxuxejvli6q7h4inay` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`),
  CONSTRAINT `FKqd27lpx0ucnuyf0arh4ow0axl` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_outbox`
--

LOCK TABLES `email_outbox` WRITE;
/*!40000 ALTER TABLE `email_outbox` DISABLE KEYS */;
INSERT INTO `email_outbox` VALUES (1,0,'2025-12-01 15:48:40.041223','2025-12-01 15:56:40.108371',NULL,'SENT','iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAADEUlEQVR4Xu2ZO47jMBBE23CgcI6gm0gXMyADuph9Ex3BoQLD3HrFNXZMDLAbbMAG1IFgkU9Bg/2ppqP8iz2iXfnRDqyxA2vswBo7sMYOrLGK7SHb50fEUMpzLHf92k9l3bR8ToLNpWzl9nUdBz1ieMV501fLpr01CTaxfipP+adNiBJge3ylwmadU2gd/546tsc5HTbFUsqN0Iu4jIRaJmyu8aZskX+FzS1OrP0Yll1iOLdP2mweuJsDq3bncdO6HL9SvUzY+sfkqTO8lIGyq4Q/b8NdGdTEW8dYeYkI1aziTT2uWsfTVxpM2RLCdE4T7hY64Kz1IQ1GevByGd33TKgKO+iWHBj9m+YXoz54hpNn8MO7GTBU1BjkunTIyhFRham9rgRZMOU6TZyEp3ptf4IuB3ZSqOlglOt3gu4EQeeIGogpMBMS4TueWpGQPGW4oU0yYE7umN6dPEgeEfL5s4n3jGnJcpxsuSr1I8bC2Tn1k2BKc3fA8jvrV6WMzy4L5niTgr2OrGA+Iq1N3w+rZ6y8Ag34uDhv+IWUqj39W9Z3jTnhuRaoR3R6j3ms5cD8RqJQe8+8WofY5yUHZh3iKENAKcpo4m91ngPbyRYKF/172QYkOk5un5q8aww3C4KW5KEDWoe4nS85MNqHCHUOJGGAFWa9jbuzHJiXiLea5gPXTYEYCWbWFBhRZjGyBcOdWcuSNrP6xRRWDjBCTQmvI1pqOy/+lQKbuOX73So4rOrf6u9zYDSNTXMdU6lOTFo2mDKeLgI5MNVZzadyN3zdJA1Y5+w26zvGCuqjEuhCZKwjr054ObCb3uyVCZxkNNXAV5r20S1mBevZYmcz6N+++VM3WXNgNp1TIW9WlHi98vBaDuytQ878+bD6/5MNjKDLgs1E2Y1yhfzz2e0MRfp+SYJN4Xsy37pekSCXoJihzr+HZe/YzqUflYoTU7xN9a9e9tNgVlFsKt70gfv351jRMzYr3t7V68369f5Z3zrGFFkKMJbQIVUIajKi9tr6x/5qB9bYgTV2YI0dWGMH1th/xn4BI/13D87QiVUAAAAASUVORK5CYII=','2025-12-01 15:56:40.152813',40,38,1),(2,0,'2025-12-01 15:48:40.057317','2025-12-01 15:56:40.160567',NULL,'SENT','iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAADIUlEQVR4Xu2ZTY6bQBBGC7FgOUfgJnAxS23JF8M34QhesrBc+V7hJEPHUrLIokuiFgh3P0Yq1d/XPeb/Yg+rVz7aiVV2YpWdWGUnVtmJVbZjm8m2+WE2uD9Hv+tt6/y2arlPgs3uqy9f13HQw4aX9au+Kqv2bkmwifXOn/JPmxBuYJt9pcKmr+doWse/p8L26NNhHYFZSD2zy0iqZcLmyDezUSu9s7lax9qntGwTwzkF60bEvj9wNwe2212FInd7HL/SvYIIax+Tp1Hh7gNtVwXfr8NdXbjKt4Yxf/ELT2NTj6vW8TQ2UmALCdZRLRPuOhNw1vqQBqM8SLXLGHMvCHXhSLqSBJtxbVLj0gdP87uTeTzYzYHhlTE0pENuhEh6SrbGBzkwPFWt2xgFT/dafyddDgxPtX5RnB5Kug6CyWGRiCkwBrYS7IYQ7DX8rOB98WHRWwpsn3Yvo24Y3fJUmUcTOA7xljEtcZgoVMt1jPrXqg4Yh5JpGqPMOUfEIImhoZKJ2KXBPKoeARVv7xBdzaZDsFrGyDflFlUvER51M8Taoepbx9Ah5R0inVT3Y95+WsqAhW59z2/8Ux9Dh4TPJQfGHceeZQgoZRlD/Kc6z4HJtStzrzC/FbGXVC1OrkdN3jSGm8781gzpmYChQ2KclxyYNiGQhOpeSNueHNRgfx0aV8OYllQtHqW/l/mTtltIv/gzCTA6lZptWY3DXbDCPpRMuxieDuEpBR+KJMa5x1sKLALzjtONI3b4p97bHTxtGIsJqHMdp1LG4GIStHGbf6yshjH1WZ3rJlQtQ0MakFnof1R9w5ijPhQiER2NaxYRM706VrSMLfo18Agi7gZU/3FtVnJgoWCdBNvY5IDBcZW3vYzax8LCybirie4VbXdDF6bAdh1C76V7xf9PVjCSLgs2k2UL7Qr5F7HbOBTp+5IEk3bateyGlIoWRjNDnX9Py9YxESoZdSoipnybuEVjpCTCUH5U/YgsGff5fTxWtIzNyjfWFbFfLEkXlwY5MGWWRY04OmQXgtzEqveGtY/91U6sshOr7MQqO7HKTqyy/4z9AGyjbH73C4GvAAAAAElFTkSuQmCC','2025-12-01 15:56:40.170753',41,39,1),(3,0,'2025-12-01 15:48:50.771092','2025-12-01 15:56:40.178761',NULL,'SENT','iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAADI0lEQVR4Xu2ZMY7jMAxFGbhwmSP4JvHFAtiAL+bcxEdI6SIw9z9qBptoB9gtthABswg80lNBiOQnNeb/Yk+rV360E6vsxCo7scpOrLITq6xgu8n28WnWu78Gf+hrv/iyablLgo3umz98Hvr1Olt/WLfp1LRpb0mC3Vi/+Ev+aRPCDWy3ayps1D2Z1vHvpWt7dumw21WfK6Fndh8ItUzYSLyt2tRK52xudmHth7BsFMM5ebrg7vsP7ubAij3kpNztcHymegUR1j4mTyPD3XvKrhK+2/qHMqiKt4YxP67zYKpZHpv6mbWOp0cWTA7NKKDuidRXzUIQtdHnwW7x1/M+hO4FoSocQTflwL4bj3nQgZdJzok8fmI3BXaYKd4ItVU+64rUT8noTZJgVCrlOiJOwlO9tt9BlwOjPhmsrk1Bd4FAOawEYgYMwVaASffwVG3J5NES9qu+UmCR3Nq0ouSqwqbIU1sS7qbARNyth+0REg4MWtaA8ZkyLWOmNDeuyL+yflHKxN2lwciRohceX+WKVAluH5fVNKbWWzliKEd80UoVTf/M+mYxpUzHfOpfV4SQxJgX51NgxVOyngNRx+hDwucpB+Y8b0SUEXSKMkSc3gRBzIHt0UVdeJyRXmzKeg8nt6onbxjTpu7pYMQ+4r2g9CEh51MSLIiV8YhmBIx6rAw63gtX01io3YqTJE/Pc1OoiTGz5sBWFJA52+LNiQJsFIE/MqtZDBG3EA0mo0FXNHF04RanJNhI+4d/zhMZT5blSynz7mnDWCig5jqmUpUrRZ5OKeiiCOTAwlP76mpjpCtzdpX1LWNO96ElERcK1ygi3guq6aNlTDWrRFkQ8Tag/Ne46pXKNItFwsfzxs6mod89L39SkyUHFhZOxltNVK8ou9EXpsAUVsbFdPzzYYn/n2xgBF0WbCTKVsoV7d+3kCycn5Jg6p1KL7vTSkUJo5jRnb+HZevYzqMflYqs59FAzWFISiKM9CDr1VSR/6HfH2NF09ioeCtO4lqwEXTRF+bAFFlGrtORMOGFf887tTesfeyvdmKVnVhlJ1bZiVV2YpX9Z+wXMjJf27ZgSAsAAAAASUVORK5CYII=','2025-12-01 15:56:40.185358',42,40,1),(4,0,'2025-12-01 17:25:01.395982','2025-12-01 17:25:10.008057',NULL,'SENT','iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAADJklEQVR4Xu2ZPY7jMAxGaaRwmSP4JtbFBrCBXCxzEx8hpYsg3O9RATYWBpgtthABswhk+aX4Iv4q5v9iD2t3frQTa+zEGjuxxk6ssRNrrGK7yfx+vfHwnPz7YbYPftu0fUmCFfdtn+0rnjY9rrDLpqf4agJstsvmL1u28X5FqUTug7DdqvokGEon0z76njb645IN89fVN72Ujw06sfHFV/NgBS9TjLjN2te3lrrnP7hlpxhRv4vYmo8fkkOnWDWiBS97u1/5+yYBJqWXqkoOtoa/udxvnUZlryTYoKJhg8J8/NZKH6v2pVnsp791jKlUBOGbRK4Trlb3lIXDUmBKu/PVfXxF+SDtFu0rFWfB7nI2pwWRyKeFPoT7GG8zYFIV+qIPEVuo33I/hH+6Zc9YdFHfNUbC3xz1C2VwSYIV+ZtH+4fnSaSqCXksCmIObCDqXXlWfvelLsrD6WiqUJ8Cm+OIJI1WaqoYhOLm4G8dY4W0a/Ktsfqb2aTt1SL0c2ByMGKdE6tRbxREzq7xt24xdVGrUcSJEa1muvPYK5+H1TMWEY6+CBRW1PQHfeHhB+kYk72bcK4KNA/VtHthL972j8UM946bWNU+5IbSJBh1j058ZcQOf4uabvK3z9zbNeaqeyN9SIzYUkpNl9Jj4uoae1/TqIONmyZW6kNUPmKVAds5HfRNYBa1UD7oETdJMKvXAk7ufVIBF0TeatzkwHgKV9POF4ESPfnCFVQWbCD3FqIFpbUTv0Mce/KesTidCH0ubHYqeSg1XqTAnA6WooHnPRm2n/VKv2lXesY0yNUpaIx/IEodK9qo7xpzpqC7OnGLRvBlmvXisXzemHWNRalgogiCY+My2fG34w/SLRb7zn1fREskrpGbv6PSnrGwncZcFt1gYNENHiOrW0yE1UzFsW2Rx/gW+XhJghX5FtdNW1yRVX9D5M7YnQObUaUyyEyNl7EZ/jawyoNxMATKpBVnxwuuChJhdINEPeOR4p9Lg6aV6hkrzr+KKHUpVUcSeczJx7zPgNWoV+6tIucYlKS+6QY7xn61E2vsxBo7scZOrLETa+w/Y38AgXyz3AxsU6EAAAAASUVORK5CYII=','2025-12-01 17:25:10.026764',43,44,1),(5,0,'2025-12-03 12:43:20.768819','2025-12-03 12:43:30.006185',NULL,'SENT','iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAADGklEQVR4Xu2ZPYrjQBCF2zhQ6CPoJqOLGSTQxaSb6AgOFRjXvu/1DLvuGdgNNlCBKjBy9ydD0fXzulziX+xR2pUf7cQaO7HGTqyxE2vsxBqr2F5k+/AopYt49rHqab/EvGn5mgQbIrZYY+q75TaV7lWum94aN+3NSbAP1j9uT/mnTYgoYHu55cLKvS9ax7+nju1xTYfhXyyEXvELrOXBBuJtuSlb5F+wuZULaz+E5UExnFO8zQTdnx+4mwOrtspJHdYVxyeqlwnb8TF56gyP6Ci7Svjr1q2qwk28HRiLVyXG8KY+Jq3j6SsLhpMbqa/854WgAw7a6DJhfHvce/c9E6rCDroxCXapwkMBRhtUO+fs+GA3Baawmmh+T3TIzBFRhcPaJAkm/yZyvfROeKrX9jvocmB0CR2Mcn0l6Hx2rJWP95Q5MHZBO60S4TueKv/HsCzpFj2lwNzttFlqJy/yVJEnPWV3c2CLCF0mRrJl6nmhD87OqZ8Co1z1hSOKz6yflTI+uyxYVeLUXlYwH9FUCl0xBaY0RwM+7nSOmSeklGPwPeuPi5Eym6+m9Yjw2dc8RgU5MOsQZ33N/0tYh9jnMQcWjDccZQgoRRlN/Eud58DkmsoVh6Xk0Ykh0XFyazT5kTGfE4I2XnXkYR3idj7mwLQpQpVKUkrlCowYjI3ZWQ6MLrFRZ2uai31SdkfCzz9zfMyesi7WN1VYDrDJrANjOphnnQ24nVuRuJ2Hn1JgQ3z6F4zIGFnWJ7fGFJg7oO51sLTBRWqw8zT/PbMOjKnOFuLtXjxu2kkZrqbfsv64WKA+KnGhcA3B6IaR/vu14sjYom9V/kF4NlC4dgsYc2BWsGrYntVM5L+CjsmfqvCcA7PVlke5cvVy2bUuTIEprKxDrvz5MPv/k+2rFGfBBqJsoVwh/3x2O5civT8mwZAg1rI7UsoljGJWmnHT0TERSpnCCyuXOyUPY0z202DE1s5mYQhb+3d7rTguNijeqpO4ZrYWs09tkgBTZNH3ZhQJNzz797hTe23Hx/5qJ9bYiTV2Yo2dWGMn1th/xn4Bur1WK4E0nWoAAAAASUVORK5CYII=','2025-12-03 12:43:30.020985',44,46,1),(6,0,'2025-12-03 12:43:20.783940','2025-12-03 12:43:30.026425',NULL,'SENT','iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAADHElEQVR4Xu2ZTY7iMBBGjbLIkiPkJuFiLQWJi4Wb5AgsWSBqvveZmW7cLc0sZuGSUou0sV+QquvflPgXuZV250fZsUZ2rJEda2THGtmxRip2L5L76VbKGPGY4qrV/RCXTdtDEuwUscU1ztO4Hs9lfJZh01vLprNLEmxmvxwf0k+HEFHAtJcKO9w+pqJ99HvIbDe/mgtjueJ6pfBC1T4LdsLf1qOiRfoFh1s5sPeDW3aKodx91mHzQN0cWJWrAkXGGlD8TPYyYekfk6aO8IiRtKuAH7bxqizc+FvHWDxxNTT1oR5n7aPpMwvmGKl2mlE3qIAnHYxpMAU8n5R7XfdMKAvb6ZYcmCoHjQfYrDKoco7n8fCXZMCkUJnKfHQfcsFEZOFwb5IEk35nxbrSlQMez9s+nS4HJmNN+qtYv2Ix247KIe2/RlbXmAg52IVGcFDxK0u4JRxXrVJgVb/5dyUvKC5Ce6ibAnMfYq0ULWe/MAW2c+inwNwIMkfULEzRUMjYdokwol4WYwexibQ3v9m0Y0wKXdwDUjlYuZWiO3+P+o4xDhnulpeJDuqnPOZxVZADc3A76sP6HcJ9iHVecmDV31wvCqu59rfF3XkOjPJB3Vuo37LY84gP8vHZVsBesXhKqaBeaDVQAd2HuJwvSTAT3JjRTxWwYNbbuDvLgTlnETd/wvxB2l1wP39NAgybSKtlKx7uSMCFJNCGTMcYeZatjclo4oVazsOrFJiHIvQLrsi4sqwr/Qu+atoxRtHYNNdhJ8rgqm5w9G3+e2R1jCnPMp+Wj+LrJkVQnbO/RX2/WNB9jCRgComnUntenfByYKs+jTxMoCSjqZrDaMpHt5g72GC2IHhIV3I6VROtahj1j1mspO9qnL2cdt0XpsDkVgXDDPz4cOEe+TMVZ8FOeNlKuqL9s+3uDEV6f0mCqXeqveydjsQpjGRGd/7VLXvHRChk6KeCi3yinmtMztNghAdRz2j6oB1X2n0bK7rGTvI39sleL7Yms1dvkgCTZxXHSNCH1EZQkxG519I/9lfZsUZ2rJEda2THGtmxRv4z9gtEcGh9nPDbTQAAAABJRU5ErkJggg==','2025-12-03 12:43:30.032561',45,45,1),(7,0,'2025-12-03 13:55:53.083815','2025-12-03 13:56:00.009389',NULL,'SENT','iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAADGklEQVR4Xu2YPY7bMBCFR3ChMkfQTeyLLSADuph9Ex3BJQtjmfc9ehEvEyApUnAATeG1qM8LDObvDaP+iz2iP/mjHVhnB9bZgXV2YJ0dWGcNKyErl0fEXOtzqXd9K1Pddh2fkmCXWnf9vS7z7cc15s847frVytmWBDv7vD7ln15C1AAr8SMX9ogldI5/T4XtcUqHTQTmRupFfCykWibsQr4p1RadnCov95haDmbBcK6c9bL7wN0cWLO7CkXBOuH4le5lwjY+Jk9d4bXOtF0V/Gmf7+rCXb4NjNVPPc146pf6uOocT3mRA/PQcJzOuFuZgBedz5mwjXz7WDz3TKgLO+nWHJh6r1yj48rTZ9R7JfP48D/JgLnt0rzQIRshkp6q9F7YFJg9DemQxQVP99p/JV0ObFKq8RAt6SYIJkec30tmbIxOdZcIL3gqx9dqSahOsObAXNytbs6MbnmqzKOCcDcHxh7RxseniocfLDpVP/5eWQNjmtWulrV1YYaGSsaxy4M1e9VNvEJ0jTi/B2tkrCK9XevUDdXi2Hmmv1X90NgNEc61QAvRRBeO11kOzLrVVV/t31StQ+zzmgNjiLcsQ0ApyxjiX+o8BybX1K4mLmc0L3ZVfbWT+3dNPjLmuwE1YFU9XbhwaSAd4nG+5sCsZSuTQ3rqGmDkYN25O0uCMbrt31eZP2m7K+nHUwLMcw8xsgfLndloIrerrGGxQq23Wz4KXiFaXz/1txSYF+t7i9PGim3/1Hunb54OjDE0du11sIqYtKwErZLOTSAHpj4bixLsQwqWpUhrHorkt6ofGKuoj7aQsiMhY515bcPLgd305Cwz4bsB1b8WvtpNmWExK9hAAxZesmDsvvnTNNlyYLY28mhX7l5uuwVdmALzZqQHu7txj6w9G+/d0XJgF7LsRrtC/jl2haVIv1+TYEgQa9mClHILo5mhzt/TcnSscOlHp6LqlW8qHq4xeZ8GQ/lR9aymT+S42i6xy4FdlG+cK2JfrJPOlwY5MGVWUOtN1TYhqM2I3msbH/urHVhnB9bZgXV2YJ0dWGf/GfsJs9VaHTt8Kk0AAAAASUVORK5CYII=','2025-12-03 13:56:00.023953',46,49,1),(8,0,'2025-12-03 13:55:53.098431','2025-12-03 13:56:00.030529',NULL,'SENT','iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAADHklEQVR4Xu2ZTa6jMBCEG7FgmSNwE7gYEkhcLLmJj5ClFyg9VWVGSvwivVnMwi3RCx4xnxc97p9qj/m/2NPqla92YZVdWGUXVtmFVXZhlRUsG4w/bHA/Rn/gLXe+Jyz3QbDZPeXuuehXws+N7Jrwaw+CTdYnv9/2NNxv9BROYteast0iYXjYaFinfweO7dlHw4qnWDHrcGLDi1vjYLOi7Nm7TVjHrrWs+ZewbBRj1mcQqXp8KQ6NYqchwBhlZ/jN71+ax+Bpn4zlCgG2Kd7cX7dtHFC9YmBYAotK5cMD7QOPzeg4P7zFW8uYOgcCzBOchLsTPz5wdqjCsgAY0xwH0/vwUvtg2Z3xAaU4COY8HRSuhU4eJv8GPfQ1AqbTGSignFIKG1Y2cTn+Hm8NY2h+hyH115LrjDys2co2uMbAWK5Gp/pAqOHEOgpadg41xBAYvFrMcETIEbxRmDPoKKpYvSJgpdtRhFNKjQUjgbx5/wdpGHMpP1WvEm9mI1Y3U+rHwJzND8ov0UlmPdQ5GyIlegwsUw0aKxVyBDqEvRAzEt7mH4K2UYwThfOIlChOdZ7UEEs7j4DpRiDLK45HrMLnZKQqHAFzZvh5s0Gf6al2BcIUW+oX0uRT0VPGetxHwcoMBzHibH4zb20YeVXhahjL1ICa6zQewfFz66C3GJgto1QUMQwYIKBNXHkTA8NfzafO2ov2oV6o/P+svU1jvHB9me5fFz6kyddUjbENY1pivLHYYsSWEmdL8U9N3jBG15yCtly9ZnZybTUeWwiMgjbhr+5qjpFBZ/L0U4e0jVH+TZwo9D8Qcxkr6qxvGYPtzHo5qY+oY3cIc01LMTB1QH4UUXRIB0+5Kwam9bPsSokzeXh/8yPemsVkmVIKJjUoTGowCAbCeLOBznH/O6lyV+KGINiM2FKO6IqsxBudzLqCCoFNZQraT00+c1Hx1vEtDsahiIkywlOcHQm2wUgYixQeULVsg5mXBpWUahmbEW9ybWAnl6hiB+TtE79HwJD0lll7i5MTbwmY/5VobBj71S6ssgur7MIqu7DKLqyy/4z9AQmYtSt6ApVEAAAAAElFTkSuQmCC','2025-12-03 13:56:00.037239',47,48,1),(9,0,'2025-12-03 13:55:53.110965','2025-12-03 13:56:00.043086',NULL,'SENT','iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAADG0lEQVR4Xu2ZMY7jMAxFabhwmSP4JvbFAtiALubcREdIqSII9/+vSXaiHWC32EIEzCLjyC8FIX7yS2P+L3G3duXHOLEmTqyJE2vixJo4sSYqVgxR1rvZ5P6Y/YanMnjKWB6DYKt79pvv83RcdpueNmb8ast4l4JgC9eXywP54SUJN2LFLqGw9X6dDevM74Ftu4/hMC0dLD0z/uC9FgJbWW8gZqyMzpfZBq79UJadYkwO9ZZYdN8/mG4MrMaNWR1YR+I7u5cIRf8YMpXC3Se2XQh+zNMNXbipt44xf+Ibe5brJT52rDNTvgiBIaHdbOE+LUzXOQFXvJgiYYmldp0190SgC6vothgY9mSn6lFgHIMQDyuPH3obAoNQZgjF6EMStwh+CkFvEgRDfqi3FWqR4Nm98u+ii4Fh2lEoV+wTzAj0D4KTw5YPyfSNJW5Mop8aMfxsc1nC6cBTCEzihhO3OsltUOWxCXwO8Z6xg4TGB/QP6ctUYYZI+iEw9N4HHeyGH1TVJ0hGexcGe6ueKwxt0V6nYhDM6MSNqocllG4mukGWXwysCp7Sr1s0sAsbjfnw/VjRM6Zvpc5v5sdWTKx2rxCY83pDVUYDhSrjEH+58xgYUkO7Gng5g3mRoXpXkrnx5D1jaLZ1fvuTdwNL9SEa51sMTHYcf3ZYKbSrryZw8cy7sxgYpkRVS5W5vhrNiPHMGgXTjm3ZdLhDH4MRpIg+JdMzNiBJ2XF2rxlbtNVx7noKgXF+Kz/nFRnUovySfh8DU6nhXMd9QruCl4WhddqST2V1jKHP4ny6wIfouulLQf6n6vvFnO6jHkgHNq4VhGZ6c6zoGTvwTVUmgknyaKprsy0GJgfruqis7pxFx5s/TJMUA1OAcOom8aleebh8YQgMZWXcmJH/fEj6/8m7FUfBVlbZwXZF+6e9KzwUFZ1ZQ2DwTtXLFloptTA2M7rz72XZO1Y4Btmpqnioel5j8n0YjM6v8KXx3qPO7/ZY0S+2OueFduzFvppZFAyVZdKI04dUI8ibWPReRf/YX+PEmjixJk6siRNr4sSa+M/YL618eA5WWNH2AAAAAElFTkSuQmCC','2025-12-03 13:56:00.049085',48,47,1),(10,0,'2025-12-03 13:58:52.356266','2025-12-03 13:59:00.007192',NULL,'SENT','iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAADDklEQVR4Xu2ZPY6cQBCFa0VAOEfgJsPFRgJpLjbcpI9ASICm/N4rdr20V7IDB10SFYzZ4iMo199rMP8XW632/GgXVtmFVXZhlV1YZRdWWWCbwXxZzbYP/PRvm0q/+LPA3SXBRvfiL3tY777bNq424N5UcO+ZBLtbV1zxvW774AwSMU9ls1smDOHu5sLeTN7H2qXD7jcvPTwgkDsUXSpsZL352uEB+PmAb2T9h7JsFGNySDDc7z/253BoFAt722P4Crcw0i9rH4Nrt/6FqFBgs+oNTzFtB5sAY07YN/Q/Mb0whekrxspLgWH5aex6QZDzwGTRh3m86HYCjGN3+EwRn9L64A07/Yc0jDkXtiYuNrkN/fuGcA0Z4w5Jgi0rGh4EdYgCdyxEEuybFFjsbxQd/ao3OF+UJdAmSTD6jY3yqT7wlLNlVH4pMCo/SJDJjVUmVau+IZsEuyMnHLb0dI4HHsxdp/7PgUHGzkY5booUGUPM3CamvkmBLVwaLDX9hQ6aBxCM/jwc2sV0KpUffTMbVzcXIq7GU05bxhiQTqXYIU8NgaJkxQjLgLlOQRhc2IDUshZjV74kmBpe9XbEHDokYs6BsWVQcAitj9M1CPutzlNg2BdDTFzuvdjpOpouabDQHEiMcWah6OTDItFVCsz5imxRpDBEephWo27H3w1jC3uENzF290E3e/X/afY2jTEn7BWjntID+KG8Okv3hjEeITR7WWXxHhmLhC+TOceSYKFDeDy6qcpAMNJzvbWMOUU4w+U6x2H7zmMF6o1D4HtOW8bQMq7vDhu1bETK5VdF2jLmcQoyBulcg3HiDlWbA9Oq4Oma3yIOHTKy9SkJU2Dqep6H9K4mbNMvFUkKTCZJiNO11CAypisNswyYomK3MFJOYWbMeHWqt6ax0Y/aUmhs+KJJoOhzYCDCH0p8pFM6/aTJE2BMjFgsRGP0fIE25cIgaPUFAoOLanBm62fBRtbb4Xqq9Qsv+SZW/yTA1PWU40YdgsAnfhHq9NU9B/ZXu7DKLqyyC6vswiq7sMr+M/YLyMX3rClLO00AAAAASUVORK5CYII=','2025-12-03 13:59:00.030558',49,50,1),(11,0,'2025-12-03 13:58:52.401914','2025-12-03 13:59:00.037568',NULL,'SENT','iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAADEElEQVR4Xu2ZQY6jQAxFjViwzBG4SbhYJJC4WHKTOkKWLFA8/7nSM+makWYWs8AStaBJ8RLJKn/7mzb/l/W0dueP68SadWLNOrFmnVizTqxZFdtMa7teYmsf/fHUx87Xou0+CTa5F7/bbRzul8WGl/Vlm55z0bM1CXZlv/Nd8ekhhBvYZpds2GjaJ77dBn/26bCJ2zupZzo2Ui0TNpFv7suond55WKyre1kwgpPq19JcCDcHVtdDQeqwegJfqF5BxDo+pkhnJLOMA2VXgu/L8JCCmnw7MBZRGWw81GXRPpG+smCquFL41WbpX21QNUvpN+nBkAajXI0D/Tv6XhCqwpF0cw5M+zuncxsVqe4e2hviEj+SBRs5InzIyhHJTzm1FzYFpjO5mfMwBE/1Kr+SLgf2Qih3qZ4vjFKQCDqHXb9J5sBYVT0mfCPSqn8sIV9Ngk1cI8ho3V1knvxUhJsC47AKwwRqWcSqjmlXA8Z3yRwY0/4N1c+qwlX1qyQTZ5cGQyNW4+PufUQLe59nemSMT9KI0TlW7rBS8iGkXxIstC7V0/LWjzEv9lJgHlF1eMD3XfiQiHnOgSFzOXF5cgyUsowm/uXOk2Cy426MFU4bHGiIBFkaT35kjEi3GOlevBu4Vh8S7XzOganiKsEowJyY1SJgXy0lBUavLtTZnzLfKbszuomfOT7G39F432cMd1GArcroUzKHxrRPv1DSIXgd0VzbucddCuwesyi1l1dkUkvEt9LdPyM9MuYxGeGnNFMXedn3xE0RyIGpZq3Vc9QTi4lbXrZV/YExx304Wo9Ojo0tZF6d8HJgd32KESIIgmQ0fadfCizcoJNgGw+N/h1v/rjLgcUSoQSjXHFO8cqj+sIUmNJKE4Vj/zo8OXYcjKTLgk1kmTMPYf/qlEER2LCJOTAsSHjZDSuFqZL0CRw2D4arHalUqEX5JvHwGpPnebALd5cYj3bseM9LnN8jPSY2efQL9RBCC7YWM3xhDgzV1/9d4UOqEcSbaMqIdXzsr+vEmnVizTqxZp1Ys06sWf8Z+wFB2qlQU2Y/LQAAAABJRU5ErkJggg==','2025-12-03 13:59:00.044110',50,51,1),(12,0,'2025-12-03 14:00:22.259655','2025-12-03 14:00:30.005018',NULL,'SENT','iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAADHElEQVR4Xu2ZTYrjMBCFFbLwMkfITeyLNTjgizk38RF66UVIzfue0kxHNPQsZqEC1yI40udAUX9PSol/sc/SrvxoB9bYgTV2YI0dWGMH1ljF9iLbp89ShojHNe562k+xbFo+J8GmiC3ucbsO6+VWhmc5b3pr3rS3JMFG1sfLQ/5pEyIK2F4uqbByeVyL1vHvobB9ei0Vhn+xknqlfFxJtUzYRL6t2tTKOdjcyom1H9KyUwznlG8LSff9A3dzYNXueLVqXY7f6F4mbP1j8tQVHjHQdlXw5224qws3+dYxFk8VPD0rvKmPm9bx9JkFc42o6hWnEXeDCThpY0iDVYeeqnrPPRPqwk66OQemOFH1YIxBFQ+Zx4d/JAWGV4Valw5ZCJH0lAxtkgWbaq1r7lHwdK/tb9LlwJja+vKhOImtsWNylPG9sjrGTupUSrBa+hp+Zcb7OYZVTymw6t/4NcmLPFXmUUHvQ7xjTN+KHnDtqeLhhauW7b2tf8wTsBCieFX9opJx7LJgzjc3LlYwh0hr41uwesZYp/SZHAvVgpSqM/171XeMEaLNR9MaolO8jnlcFeTArFtf8xv/TmEdYp/nHFhwveEsQ0ApyxjiX+o8B0bJXB0szW9F7ClVi5Nbo8l7xnDTM0Sq9swEtA7xOJ+TYGjZYHLQvZC2Z3IwNu7OcmAiYrMQdJmLfdB2Z9LP+/1jzjdFZ94Khzs34IIs+aFkusVQ4mEhqIJXiOY6zsNPKbARAfUaFQtHbPu3+P0cGENj07mOUyljcJUaHHyb/14yHWPqswU1+FF83bRTMhxN2+bQMRaoDy2JQBciY5159YSXA1v1zVlmwncDqn9fm805MCvYIMEoHtqVku5edfqSA7OJCOpm4enhKw+asq1/rOoQBYs/Hxb/f/IaiJ4mKbCJLFtpV8g/x27nUKT35ySYtFPVsjtSyi2MZoY6/56WvWMiVDIFLctFPlXPNSb7aTCU385m4S/SOr/fjxU9Y5PyrXYvXDNbv6ILc2DKrOJzdqBDqhDkJla919Y/9qsdWGMH1tiBNXZgjR1YY/8Z+wMPilv4osMleAAAAABJRU5ErkJggg==','2025-12-03 14:00:30.011016',51,52,1),(13,0,'2025-12-03 14:00:54.961360','2025-12-03 14:42:00.116089',NULL,'SENT','iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAADEklEQVR4Xu2ZPa7qMBCFJ0qRkiVkJ/HGkIKUjcFOvARKF4i55xxHXLCQ7ite4ZEyRXDsj2Lw/Bwb83+xu7UzX+3AGjuwxg6ssQNr7MAaq1gxGF9scn/MfsOoDL5lTI9BsOSeS7qf9YaRX8iuGW9bEGyxMfsNrk3XEz3FqAzAip0iYdidx2yYp38PbNt9jIb509aMRcTYgB2bnvxqHCzVKHO3BfP41lrn/FtY9okx6wuI3Dy+FIdOsZdxs8Y9/NLbfP8YPB2zsVwhwC6KN/fn6TJPqF4xsN+p6YbUx+OCefiMhbd46xpjq8CiZzgJdxcu3u6qx7L+MWwWnESlwkPtg2U3YQGlOAjGzYLwuJ/p5MPkHx33SatBsI1OMsoQb1AkK5u4HP8My34xJIoVbQyTB+5idGVDRFeMgRUmCj7h6Y07NlDQsnOoIYbA4NXZuEXIEYwozBl0FFWsXhEwZLhyfZWUmitGAnnz/oP0jFGHWK1eijez2bl3Sv0Y2MDmhwzPdJJZb7NxjhI9CgavjLmORx3xjIRR+tisnrGE2Co6nzLA+IqezrnP4tAzNvBAyqz3K+W41bI7UphzPQDmzHAGXVaAydPCfQqEKbbUL6TJl6qnMMNdDILVMxzEiLP5vSLvs3B1jUHG2kRWxyM4vn910igChhw5z1JRxIznU6eyUt4EwaRg99qLwx014KT8b2tvvxgJbJbuX6uoqpd+EusxMON5yFmuXp6yCjtv0WJg/JzxWOvVa2Enr6V4iYLxOJ3xqbuax8ygM3na6pCeMcq/hScK/QOR6rHiS9Z3i8G2rITHQ4uoY8gg1DFqkxCYOiAXRRTpkAGe8lsxMM37K1voHy/QOGrirVtMpr4HkxoUJjUYBEMTN95sQEBdpcRRrvCK0Ue8dY0lxJZyRFdkNd7oZOGxOwa21FPQtmvyxEnF28BRHIyHIiYKyxX2joSuDwJhLFJ4AGMbLLx6bRVXx1jy/V/FiZ2cf2pldkBH+HE9Aoakt6IOKCcX3hIw/1vR2C/2px1YYwfW2IE1dmCNHVhj/xn7AYfPuKoqQDl4AAAAAElFTkSuQmCC','2025-12-03 14:42:00.163189',52,53,1),(14,0,'2025-12-03 14:54:26.498128','2025-12-03 14:54:30.008636',NULL,'SENT','iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAADCklEQVR4Xu2ZP+7iMBCFB1Gk5Ai5CbkYUpByMbiJj0CZAuF933PQLvkh7RZbZKRMERL7czHy/Hk2Uf/FHrEe+Wo7trIdW9mOrWzHVrZjK2vYHLI5ThMfz77eH/o81Klo+JgEG2ot9Xa69p0e0b3iWObhMRbNeWkC7My43T1oEqIG2Nv7LNjwuPShcfx7RlcfLM2FxUmvN/yNYEEby4INxNsrotfIsTJZ4sDYl7DcKIZz81mTqwfu5sCaveIS2qwjjl+pXiZs28fkqXP90neUXSX8sXR3VeFVvG0ZO/C8vyf1uGocT19psFDT8O4oympVzVL4DZro8mAE2PJw+9CYqrCDbsyBydgdsPPpKZ/13vmxzG4fm+kcM01DOmRii6SnZMULUmDue5q89k54qlf5HXQpMNcsqygFmN9E0Dni/Jky28WkZZ9NhM94KlkysnSs3U1vSTCFWnPSrVueKvI0ZndzYHc5qQAbyRZS36JKlcCpnwNzl3hZ0LasZ5X3Lg3GkU4KlnKFDlm2SGPnzz3dLuawogPSOSayBUXSevqUBBvYGNi2RfjsYx6VIAfmePtDTx2qdYh9HtNgjrcr/hFlNPG3Os+BWcaG4q1XFR5LR/7jZGnlIAVGbClbhL24G+DSQKW4NfYcWEX+zb6rIfXBWFoLd2dJsJtca06S5h3XTYEYCd/fZMBcYmniJXy4owAL+5Iy28VUbIMP7ZO2TVs0tnZe/ZYB47cnZfiyz/ZPtffwUd82jPkcET7c6UxdpGUlaH2b/5EyW8aouO0U5Osm9fR2zv6Z9dvFrD6YVORdwWoh8toJLwd246v1vbrcDcjxJfxSYIuCVYDNTAb9uyODeMuB2UQowChXrl4uu9aFKbCZlFeGB5Jw8v8nBYygy4INjjJ2zOp8ISbWj0mwMzqEJj4jpdS/L0p96jFsHowd66lULXnIeq4xmU+DEVszk9KAyn/3b/YuBzYo3vSY+Jt9YR10FV2YA9M+hXOkokOaENTJSLWX6QzYX23HVrZjK9uxle3YynZsZf8Z+wXIaJiTaNXU5AAAAABJRU5ErkJggg==','2025-12-03 14:54:30.023220',53,54,1),(15,0,'2025-12-03 14:54:26.514267','2025-12-03 14:54:30.030304',NULL,'SENT','iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAADF0lEQVR4Xu2YS66jMBBFK8qAYZbATmBjkUBiY2QnXgJDBgj3PeVE/Z5fS92DHrgkakCIfRiU6ndty/9im9Urf7QLq+zCKruwyi6ssgurrGC7yfbh4UtHn1+b/t7ykrR8D4KNOae82rPv1sds3Wn3tI/blLS3BMEG1u1xyD9tQmQD01oobJCnpnX8O6zLm38aChs35dZK6pl/ULyPgo3km1eL/MtsJruVtSgYzqnql1Q9cDcGVuzFY9W6HJ/pXk64tY/JU1X4TZsdbVcFf0/dS7Gr8q1hzL0yWN/UY9Y6np5RMO+4cnJS/WsMqmcp/UZtdHGwwYzhp+j40IBQF/akm4JgaraWNb97eXqYF0/nD3ZjYOMmCZIpmVXRUYjowlm91ztBBAwVZflk7lHwdK/0O+liYCdxYu4pwZ69KkgEk8OGb82hYUzrd6b24mJEw88mAjjlbtVbDIyRZzec9NEtT5V5ip27GwJTwctJljoPG31MqzpgVCXTMmYcigjMu+oXlYzHLgqGa1IfRdDyWkI0mw3fYtoyNhYlPjM5Fv/LTNfa96pvHEMN+shbeDvKMY+rghhY8U9Vb64L/QMwvp9iYJ98m/GPLGOIf9R5EExyPBvHCtW/pO358ABOqdLkbWOuOYSd5crDdYiP8ykGpo6buV9CSqldgdEEcuLuLAqGq4iRT5kftN2JuvH9AJh+ez2mZH64owlYKaOvJdM0tirBvMw5GfUK0UQA5bO/hcDouLncDchJrixZ9X781dOWsczk4GTEVUGSqDLOeoc3gRiYetZSNIdfN+lvOWdXVd8yllEfWucOn8Y1iiDzygkvBqaS0UHO515+3w2o/t/pFwIrCpbrjZ1NY377zR9vMTC3MvJoV969vO26LgyBuQ7hj5WrAuQ4GEkXBRvJslwitpTY7TSBHZkYAxuYgGjZHSml+f1U6eM4bBxs53qDTkW1KN9UPL13gkAYyo+q53h0IMfv3Ab+8LRRbFS+oWWTq3NnPekyujAGRtXvTPKMDilCEG2iU4Zb+9hf7cIqu7DKLqyyC6vswir7z9gvi25539ogcuYAAAAASUVORK5CYII=','2025-12-03 14:54:30.037305',54,55,1);
/*!40000 ALTER TABLE `email_outbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flight_reservations`
--

DROP TABLE IF EXISTS `flight_reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flight_reservations` (
  `expires_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL,
  `flight_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2kih3wrn3ub1natnutk1y049h` (`flight_id`),
  CONSTRAINT `FK2kih3wrn3ub1natnutk1y049h` FOREIGN KEY (`flight_id`) REFERENCES `flights` (`id`),
  CONSTRAINT `FK2yok0qcir64fow1xhehvjrysj` FOREIGN KEY (`id`) REFERENCES `reservations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flight_reservations`
--

LOCK TABLES `flight_reservations` WRITE;
/*!40000 ALTER TABLE `flight_reservations` DISABLE KEYS */;
INSERT INTO `flight_reservations` VALUES ('2025-11-17 16:43:31.402752',5,5),('2025-11-17 16:53:53.490981',6,5),('2025-11-17 16:56:17.450723',7,6),('2025-11-21 15:52:45.208223',8,6),('2025-11-23 11:30:50.144711',9,7),('2025-11-23 11:39:10.532185',10,8),('2025-11-23 11:39:10.551652',11,8),('2025-11-23 11:46:19.181059',12,9),('2025-11-23 11:46:19.290056',13,9),('2025-11-23 11:46:19.303287',14,9),('2025-11-23 11:46:19.365492',15,9),('2025-11-23 11:51:00.455046',16,9),('2025-11-23 11:51:00.573324',17,9),('2025-11-23 11:51:00.589697',18,9),('2025-11-23 11:51:00.606680',19,9),('2025-11-23 11:51:00.622973',20,9),('2025-11-23 11:51:00.643704',21,9),('2025-11-23 11:54:04.937722',22,8),('2025-11-23 11:55:12.168180',23,10),('2025-11-23 11:55:12.220024',24,10),('2025-11-23 11:55:34.308448',25,10),('2025-11-23 12:53:39.994455',26,7),('2025-11-23 13:02:15.812934',27,11),('2025-11-23 13:03:46.438124',28,11),('2025-11-23 18:10:52.664160',29,11),('2025-11-23 18:13:28.064061',30,12),('2025-11-23 18:16:31.196193',31,12),('2025-11-23 18:19:00.114192',32,12),('2025-12-01 15:45:43.272707',33,13),('2025-12-01 15:48:48.882317',34,13),('2025-12-01 15:50:34.242071',35,5),('2025-12-01 15:55:27.515558',36,6),('2025-12-01 15:56:40.945575',37,7),('2025-12-01 15:59:06.483282',38,8),('2025-12-01 16:00:59.829902',39,9),('2025-12-01 16:03:50.614876',40,10),('2025-12-01 17:35:06.832208',41,11),('2025-12-01 17:36:45.751602',42,11),('2025-12-01 17:38:21.739105',43,11),('2025-12-01 17:40:01.159649',44,10),('2025-12-03 12:58:20.472993',45,9),('2025-12-03 12:58:20.512474',46,9),('2025-12-03 14:10:52.843149',47,11),('2025-12-03 14:10:52.882439',48,11),('2025-12-03 14:10:52.901792',49,11),('2025-12-03 14:13:52.303758',50,11),('2025-12-03 14:13:52.365889',51,11),('2025-12-03 14:15:22.225277',52,10),('2025-12-03 14:15:54.928352',53,9),('2025-12-03 15:09:26.217325',54,10),('2025-12-03 15:09:26.259680',55,10);
/*!40000 ALTER TABLE `flight_reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flights`
--

DROP TABLE IF EXISTS `flights`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flights` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `airline` varchar(255) NOT NULL,
  `arrival_time` time(6) NOT NULL,
  `departure_time` time(6) NOT NULL,
  `flight_day` date NOT NULL,
  `destination_id` bigint NOT NULL,
  `origin_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1u0glmakf1bky242ovym6q8wi` (`destination_id`),
  KEY `FKj1yhywjt8ddf8kq9g6kvwcnbj` (`origin_id`),
  CONSTRAINT `FK1u0glmakf1bky242ovym6q8wi` FOREIGN KEY (`destination_id`) REFERENCES `airports` (`id`),
  CONSTRAINT `FKj1yhywjt8ddf8kq9g6kvwcnbj` FOREIGN KEY (`origin_id`) REFERENCES `airports` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flights`
--

LOCK TABLES `flights` WRITE;
/*!40000 ALTER TABLE `flights` DISABLE KEYS */;
INSERT INTO `flights` VALUES (5,'Iberia','11:45:00.000000','10:30:00.000000','2025-03-15',2,1),(6,'Iberia','19:35:00.000000','18:20:00.000000','2025-03-18',1,2),(7,'Iberia','11:45:00.000000','10:30:00.000000','2025-03-20',2,1),(8,'Iberia','11:45:00.000000','10:30:00.000000','2025-03-23',1,2),(9,'Iberia','11:45:00.000000','10:30:00.000000','2025-03-26',2,1),(10,'Iberia','11:45:00.000000','10:30:00.000000','2025-03-28',2,1),(11,'Iberia','11:45:00.000000','10:30:00.000000','2025-03-30',2,1),(12,'Iberia','11:45:00.000000','10:30:00.000000','2025-03-24',2,1),(13,'Iberia','11:45:00.000000','10:30:00.000000','2025-05-15',2,1);
/*!40000 ALTER TABLE `flights` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel_employees`
--

DROP TABLE IF EXISTS `hotel_employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel_employees` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `hotel_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK8s6ysb2rxm0ep771fjsy0ayhu` (`user_id`),
  KEY `FKfmeu9jf6f3txro7uh1hb6266x` (`hotel_id`),
  CONSTRAINT `FKfmeu9jf6f3txro7uh1hb6266x` FOREIGN KEY (`hotel_id`) REFERENCES `hotels` (`id`),
  CONSTRAINT `FKs0gbxf9cxlq65yqxw0sa99xfe` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_employees`
--

LOCK TABLES `hotel_employees` WRITE;
/*!40000 ALTER TABLE `hotel_employees` DISABLE KEYS */;
/*!40000 ALTER TABLE `hotel_employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel_favourite_hotels`
--

DROP TABLE IF EXISTS `hotel_favourite_hotels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel_favourite_hotels` (
  `favourite_id` bigint NOT NULL,
  `hotel_id` bigint NOT NULL,
  KEY `FKo879knoqywyapf7f3gxbm9pp2` (`hotel_id`),
  KEY `FKgw7h576x04538ntqqyvlvd16n` (`favourite_id`),
  CONSTRAINT `FKgw7h576x04538ntqqyvlvd16n` FOREIGN KEY (`favourite_id`) REFERENCES `hotel_favourites` (`id`),
  CONSTRAINT `FKo879knoqywyapf7f3gxbm9pp2` FOREIGN KEY (`hotel_id`) REFERENCES `hotels` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_favourite_hotels`
--

LOCK TABLES `hotel_favourite_hotels` WRITE;
/*!40000 ALTER TABLE `hotel_favourite_hotels` DISABLE KEYS */;
INSERT INTO `hotel_favourite_hotels` VALUES (1,2),(1,1);
/*!40000 ALTER TABLE `hotel_favourite_hotels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel_favourites`
--

DROP TABLE IF EXISTS `hotel_favourites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel_favourites` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKl7cihkfkhvd7doogtcqjnypf4` (`user_id`),
  CONSTRAINT `FKadw6scbt8yuiul733qqir5tr` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_favourites`
--

LOCK TABLES `hotel_favourites` WRITE;
/*!40000 ALTER TABLE `hotel_favourites` DISABLE KEYS */;
INSERT INTO `hotel_favourites` VALUES (1,1);
/*!40000 ALTER TABLE `hotel_favourites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel_histories`
--

DROP TABLE IF EXISTS `hotel_histories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel_histories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKfgdrkan9phd06ojjo5e34ry8p` (`user_id`),
  CONSTRAINT `FK5mxfe0ht337iv58xmqwq6c8du` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_histories`
--

LOCK TABLES `hotel_histories` WRITE;
/*!40000 ALTER TABLE `hotel_histories` DISABLE KEYS */;
INSERT INTO `hotel_histories` VALUES (1,1);
/*!40000 ALTER TABLE `hotel_histories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel_history_hotels`
--

DROP TABLE IF EXISTS `hotel_history_hotels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel_history_hotels` (
  `history_id` bigint NOT NULL,
  `hotel_id` bigint NOT NULL,
  KEY `FK3xuhe12xsjq2uq3s7mlva2x39` (`hotel_id`),
  KEY `FKst2ifaw00o1wqmvk7gyyhwk1f` (`history_id`),
  CONSTRAINT `FK3xuhe12xsjq2uq3s7mlva2x39` FOREIGN KEY (`hotel_id`) REFERENCES `hotels` (`id`),
  CONSTRAINT `FKst2ifaw00o1wqmvk7gyyhwk1f` FOREIGN KEY (`history_id`) REFERENCES `hotel_histories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_history_hotels`
--

LOCK TABLES `hotel_history_hotels` WRITE;
/*!40000 ALTER TABLE `hotel_history_hotels` DISABLE KEYS */;
INSERT INTO `hotel_history_hotels` VALUES (1,1),(1,3);
/*!40000 ALTER TABLE `hotel_history_hotels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel_reservations`
--

DROP TABLE IF EXISTS `hotel_reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel_reservations` (
  `check_in` date DEFAULT NULL,
  `check_out` date DEFAULT NULL,
  `id` bigint NOT NULL,
  `hotel_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK95ks46jdfyj63yc3iteo31dne` (`hotel_id`),
  CONSTRAINT `FK3wkj40xulo84lfsvg207cdncm` FOREIGN KEY (`id`) REFERENCES `reservations` (`id`),
  CONSTRAINT `FK95ks46jdfyj63yc3iteo31dne` FOREIGN KEY (`hotel_id`) REFERENCES `hotels` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_reservations`
--

LOCK TABLES `hotel_reservations` WRITE;
/*!40000 ALTER TABLE `hotel_reservations` DISABLE KEYS */;
INSERT INTO `hotel_reservations` VALUES (NULL,NULL,3,1),('2025-03-12','2025-03-15',4,6);
/*!40000 ALTER TABLE `hotel_reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotels`
--

DROP TABLE IF EXISTS `hotels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotels` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `hotel_name` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `direction` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  `stars` varchar(255) DEFAULT NULL,
  `rating` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotels`
--

LOCK TABLES `hotels` WRITE;
/*!40000 ALTER TABLE `hotels` DISABLE KEYS */;
INSERT INTO `hotels` VALUES (1,'Manolo','Madrid','Spain','Jesus Street','28001','',NULL),(2,'Pepe','Barcelona','Spain','Jesus Street','08001','',NULL),(3,'Spain','Ibiza','Spain','Jesus Street','08001','',NULL),(6,'Hotel Sol y Mar','Barcelona','Spain','Calle de la Playa, 123','08001','4',NULL),(7,'Hotel Marimar','Barcelona','Spain','Calle de la Playa, 123','08001','4',NULL);
/*!40000 ALTER TABLE `hotels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loyalty_entity`
--

DROP TABLE IF EXISTS `loyalty_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loyalty_entity` (
  `id` bigint NOT NULL,
  `max_points` int NOT NULL,
  `min_points` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loyalty_entity`
--

LOCK TABLES `loyalty_entity` WRITE;
/*!40000 ALTER TABLE `loyalty_entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `loyalty_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loyalty_entity_seq`
--

DROP TABLE IF EXISTS `loyalty_entity_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loyalty_entity_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loyalty_entity_seq`
--

LOCK TABLES `loyalty_entity_seq` WRITE;
/*!40000 ALTER TABLE `loyalty_entity_seq` DISABLE KEYS */;
INSERT INTO `loyalty_entity_seq` VALUES (1);
/*!40000 ALTER TABLE `loyalty_entity_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loyalty_points`
--

DROP TABLE IF EXISTS `loyalty_points`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loyalty_points` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `points` int NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpo5k4e6nx6no1j433kj03fpjy` (`user_id`),
  CONSTRAINT `FKjx52acv9c5myhf3y1vmrufayb` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loyalty_points`
--

LOCK TABLES `loyalty_points` WRITE;
/*!40000 ALTER TABLE `loyalty_points` DISABLE KEYS */;
/*!40000 ALTER TABLE `loyalty_points` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loyalty_tier_benefits`
--

DROP TABLE IF EXISTS `loyalty_tier_benefits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loyalty_tier_benefits` (
  `tier_id` bigint NOT NULL,
  `benefit` varchar(255) DEFAULT NULL,
  KEY `FKdsiq8mmmkphuor777s6hpurr8` (`tier_id`),
  CONSTRAINT `FKdsiq8mmmkphuor777s6hpurr8` FOREIGN KEY (`tier_id`) REFERENCES `loyalty_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loyalty_tier_benefits`
--

LOCK TABLES `loyalty_tier_benefits` WRITE;
/*!40000 ALTER TABLE `loyalty_tier_benefits` DISABLE KEYS */;
/*!40000 ALTER TABLE `loyalty_tier_benefits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `message` varchar(255) NOT NULL,
  `status` enum('READ','UNREAD') NOT NULL,
  `type` enum('CHECK_IN_AVAILABLE','FLIGHT_CANCELLED','FLIGHT_DELAYED','GENERAL_INFO','RESERVATION_CANCELLED','RESERVATION_CONFIRMED') NOT NULL,
  `reservation_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeua0pytgljr86idnpkhuir1mu` (`reservation_id`),
  KEY `FK9y21adhxn0ayjhfocscqox7bh` (`user_id`),
  CONSTRAINT `FK9y21adhxn0ayjhfocscqox7bh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKeua0pytgljr86idnpkhuir1mu` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (1,'Your reservation with ID8has been created','UNREAD','RESERVATION_CONFIRMED',8,1),(2,'Your reservation with ID9has been created','UNREAD','RESERVATION_CONFIRMED',9,1),(3,'Your reservation with ID10has been created','UNREAD','RESERVATION_CONFIRMED',10,1),(4,'Your reservation with ID11has been created','UNREAD','RESERVATION_CONFIRMED',11,1),(5,'Your reservation with ID12has been created','UNREAD','RESERVATION_CONFIRMED',12,1),(6,'Your reservation with ID13has been created','UNREAD','RESERVATION_CONFIRMED',13,1),(7,'Your reservation with ID14has been created','UNREAD','RESERVATION_CONFIRMED',14,1),(8,'Your reservation with ID15has been created','UNREAD','RESERVATION_CONFIRMED',15,1),(9,'Your reservation with ID16has been created','UNREAD','RESERVATION_CONFIRMED',16,1),(10,'Your reservation with ID17has been created','UNREAD','RESERVATION_CONFIRMED',17,1),(11,'Your reservation with ID18has been created','UNREAD','RESERVATION_CONFIRMED',18,1),(12,'Your reservation with ID19has been created','UNREAD','RESERVATION_CONFIRMED',19,1),(13,'Your reservation with ID20has been created','UNREAD','RESERVATION_CONFIRMED',20,1),(14,'Your reservation with ID21has been created','UNREAD','RESERVATION_CONFIRMED',21,1),(15,'Your reservation with ID22has been created','UNREAD','RESERVATION_CONFIRMED',22,1),(16,'Your reservation with ID23has been created','UNREAD','RESERVATION_CONFIRMED',23,1),(17,'Your reservation with ID24has been created','UNREAD','RESERVATION_CONFIRMED',24,1),(18,'Your reservation with ID25has been created','UNREAD','RESERVATION_CONFIRMED',25,1),(19,'Your reservation with ID26has been created','UNREAD','RESERVATION_CONFIRMED',26,1),(20,'Your reservation with ID27has been created','UNREAD','RESERVATION_CONFIRMED',27,1),(21,'Your reservation with ID27has been created','UNREAD','RESERVATION_CONFIRMED',27,1),(22,'Your reservation with ID27has been created','UNREAD','RESERVATION_CONFIRMED',27,1),(23,'Your reservation with ID27has been created','UNREAD','RESERVATION_CONFIRMED',27,1),(24,'Your reservation with ID27has been created','UNREAD','RESERVATION_CONFIRMED',27,1),(25,'Your reservation with ID27has been created','UNREAD','RESERVATION_CONFIRMED',27,1),(26,'Your reservation with ID27has been created','UNREAD','RESERVATION_CONFIRMED',27,1),(27,'Your reservation with ID27has been created','UNREAD','RESERVATION_CONFIRMED',27,1),(28,'Your reservation with ID27has been created','UNREAD','RESERVATION_CONFIRMED',27,1),(29,'Your reservation with ID27has been created','UNREAD','RESERVATION_CONFIRMED',27,1),(30,'Your reservation with ID28has been created','UNREAD','RESERVATION_CONFIRMED',28,1),(31,'Your reservation with ID29has been created','UNREAD','RESERVATION_CONFIRMED',29,1),(32,'Your reservation with ID30has been created','UNREAD','RESERVATION_CONFIRMED',30,1),(33,'Your reservation with ID31has been created','UNREAD','RESERVATION_CONFIRMED',31,1),(34,'Your reservation with ID32has been created','UNREAD','RESERVATION_CONFIRMED',32,1),(35,'Your reservation with ID38has been created','UNREAD','RESERVATION_CONFIRMED',38,1),(36,'Your reservation with ID38has been created','UNREAD','RESERVATION_CONFIRMED',38,1),(37,'Your reservation with ID39has been created','UNREAD','RESERVATION_CONFIRMED',39,1),(38,'Your reservation with ID38has been created','UNREAD','RESERVATION_CONFIRMED',38,1),(39,'Your reservation with ID39has been created','UNREAD','RESERVATION_CONFIRMED',39,1),(40,'Your reservation with ID38has been created','UNREAD','RESERVATION_CONFIRMED',38,1),(41,'Your reservation with ID39has been created','UNREAD','RESERVATION_CONFIRMED',39,1),(42,'Your reservation with ID40has been created','UNREAD','RESERVATION_CONFIRMED',40,1),(43,'Your reservation with ID44has been created','UNREAD','RESERVATION_CONFIRMED',44,1),(44,'Your reservation with ID46has been created','UNREAD','RESERVATION_CONFIRMED',46,1),(45,'Your reservation with ID45has been created','UNREAD','RESERVATION_CONFIRMED',45,1),(46,'Your reservation with ID49has been created','UNREAD','RESERVATION_CONFIRMED',49,1),(47,'Your reservation with ID48has been created','UNREAD','RESERVATION_CONFIRMED',48,1),(48,'Your reservation with ID47has been created','UNREAD','RESERVATION_CONFIRMED',47,1),(49,'Your reservation with ID50has been created','UNREAD','RESERVATION_CONFIRMED',50,1),(50,'Your reservation with ID51has been created','UNREAD','RESERVATION_CONFIRMED',51,1),(51,'Your reservation with ID52has been created','UNREAD','RESERVATION_CONFIRMED',52,1),(52,'Your reservation with ID53has been created','UNREAD','RESERVATION_CONFIRMED',53,1),(53,'Your reservation with ID54has been created','UNREAD','RESERVATION_CONFIRMED',54,1),(54,'Your reservation with ID55has been created','UNREAD','RESERVATION_CONFIRMED',55,1);
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) NOT NULL,
  `card_brand` varchar(50) DEFAULT NULL,
  `card_last4` varchar(4) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `currency` varchar(3) NOT NULL,
  `failed_at` datetime(6) DEFAULT NULL,
  `failure_message` varchar(500) DEFAULT NULL,
  `paid_at` datetime(6) DEFAULT NULL,
  `payment_method` tinyint DEFAULT NULL,
  `status` enum('FAILED','PENDING','REFUNDED','REQUIRES_ACTION','SUCCESS') NOT NULL,
  `stripe_charge_id` varchar(255) DEFAULT NULL,
  `stripe_customer_id` varchar(1000) DEFAULT NULL,
  `stripe_payment_intent_id` varchar(255) NOT NULL,
  `webhook_received_at` datetime(6) DEFAULT NULL,
  `reservation_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpuc8mkpduwb4ws7khxcoo0s3t` (`stripe_payment_intent_id`),
  UNIQUE KEY `UKbec64b0nlxlmn5h3tpe7y4pkp` (`stripe_charge_id`),
  KEY `FKp8yh4sjt3u0g6aru1oxfh3o14` (`reservation_id`),
  CONSTRAINT `FKp8yh4sjt3u0g6aru1oxfh3o14` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`),
  CONSTRAINT `payments_chk_1` CHECK ((`payment_method` between 0 and 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `booking_status` enum('CANCELED','COMPLETED','CONFIRMED','EXPIRED','NOT_SHOWN','PAID','PENDING_PAYMENT','REFUNDED') DEFAULT NULL,
  `reservation_date` datetime(6) DEFAULT NULL,
  `total_price` decimal(38,2) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `check_in_time` datetime(6) DEFAULT NULL,
  `checked_in` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb5g9io5h54iwl2inkno50ppln` (`user_id`),
  CONSTRAINT `FKb5g9io5h54iwl2inkno50ppln` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservations`
--

LOCK TABLES `reservations` WRITE;
/*!40000 ALTER TABLE `reservations` DISABLE KEYS */;
INSERT INTO `reservations` VALUES (2,'PENDING_PAYMENT','2025-10-07 16:52:30.290221',789.99,1,NULL,NULL),(3,'PENDING_PAYMENT','2025-10-08 16:18:27.912408',1800.00,1,NULL,NULL),(4,'PENDING_PAYMENT','2025-11-15 16:07:09.042342',350.75,1,NULL,NULL),(5,'EXPIRED','2025-11-17 16:28:31.399751',630.50,1,NULL,NULL),(6,'EXPIRED','2025-11-17 16:38:53.477978',120.00,1,NULL,NULL),(7,'EXPIRED','2025-11-17 16:41:17.440722',630.50,1,NULL,NULL),(8,'EXPIRED','2025-11-21 15:37:45.202709',120.00,1,NULL,NULL),(9,'EXPIRED','2025-11-23 11:15:50.141609',630.50,1,NULL,NULL),(10,'EXPIRED','2025-11-23 11:24:10.529568',630.50,1,NULL,NULL),(11,'EXPIRED','2025-11-23 11:24:10.549015',630.50,1,NULL,NULL),(12,'EXPIRED','2025-11-23 11:31:19.180010',350.50,1,NULL,NULL),(13,'EXPIRED','2025-11-23 11:31:19.289524',350.50,1,NULL,NULL),(14,'EXPIRED','2025-11-23 11:31:19.303287',350.50,1,NULL,NULL),(15,'EXPIRED','2025-11-23 11:31:19.365492',280.00,1,NULL,NULL),(16,'EXPIRED','2025-11-23 11:36:00.453486',120.00,1,NULL,NULL),(17,'EXPIRED','2025-11-23 11:36:00.572806',120.00,1,NULL,NULL),(18,'EXPIRED','2025-11-23 11:36:00.588646',120.00,1,NULL,NULL),(19,'EXPIRED','2025-11-23 11:36:00.605618',120.00,1,NULL,NULL),(20,'EXPIRED','2025-11-23 11:36:00.621975',120.00,1,NULL,NULL),(21,'EXPIRED','2025-11-23 11:36:00.642654',120.00,1,NULL,NULL),(22,'EXPIRED','2025-11-23 11:39:04.937722',120.00,1,NULL,NULL),(23,'EXPIRED','2025-11-23 11:40:12.167122',350.50,1,NULL,NULL),(24,'EXPIRED','2025-11-23 11:40:12.219492',280.00,1,NULL,NULL),(25,'EXPIRED','2025-11-23 11:40:34.307925',120.00,1,NULL,NULL),(26,'EXPIRED','2025-11-23 12:38:39.993411',120.00,1,NULL,NULL),(27,'EXPIRED','2025-11-23 12:47:15.811357',350.50,1,NULL,NULL),(28,'EXPIRED','2025-11-23 12:48:46.436546',280.00,1,NULL,NULL),(29,'EXPIRED','2025-11-23 17:55:52.662528',120.00,1,NULL,NULL),(30,'EXPIRED','2025-11-23 17:58:28.063024',120.00,1,NULL,NULL),(31,'EXPIRED','2025-11-23 18:01:31.194628',350.50,1,NULL,NULL),(32,'EXPIRED','2025-11-23 18:04:00.112051',280.00,1,NULL,NULL),(33,'EXPIRED','2025-12-01 15:30:43.271706',630.50,1,NULL,NULL),(34,'EXPIRED','2025-12-01 15:33:48.880765',120.00,1,NULL,NULL),(35,'EXPIRED','2025-12-01 15:35:34.240071',120.00,1,NULL,NULL),(36,'EXPIRED','2025-12-01 15:40:27.514509',120.00,1,NULL,NULL),(37,'EXPIRED','2025-12-01 15:41:40.944533',120.00,1,NULL,NULL),(38,'EXPIRED','2025-12-01 15:44:06.481709',120.00,1,NULL,NULL),(39,'EXPIRED','2025-12-01 15:45:59.828858',120.00,1,NULL,NULL),(40,'EXPIRED','2025-12-01 15:48:50.613307',120.00,1,NULL,NULL),(41,'EXPIRED','2025-12-01 17:20:06.831208',120.00,1,NULL,NULL),(42,'EXPIRED','2025-12-01 17:21:45.750552',350.50,1,NULL,NULL),(43,'EXPIRED','2025-12-01 17:23:21.738071',280.00,1,NULL,NULL),(44,'EXPIRED','2025-12-01 17:25:01.158090',350.50,1,NULL,NULL),(45,'EXPIRED','2025-12-03 12:43:20.471398',120.00,1,NULL,NULL),(46,'EXPIRED','2025-12-03 12:43:20.511473',120.00,1,NULL,NULL),(47,'PENDING_PAYMENT','2025-12-03 13:55:52.841149',120.00,1,NULL,NULL),(48,'PENDING_PAYMENT','2025-12-03 13:55:52.881380',120.00,1,NULL,NULL),(49,'PENDING_PAYMENT','2025-12-03 13:55:52.900597',120.00,1,NULL,NULL),(50,'PENDING_PAYMENT','2025-12-03 13:58:52.302712',350.50,1,NULL,NULL),(51,'PENDING_PAYMENT','2025-12-03 13:58:52.364826',280.00,1,NULL,NULL),(52,'PENDING_PAYMENT','2025-12-03 14:00:22.224224',120.00,1,NULL,NULL),(53,'PENDING_PAYMENT','2025-12-03 14:00:54.927834',120.00,1,NULL,NULL),(54,'PENDING_PAYMENT','2025-12-03 14:54:26.215708',350.50,1,NULL,NULL),(55,'PENDING_PAYMENT','2025-12-03 14:54:26.258125',280.00,1,NULL,NULL);
/*!40000 ALTER TABLE `reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_reservations`
--

DROP TABLE IF EXISTS `room_reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_reservations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `reservation_id` bigint DEFAULT NULL,
  `room_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKram8gh010g2dxvy1nhueufbna` (`reservation_id`),
  KEY `FKf520kpinewy2hpxke3uh7qmvd` (`room_id`),
  CONSTRAINT `FKf520kpinewy2hpxke3uh7qmvd` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`),
  CONSTRAINT `FKram8gh010g2dxvy1nhueufbna` FOREIGN KEY (`reservation_id`) REFERENCES `hotel_reservations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_reservations`
--

LOCK TABLES `room_reservations` WRITE;
/*!40000 ALTER TABLE `room_reservations` DISABLE KEYS */;
INSERT INTO `room_reservations` VALUES (1,4,12);
/*!40000 ALTER TABLE `room_reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rooms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `available` bit(1) NOT NULL,
  `cost_per_night` decimal(38,2) DEFAULT NULL,
  `num_room` varchar(255) DEFAULT NULL,
  `type` enum('DOUBLE','INDIVIDUAL','PRESIDENTIAL_SUITE','SUITE','TRIPLE') DEFAULT NULL,
  `hotel_id` bigint DEFAULT NULL,
  `room_id` bigint DEFAULT NULL,
  `reservation_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp5lufxy0ghq53ugm93hdc941k` (`hotel_id`),
  KEY `FK2yy5dsktc1d6auksogrb0xper` (`room_id`),
  KEY `FKfmgdtdr1m1cbjof7lfmjmxl4d` (`reservation_id`),
  CONSTRAINT `FK2yy5dsktc1d6auksogrb0xper` FOREIGN KEY (`room_id`) REFERENCES `hotel_reservations` (`id`),
  CONSTRAINT `FKfmgdtdr1m1cbjof7lfmjmxl4d` FOREIGN KEY (`reservation_id`) REFERENCES `hotel_reservations` (`id`),
  CONSTRAINT `FKp5lufxy0ghq53ugm93hdc941k` FOREIGN KEY (`hotel_id`) REFERENCES `hotels` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES (1,_binary '\0',400.00,'100','INDIVIDUAL',1,NULL,3),(2,_binary '\0',400.00,'101','INDIVIDUAL',1,NULL,3),(3,_binary '\0',1000.00,'400','SUITE',1,NULL,3),(4,_binary '\0',400.00,'100','INDIVIDUAL',2,NULL,NULL),(5,_binary '\0',400.00,'101','INDIVIDUAL',2,NULL,NULL),(6,_binary '\0',1000.00,'400','SUITE',2,NULL,NULL),(7,_binary '\0',400.00,'100','INDIVIDUAL',3,NULL,NULL),(8,_binary '\0',400.00,'101','INDIVIDUAL',3,NULL,NULL),(9,_binary '\0',1000.00,'400','SUITE',3,NULL,NULL),(10,_binary '',120.50,'100A','INDIVIDUAL',6,NULL,NULL),(11,_binary '',200.00,'101B','DOUBLE',6,NULL,NULL),(12,_binary '\0',350.75,'102C','SUITE',6,NULL,NULL),(13,_binary '',120.50,'100A','INDIVIDUAL',7,NULL,NULL),(14,_binary '',200.00,'101B','DOUBLE',7,NULL,NULL),(15,_binary '',350.75,'102C','SUITE',7,NULL,NULL);
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seats`
--

DROP TABLE IF EXISTS `seats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seats` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `available` bit(1) NOT NULL,
  `cost_per_seat` decimal(38,2) NOT NULL,
  `seat_class` enum('BUSINESS','ECONOMY','PREMIUM') NOT NULL,
  `seat_column` varchar(255) NOT NULL,
  `seat_number` varchar(255) NOT NULL,
  `seat_row` int NOT NULL,
  `seat_type` enum('AISLE','MIDDLE','WINDOW') NOT NULL,
  `flight_id` bigint DEFAULT NULL,
  `reservation_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8qbyfo5qix0qqg5euuk81qo0p` (`flight_id`),
  KEY `FKi9m9bbx0q62s4v4oixyft945o` (`reservation_id`),
  CONSTRAINT `FK8qbyfo5qix0qqg5euuk81qo0p` FOREIGN KEY (`flight_id`) REFERENCES `flights` (`id`),
  CONSTRAINT `FKi9m9bbx0q62s4v4oixyft945o` FOREIGN KEY (`reservation_id`) REFERENCES `flight_reservations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seats`
--

LOCK TABLES `seats` WRITE;
/*!40000 ALTER TABLE `seats` DISABLE KEYS */;
INSERT INTO `seats` VALUES (13,_binary '',350.50,'BUSINESS','A','1A',1,'WINDOW',5,NULL),(14,_binary '',280.00,'BUSINESS','B','1B',1,'MIDDLE',5,NULL),(15,_binary '',120.00,'ECONOMY','C','12C',12,'AISLE',5,NULL),(16,_binary '',350.50,'BUSINESS','A','1A',1,'WINDOW',6,NULL),(17,_binary '',280.00,'BUSINESS','B','1B',1,'MIDDLE',6,NULL),(18,_binary '',120.00,'ECONOMY','C','12C',12,'AISLE',6,NULL),(19,_binary '',350.50,'BUSINESS','A','1A',1,'WINDOW',7,NULL),(20,_binary '',280.00,'BUSINESS','B','1B',1,'MIDDLE',7,NULL),(21,_binary '',120.00,'ECONOMY','C','12C',12,'AISLE',7,NULL),(22,_binary '',350.50,'BUSINESS','A','1A',1,'WINDOW',8,NULL),(23,_binary '',280.00,'BUSINESS','B','1B',1,'MIDDLE',8,NULL),(24,_binary '',120.00,'ECONOMY','C','12C',12,'AISLE',8,NULL),(25,_binary '',350.50,'BUSINESS','A','1A',1,'WINDOW',9,NULL),(26,_binary '',280.00,'BUSINESS','B','1B',1,'MIDDLE',9,NULL),(27,_binary '\0',120.00,'ECONOMY','C','12C',12,'AISLE',9,53),(28,_binary '\0',350.50,'BUSINESS','A','1A',1,'WINDOW',10,54),(29,_binary '\0',280.00,'BUSINESS','B','1B',1,'MIDDLE',10,55),(30,_binary '\0',120.00,'ECONOMY','C','12C',12,'AISLE',10,52),(31,_binary '\0',350.50,'BUSINESS','A','1A',1,'WINDOW',11,50),(32,_binary '\0',280.00,'BUSINESS','B','1B',1,'MIDDLE',11,51),(33,_binary '\0',120.00,'ECONOMY','C','12C',12,'AISLE',11,49),(34,_binary '',350.50,'BUSINESS','A','1A',1,'WINDOW',12,NULL),(35,_binary '',280.00,'BUSINESS','B','1B',1,'MIDDLE',12,NULL),(36,_binary '',120.00,'ECONOMY','C','12C',12,'AISLE',12,NULL),(37,_binary '',350.50,'BUSINESS','A','1A',1,'WINDOW',13,NULL),(38,_binary '',280.00,'BUSINESS','B','1B',1,'MIDDLE',13,NULL),(39,_binary '',120.00,'ECONOMY','C','12C',12,'AISLE',13,NULL);
/*!40000 ALTER TABLE `seats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tokens`
--

DROP TABLE IF EXISTS `tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tokens` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expired` bit(1) NOT NULL,
  `revoked` bit(1) NOT NULL,
  `token` varchar(500) DEFAULT NULL,
  `token_type` enum('BEARER') DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKna3v9f8s7ucnj16tylrs822qj` (`token`),
  KEY `FK_tokens_user_id` (`user_id`),
  CONSTRAINT `FK_tokens_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tokens`
--

LOCK TABLES `tokens` WRITE;
/*!40000 ALTER TABLE `tokens` DISABLE KEYS */;
INSERT INTO `tokens` VALUES (1,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzU5NzU3MzgyLCJleHAiOjE3NTk4NDM3ODJ9.PisGiYXQwMWvv_olavjPumNGH_9Igd3Qce9FRauGaqs','BEARER',1),(2,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzU5NzYyMTA3LCJleHAiOjE3NTk4NDg1MDd9.p8xqVntpgZqdCNQT5oHNnPFZpEYRsZ-cYfxC2TjClsA','BEARER',1),(3,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzU5ODQ2NDY1LCJleHAiOjE3NTk5MzI4NjV9.TLr-hg7jKkthV-m-bB_EVsiDdDLNE4Gj2Ml6Kzv1oUk','BEARER',1),(4,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzU5OTI4MTM4LCJleHAiOjE3NjAwMTQ1Mzh9.x6nTiUD6sJmtt2xD0KQD-QQ2LWiKkKCtHStJG5UuXbo','BEARER',1),(5,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzYyODc0OTMxLCJleHAiOjE3NjI5NjEzMzF9.yb8uQVQZaDSnyn5Nhpb1vqoER5bff3GC9Jn4vj4L5H8','BEARER',1),(6,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzYzMDQ3MTIzLCJleHAiOjE3NjMxMzM1MjN9.U34cmsspiEKObgju_tHQcouPmYxrU6FFTHqEE5Y2z8Q','BEARER',1),(7,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzYzMDQ3MTMzLCJleHAiOjE3NjMxMzM1MzN9.LZhc8Od6f24MdnEXvzDtrkhr9jFD9A2slKQLvpinxUU','BEARER',1),(8,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzYzMjE3Mjk2LCJleHAiOjE3NjMzMDM2OTZ9.-vaTousQCAlOpFkEu7IoUG76_G5NBEuHkMdHkc0-8B4','BEARER',1),(9,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzYzMzA2OTkyLCJleHAiOjE3NjMzOTMzOTJ9.iViWVqTLCTTPyHAYyuo82Df90Dq-nHHXp_LIXSthUVI','BEARER',1),(10,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzYzMzA3MDA1LCJleHAiOjE3NjMzOTM0MDV9.2o5oJG0Knm68GKsGcHanAtMjKKo3Zvma0oC5-QXrpnk','BEARER',1),(11,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzYzMzA3MTA5LCJleHAiOjE3NjMzOTM1MDl9.kOv_IqMhaGeiVYxONpDSymYDqYKrOOaRwpgdAismycU','BEARER',1),(12,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzYzMzg4OTg3LCJleHAiOjE3NjM0NzUzODd9.WAt_v-SQ2I-VnObn0_J8sBkny3hcpTZql4X5nh4R51M','BEARER',1),(13,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ0cmFiYWphZG9yQGhvdGVsLmNvbSIsInJvbGVzIjoiSE9URUxfV09SS0VSIiwibmFtZSI6Ikp1YW4gUMOpcmV6Iiwic3ViIjoidHJhYmFqYWRvckBob3RlbC5jb20iLCJpYXQiOjE3NjM1NjUwNjMsImV4cCI6MTc2MzY1MTQ2M30.vLKsOizopWgPAewSw-UELOMgPBpzFg3LET0VXJ5Fe08','BEARER',3),(14,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ0cmFiYWphZG9yQGFlcm9wdWVydG80LmNvbSIsInJvbGVzIjoiQUlSUE9SVF9XT1JLRVIiLCJuYW1lIjoiTWFyw61hIEdhcmPDrWEiLCJzdWIiOiJ0cmFiYWphZG9yQGFlcm9wdWVydG80LmNvbSIsImlhdCI6MTc2MzU2NjMzMSwiZXhwIjoxNzYzNjUyNzMxfQ.QiODIPW4Un3Nm6ZdSLYxCeNY317FY1bRFuNnBGaGkvA','BEARER',10),(15,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ0cmFiYWphZG9yQGhvdGVsMi5jb20iLCJyb2xlcyI6IkFJUlBPUlRfV09SS0VSIiwibmFtZSI6Ik1hcsOtYSBHYXJjw61hIiwic3ViIjoidHJhYmFqYWRvckBob3RlbDIuY29tIiwiaWF0IjoxNzYzNTY2MzQwLCJleHAiOjE3NjM2NTI3NDB9.8eOmbj8_EME28ExQB0qoZh90dul0vCK1OUal_DnRWNQ','BEARER',11),(16,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ0cmFiYWphZG9yQGhvdGVsMi5jb20iLCJyb2xlcyI6IkFJUlBPUlRfV09SS0VSIiwibmFtZSI6Ik1hcsOtYSBHYXJjw61hIiwic3ViIjoidHJhYmFqYWRvckBob3RlbDIuY29tIiwiaWF0IjoxNzYzNTY2MzY0LCJleHAiOjE3NjM2NTI3NjR9.MJF3BchLwadt-Cys5-dm3cy6TE5R6ERP75LO35HSxl0','BEARER',11),(17,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ0cmFiYWphZG9yQGhvdGVsMi5jb20iLCJyb2xlcyI6IkFJUlBPUlRfV09SS0VSIiwibmFtZSI6Ik1hcsOtYSBHYXJjw61hIiwic3ViIjoidHJhYmFqYWRvckBob3RlbDIuY29tIiwiaWF0IjoxNzYzNTY2NDMxLCJleHAiOjE3NjM2NTI4MzF9.2IfpVrbxO0ixmoyvtJpdnWLJlHwRUZzaUGRy9BypFkA','BEARER',11),(18,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzYzNzM1NjE5LCJleHAiOjE3NjM4MjIwMTl9.lWv6LN3_w5kFmOfdnm6__zEf0jGHy_tOe5EEDtysM9A','BEARER',1),(19,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzYzODkyMzI5LCJleHAiOjE3NjM5Nzg3Mjl9.ccj9CfElIwSI8VHdoqKDvExoA9XP61ZmA493cu_f9Kk','BEARER',1),(20,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzYzOTE2ODcwLCJleHAiOjE3NjQwMDMyNzB9.DtC0q6807jRmjtWft17CExYWP3-SWy00XsUUu1fUnMo','BEARER',1),(21,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzY0NTk3NTgyLCJleHAiOjE3NjQ2ODM5ODJ9.ZHkm5dMbsDSGwdsfyiJLUcGz5RHBQ8c3OXVbMfKlyJw','BEARER',1),(22,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzY0NTk4NjYxLCJleHAiOjE3NjQ2ODUwNjF9.PRG_1ZIudfdP8LF0cEHbcbdF3l-wjzOzXW0u68cid6s','BEARER',1),(23,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzY0NTk4NzQwLCJleHAiOjE3NjQ2ODUxNDB9.Z6XItQMG8aKKMBeSjV9tRtyMD6eY1wl_RLtRWCT2XQo','BEARER',1),(24,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzY0NzYyMDgwLCJleHAiOjE3NjQ4NDg0ODB9.xfi_xJw9pxFGyYnuRcG8BZG44MhHD6BAYqlbjIkaIo4','BEARER',1),(25,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzY0NzY2NDg3LCJleHAiOjE3NjQ4NTI4ODd9.ssAml9oAmVIoldP4EghiMubjYprnJiTrNdysj1IOang','BEARER',1),(26,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzY0NzY2Njc0LCJleHAiOjE3NjQ4NTMwNzR9.RPGLMrXk_9LzvtH6GFI8UZCC2G9bUrvPoyVWOmVUfIs','BEARER',1),(27,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzY0NzY5ODg5LCJleHAiOjE3NjQ4NTYyODl9.cn-hEpMcscb-1qfrWQWUmVNPGQng78Kesc5an_UvHio','BEARER',1),(28,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhc2llci5ocjE1QGdtYWlsLmNvbSIsInJvbGVzIjoiVVNFUiIsIm5hbWUiOiJhc2llciIsInN1YiI6ImFzaWVyLmhyMTVAZ21haWwuY29tIiwiaWF0IjoxNzY0NzY5OTYzLCJleHAiOjE3NjQ4NTYzNjN9.K6cl4V6VB5uSGQ-DKlQNZnjWnB5QsK93TMtyHHF1xJc','BEARER',1);
/*!40000 ALTER TABLE `tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_role` enum('ADMIN','OWNER','HOTEL_WORKER','USER','AIRPORT_WORKER') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKjhck7kjdogc7yia7qamc89ypv` (`mail`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2025-10-02 18:17:31.877496','asier.hr15@gmail.com','asier','$2a$10$FKbyUG6VjFh/TBDTH0xrFeU.Xp.DawaSYOlh0ZbiMNKg6SK.W79vq','USER'),(3,'2025-11-19 16:11:02.932070','trabajador@hotel.com','Juan Pérez','$2a$10$L8zJhd15c/vBnx4rPp1qlu6/Cdk4sdE3NPo9pkSaO6zbr9Cpbzk8u','HOTEL_WORKER'),(4,'2025-11-19 16:11:49.347503','trabajador@aeropuerto.com','María García','$2a$10$MRi6t7.xGXTN84WZNcs2f.8vUyZ7a27tkZSINrITQpSeFJ6phWZy2','AIRPORT_WORKER'),(6,'2025-11-19 16:13:24.446540','trabajador@aeropuerto1.com','María García','$2a$10$8/m48Gtvikf1X9evxGey2OVcdb1CMS0uBoLnQbnUBnUoLjdAYixIW','AIRPORT_WORKER'),(7,'2025-11-19 16:24:19.221717','trabajador@aeropuerto2.com','María García','$2a$10$Gm3AoerdwaSgSwsCMhwQ.ubOv8j/JfH7tpk2i0ayXSOsaDilVwoq2','AIRPORT_WORKER'),(9,'2025-11-19 16:25:10.803215','trabajador@aeropuerto3.com','María García','$2a$10$EkPrZ8oW8OsB9b0qnUWkC.dkjdsXWCr8FEr/91mQ/WVQFmqcQyDIC','AIRPORT_WORKER'),(10,'2025-11-19 16:32:11.498870','trabajador@aeropuerto4.com','María García','$2a$10$WNDUTZQUWvHYBLsJewdozeJPQ1Dz/LN6hVhg..KM2C90xiXP688Ca','AIRPORT_WORKER'),(11,'2025-11-19 16:32:20.549106','trabajador@hotel2.com','María García','$2a$10$txf.77vv14op0nVI7PWBHOXWu8ueSG1rnYZFIdoIe8Opcb3GFCHSa','AIRPORT_WORKER');
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

-- Dump completed on 2025-12-03 16:51:26
