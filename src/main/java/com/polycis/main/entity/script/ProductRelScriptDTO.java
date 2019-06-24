package com.polycis.main.entity.script;

import java.io.Serializable;

/**
 * <p>
 *     js脚本传输entity
 * </p>
 *
 * @author cg
 * @since 2019-06-19
 */
public class ProductRelScriptDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 产品ID
     */
    private Integer productId;

    /**
     * 脚本内容
     */
    private String scriptContent;

    /**
     * 脚本草稿
     */
    private String scriptDraft;

    /**
     * 操作类型 0:上报数据, 1:下发数据
     */
    private String operType;

    /**
     * 针对operType传入的参数数据
     */
    private String param;


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getScriptContent() {
        return scriptContent;
    }

    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }

    public String getScriptDraft() {
        return scriptDraft;
    }

    public void setScriptDraft(String scriptDraft) {
        this.scriptDraft = scriptDraft;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
