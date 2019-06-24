package com.polycis.main.entity.script;

import java.io.Serializable;

/**
 * @auther cheng
 * @date 2019-06-24
 */
public class ProductRelScriptVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String scriptContent;
    private String operType;
    private String param;

    public String getScriptContent() {
        return scriptContent;
    }

    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
