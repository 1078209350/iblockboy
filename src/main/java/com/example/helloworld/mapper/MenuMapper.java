package com.example.helloworld.mapper;

import com.example.helloworld.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {

    /**
     * 按角色名查该角色有权限访问的启用状态菜单，
     * 结果为扁平列表，服务层再按 parent_id 拼成树。
     */
    List<Menu> findMenusByRoleName(@Param("roleName") String roleName);
}
