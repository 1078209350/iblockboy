package com.example.helloworld.utils;

/**
 * @author guantong
 */
public class PageParam {
    private int page;
    private Integer size;
    private Integer totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "PageParam{" +
                "page=" + page +
                ", size=" + size +
                ", totalPages=" + totalPages +
                '}';
    }
}
