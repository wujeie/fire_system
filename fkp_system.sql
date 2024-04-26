/*
Navicat MySQL Data Transfer

Source Server         : localhost-mysql_8.0
Source Server Version : 80031
Source Host           : localhost:3306
Source Database       : fkp_system

Target Server Type    : MYSQL
Target Server Version : 80031
File Encoding         : 65001

Date: 2024-04-26 18:16:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `feedback_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `content` text NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `admin_reply` text,
  `replied_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES ('1', '2', '我非常喜欢这个平台', '2024-04-02 17:08:48', 'ok', '2024-04-25 17:03:13');
INSERT INTO `feedback` VALUES ('2', '2', '我非常喜欢这个平台', '2024-04-02 17:09:04', 'ok', '2024-04-09 17:09:15');
INSERT INTO `feedback` VALUES ('3', '2', '我非常喜欢这个平台 水字数。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。', '2024-04-02 19:08:03', 'thanks', '2024-04-09 19:08:10');
INSERT INTO `feedback` VALUES ('4', '3', '作者大大真厉害', '2024-04-17 17:04:48', '谢谢你，我会继续努力，希望你继续支持我', '2024-04-25 09:07:39');
INSERT INTO `feedback` VALUES ('5', '2', '作者大大真厉害', '2024-04-26 17:12:40', null, null);
INSERT INTO `feedback` VALUES ('10', '2', '哈哈哈', '2024-04-26 09:23:01', null, null);

-- ----------------------------
-- Table structure for firearticles
-- ----------------------------
DROP TABLE IF EXISTS `firearticles`;
CREATE TABLE `firearticles` (
  `article_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of firearticles
-- ----------------------------
INSERT INTO `firearticles` VALUES ('1', '灭火器的种类及使用方法', '　1．泡沫灭火器：适用AB类火灾，分为化学泡沫和机械泡沫两种，其中化学泡沫使用时颠倒使用，现已淘汰，而机械泡沫使用方法同于粉灭火剂。缺点：造成污染，不可使用于C类火灾，每四个月检查一次，药剂一年更换。\r\n\r\n　2.二氧化碳灭火器适BC类火灾，使用方法：a)拔出保险插销：b)握住喇叭喷嘴和阀门压把；c)压下压把即受内部高压喷出。每三个月检查，重量减二》需重新灌充。缺点：使用人员极易冻伤。\r\n\r\n　3．干粉灭火器：分为ABC和BC干粉两种，其中适用ABC类火灾，使用方法：a)拔掉保险销；b)喷嘴管朝向火焰，压下阀门压把即可喷出。三个月检查压力表(1．2Mpa)药剂有效时限三年。\r\n\r\n　4．清水灭火器：它最适合灭A类火灾，不适合扑灭其它类火灾。采用拍击法：先将清水灭火器直立放稳，摘下保护帽，用手掌拍击开启杠顶端的凸头，水流便会从喷嘴喷出。', '2024-04-04 15:19:04', '2024-04-18 00:00:00');
INSERT INTO `firearticles` VALUES ('2', '消防安全常识二十条', '\n    一、自觉维护公共消防安全，发现火灾迅速拨打１１９电话报警，消防队救火不收费。\n\n    二、发现火灾隐患和消防安全违法行为可拨打９６１１９电话，向当地公安消防部门举报。\n\n    三、不埋压、圈占、损坏、挪用、遮挡消防设施和器材。\n\n    四、不携带易燃易爆危险品进入公共场所、乘坐公共交通工具。\n\n    五、不在严禁烟火的场所和人员密集场所动用明火和吸烟。\n\n    六、购买合格的烟花爆竹，燃放时遵守安全燃放规定，注意消防安全。\n\n    七、家庭和单位配备必要的消防器材并掌握正确的使用方法。\n\n    八、每个家庭都应制定消防安全计划，绘制逃生疏散路线图，及时检查、消除火灾隐患。\n\n    九、室内装修装饰不宜采用易燃材料。\n\n    十、正确使用电器设备，不乱接电源线，不超负荷用电，及时更换老化电器设备和线路，外出时要关闭电源开关。\n\n    十一、正确使用、经常检查燃气设施和用具，发现燃气泄漏，迅速关阀门、开门窗，切勿触动电器开关和使用明火。\n\n    十二、教育儿童不玩火，将打火机和火柴放在儿童拿不到的地方。\n\n    十三、不占用、堵塞或封闭安全出口、疏散通道和消防车通道，不设置妨碍消防车通行和火灾扑救的障碍物。\n\n    十四、不躺在床上或沙发上吸烟，不乱扔烟头。\n\n    十五、学校和单位定期组织逃生疏散演练。\n\n    十六、进入公共场所注意观察安全出口和疏散通道，记住疏散方向。\n\n    十七、遇到火灾时沉着、冷静，迅速正确逃生，不贪恋财物、不乘坐电梯、不盲目跳楼。\n\n    十八、必须穿过浓烟逃生时，尽量用浸湿的衣物保护头部和身体，捂住口鼻，弯腰低姿前行。\n\n    十九、身上着火，可就地打滚或用厚重衣物覆盖，压灭火苗。\n\n    二十、大火封门无法逃生时，可用浸湿的毛巾衣物堵塞门缝，发出求救信号等待救援。', '2024-04-06 15:19:50', '2024-04-24 00:00:00');

-- ----------------------------
-- Table structure for firevideos
-- ----------------------------
DROP TABLE IF EXISTS `firevideos`;
CREATE TABLE `firevideos` (
  `video_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `video_cover` varchar(255) DEFAULT NULL,
  `description` text,
  `file_path` varchar(512) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`video_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of firevideos
-- ----------------------------
INSERT INTO `firevideos` VALUES ('1', '火灾真实逃亡时间', '/videoCover/封面一.jpg', null, '/video/火灾真实逃亡时间.mp4', '2024-04-04 21:54:03');
INSERT INTO `firevideos` VALUES ('2', '消防栓的使用方法', '/videoCover/封面二.jpg', null, '/video/消防栓的使用方法.mp4', '2024-04-05 21:55:10');
INSERT INTO `firevideos` VALUES ('6', '灭火器的使用方法', '/videoCover/灭火器的使用方法.jpg', null, '/video/灭火器的使用方法.mp4', '2024-04-19 11:07:47');

-- ----------------------------
-- Table structure for menus
-- ----------------------------
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus` (
  `menu_id` int NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(255) NOT NULL,
  `parent_id` int DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`menu_id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of menus
-- ----------------------------
INSERT INTO `menus` VALUES ('1', '', null, null, null);

-- ----------------------------
-- Table structure for questions
-- ----------------------------
DROP TABLE IF EXISTS `questions`;
CREATE TABLE `questions` (
  `question_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `option_A` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `option_B` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `option_C` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `option_D` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `correct_answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of questions
-- ----------------------------
INSERT INTO `questions` VALUES ('1', '1+1=?', '1', '2', '3', '4', 'B');
INSERT INTO `questions` VALUES ('2', '2+1=?', '1', '2', '3', '4', 'C');
INSERT INTO `questions` VALUES ('11', '123', '123', '123', '123', '123', 'A');

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('1', 'admin', '管理员');
INSERT INTO `roles` VALUES ('2', 'user', '普通用户');

-- ----------------------------
-- Table structure for testpaperquestions
-- ----------------------------
DROP TABLE IF EXISTS `testpaperquestions`;
CREATE TABLE `testpaperquestions` (
  `testpaperquestions_id` int NOT NULL AUTO_INCREMENT,
  `testpaper_id` int DEFAULT NULL,
  `question_id` int DEFAULT NULL,
  `question_order` int DEFAULT NULL,
  PRIMARY KEY (`testpaperquestions_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of testpaperquestions
-- ----------------------------
INSERT INTO `testpaperquestions` VALUES ('2', '2', '1', '1');
INSERT INTO `testpaperquestions` VALUES ('3', '1', '2', '2');
INSERT INTO `testpaperquestions` VALUES ('5', '2', '2', '2');
INSERT INTO `testpaperquestions` VALUES ('6', '1', '1', '3');
INSERT INTO `testpaperquestions` VALUES ('11', '12', '11', '1');

-- ----------------------------
-- Table structure for testpapers
-- ----------------------------
DROP TABLE IF EXISTS `testpapers`;
CREATE TABLE `testpapers` (
  `testpaper_Id` int NOT NULL AUTO_INCREMENT,
  `testpaper_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `createtime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `totalscore` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`testpaper_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of testpapers
-- ----------------------------
INSERT INTO `testpapers` VALUES ('1', 'papertest1', '2024-04-16 16:47:22', '100');
INSERT INTO `testpapers` VALUES ('2', 'papertest2', '2024-04-24 15:45:45', '150');
INSERT INTO `testpapers` VALUES ('12', 'testpaper3', '2024-04-25 03:06:53', '100');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `role_id` int NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'admin', '1', '$2a$10$meixulgCtIJ/KDhziIahUu5KtBxhhi53e5r0QIsglcdtNfrMbZ3EK', 'hysjfmm@uu.com', '2024-02-07 15:25:59', '2024-04-16 14:18:03', '/avatar/a11.jpeg');
INSERT INTO `users` VALUES ('2', 'user01', '2', '$2a$10$meixulgCtIJ/KDhziIahUu5KtBxhhi53e5r0QIsglcdtNfrMbZ3EK', '12345@123.com', '2024-02-08 15:26:34', '2024-04-26 17:10:47', '/avatar/a12.jpeg');
INSERT INTO `users` VALUES ('3', 'user02', '1', '$2a$10$meixulgCtIJ/KDhziIahUu5KtBxhhi53e5r0QIsglcdtNfrMbZ3EK', null, '2024-04-03 11:53:20', '2024-04-25 11:56:09', '/avatar/a12.jpeg');

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `user_role_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_roles
-- ----------------------------

-- ----------------------------
-- Table structure for user_tests
-- ----------------------------
DROP TABLE IF EXISTS `user_tests`;
CREATE TABLE `user_tests` (
  `test_record_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `test_id` int NOT NULL,
  `score` int NOT NULL,
  `completed_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`test_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_tests
-- ----------------------------
