-- MySQL dump 10.13  Distrib 8.0.29, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: gtStore
-- ------------------------------------------------------
-- Server version	8.0.29

--
-- Table structure for table `dict`
--
CREATE TABLE `dict` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `describe` varchar(50) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典表';

--
-- Table structure for table `product`
--
CREATE TABLE `product` (
                           `product_id` int NOT NULL AUTO_INCREMENT COMMENT '产品ID',
                           `product_name` varchar(50) NOT NULL COMMENT '产品名称',
                           `product_price` varchar(20) NOT NULL COMMENT '产品价格',
                           `product_type` varchar(10) NOT NULL COMMENT '产品类型',
                           `product_number` int NOT NULL DEFAULT '0' COMMENT '库存',
                           `product_img` text COMMENT '产品图例',
                           PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品表';

--
-- Table structure for table `user`
--
CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `name` varchar(15) NOT NULL COMMENT '用户名',
                        `password` varchar(20) NOT NULL COMMENT '密码',
                        `phone` varchar(11) DEFAULT NULL COMMENT '联系方式',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dump completed on 2026-06-17 14:06:13
