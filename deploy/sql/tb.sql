-- drop `horizon_dev` database if exists
DROP DATABASE IF EXISTS `horizon_dev`;

-- create `horizon_dev` database
CREATE DATABASE `horizon_dev` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- Added COLLATE

-- select `horizon_dev` data table
USE `horizon_dev`;

-- ----------------------------------
-- Table structure for tb_system_user
-- ----------------------------------

DROP TABLE IF EXISTS `tb_system_user`;
CREATE TABLE `tb_system_user`
(
    `user_id`       BIGINT UNSIGNED NOT NULL COMMENT '用户id (主键)',
    `user_account`  VARCHAR(32)     NOT NULL COMMENT '用户账号',
    `user_password` CHAR(60)        NOT NULL COMMENT '用户密码(bcrypt密文)',
    `nick_name`     VARCHAR(32)              DEFAULT NULL COMMENT '昵称',
    `grade`         TINYINT         NOT NULL DEFAULT FALSE COMMENT '管理员等级(权限越小权限越大)',
    `active`        TINYINT         NOT NULL DEFAULT 1 COMMENT '账号是否物理启用(1:启用,2:禁用)',
    `create_by`     BIGINT UNSIGNED          DEFAULT NULL COMMENT '创建者ID',
    `create_time`   DATETIME                 DEFAULT NULL COMMENT '创建时间',
    `update_by`     BIGINT UNSIGNED          DEFAULT NULL COMMENT '最后更新者ID',
    `update_time`   DATETIME                 DEFAULT NULL COMMENT '最后更新时间',
    `is_deleted`    TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '是否逻辑删除 (0:未删除, 1:已删除)',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `uq_user_account_active` (`user_account`, `is_deleted`),
    KEY `idx_user_deleted` (`is_deleted`),
    CONSTRAINT `fk_user_create_by` FOREIGN KEY (`create_by`) REFERENCES `tb_system_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT `fk_user_update_by` FOREIGN KEY (`update_by`) REFERENCES `tb_system_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='管理端用户表';

-- -------------------------------
-- Table structure for tb_language
-- -------------------------------

DROP TABLE IF EXISTS `tb_language`;
CREATE TABLE `tb_language`
(
    `language_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '语言ID (主键)',
    `name`        VARCHAR(20)     NOT NULL COMMENT '语言名称 (Java 11, Python 3.9, C++17)',
    `is_enabled`  TINYINT(1)      NOT NULL DEFAULT TRUE COMMENT '是否物理启用该语言选项 (1:启用, 0:禁用)',
    `create_by`   BIGINT UNSIGNED          DEFAULT NULL COMMENT '创建者ID',
    `create_time` DATETIME                 DEFAULT NULL COMMENT '创建时间',
    `update_by`   BIGINT UNSIGNED          DEFAULT NULL COMMENT '最后更新者ID',
    `update_time` DATETIME                 DEFAULT NULL COMMENT '最后更新时间',
    `is_deleted`  TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '是否逻辑删除 (0:未删除, 1:已删除)',
    PRIMARY KEY (`language_id`),
    UNIQUE KEY `uq_language_name_active` (`name`, `is_deleted`),
    KEY `idx_lang_deleted` (`is_deleted`),
    CONSTRAINT `fk_lang_create_by` FOREIGN KEY (`create_by`) REFERENCES `tb_system_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT `fk_lang_update_by` FOREIGN KEY (`update_by`) REFERENCES `tb_system_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='支持的编程语言表';

-- -------------------------------
-- Table structure for tb_question
-- -------------------------------

DROP TABLE IF EXISTS `tb_question`;
CREATE TABLE `tb_question`
(
    `question_id` BIGINT UNSIGNED NOT NULL COMMENT '题目id (主键)',
    `title`       VARCHAR(150)    NOT NULL COMMENT '题目标题',
    `difficulty`  TINYINT         NOT NULL COMMENT '题目难度 1:简单 2:中等 3:困难',
    `content`     TEXT            NOT NULL COMMENT '题目描述内容 (Markdown)',
    `tags`        VARCHAR(255)             DEFAULT NULL COMMENT '题目标签 (逗号分隔,"数组,动态规划")',
    `source`      VARCHAR(100)             DEFAULT NULL COMMENT '题目来源',
    `hint`        TEXT                     DEFAULT NULL COMMENT '题目提示',
    `create_by`   BIGINT UNSIGNED          DEFAULT NULL COMMENT '创建者ID',
    `create_time` DATETIME                 DEFAULT NULL COMMENT '创建时间',
    `update_by`   BIGINT UNSIGNED          DEFAULT NULL COMMENT '最后更新者ID',
    `update_time` DATETIME                 DEFAULT NULL COMMENT '最后更新时间',
    `is_deleted`  TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '是否逻辑删除 (0:未删除, 1:已删除)',
    PRIMARY KEY (`question_id`),
    UNIQUE KEY `uq_question_title_active` (`title`, `is_deleted`),
    KEY `idx_question_difficulty` (`difficulty`),
    KEY `idx_question_create_by` (`create_by`),
    KEY `idx_question_update_by` (`update_by`),
    KEY `idx_question_deleted` (`is_deleted`),
    CONSTRAINT `fk_question_create_by` FOREIGN KEY (`create_by`) REFERENCES `tb_system_user` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_question_update_by` FOREIGN KEY (`update_by`) REFERENCES `tb_system_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='题目主信息表';

-- ----------------------------------------
-- Table structure for tb_question_language
-- ----------------------------------------

DROP TABLE IF EXISTS `tb_question_language`;
CREATE TABLE `tb_question_language`
(
    `question_language_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '题目语言详情ID (主键)',
    `question_id`          BIGINT UNSIGNED NOT NULL COMMENT '题目ID (外键, 关联 tb_question)',
    `language_id`          BIGINT UNSIGNED NOT NULL COMMENT '语言ID (外键, 关联 tb_language)',
    `time_limit`           INT UNSIGNED    NOT NULL COMMENT '该语言的时间限制 (ms)',
    `space_limit`          INT UNSIGNED    NOT NULL COMMENT '该语言的空间限制 (MB)',
    `default_code`         TEXT            NOT NULL COMMENT '该语言的默认代码模板',
    `main_func`            TEXT                     DEFAULT NULL COMMENT '主函数',
    `create_by`            BIGINT UNSIGNED          DEFAULT NULL COMMENT '创建者ID',
    `create_time`          DATETIME                 DEFAULT NULL COMMENT '创建时间',
    `update_by`            BIGINT UNSIGNED          DEFAULT NULL COMMENT '最后更新者ID',
    `update_time`          DATETIME                 DEFAULT NULL COMMENT '最后更新时间',
    `is_deleted`           TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '是否逻辑删除 (0:未删除, 1:已删除)',
    PRIMARY KEY (`question_language_id`),
    UNIQUE KEY `uq_ql_active` (`question_id`, `language_id`, `is_deleted`),
    KEY `idx_ql_question_id` (`question_id`),
    KEY `idx_ql_language_id` (`language_id`),
    KEY `idx_ql_deleted` (`is_deleted`),
    CONSTRAINT `fk_ql_question` FOREIGN KEY (`question_id`) REFERENCES `tb_question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_ql_language` FOREIGN KEY (`language_id`) REFERENCES `tb_language` (`language_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='题目与语言关联及特定详情表';

-- ------------------------------------
-- Table structure for tb_question_case
-- ------------------------------------

DROP TABLE IF EXISTS `tb_question_case`;
CREATE TABLE `tb_question_case`
(
    `case_id`     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用例ID (主键)',
    `question_id` BIGINT UNSIGNED NOT NULL COMMENT '题目ID',
    `input`       TEXT            NOT NULL COMMENT '输入数据',
    `output`      TEXT            NOT NULL COMMENT '预期输出数据',
    `is_sample`   INT             NOT NULL DEFAULT FALSE COMMENT '是否为示例用例 (n:是 (也表示样例的顺序), 0:否)',
    `score`       INT UNSIGNED             DEFAULT 10 COMMENT '该测试点的分数 (可选, 用于部分分)',
    `create_by`   BIGINT UNSIGNED          DEFAULT NULL COMMENT '创建者ID',
    `create_time` DATETIME                 DEFAULT NULL COMMENT '创建时间',
    `update_by`   BIGINT UNSIGNED          DEFAULT NULL COMMENT '最后更新者ID',
    `update_time` DATETIME                 DEFAULT NULL COMMENT '最后更新时间',
    `is_deleted`  TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '是否逻辑删除 (0:未删除, 1:已删除)',
    PRIMARY KEY (`case_id`),
    KEY `idx_qc_question_id` (`question_id`),
    KEY `idx_qc_deleted` (`is_deleted`),
    CONSTRAINT `fk_qc_question` FOREIGN KEY (`question_id`) REFERENCES `tb_question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='题目测试用例表';

-- ------------------------------
-- Table structure for tb_contest
-- ------------------------------

DROP TABLE IF EXISTS `tb_contest`;
CREATE TABLE `tb_contest`
(
    `contest_id`        BIGINT UNSIGNED NOT NULL COMMENT '竞赛id (主键)',
    `title`             VARCHAR(150)    NOT NULL COMMENT '竞赛标题',
    `description`       TEXT                     DEFAULT NULL COMMENT '竞赛描述 (Markdown)',
    `start_time`        DATETIME        NOT NULL COMMENT '竞赛开始时间',
    `end_time`          DATETIME        NOT NULL COMMENT '竞赛结束时间',
    `status`            TINYINT         NOT NULL DEFAULT 0 COMMENT '状态 0:未发布 1:以发布',
    `allowed_languages` VARCHAR(255)             DEFAULT NULL COMMENT '允许的语言ID列表 (逗号分隔, null表示允许所有)',
    `create_by`         BIGINT UNSIGNED          DEFAULT NULL COMMENT '创建者ID',
    `create_time`       DATETIME                 DEFAULT NULL COMMENT '创建时间',
    `update_by`         BIGINT UNSIGNED          DEFAULT NULL COMMENT '最后更新者ID',
    `update_time`       DATETIME                 DEFAULT NULL COMMENT '最后更新时间',
    `is_deleted`        TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '是否逻辑删除 (0:未删除, 1:已删除)',
    PRIMARY KEY (`contest_id`),
    KEY `idx_contest_time` (`start_time`, `end_time`),
    KEY `idx_contest_status` (`status`),
    KEY `idx_contest_create_by` (`create_by`),
    KEY `idx_contest_update_by` (`update_by`),
    KEY `idx_contest_deleted` (`is_deleted`),
    CONSTRAINT `fk_contest_create_by` FOREIGN KEY (`create_by`) REFERENCES `tb_system_user` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_contest_update_by` FOREIGN KEY (`update_by`) REFERENCES `tb_system_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='竞赛信息表';


-- ----------------------------------------
-- Table structure for tb_contest_question
-- ----------------------------------------

DROP TABLE IF EXISTS `tb_contest_question`;
CREATE TABLE `tb_contest_question`
(
    `contest_question_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '竞赛题目关系id (主键)',
    `contest_id`          BIGINT UNSIGNED NOT NULL COMMENT '竞赛id (外键)',
    `question_id`         BIGINT UNSIGNED NOT NULL COMMENT '题目id (外键)',
    `display_order`       INT             NOT NULL COMMENT '题目顺序',
    `display_title`       VARCHAR(150)             DEFAULT NULL COMMENT '竞赛中显示的题目标题 (可选, 覆盖题目原标题)',
    `score`               INT UNSIGNED    NOT NULL DEFAULT 10 COMMENT '该题目在竞赛中的基础分值',
    `create_by`           BIGINT UNSIGNED          DEFAULT NULL COMMENT '创建者ID',
    `create_time`         DATETIME                 DEFAULT NULL COMMENT '创建时间',
    `update_by`           BIGINT UNSIGNED          DEFAULT NULL COMMENT '最后更新者ID',
    `update_time`         DATETIME                 DEFAULT NULL COMMENT '最后更新时间',
    `is_deleted`          TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '是否逻辑删除 (0:未删除, 1:已删除)',
    PRIMARY KEY (`contest_question_id`),
    KEY `idx_cq_contest_id` (`contest_id`),
    KEY `idx_cq_question_id` (`question_id`),
    KEY `idx_cq_create_by` (`create_by`),
    KEY `idx_cq_update_by` (`update_by`),
    KEY `idx_cq_deleted` (`is_deleted`),
    CONSTRAINT `fk_cq_contest` FOREIGN KEY (`contest_id`) REFERENCES `tb_contest` (`contest_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_cq_question` FOREIGN KEY (`question_id`) REFERENCES `tb_question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_cq_create_by` FOREIGN KEY (`create_by`) REFERENCES `tb_system_user` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_cq_update_by` FOREIGN KEY (`update_by`) REFERENCES `tb_system_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='竞赛与题目关联表';

-- ----------------------------------------
-- Table structure for tb_user
-- ----------------------------------------

CREATE TABLE `tb_user`
(
    user_id     BIGINT UNSIGNED NOT NULL COMMENT '用户id（主键）',
    nick_name   VARCHAR(20) COMMENT '用户昵称',
    head_image  VARCHAR(100) COMMENT '用户头像',
    sex         TINYINT COMMENT '用户状态1: 男  2：女',
    phone       CHAR(11)        NOT NULL COMMENT '手机号',
    code        CHAR(6) COMMENT '验证码',
    email       VARCHAR(32) COMMENT '邮箱',
    wechat      VARCHAR(20) COMMENT '微信号',
    school_name VARCHAR(20) COMMENT '学校',
    major_name  VARCHAR(20) COMMENT '专业',
    introduce   VARCHAR(100) COMMENT '个人介绍',
    status      TINYINT         NOT NULL COMMENT '用户状态0: 拉黑  1：正常',
    create_by   BIGINT UNSIGNED NOT NULL COMMENT '创建人',
    create_time DATETIME        NOT NULL COMMENT '创建时间',
    update_by   BIGINT UNSIGNED COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (`user_id`)
)
