CREATE TABLE gtStore.role_menu (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    role_id BIGINT UNSIGNED NOT NULL COMMENT '角色ID',
    menu_id BIGINT UNSIGNED NOT NULL COMMENT '菜单ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_menu (role_id, menu_id),
    KEY idx_menu_id (menu_id),
    CONSTRAINT fk_role_menu_role_id FOREIGN KEY (role_id) REFERENCES gtStore.role(id) ON DELETE CASCADE,
    CONSTRAINT fk_role_menu_menu_id FOREIGN KEY (menu_id) REFERENCES gtStore.menu(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';