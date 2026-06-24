CREATE TABLE gtStore.role (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    role_name VARCHAR(64) NOT NULL COMMENT '角色标识（唯一，如 admin/guest/manager）',
    role_desc VARCHAR(128) DEFAULT NULL COMMENT '角色描述（如 超级管理员）',
    status TINYINT(4) DEFAULT 1 COMMENT '状态：0禁用 1启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_name (role_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';