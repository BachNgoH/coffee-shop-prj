CREATE DATABASE IF NOT EXISTS `coffee-shop-ver2`;
USE `coffee-shop-ver2`;
DROP TABLE IF EXISTS `product`;

CREATE TABLE IF NOT EXISTS `product`(
	`id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(100) NOT NULL,
    `price` INT NOT NULL,
    `image_url` VARCHAR(255),
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `status`;
CREATE TABLE IF NOT EXISTS `status`(
	`id` INT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `product` VALUES (1, "Americano", 20, "/images/product-1.jpg");
INSERT INTO `product` VALUES (2, "Milk Coffee", 15, "/images/product-2.jpg");
INSERT INTO `product` VALUES (3, "Black Coffee", 20, "/images/product-3.jpg");
INSERT INTO `product` VALUES (4, "Matcha Tea", 22, "/images/product-4.jpg");
INSERT INTO `product` VALUES (5, "Smoothie", 20, "/images/product-5.jpg");
INSERT INTO `product` VALUES (6, "Matcha Latte", 25, "/images/product-6.jpg");
INSERT INTO `product` VALUES (7, "Capuchino", 25, "/images/product-7.jpg");
INSERT INTO `product` VALUES (9, "Strawberry Smoothie", 35, "/images/product-9.jpg");

INSERT INTO `status` VALUES (1, "Verifying");
INSERT INTO `status` VALUES (2, "Delivering");
INSERT INTO `status` VALUES (3, "Delivered");





