package com.polycis.main.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
 */
@TableName("iot_product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品分类id
     */
    private Integer classifyId;
    /**
     * 产品平台 1lora 2nb
     */
    private Integer platform;

    /**
     * 产品描述 1lora 2nb
     */
    private String description;

    /**
     * 关联的lora设备模式
     */
    private Integer loraDeviceClass;
    /**
     * 用户id
     */
    private Integer org;
    /**
     * 数据解析方式 0透传 1解析
     */
    private Integer analysisWay;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 删除标识 0未删除 1删除
     */
    private Integer isDelete;


    /**
     * 激活方式 1:ABP 2:OTAA
     */
    private Integer activeWay;

    /**
     * lora设备配置id
     */
    private  Integer loraConfigId;

    /**
     * 设备数量
     */
    private transient Integer devCount;

    /**
     * 设备数量
     */
    private transient String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDevCount() {
        return devCount;
    }

    public void setDevCount(Integer devCount) {
        this.devCount = devCount;
    }

    public Integer getOrg() {
        return org;
    }

    public void setOrg(Integer org) {
        this.org = org;
    }

    public Integer getActiveWay() {
        return activeWay;
    }

    public void setActiveWay(Integer activeWay) {
        this.activeWay = activeWay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Integer getLoraDeviceClass() {
        return loraDeviceClass;
    }

    public void setLoraDeviceClass(Integer loraDeviceClass) {
        this.loraDeviceClass = loraDeviceClass;
    }

    public Integer getAnalysisWay() {
        return analysisWay;
    }

    public void setAnalysisWay(Integer analysisWay) {
        this.analysisWay = analysisWay;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getLoraConfigId() {
        return loraConfigId;
    }

    public void setLoraConfigId(Integer loraConfigId) {
        this.loraConfigId = loraConfigId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classifyId=" + classifyId +
                ", platform=" + platform +
                ", description='" + description + '\'' +
                ", loraDeviceClass=" + loraDeviceClass +
                ", org=" + org +
                ", analysisWay=" + analysisWay +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", isDelete=" + isDelete +
                ", activeWay=" + activeWay +
                ", loraConfigId=" + loraConfigId +
                ", devCount=" + devCount +
                ", username=" + username +
                '}';
    }
}
