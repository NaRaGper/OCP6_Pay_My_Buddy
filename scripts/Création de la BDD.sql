SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `payMyBuddyDB`;
USE `payMyBuddyDB`;

DROP TABLE IF EXISTS `Users`;
CREATE TABLE `Users` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `email` VARCHAR(255) NOT NULL UNIQUE,
  `username` VARCHAR(45) NOT NULL DEFAULT(`email`),
  `balance` DOUBLE NOT NULL DEFAULT 0,
  `bank_account_number` VARCHAR(45) NOT NULL DEFAULT '',
  `salt` CHAR(12) NOT NULL UNIQUE,
  `salted_hash` CHAR(64) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS `Transactions`;
CREATE TABLE `Transactions` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `type` ENUM('user', 'toBank', 'fromBank', 'toCash', 'fromCash') NOT NULL,
  `amount` DOUBLE NOT NULL,
  `date` DATETIME NOT NULL DEFAULT LOCALTIME,
  `description` VARCHAR(255) NOT NULL DEFAULT '',
  `sender_id` INT,
  `receiver_id` INT,
  FOREIGN KEY (`sender_id`) REFERENCES `Users`(`id`),
  FOREIGN KEY (`receiver_id`) REFERENCES `Users`(`id`)
);

DROP TABLE IF EXISTS `Connections`;
CREATE TABLE `Connections` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `date` DATETIME NOT NULL DEFAULT LOCALTIME,
  `user_id1` INT NOT NULL,
  `user_id2` INT NOT NULL,
  FOREIGN KEY (`user_id1`) REFERENCES `Users`(`id`),
  FOREIGN KEY (`user_id2`) REFERENCES `Users`(`id`),
  CHECK (`user_id1`!=`user_id2`),
  UNIQUE INDEX `unique_pair` ((LEAST(`user_id1`, `user_id2`)), (GREATEST(`user_id1`, `user_id2`)))
);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
