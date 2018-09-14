/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-09-14 15:04:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for assessment
-- ----------------------------
DROP TABLE IF EXISTS `assessment`;
CREATE TABLE `assessment` (
  `id` varchar(50) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `sort_num` int(11) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user_id` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `record_state` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考核项目表';

-- ----------------------------
-- Records of assessment
-- ----------------------------
INSERT INTO `assessment` VALUES ('001', '1', '20项指标提前完成全年任务', '1', null, null, null, null, 'Y');
INSERT INTO `assessment` VALUES ('002', '2', '42项指标已经完成序时进度', '2', null, null, null, null, 'Y');
INSERT INTO `assessment` VALUES ('003', '3', '5项指标落后进度要求', '3', null, null, null, null, 'Y');

-- ----------------------------
-- Table structure for assessment_detail
-- ----------------------------
DROP TABLE IF EXISTS `assessment_detail`;
CREATE TABLE `assessment_detail` (
  `id` varchar(50) NOT NULL COMMENT '考核主键',
  `title` varchar(255) DEFAULT NULL COMMENT '考核标题',
  `type` varchar(255) DEFAULT NULL COMMENT '考核分类',
  `num` int(11) DEFAULT NULL COMMENT '考核序号',
  `complete` double(2,0) DEFAULT NULL COMMENT '完成',
  `targetValue` double(2,0) DEFAULT NULL COMMENT '指标值',
  `target` varchar(255) DEFAULT NULL COMMENT '指标',
  `company` varchar(255) DEFAULT NULL COMMENT '单位',
  `checkDept` varchar(255) DEFAULT NULL COMMENT '考核部门',
  `weight` int(11) DEFAULT NULL COMMENT '权重',
  `chargeDept` varchar(255) DEFAULT NULL COMMENT '负责部门',
  `content` varchar(1000) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user_id` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `record_state` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考核详情表';

-- ----------------------------
-- Records of assessment_detail
-- ----------------------------

-- ----------------------------
-- Table structure for pub_file
-- ----------------------------
DROP TABLE IF EXISTS `pub_file`;
CREATE TABLE `pub_file` (
  `file_id` varchar(50) NOT NULL,
  `file_name` varchar(50) DEFAULT NULL,
  `file_remark` varchar(50) DEFAULT NULL,
  `file_typeId` varchar(50) DEFAULT NULL,
  `file_suffix` varchar(50) DEFAULT NULL,
  `file_size` double DEFAULT NULL,
  `file_path` varchar(100) DEFAULT NULL,
  `record_state` varchar(1) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user_id` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pub_file
-- ----------------------------
INSERT INTO `pub_file` VALUES ('001', '趣味打击乐--垃圾桶齐奏.mp4', null, null, 'mp4', null, 'D:\\download\\lajitongqizou.mp4', 'Y', null, null, null, null);
INSERT INTO `pub_file` VALUES ('002', 'test.jpg', null, null, null, null, 'D:\\download\\test.jpg', 'Y', null, null, null, null);

-- ----------------------------
-- Table structure for sys_cfg
-- ----------------------------
DROP TABLE IF EXISTS `sys_cfg`;
CREATE TABLE `sys_cfg` (
  `cfg_code` varchar(200) NOT NULL COMMENT '配置项',
  `cfg_value` varchar(1000) DEFAULT NULL COMMENT '配置值',
  `cfg_desc` varchar(200) DEFAULT NULL COMMENT '配置描述',
  `ws_id` varchar(50) DEFAULT NULL COMMENT '工作命名空间ID',
  `ws_name` varchar(50) DEFAULT NULL COMMENT '工作命名空间Name',
  `record_state` varchar(1) DEFAULT NULL COMMENT '记录状态',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`cfg_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置';

-- ----------------------------
-- Records of sys_cfg
-- ----------------------------
INSERT INTO `sys_cfg` VALUES ('accept_excel_mime', 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', '允许上传Excel的MIME类型', 'sys', null, 'Y', '00000000000000000000000000000000', '2016-08-03 11:33:57', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('accept_excel_suffix', '.xls,.xlsx', '允许上传Excel的后缀名', 'sys', null, 'Y', '00000000000000000000000000000000', '2016-08-03 11:33:57', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('accept_file_size_limit', '20', '允许上传文件大小限制(M)', 'sys', null, 'Y', '00000000000000000000000000000000', '2016-08-03 11:33:57', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('accept_file_suffix', '.jpg,.jpeg,.bmp,.png,.txt,.doc,.docx,.xls,.xlsx,.ppt,.zip,.rar', '允许上传文件后缀名', 'sys', null, 'Y', '00000000000000000000000000000000', '2016-08-03 11:33:57', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('accept_image_mime', 'image/bmp,image/gif,image/jpeg,image/png', '允许上传图片的MIME类型', 'sys', null, 'Y', '00000000000000000000000000000000', '2016-08-03 11:33:57', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('accept_image_suffix', '.jpg,.jpeg,.bmp,.png', '允许上传图片的后缀名', 'sys', null, 'Y', '00000000000000000000000000000000', '2016-08-03 11:33:57', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('roleId_area', 'role0002', '区域管理员', 'sys', null, 'Y', '00000000000000000000000000000000', '2016-08-08 19:50:52', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('roleId_city', 'role0003', '城市管理员', 'sys', null, 'Y', '00000000000000000000000000000000', '2016-08-09 19:43:02', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('roleId_normal', 'role0004', '普通用户', 'sys', null, 'Y', '00000000000000000000000000000000', '2016-08-09 19:43:02', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('roleId_root', 'role0001', '超级管理员', 'sys', null, 'Y', '00000000000000000000000000000000', '2017-05-26 10:41:05', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('upload_base_dir', '/home/springbootdemo/mesUploadFile', '上传文件保存物理路径', 'sys', null, 'Y', '00000000000000000000000000000000', '2016-08-03 11:33:57', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('upload_base_url', 'http://www.zhihuiyiguanjia.com/xhImg', '上传访问url根地址', 'sys', null, 'Y', '00000000000000000000000000000000', '2016-08-05 14:36:43', '00000000000000000000000000000000', '2018-06-20 14:01:10');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `dict_id` varchar(50) NOT NULL COMMENT '字典ID',
  `dict_type_id` varchar(50) DEFAULT NULL COMMENT '字典类型ID',
  `dict_type_name` varchar(50) DEFAULT NULL COMMENT '字典类型名称',
  `dict_key` varchar(50) DEFAULT NULL COMMENT '字典键',
  `dict_value` varchar(50) DEFAULT NULL COMMENT '字典值',
  `dict_desc` varchar(200) DEFAULT NULL COMMENT '字典描述',
  `sort_key` int(11) DEFAULT NULL COMMENT '排序码',
  `record_state` varchar(1) DEFAULT NULL COMMENT '记录状态',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('dict001', 'userGender', '用户性别', '1', '男', '', '1', 'Y', '00000000000000000000000000000000', '2017-03-16 09:53:59', '', '2018-08-15 15:59:19');
INSERT INTO `sys_dict` VALUES ('dict002', 'userGender', '用户性别', '2', '女', '', '2', 'Y', '00000000000000000000000000000000', '2016-08-06 10:41:04', '', '2018-08-15 15:59:22');
INSERT INTO `sys_dict` VALUES ('dict003', 'userRole', '用户角色', '1', '超级管理员', '', '1', 'Y', '00000000000000000000000000000000', '2016-08-06 10:39:54', '', '2018-08-15 15:59:24');
INSERT INTO `sys_dict` VALUES ('dict004', 'userRole', '用户角色', '2', '区域管理员', '', '2', 'Y', '00000000000000000000000000000000', '2017-03-16 09:53:39', '', '2018-08-15 15:59:27');
INSERT INTO `sys_dict` VALUES ('dict005', 'userRole', '用户角色', '3', '城市管理员', null, '3', 'Y', '00000000000000000000000000000000', '2018-08-15 15:59:31', null, null);
INSERT INTO `sys_dict` VALUES ('dict006', 'userRole', '用户角色', '4', '普通用户', null, '4', 'Y', '00000000000000000000000000000000', '2018-08-15 15:59:34', null, null);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `log_id` varchar(50) NOT NULL COMMENT '日志ID',
  `local_host` varchar(50) DEFAULT NULL COMMENT '本地主机',
  `proxy_client_ip` varchar(50) DEFAULT NULL COMMENT '客户端IP',
  `log_type_id` varchar(50) DEFAULT NULL COMMENT '日志类型ID',
  `log_detail` varchar(1000) DEFAULT NULL COMMENT '日志详情',
  `record_state` varchar(1) DEFAULT NULL COMMENT '记录状态',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` varchar(50) NOT NULL,
  `menu_name` varchar(50) DEFAULT NULL,
  `menu_parent_id` varchar(50) DEFAULT NULL,
  `menu_sort` int(11) DEFAULT NULL,
  `menu_icon` varchar(50) DEFAULT NULL,
  `url` varchar(50) DEFAULT NULL,
  `record_state` varchar(1) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user_id` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('menu0001', '系统管理', '0', '0', null, null, 'Y', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('menu0002', '角色管理', 'menu0001', '0', null, null, 'Y', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('menu0003', '资源管理', 'menu0001', '1', null, null, 'Y', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('menu0004', '组织管理', '0', '1', null, null, 'Y', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('menu0005', '用户管理', 'menu0004', '0', null, null, 'Y', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('menu0006', '组织机构管理', 'menu0004', '1', null, null, 'Y', null, null, null, null);

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `org_id` varchar(50) CHARACTER SET utf8 NOT NULL,
  `expanded` bit(1) DEFAULT NULL,
  `isleaf` bit(1) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `sort_key` int(11) DEFAULT NULL,
  `org_name` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `org_parent_id` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `record_state` varchar(1) CHARACTER SET utf8 DEFAULT NULL,
  `create_user_id` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `update_user_id` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  PRIMARY KEY (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO `sys_org` VALUES ('org0001', '', '\0', '1', '1', '江苏省移动公司', '-1', 'Y', null, '2018-07-28', null, '2018-07-28');
INSERT INTO `sys_org` VALUES ('org0002', '', '', '2', '1', '南京市移动公司', '0001', 'Y', null, '2018-07-28', null, '2018-07-28');
INSERT INTO `sys_org` VALUES ('org0003', '', '', '3', '1', '玄武区移动公司', '0002', 'Y', null, '2018-07-28', null, '2018-07-28');
INSERT INTO `sys_org` VALUES ('org0004', '', '', '3', '2', '鼓楼区移动公司', '0002', 'Y', null, '2018-07-28', null, '2018-07-28');
INSERT INTO `sys_org` VALUES ('org0005', '', '', '3', '3', '秦淮区移动公司', '0002', 'Y', null, '2018-07-28', null, '2018-07-28');
INSERT INTO `sys_org` VALUES ('org0006', '', '', '2', '2', '盐城市移动公司', '0001', 'Y', null, '2018-07-28', null, '2018-07-28');
INSERT INTO `sys_org` VALUES ('org0007', '', '', '3', '1', '建湖县移动公司', '0006', 'Y', null, '2018-07-28', null, '2018-07-28');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` varchar(50) NOT NULL,
  `role_name` varchar(50) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user_id` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `record_state` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('role0001', '超级管理员', null, null, '00000000000000000000000000000000', '2018-08-05 20:36:42', 'Y');
INSERT INTO `sys_role` VALUES ('role0002', '区域管理员', null, null, '00000000000000000000000000000000', '2018-08-05 14:30:35', 'Y');
INSERT INTO `sys_role` VALUES ('role0003', '城市管理员', null, null, '00000000000000000000000000000000', '2018-08-05 14:37:01', 'Y');
INSERT INTO `sys_role` VALUES ('role0004', '普通用户', null, null, '00000000000000000000000000000000', '2018-08-05 14:40:13', 'Y');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_menu_id` varchar(50) NOT NULL,
  `role_id` varchar(50) DEFAULT NULL,
  `menu_id` varchar(50) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user_id` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `record_state` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`role_menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('0001', 'role0001', 'menu0001', null, null, null, null, 'Y');
INSERT INTO `sys_role_menu` VALUES ('0002', 'role0001', 'menu0002', null, null, null, null, 'Y');
INSERT INTO `sys_role_menu` VALUES ('0003', 'role0001', 'menu0003', null, null, null, null, 'Y');
INSERT INTO `sys_role_menu` VALUES ('0004', 'role0001', 'menu0004', null, null, null, null, 'Y');
INSERT INTO `sys_role_menu` VALUES ('0005', 'role0001', 'menu0005', null, null, null, null, 'Y');
INSERT INTO `sys_role_menu` VALUES ('0006', 'role0002', 'menu0004', null, null, null, null, 'Y');
INSERT INTO `sys_role_menu` VALUES ('0007', 'role0002', 'menu0005', null, null, null, null, 'Y');
INSERT INTO `sys_role_menu` VALUES ('0008', 'role0002', 'menu0006', null, null, null, null, 'Y');
INSERT INTO `sys_role_menu` VALUES ('021c2484f1304e76af66cf5325b90939', 'role0003', 'menu0003', '00000000000000000000000000000000', '2018-08-08 12:43:35', '00000000000000000000000000000000', '2018-08-08 12:51:19', 'N');
INSERT INTO `sys_role_menu` VALUES ('77448afd81464b86b4dd52d9a0c47c61', 'role0003', 'menu0001', '00000000000000000000000000000000', '2018-08-08 12:42:40', null, null, 'N');
INSERT INTO `sys_role_menu` VALUES ('843a0748a8a049f280337a5feb2622cd', 'role0003', 'menu0002', '00000000000000000000000000000000', '2018-08-08 12:42:43', null, null, 'N');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` varchar(50) NOT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `user_acc` varchar(50) DEFAULT NULL,
  `user_pwd` varchar(50) DEFAULT NULL,
  `user_phone` varchar(50) DEFAULT NULL,
  `user_gender` int(1) DEFAULT NULL,
  `headortraitpath` varchar(50) DEFAULT NULL,
  `user_addr` varchar(200) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '������',
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `update_user_id` varchar(100) DEFAULT NULL COMMENT '������',
  `update_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `record_state` varchar(1) DEFAULT NULL COMMENT 'YΪδɾ��NΪɾ��',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('00000000000000000000000000000000', '超级管理员', 'root', '72c8c473996f5f9cb2d2a71c79bf3ac8', '18251922826', '1', null, '南京市', null, '2018-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:33', 'Y');
INSERT INTO `sys_user` VALUES ('00b0daa6a59347a0a64a89c4bc57239b', '李昊', '', 'f23524ee1e9d6289f2f91204400c3360', '18251922802', '1', null, '苏州市', null, '2018-08-05 19:27:33', null, null, 'Y');
INSERT INTO `sys_user` VALUES ('00dd346f08284a5f927d90d046e2f220', '贾宏声', '', 'e8983ad75ee98dc90b72ec24ea35ad84', '18251922806', '0', null, '盐城市', null, '2018-08-05 19:27:36', null, null, 'Y');
INSERT INTO `sys_user` VALUES ('127669b85d214e0ea7a1ffec9e2ab2d6', '李昊', '', '900163aee11277cc4971a46fc922521c', '18251922802', '1', null, '苏州市', '00000000000000000000000000000000', '2018-07-27 23:00:12', null, null, 'Y');
INSERT INTO `sys_user` VALUES ('195A8273-4E6E-FECB-779C-FCC10A9F3AF6', '蓝雨 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922811', '0', null, '上海市', null, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('1D8878D8-63B3-3D00-34A7-4B964BE67D50', '刘恩佑 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922812', '0', null, '北京市', null, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('25e443bb96834e0f8302410b3118140f', '戴军梅', '', '75ced789e992bca40a545c732046e815', '18251922804', '1', null, '常州市', '00000000000000000000000000000000', '2018-07-27 23:00:12', null, null, 'Y');
INSERT INTO `sys_user` VALUES ('2661ee3ec38a4eb89ebecef34a944dd7', '贾宏声', '', '57e082fed0948fb235b6ddd09f24a1c6', '18251922806', '0', null, '盐城市', '00000000000000000000000000000000', '2018-07-27 23:00:12', null, null, 'Y');
INSERT INTO `sys_user` VALUES ('267FA6D5-260E-AEB8-DE4E-EE89FAB35499', '李光洁 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922814', '0', null, '长沙市', null, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('2fe67e86c813485caa97e9168e17f0cc', '戴军梅', '', 'f7b590dce086c7d5c897c88f37fe5b3b', '18251922804', '1', null, '常州市', null, '2018-08-05 19:27:58', null, null, 'Y');
INSERT INTO `sys_user` VALUES ('30CA760D-9CE1-5FB4-9B8D-75102FF0B4A6', '李连杰 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922808', '0', null, '镇江市', null, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('3dbd56094a284324b34c98f7def1649a', '区域管理员', 'admin', 'c2fe4afd8d91b7babc9859d5bdfb791c', '18251922826', '1', null, '南京市', '00000000000000000000000000000000', '2018-07-27 23:00:12', null, null, 'Y');
INSERT INTO `sys_user` VALUES ('45E108BD-7D4A-E4A9-B182-CF16E9566FC0', '于小伟 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922824', '0', null, '天水市', null, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('5817d80a201a4d478fa4c140795ec117', '蓝雨', '', '073b5fef60a5486cd07d6d10569c2722', '18251922811', '0', null, '上海市', null, '2018-08-05 19:28:04', null, null, 'Y');
INSERT INTO `sys_user` VALUES ('58DAEF53-14ED-F71B-E95F-61951BC85A49', '沙溢 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922821', '0', null, '宁波市', null, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('5c3216996e134a98ab7767afd8851e4e', '薛之谦', '', 'b9e714cd79718ac650289cc7127b6b8f', '18251922805', '1', null, '连云港', null, '2018-08-05 19:28:07', null, null, 'Y');
INSERT INTO `sys_user` VALUES ('5CA44192-6F31-8E73-00BC-C860DC6D346F', '贾宏声 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922806', '0', null, '盐城市', null, '2017-12-12 21:39:33', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('5DD52ED1-517F-E5EB-4417-2E9F3C3F883F', '张殿菲 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922817', '0', null, '重庆市', null, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('5EF7B795-076F-61AB-4E0E-46C288B1227E', '黄磊 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922823', '1', null, '兰州市', null, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('6B5A745F-1958-1D7D-0774-0FA775DD3CC9', '于波 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922807', '1', null, '淮安市', null, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('8285CD7B-535C-3C8A-8013-0A544F861E78', '张杰 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922819', '1', null, '马鞍山', null, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('96C89EE3-290D-6054-4F07-E888DCC72E81', '杨坤 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922820', '1', null, '南昌市', null, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('9f8cc7831918425d89b51a0a102157a5', '邢佳栋', '', '2b645ead0fcb67c7124273a3f0c3b8e2', '18251922800', '1', null, '南京市', null, '2018-08-05 19:28:12', null, null, 'Y');
INSERT INTO `sys_user` VALUES ('A10D3D8D-505C-55E6-B597-3BC88F0BD88C', '李茂 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922822', '1', null, '绍兴市', null, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('A123C83D-40B0-03DC-3F23-809E8631EE3F', '李美美', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922803', '1', null, '无锡市', null, '2017-12-12 21:39:24', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('a791af6df05941c0823802bdf8b9c3f0', '邢佳栋', '', 'ee902e2b8e509c2bf14c7d2f44c8cf11', '18251922800', '1', null, '南京市', '00000000000000000000000000000000', '2018-07-27 23:00:12', null, null, 'Y');
INSERT INTO `sys_user` VALUES ('ab56de8ba5164c3eb0cfd6fa782135ea', '刘恩佑', '', 'a7c79924114306ea1ba711c70b809c3d', '18251922812', '0', null, '北京市', null, '2018-08-05 19:28:15', null, null, 'Y');
INSERT INTO `sys_user` VALUES ('acdf33328ad6478ab71d91febfe454c3', '蓝雨', '', '12a8919192cbb88e87e94cd4d00ac0c6', '18251922811', '0', null, '上海市', '00000000000000000000000000000000', '2018-07-27 23:00:12', null, null, 'Y');
INSERT INTO `sys_user` VALUES ('ACEB9888-B017-1337-8CDF-14354FD64D60', '戴军梅 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922804', '1', null, '常州市', null, '2017-12-12 21:39:27', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('b1a3c5bb0a344dba923a31d0169bdaaf', '刘恩佑', '', '4023deba5da26eaf6252673109c68c9b', '18251922812', '0', null, '北京市', '00000000000000000000000000000000', '2018-07-27 23:00:12', null, null, 'Y');
INSERT INTO `sys_user` VALUES ('B4DD5063-9328-5702-ADB9-E4AB59B88B12', '薛之谦 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922805', '1', null, '连云港', null, '2017-12-12 21:39:30', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('B5774782-95A6-8C37-CC73-1E2C2340B7B3', '姜文 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922815', '1', null, '武汉市', null, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('DA53B5AD-56D4-99B2-640A-987E6D76243F', '邓超 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922818', '1', null, '芜湖市', null, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('E350C542-B371-0DF7-8800-C1F648CD986F', '黑龙 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922816', '1', null, '天津市', null, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('eb46d88bf4dd456687670bfadfcd5a20', '李雪梅', '', '0935f4e5a66e9d7d8e9790c7d609bf58', '18251922801', '1', null, '南通市', null, '2018-08-05 19:28:21', null, null, 'Y');
INSERT INTO `sys_user` VALUES ('ECA8EBAC-8515-18F6-D062-7B5019C1B170', '李昊', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922802', '1', null, '苏州市', null, '2017-12-12 21:39:21', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('F03B8105-C4B5-DFDF-F4C0-79A129C0148C', '王斑 ', null, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922809', '1', null, '徐州市', null, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('f4b3e2d2be3948b39f41360c509f64c2', '薛之谦', '', 'e723846ed2c7eb55b081ad0ed72470dd', '18251922805', '1', null, '连云港', '00000000000000000000000000000000', '2018-07-27 23:00:12', null, null, 'Y');

-- ----------------------------
-- Table structure for sys_user_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_org`;
CREATE TABLE `sys_user_org` (
  `user_org_id` varchar(50) CHARACTER SET utf8 NOT NULL,
  `user_id` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `org_id` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `record_state` varchar(1) CHARACTER SET utf8 DEFAULT NULL,
  `create_user_id` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `update_user_id` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  PRIMARY KEY (`user_org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_user_org
-- ----------------------------
INSERT INTO `sys_user_org` VALUES ('e53461f4389a484fa4f8cf0df1e35bea', 'e8b951213a57432999959ac655bf7c93', 'org0002', 'Y', '00000000000000000000000000000000', '2018-08-07', '00000000000000000000000000000000', '2018-08-07');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_role_id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `role_id` varchar(50) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user_id` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `record_state` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('380782fbdd0143c9b8e42c8ec3e93a7c', '00000000000000000000000000000000', 'role0002', '00000000000000000000000000000000', '2018-08-08 09:03:35', '00000000000000000000000000000000', '2018-08-08 09:05:35', 'Y');
INSERT INTO `sys_user_role` VALUES ('3d6f36fd67374483993fcae6d1f418da', '00000000000000000000000000000000', 'role0001', '00000000000000000000000000000000', '2018-08-08 09:04:37', '00000000000000000000000000000000', '2018-08-08 09:05:46', 'Y');
INSERT INTO `sys_user_role` VALUES ('b6c1eccada9d4732863878e2941859c4', '00000000000000000000000000000000', 'role0003', '00000000000000000000000000000000', '2018-08-08 09:05:15', '00000000000000000000000000000000', '2018-08-08 09:05:35', 'Y');
