/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50045
Source Host           : localhost:3306
Source Database       : erpdb2

Target Server Type    : MYSQL
Target Server Version : 50045
File Encoding         : 65001

Date: 2015-03-31 16:33:54
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `tbl_dep`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_dep`;
CREATE TABLE `tbl_dep` (
  `uuid` bigint(20) NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `tele` varchar(26) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_dep
-- ----------------------------
INSERT INTO `tbl_dep` VALUES ('1', '网络中心', '1234');
INSERT INTO `tbl_dep` VALUES ('2', '总裁办', '8888');
INSERT INTO `tbl_dep` VALUES ('3', '采购部', '8886');
INSERT INTO `tbl_dep` VALUES ('4', '销售部', '6668');
INSERT INTO `tbl_dep` VALUES ('5', '运管中心', '6666');
INSERT INTO `tbl_dep` VALUES ('6', '库管中心', '5678');
INSERT INTO `tbl_dep` VALUES ('7', '人力资源', '4444');
INSERT INTO `tbl_dep` VALUES ('8', '财务部', '3333');
INSERT INTO `tbl_dep` VALUES ('27', '请不要删我', '6666');

-- ----------------------------
-- Table structure for `tbl_emp`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_emp`;
CREATE TABLE `tbl_emp` (
  `uuid` bigint(20) NOT NULL auto_increment,
  `userName` varchar(30) NOT NULL,
  `pwd` varchar(32) NOT NULL,
  `name` varchar(30) NOT NULL,
  `email` varchar(255) NOT NULL,
  `tele` varchar(30) NOT NULL,
  `gender` int(1) NOT NULL,
  `address` varchar(255) NOT NULL,
  `birthday` bigint(20) NOT NULL,
  `depUuid` bigint(20) NOT NULL,
  `lastLoginTime` bigint(20) NOT NULL,
  `lastLoginIp` varchar(255) NOT NULL,
  `loginTimes` int(11) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_emp
-- ----------------------------
INSERT INTO `tbl_emp` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '赵云', 'lirl@itcast.cn', '110110', '1', '', '0', '1', '1427763953628', '0:0:0:0:0:0:0:1', '291');
INSERT INTO `tbl_emp` VALUES ('2', 'aa', 'e0323a9039add2978bf5b49550572c7c', 'aa', 'aa', 'aa', '1', 'aa', '1426608000010', '1', '1427513482006', '0:0:0:0:0:0:0:1', '2');
INSERT INTO `tbl_emp` VALUES ('4', 'cc', 'e0323a9039add2978bf5b49550572c7c', 'cc', 'cc', 'cc', '0', 'cc', '1300204800000', '5', '1427361059922', '0:0:0:0:0:0:0:1', '2');
INSERT INTO `tbl_emp` VALUES ('7', 'hr', '202cb962ac59075b964b07152d234b70', '王人力', 'hr@itcast.cn', '123321', '0', '金燕龙大厦', '1415030400000', '7', '1427095753167', '127.0.0.1', '4');
INSERT INTO `tbl_emp` VALUES ('8', 'hr1', '202cb962ac59075b964b07152d234b70', '刘面试', 'hr1@itcast.cn', '12312321321', '1', '金燕龙大厦', '1415548800000', '7', '1427095767162', '127.0.0.1', '4');
INSERT INTO `tbl_emp` VALUES ('10', 'xxx', 'f561aaf6ef0bf14d4208bb46a4ccb3ad', 'xxx', 'xxx', 'xxx', '1', 'xxx', '1427299200000', '1', '1426992757475', '-', '0');

-- ----------------------------
-- Table structure for `tbl_emp_role`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_emp_role`;
CREATE TABLE `tbl_emp_role` (
  `uuid` bigint(20) NOT NULL auto_increment,
  `empUuid` bigint(20) NOT NULL,
  `roleUuid` bigint(20) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_emp_role
-- ----------------------------
INSERT INTO `tbl_emp_role` VALUES ('31', '7', '8');
INSERT INTO `tbl_emp_role` VALUES ('32', '8', '9');
INSERT INTO `tbl_emp_role` VALUES ('40', '1', '1');
INSERT INTO `tbl_emp_role` VALUES ('41', '4', '1');
INSERT INTO `tbl_emp_role` VALUES ('42', '2', '1');

-- ----------------------------
-- Table structure for `tbl_goods`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_goods`;
CREATE TABLE `tbl_goods` (
  `uuid` bigint(20) NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `origin` varchar(30) NOT NULL,
  `producer` varchar(30) NOT NULL,
  `unit` varchar(30) NOT NULL,
  `inPrice` double(10,2) NOT NULL,
  `outPrice` double(10,2) NOT NULL,
  `goodsTypeUuid` bigint(20) NOT NULL,
  `useNum` int(11) NOT NULL,
  `maxNum` int(11) NOT NULL,
  `minNum` int(11) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_goods
-- ----------------------------
INSERT INTO `tbl_goods` VALUES ('1', '大白兔', '上海', '上海糖果厂', '斤', '1.10', '11.00', '1', '6', '1000', '50');
INSERT INTO `tbl_goods` VALUES ('2', '阿尔卑斯', '南京', '南京糖果厂', '箱', '2.12', '22.00', '1', '5', '1000', '50');
INSERT INTO `tbl_goods` VALUES ('3', '中南海', '北京', '北京卷烟厂', '箱', '3.10', '33.00', '2', '5', '1000', '50');
INSERT INTO `tbl_goods` VALUES ('4', '黄金叶', '河南', '河南卷烟厂', '箱', '4.00', '44.00', '2', '5', '1000', '50');
INSERT INTO `tbl_goods` VALUES ('5', '南京（九五至尊）', '南京', '南京卷烟厂', '条', '5.00', '55.00', '2', '4', '1000', '50');
INSERT INTO `tbl_goods` VALUES ('6', '茅台', '贵州', '贵州茅台集团', '箱', '6.00', '66.00', '3', '4', '1000', '50');
INSERT INTO `tbl_goods` VALUES ('7', 'iphone6plus', '深圳', '富士康代工厂', '个', '7.00', '77.00', '5', '2', '1000', '50');
INSERT INTO `tbl_goods` VALUES ('8', '神州小本', '郑州', '郑州富士康', '个', '8.00', '88.00', '6', '3', '1000', '50');
INSERT INTO `tbl_goods` VALUES ('9', '外星人', '美国', '美国笔记本厂', '箱', '9.00', '99.00', '6', '3', '1000', '50');

-- ----------------------------
-- Table structure for `tbl_goodstype`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_goodstype`;
CREATE TABLE `tbl_goodstype` (
  `uuid` bigint(20) NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `supplierUuid` bigint(20) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_goodstype
-- ----------------------------
INSERT INTO `tbl_goodstype` VALUES ('1', '糖', '1');
INSERT INTO `tbl_goodstype` VALUES ('2', '烟', '1');
INSERT INTO `tbl_goodstype` VALUES ('3', '酒', '1');
INSERT INTO `tbl_goodstype` VALUES ('4', '茶', '1');
INSERT INTO `tbl_goodstype` VALUES ('5', '手机', '2');
INSERT INTO `tbl_goodstype` VALUES ('6', '笔记本电脑', '2');
INSERT INTO `tbl_goodstype` VALUES ('7', '洗衣机', '2');
INSERT INTO `tbl_goodstype` VALUES ('8', '椰子', '3');

-- ----------------------------
-- Table structure for `tbl_menu`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_menu`;
CREATE TABLE `tbl_menu` (
  `uuid` bigint(20) NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `url` varchar(255) NOT NULL,
  `puuid` bigint(20) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=707 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_menu
-- ----------------------------
INSERT INTO `tbl_menu` VALUES ('1', '系统菜单', '-', '0');
INSERT INTO `tbl_menu` VALUES ('100', '商品管理', '-', '1');
INSERT INTO `tbl_menu` VALUES ('101', '供应商', 'supplier_list.action', '100');
INSERT INTO `tbl_menu` VALUES ('102', '商品类别', 'goodsType_list.action', '100');
INSERT INTO `tbl_menu` VALUES ('103', '商品', 'goods_list.action', '100');
INSERT INTO `tbl_menu` VALUES ('200', '采购管理', '-', '1');
INSERT INTO `tbl_menu` VALUES ('201', '采购订单', 'order_buyList.action', '200');
INSERT INTO `tbl_menu` VALUES ('202', '采购审批', 'order_buyCheckList.action', '200');
INSERT INTO `tbl_menu` VALUES ('300', '销售管理', '-', '1');
INSERT INTO `tbl_menu` VALUES ('400', '商品运输', '-', '1');
INSERT INTO `tbl_menu` VALUES ('401', '任务指派', 'transport_taskList.action', '400');
INSERT INTO `tbl_menu` VALUES ('402', '任务查询', 'transport_tasks.action', '400');
INSERT INTO `tbl_menu` VALUES ('500', '仓库管理', '-', '1');
INSERT INTO `tbl_menu` VALUES ('501', '入库', 'order_inStoreList.action', '500');
INSERT INTO `tbl_menu` VALUES ('600', '报表中心', '-', '1');
INSERT INTO `tbl_menu` VALUES ('601', '采购报表', 'bill_buyBill.action', '600');
INSERT INTO `tbl_menu` VALUES ('700', '基础维护', '-', '1');
INSERT INTO `tbl_menu` VALUES ('701', '部门维护', 'dep_list.action', '700');
INSERT INTO `tbl_menu` VALUES ('702', '员工维护', 'emp_list.action', '700');
INSERT INTO `tbl_menu` VALUES ('703', '角色维护', 'role_list.action', '700');
INSERT INTO `tbl_menu` VALUES ('704', '资源维护', 'res_list.action', '700');
INSERT INTO `tbl_menu` VALUES ('705', '菜单维护', 'menu_list.action', '700');
INSERT INTO `tbl_menu` VALUES ('706', '仓库维护', 'store_list.action', '700');

-- ----------------------------
-- Table structure for `tbl_order`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_order`;
CREATE TABLE `tbl_order` (
  `uuid` bigint(20) NOT NULL auto_increment,
  `orderNum` varchar(30) NOT NULL,
  `creater` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `checker` bigint(20) default NULL,
  `checkTime` bigint(20) default NULL,
  `completer` bigint(20) default NULL,
  `endTime` bigint(20) default NULL,
  `orderType` int(1) NOT NULL,
  `type` int(3) NOT NULL,
  `supplierUuid` bigint(20) NOT NULL,
  `totalNum` int(11) NOT NULL,
  `totalPrice` double(10,2) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_order
-- ----------------------------
INSERT INTO `tbl_order` VALUES ('1', '2015032600000001', '1', '1427358532848', '1', '1427508971420', null, null, '1', '120', '1', '1', '1.10');
INSERT INTO `tbl_order` VALUES ('2', '2015032600000002', '1', '1427358560098', '1', '1427507362901', '1', '1427534499782', '1', '199', '1', '210', '916.40');
INSERT INTO `tbl_order` VALUES ('3', '2015032600000003', '1', '1427359424538', '1', '1427510056209', '1', null, '1', '141', '2', '66', '645.48');
INSERT INTO `tbl_order` VALUES ('4', '2015032600000001', '1', '1427359998328', '1', '1427510051046', '1', null, '1', '141', '2', '300', '2600.00');
INSERT INTO `tbl_order` VALUES ('5', '728A956557601', '4', '1427361068834', '1', '1427510071177', '1', null, '1', '131', '1', '6', '21.32');
INSERT INTO `tbl_order` VALUES ('6', '728A956557601', '1', '1427362170175', '1', '1427510047919', '1', '1427534561186', '1', '199', '1', '2032', '10583.58');
INSERT INTO `tbl_order` VALUES ('7', '728A956557602', '1', '1427362174718', '1', '1427510059113', '2', null, '1', '131', '1', '6', '21.32');
INSERT INTO `tbl_order` VALUES ('8', '728A956557603', '1', '1427362178055', null, null, null, null, '1', '111', '1', '4', '10.32');
INSERT INTO `tbl_order` VALUES ('9', '728A956557604', '1', '1427362183093', '1', '1427510066394', null, null, '1', '121', '2', '3', '24.00');

-- ----------------------------
-- Table structure for `tbl_orderdetail`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_orderdetail`;
CREATE TABLE `tbl_orderdetail` (
  `uuid` bigint(20) NOT NULL auto_increment,
  `goodsUuid` bigint(20) NOT NULL,
  `price` double(10,2) NOT NULL,
  `orderUuid` bigint(20) NOT NULL,
  `num` int(11) NOT NULL,
  `surplus` int(11) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_orderdetail
-- ----------------------------
INSERT INTO `tbl_orderdetail` VALUES ('1', '1', '1.10', '1', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('2', '6', '6.00', '2', '60', '0');
INSERT INTO `tbl_orderdetail` VALUES ('3', '2', '2.12', '2', '20', '0');
INSERT INTO `tbl_orderdetail` VALUES ('4', '5', '5.00', '2', '50', '0');
INSERT INTO `tbl_orderdetail` VALUES ('5', '1', '1.10', '2', '10', '0');
INSERT INTO `tbl_orderdetail` VALUES ('6', '4', '4.00', '2', '40', '0');
INSERT INTO `tbl_orderdetail` VALUES ('7', '3', '3.10', '2', '30', '0');
INSERT INTO `tbl_orderdetail` VALUES ('8', '8', '12.34', '3', '22', '22');
INSERT INTO `tbl_orderdetail` VALUES ('9', '7', '7.00', '3', '11', '11');
INSERT INTO `tbl_orderdetail` VALUES ('10', '9', '9.00', '3', '33', '33');
INSERT INTO `tbl_orderdetail` VALUES ('11', '8', '8.00', '4', '100', '100');
INSERT INTO `tbl_orderdetail` VALUES ('12', '9', '9.00', '4', '200', '200');
INSERT INTO `tbl_orderdetail` VALUES ('13', '6', '6.00', '5', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('14', '2', '2.12', '5', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('15', '1', '1.10', '5', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('16', '3', '3.10', '5', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('17', '5', '5.00', '5', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('18', '4', '4.00', '5', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('19', '5', '5.00', '6', '555', '0');
INSERT INTO `tbl_orderdetail` VALUES ('20', '1', '1.10', '6', '30', '0');
INSERT INTO `tbl_orderdetail` VALUES ('21', '4', '4.00', '6', '444', '0');
INSERT INTO `tbl_orderdetail` VALUES ('22', '6', '6.00', '6', '998', '0');
INSERT INTO `tbl_orderdetail` VALUES ('23', '3', '3.10', '6', '1', '0');
INSERT INTO `tbl_orderdetail` VALUES ('24', '2', '2.12', '6', '4', '0');
INSERT INTO `tbl_orderdetail` VALUES ('25', '1', '1.10', '7', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('26', '2', '2.12', '7', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('27', '6', '6.00', '7', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('28', '4', '4.00', '7', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('29', '5', '5.00', '7', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('30', '3', '3.10', '7', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('31', '2', '2.12', '8', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('32', '4', '4.00', '8', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('33', '1', '1.10', '8', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('34', '3', '3.10', '8', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('35', '8', '8.00', '9', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('36', '9', '9.00', '9', '1', '1');
INSERT INTO `tbl_orderdetail` VALUES ('37', '7', '7.00', '9', '1', '1');

-- ----------------------------
-- Table structure for `tbl_res`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_res`;
CREATE TABLE `tbl_res` (
  `uuid` bigint(20) NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_res
-- ----------------------------
INSERT INTO `tbl_res` VALUES ('1', '员工列表', 'cn.itcast.erp.auth.emp.web.EmpAction.list');
INSERT INTO `tbl_res` VALUES ('2', '进入添加/修改员工', 'cn.itcast.erp.auth.emp.web.EmpAction.input');
INSERT INTO `tbl_res` VALUES ('3', '添加/修改员工', 'cn.itcast.erp.auth.emp.web.EmpAction.save');
INSERT INTO `tbl_res` VALUES ('4', '删除员工', 'cn.itcast.erp.auth.emp.web.EmpAction.delete');

-- ----------------------------
-- Table structure for `tbl_role`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_role`;
CREATE TABLE `tbl_role` (
  `uuid` bigint(20) NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `code` varchar(30) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_role
-- ----------------------------
INSERT INTO `tbl_role` VALUES ('1', '超级管理员', 'admin');
INSERT INTO `tbl_role` VALUES ('2', '采购主管', 'buymgr');
INSERT INTO `tbl_role` VALUES ('3', '采购专员', 'buyer');
INSERT INTO `tbl_role` VALUES ('4', '销售主管', 'salemgr');
INSERT INTO `tbl_role` VALUES ('5', '销售专员', 'saler');
INSERT INTO `tbl_role` VALUES ('6', '运输主管', 'transportmgr');
INSERT INTO `tbl_role` VALUES ('7', '跟单员', 'transporter');
INSERT INTO `tbl_role` VALUES ('8', '人力资源总监', 'hrmgr');
INSERT INTO `tbl_role` VALUES ('9', '人力资源专员', 'hr');
INSERT INTO `tbl_role` VALUES ('10', '老板', 'boss');

-- ----------------------------
-- Table structure for `tbl_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_role_menu`;
CREATE TABLE `tbl_role_menu` (
  `uuid` bigint(20) NOT NULL auto_increment,
  `roleUuid` bigint(20) NOT NULL,
  `menuUuid` bigint(20) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_role_menu
-- ----------------------------
INSERT INTO `tbl_role_menu` VALUES ('25', '9', '702');
INSERT INTO `tbl_role_menu` VALUES ('26', '9', '1');
INSERT INTO `tbl_role_menu` VALUES ('27', '9', '700');
INSERT INTO `tbl_role_menu` VALUES ('28', '8', '702');
INSERT INTO `tbl_role_menu` VALUES ('29', '8', '703');
INSERT INTO `tbl_role_menu` VALUES ('30', '8', '700');
INSERT INTO `tbl_role_menu` VALUES ('31', '8', '1');
INSERT INTO `tbl_role_menu` VALUES ('32', '8', '701');
INSERT INTO `tbl_role_menu` VALUES ('33', '8', '600');
INSERT INTO `tbl_role_menu` VALUES ('34', '8', '704');
INSERT INTO `tbl_role_menu` VALUES ('89', '1', '400');
INSERT INTO `tbl_role_menu` VALUES ('90', '1', '702');
INSERT INTO `tbl_role_menu` VALUES ('91', '1', '704');
INSERT INTO `tbl_role_menu` VALUES ('92', '1', '1');
INSERT INTO `tbl_role_menu` VALUES ('93', '1', '200');
INSERT INTO `tbl_role_menu` VALUES ('94', '1', '202');
INSERT INTO `tbl_role_menu` VALUES ('95', '1', '600');
INSERT INTO `tbl_role_menu` VALUES ('96', '1', '100');
INSERT INTO `tbl_role_menu` VALUES ('97', '1', '701');
INSERT INTO `tbl_role_menu` VALUES ('98', '1', '201');
INSERT INTO `tbl_role_menu` VALUES ('99', '1', '500');
INSERT INTO `tbl_role_menu` VALUES ('100', '1', '700');
INSERT INTO `tbl_role_menu` VALUES ('101', '1', '101');
INSERT INTO `tbl_role_menu` VALUES ('102', '1', '300');
INSERT INTO `tbl_role_menu` VALUES ('103', '1', '102');
INSERT INTO `tbl_role_menu` VALUES ('104', '1', '705');
INSERT INTO `tbl_role_menu` VALUES ('105', '1', '103');
INSERT INTO `tbl_role_menu` VALUES ('106', '1', '703');
INSERT INTO `tbl_role_menu` VALUES ('107', '1', '401');
INSERT INTO `tbl_role_menu` VALUES ('108', '1', '402');
INSERT INTO `tbl_role_menu` VALUES ('109', '1', '706');
INSERT INTO `tbl_role_menu` VALUES ('110', '1', '501');
INSERT INTO `tbl_role_menu` VALUES ('111', '1', '601');

-- ----------------------------
-- Table structure for `tbl_role_res`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_role_res`;
CREATE TABLE `tbl_role_res` (
  `uuid` bigint(20) NOT NULL auto_increment,
  `roleUuid` bigint(20) NOT NULL,
  `resUuid` bigint(20) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_role_res
-- ----------------------------
INSERT INTO `tbl_role_res` VALUES ('16', '9', '1');
INSERT INTO `tbl_role_res` VALUES ('17', '8', '3');
INSERT INTO `tbl_role_res` VALUES ('18', '8', '2');
INSERT INTO `tbl_role_res` VALUES ('19', '8', '1');
INSERT INTO `tbl_role_res` VALUES ('32', '1', '2');
INSERT INTO `tbl_role_res` VALUES ('33', '1', '4');
INSERT INTO `tbl_role_res` VALUES ('34', '1', '1');
INSERT INTO `tbl_role_res` VALUES ('35', '1', '3');

-- ----------------------------
-- Table structure for `tbl_store`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_store`;
CREATE TABLE `tbl_store` (
  `uuid` bigint(20) NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `address` varchar(30) NOT NULL,
  `empUuid` bigint(20) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_store
-- ----------------------------
INSERT INTO `tbl_store` VALUES ('1', '服装仓库', '北京市海淀区', '1');
INSERT INTO `tbl_store` VALUES ('2', '3#冷库', '北京昌平区', '1');
INSERT INTO `tbl_store` VALUES ('3', '食品仓库', '石家庄', '1');
INSERT INTO `tbl_store` VALUES ('4', '测试仓库', '测试地址', '2');

-- ----------------------------
-- Table structure for `tbl_storedetail`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_storedetail`;
CREATE TABLE `tbl_storedetail` (
  `uuid` bigint(20) NOT NULL auto_increment,
  `storeUuid` bigint(20) NOT NULL,
  `goodsUuid` bigint(20) NOT NULL,
  `num` int(11) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_storedetail
-- ----------------------------
INSERT INTO `tbl_storedetail` VALUES ('20', '1', '1', '2');
INSERT INTO `tbl_storedetail` VALUES ('21', '2', '1', '32');
INSERT INTO `tbl_storedetail` VALUES ('22', '3', '1', '6');
INSERT INTO `tbl_storedetail` VALUES ('23', '2', '2', '5');
INSERT INTO `tbl_storedetail` VALUES ('24', '1', '2', '18');
INSERT INTO `tbl_storedetail` VALUES ('25', '1', '3', '12');
INSERT INTO `tbl_storedetail` VALUES ('26', '2', '3', '12');
INSERT INTO `tbl_storedetail` VALUES ('27', '3', '3', '7');
INSERT INTO `tbl_storedetail` VALUES ('28', '1', '5', '567');
INSERT INTO `tbl_storedetail` VALUES ('29', '2', '5', '32');
INSERT INTO `tbl_storedetail` VALUES ('30', '3', '5', '6');
INSERT INTO `tbl_storedetail` VALUES ('31', '1', '4', '484');
INSERT INTO `tbl_storedetail` VALUES ('32', '1', '6', '1058');
INSERT INTO `tbl_storedetail` VALUES ('33', '3', '2', '1');

-- ----------------------------
-- Table structure for `tbl_storeoper`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_storeoper`;
CREATE TABLE `tbl_storeoper` (
  `uuid` bigint(20) NOT NULL auto_increment,
  `empUuid` bigint(20) NOT NULL,
  `operTime` bigint(20) NOT NULL,
  `orderDetailUuid` bigint(20) NOT NULL,
  `num` int(11) NOT NULL,
  `type` int(1) NOT NULL,
  `storeUuid` bigint(20) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_storeoper
-- ----------------------------
INSERT INTO `tbl_storeoper` VALUES ('31', '1', '1427534453688', '5', '2', '1', '1');
INSERT INTO `tbl_storeoper` VALUES ('32', '1', '1427534457258', '5', '2', '1', '2');
INSERT INTO `tbl_storeoper` VALUES ('33', '1', '1427534459636', '5', '6', '1', '3');
INSERT INTO `tbl_storeoper` VALUES ('34', '1', '1427534464499', '3', '4', '1', '2');
INSERT INTO `tbl_storeoper` VALUES ('35', '1', '1427534468345', '3', '14', '1', '1');
INSERT INTO `tbl_storeoper` VALUES ('36', '1', '1427534469053', '3', '2', '1', '1');
INSERT INTO `tbl_storeoper` VALUES ('37', '1', '1427534474689', '7', '11', '1', '1');
INSERT INTO `tbl_storeoper` VALUES ('38', '1', '1427534479662', '7', '12', '1', '2');
INSERT INTO `tbl_storeoper` VALUES ('39', '1', '1427534483003', '7', '7', '1', '3');
INSERT INTO `tbl_storeoper` VALUES ('40', '1', '1427534488786', '4', '12', '1', '1');
INSERT INTO `tbl_storeoper` VALUES ('41', '1', '1427534491789', '4', '21', '1', '2');
INSERT INTO `tbl_storeoper` VALUES ('42', '1', '1427534494660', '4', '11', '1', '2');
INSERT INTO `tbl_storeoper` VALUES ('43', '1', '1427534497058', '4', '6', '1', '3');
INSERT INTO `tbl_storeoper` VALUES ('44', '1', '1427534498722', '6', '40', '1', '1');
INSERT INTO `tbl_storeoper` VALUES ('45', '1', '1427534499776', '2', '60', '1', '1');
INSERT INTO `tbl_storeoper` VALUES ('46', '1', '1427534516919', '22', '998', '1', '1');
INSERT INTO `tbl_storeoper` VALUES ('47', '1', '1427534519869', '21', '444', '1', '1');
INSERT INTO `tbl_storeoper` VALUES ('48', '1', '1427534521886', '19', '555', '1', '1');
INSERT INTO `tbl_storeoper` VALUES ('49', '1', '1427534523503', '23', '1', '1', '1');
INSERT INTO `tbl_storeoper` VALUES ('50', '1', '1427534525977', '24', '1', '1', '1');
INSERT INTO `tbl_storeoper` VALUES ('51', '1', '1427534528515', '24', '1', '1', '1');
INSERT INTO `tbl_storeoper` VALUES ('52', '1', '1427534531999', '24', '1', '1', '2');
INSERT INTO `tbl_storeoper` VALUES ('53', '1', '1427534534350', '24', '1', '1', '3');
INSERT INTO `tbl_storeoper` VALUES ('54', '1', '1427534560162', '20', '1', '1', '2');
INSERT INTO `tbl_storeoper` VALUES ('55', '1', '1427534561183', '20', '29', '1', '2');

-- ----------------------------
-- Table structure for `tbl_supplier`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_supplier`;
CREATE TABLE `tbl_supplier` (
  `uuid` bigint(20) NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `address` varchar(30) NOT NULL,
  `contact` varchar(30) NOT NULL,
  `tele` varchar(30) NOT NULL,
  `needs` int(1) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_supplier
-- ----------------------------
INSERT INTO `tbl_supplier` VALUES ('1', '北京市糖烟酒总公司', '北京市王府井', '王糖糖', '11112222', '0');
INSERT INTO `tbl_supplier` VALUES ('2', '国美电器(中关村店)', '中关村大街155号', '赵手机', '99998888', '1');
INSERT INTO `tbl_supplier` VALUES ('3', '三亚鲜果批发总公司', '三亚市水果街1号', '孙果果', '88887654', '0');
INSERT INTO `tbl_supplier` VALUES ('4', '中腾建华楼下煎饼摊', '中腾建华出门左转100米', '赵煎饼', '00000000', '0');
