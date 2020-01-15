/*
 Navicat Premium Data Transfer

 Source Server         : 本地测试
 Source Server Type    : MySQL
 Source Server Version : 50640
 Source Host           : localhost:3306
 Source Schema         : shipment

 Target Server Type    : MySQL
 Target Server Version : 50640
 File Encoding         : 65001

 Date: 13/01/2020 11:35:10
*/
DROP DATABASE IF EXISTS `shipment`;
CREATE DATABASE shipment;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
use shipment;
-- ----------------------------
-- Table structure for shipment
-- ----------------------------
DROP TABLE IF EXISTS `shipment`;
CREATE TABLE `shipment`  (
  `ID` varchar(32) NOT NULL COMMENT 'primary key',
  `TRADE_ORDER_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'Trade order id',
  `PARENT_SHIPMENT_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'Splitshipment id',
  `CHILD_SHIPMENT_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'Mergeshipment id',
  `STATUS` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'status',
  `QUANTITY` decimal(20, 6) DEFAULT NULL COMMENT 'trade quantity',
  `CREATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME` datetime(0) DEFAULT NULL,
  `MODIFY_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `MODIFY_TIME` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'shipment' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for trade_order
-- ----------------------------
DROP TABLE IF EXISTS `trade_order`;
CREATE TABLE `trade_order`  (
  `ID` varchar(32) NOT NULL COMMENT 'primary key',
  `TRADE_ORDER_NO` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'Trade order no',
  `STATUS` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'status',
  `QUANTITY` decimal(20, 6) DEFAULT NULL COMMENT 'trade quantity',
  `CREATE_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME` datetime(0) DEFAULT NULL,
  `MODIFY_USER` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `MODIFY_TIME` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'trade order' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
