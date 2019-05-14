package com.polycis.main.common.page;

import java.io.Serializable;

/**
 * 分页对象
 * @author weitt
 * @date 2017/1/25.
 */
public class PageConfig implements Serializable {
    private int pageNo;          //当前页码
    private int pageSize;        //每页行数
    private int totalRecord;      //总记录数
    private int totalPage;        //总页数


    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }



}
