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
  DEFAULT CHARSET = utf8mb4 COMMENT ='管理端⽤⼾表';

CREATE TABLE tb_question
(
    QUESTION_ID   BIGINT UNSIGNED NOT NULL COMMENT '题目id',
    TITLE         VARCHAR(50)     NOT NULL COMMENT '题目标题',
    DIFFICULTY    TINYINT         NOT NULL COMMENT '题目难度1:简单  2：中等 3：困难',
    TIME_LIMIT    INT             NOT NULL COMMENT '时间限制',
    SPACE_LIMIT   INT             NOT NULL COMMENT '空间限制',
    CONTENT       VARCHAR(1000)   NOT NULL COMMENT '题目内容',
    QUESTION_CASE VARCHAR(1000) COMMENT '题目用例',
    DEFAULT_CODE  VARCHAR(500)    NOT NULL COMMENT '默认代码块',
    MAIN_FUC      VARCHAR(500)    NOT NULL COMMENT 'main函数',
    CREATE_BY     BIGINT UNSIGNED NOT NULL COMMENT '创建人',
    CREATE_TIME   DATETIME        NOT NULL COMMENT '创建时间',
    UPDATE_BY     BIGINT UNSIGNED COMMENT '更新人',
    UPDATE_TIME   DATETIME COMMENT '更新时间',
    PRIMARY KEY (`QUESTION_ID`)
);