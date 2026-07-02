package com.example.helloworld.entity;

/**
 * 菜单/路由表实体，对应 gtStore.menu。
 * meta_* 字段保留数据库列名映射后的形式（metaTitle 等），
 * 输出给前端时再收敛到 MenuVO.meta 对象中。
 */
public class Menu {

    private Long id;
    private Long parentId;
    private String path;
    private String name;
    private String component;
    private String redirect;

    private String metaTitle;
    private String metaIcon;
    private Boolean metaHidden;
    private Boolean metaAlwaysShow;
    private Boolean metaNoCache;
    private Boolean metaAffix;
    private Boolean metaNoTagsView;
    private Boolean metaBreadcrumb;

    private Integer sortOrder;
    private Integer status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }

    public String getRedirect() { return redirect; }
    public void setRedirect(String redirect) { this.redirect = redirect; }

    public String getMetaTitle() { return metaTitle; }
    public void setMetaTitle(String metaTitle) { this.metaTitle = metaTitle; }

    public String getMetaIcon() { return metaIcon; }
    public void setMetaIcon(String metaIcon) { this.metaIcon = metaIcon; }

    public Boolean getMetaHidden() { return metaHidden; }
    public void setMetaHidden(Boolean metaHidden) { this.metaHidden = metaHidden; }

    public Boolean getMetaAlwaysShow() { return metaAlwaysShow; }
    public void setMetaAlwaysShow(Boolean metaAlwaysShow) { this.metaAlwaysShow = metaAlwaysShow; }

    public Boolean getMetaNoCache() { return metaNoCache; }
    public void setMetaNoCache(Boolean metaNoCache) { this.metaNoCache = metaNoCache; }

    public Boolean getMetaAffix() { return metaAffix; }
    public void setMetaAffix(Boolean metaAffix) { this.metaAffix = metaAffix; }

    public Boolean getMetaNoTagsView() { return metaNoTagsView; }
    public void setMetaNoTagsView(Boolean metaNoTagsView) { this.metaNoTagsView = metaNoTagsView; }

    public Boolean getMetaBreadcrumb() { return metaBreadcrumb; }
    public void setMetaBreadcrumb(Boolean metaBreadcrumb) { this.metaBreadcrumb = metaBreadcrumb; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
