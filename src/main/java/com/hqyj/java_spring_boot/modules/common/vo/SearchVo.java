package com.hqyj.java_spring_boot.modules.common.vo;

public class SearchVo {
    public final static int DEFAULT_CURRENT_PAGE = 1;//默认分页，第一页
    public final static int DEFAULT_PAGE_SIZE = 5;//默认每页显示记录数，5条

    private int currentPage;//当前页
    private int pageSize;//页大小
    private String keyWord;//关键字
    private String orderBy;//排序
    private String sort;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void initSearchVo() {
        if (this != null) {
            this.setCurrentPage(this.getCurrentPage() == 0 ? DEFAULT_CURRENT_PAGE : this.getCurrentPage());
            this.setPageSize(this.getPageSize() == 0 ? DEFAULT_PAGE_SIZE : this.getPageSize());
        }
    }
}
