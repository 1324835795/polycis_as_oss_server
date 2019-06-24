package com.polycis.main.controller.dataspecs;


import java.io.Serializable;
import java.util.List;

/**
 * 描述:
 * 设备扩展属性
 *
 * @auhtor weitao
 * @create 2019-06-13:47
 */
public class ProductPropertyDTO implements Serializable {

    /**属性唯一标识*/
    private String id;
    /**产品唯一标识（关联产品id）*/
    private Integer productId;
    /**产品唯一key*/
    private String  productKey;
    /**属性唯一标识*/
    private  String identifier;
    /**数据类型定义*/
    private  Integer dataType;
    /**数据规格-基本类型*/
    private Object dataSpecs;
    /**数据规格-bool*/
    private List<Object> dataSpecsList;
    /**属性名称*/
    private  String name;
    /**读写模式*/
    private  String accessMode;
    /**是否必填*/
    private  Boolean required;
    /**描述*/
    private  String desc;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public List<Object> getDataSpecsList() {
        return dataSpecsList;
    }

    public void setDataSpecsList(List<Object> dataSpecsList) {
        this.dataSpecsList = dataSpecsList;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Object getDataSpecs() {
        return dataSpecs;
    }

    public void setDataSpecs(Object dataSpecs) {
        this.dataSpecs = dataSpecs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(String accessMode) {
        this.accessMode = accessMode;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
