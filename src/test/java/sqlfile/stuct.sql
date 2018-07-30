/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.28-log : Database - jingjia
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `t_ad` */

CREATE TABLE `t_ad` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(10) unsigned NOT NULL COMMENT '此记录的用户id',
  `planid` int(10) unsigned NOT NULL COMMENT '该广告对应的计划',
  `unitid` int(10) unsigned NOT NULL COMMENT '该广告对应的单元',
  `title` char(255) NOT NULL COMMENT '用户填写的标题',
  `des` char(255) DEFAULT NULL COMMENT '用户填写的描述信息',
  `des1` char(255) DEFAULT NULL COMMENT '描述2',
  `url` varchar(1024) DEFAULT NULL COMMENT '实际被点击的url',
  `showurl` char(64) DEFAULT NULL COMMENT '展示出来的url',
  `phone` char(16) DEFAULT NULL COMMENT '电话',
  `adstat` tinyint(4) NOT NULL COMMENT '1 审核中\r\n2 有效\r\n3 审核未通过\r\n4 暂停推广\r\n',
  `checkno` tinyint(4) DEFAULT NULL COMMENT '审核不通过原因',
  `createtime` datetime NOT NULL COMMENT '该广告的创建时间',
  `lasttime` datetime NOT NULL COMMENT '最后一次修改的时间',
  `checkstat` tinyint(4) DEFAULT NULL COMMENT '审核状态',
  `imagepath` varchar(1024) DEFAULT NULL COMMENT '用户上传到服务器的路径地址',
  `remark` varchar(1024) DEFAULT NULL COMMENT '用户填写的备注（更改为按钮下载文字：默认0：不显示 1:显示）',
  `adtype` tinyint(4) DEFAULT '1' COMMENT '1文本广告2图片广告3小说广告4App广告11插屏广告12积分墙广告14图文广告',
  `apppath` varchar(1024) DEFAULT NULL COMMENT '上传应用地址',
  `appurl` varchar(1024) DEFAULT NULL COMMENT '下载地址',
  `deleteflag` tinyint(4) DEFAULT '1' COMMENT '1:正常2:删除',
  `promotion_type` int(4) DEFAULT '1' COMMENT '推广类型1：搜索推广 2：展示推广',
  `show_type` int(4) DEFAULT '0' COMMENT '展现形式 1：横幅广告2：全屏广告3：文字广告 5：插屏 8：图文',
  `click_type` int(4) DEFAULT '0' COMMENT '点击类型 1：打开网页2：下载程序3：拨打电话',
  `open_type` int(4) DEFAULT '0' COMMENT '打开方式 1：默认浏览器打开 2：用webvew打开 0：原搜索推广默认值无意义',
  `extid` varchar(64) DEFAULT NULL,
  `category` varchar(500) DEFAULT '0:DFT' COMMENT '应用类型',
  `quantity` int(4) DEFAULT '0' COMMENT '日均流量',
  `remark1` varchar(400) DEFAULT NULL COMMENT '备注字段 积分墙广告(默认 0:android 1:ios 2:其他)',
  `remark2` varchar(200) DEFAULT NULL COMMENT '备用字段2 积分墙广告存储 (受众男女 默认所有:0:DFT 男:1:0 女:1:1)',
  `pksize` int(11) DEFAULT '0' COMMENT '应用包大小，默认为0 单位KB',
  `pkname` varchar(255) DEFAULT NULL COMMENT '应用包名',
  PRIMARY KEY (`id`),
  KEY `idx_planid` (`planid`),
  KEY `idx_unitid` (`unitid`),
  KEY `idx_userid` (`userid`),
  KEY `idx_lasttime` (`lasttime`),
  KEY `idx_adstat` (`adstat`),
  KEY `idx_verify` (`unitid`,`adstat`,`checkstat`,`deleteflag`),
  KEY `idx_deleteflag` (`deleteflag`),
  KEY `idx_audit` (`adstat`,`checkstat`,`deleteflag`),
  KEY `t_adtype` (`adstat`,`checkstat`,`adtype`,`deleteflag`),
  KEY `idx_gowall` (`adtype`,`deleteflag`)
) ENGINE=InnoDB AUTO_INCREMENT=9303753 DEFAULT CHARSET=utf8;

/*Table structure for table `t_ad_ext` */

CREATE TABLE `t_ad_ext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ad_id` int(11) DEFAULT NULL COMMENT '广告id',
  `link_text1` char(255) DEFAULT NULL COMMENT '链接文字',
  `link_href1` varchar(1024) DEFAULT NULL COMMENT '链接',
  `link_text2` varchar(1024) DEFAULT NULL,
  `link_href2` varchar(1024) DEFAULT NULL,
  `link_text3` char(200) DEFAULT NULL,
  `link_href3` varchar(1024) DEFAULT NULL,
  `link_text4` char(200) DEFAULT NULL,
  `link_href4` varchar(1024) DEFAULT NULL,
  `remark1` varchar(200) DEFAULT NULL COMMENT '备用字段1',
  `remark2` varchar(200) DEFAULT NULL COMMENT '备用字段2 积分墙广告存储 获取积分说明',
  `remark3` varchar(200) DEFAULT NULL COMMENT '备用字段3 积分墙广告按钮文字',
  `remark4` varchar(200) DEFAULT NULL COMMENT '备用字段4 积分墙广告公司名称',
  `remark5` varchar(200) DEFAULT NULL COMMENT '备用字段5 锁屏广告付费形式',
  `remark6` varchar(200) DEFAULT NULL COMMENT '备用字段6 IOS积分墙scheme',
  PRIMARY KEY (`id`),
  KEY `idx_ad_id` (`ad_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51012 DEFAULT CHARSET=utf8;

/*Table structure for table `t_ad_media_config` */

CREATE TABLE `t_ad_media_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `adid` int(11) unsigned DEFAULT NULL COMMENT '广告id',
  `mediaids` varchar(9000) DEFAULT '0:DFT' COMMENT '对应投放的媒体id 默认为0:DFT投放所有',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Table structure for table `t_ad_postion` */

CREATE TABLE `t_ad_postion` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `cmatch` int(6) DEFAULT NULL COMMENT '广告位id',
  `name` varchar(100) DEFAULT NULL COMMENT '广告位名称',
  `pdesc` varchar(100) DEFAULT NULL COMMENT '父频道名称',
  `channel` int(5) DEFAULT NULL COMMENT '频道id',
  `dept` varchar(200) DEFAULT NULL,
  `tfl` float DEFAULT '0' COMMENT '投放量占比',
  `unitids` varchar(200) DEFAULT NULL COMMENT '选择该广告位的广告组id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

/*Table structure for table `t_ad_postion_b` */

CREATE TABLE `t_ad_postion_b` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `cmatch` int(6) DEFAULT NULL COMMENT '广告位id',
  `name` varchar(100) DEFAULT NULL COMMENT '广告位名称',
  `pdesc` varchar(100) DEFAULT NULL COMMENT '父频道名称',
  `channel` int(5) DEFAULT NULL COMMENT '频道id',
  `dept` varchar(200) DEFAULT NULL,
  `tfl` float DEFAULT '0' COMMENT '投放量占比',
  `unitids` varchar(200) DEFAULT NULL COMMENT '选择该广告位的广告组id',
  PRIMARY KEY (`id`),
  KEY `idx_cmatch` (`cmatch`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8;

/*Table structure for table `t_ad_postion_by_date` */

CREATE TABLE `t_ad_postion_by_date` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `date` date DEFAULT NULL COMMENT '广告位所属日期',
  `cmatch` int(6) DEFAULT NULL COMMENT '广告位id',
  `tfl` float DEFAULT '0' COMMENT '投放量占比',
  `unitids` varchar(200) DEFAULT NULL COMMENT '选择该广告位的广告组id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3249 DEFAULT CHARSET=utf8;

/*Table structure for table `t_ad_sort` */

CREATE TABLE `t_ad_sort` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `levelinfo` int(11) NOT NULL COMMENT '层级',
  `gfixedids` varchar(21841) DEFAULT '0:DFT' COMMENT '全局排名',
  PRIMARY KEY (`id`,`levelinfo`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Table structure for table `t_adwall_data` */

CREATE TABLE `t_adwall_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id 自增',
  `adid` int(11) DEFAULT NULL COMMENT '积分墙广告id',
  `unitid` int(11) DEFAULT NULL COMMENT '广告所在的广告组',
  `adname` varchar(255) DEFAULT NULL COMMENT '积分墙广告名称',
  `unitname` varchar(255) DEFAULT NULL COMMENT '广告组名称',
  `date` date NOT NULL DEFAULT '0000-00-00' COMMENT '日期',
  `lasttime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '最后修改时间',
  `adprice` int(11) DEFAULT '0' COMMENT '广告接入单价 单位分',
  `adactivenum` int(11) DEFAULT '0' COMMENT '广告主统计激活数',
  `esactivenum` int(11) DEFAULT '0' COMMENT '宜搜统计激活数',
  `esoutdataoutcome` int(11) DEFAULT '0' COMMENT '宜搜出数支出 单位分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32873 DEFAULT CHARSET=utf8;

/*Table structure for table `t_alter_log` */

CREATE TABLE `t_alter_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(10) unsigned DEFAULT NULL COMMENT '用户信息表id',
  `level` tinyint(4) DEFAULT NULL COMMENT '用户变更层级\r\n1 用户\r\n2 计划\r\n3 广告组\r\n4 广告\r\n5 关键字\r\n',
  `objctid` int(10) unsigned NOT NULL COMMENT '根据level来定是用户id、计划id、者广告组id、广告id或者关键词id。',
  `objectdesc` char(128) DEFAULT NULL COMMENT '对象变更之前的描述',
  `altertime` datetime DEFAULT NULL COMMENT '状态变更时间',
  `oldstat` tinyint(4) DEFAULT NULL COMMENT '原有状态',
  `newstat` tinyint(4) DEFAULT NULL COMMENT '新状态',
  PRIMARY KEY (`id`),
  KEY `idx_userid` (`userid`),
  KEY `idx_objectid` (`objctid`),
  KEY `idx_altertime` (`altertime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_app_resource` */

CREATE TABLE `t_app_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resourcename` varchar(200) DEFAULT NULL COMMENT '资源名称',
  `principal` varchar(200) DEFAULT NULL COMMENT '负责人',
  `company` varchar(200) DEFAULT NULL COMMENT '公司名',
  `city` varchar(200) DEFAULT NULL COMMENT '城市',
  `address` varchar(400) DEFAULT NULL COMMENT '公司地址',
  `marketurl` varchar(400) DEFAULT NULL COMMENT '市场链接',
  `downloadnum` varchar(100) DEFAULT NULL COMMENT '下载量',
  `type` varchar(100) DEFAULT NULL COMMENT '分类',
  `status` varchar(100) DEFAULT NULL COMMENT '跟进状态',
  `info` text COMMENT '说明',
  `linkman` varchar(100) DEFAULT NULL COMMENT '联系人',
  `linkmanphone` varchar(100) DEFAULT NULL COMMENT '联系人电话',
  `telephone` varchar(100) DEFAULT NULL COMMENT '公司电话',
  `linkmanposition` varchar(100) DEFAULT NULL COMMENT '联系人职务',
  `qq` varchar(50) DEFAULT NULL COMMENT 'QQ',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `othercontactinfo` varchar(100) DEFAULT NULL COMMENT '其他联系方式',
  `remark` text COMMENT '备注信息',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '录入时间',
  `lasttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `adminid` int(11) DEFAULT NULL COMMENT '管理员id',
  PRIMARY KEY (`id`),
  KEY `index_principal` (`principal`)
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8;

/*Table structure for table `t_app_resource_remark` */

CREATE TABLE `t_app_resource_remark` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resourceid` int(11) DEFAULT NULL COMMENT 'app资源id',
  `remark` text COMMENT '备注',
  `adminid` int(11) DEFAULT NULL COMMENT '管理员id',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;

/*Table structure for table `t_apply_industry_log` */

CREATE TABLE `t_apply_industry_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL COMMENT '用户id',
  `adminId` int(11) DEFAULT '0' COMMENT '预留管理员id',
  `userName` varchar(100) DEFAULT NULL COMMENT '用户名',
  `industry` int(11) DEFAULT NULL COMMENT '原一级行业',
  `smallindustry` int(11) DEFAULT NULL COMMENT '原二级行业',
  `newIndustry` int(11) DEFAULT NULL COMMENT '新一级行业',
  `newSmallindustry` int(11) DEFAULT NULL COMMENT '新二级行业',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请行业修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8;

/*Table structure for table `t_apply_url_log` */

CREATE TABLE `t_apply_url_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `url` varchar(200) DEFAULT NULL,
  `newUrl` varchar(200) DEFAULT NULL,
  `reason` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1648 DEFAULT CHARSET=utf8;

/*Table structure for table `t_bank` */

CREATE TABLE `t_bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `bankname` varchar(30) DEFAULT NULL COMMENT '银行名称',
  `code` int(4) DEFAULT NULL COMMENT '编码',
  `ordernum` int(4) DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Table structure for table `t_base_user` */

CREATE TABLE `t_base_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uname` char(32) NOT NULL COMMENT '客户自由注册，一旦注册后客户无权限修改',
  `password` char(32) DEFAULT NULL COMMENT '密码',
  `company` char(128) DEFAULT NULL COMMENT '合同签订的公司名称，一旦注册客户无权限修改',
  `site` char(64) DEFAULT NULL COMMENT '需与被推广网站名称保持一致，一旦注册客户无权限修改',
  `url` varchar(1024) DEFAULT NULL COMMENT '客户被推广网站主域名',
  `province` tinyint(4) DEFAULT NULL COMMENT '省份/直辖市',
  `city` tinyint(4) DEFAULT NULL COMMENT '城市/区',
  `address` varchar(1024) DEFAULT NULL COMMENT '详细通讯地址',
  `contact` char(32) DEFAULT NULL COMMENT '联系人',
  `telephone` char(32) DEFAULT NULL COMMENT '联系电话（固话）',
  `mobile` char(32) DEFAULT NULL COMMENT '联系电话（手机）',
  `mail` char(64) DEFAULT NULL COMMENT 'Email',
  `post` char(16) DEFAULT NULL COMMENT '邮政编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `fax` char(16) DEFAULT NULL COMMENT '传真',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `telcode` char(4) DEFAULT NULL COMMENT '固话区号',
  `faxcode` char(4) DEFAULT NULL COMMENT '传真区号',
  `u_type` tinyint(4) DEFAULT '1' COMMENT '1:普通用户；2：高危A1；3：高危A2；4：高危A3',
  `changetime` datetime DEFAULT NULL COMMENT '信息更新时间',
  `industry` int(11) DEFAULT NULL COMMENT '用户的行业',
  `smallindustry` int(11) DEFAULT NULL COMMENT '用户二级行业',
  `source` int(2) DEFAULT NULL COMMENT ' 1试点（直销）、2营销部，3互动广告部，4广告部，5市场部，6游戏部,7客户端,8媒体部门',
  `billtype` int(2) DEFAULT NULL COMMENT '计费类型 1:cpc广告 2:cps小说 3:cps音乐 4:cps购物 5:cps视频 6:cpm品牌',
  `logintype` int(2) DEFAULT NULL COMMENT '登陆用户类别 1:正式用户 2:测试用户',
  `risk` int(6) DEFAULT '0' COMMENT '高危行业标注',
  `qq` char(16) DEFAULT NULL,
  `discount` int(3) DEFAULT '100' COMMENT '出价*该字段折扣 计算为实际出价 按实际出价影响广告排名 ',
  `cptype` int(2) DEFAULT '0' COMMENT '是否为CP用户，默认为0:非cp用户，1:cp用户',
  `sysbussnis_id` int(4) DEFAULT '0' COMMENT '对应商务id',
  PRIMARY KEY (`id`),
  KEY `idx_uname` (`uname`),
  KEY `idx_company` (`company`),
  KEY `idx_mail` (`mail`)
) ENGINE=InnoDB AUTO_INCREMENT=13450 DEFAULT CHARSET=utf8;

/*Table structure for table `t_base_user_bk` */

CREATE TABLE `t_base_user_bk` (
  `id` int(10) unsigned NOT NULL DEFAULT '0',
  `uname` char(32) NOT NULL COMMENT '客户自由注册，一旦注册后客户无权限修改',
  `password` char(32) DEFAULT NULL COMMENT '密码',
  `company` char(128) DEFAULT NULL COMMENT '合同签订的公司名称，一旦注册客户无权限修改',
  `site` char(64) DEFAULT NULL COMMENT '需与被推广网站名称保持一致，一旦注册客户无权限修改',
  `url` varchar(1024) DEFAULT NULL COMMENT '客户被推广网站主域名',
  `province` tinyint(4) DEFAULT NULL COMMENT '省份/直辖市',
  `city` tinyint(4) DEFAULT NULL COMMENT '城市/区',
  `address` varchar(1024) DEFAULT NULL COMMENT '详细通讯地址',
  `contact` char(32) DEFAULT NULL COMMENT '联系人',
  `telephone` char(32) DEFAULT NULL COMMENT '联系电话（固话）',
  `mobile` char(32) DEFAULT NULL COMMENT '联系电话（手机）',
  `mail` char(64) DEFAULT NULL COMMENT 'Email',
  `post` char(16) DEFAULT NULL COMMENT '邮政编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `fax` char(16) DEFAULT NULL COMMENT '传真',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `telcode` char(4) DEFAULT NULL COMMENT '固话区号',
  `faxcode` char(4) DEFAULT NULL COMMENT '传真区号',
  `u_type` tinyint(4) DEFAULT '1' COMMENT '1:普通用户；2：高危A1；3：高危A2；4：高危A3',
  `changetime` datetime DEFAULT NULL COMMENT '信息更新时间',
  `industry` int(11) DEFAULT NULL COMMENT '用户的行业',
  `smallindustry` int(11) DEFAULT NULL COMMENT '用户二级行业',
  `source` int(2) DEFAULT NULL COMMENT '1直销部 2营销部 3广告部 4市场部',
  `billtype` int(2) DEFAULT NULL COMMENT '计费类型 1:cpc广告 2:cps小说 3:cps音乐 4:cps购物 5:cps视频 6:cpm品牌',
  `logintype` int(2) DEFAULT NULL COMMENT '登陆用户类别 1:正式用户 2:测试用户',
  `risk` int(6) DEFAULT '0' COMMENT '高危行业标注',
  `qq` char(16) DEFAULT NULL,
  `discount` int(3) DEFAULT '100' COMMENT '出价*该字段折扣 计算为实际出价 按实际出价影响广告排名 '
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_base_user_clean` */

CREATE TABLE `t_base_user_clean` (
  `id` int(10) unsigned NOT NULL DEFAULT '0',
  `uname` char(32) NOT NULL COMMENT '客户自由注册，一旦注册后客户无权限修改',
  `password` char(32) DEFAULT NULL COMMENT '密码',
  `company` char(128) DEFAULT NULL COMMENT '合同签订的公司名称，一旦注册客户无权限修改',
  `site` char(64) DEFAULT NULL COMMENT '需与被推广网站名称保持一致，一旦注册客户无权限修改',
  `url` varchar(1024) DEFAULT NULL COMMENT '客户被推广网站主域名',
  `province` tinyint(4) DEFAULT NULL COMMENT '省份/直辖市',
  `city` tinyint(4) DEFAULT NULL COMMENT '城市/区',
  `address` varchar(1024) DEFAULT NULL COMMENT '详细通讯地址',
  `contact` char(32) DEFAULT NULL COMMENT '联系人',
  `telephone` char(32) DEFAULT NULL COMMENT '联系电话（固话）',
  `mobile` char(32) DEFAULT NULL COMMENT '联系电话（手机）',
  `mail` char(64) DEFAULT NULL COMMENT 'Email',
  `post` char(16) DEFAULT NULL COMMENT '邮政编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `fax` char(16) DEFAULT NULL COMMENT '传真',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `telcode` char(4) DEFAULT NULL COMMENT '固话区号',
  `faxcode` char(4) DEFAULT NULL COMMENT '传真区号',
  `u_type` tinyint(4) DEFAULT '1' COMMENT '1:普通用户；2：高危A1；3：高危A2；4：高危A3',
  `changetime` datetime DEFAULT NULL COMMENT '信息更新时间',
  `industry` int(11) DEFAULT NULL COMMENT '用户的行业',
  `smallindustry` int(11) DEFAULT NULL COMMENT '用户二级行业',
  `source` int(2) DEFAULT NULL COMMENT '1直销部 2营销部 3广告部 4市场部',
  `billtype` int(2) DEFAULT NULL COMMENT '计费类型 1:cpc广告 2:cps小说 3:cps音乐 4:cps购物 5:cps视频 6:cpm品牌',
  `logintype` int(2) DEFAULT NULL COMMENT '登陆用户类别 1:正式用户 2:测试用户',
  `risk` int(6) DEFAULT '0' COMMENT '高危行业标注',
  `qq` char(16) DEFAULT NULL,
  `discount` int(3) DEFAULT '100' COMMENT '出价*该字段折扣 计算为实际出价 按实际出价影响广告排名 '
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_bidword` */

CREATE TABLE `t_bidword` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `word` char(64) DEFAULT NULL COMMENT '词名称',
  `bidnum` int(11) DEFAULT NULL COMMENT '购买数量',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `lasttime` datetime DEFAULT NULL COMMENT '最后一次购买时间',
  `minbid` int(11) DEFAULT NULL COMMENT '最低出价',
  `r_bid` int(11) DEFAULT NULL COMMENT '参考出价',
  `industry` int(11) DEFAULT '0' COMMENT '行业id',
  `pricesystem` varchar(128) DEFAULT '30;0;0;0;0;0;0;0' COMMENT '价格体系',
  `userid` int(11) DEFAULT NULL COMMENT '所属购买人，用于小说，音乐数据导入时的标记',
  `wordindustry` int(11) DEFAULT NULL COMMENT '所属行业，用于小说，音乐数据导入时的标记 1:小说 2:音乐',
  `minshowbid` int(11) DEFAULT NULL COMMENT '最低展现价格',
  PRIMARY KEY (`id`),
  KEY `word` (`word`),
  KEY `index_pricesystem` (`pricesystem`),
  KEY `bidnum` (`bidnum`),
  KEY `minbid` (`minbid`),
  KEY `minbidorder` (`minbid`,`bidnum`,`id`,`word`,`createtime`,`industry`,`pricesystem`,`lasttime`)
) ENGINE=InnoDB AUTO_INCREMENT=10251106 DEFAULT CHARSET=utf8;

/*Table structure for table `t_biz_agent` */

CREATE TABLE `t_biz_agent` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(10) DEFAULT NULL COMMENT '代理商id',
  `income` int(10) DEFAULT '0' COMMENT '总入账金额',
  `outcome` int(10) DEFAULT '0' COMMENT '总分配金额',
  `value` int(10) DEFAULT '0' COMMENT '可分配金额',
  `createtime` datetime DEFAULT NULL COMMENT '创建日期',
  `opuserid` int(10) DEFAULT NULL COMMENT '操作人',
  `updatetime` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `t_biz_agent_bill` */

CREATE TABLE `t_biz_agent_bill` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` char(16) DEFAULT NULL COMMENT '订单编号',
  `userid` int(10) DEFAULT NULL COMMENT '代理商编号',
  `flag` int(10) DEFAULT NULL COMMENT '标志 1:入账，2：分配',
  `value` int(10) DEFAULT NULL COMMENT '金额 单位：分',
  `createtime` datetime DEFAULT NULL COMMENT '创建日期',
  `opuserid` int(10) DEFAULT NULL COMMENT '操作人',
  `remark` char(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12825 DEFAULT CHARSET=utf8;

/*Table structure for table `t_biz_customer_bill` */

CREATE TABLE `t_biz_customer_bill` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` char(16) DEFAULT NULL COMMENT '账单编号',
  `userid` int(10) DEFAULT NULL COMMENT '代理商id',
  `customerid` int(10) DEFAULT NULL COMMENT '客户id',
  `inputvalue` int(10) DEFAULT NULL COMMENT '金额：分',
  `createtime` datetime DEFAULT NULL COMMENT '创建日期',
  `usetype` int(10) DEFAULT NULL COMMENT '用途 1:服务费 2:预存款',
  `usertype` int(10) DEFAULT NULL COMMENT '用户类型 1:新用户 2:老用户',
  `remark` char(255) DEFAULT NULL COMMENT '备注',
  `freeamount` int(12) DEFAULT NULL COMMENT '赠送金额',
  `billtype` int(2) DEFAULT NULL COMMENT '1：代理商系统充值 0：mis后台直冲',
  `billing` int(1) DEFAULT NULL COMMENT '1已开具发票 0未开具发票',
  `billtime` datetime DEFAULT NULL COMMENT '提交发票申请时间',
  `discount` int(3) DEFAULT '100' COMMENT '出价*该字段折扣 计算为实际出价 按实际出价影响广告排名 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13642 DEFAULT CHARSET=utf8;

/*Table structure for table `t_c3p0test` */

CREATE TABLE `t_c3p0test` (
  `a` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Table structure for table `t_crm_user` */

CREATE TABLE `t_crm_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userid` int(11) DEFAULT NULL COMMENT 'crm用户id',
  `username` varchar(100) DEFAULT NULL COMMENT 'crm用户名',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=236 DEFAULT CHARSET=utf8;

/*Table structure for table `t_crs_remark` */

CREATE TABLE `t_crs_remark` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `rid` int(11) DEFAULT NULL COMMENT '表客户资源id,表t_crs_resource id',
  `lasttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '联系日期',
  `remark` text COMMENT '跟进记录',
  `opuserid` int(11) DEFAULT NULL COMMENT '预留系统操作者id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Table structure for table `t_crs_resource` */

CREATE TABLE `t_crs_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户资源id',
  `principal` varchar(100) DEFAULT NULL COMMENT '负责人',
  `name` varchar(100) DEFAULT NULL COMMENT '客户姓名',
  `company` varchar(100) DEFAULT NULL COMMENT '公司',
  `address` varchar(200) DEFAULT NULL COMMENT '公司地址',
  `industry` varchar(100) DEFAULT NULL COMMENT '行业',
  `status` varchar(100) DEFAULT NULL COMMENT '跟进状态',
  `type` varchar(100) DEFAULT NULL COMMENT '分级:高、较高、低',
  `adminid` int(11) DEFAULT '0' COMMENT '系统客户管理员id',
  `linkman` varchar(100) DEFAULT NULL COMMENT '联系人',
  `linkmanphone` varchar(100) DEFAULT NULL COMMENT '联系电话',
  `telephone` varchar(100) DEFAULT NULL COMMENT '公司电话',
  `linkmanposition` varchar(100) DEFAULT NULL COMMENT '联系人职务',
  `companyproduct` varchar(100) DEFAULT NULL COMMENT '公司产品',
  `qq` varchar(50) DEFAULT NULL COMMENT 'qq',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `othercontactinfo` varchar(100) DEFAULT NULL COMMENT '其他联系方式',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '录入时间',
  `lasttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `opuserid` int(11) DEFAULT '0' COMMENT '系统操作者id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Table structure for table `t_crs_user` */

CREATE TABLE `t_crs_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '销售级别id',
  `level` varchar(32) DEFAULT NULL COMMENT '管理级别 0总监 1高级经理 2经理',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `lasttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `adminid` int(11) DEFAULT NULL COMMENT '系统客户管理员id',
  `opuserid` int(11) DEFAULT NULL COMMENT '系统操作者id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;

/*Table structure for table `t_data_download` */

CREATE TABLE `t_data_download` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `userid` int(11) DEFAULT NULL COMMENT '用户id',
  `applydate` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '申请导出日期时间',
  `downloadurl` varchar(255) DEFAULT NULL COMMENT '下载地址（file_useid）',
  `applyflag` int(4) DEFAULT '0' COMMENT '申请标记 默认0 未申请，1已申请',
  `donetime` timestamp NULL DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=435 DEFAULT CHARSET=utf8;

/*Table structure for table `t_domain_ext` */

CREATE TABLE `t_domain_ext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL COMMENT '用户id',
  `domain` varchar(1024) DEFAULT NULL COMMENT '多个域名，逗号分隔',
  `opuserid` int(11) DEFAULT NULL COMMENT '操作人',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `lasttime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9442 DEFAULT CHARSET=utf8;

/*Table structure for table `t_dsp_config` */

CREATE TABLE `t_dsp_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `muid` int(10) DEFAULT NULL,
  `mediaid` int(10) DEFAULT NULL,
  `dsp_pt` varchar(100) DEFAULT NULL COMMENT 'dsp平台1、哇棒 2、adview 3、都锦 4、苏宁 5、今日头条 6、银橙',
  `adtype` int(4) DEFAULT NULL COMMENT '1、横幅 2、文本 3、全屏 4、推送 5、插屏 6、积分墙 7、锁屏 8、图文 9、原生 10、开屏',
  `os` int(4) DEFAULT NULL COMMENT '安卓 1	IOS 2',
  `shownum` int(11) DEFAULT '0' COMMENT '展现量限制',
  `status` int(4) DEFAULT '1' COMMENT '有效1，无效：0',
  `creattime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lasttime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `adminid` int(11) DEFAULT NULL COMMENT '后台操作者id',
  `validdate` date DEFAULT '0000-00-00' COMMENT '生效日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Table structure for table `t_dsp_config_op` */

CREATE TABLE `t_dsp_config_op` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `muid` int(10) DEFAULT NULL,
  `mediaid` int(10) DEFAULT NULL,
  `dsp_pt` varchar(100) DEFAULT NULL COMMENT 'dsp平台哇棒 1 adview 2 都锦 3 苏宁 4',
  `adtype` int(4) DEFAULT NULL COMMENT '1、横幅 2、文本 3、全屏 4、推送 5、插屏 6、积分墙 7、锁屏 8、图文 9、原生 10、开屏',
  `os` int(4) DEFAULT NULL COMMENT '安卓 1    IOS 2',
  `shownum` int(11) DEFAULT '0' COMMENT '展现量限制',
  `lasttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `adminid` int(11) DEFAULT NULL COMMENT '后台操作者id',
  `validdate` date DEFAULT '0000-00-00' COMMENT '生效日期',
  `status` int(4) DEFAULT '1' COMMENT '有效1，无效：0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=258 DEFAULT CHARSET=utf8;

/*Table structure for table `t_dsp_history_data` */

CREATE TABLE `t_dsp_history_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mediaid` int(10) DEFAULT NULL,
  `dsp_pt` varchar(100) DEFAULT NULL COMMENT 'dsp平台哇棒 1 adview 2 都锦 3 苏宁 4',
  `adtype` int(4) DEFAULT NULL COMMENT '1、横幅 2、文本 3、全屏 4、推送 5、插屏 6、积分墙 7、锁屏 8、图文 9、原生 10、开屏',
  `os` int(4) DEFAULT NULL COMMENT '安卓 1	IOS 2',
  `show_data` int(11) DEFAULT NULL COMMENT '展现量数据',
  `click_data` int(11) DEFAULT NULL COMMENT '点击数据',
  `down_data` int(11) DEFAULT NULL COMMENT '下载数据',
  `downed_data` int(11) DEFAULT NULL COMMENT '下载完成数据',
  `installed_data` int(11) DEFAULT NULL COMMENT '安装完成数据',
  `date_data` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '数据日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6402 DEFAULT CHARSET=utf8;

/*Table structure for table `t_es_ad_history_data` */

CREATE TABLE `t_es_ad_history_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ad_id` int(4) DEFAULT NULL COMMENT '广告id',
  `value` int(10) DEFAULT '0' COMMENT '消耗金额(单位 分)',
  `shownum` int(11) DEFAULT '0' COMMENT '展现',
  `clicknum` int(11) DEFAULT '0' COMMENT '点击',
  `validclicknum` int(11) DEFAULT '0' COMMENT '有效点击',
  `downsecondnum` int(11) DEFAULT '0' COMMENT '非wifi环境下，下载二次确认',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `cost_date` date NOT NULL DEFAULT '0000-00-00' COMMENT '数据消费日期',
  `ad_type` tinyint(4) DEFAULT NULL COMMENT '广告类别：1：宜搜开屏广告 2：宜搜原生广告',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16385 DEFAULT CHARSET=utf8;

/*Table structure for table `t_es_brandad` */

CREATE TABLE `t_es_brandad` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` char(255) NOT NULL COMMENT '广告名称',
  `title` char(255) DEFAULT NULL COMMENT '用户填写的标题',
  `des` char(255) DEFAULT NULL COMMENT '用户填写的描述信息',
  `imagepath` varchar(1024) DEFAULT NULL COMMENT '用户上传到服务器的路径地址',
  `url` varchar(1024) DEFAULT NULL COMMENT 'url',
  `listen_url` varchar(1024) DEFAULT NULL COMMENT '监控url',
  `adstat` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：有效 1：暂停',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '该广告的创建时间',
  `lasttime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后一次修改的时间',
  `deleteflag` tinyint(4) DEFAULT '1' COMMENT '1:正常2:删除',
  `begindate` datetime DEFAULT NULL COMMENT '开始时间 默认当前时间',
  `enddate` datetime DEFAULT NULL COMMENT '结束时间 默认为0无结束时间',
  `opuserid` int(4) DEFAULT NULL COMMENT '操作者id',
  `mediaid` int(4) DEFAULT NULL COMMENT '媒体id',
  `es_mediaid` int(4) DEFAULT NULL COMMENT '对应投放位置的媒体 对应t_es_media 中id',
  `es_positionid` int(4) DEFAULT NULL COMMENT '对应投放位置的投放位id 对应t_es_media_position 中id',
  `click_type` int(4) DEFAULT '2' COMMENT '网页浏览类(1)，下载应用类(2)',
  `showos` varchar(20) DEFAULT '2' COMMENT '操作系统选择 android: 1 默认 ,ios: 2 ,android+ios:1;2 ',
  `adsource` int(4) DEFAULT '1' COMMENT '广告来源 1：默认 爱得康赛 2：其他',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=189 DEFAULT CHARSET=utf8;

/*Table structure for table `t_es_media` */

CREATE TABLE `t_es_media` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `mediaid` int(4) DEFAULT NULL COMMENT '媒体id',
  `name` varchar(255) DEFAULT NULL COMMENT '投放媒体名称',
  `deleteflag` tinyint(4) DEFAULT '1' COMMENT '删除标记 1:正常 2:删除',
  `function_type` tinyint(4) DEFAULT NULL COMMENT '功能类别：1：对应宜搜品牌广告功能 2：对应宜搜开屏广告功能 3:对应宜搜原生广告 4：对应自有广告排期（针对排期设定的媒体信息）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Table structure for table `t_es_media_position` */

CREATE TABLE `t_es_media_position` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `es_mediaid` int(4) DEFAULT NULL COMMENT '品牌广告投放位置对应媒体t_es_media表中的id ',
  `name` varchar(255) DEFAULT NULL COMMENT '品牌广告投放位置对应投放位名称',
  `deleteflag` tinyint(4) DEFAULT '1' COMMENT '删除标记 1:正常 2:删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Table structure for table `t_es_openscreen_ad` */

CREATE TABLE `t_es_openscreen_ad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(255) NOT NULL COMMENT '广告名称',
  `imagepath` varchar(1024) DEFAULT NULL COMMENT '用户上传图片到服务器的路径地址',
  `url` varchar(1024) DEFAULT NULL COMMENT '访问Url地址',
  `adstat` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：有效 1：暂停 2：时间结束 3：分日暂停 4：总量结束',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '该广告的创建时间',
  `lasttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次修改的时间',
  `deleteflag` tinyint(4) DEFAULT '1' COMMENT '1:正常2:删除',
  `begindate` datetime DEFAULT NULL COMMENT '开始时间 ',
  `enddate` datetime DEFAULT NULL COMMENT '结束时间 默认当前时间',
  `opuserid` int(4) DEFAULT NULL COMMENT '修改物料时的操作者id',
  `mediaid` int(4) DEFAULT NULL COMMENT '媒体id',
  `es_mediaid` int(4) DEFAULT NULL COMMENT '对应投放位置的媒体 对应t_es_media 中id',
  `click_type` int(4) DEFAULT '1' COMMENT '点击类型：仅展示(0)，打开网页(1)，直接下载(2)',
  `os` varchar(20) DEFAULT '1' COMMENT '操作系统选择 android: 1 默认 ,ios: 2 ,android+ios:1;2（无用字段）',
  `putnum` int(11) DEFAULT '-1' COMMENT '投放量（默认为-1，不限制投放量）',
  `puttype` int(4) DEFAULT '0' COMMENT '投放类型：0:无，1：展示，2：点击，3：激活',
  `regincodes` varchar(3500) DEFAULT '11;12;13;14;15;21;22;23;31;32;33;34;35;36;37;41;42;43;44;46;45;50;51;52;53;54;61;62;63;64;65;81;91;97;71;0' COMMENT '投放地域省市编码',
  `packagename` varchar(255) DEFAULT NULL COMMENT '下载类包名',
  `downappflag` tinyint(4) DEFAULT '0' COMMENT '直接下载选项判断 0：输入url 1：上传包',
  `netprotocol` varchar(16) DEFAULT '3;5;4;1;7' COMMENT '网络协议 1:2G 3:wifi 4:3G 5:4G 7:其他  全选1;3;4;5;7',
  `showlimit` int(4) DEFAULT '0' COMMENT '展现频控限制 默认为0 无限制',
  `clicklimit` int(4) DEFAULT '0' COMMENT '点击频控限制 默认为0 无限制',
  `saleid` int(4) DEFAULT NULL COMMENT '销售部门id',
  `cooperation_mode` int(4) DEFAULT '1' COMMENT '合作模式 默认1：CPM 2：CPT 3：CPC',
  `price` int(11) DEFAULT '0' COMMENT '单价（分）',
  `tfday_mode` int(4) DEFAULT '0' COMMENT '分日投放 默认0：否 1：是',
  `tfavg_num` int(11) DEFAULT '0' COMMENT '日均投放',
  `autostop` tinyint(1) DEFAULT '0',
  `create_opuserid` int(4) DEFAULT NULL COMMENT '新建物料时的操作者id',
  `imagepath_2` varchar(1024) DEFAULT NULL,
  `click_type_2` int(4) DEFAULT '1',
  `url_2` varchar(1024) DEFAULT NULL,
  `packagename_2` varchar(255) DEFAULT NULL,
  `downappflag_2` tinyint(4) DEFAULT '0',
  `imagepath_3` varchar(1024) DEFAULT NULL,
  `click_type_3` int(4) DEFAULT '1',
  `url_3` varchar(1024) DEFAULT NULL,
  `packagename_3` varchar(255) DEFAULT NULL,
  `downappflag_3` tinyint(4) DEFAULT '0',
  `imagepath_4` varchar(1024) DEFAULT NULL,
  `click_type_4` int(4) DEFAULT '1',
  `url_4` varchar(1024) DEFAULT NULL,
  `packagename_4` varchar(255) DEFAULT NULL,
  `downappflag_4` tinyint(4) DEFAULT '0',
  `imagepath_5` varchar(1024) DEFAULT NULL,
  `click_type_5` int(4) DEFAULT '1',
  `url_5` varchar(1024) DEFAULT NULL,
  `packagename_5` varchar(255) DEFAULT NULL,
  `downappflag_5` tinyint(4) DEFAULT '0',
  `show_monit_url` varchar(1024) DEFAULT NULL COMMENT '展现监控URL',
  `click_monit_url` varchar(1024) DEFAULT NULL COMMENT '点击监控URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=279 DEFAULT CHARSET=utf8;

/*Table structure for table `t_es_openscreen_schedul` */

CREATE TABLE `t_es_openscreen_schedul` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` char(255) NOT NULL COMMENT '广告名称',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '该广告的创建时间',
  `lasttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次修改的时间',
  `deleteflag` tinyint(4) DEFAULT '1' COMMENT '1:正常2:删除',
  `begindate` datetime DEFAULT NULL COMMENT '开始时间 默认当前时间',
  `enddate` datetime DEFAULT NULL COMMENT '结束时间 ',
  `opuserid` int(4) DEFAULT NULL COMMENT '操作者id',
  `mediaid` int(4) DEFAULT NULL COMMENT '媒体id（前端取值）',
  `es_mediaid` int(4) DEFAULT NULL COMMENT '对应投放位置的媒体 对应t_es_media 中id',
  `click_type` int(4) DEFAULT '1' COMMENT '点击类型：仅展示(0)，打开网页(1)，直接下载(2)',
  `putnum` int(11) DEFAULT '-1' COMMENT '投放量（默认为-1，不限制投放量）',
  `puttype` int(4) DEFAULT '0' COMMENT '投放类型：0:无，1：展示，2：点击，3：激活',
  `saleid` int(4) DEFAULT NULL COMMENT '销售部门id',
  `cooperation_mode` int(4) DEFAULT NULL COMMENT '合作模式 默认1：CPM 2：CPT 3：CPC',
  `price` int(11) DEFAULT NULL COMMENT '单价（分）',
  `tfday_mode` int(4) DEFAULT NULL COMMENT '分日投放 默认0：否 1：是',
  `tfavg_num` int(11) DEFAULT NULL COMMENT '日均投放',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  `remind_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '提醒日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8;

/*Table structure for table `t_es_proto_ad` */

CREATE TABLE `t_es_proto_ad` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` char(255) NOT NULL COMMENT '广告名称',
  `imagepath` varchar(1024) DEFAULT NULL COMMENT '用户上传详情大图地址',
  `url` varchar(1024) DEFAULT NULL COMMENT '访问Url地址',
  `adstat` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：有效 1：暂停',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '该广告的创建时间',
  `lasttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次修改的时间',
  `deleteflag` tinyint(4) DEFAULT '1' COMMENT '1:正常2:删除',
  `begindate` datetime DEFAULT NULL COMMENT '开始时间 默认当前时间',
  `enddate` datetime DEFAULT NULL COMMENT '结束时间 ',
  `opuserid` int(4) DEFAULT NULL COMMENT '修改物料时的操作者id',
  `mediaid` int(4) DEFAULT NULL COMMENT '媒体id（前端取值）',
  `es_mediaid` int(4) DEFAULT NULL COMMENT '对应投放位置的媒体 对应t_es_media 中id',
  `es_positionid` int(4) DEFAULT NULL COMMENT '对应投放位置的投放位id 对应t_es_media_position 中id（前端取值）',
  `click_type` int(4) DEFAULT '1' COMMENT '点击类型：仅展示(0)，打开网页(1)，直接下载(2)',
  `os` varchar(20) DEFAULT '1' COMMENT '操作系统选择 android: 1 默认 ,ios: 2 ,android+ios:1;2 （无用字段）',
  `putnum` int(11) DEFAULT '-1' COMMENT '投放量（默认为-1，不限制投放量）',
  `puttype` int(4) DEFAULT '0' COMMENT '投放类型：0:无，1：展示，2：点击，3：激活',
  `packagename` varchar(255) DEFAULT NULL COMMENT '下载类包名',
  `downappflag` tinyint(4) DEFAULT '0' COMMENT '直接下载选项判断 0：输入url 1：上传包',
  `netprotocol` varchar(16) DEFAULT '3;5;4;1;7' COMMENT '网络协议 1:2G 3:wifi 4:3G 5:4G 7:其他  全选1;3;4;5;7',
  `saleid` int(4) DEFAULT NULL COMMENT '销售部门id',
  `cooperation_mode` int(4) DEFAULT '1' COMMENT '合作模式 默认1：CPM 2：CPT 3：CPC',
  `price` int(11) DEFAULT '0' COMMENT '单价（分）',
  `tfday_mode` int(4) DEFAULT '0' COMMENT '分日投放 默认0：否 1：是',
  `tfavg_num` int(11) DEFAULT '0' COMMENT '日均投放',
  `iconpath` varchar(1024) DEFAULT NULL COMMENT '上传icon图片到服务器的路径',
  `title` varchar(255) DEFAULT NULL COMMENT '广告标题',
  `des` varchar(512) DEFAULT NULL COMMENT '广告描述',
  `adstyle` int(4) DEFAULT '1' COMMENT '物料样式：1：icon，2：图片',
  `title_3` varchar(255) DEFAULT NULL COMMENT '广告标题_物料2',
  `des_3` varchar(1024) DEFAULT NULL COMMENT '广告描述_物料3',
  `iconpath_3` varchar(1024) DEFAULT NULL COMMENT '上传icon图片到服务器的路径_物料3',
  `imagepath_3` varchar(1024) DEFAULT NULL COMMENT '用户上传详情大图地址_物料3',
  `click_type_3` int(4) DEFAULT NULL COMMENT '点击类型：仅展示(0)，打开网页(1)，直接下载(2)_物料3',
  `url_3` varchar(1024) DEFAULT NULL COMMENT '访问Url地址_物料3',
  `packagename_3` varchar(255) DEFAULT NULL COMMENT '下载类包名_物料3',
  `title_2` varchar(255) DEFAULT NULL COMMENT '广告标题_物料2',
  `des_2` varchar(1024) DEFAULT NULL COMMENT '广告描述_物料2',
  `iconpath_2` varchar(1024) DEFAULT NULL COMMENT '上传icon图片到服务器的路径_物料2',
  `imagepath_2` varchar(1024) DEFAULT NULL COMMENT '用户上传详情大图地址_物料2',
  `click_type_2` int(4) DEFAULT NULL COMMENT '点击类型：仅展示(0)，打开网页(1)，直接下载(2)_物料2',
  `url_2` varchar(1024) DEFAULT NULL COMMENT '访问Url地址_物料2',
  `packagename_2` varchar(255) DEFAULT NULL COMMENT '下载类包名_物料2',
  `downappflag_2` tinyint(4) DEFAULT '0' COMMENT '物料2 直接下载选项判断 0：输入url 1：上传包',
  `downappflag_3` tinyint(4) DEFAULT '0' COMMENT '物料3 直接下载选项判断 0：输入url 1：上传包',
  `create_opuserid` int(4) DEFAULT NULL COMMENT '新建物料时的操作者id',
  `imagepath_1_2` varchar(1024) DEFAULT NULL COMMENT '物料1图2 ',
  `imagepath_1_3` varchar(1024) DEFAULT NULL COMMENT '物料1图3',
  `imagepath_2_2` varchar(1024) DEFAULT NULL COMMENT '物料2图2 ',
  `imagepath_2_3` varchar(1024) DEFAULT NULL COMMENT '物料2图3 ',
  `imagepath_3_2` varchar(1024) DEFAULT NULL COMMENT '物料3图2 ',
  `imagepath_3_3` varchar(1024) DEFAULT NULL COMMENT '物料3图3 ',
  `mul_img_flag` tinyint(4) DEFAULT '0' COMMENT '单图、3图标识（单图默认:0 、3图：1）',
  `position_index` tinyint(4) DEFAULT '-1' COMMENT '广告投放位置默认为-1,非搜索结果页广告,0,1,2 为投放第一、第二、第三',
  `show_monit_url` varchar(1024) DEFAULT NULL COMMENT '展现监控URL',
  `click_monit_url` varchar(1024) DEFAULT NULL COMMENT '点击监控URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=219 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Table structure for table `t_fixed_rank` */

CREATE TABLE `t_fixed_rank` (
  `fr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `fr_uname` char(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `fr_planname` char(64) NOT NULL DEFAULT '' COMMENT '计划名',
  `fr_unitname` char(64) NOT NULL DEFAULT '' COMMENT '广告组名',
  `fr_word` char(64) NOT NULL DEFAULT '' COMMENT '关键字',
  `fr_wordid` int(11) NOT NULL COMMENT '关键字id',
  `fr_rank` tinyint(4) NOT NULL DEFAULT '1' COMMENT '排名',
  `fr_starttime` datetime NOT NULL COMMENT '开始时间',
  `fr_endtime` datetime NOT NULL COMMENT '结束时间',
  `fr_exclusive` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否排斥; 0非排他，1排他',
  `fr_bid` int(11) NOT NULL COMMENT '固定排名出价',
  `fr_state` tinyint(4) NOT NULL DEFAULT '2' COMMENT '0表示无效，1表示生效，2表示未生效',
  PRIMARY KEY (`fr_id`),
  KEY `idx_state` (`fr_state`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;

/*Table structure for table `t_industry` */

CREATE TABLE `t_industry` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `ordernum` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

/*Table structure for table `t_industry_small` */

CREATE TABLE `t_industry_small` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `pid` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=344 DEFAULT CHARSET=utf8;

/*Table structure for table `t_lockscreen_let` */

CREATE TABLE `t_lockscreen_let` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `midnum` varchar(30) DEFAULT NULL COMMENT '唯一设备号',
  `startnum` int(11) DEFAULT NULL COMMENT '点亮次数，等待解锁次数,锁屏界面点亮次数',
  `shownum` int(11) DEFAULT NULL COMMENT '在点亮页面切换次数,所有物料 （广告+新闻）显示次数',
  `adshow` int(11) DEFAULT NULL COMMENT '广告显示次数',
  `click` int(11) DEFAULT NULL COMMENT '滑动向左浏览物料次数',
  `adclick` int(11) DEFAULT NULL COMMENT '滑动向左浏览广告次数',
  `unlocknum` int(11) DEFAULT NULL COMMENT '滑动向右解锁次数',
  `home` int(11) DEFAULT NULL COMMENT '打开管理界面的次数',
  `down` int(11) DEFAULT NULL COMMENT '下载次数',
  `active` int(11) DEFAULT NULL COMMENT '激活次数',
  `charge` int(11) DEFAULT NULL COMMENT '收入',
  `advanceCharge` int(11) DEFAULT NULL COMMENT '当天预付费用，指付给用户的钱',
  `totalCharge` int(11) DEFAULT NULL COMMENT '用户总收入',
  `buy` int(11) DEFAULT NULL COMMENT '购买次数',
  `actualCharge` int(11) DEFAULT NULL COMMENT '实际购买金额，指用户支付的钱,当天购买商品的金额',
  `cost` int(11) DEFAULT NULL COMMENT '实际支付成本，指购买商品的钱,当天购买商品的成本',
  `dau` int(11) DEFAULT NULL COMMENT '进入商城的用户数',
  `dau2` int(11) DEFAULT NULL COMMENT '每天有交互的用户数（翻页、点击）,上下滑动和左滑操作',
  `costdate` date NOT NULL DEFAULT '0000-00-00' COMMENT '业务时间',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  PRIMARY KEY (`id`),
  KEY `index_query1` (`midnum`,`costdate`),
  KEY `index_query2` (`costdate`)
) ENGINE=InnoDB AUTO_INCREMENT=741813 DEFAULT CHARSET=utf8;

/*Table structure for table `t_mail_histroy` */

CREATE TABLE `t_mail_histroy` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `userid` bigint(11) DEFAULT NULL,
  `info` varchar(500) DEFAULT NULL,
  `mailtype` varchar(10) DEFAULT NULL COMMENT '邮件类型 1:提醒预算 余额 不足',
  `sendtime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userid_mailtype` (`userid`,`mailtype`)
) ENGINE=InnoDB AUTO_INCREMENT=13168 DEFAULT CHARSET=utf8;

/*Table structure for table `t_media` */

CREATE TABLE `t_media` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userid` int(11) DEFAULT NULL COMMENT '账号id',
  `name` varchar(100) DEFAULT NULL COMMENT '媒体名称',
  `category` varchar(500) DEFAULT NULL COMMENT '所属分类',
  `platform` int(4) DEFAULT '1' COMMENT '平台类型 1:android ',
  `quantity` int(4) DEFAULT NULL COMMENT '流量/用户量范围',
  `sex` int(2) DEFAULT '0' COMMENT '0全部性别 1男 2女',
  `age` varchar(100) DEFAULT '0:DFT' COMMENT '1儿童 2少年 3青年 4中年 5老年',
  `url` varchar(1000) DEFAULT NULL COMMENT '网站/下载地址',
  `mediatype` int(4) DEFAULT NULL COMMENT '媒体类型 1：wap 2：app',
  `showadtype` varchar(100) DEFAULT '4:1;2;3;5' COMMENT '接收广告类型 1：横幅广告2：全屏广告3：文本广告4：推送广告 5：插屏广告  101：搜索广告(包括热词，搜索框，商业词触发的广告) 102:词贴中的展示广告',
  `pushfreq` int(4) DEFAULT '4' COMMENT '推送频率 1 2 4 6 8',
  `starthour` int(4) DEFAULT '8' COMMENT '推送开始小时',
  `endhour` int(4) DEFAULT '22' COMMENT '推送结束小时',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `deleteflag` int(11) DEFAULT '1' COMMENT '删除标记 1：正常 2：删除',
  `lasttime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '上次修改时间',
  `mediastat` int(2) DEFAULT '1' COMMENT ' 1 待审核 2 有效   3 未通过 4激活上限下线',
  `baiduappid` varchar(100) DEFAULT NULL COMMENT '百度appid',
  `baiduchargeid` varchar(100) DEFAULT NULL COMMENT '百度计费id',
  `tencentmid` varchar(100) DEFAULT NULL COMMENT '腾讯媒体id',
  `tencentbannerid` varchar(100) DEFAULT NULL COMMENT '腾讯横幅广告位id',
  `tencentplugid` varchar(100) DEFAULT NULL COMMENT '腾讯插屏广告位id',
  `cooperationtpye` int(2) DEFAULT '2' COMMENT '广告合作方式 1搜索框热词合作 2展示广告',
  `vcurrencyname` varchar(100) DEFAULT NULL COMMENT '积分墙虚拟货币名称',
  `vcurrencyexchangerates` int(11) DEFAULT NULL COMMENT '积分墙虚拟货币汇率',
  `limitnumber` int(11) DEFAULT '6' COMMENT '积分墙每台设备每日下载次数限制：默认是0，无限制',
  `setprice` int(10) DEFAULT '0' COMMENT '积分墙媒体单价调整，单位分',
  `wallpointswitch` int(4) DEFAULT '1' COMMENT '积分墙余额显示0:off 1:on',
  `black_ads` varchar(9000) DEFAULT '0:DFT',
  `fixed_ad_rank` varchar(255) DEFAULT '0:DFT',
  `allactivelimit` int(10) DEFAULT '2000' COMMENT '总激活次数上限',
  `isshowpoint` int(4) DEFAULT '1' COMMENT '积分墙有无积分 0：无积分 默认为1：有积分 ',
  `fillup` varchar(255) DEFAULT '0:DFT' COMMENT '无指定广告时进行补余 1：补余 0：默认不补余',
  `unitwhitelist` varchar(255) DEFAULT '0:DFT' COMMENT '媒体指定的需要按比例展现的广告 0:DFT 未指定，无白名单 N:unit1,rate1;...unitN,rateN;',
  `unitwflist` varchar(255) DEFAULT '0:DFT' COMMENT '媒体指定的需要按竞价展现的广告 0:DFT 未指定',
  `baiduplugid` varchar(100) DEFAULT NULL COMMENT '百度api插屏广告位id',
  `packagename` varchar(100) DEFAULT NULL COMMENT '应用程序包名',
  `packageversion` varchar(100) DEFAULT NULL COMMENT '应用程序版本号',
  `appdesc` varchar(400) DEFAULT NULL COMMENT '应用描述',
  `downappurlflag` int(4) DEFAULT '0' COMMENT '判断APP上传或者是下载的标识 0：默认 url 链接，1：上传应用程序',
  `sorttype` int(4) DEFAULT '0' COMMENT '是否按照ECPM自动排序 默认 0：自动排序 、1：手动排序',
  `tencentprotoid` varchar(100) DEFAULT NULL COMMENT '腾讯原生广告位id',
  `baiduopenid` varchar(100) DEFAULT NULL COMMENT '百度开屏广告位id',
  `tencentopenid` varchar(100) DEFAULT NULL COMMENT '腾讯开屏广告位id',
  `baiduprotoid` varchar(100) DEFAULT NULL COMMENT '百度原生广告位id',
  `juxiao360id` varchar(100) DEFAULT NULL COMMENT '360聚效平台媒体id',
  `juxiao360protoid` varchar(100) DEFAULT NULL COMMENT '360聚效平台原生广告位id',
  `baiduprotoappid` varchar(100) DEFAULT NULL COMMENT '百度原生横幅绑定id',
  `baiduprotobannerid` varchar(100) DEFAULT NULL COMMENT '百度原生横幅平台广告位id',
  `baiducooperate` int(4) DEFAULT '2' COMMENT '百度合作 1：合作 默认：2不合作',
  `esprotoid` varchar(100) DEFAULT NULL COMMENT '自有原生广告位id',
  `esprotoappid` varchar(100) DEFAULT NULL COMMENT '自有原生平台id\r\n\r\n\r\n',
  `tencentprotobannerappid` varchar(100) DEFAULT NULL COMMENT '腾讯原生横幅平台id',
  `tencentprotobannerid` varchar(100) DEFAULT NULL COMMENT '腾讯原生横幅平台广告位id\r\n',
  `esprotobannerid` varchar(100) DEFAULT NULL COMMENT '自有原生横幅广告位id',
  UNIQUE KEY `id` (`id`),
  KEY `idx_userid` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=2796 DEFAULT CHARSET=utf8;

/*Table structure for table `t_media_accessratio` */

CREATE TABLE `t_media_accessratio` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '接入比例id',
  `userid` int(11) DEFAULT NULL COMMENT '用户id',
  `mediaid` int(11) DEFAULT NULL COMMENT '媒体id',
  `br_rto` int(4) DEFAULT '100' COMMENT '自有横幅比例',
  `baidu_br_rto` int(4) DEFAULT '0' COMMENT '百度横幅比例',
  `gdt_br_rto` int(4) DEFAULT '0' COMMENT '广点通横幅比例',
  `sn_rto` int(4) DEFAULT '100' COMMENT '自有插屏比例',
  `baidu_sn_rto` int(4) DEFAULT '0' COMMENT '百度插屏比例',
  `gdt_sn_rto` int(4) DEFAULT '0' COMMENT '广点通插屏比例',
  `lasttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `adminid` int(11) DEFAULT NULL COMMENT '操作者id(预留)',
  `gdt_proto_rto` int(4) DEFAULT '0' COMMENT '广点通原生广告比例',
  `baidu_open_rto` int(4) DEFAULT '0' COMMENT '百度开屏比例',
  `gdt_open_rto` int(4) DEFAULT '0' COMMENT '广点通开屏比例',
  `baidu_proto_rto` int(4) DEFAULT '0' COMMENT '百度原生广告比例',
  `juxiao360_proto_rto` int(4) DEFAULT '0' COMMENT '360聚效平台原生广告比例',
  `baiduproto_banner_rto` int(4) DEFAULT '0' COMMENT '百度原生横幅广告比例',
  `esproto_rto` int(4) DEFAULT '0' COMMENT '自有原生比例',
  `gdtproto_banner_rto` int(4) DEFAULT '0' COMMENT '广点通原生横幅比例',
  `dsp_proto_rto` int(4) DEFAULT '0' COMMENT 'dsp原生比例',
  `esproto_banner_rto` int(4) DEFAULT '0' COMMENT '自有原生横幅比例',
  `dsp_banner_rto` int(4) DEFAULT '0' COMMENT 'dsp横幅比例(产品用来接科大讯飞横幅)',
  `dsp_open_rto` int(4) DEFAULT '0' COMMENT 'dsp开屏比例',
  `toutiao_banner_rto` int(4) DEFAULT '0' COMMENT '今日头条横幅比例',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=970 DEFAULT CHARSET=utf8;

/*Table structure for table `t_media_accessratio_history` */

CREATE TABLE `t_media_accessratio_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '接入比例id',
  `userid` int(11) DEFAULT NULL COMMENT '用户id',
  `mediaid` int(11) DEFAULT NULL COMMENT '媒体id',
  `br_rto` int(4) DEFAULT '100' COMMENT '自有横幅比例',
  `baidu_br_rto` int(4) DEFAULT '0' COMMENT '百度横幅比例',
  `gdt_br_rto` int(4) DEFAULT '0' COMMENT '广点通横幅比例',
  `sn_rto` int(4) DEFAULT '100' COMMENT '自有插屏比例',
  `baidu_sn_rto` int(4) DEFAULT '0' COMMENT '百度插屏比例',
  `gdt_sn_rto` int(4) DEFAULT '0' COMMENT '广点通插屏比例',
  `lasttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `adminid` int(11) DEFAULT NULL COMMENT '操作者id(预留)',
  `gdt_proto_rto` int(11) DEFAULT '0' COMMENT '广点通原生比例',
  `baidu_open_rto` int(4) DEFAULT '0' COMMENT '百度开屏比例',
  `gdt_open_rto` int(4) DEFAULT '0' COMMENT '广点通开屏比例',
  `baidu_proto_rto` int(4) DEFAULT '0' COMMENT '百度原生比例',
  `juxiao360_proto_rto` int(4) DEFAULT '0' COMMENT '360聚效原生比例',
  `esproto_rto` int(4) DEFAULT '0' COMMENT '自有原生比例',
  `gdtproto_banner_rto` int(4) DEFAULT '0' COMMENT '广点通原生横幅比例',
  `baiduproto_banner_rto` int(4) DEFAULT '0' COMMENT '百度原生横幅广告比例',
  `dsp_proto_rto` int(4) DEFAULT '0' COMMENT 'dsp原生比例',
  `esproto_banner_rto` int(4) DEFAULT '0' COMMENT '自有原生横幅比例',
  `dsp_banner_rto` int(4) DEFAULT '0' COMMENT 'dsp横幅比例',
  `dsp_open_rto` int(4) DEFAULT '0' COMMENT 'dsp开屏比例',
  `toutiao_banner_rto` int(4) DEFAULT '0' COMMENT '今日头条横幅比例',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2282 DEFAULT CHARSET=utf8;

/*Table structure for table `t_media_adratio` */

CREATE TABLE `t_media_adratio` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '指定广告概率id',
  `mediaid` int(11) DEFAULT NULL COMMENT '媒体id',
  `userid` int(11) DEFAULT NULL COMMENT '用户id',
  `showtype` int(1) DEFAULT NULL COMMENT '广告类型 1:横幅 2:全屏 3:文本 5:插屏',
  `ratio` varchar(255) DEFAULT NULL COMMENT '展现概率 用;分割',
  `adtfflag` int(11) DEFAULT '0' COMMENT '媒体指定的展现的广告标示 0: 按比例展现，1 自由竞价展现',
  `lasttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `fillup` int(1) DEFAULT '1' COMMENT '无指定广告时进行补余 1：默认补余 0：不补余',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

/*Table structure for table `t_media_adratio_history` */

CREATE TABLE `t_media_adratio_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '指定广告概率id',
  `mediaid` int(11) DEFAULT NULL COMMENT '媒体id',
  `userid` int(11) DEFAULT NULL COMMENT '用户id',
  `showtype` int(1) DEFAULT NULL COMMENT '广告类型 1:横幅 2:全屏 3:文本 5:插屏',
  `ratio` varchar(255) DEFAULT NULL COMMENT '展现概率 用;分割',
  `adtfflag` int(11) DEFAULT '0' COMMENT '媒体指定的展现的广告标示 0: 按比例展现，1 自由竞价展现',
  `lasttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `fillup` int(1) DEFAULT '1' COMMENT '无指定广告时进行补余 1：默认补余 0：不补余',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=330 DEFAULT CHARSET=utf8;

/*Table structure for table `t_media_category` */

CREATE TABLE `t_media_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) DEFAULT NULL COMMENT '类别名称',
  `code` int(4) DEFAULT NULL COMMENT '编码',
  `ordernum` int(4) DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Table structure for table `t_media_income` */

CREATE TABLE `t_media_income` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL COMMENT '媒体联盟用户id',
  `mediaid` int(11) DEFAULT NULL COMMENT '媒体id',
  `adtype` int(4) DEFAULT NULL COMMENT '广告类型 1：横幅广告 2：全屏广告3：文本广告 4：推送广告  5：插屏广告 6:积分墙广告 101：搜索广告(包括热词，搜索框，商业词触发的广告) 102:词贴中的展示广告',
  `showrate` double DEFAULT '1' COMMENT '展现量扣量比',
  `clickrate` double DEFAULT '1' COMMENT '点击量扣量比',
  `activerate` double DEFAULT '1' COMMENT '积分墙激活量比',
  `discount` double DEFAULT '1' COMMENT '收入分成扣量比',
  `shownum` int(11) DEFAULT NULL COMMENT '展现量',
  `click` int(11) DEFAULT NULL COMMENT '点击量',
  `income` int(12) DEFAULT '0' COMMENT '总收入 单位分',
  `active` int(12) DEFAULT NULL COMMENT '积分墙激活量',
  `mediaincome` int(12) DEFAULT NULL COMMENT '媒体今日收入 单位分',
  `easouincome` int(12) DEFAULT NULL COMMENT '宜搜今日收入 单位分',
  `date` date NOT NULL DEFAULT '0000-00-00' COMMENT '收入日期',
  `baiduappid` varchar(100) DEFAULT NULL COMMENT '百度appid',
  `baiduchargeid` varchar(100) DEFAULT NULL COMMENT '百度计费id',
  `tencentmid` varchar(100) DEFAULT NULL COMMENT '腾讯媒体id',
  `tencentbannerid` varchar(100) DEFAULT NULL COMMENT '腾讯横幅广告位id',
  `tencentplugid` varchar(100) DEFAULT NULL COMMENT '腾讯插屏广告位id',
  `source` int(4) DEFAULT '1' COMMENT '来源 1:宜搜自己统计 2:百度数据手工录入 3:艾德思奇数据 4:腾讯的广点通 5:百灵欧拓',
  `publish` int(2) DEFAULT '0' COMMENT '是否发布 0未发布 1已发布',
  `promotiontype` int(2) DEFAULT '2' COMMENT '推广合作类别 1:搜索广告合作 2:展示广告合作',
  `cid` varchar(100) DEFAULT NULL COMMENT 'cid',
  `status` int(2) DEFAULT '0' COMMENT '提款状态 0：未提款 1：已提款',
  `hiddentax` int(4) DEFAULT '0' COMMENT '暗扣税点',
  `baiduplugid` varchar(100) DEFAULT NULL COMMENT '百度api插屏id',
  `tencentprotoid` varchar(100) DEFAULT NULL COMMENT '腾讯原生广告位id',
  `baiduopenid` varchar(100) DEFAULT NULL COMMENT '百度开屏广告位id',
  `tencentopenid` varchar(100) DEFAULT NULL COMMENT '腾讯开屏广告位id',
  `baiduprotoid` varchar(100) DEFAULT NULL COMMENT '百度原生广告位id',
  `juxiao360id` varchar(100) DEFAULT NULL COMMENT '360聚效平台id',
  `juxiao360protoid` varchar(100) DEFAULT NULL COMMENT '360原生广告位id',
  PRIMARY KEY (`id`,`date`),
  KEY `index_query1` (`userid`,`date`)
) ENGINE=InnoDB AUTO_INCREMENT=1357851 DEFAULT CHARSET=utf8
/*!50100 PARTITION BY LIST (MONTH(DATE))
SUBPARTITION BY HASH (DAYOFWEEK(DATE)-1)
SUBPARTITIONS 7
(PARTITION m1 VALUES IN (1) ENGINE = InnoDB,
 PARTITION m2 VALUES IN (2) ENGINE = InnoDB,
 PARTITION m3 VALUES IN (3) ENGINE = InnoDB,
 PARTITION m4 VALUES IN (4) ENGINE = InnoDB,
 PARTITION m5 VALUES IN (5) ENGINE = InnoDB,
 PARTITION m6 VALUES IN (6) ENGINE = InnoDB,
 PARTITION m7 VALUES IN (7) ENGINE = InnoDB,
 PARTITION m8 VALUES IN (8) ENGINE = InnoDB,
 PARTITION m9 VALUES IN (9) ENGINE = InnoDB,
 PARTITION m10 VALUES IN (10) ENGINE = InnoDB,
 PARTITION m11 VALUES IN (11) ENGINE = InnoDB,
 PARTITION m12 VALUES IN (12) ENGINE = InnoDB) */;

/*Table structure for table `t_media_income_admin` */

CREATE TABLE `t_media_income_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL COMMENT '媒体联盟用户id',
  `mediaid` int(11) DEFAULT NULL COMMENT '媒体id',
  `adtype` int(4) DEFAULT NULL COMMENT '广告类型 1：横幅广告 2：全屏广告3：文本广告 4：推送广告  5：插屏广告 101：搜索广告(包括热词，搜索框，商业词触发的广告) 102:词贴中的展示广告 6：积分墙广告',
  `showrate` double DEFAULT '1' COMMENT '展现量扣量比',
  `clickrate` double DEFAULT '1' COMMENT '点击量扣量比',
  `activerate` double DEFAULT '1' COMMENT '激活量比',
  `discount` double DEFAULT '1' COMMENT '收入分成扣量比',
  `shownum` int(11) DEFAULT NULL COMMENT '展现量',
  `click` int(11) DEFAULT NULL COMMENT '点击量',
  `income` int(12) DEFAULT '0' COMMENT '总收入 单位分',
  `down` int(12) DEFAULT '0' COMMENT '积分墙下载数量',
  `active` int(12) DEFAULT '0' COMMENT '积分墙激活数量',
  `entry` int(12) DEFAULT '0' COMMENT '积分墙入口数量',
  `mediaincome` int(12) DEFAULT NULL COMMENT '媒体今日收入 单位分',
  `easouincome` int(12) DEFAULT NULL COMMENT '宜搜今日收入 单位分',
  `date` date NOT NULL DEFAULT '0000-00-00' COMMENT '收入日期',
  `appshow` int(11) DEFAULT NULL COMMENT 'ads统计展现量',
  `appclick` int(11) DEFAULT NULL COMMENT 'ads统计点击量',
  `appinvoke` int(11) DEFAULT NULL COMMENT 'ads统计调用次数',
  `appfailure` int(11) DEFAULT NULL COMMENT 'ads统计失败次数',
  `baiduappid` varchar(100) DEFAULT NULL COMMENT '百度appid',
  `baiduchargeid` varchar(100) DEFAULT NULL COMMENT '百度计费id',
  `tencentmid` varchar(100) DEFAULT NULL COMMENT '腾讯媒体id',
  `tencentbannerid` varchar(100) DEFAULT NULL COMMENT '腾讯横幅广告位id',
  `tencentplugid` varchar(100) DEFAULT NULL COMMENT '腾讯插屏广告位id',
  `source` int(4) DEFAULT '1' COMMENT '来源 1:宜搜自己统计 2:百度数据手工录入 3:艾德思奇数据 4:腾讯的广点通 5:百灵欧拓',
  `publish` int(2) DEFAULT '0' COMMENT '是否发布 0未发布 1已发布',
  `promotiontype` int(2) DEFAULT '2' COMMENT '推广合作类别 1:搜索广告合作 2:展示广告合作',
  `cid` varchar(100) DEFAULT NULL COMMENT 'cid',
  `inserttime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `hiddentax` int(4) DEFAULT '0' COMMENT '暗扣税点',
  `baiduplugid` varchar(100) DEFAULT NULL COMMENT '百度api插屏id',
  `medianame` varchar(100) DEFAULT NULL COMMENT '媒体名称',
  `mediatype` int(4) DEFAULT NULL COMMENT '媒体类型 1：wap 2：app',
  `username` varchar(100) DEFAULT NULL COMMENT '用户名',
  `tencentprotoid` varchar(100) DEFAULT NULL COMMENT '腾讯原生广告位id',
  `baiduopenid` varchar(100) DEFAULT NULL COMMENT '百度开屏广告位id',
  `tencentopenid` varchar(100) DEFAULT NULL COMMENT '腾讯开屏广告位id',
  `baiduprotoid` varchar(100) DEFAULT NULL COMMENT '百度原生广告位id',
  `juxiao360id` varchar(100) DEFAULT NULL COMMENT '360聚效id',
  `juxiao360protoid` varchar(100) DEFAULT NULL COMMENT '360聚效原生广告位id',
  PRIMARY KEY (`id`),
  KEY `index_query1` (`userid`,`adtype`,`shownum`,`date`,`promotiontype`),
  KEY `index_update` (`userid`,`mediaid`,`adtype`,`date`,`source`,`promotiontype`,`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=2511657 DEFAULT CHARSET=utf8;

/*Table structure for table `t_media_income_let` */

CREATE TABLE `t_media_income_let` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL COMMENT '媒体联盟用户id',
  `mediaid` int(11) DEFAULT NULL COMMENT '媒体id',
  `adtype` int(4) DEFAULT NULL COMMENT '广告类型 1：横幅广告 2：全屏广告3：文本广告 4：推送广告  5：插屏广告',
  `showrate` double DEFAULT '1' COMMENT '展现量扣量比',
  `clickrate` double DEFAULT '1' COMMENT '点击量扣量比',
  `discount` double DEFAULT '1' COMMENT '收入分成扣量比',
  `shownum` int(11) DEFAULT NULL COMMENT '展现量',
  `click` int(11) DEFAULT NULL COMMENT '点击量',
  `income` int(12) DEFAULT '0' COMMENT '总收入 单位分',
  `mediaincome` int(12) DEFAULT NULL COMMENT '媒体今日收入 单位分',
  `easouincome` int(12) DEFAULT NULL COMMENT '宜搜今日收入 单位分',
  `date` date NOT NULL DEFAULT '0000-00-00' COMMENT '收入日期',
  `baiduappid` varchar(100) DEFAULT NULL COMMENT '百度appid',
  `baiduchargeid` varchar(100) DEFAULT NULL COMMENT '百度计费id',
  `source` int(4) DEFAULT '1' COMMENT '来源 1:宜搜自己统计 2:百度数据手工录入',
  `publish` int(2) DEFAULT '0' COMMENT '是否发布 0未发布 1已发布',
  PRIMARY KEY (`id`,`date`)
) ENGINE=InnoDB AUTO_INCREMENT=507 DEFAULT CHARSET=utf8
/*!50100 PARTITION BY LIST (MONTH(DATE))
SUBPARTITION BY HASH (DAYOFWEEK(DATE)-1)
SUBPARTITIONS 7
(PARTITION m1 VALUES IN (1) ENGINE = InnoDB,
 PARTITION m2 VALUES IN (2) ENGINE = InnoDB,
 PARTITION m3 VALUES IN (3) ENGINE = InnoDB,
 PARTITION m4 VALUES IN (4) ENGINE = InnoDB,
 PARTITION m5 VALUES IN (5) ENGINE = InnoDB,
 PARTITION m6 VALUES IN (6) ENGINE = InnoDB,
 PARTITION m7 VALUES IN (7) ENGINE = InnoDB,
 PARTITION m8 VALUES IN (8) ENGINE = InnoDB,
 PARTITION m9 VALUES IN (9) ENGINE = InnoDB,
 PARTITION m10 VALUES IN (10) ENGINE = InnoDB,
 PARTITION m11 VALUES IN (11) ENGINE = InnoDB,
 PARTITION m12 VALUES IN (12) ENGINE = InnoDB) */;

/*Table structure for table `t_media_outcome` */

CREATE TABLE `t_media_outcome` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userid` int(11) DEFAULT NULL COMMENT '媒体用户id',
  `paymode` int(4) DEFAULT NULL COMMENT '1转账到推广账户 2 提现 3 团队结算',
  `value` int(11) DEFAULT NULL COMMENT '支出金额 单位分',
  `real_value` int(11) DEFAULT NULL COMMENT '实际支出金额  单位分',
  `status` int(4) DEFAULT NULL COMMENT '支出状态 1 申请中 2 已到账',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '支出时间',
  `tax` int(4) DEFAULT '6' COMMENT '税点 整数，显示除以100',
  `cooperate_value` int(11) DEFAULT '0' COMMENT '百度合作金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4258 DEFAULT CHARSET=utf8;

/*Table structure for table `t_media_outcome_week` */

CREATE TABLE `t_media_outcome_week` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `starttime` date NOT NULL DEFAULT '0000-00-00' COMMENT '起始时间',
  `endtime` date DEFAULT NULL COMMENT '结束时间',
  `value` int(12) DEFAULT NULL COMMENT '支出金额',
  `outcomeid` int(11) DEFAULT NULL COMMENT '支出表外键',
  `income` int(12) DEFAULT NULL COMMENT '总收入',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7869 DEFAULT CHARSET=utf8;

/*Table structure for table `t_media_search_config` */

CREATE TABLE `t_media_search_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '搜索热词配置表id',
  `userid` int(11) DEFAULT NULL COMMENT '搜索热词对应的用户id',
  `mediaid` int(11) DEFAULT NULL COMMENT '搜索热词对应的媒体id',
  `cid` varchar(100) DEFAULT NULL COMMENT '搜索 1 热词 2 ',
  `searchbordercolor` varchar(100) DEFAULT NULL COMMENT '搜索框边框的颜色',
  `easoulogo` int(11) DEFAULT NULL COMMENT '搜索框的easou的logo图片 0 无 1 有',
  `pushtype` int(11) DEFAULT NULL COMMENT '投放方式1 搜索 2 自动 3 选择热词',
  `hotword` varchar(100) DEFAULT NULL COMMENT '用户自己选择的热词',
  `linenum` int(11) DEFAULT NULL COMMENT '热词显示的行数',
  `fontfamily` varchar(100) DEFAULT NULL COMMENT '热词的字体',
  `fontsize` varchar(100) DEFAULT NULL COMMENT '热词的字大小',
  `divborder` varchar(100) DEFAULT NULL COMMENT '热词的div边框色',
  `backgroundcolor` varchar(100) DEFAULT NULL COMMENT '热词的div背景色',
  `fontcolor` varchar(100) DEFAULT NULL COMMENT '热词的字体颜色',
  `fontweight` varchar(100) DEFAULT NULL COMMENT '热词的字是否加粗',
  `underline` varchar(100) DEFAULT NULL COMMENT '热词的字是否带下划线',
  `adshow` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Table structure for table `t_media_user` */

CREATE TABLE `t_media_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userid` int(11) DEFAULT NULL COMMENT '账号id',
  `accountname` varchar(100) DEFAULT NULL COMMENT '提现银行户名',
  `account` varchar(100) DEFAULT NULL COMMENT '银行卡号',
  `bankcode` int(6) DEFAULT NULL COMMENT '开户行code',
  `bankcity` varchar(300) DEFAULT NULL COMMENT '开户行所在地',
  `subbankinfo` varchar(500) DEFAULT NULL COMMENT '支行信息',
  `income` int(12) DEFAULT '0' COMMENT '总收入 冗余字段 单位分',
  `outcome1` int(12) DEFAULT '0' COMMENT '总提现支出 冗余字段 单位分',
  `outcome2` int(12) DEFAULT '0' COMMENT '总推广支出 冗余字段 单位分',
  `discount` int(4) DEFAULT '50' COMMENT '分成比例 整数，显示除以100',
  `discountadmin` int(11) DEFAULT NULL COMMENT '设置分成管理员id',
  `discounttime` datetime DEFAULT NULL COMMENT '分成设置时间',
  `userstat` int(4) DEFAULT '1' COMMENT '用户状态 1 待审核 2 有效   3 未通过',
  `verifyadmin` int(11) DEFAULT NULL COMMENT '审核人员id',
  `verifytime` datetime DEFAULT NULL COMMENT '审核时间',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lasttime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `applytime` datetime DEFAULT NULL COMMENT '申请提现时间 冗余字段',
  `identity` int(11) DEFAULT '2' COMMENT '媒体主身份 1个人 2公司',
  `role` varchar(4) DEFAULT NULL,
  `role_wap_search` int(2) DEFAULT '0' COMMENT 'wap推广搜索框合作权限 0无 1有',
  `role_wap_hot` int(2) DEFAULT '0' COMMENT 'wap推广热词合作权限 0无 1有',
  `role_app_search` int(2) DEFAULT '0' COMMENT 'app推广搜索框合作权限 0无 1有',
  `role_app_hot` int(2) DEFAULT '0' COMMENT 'app推广搜热词作权限 0无 1有',
  `tax` int(4) DEFAULT '6' COMMENT '税点 整数，显示除以100',
  `hiddentax` int(4) DEFAULT '0' COMMENT '暗扣税点 整数，显示除以100',
  `hiddentaxtime` datetime DEFAULT NULL COMMENT '开启暗扣时间',
  `sysbussnis_id` int(4) DEFAULT '0' COMMENT '后台商务id 0：无（默认） 后台商务人员方便查看自己媒体主的数据',
  `mediausertype` int(4) DEFAULT '0' COMMENT '媒体用户类型:0：默认（无）1：app 2：wap',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2261 DEFAULT CHARSET=utf8;

/*Table structure for table `t_multi_user` */

CREATE TABLE `t_multi_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL COMMENT '用户id',
  `childaccounts` varchar(280) DEFAULT NULL COMMENT '子账户名,用逗号分隔',
  `createtime` datetime DEFAULT NULL,
  `lasttime` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `opuserid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Table structure for table `t_plan` */

CREATE TABLE `t_plan` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(10) unsigned NOT NULL COMMENT '创建用户id',
  `channel` int(11) DEFAULT NULL COMMENT '推广频道',
  `begindate` bigint(20) DEFAULT NULL COMMENT '开始时间 默认当前时间',
  `enddate` bigint(20) DEFAULT NULL COMMENT '结束时间 默认为0无结束时间',
  `cyc` char(32) DEFAULT NULL COMMENT 'N为数量，后面按7 * 24的矩阵存储，默认0:DFT',
  `region` char(16) DEFAULT NULL COMMENT 'N:reg1;reg2…regn\r\n默认0:DFT是所有地域\r\n',
  `exactnegative` varchar(1024) DEFAULT NULL COMMENT 'N:str1;str2…strn，默认0:DFT',
  `negative` varchar(1024) DEFAULT NULL COMMENT 'N:str1;str2…strn，默认0:DFT',
  `budget` int(11) DEFAULT NULL COMMENT '计划层预算 单位：分',
  `value` int(11) DEFAULT NULL COMMENT '消费金额 单位：分',
  `planstat` tinyint(4) NOT NULL COMMENT '计划状态\r\n1 有效\r\n2 暂停推广\r\n',
  `showprob` tinyint(4) NOT NULL COMMENT '展现规则 1轮选，2优选',
  `showratio` int(11) DEFAULT NULL COMMENT '展现系数',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `lasttime` datetime NOT NULL COMMENT '最后一次修改时间',
  `planname` char(64) NOT NULL COMMENT '计划名称',
  `budgetstat` tinyint(4) NOT NULL DEFAULT '2' COMMENT '资金状态\r\n1 预算充足\r\n2 预算不足\r\n',
  `regionmode` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1 用账户定义的地域\r\n2 用计划定义的地域\r\n',
  `budgetday` tinyint(3) unsigned NOT NULL DEFAULT '4' COMMENT '预算周期 默认按天\r\n1 天\r\n2 周\r\n3 无预算\r\n4 使用用户层预算\r\n',
  `outcome` int(10) unsigned zerofill NOT NULL DEFAULT '0000000000' COMMENT '总支出',
  `deliveryChannel` varchar(20) DEFAULT '0:DFT' COMMENT '投放频道',
  `operator` varchar(20) DEFAULT '0:DFT' COMMENT '运营商网络',
  `deleteflag` tinyint(4) DEFAULT '1' COMMENT '1:正常2:删除',
  `codes` varchar(3500) DEFAULT '999999' COMMENT '推广地域省市编码',
  `netprotocol` varchar(16) DEFAULT '0:DFT' COMMENT '网络协议',
  `tfwl` varchar(50) DEFAULT '2:2;1' COMMENT '投放网络 1 搜索网络 2 内容网络 3 展示广告网络 ',
  `promotion_type` int(4) DEFAULT '1' COMMENT '推广类型1：搜索推广 2：展示推广',
  `show_budget` varchar(20) DEFAULT '0' COMMENT '展现量数目预算',
  `industry` varchar(200) DEFAULT '0:DFT' COMMENT '投放行业 num:id;id;id ";"分隔 ',
  `media` varchar(200) DEFAULT '0:DFT' COMMENT '投放媒体（预留） num:id;id;id ";"分隔 ',
  `payment_type` int(2) DEFAULT '0' COMMENT '付费方式 1：cpc付费 2：cpm付费',
  `showcount` bigint(20) DEFAULT '0' COMMENT '每日展示次数',
  `showtfwl` varchar(200) DEFAULT '1:1' COMMENT '展示推广投放网络 宜搜 app:1  wap:2 联盟 app:3  wap:4',
  `showos` varchar(200) DEFAULT '0:DFT' COMMENT '1 Andriod 2 iPhone 3 Windows Phone 4 其他',
  `probability` int(8) DEFAULT '1000' COMMENT '1000：展现概率100% 500：展现概率50% 250： 展现概率25% 125：展现概率12.5%',
  `custf_type` int(4) DEFAULT '0' COMMENT '大客户投放 0:正常投放 1:指定投放',
  PRIMARY KEY (`id`),
  KEY `idx_userid` (`userid`),
  KEY `idx_lasttime` (`lasttime`),
  KEY `idx_planstat` (`planstat`)
) ENGINE=InnoDB AUTO_INCREMENT=165261 DEFAULT CHARSET=utf8;

/*Table structure for table `t_province` */

CREATE TABLE `t_province` (
  `id` int(10) unsigned NOT NULL,
  `pname` char(32) NOT NULL COMMENT '省份名称',
  `orderby` int(11) DEFAULT '1' COMMENT '排序数字',
  `bigpart` tinyint(4) DEFAULT '1' COMMENT '所在区域',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_province_city` */

CREATE TABLE `t_province_city` (
  `pid` varchar(100) NOT NULL,
  `code` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `zone` varchar(500) DEFAULT NULL,
  `ip` varchar(500) DEFAULT NULL,
  `temp1` varchar(500) DEFAULT NULL,
  `hascity` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_real_income` */

CREATE TABLE `t_real_income` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `income` int(11) DEFAULT NULL COMMENT '总金额',
  `real_income` int(11) DEFAULT NULL COMMENT '实际收入金额',
  `free_income` int(11) DEFAULT NULL COMMENT '赠送金额',
  `real_income_percent` float(5,4) DEFAULT NULL COMMENT '实收比',
  `agent_id` int(11) DEFAULT NULL COMMENT '操作人代理商id',
  `customer_id` int(11) DEFAULT NULL COMMENT '客户id',
  `type` int(4) DEFAULT NULL COMMENT '0：普通，1：汇总',
  `save_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_custom_type_savetime` (`customer_id`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=10756509 DEFAULT CHARSET=utf8;

/*Table structure for table `t_sdk_update_log` */

CREATE TABLE `t_sdk_update_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'sdk文档id',
  `title` varchar(100) DEFAULT NULL COMMENT 'sdk文档title',
  `file_name` varchar(200) DEFAULT NULL COMMENT 'sdk文件名称',
  `url` varchar(100) DEFAULT NULL COMMENT 'sdk文件的下载url',
  `sdk_version` varchar(32) DEFAULT NULL COMMENT 'sdk文件版本',
  `size` char(16) DEFAULT NULL COMMENT 'sdk文件大小',
  `sdk_date` datetime DEFAULT NULL COMMENT 'sdk文件上传时间',
  `file_desc` text COMMENT 'sdk文件的描述',
  `version_log` text COMMENT 'sdk文件版本描述',
  `sdk_type` char(4) DEFAULT NULL COMMENT 'sdk文件类型 1.Easou Android SDK 2.Easou推送广告 Android SDK 及文档(zip) 3.Easou js 文档(docx)',
  `sdk_release` char(4) DEFAULT NULL COMMENT '发布',
  `sort` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

/*Table structure for table `t_showclick_log` */

CREATE TABLE `t_showclick_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `costdate` datetime NOT NULL COMMENT '消费日期 发送数据的时间',
  `userid` int(10) unsigned NOT NULL COMMENT '用户id',
  `planid` int(10) unsigned NOT NULL COMMENT '相关计划id',
  `unitid` int(10) unsigned NOT NULL COMMENT '相关广告组id',
  `wordid` int(10) unsigned NOT NULL COMMENT '相关关键词id',
  `adid` int(10) unsigned NOT NULL COMMENT '相关广告id',
  `shownum` int(11) NOT NULL DEFAULT '0' COMMENT '展现次数',
  `click` int(11) NOT NULL DEFAULT '0' COMMENT '点击次数',
  `charge` int(11) NOT NULL DEFAULT '0' COMMENT '消费额 单位：分',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1正常',
  `daypart` tinyint(4) DEFAULT '24' COMMENT '0~23：代表一天中的24小时\r\n24：代表全天\r\n',
  `region` tinyint(4) DEFAULT '0' COMMENT '0：代表全地域\r\n1~35 代表全国各省或者海外\r\n',
  `savetime` datetime DEFAULT NULL COMMENT '数据保存时间',
  `bidwordid` int(10) DEFAULT NULL COMMENT '拍卖词id',
  `bidcharge` int(11) DEFAULT NULL COMMENT '出价总额',
  `clicktype` int(11) DEFAULT '0' COMMENT '点击类型',
  `promotiontype` int(4) DEFAULT '1' COMMENT '推广类型1：搜索推广 2：展示推广',
  `paytype` int(4) DEFAULT '1' COMMENT '付费类型:1 CPC付费 2 CPM付费',
  PRIMARY KEY (`id`,`costdate`),
  KEY `idx_userid` (`userid`),
  KEY `idx_planid` (`planid`),
  KEY `idx_unitid` (`unitid`),
  KEY `idx_wordid` (`wordid`),
  KEY `idx_ideaid` (`adid`),
  KEY `idx_costdate` (`costdate`),
  KEY `idx_1` (`costdate`,`wordid`,`adid`,`clicktype`),
  KEY `idx_wordid_costdate` (`costdate`,`bidwordid`),
  KEY `idx_bidword` (`bidwordid`),
  KEY `idx_winfoid_costdate` (`costdate`,`wordid`),
  KEY `idx_uid_winfoid` (`userid`,`wordid`)
) ENGINE=InnoDB AUTO_INCREMENT=204211859 DEFAULT CHARSET=utf8
/*!50100 PARTITION BY HASH (MONTH(costdate)-1)
PARTITIONS 12 */;

/*Table structure for table `t_showclick_log_test` */

CREATE TABLE `t_showclick_log_test` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `costdate` datetime NOT NULL COMMENT '消费日期 发送数据的时间',
  `userid` int(10) unsigned NOT NULL COMMENT '用户id',
  `planid` int(10) unsigned NOT NULL COMMENT '相关计划id',
  `unitid` int(10) unsigned NOT NULL COMMENT '相关广告组id',
  `wordid` int(10) unsigned NOT NULL COMMENT '相关关键词id',
  `adid` int(10) unsigned NOT NULL COMMENT '相关广告id',
  `shownum` int(11) NOT NULL DEFAULT '0' COMMENT '展现次数',
  `click` int(11) NOT NULL DEFAULT '0' COMMENT '点击次数',
  `charge` int(11) NOT NULL DEFAULT '0' COMMENT '消费额 单位：分',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1正常',
  `daypart` tinyint(4) DEFAULT '24' COMMENT '0~23：代表一天中的24小时\r\n24：代表全天\r\n',
  `region` tinyint(4) DEFAULT '0' COMMENT '0：代表全地域\r\n1~35 代表全国各省或者海外\r\n',
  `savetime` datetime DEFAULT NULL COMMENT '数据保存时间',
  `bidwordid` int(10) DEFAULT NULL COMMENT '拍卖词id',
  `bidcharge` int(11) DEFAULT NULL COMMENT '出价总额',
  `clicktype` int(11) DEFAULT '0' COMMENT '点击类型',
  PRIMARY KEY (`id`),
  KEY `idx_costdate` (`costdate`),
  KEY `idx_userid` (`userid`),
  KEY `idx_planid` (`planid`),
  KEY `idx_unitid` (`unitid`),
  KEY `idx_wordid` (`wordid`),
  KEY `idx_ideaid` (`adid`),
  KEY `idx_1` (`costdate`,`wordid`,`adid`,`clicktype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_spiderIp` */

CREATE TABLE `t_spiderIp` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `time` varchar(16) DEFAULT NULL COMMENT '爬虫时间',
  `ip` varchar(16) DEFAULT NULL COMMENT '爬虫ip',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`),
  KEY `time` (`time`)
) ENGINE=InnoDB AUTO_INCREMENT=3138960 DEFAULT CHARSET=utf8;

/*Table structure for table `t_summary_word` */

CREATE TABLE `t_summary_word` (
  `word_id` int(10) NOT NULL,
  `show` int(11) DEFAULT NULL,
  `click` int(11) DEFAULT NULL,
  `charge` int(11) DEFAULT NULL,
  `user_id` int(10) DEFAULT NULL,
  `cost_date` date DEFAULT NULL,
  `last_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`word_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_conf` */

CREATE TABLE `t_sys_conf` (
  `id1` int(10) unsigned NOT NULL,
  `id2` int(10) unsigned NOT NULL,
  `id3` int(10) unsigned NOT NULL,
  `disname` char(32) DEFAULT NULL,
  `dataname` char(32) DEFAULT NULL,
  `datavalue1` char(32) DEFAULT NULL,
  `datavalue2` char(32) DEFAULT NULL,
  `defaultvalue` char(32) DEFAULT NULL,
  `ordernum` int(11) DEFAULT NULL,
  `savetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_dictionary` */

CREATE TABLE `t_sys_dictionary` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dkey` varchar(200) DEFAULT NULL COMMENT '系统码表 key ',
  `dvalue` varchar(200) DEFAULT NULL COMMENT '系统码表 value',
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_finance` */

CREATE TABLE `t_sys_finance` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(10) unsigned NOT NULL COMMENT '客户编号',
  `amout` int(11) NOT NULL COMMENT '入账金额 单位：分',
  `banktype` char(16) DEFAULT NULL COMMENT '银行卡类型',
  `accountno` int(11) DEFAULT NULL COMMENT '汇款传真过来单底编号），凭证编号中如果是转帐等过来的没有汇款单底的编号则为“0”',
  `receive` tinyint(4) DEFAULT NULL COMMENT '是否到账 是：1 否：0',
  `remark` char(255) DEFAULT NULL COMMENT '补充说明',
  `opuserid` int(10) unsigned NOT NULL COMMENT '创建者id',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `lasttime` datetime NOT NULL COMMENT '最后一次更新时间',
  `agentaccountno` int(11) DEFAULT NULL COMMENT '代理商入账编号',
  `agentid` int(11) DEFAULT NULL COMMENT '代理商编号',
  PRIMARY KEY (`id`),
  KEY `idx_userid` (`userid`),
  KEY `idx_opuserid` (`opuserid`),
  KEY `idx_createtime` (`createtime`),
  KEY `idx_lasttime` (`lasttime`),
  KEY `idx_accountno` (`accountno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_fun` */

CREATE TABLE `t_sys_fun` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '功能id',
  `menuid` int(11) NOT NULL COMMENT '对应菜单id',
  `fname` char(32) NOT NULL COMMENT '功能名称',
  `des` char(255) DEFAULT NULL COMMENT '功能描述',
  `url` varchar(1024) NOT NULL COMMENT '功能url',
  `adminid` int(10) NOT NULL COMMENT '创建者id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `lasttime` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_opuserid` (`adminid`),
  KEY `idx_createtime` (`createtime`),
  KEY `idx_lasttime` (`lasttime`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_fun_menu` */

CREATE TABLE `t_sys_fun_menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `funid` int(11) NOT NULL COMMENT '功能id',
  `menuid` int(11) NOT NULL COMMENT '菜单id',
  `opuserid` int(11) NOT NULL COMMENT '创建者id',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `lasttime` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_opuserid` (`opuserid`),
  KEY `idx_funid` (`funid`),
  KEY `idx_menuid` (`menuid`),
  KEY `idx_createtime` (`createtime`),
  KEY `idx_lasttime` (`lasttime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_industry` */

CREATE TABLE `t_sys_industry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` char(16) NOT NULL COMMENT '级别编码',
  `level` int(11) DEFAULT NULL COMMENT '等级',
  `i_name` char(255) NOT NULL DEFAULT '' COMMENT '行业名称',
  `desc` char(255) NOT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_menu` */

CREATE TABLE `t_sys_menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mname` char(32) NOT NULL COMMENT '菜单名称',
  `des` char(255) DEFAULT NULL COMMENT '菜单描述',
  `url` varchar(1024) DEFAULT NULL COMMENT '菜单url',
  `preId` int(11) DEFAULT NULL COMMENT '上级菜单id',
  `levelId` tinyint(4) DEFAULT NULL COMMENT '菜单分级，1级，2级，3级',
  `opuserid` int(11) DEFAULT NULL COMMENT '创建者id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `lasttime` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `type` tinyint(4) DEFAULT NULL COMMENT '普通菜单0，系统菜单1',
  `levelcode` char(32) DEFAULT NULL COMMENT '级别标志码',
  PRIMARY KEY (`id`),
  KEY `idx_opuserid` (`opuserid`),
  KEY `idx_createtime` (`createtime`),
  KEY `idx_lasttime` (`lasttime`),
  KEY `idx_type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=246 DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_role` */

CREATE TABLE `t_sys_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `rname` char(64) NOT NULL COMMENT '角色',
  `des` char(255) DEFAULT NULL COMMENT '角色信息',
  `opuserid` int(11) DEFAULT NULL COMMENT '创建者id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `lasttime` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `type` tinyint(4) DEFAULT NULL COMMENT '角色类型:\r\n财务审核角色\r\n物料审核角色\r\n客户信息审核角色\r\n系统管理角色\r\n广告主角色\r\n',
  PRIMARY KEY (`id`),
  KEY `idx_opuserid` (`opuserid`),
  KEY `idx_createtime` (`createtime`),
  KEY `idx_lasttime` (`lasttime`),
  KEY `idx_type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_role_fun` */

CREATE TABLE `t_sys_role_fun` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `funid` int(11) NOT NULL COMMENT '功能id',
  `roleid` int(11) NOT NULL COMMENT '菜单id',
  `adminid` int(11) NOT NULL COMMENT '创建者id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `lasttime` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_opuserid` (`adminid`),
  KEY `idx_funid` (`funid`),
  KEY `idx_menuid` (`roleid`),
  KEY `idx_createtime` (`createtime`),
  KEY `idx_lasttime` (`lasttime`)
) ENGINE=InnoDB AUTO_INCREMENT=1915 DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_role_menu` */

CREATE TABLE `t_sys_role_menu` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) NOT NULL COMMENT '角色',
  `menuid` int(11) NOT NULL COMMENT '菜单',
  `opuserid` int(11) DEFAULT NULL COMMENT '创建者id',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `lasttime` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_roleid` (`roleid`),
  KEY `idx_menuid` (`menuid`),
  KEY `idx_opuserid` (`opuserid`),
  KEY `idx_createtime` (`createtime`),
  KEY `idx_lasttime` (`lasttime`)
) ENGINE=InnoDB AUTO_INCREMENT=4873 DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_security_code` */

CREATE TABLE `t_sys_security_code` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) DEFAULT NULL COMMENT '验证码',
  `userid` int(10) DEFAULT NULL COMMENT '用户id',
  `phone` varchar(20) DEFAULT NULL COMMENT '发送手机号',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '生成时间',
  `invalidtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '失效时间',
  `usedtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '使用时间',
  `status` int(11) DEFAULT NULL COMMENT '状态0:未使用 1:已使用',
  `ip` varchar(300) DEFAULT NULL COMMENT '生成者ip地址',
  `ua` varchar(500) DEFAULT NULL COMMENT '生成者ua',
  `type` int(11) DEFAULT '1' COMMENT '1:运营后台登陆使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=287 DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_user` */

CREATE TABLE `t_sys_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uname` char(32) NOT NULL,
  `password` char(32) NOT NULL,
  `truename` char(32) DEFAULT NULL COMMENT '真实姓名',
  `mobile` char(32) DEFAULT NULL COMMENT '手机号',
  `email` char(64) DEFAULT NULL COMMENT '邮件',
  `opuserid` int(11) DEFAULT NULL COMMENT '创建者id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `lasttime` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `type` tinyint(4) DEFAULT NULL COMMENT '普通用户:0\r\n管理员:1\r\n 代理商:2',
  `enable` tinyint(2) DEFAULT '1' COMMENT '状态 启用1 暂停0',
  `source` int(11) DEFAULT '0' COMMENT '管理员所属部门 默认为0:所有部门 1:试点 2:营销部 3:互动广告部 4:广告部 5:市场部 6:游戏部 7:客户端部 8:媒体部',
  `times` int(11) DEFAULT '0',
  `sourcecpd` int(11) DEFAULT '0' COMMENT '针对cpd部门：1：试点 2：销售一部（原营销部） 3：销售二部（原广告部）4:销售三部（原广平部） 5：乐点',
  PRIMARY KEY (`id`),
  KEY `idx_opuserid` (`opuserid`),
  KEY `idx_createtime` (`createtime`),
  KEY `idx_lasttime` (`lasttime`),
  KEY `idx_type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_user_bk` */

CREATE TABLE `t_sys_user_bk` (
  `id` int(10) unsigned NOT NULL DEFAULT '0',
  `uname` char(32) NOT NULL,
  `password` char(32) NOT NULL,
  `truename` char(32) DEFAULT NULL COMMENT '真实姓名',
  `mobile` char(32) DEFAULT NULL COMMENT '手机号',
  `email` char(64) DEFAULT NULL COMMENT '邮件',
  `opuserid` int(11) DEFAULT NULL COMMENT '创建者id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `lasttime` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `type` tinyint(4) DEFAULT NULL COMMENT '普通用户:0\r\n管理员:1\r\n 代理商:2',
  `enable` tinyint(2) DEFAULT '1' COMMENT '状态 启用1 暂停0',
  `source` int(11) DEFAULT '0' COMMENT '管理员所属部门 默认为0:所有部门 1:试点 2:营销部 3:互动广告部 4:广告部 5:市场部 6:游戏部 7:客户端部 8:媒体部'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_user_role` */

CREATE TABLE `t_sys_user_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sysuserid` int(11) NOT NULL COMMENT '系统用户id',
  `roleid` int(10) unsigned NOT NULL COMMENT '角色id',
  `opuserid` int(10) unsigned NOT NULL COMMENT '创建者id',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `lasttime` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_sysuserid` (`sysuserid`),
  KEY `idx_roleid` (`roleid`),
  KEY `idx_opuserid` (`opuserid`),
  KEY `idx_createtime` (`createtime`),
  KEY `idx_lasttime` (`lasttime`)
) ENGINE=InnoDB AUTO_INCREMENT=810 DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_verify_log` */

CREATE TABLE `t_sys_verify_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `checkno` int(11) DEFAULT NULL,
  `opuserid` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `userstat` int(11) DEFAULT NULL,
  `other_reason` varchar(255) DEFAULT NULL COMMENT '审核通过其他原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3327 DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_verify_reason` */

CREATE TABLE `t_sys_verify_reason` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `checkno` int(11) DEFAULT NULL,
  `checkvalue` char(255) DEFAULT NULL,
  `opuserid` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '1:客户信息审核 2:物料审核',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_wordblack` */

CREATE TABLE `t_sys_wordblack` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `word` char(64) DEFAULT NULL COMMENT '词名',
  `opuserid` int(10) unsigned NOT NULL COMMENT '创建者id',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `lasttime` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_opuserid` (`opuserid`),
  KEY `idx_createtime` (`createtime`),
  KEY `idx_lasttime` (`lasttime`)
) ENGINE=InnoDB AUTO_INCREMENT=106577 DEFAULT CHARSET=utf8;

/*Table structure for table `t_teampay_member` */

CREATE TABLE `t_teampay_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '成员id',
  `userid` int(11) DEFAULT NULL COMMENT '用户id',
  `id_name` varchar(255) DEFAULT NULL COMMENT '成员姓名',
  `id_number` varchar(255) DEFAULT NULL COMMENT '成员身份证号',
  `p_image` varchar(255) DEFAULT NULL COMMENT '身份证正面url',
  `n_image` varchar(255) DEFAULT NULL COMMENT '身份证反面url',
  `createtime` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=264 DEFAULT CHARSET=utf8;

/*Table structure for table `t_temp_medias` */

CREATE TABLE `t_temp_medias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appname` varchar(100) DEFAULT NULL COMMENT '应用名称',
  `packagename` varchar(100) DEFAULT NULL COMMENT '包名',
  `mediausername` varchar(100) DEFAULT NULL COMMENT '开发者名称',
  `mediatype` int(4) DEFAULT NULL COMMENT '媒体软件 1 游戏2',
  `mediacategory` varchar(100) DEFAULT NULL COMMENT '媒体分类',
  `downcount` int(11) DEFAULT NULL COMMENT '下载量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2900 DEFAULT CHARSET=utf8;

/*Table structure for table `t_temp_mis` */

CREATE TABLE `t_temp_mis` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uname` varchar(255) DEFAULT NULL,
  `charge` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=312 DEFAULT CHARSET=utf8;

/*Table structure for table `t_temp_sep` */

CREATE TABLE `t_temp_sep` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `charge` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=313 DEFAULT CHARSET=utf8;

/*Table structure for table `t_tips_close_log` */

CREATE TABLE `t_tips_close_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `tipId` varchar(10) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3545 DEFAULT CHARSET=utf8;

/*Table structure for table `t_ueasou_news` */

CREATE TABLE `t_ueasou_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '新闻id',
  `title` varchar(100) DEFAULT NULL COMMENT '新闻标题title',
  `source` varchar(200) DEFAULT NULL COMMENT '来源/作者',
  `date` varchar(100) DEFAULT NULL COMMENT '日期',
  `content` text COMMENT '新闻内容',
  `newstype` char(4) DEFAULT NULL COMMENT '新闻类型 1.成功案例 2.广告投放技巧 3.媒体新闻',
  `lastmodifytime` datetime DEFAULT NULL COMMENT '最后发布时间',
  `summary` text COMMENT '新闻概要',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Table structure for table `t_union_prepay_info` */

CREATE TABLE `t_union_prepay_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `value` int(11) DEFAULT NULL COMMENT '预付费金额单位分',
  `begindate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '预付开始时间',
  `enddate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '预付结束时间',
  `adminname` varchar(100) DEFAULT NULL COMMENT '操作人',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `userid` int(11) DEFAULT NULL COMMENT '媒体用户userid',
  `mediaid` int(11) DEFAULT NULL COMMENT '应用媒体id',
  `adminid` int(11) DEFAULT NULL COMMENT '管理员id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Table structure for table `t_unit` */

CREATE TABLE `t_unit` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(10) unsigned NOT NULL COMMENT '此记录的用户id',
  `planid` int(10) unsigned NOT NULL COMMENT '该词对应的计划',
  `unitname` char(64) NOT NULL COMMENT '用户填写的单元标题',
  `unitbid` int(11) DEFAULT NULL COMMENT '单元出价 单位：分',
  `url` varchar(1024) DEFAULT NULL COMMENT '点击的URL',
  `showurl` char(64) DEFAULT NULL COMMENT '默认取用户的网址',
  `phone` char(16) DEFAULT NULL COMMENT '区号+电话号码',
  `exactnegative` varchar(1024) DEFAULT NULL COMMENT '精确否定关键词 N:str1;str2…strn',
  `negative` varchar(1024) DEFAULT NULL COMMENT '否定关键词 默认0：DFT',
  `unitstat` tinyint(4) NOT NULL COMMENT '1 有效\r\n2 暂停推广\r\n3 推广计划暂停推广\r\n',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `lasttime` datetime NOT NULL COMMENT '最后一次修改时间',
  `deleteflag` tinyint(4) DEFAULT '1' COMMENT '1:正常2:删除',
  `promotion_type` int(4) DEFAULT '1' COMMENT '推广类型1：搜索推广 2：展示推广',
  `payment_price` int(10) DEFAULT '0' COMMENT '付费单价 单位（分）',
  `set_price` int(10) DEFAULT '0',
  `adpostion` varchar(1024) DEFAULT '0:DFT' COMMENT '广告位字符串',
  `ad_type` int(11) DEFAULT '0',
  `ad_active_limit` int(11) DEFAULT '2000' COMMENT '广告激活数上限:积分墙广告激活总数上限，0表示没有限制 ',
  `step_pay_rate` varchar(200) DEFAULT '7:70;50;50;50;50;50;50' COMMENT '分步返积分比例:积分墙广告按天分步激活所返积分的比率 7:70;50;...50 默认0:DFT ',
  `retention_rate` varchar(200) DEFAULT '6:20;16;13;10;8;5' COMMENT '6日目标留存率:积分墙广告激活后6日的目标留存率，从激活日的下一天开始 6:20;...5 默认0:DFT ',
  `hidden_on_active` int(11) DEFAULT '0' COMMENT '激活页面隐藏标志:0：默认值，该广告可以在积分墙下载页面显示；1：该广告在下载页面不显示，但可以在签到页面显示 ',
  `channel_name` varchar(30) DEFAULT NULL COMMENT '渠道名称',
  `channel_ratio` int(7) DEFAULT '100' COMMENT '渠道比例',
  `freebid` int(4) DEFAULT '0' COMMENT '默认为 0：参与自由竞价 1：不参与自由竞价',
  `downtype` int(4) DEFAULT '0' COMMENT '下载类标识：默认0：非下载类型、1：下载类型',
  `down_pkg_size` int(11) DEFAULT '0' COMMENT '下载类型广告包大小',
  `targetflow` varchar(200) DEFAULT '0:DFT' COMMENT '1:自有流量 2:联盟wa p3:联盟APP 0:DFT(默认全选) N:t1;t2;t3',
  PRIMARY KEY (`id`),
  KEY `idx_planid` (`planid`),
  KEY `idx_userid` (`userid`),
  KEY `idx_lasttime` (`lasttime`),
  KEY `idx_unitstat` (`unitstat`)
) ENGINE=InnoDB AUTO_INCREMENT=3691696 DEFAULT CHARSET=utf8;

/*Table structure for table `t_user` */

CREATE TABLE `t_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(10) unsigned NOT NULL COMMENT '用户信息表id',
  `region` char(36) DEFAULT NULL COMMENT 'N:reg1;reg2…regn\r\n默认0:DFT是所有地域\r\n',
  `exactnegative` varchar(1024) DEFAULT NULL COMMENT 'N:str1;str2…strn，默认0:DFT',
  `negative` varchar(1024) DEFAULT NULL COMMENT 'N:str1;str2…strn，默认0:DFT',
  `budget` int(11) DEFAULT NULL COMMENT '用户层预算 单位：分',
  `value` int(11) NOT NULL DEFAULT '0' COMMENT '用户已经消费的金额，系统会根据预算和已消费，计算状态是预算充足还是不足。\r\n每天需要清0.\r\n',
  `budgetday` tinyint(4) DEFAULT NULL COMMENT '预算周期 默认按天\r\n1 天\r\n2 周\r\n',
  `userstat` tinyint(4) NOT NULL COMMENT '用户状态\r\n1 待审核 \r\n2 有效\r\n3 未通过\r\n4 停用\r\n',
  `checkno` tinyint(4) DEFAULT NULL COMMENT '审核不通过原因 0 无\r\n1 。。。\r\n2 。。。\r\n',
  `showratio` int(11) DEFAULT NULL COMMENT '展现系数',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `lasttime` datetime NOT NULL COMMENT '最后一次修改该表信息的时间',
  `lastinfotime` datetime DEFAULT NULL COMMENT '最后一次修改关键词或创意的时间',
  `valuestat` tinyint(4) NOT NULL COMMENT '资金状态\r\n1 开户金未到\r\n2 资金充足\r\n3 余额不足\r\n4 预算不足\r\n',
  `verifytime` datetime DEFAULT NULL COMMENT '审核时间',
  `income` int(10) unsigned zerofill NOT NULL COMMENT '总收入',
  `outcome` int(10) unsigned zerofill NOT NULL COMMENT '总支出',
  `codes` varchar(3500) DEFAULT '999999' COMMENT '推广地域省市编码',
  `pricetype` int(10) DEFAULT '0' COMMENT '价格类型',
  `role_adtype` varchar(100) DEFAULT 'ad1' COMMENT '广告类型显示 ad1;//文本广告 ad2;//图片广告 ad3;//小说广告 ad4;//app广告 ad6;//品牌广告',
  `role_channel` varchar(100) DEFAULT NULL COMMENT '频道 //小说频道 c3//购物频道  c22//图片频道  c2//音乐频道  c10//新闻频道  c7//网页频道  c14',
  `role_operators` varchar(30) DEFAULT NULL COMMENT '运行商显示 0不显示 1显示',
  `role_operators_sp` varchar(30) DEFAULT '0' COMMENT '运行商(展示推广)显示 0不显示 1显示',
  `role_networktype` varchar(30) DEFAULT NULL COMMENT '网络协议 0不显示 1显示',
  `role_networktype_sp` varchar(30) DEFAULT '0' COMMENT '网络协议(展示推广) 0不显示 1显示',
  `other_reason` varchar(255) DEFAULT NULL COMMENT '审核通过其他原因',
  `openfee` int(8) DEFAULT '0' COMMENT '开户费',
  `role_adpostion` varchar(30) DEFAULT '0' COMMENT '广告位选择按钮 0不显示 1显示',
  `role_postiontfl` varchar(30) DEFAULT '0' COMMENT '投放量选择按钮 0不显示 1显示',
  `role_showos` varchar(30) DEFAULT '0' COMMENT '操作系统按钮 0不显示 1显示',
  `probability` int(8) DEFAULT '1000' COMMENT '1000：展现概率100% 500：展现概率50% 250： 展现概率25% 125：展现概率12.5%',
  `role_lockad` tinyint(4) DEFAULT '0' COMMENT '是否有权限创建锁屏广告，0:不可创建,1:可以创建，默认为0',
  `role_bcustf` tinyint(4) DEFAULT '0' COMMENT '大客户投放选择按钮 0不显示 1显示',
  PRIMARY KEY (`id`),
  KEY `idx_createtime` (`createtime`),
  KEY `idx_lasttime` (`lasttime`),
  KEY `idx_userstat` (`userstat`),
  KEY `idx_lastinfotime` (`lastinfotime`),
  KEY `idx_audit` (`userid`,`userstat`)
) ENGINE=InnoDB AUTO_INCREMENT=7047 DEFAULT CHARSET=utf8;

/*Table structure for table `t_wapage_word` */

CREATE TABLE `t_wapage_word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `str_word` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_winfo` */

CREATE TABLE `t_winfo` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '数据库自增id',
  `userid` int(10) unsigned NOT NULL COMMENT '第一次创建此记录的用户id',
  `planid` int(10) unsigned zerofill DEFAULT NULL COMMENT '该词对应的计划',
  `unitid` int(10) unsigned zerofill DEFAULT NULL COMMENT '该词对应的单元',
  `word` char(64) NOT NULL COMMENT '用户选择的关键词',
  `wordid` int(10) unsigned NOT NULL COMMENT '关键词唯一id',
  `bid` int(10) unsigned zerofill NOT NULL COMMENT '用户给的单价，单位分',
  `minbid` int(11) DEFAULT NULL COMMENT '单位：分',
  `minprice` int(11) DEFAULT NULL COMMENT '单位：分',
  `showfactor` int(11) DEFAULT NULL,
  `createtime` datetime NOT NULL COMMENT '该词的创建时间',
  `wordstat` tinyint(4) NOT NULL COMMENT '关键词状态',
  `checkno` tinyint(3) unsigned zerofill NOT NULL COMMENT '审核不通过理由',
  `lasttime` datetime NOT NULL,
  `matchmodel` int(11) DEFAULT NULL COMMENT '匹配模式',
  `checkstat` tinyint(4) DEFAULT NULL COMMENT '审核状态',
  `deleteflag` tinyint(4) DEFAULT '1' COMMENT '1:正常2:删除',
  `promotion_type` int(4) DEFAULT '1' COMMENT '推广类型1：搜索推广 2：展示推广',
  PRIMARY KEY (`id`),
  KEY `idx_userid` (`userid`),
  KEY `idx_planid` (`planid`),
  KEY `idx_unitid` (`unitid`),
  KEY `idx_lasttime` (`lasttime`),
  KEY `idx_wordstat` (`wordstat`),
  KEY `index_admin_count` (`unitid`,`wordstat`,`checkstat`,`deleteflag`),
  KEY `idx_check_delete` (`deleteflag`,`checkstat`),
  KEY `idx_word` (`word`),
  KEY `idx_wordid_deleteflag` (`wordid`,`deleteflag`)
) ENGINE=InnoDB AUTO_INCREMENT=25233026 DEFAULT CHARSET=utf8;

/*Table structure for table `tmp` */

CREATE TABLE `tmp` (
  `id` int(11) unsigned NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
