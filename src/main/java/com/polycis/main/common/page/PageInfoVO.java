package com.polycis.main.common.page;

import java.io.Serializable;

public class PageInfoVO implements Serializable {


    /**
     * 查询页
     */
    private Integer currentPage;
    /**
     * 每页显示条数
     */
    private Integer pageSize;



    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public String toString() {
        return "PageInfoVO{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                '}';
    }
}
