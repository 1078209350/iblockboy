package com.example.helloworld.controller;

import com.example.helloworld.entity.Menu;
import com.example.helloworld.entity.vo.MenuVO;
import com.example.helloworld.mapper.MenuMapper;
import com.example.helloworld.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单/动态路由接口。
 * 对接前端 vue-element-plus-admin 的服务端动态路由模式：
 * 登录成功后前端调用 GET /user/menu?roleName=xxx 拉取当前角色可见的路由树，
 * 由 src/utils/routerHelper.ts 的 generateRoutesByServer 转成 Vue 路由并注册。
 */
@RestController
@RequestMapping("/user")
public class MenuController {

    @Autowired
    private MenuMapper menuMapper;

    @ApiOperation("获取当前角色的菜单/路由树")
    @GetMapping("/menu")
    public Result menu(@RequestParam("roleName") String roleName) {
        List<Menu> flat = menuMapper.findMenusByRoleName(roleName);
        List<MenuVO> tree = buildTree(flat);
        // 前端约定顶层 data 直接是数组，不是 { data: [...] }，所以走 data(Object) 整体替换
        return Result.ok().data((Object) tree);
    }

    /**
     * 按 parent_id 组装菜单树。
     * 入参已按 (parent_id, sort_order, id) 排好序，父在子前，兄弟按顺序。
     * parent_id = 0 视为顶级；找不到父节点的孤儿节点当作顶级处理，避免整棵树丢数据。
     */
    private List<MenuVO> buildTree(List<Menu> flat) {
        Map<Long, MenuVO> voById = new HashMap<>();
        List<MenuVO> roots = new ArrayList<>();
        // 先把每条记录转成 VO，方便二次遍历时按 parentId 快速定位
        for (Menu m : flat) {
            voById.put(m.getId(), MenuVO.from(m));
        }
        for (Menu m : flat) {
            MenuVO vo = voById.get(m.getId());
            Long parentId = m.getParentId();
            if (parentId == null || parentId == 0L) {
                roots.add(vo);
            } else {
                MenuVO parent = voById.get(parentId);
                if (parent != null) {
                    parent.addChild(vo);
                } else {
                    // 父节点没被当前角色授权时挂到根，防止子菜单被静默丢弃
                    roots.add(vo);
                }
            }
        }
        return roots;
    }
}
