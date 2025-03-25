# 创建数据库
CREATE DATABASE IF NOT EXISTS `horizon_dev`;
# 创建horizon用户
CREATE USER `horizon`@`%` IDENTIFIED BY 'horizon';
# 赋予用户操作 `horizon_dev` 数据库的权限
GRANT CREATE, DROP, SELECT, INSERT, UPDATE, DELETE ON horizon_dev.* TO `horizon`@`%`;