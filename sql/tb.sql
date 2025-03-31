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

# 题目表
DROP TABLE IF EXISTS `tb_question`;
CREATE TABLE `tb_question`
(
    `question_id`   BIGINT UNSIGNED NOT NULL COMMENT '题目id',
    `title`         VARCHAR(50)     NOT NULL COMMENT '题目标题',
    `difficulty`    TINYINT         NOT NULL COMMENT '题目难度1:简单  2：中等 3：困难',
    `time_limit`    INT             NOT NULL COMMENT '时间限制',
    `space_limit`   INT             NOT NULL COMMENT '空间限制',
    `content`       VARCHAR(1000)   NOT NULL COMMENT '题目内容',
    `question_case` VARCHAR(1000) COMMENT '题目用例',
    `default_code`  VARCHAR(500)    NOT NULL COMMENT '默认代码块',
    `main_func`     VARCHAR(500)    NOT NULL COMMENT 'main函数',
    `create_by`     BIGINT UNSIGNED NOT NULL COMMENT '创建人',
    `create_time`   DATETIME        NOT NULL COMMENT '创建时间',
    `update_by`     BIGINT UNSIGNED COMMENT '更新人',
    `update_time`   DATETIME COMMENT '更新时间',
    UNIQUE KEY `title` (`title`),
    PRIMARY KEY (`question_id`)
);

CREATE TABLE `tb_contest`
(
    contest_id  BIGINT UNSIGNED NOT NULL COMMENT '竞赛id（主键）',
    title       VARCHAR(50)     NOT NULL COMMENT '竞赛标题',
    start_time  DATETIME        NOT NULL COMMENT '竞赛开始时间',
    end_time    DATETIME        NOT NULL COMMENT '竞赛结束时间',
    status      TINYINT         NOT NULL COMMENT '0' COMMENT '是否发布 0：未发布  1：已发布',
    create_by   BIGINT UNSIGNED NOT NULL COMMENT '创建人',
    create_time DATETIME        NOT NULL COMMENT '创建时间',
    update_by   BIGINT UNSIGNED COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (contest_id)
);


CREATE TABLE `tb_contest_question`
(
    contest_question_id BIGINT UNSIGNED NOT NULL COMMENT '竞赛题目关系id（主键）',
    question_id         BIGINT UNSIGNED NOT NULL COMMENT '题目id（主键）',
    contest_id          BIGINT UNSIGNED NOT NULL COMMENT '竞赛id（主键）',
    question_order      INT             NOT NULL COMMENT '题目顺序',
    create_by           BIGINT UNSIGNED NOT NULL COMMENT '创建人',
    create_time         DATETIME        NOT NULL COMMENT '创建时间',
    update_by           BIGINT UNSIGNED COMMENT '更新人',
    update_time         DATETIME COMMENT '更新时间',
    PRIMARY KEY (contest_question_id)
)