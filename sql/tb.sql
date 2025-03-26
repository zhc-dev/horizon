# 系统用户表（管理员）
DROP TABLE IF EXISTS `tb_system_user`;
CREATE TABLE `tb_system_user`
(
    `user_id`       BIGINT(20) unsigned NOT NULL COMMENT '⽤⼾id',
    `user_account`  VARCHAR(32) DEFAULT NULL COMMENT '⽤⼾账号',
    `user_password` CHAR(60)    DEFAULT NULL COMMENT '用户密码(bcrypt密文)',
    `nick_name`     VARCHAR(32) DEFAULT NULL COMMENT '昵称',
    `create_by`     BIGINT(8)           NOT NULL COMMENT '创建⽤⼾',
    `create_time`   DATETIME            NOT NULL COMMENT '创建时间',
    `update_by`     BIGINT(8)   DEFAULT NULL COMMENT '更新⽤⼾',
    `update_time`   DATETIME    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `user_account` (`user_account`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='管理端⽤⼾表'