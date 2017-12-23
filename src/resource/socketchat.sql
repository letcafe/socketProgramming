/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : socketchat

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-12-23 18:32:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `ipAddress` varchar(255) DEFAULT NULL,
  `userName` varchar(20) DEFAULT NULL,
  `nickName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '192.168.2.196', '郭冬雨', '帅气男');
INSERT INTO `user` VALUES ('2', '192.168.2.38', '魏宁', 'Socket');
INSERT INTO `user` VALUES ('3', '192.168.2.21', '李丁', '丁少');
INSERT INTO `user` VALUES ('4', '192.168.2.64', '邓俊', 'Tomas');
INSERT INTO `user` VALUES ('5', '192.168.2.34', '胡启文', '小烈');
INSERT INTO `user` VALUES ('6', '192.168.2.77', '蔡何', 'FireFox');
