# 创建数据库
CREATE DATABASE IF NOT EXISTS `horizon_dev`;
# 创建horizon用户
CREATE USER `horizon`@`%` IDENTIFIED BY 'horizon';
# 赋予用户操作 `horizon_dev` 数据库的权限
GRANT ALTER,CREATE, DROP, SELECT, INSERT, UPDATE, DELETE ON horizon_dev.* TO `horizon`@`%`;
# 赋予用户操作 `horizon_nacos_dev` 数据库的权限
GRANT ALTER,CREATE, DROP, SELECT, INSERT, UPDATE, DELETE ON horizon_nacos_dev.* TO `horizon`@`%`;
# 选中数据库
USE horizon_dev;
# 创建测试表
CREATE TABLE `tb_test`
(
    `test_id` BIGINT UNSIGNED NOT NULL,
    `title`   TEXT            NOT NULL,
    `content` TEXT            NOT NULL,
    PRIMARY KEY (`test_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO tb_test VALUES (1, 'test', 'test');
SELECT * FROM tb_test;
UPDATE tb_test SET title = 'test_update' WHERE test_id = 1;