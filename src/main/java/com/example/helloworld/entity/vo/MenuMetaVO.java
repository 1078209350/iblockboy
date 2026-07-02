package com.example.helloworld.entity.vo;

/**
 * 菜单 meta 字段（对应前端路由的 meta 对象）。
 */
public class MenuMetaVO {

    private String title;
    private String icon;
    private Boolean hidden;
    private Boolean alwaysShow;
    private Boolean noCache;
    private Boolean affix;
    private Boolean noTagsView;
    private Boolean breadcrumb;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public Boolean getHidden() { return hidden; }
    public void setHidden(Boolean hidden) { this.hidden = hidden; }

    public Boolean getAlwaysShow() { return alwaysShow; }
    public void setAlwaysShow(Boolean alwaysShow) { this.alwaysShow = alwaysShow; }

    public Boolean getNoCache() { return noCache; }
    public void setNoCache(Boolean noCache) { this.noCache = noCache; }

    public Boolean getAffix() { return affix; }
    public void setAffix(Boolean affix) { this.affix = affix; }

    public Boolean getNoTagsView() { return noTagsView; }
    public void setNoTagsView(Boolean noTagsView) { this.noTagsView = noTagsView; }

    public Boolean getBreadcrumb() { return breadcrumb; }
    public void setBreadcrumb(Boolean breadcrumb) { this.breadcrumb = breadcrumb; }
}
