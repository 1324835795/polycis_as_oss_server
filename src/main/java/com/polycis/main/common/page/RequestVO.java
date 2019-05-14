package com.polycis.main.common.page;

import java.io.Serializable;
import java.util.Map;

public class RequestVO implements Serializable {
    /**
     * 分页信息
     */
    private PageInfoVO pageInfo = new PageInfoVO();

    /**
     * 具体参数
     */
    private Map<String, Object> data;

    public PageInfoVO getPageInfo() {
        return pageInfo;
}

    public void setPageInfo(PageInfoVO pageInfo) {
        this.pageInfo = pageInfo;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RequestVO{" + "pageInfo=" + pageInfo + ", data=" + data + '}';
    }
}
