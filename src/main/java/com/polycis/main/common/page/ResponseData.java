package com.polycis.main.common.page;

import java.util.List;

/**
 * Created by weitt on 2017/1/25.
 * @author weitt
 */
public class ResponseData<T> {
    /**返回结果集合*/
    List<T> list;
    /**查询参数对象*/
    private PageConfig pageConfig;


    public ResponseData(){}

    public ResponseData(List<T> list, PageConfig pageConfig){
        this.pageConfig = pageConfig;
        this.list = list;
    }

    public ResponseData(List<T> list, List<T> newestList,PageConfig pageConfig){
        this.pageConfig = pageConfig;
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public PageConfig getPageConfig() {
        return pageConfig;
    }

    public void setPageConfig(PageConfig pageConfig) {
        this.pageConfig = pageConfig;
    }
}
