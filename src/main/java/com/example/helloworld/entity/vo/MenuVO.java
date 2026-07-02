package com.example.helloworld.entity.vo;

import com.example.helloworld.entity.Menu;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * 前端要求的路由树节点结构，见「后端接口对接文档」第三节。
 * 用 JsonInclude.NON_NULL 让 redirect / children 为空时不出现在 JSON 中。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuVO {

    private String path;
    private String name;
    private String component;
    private String redirect;
    private MenuMetaVO meta;
    private List<MenuVO> children;

    public static MenuVO from(Menu m) {
        MenuVO vo = new MenuVO();
        vo.path = m.getPath();
        vo.name = m.getName();
        vo.component = m.getComponent();
        vo.redirect = m.getRedirect();

        MenuMetaVO meta = new MenuMetaVO();
        meta.setTitle(m.getMetaTitle());
        meta.setIcon(m.getMetaIcon());
        meta.setHidden(m.getMetaHidden());
        meta.setAlwaysShow(m.getMetaAlwaysShow());
        meta.setNoCache(m.getMetaNoCache());
        meta.setAffix(m.getMetaAffix());
        meta.setNoTagsView(m.getMetaNoTagsView());
        meta.setBreadcrumb(m.getMetaBreadcrumb());
        vo.meta = meta;

        return vo;
    }

    public void addChild(MenuVO child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }

    public String getRedirect() { return redirect; }
    public void setRedirect(String redirect) { this.redirect = redirect; }

    public MenuMetaVO getMeta() { return meta; }
    public void setMeta(MenuMetaVO meta) { this.meta = meta; }

    public List<MenuVO> getChildren() { return children; }
    public void setChildren(List<MenuVO> children) { this.children = children; }
}
