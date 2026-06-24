-- 方式二：创建新表（推荐，结构更清晰）
CREATE TABLE gtStore.user_new (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    name VARCHAR(64) NOT NULL COMMENT '用户名（登录账号）',
    password VARCHAR(255) NOT NULL COMMENT '加密密码',
    nick_name VARCHAR(64) DEFAULT NULL COMMENT '昵称',
    phone VARCHAR(11) DEFAULT NULL COMMENT '手机号',
    avatar VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
    role_name VARCHAR(64) DEFAULT NULL COMMENT '角色标识',
    status TINYINT(4) DEFAULT 1 COMMENT '状态：0禁用 1启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 迁移旧数据
INSERT INTO gtStore.user_new (name, password, phone)
SELECT name, password, phone FROM gtStore.user;

-- 替换旧表（谨慎操作，先备份）
DROP TABLE gtStore.user;
RENAME TABLE gtStore.user_new TO gtStore.user;