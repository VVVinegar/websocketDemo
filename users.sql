/*
MySQL Data Transfer
Source Host: localhost
Source Database: websockettest
Target Host: localhost
Target Database: websockettest
Date: 2017/5/10 19:18:37
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `users` VALUES ('26', 'first');
INSERT INTO `users` VALUES ('27', 'second');
INSERT INTO `users` VALUES ('28', 'Vin');
INSERT INTO `users` VALUES ('29', 'forth');
INSERT INTO `users` VALUES ('30', 'fifth');
