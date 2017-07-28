##------------------执行完毕 begin
CREATE TABLE `ticket_user` (
`id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`is_deleted`  char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除' ,
`gmt_create`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`gmt_modified`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
`creator`  int(11) NOT NULL DEFAULT 0 COMMENT '创建人ID' ,
`modifier`  int(11) NOT NULL DEFAULT 0 COMMENT '修改人ID' ,
`user_name`  varchar(64) NOT NULL DEFAULT '' COMMENT '用户名' ,
`password`  varchar(128) NOT NULL COMMENT '密码' ,
`user_mobile`  varchar(11) NOT NULL COMMENT '手机号' ,
`is_enable`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否开通(0:否，1:是)' ,
PRIMARY KEY (`id`)
)
COMMENT='售票系统用户表';


CREATE TABLE `ticket_user_login_code` (
`id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`is_deleted`  char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除' ,
`gmt_create`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`gmt_modified`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
`creator`  int(11) NOT NULL DEFAULT 0 COMMENT '创建人ID' ,
`modifier`  int(11) NOT NULL DEFAULT 0 COMMENT '修改人ID' ,
`user_id`  varchar(64) NOT NULL DEFAULT '' COMMENT 'ticket_user的id' ,
`user_mobile`  varchar(11) NOT NULL COMMENT '手机号' ,
`verify_code`  varchar(6) NOT NULL COMMENT '验证码' ,
`verify_status`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '验证状态（0：未验证1：验证成功2：验证失败）' ,
PRIMARY KEY (`id`)
)
COMMENT='手机验证码表';



CREATE TABLE `ticket_movie` (
`id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`is_deleted`  char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除' ,
`gmt_create`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`gmt_modified`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
`creator`  int(11) NOT NULL DEFAULT 0 COMMENT '创建人ID' ,
`modifier`  int(11) NOT NULL DEFAULT 0 COMMENT '修改人ID' ,
`movie_name`  varchar(64) NOT NULL COMMENT '电影名字' ,
`movie_director`  varchar(60) NOT NULL COMMENT '导演' ,
`movie_type` varchar(60) NOT NULL COMMENT '电影类型' ,
`movie_product` varchar(11) NOT NULL COMMENT '制片国家/地区' ,
`movie_language` varchar(11) NOT NULL COMMENT '语言' ,
`movie_length` int(11) NOT NULL COMMENT '电影片长(分钟)' ,
`movie_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '剧情介绍',
PRIMARY KEY (`id`)
)
COMMENT='电影';


CREATE TABLE `ticket_cinema` (
`id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`is_deleted`  char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除' ,
`gmt_create`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`gmt_modified`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
`creator`  int(11) NOT NULL DEFAULT 0 COMMENT '创建人ID' ,
`modifier`  int(11) NOT NULL DEFAULT 0 COMMENT '修改人ID' ,
`cinema_name`  varchar(64) NOT NULL COMMENT '影院名字' ,
`cinema_city_id`  int(11) NOT NULL COMMENT '城市id' ,
`cinema_city_name` varchar(60) NOT NULL COMMENT '城市名' ,
`cinema_area_id` int(11) NOT NULL COMMENT '城区id' ,
`cinema_area_name` varchar(60) NOT NULL COMMENT '城区名' ,
`cinema_address` varchar(60) NOT NULL COMMENT '影院详细地址' ,
`cinema_mobile` varchar(15) NOT NULL COMMENT '影院电话（不是手机）' ,
PRIMARY KEY (`id`)
)
COMMENT='影院';
##--考虑：在后台系统管理的时候，需不需要考虑组织关系（类似车队）

CREATE TABLE `ticket_movie_cinema_rel` (
`id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`is_deleted`  char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除' ,
`gmt_create`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`gmt_modified`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
`creator`  int(11) NOT NULL DEFAULT 0 COMMENT '创建人ID' ,
`modifier`  int(11) NOT NULL DEFAULT 0 COMMENT '修改人ID' ,
`movie_id`  int(11) NOT NULL COMMENT '电影id' ,
`cinema_id`  int(11) NOT NULL COMMENT '影院id' ,
`movie_effective_date` datetime NOT NULL COMMENT '影院影片播放日期' ,
`movie_expiration_date` datetime NOT NULL COMMENT '影院影片下线日期' ,
PRIMARY KEY (`id`)
)
COMMENT='影院电影排片日期';

CREATE TABLE `ticket_specific_schedule` (
`id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`is_deleted`  char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除' ,
`gmt_create`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`gmt_modified`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
`creator`  int(11) NOT NULL DEFAULT 0 COMMENT '创建人ID' ,
`modifier`  int(11) NOT NULL DEFAULT 0 COMMENT '修改人ID' ,
`movie_id`  int(11) NOT NULL COMMENT '电影id' ,
`cinema_id`  int(11) NOT NULL COMMENT '影院id' ,
`movie_show_time` datetime NOT NULL COMMENT '影院影片播放时间' ,
`movie_end_time` datetime NOT NULL COMMENT '影院影片结束时间' ,
`movie_language_version` varchar(11) NOT NULL COMMENT '语言版本',
`showroom_id` int(11) NOT NULL COMMENT '放映厅id' ,
`exist_seat_table` varchar(1000) NOT NULL COMMENT '已被选购座位表，存座位坐标，坐标之间使用-隔开' ,
`present_price` decimal(10,2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '现价' ,
`cinema_price` decimal(10,2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '影院价' ,
PRIMARY KEY (`id`)
)
COMMENT='影院电影具体排片';

CREATE TABLE `ticket_showroom` (
`id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`is_deleted`  char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除' ,
`gmt_create`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`gmt_modified`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
`creator`  int(11) NOT NULL DEFAULT 0 COMMENT '创建人ID' ,
`modifier`  int(11) NOT NULL DEFAULT 0 COMMENT '修改人ID' ,
`showroom_name` varchar(64) NOT NULL COMMENT '放映厅名字' ,
`seat_table` varchar(1000) NOT NULL COMMENT '座位表，存座位坐标，坐标之间使用-隔开' ,
`cinema_id` int(11) NOT NULL COMMENT '影院id' ,
PRIMARY KEY (`id`)
)
COMMENT='放映厅';

CREATE TABLE `ticket_open_city` (
`id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`is_deleted`  char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除' ,
`gmt_create`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
`gmt_modified`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
`creator`  int(11) NOT NULL DEFAULT 0 COMMENT '创建人ID' ,
`modifier`  int(11) NOT NULL DEFAULT 0 COMMENT '修改人ID' ,
`city_id`  int(11) NOT NULL COMMENT '城市id' ,
`city_name` varchar(60) NOT NULL COMMENT '城市名' ,
`area_id` int(11) NOT NULL COMMENT '城区id' ,
`area_name` varchar(60) NOT NULL COMMENT '城区名' ,
PRIMARY KEY (`id`)
)
COMMENT='城市表';


##--修改数据库字段--
ALTER TABLE ticket_user
  MODIFY `user_mobile` BIGINT NOT NULL;

ALTER TABLE ticket_user_login_code
  MODIFY `user_mobile` BIGINT NOT NULL,
  MODIFY `verify_code` INT NOT NULL ;

ALTER TABLE ticket_user_login_code DROP user_id;

ALTER TABLE ticket_user
MODIFY `password`  varchar(128) DEFAULT NULL COMMENT '密码';
ALTER TABLE ticket_movie
ADD `movie_picture`  varchar(256) DEFAULT NULL COMMENT '照片url';
ALTER TABLE ticket_specific_schedule
  ADD `showroom_name`  varchar(64) NOT NULL DEFAULT '' COMMENT '放映厅名字';

ALTER TABLE ticket_specific_schedule
    MODIFY `showroom_name`  varchar(64) NOT NULL DEFAULT '' COMMENT '放映厅名字' AFTER `showroom_id`;


##----------论文需要更改处begin
CREATE TABLE `ticket_order` (
  `id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `is_deleted`  char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除' ,
  `gmt_create`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
  `gmt_modified`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
  `creator`  int(11) NOT NULL DEFAULT 0 COMMENT '创建人ID' ,
  `modifier`  int(11) NOT NULL DEFAULT 0 COMMENT '修改人ID' ,
  `schedule_id`  int(11) NOT NULL COMMENT '时刻表id' ,
  `user_id`  int(11) NOT NULL COMMENT '用户id' ,
  `order_seat` varchar(80) NOT NULL COMMENT '预定的座位',
  `order_present_price` decimal(10,2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '线上实际总价',
  `order_cinema_price` decimal(10,2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '线下实际总价',
  `order_preferential_price` decimal(10,2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '优惠价格',
    PRIMARY KEY (`id`)
)
  COMMENT='订单表';

ALTER TABLE ticket_movie
  ADD `movie_actor`  varchar(64) NOT NULL DEFAULT '' COMMENT '主演';

ALTER TABLE ticket_movie
  MODIFY `movie_product`  varchar(30) NOT NULL DEFAULT '' COMMENT '制片国家/地区' ;

ALTER TABLE ticket_showroom
  MODIFY `seat_table`  varchar(3000) NOT NULL COMMENT '座位表，存座位坐标，坐标之间使用-隔开' ;

ALTER TABLE ticket_specific_schedule
  MODIFY `exist_seat_table`  varchar(3000) NOT NULL COMMENT '已被选购座位表，存座位坐标，坐标之间使用-隔开' ;

ALTER TABLE ticket_cinema
  ADD `ticket_picture`  varchar(256) DEFAULT NULL COMMENT '照片url';

ALTER TABLE ticket_cinema
  CHANGE `ticket_picture` `cinema_picture`  varchar(256) DEFAULT NULL COMMENT '照片url';

ALTER TABLE ticket_movie_cinema_rel
  ADD `movie_name`  varchar(64) NOT NULL DEFAULT '' COMMENT '电影名字' AFTER `movie_id`;

ALTER TABLE ticket_movie_cinema_rel
  ADD `cinema_name`  varchar(64) NOT NULL DEFAULT '' COMMENT '影院名字' AFTER `cinema_id`;

ALTER TABLE ticket_order
  ADD `order_status`  tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '订单状态 0：待付款 1：已付款 2：已完成 3：已退单' ;

ALTER TABLE ticket_order
  ADD `order_no`  VARCHAR(50) NOT NULL DEFAULT '' COMMENT '订单编号' AFTER `modifier`;

ALTER TABLE ticket_order
  ADD `order_tickets`  tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '票数' AFTER `order_seat`;

ALTER TABLE ticket_order
  ADD `order_mobile`  bigint(20) UNSIGNED NOT NULL COMMENT '接收短信的手机号' AFTER `order_preferential_price`;

ALTER TABLE ticket_order
  ADD `order_tickets_no`  VARCHAR(250) NOT NULL DEFAULT '' COMMENT '生成的电影票取货码' AFTER `order_tickets`;

ALTER TABLE ticket_order
  ADD `movie_show_time`  datetime NOT NULL COMMENT '影院影片播放时间' AFTER `order_no`;



CREATE TABLE `ticket_short_msg_log` (
  `id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `is_deleted`  char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除' ,
  `gmt_create`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
  `gmt_modified`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
  `creator`  int(11) NOT NULL DEFAULT 0 COMMENT '创建人ID' ,
  `modifier`  int(11) NOT NULL DEFAULT 0 COMMENT '修改人ID' ,
  `msg_mobile`  bigint(20) UNSIGNED NOT NULL COMMENT '接收短信的手机号' ,
  `msg_value`  varchar(500) NOT NULL DEFAULT '' COMMENT '发送短信的变量内容，json格式' ,
  `msg_deadline_time` datetime COMMENT 'msg在死亡线时间之前发送对用户来说才有意义，比如取票码必须再影片上映之前发送' ,
  `msg_status` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '短信状态，0:未处理 1:已处理' ,
  `msg_template_id` bigint(20) UNSIGNED NOT NULL COMMENT '短信模板id' ,
  `msg_service_provider` tinyint(1) UNSIGNED NOT NULL COMMENT '短信服务提供商，1:网易云信' ,
  PRIMARY KEY (`id`)
)
  COMMENT='短信流水表';

ALTER TABLE `ticket_specific_schedule`
  ADD `movie_name`  varchar(64) NOT NULL DEFAULT '' COMMENT '电影名字' AFTER `movie_id`;
ALTER TABLE `ticket_specific_schedule`
  ADD `cinema_name`  varchar(64) NOT NULL DEFAULT '' COMMENT '影院名字' AFTER `cinema_id`;

CREATE TABLE `ticket_login_log` (
  `id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `is_deleted`  char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除' ,
  `gmt_create`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间' ,
  `gmt_modified`  datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间' ,
  `creator`  int(11) NOT NULL DEFAULT 0 COMMENT '创建人ID' ,
  `modifier`  int(11) NOT NULL DEFAULT 0 COMMENT '修改人ID' ,
  `mobile`  bigint(20) UNSIGNED NOT NULL COMMENT '登录手机号' ,
  PRIMARY KEY (`id`)
)
  COMMENT='登录流水表';
##-------------论文需要更改处end

ALTER TABLE ticket_movie
  MODIFY `movie_actor`  varchar(64) NOT NULL DEFAULT '' COMMENT '主演' AFTER `movie_director`;

##-----------------执行完毕 end















