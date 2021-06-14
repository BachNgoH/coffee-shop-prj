CREATE DATABASE IF NOT EXISTS `coffee-shop-ver2`;
USE `coffee-shop-ver2`;
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`(
	`id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(100) NOT NULL,
    `price` INT NOT NULL,
    `image_url` VARCHAR(255),
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `status`(
	`id` INT NOT NULL,
    `name` VARCHAR(255) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
