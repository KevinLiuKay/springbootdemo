/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : springboot

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 05/08/2018 20:45:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
  `file_path` varchar(50) DEFAULT NULL,
  `record_state` varchar(1) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user_id` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
BEGIN;
INSERT INTO `sys_cfg` VALUES ('1', 'Y', '自动签到、调课开关（停课）', NULL, NULL, 'Y', '55', '2016-10-15 16:57:50', '55', '2017-02-16 11:47:26');
INSERT INTO `sys_cfg` VALUES ('787dad56c77340d9a4e957ac28e5c199', 'Y', '自动签到、调课开关（停课）', NULL, NULL, 'Y', 'ce97352a21c54af7a8b12b0f9271c22a', '2017-01-25 11:07:50', NULL, NULL);
INSERT INTO `sys_cfg` VALUES ('accept_excel_mime', 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', '允许上传Excel的MIME类型', 'sys', NULL, 'Y', '00000000000000000000000000000000', '2016-08-03 11:33:57', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('accept_excel_suffix', '.xls,.xlsx', '允许上传Excel的后缀名', 'sys', NULL, 'Y', '00000000000000000000000000000000', '2016-08-03 11:33:57', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('accept_file_size_limit', '6', '允许上传文件大小限制(M)', 'sys', NULL, 'Y', '00000000000000000000000000000000', '2016-08-03 11:33:57', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('accept_file_suffix', '.jpg,.jpeg,.bmp,.png,.txt,.doc,.docx,.xls,.xlsx,.ppt,.zip,.rar', '允许上传文件后缀名', 'sys', NULL, 'Y', '00000000000000000000000000000000', '2016-08-03 11:33:57', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('accept_image_mime', 'image/bmp,image/gif,image/jpeg,image/png', '允许上传图片的MIME类型', 'sys', NULL, 'Y', '00000000000000000000000000000000', '2016-08-03 11:33:57', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('accept_image_suffix', '.jpg,.jpeg,.bmp,.png', '允许上传图片的后缀名', 'sys', NULL, 'Y', '00000000000000000000000000000000', '2016-08-03 11:33:57', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('auto_sign_evaluate', 'N', '自动签到、评价', 'sys', NULL, 'Y', '00000000000000000000000000000000', '2017-05-26 10:41:05', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('baidu_ak', 'ctLt2gSgRdhtOSYWkkjOIAlGTlFLRhie', '访问应用（AK）', 'sys', NULL, 'Y', '00000000000000000000000000000000', '2016-08-08 19:50:52', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('sysRoleId_orgManager', 'e86488f9de484af9837def100eafc324', '机构管理员角色Id', 'mng', NULL, 'Y', '00000000000000000000000000000000', '2016-08-09 19:43:02', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('sysRoleId_student', 'a96216de2a4248a7878fac19544eb2e1', '学生角色Id', 'mng', NULL, 'Y', '00000000000000000000000000000000', '2016-08-09 19:43:02', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('sysRoleId_sysManager', '9a749a8fa6b14a15be48dc7dfb776048', '系统管理员角色Id', 'mng', NULL, 'Y', '00000000000000000000000000000000', '2016-08-09 19:43:02', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('sysRoleId_teacher', '28163fa290ef4ea1ae0642b63d5fcaf3', '老师角色Id', 'mng', NULL, 'Y', '00000000000000000000000000000000', '2016-08-09 19:43:02', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('sys_title_name', '智慧艺管家', '系统标题名称', 'sys', NULL, 'Y', '00000000000000000000000000000000', '2016-08-03 11:33:57', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('upload_base_dir', '/data/ygj/xhImg', '上传文件保存物理路径', 'sys', NULL, 'Y', '00000000000000000000000000000000', '2016-08-03 11:33:57', '00000000000000000000000000000000', '2018-06-20 14:01:10');
INSERT INTO `sys_cfg` VALUES ('upload_base_url', 'http://www.zhihuiyiguanjia.com/xhImg', '上传访问url根地址', 'sys', NULL, 'Y', '00000000000000000000000000000000', '2016-08-05 14:36:43', '00000000000000000000000000000000', '2018-06-20 14:01:10');
COMMIT;

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
BEGIN;
INSERT INTO `sys_dict` VALUES ('569e4415ac4f4244a6d7b301fec3ff22', 'userSchool', '毕业院校', '2', '东南大学', '东南大学', 1, 'Y', '00000000000000000000000000000000', '2017-03-16 09:53:59', '00000000000000000000000000000000', '2018-06-29 17:04:30');
INSERT INTO `sys_dict` VALUES ('6f2073e2e5b24c4682db4572a21b8e0e', 'courseCategory', '课程类别', '2', '键盘乐器', '键盘乐器', 2, 'Y', '00000000000000000000000000000000', '2016-08-06 10:41:04', '00000000000000000000000000000000', '2018-05-15 15:20:59');
INSERT INTO `sys_dict` VALUES ('74e76217ecb643529d25e77346a5338e', 'courseCategory', '课程类别', '1', '管弦乐器', '管弦乐器', 1, 'Y', '00000000000000000000000000000000', '2016-08-06 10:39:54', '00000000000000000000000000000000', '2018-05-15 15:20:59');
INSERT INTO `sys_dict` VALUES ('b5223ecf32c34ce4be5bfcc09372c056', 'userSchool', '毕业院校', '1', '南京大学', '南京大学', 2, 'N', '00000000000000000000000000000000', '2017-03-16 09:53:39', '00000000000000000000000000000000', '2018-06-29 17:04:30');
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `log_id` varchar(50) NOT NULL COMMENT '日志ID',
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
BEGIN;
INSERT INTO `sys_menu` VALUES ('0001', '系统管理', '0', 0, NULL, NULL, 'Y', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES ('0002', '用户管理', '0001', 0, NULL, NULL, 'Y', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES ('0003', '角色管理', '0001', 1, NULL, NULL, 'Y', NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES ('0004', '组织管理', '0001', 2, NULL, NULL, 'Y', NULL, NULL, NULL, NULL);
COMMIT;

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
  `parent_id` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
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
BEGIN;
INSERT INTO `sys_org` VALUES ('0001', b'1', b'0', 1, 1, '江苏省移动公司', '-1', 'Y', NULL, '2018-07-28', NULL, '2018-07-28');
INSERT INTO `sys_org` VALUES ('0002', b'1', b'1', 2, 1, '南京市移动公司', '0001', 'Y', NULL, '2018-07-28', NULL, '2018-07-28');
INSERT INTO `sys_org` VALUES ('0003', b'1', b'1', 3, 1, '玄武区移动公司', '0002', 'Y', NULL, '2018-07-28', NULL, '2018-07-28');
INSERT INTO `sys_org` VALUES ('0004', b'1', b'1', 3, 2, '鼓楼区移动公司', '0002', 'Y', NULL, '2018-07-28', NULL, '2018-07-28');
INSERT INTO `sys_org` VALUES ('0005', b'1', b'1', 3, 3, '秦淮区移动公司', '0002', 'Y', NULL, '2018-07-28', NULL, '2018-07-28');
INSERT INTO `sys_org` VALUES ('0006', b'1', b'1', 2, 2, '盐城市移动公司', '0001', 'Y', NULL, '2018-07-28', NULL, '2018-07-28');
INSERT INTO `sys_org` VALUES ('0007', b'1', b'1', 3, 1, '建湖县移动公司', '0006', 'Y', NULL, '2018-07-28', NULL, '2018-07-28');
COMMIT;

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
BEGIN;
INSERT INTO `sys_role` VALUES ('role0001', '超级管理员', NULL, NULL, '00000000000000000000000000000000', '2018-08-05 20:36:42', 'Y');
INSERT INTO `sys_role` VALUES ('role0002', '区域管理员', NULL, NULL, '00000000000000000000000000000000', '2018-08-05 14:30:35', 'Y');
INSERT INTO `sys_role` VALUES ('role0003', '城市管理员', NULL, NULL, '00000000000000000000000000000000', '2018-08-05 14:37:01', 'Y');
INSERT INTO `sys_role` VALUES ('role0004', '普通用户', NULL, NULL, '00000000000000000000000000000000', '2018-08-05 14:40:13', 'Y');
COMMIT;

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
BEGIN;
INSERT INTO `sys_role_menu` VALUES ('', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

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
BEGIN;
INSERT INTO `sys_user` VALUES ('00000000000000000000000000000000', '超级管理员', 'root', '72c8c473996f5f9cb2d2a71c79bf3ac8', '18251922826', 1, NULL, '南京市', NULL, '2018-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:33', 'Y');
INSERT INTO `sys_user` VALUES ('00b0daa6a59347a0a64a89c4bc57239b', '李昊', '', 'f23524ee1e9d6289f2f91204400c3360', '18251922802', 1, NULL, '苏州市', NULL, '2018-08-05 19:27:33', NULL, NULL, 'Y');
INSERT INTO `sys_user` VALUES ('00dd346f08284a5f927d90d046e2f220', '贾宏声', '', 'e8983ad75ee98dc90b72ec24ea35ad84', '18251922806', 0, NULL, '盐城市', NULL, '2018-08-05 19:27:36', NULL, NULL, 'Y');
INSERT INTO `sys_user` VALUES ('127669b85d214e0ea7a1ffec9e2ab2d6', '李昊', '', '900163aee11277cc4971a46fc922521c', '18251922802', 1, NULL, '苏州市', '00000000000000000000000000000000', '2018-07-27 23:00:12', NULL, NULL, 'Y');
INSERT INTO `sys_user` VALUES ('195A8273-4E6E-FECB-779C-FCC10A9F3AF6', '蓝雨 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922811', 0, NULL, '上海市', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('1D8878D8-63B3-3D00-34A7-4B964BE67D50', '刘恩佑 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922812', 0, NULL, '北京市', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('25e443bb96834e0f8302410b3118140f', '戴军梅', '', '75ced789e992bca40a545c732046e815', '18251922804', 1, NULL, '常州市', '00000000000000000000000000000000', '2018-07-27 23:00:12', NULL, NULL, 'Y');
INSERT INTO `sys_user` VALUES ('2661ee3ec38a4eb89ebecef34a944dd7', '贾宏声', '', '57e082fed0948fb235b6ddd09f24a1c6', '18251922806', 0, NULL, '盐城市', '00000000000000000000000000000000', '2018-07-27 23:00:12', NULL, NULL, 'Y');
INSERT INTO `sys_user` VALUES ('267FA6D5-260E-AEB8-DE4E-EE89FAB35499', '李光洁 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922814', 0, NULL, '长沙市', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('2fe67e86c813485caa97e9168e17f0cc', '戴军梅', '', 'f7b590dce086c7d5c897c88f37fe5b3b', '18251922804', 1, NULL, '常州市', NULL, '2018-08-05 19:27:58', NULL, NULL, 'Y');
INSERT INTO `sys_user` VALUES ('30CA760D-9CE1-5FB4-9B8D-75102FF0B4A6', '李连杰 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922808', 0, NULL, '镇江市', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('3dbd56094a284324b34c98f7def1649a', '区域管理员', 'admin', 'c2fe4afd8d91b7babc9859d5bdfb791c', '18251922826', 1, NULL, '南京市', '00000000000000000000000000000000', '2018-07-27 23:00:12', NULL, NULL, 'Y');
INSERT INTO `sys_user` VALUES ('45E108BD-7D4A-E4A9-B182-CF16E9566FC0', '于小伟 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922824', 0, NULL, '天水市', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('5817d80a201a4d478fa4c140795ec117', '蓝雨', '', '073b5fef60a5486cd07d6d10569c2722', '18251922811', 0, NULL, '上海市', NULL, '2018-08-05 19:28:04', NULL, NULL, 'Y');
INSERT INTO `sys_user` VALUES ('58DAEF53-14ED-F71B-E95F-61951BC85A49', '沙溢 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922821', 0, NULL, '宁波市', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('5c3216996e134a98ab7767afd8851e4e', '薛之谦', '', 'b9e714cd79718ac650289cc7127b6b8f', '18251922805', 1, NULL, '连云港', NULL, '2018-08-05 19:28:07', NULL, NULL, 'Y');
INSERT INTO `sys_user` VALUES ('5CA44192-6F31-8E73-00BC-C860DC6D346F', '贾宏声 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922806', 0, NULL, '盐城市', NULL, '2017-12-12 21:39:33', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('5DD52ED1-517F-E5EB-4417-2E9F3C3F883F', '张殿菲 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922817', 0, NULL, '重庆市', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('5EF7B795-076F-61AB-4E0E-46C288B1227E', '黄磊 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922823', 1, NULL, '兰州市', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('6B5A745F-1958-1D7D-0774-0FA775DD3CC9', '于波 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922807', 1, NULL, '淮安市', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('8285CD7B-535C-3C8A-8013-0A544F861E78', '张杰 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922819', 1, NULL, '马鞍山', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('96C89EE3-290D-6054-4F07-E888DCC72E81', '杨坤 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922820', 1, NULL, '南昌市', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('9f8cc7831918425d89b51a0a102157a5', '邢佳栋', '', '2b645ead0fcb67c7124273a3f0c3b8e2', '18251922800', 1, NULL, '南京市', NULL, '2018-08-05 19:28:12', NULL, NULL, 'Y');
INSERT INTO `sys_user` VALUES ('A10D3D8D-505C-55E6-B597-3BC88F0BD88C', '李茂 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922822', 1, NULL, '绍兴市', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('A123C83D-40B0-03DC-3F23-809E8631EE3F', '李美美', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922803', 1, NULL, '无锡市', NULL, '2017-12-12 21:39:24', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('a791af6df05941c0823802bdf8b9c3f0', '邢佳栋', '', 'ee902e2b8e509c2bf14c7d2f44c8cf11', '18251922800', 1, NULL, '南京市', '00000000000000000000000000000000', '2018-07-27 23:00:12', NULL, NULL, 'Y');
INSERT INTO `sys_user` VALUES ('ab56de8ba5164c3eb0cfd6fa782135ea', '刘恩佑', '', 'a7c79924114306ea1ba711c70b809c3d', '18251922812', 0, NULL, '北京市', NULL, '2018-08-05 19:28:15', NULL, NULL, 'Y');
INSERT INTO `sys_user` VALUES ('acdf33328ad6478ab71d91febfe454c3', '蓝雨', '', '12a8919192cbb88e87e94cd4d00ac0c6', '18251922811', 0, NULL, '上海市', '00000000000000000000000000000000', '2018-07-27 23:00:12', NULL, NULL, 'Y');
INSERT INTO `sys_user` VALUES ('ACEB9888-B017-1337-8CDF-14354FD64D60', '戴军梅 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922804', 1, NULL, '常州市', NULL, '2017-12-12 21:39:27', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('b1a3c5bb0a344dba923a31d0169bdaaf', '刘恩佑', '', '4023deba5da26eaf6252673109c68c9b', '18251922812', 0, NULL, '北京市', '00000000000000000000000000000000', '2018-07-27 23:00:12', NULL, NULL, 'Y');
INSERT INTO `sys_user` VALUES ('B4DD5063-9328-5702-ADB9-E4AB59B88B12', '薛之谦 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922805', 1, NULL, '连云港', NULL, '2017-12-12 21:39:30', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('B5774782-95A6-8C37-CC73-1E2C2340B7B3', '姜文 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922815', 1, NULL, '武汉市', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('D34A5FDA-C108-A994-01B6-A5A6BFF104D9', '任泉 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922813', 1, NULL, '郑州市', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('DA53B5AD-56D4-99B2-640A-987E6D76243F', '邓超 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922818', 1, NULL, '芜湖市', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('E350C542-B371-0DF7-8800-C1F648CD986F', '黑龙 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922816', 1, NULL, '天津市', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('eb46d88bf4dd456687670bfadfcd5a20', '李雪梅', '', '0935f4e5a66e9d7d8e9790c7d609bf58', '18251922801', 1, NULL, '南通市', NULL, '2018-08-05 19:28:21', NULL, NULL, 'Y');
INSERT INTO `sys_user` VALUES ('ECA8EBAC-8515-18F6-D062-7B5019C1B170', '李昊', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922802', 1, NULL, '苏州市', NULL, '2017-12-12 21:39:21', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('F03B8105-C4B5-DFDF-F4C0-79A129C0148C', '王斑 ', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922809', 1, NULL, '徐州市', NULL, '2017-12-12 21:39:35', 'FDC11C54-8A46-6587-64F1-1A9201FC267f', '2017-12-13 23:03:24', 'Y');
INSERT INTO `sys_user` VALUES ('f4b3e2d2be3948b39f41360c509f64c2', '薛之谦', '', 'e723846ed2c7eb55b081ad0ed72470dd', '18251922805', 1, NULL, '连云港', '00000000000000000000000000000000', '2018-07-27 23:00:12', NULL, NULL, 'Y');
INSERT INTO `sys_user` VALUES ('test001', 'test1', 'test001', 'f32aac1375452a66a1fafc94390d8d96', '18351922800', 1, NULL, '南京市', '00000000000000000000000000000000', '2018-07-27 23:03:18', '00000000000000000000000000000000', '2018-08-05 04:35:51', 'N');
INSERT INTO `sys_user` VALUES ('test002', 'test2', 'test002', '128b82ae7df73b9b5d6a0a5a83b8d635', '18351922801', 1, NULL, '南通市', '00000000000000000000000000000000', '2018-07-27 23:03:18', '00000000000000000000000000000000', '2018-08-05 04:35:51', 'N');
INSERT INTO `sys_user` VALUES ('user31', 'admin', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922827', 1, NULL, '天水市', NULL, '2017-12-14 15:42:15', NULL, '2017-12-14 15:42:20', 'Y');
INSERT INTO `sys_user` VALUES ('user32', 'normal', NULL, '67229b3e73823f91fa1b9ba1fd15cf20', '18251922828', 1, NULL, '福州市', NULL, '2017-12-14 15:42:18', NULL, '2017-12-14 15:42:23', 'Y');
COMMIT;

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
BEGIN;
INSERT INTO `sys_user_org` VALUES ('0001', '00000000000000000000000000000000', '0001', 'Y', '00000000000000000000000000000000', '2018-08-05', NULL, NULL);
INSERT INTO `sys_user_org` VALUES ('0002', '00b0daa6a59347a0a64a89c4bc57239b', '0002', 'Y', '00000000000000000000000000000000', '2018-08-06', NULL, NULL);
INSERT INTO `sys_user_org` VALUES ('0003', '00dd346f08284a5f927d90d046e2f220', '0003', 'Y', '00000000000000000000000000000000', '2018-08-07', NULL, NULL);
COMMIT;

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
BEGIN;
INSERT INTO `sys_user_role` VALUES ('01c7be7156d649838fad7a691eff8465', '00000000000000000000000000000000', 'role0003', '00000000000000000000000000000000', '2018-08-05 15:01:10', '00000000000000000000000000000000', '2018-08-05 15:01:10', 'Y');
INSERT INTO `sys_user_role` VALUES ('06515cd16fb84daba9d8964519469a13', '00b0daa6a59347a0a64a89c4bc57239b', 'role0001', '00000000000000000000000000000000', '2018-08-05 12:43:28', '00000000000000000000000000000000', '2018-08-05 13:48:56', 'Y');
INSERT INTO `sys_user_role` VALUES ('0f07d005e12f4fb5804eba6178bc938a', '127669b85d214e0ea7a1ffec9e2ab2d6', 'role0002', '00000000000000000000000000000000', '2018-08-05 12:45:12', '00000000000000000000000000000000', '2018-08-05 14:29:40', 'Y');
INSERT INTO `sys_user_role` VALUES ('3821ddb070cf416b9d852f63da564e7a', '00000000000000000000000000000000', 'role0002', '00000000000000000000000000000000', '2018-08-05 12:45:12', '00000000000000000000000000000000', '2018-08-05 14:29:40', 'Y');
INSERT INTO `sys_user_role` VALUES ('71c69bde0db049149a969418b4e1f1af', '127669b85d214e0ea7a1ffec9e2ab2d6', 'role0001', '00000000000000000000000000000000', '2018-08-05 12:44:57', '00000000000000000000000000000000', '2018-08-05 13:48:56', 'Y');
INSERT INTO `sys_user_role` VALUES ('a6731435e0f14cb99033e4cf815ecb73', '00000000000000000000000000000000', 'role0001', '00000000000000000000000000000000', '2018-08-05 12:43:28', '00000000000000000000000000000000', '2018-08-05 13:48:56', 'Y');
INSERT INTO `sys_user_role` VALUES ('b15f9cc659234df6825fc14db743384c', '00dd346f08284a5f927d90d046e2f220', 'role0001', '00000000000000000000000000000000', '2018-08-05 12:44:20', '00000000000000000000000000000000', '2018-08-05 13:48:56', 'Y');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
