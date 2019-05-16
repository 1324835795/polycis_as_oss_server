package com.polycis.main.entity.lora;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 服务配置文件
 * </p>
 *
 * @author ${author}
 * @since 2019-05-15
 */
@TableName("lora_service_profile")
public class ServiceProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 服务配置文件名字
     */
    private String name;

    /**
     * 对应Lora中的文件id
     */
    @TableField("serviceProfileId")
    private String serviceProfileId;
    /**
     * 网络服务id
     */
    @TableField("networkServerID")
    private String networkServerID;
    /**
     * 组织id
     */
    @TableField("organizationID")
    private String organizationID;
    /**
     * 是否添加网关元数据
     */
    @TableField("addGWMetaData")
    private Boolean addGWMetaData;
    /**
     * 设备状态请求频率
     */
    @TableField("devStatusReqFreq")
    private Integer devStatusReqFreq;
    /**
     * 向应用程序服务器报告设备电池电量
     */
    @TableField("reportDevStatusBattery")
    private Boolean reportDevStatusBattery;
    /**
     * 向应用程序服务器报告设备链接边距
     */
    @TableField("reportDevStatusMargin")
    private Boolean reportDevStatusMargin;
    /**
     * 最低允许数据速率。用于ADR
     */
    @TableField("drMin")
    private Integer drMin;
    /**
     * 最大允许数据速率。用于ADR。
     */
    @TableField("drMax")
    private Integer drMax;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;






    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetworkServerID() {
        return networkServerID;
    }

    public void setNetworkServerID(String networkServerID) {
        this.networkServerID = networkServerID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public Integer getDevStatusReqFreq() {
        return devStatusReqFreq;
    }

    public void setDevStatusReqFreq(Integer devStatusReqFreq) {
        this.devStatusReqFreq = devStatusReqFreq;
    }

    public Integer getDrMin() {
        return drMin;
    }

    public void setDrMin(Integer drMin) {
        this.drMin = drMin;
    }

    public Integer getDrMax() {
        return drMax;
    }

    public void setDrMax(Integer drMax) {
        this.drMax = drMax;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public String getServiceProfileId() {
        return serviceProfileId;
    }

    public void setServiceProfileId(String serviceProfileId) {
        this.serviceProfileId = serviceProfileId;
    }

    public Boolean getAddGWMetaData() {
        return addGWMetaData;
    }

    public void setAddGWMetaData(Boolean addGWMetaData) {
        this.addGWMetaData = addGWMetaData;
    }

    public Boolean getReportDevStatusBattery() {
        return reportDevStatusBattery;
    }

    public void setReportDevStatusBattery(Boolean reportDevStatusBattery) {
        this.reportDevStatusBattery = reportDevStatusBattery;
    }

    public Boolean getReportDevStatusMargin() {
        return reportDevStatusMargin;
    }

    public void setReportDevStatusMargin(Boolean reportDevStatusMargin) {
        this.reportDevStatusMargin = reportDevStatusMargin;
    }

    @Override
    public String toString() {
        return "ServiceProfile{" +
        ", id=" + id +
        ", name=" + name +
        ", networkServerID=" + networkServerID +
        ", organizationID=" + organizationID +
        ", addGWMetaData=" + addGWMetaData +
        ", devStatusReqFreq=" + devStatusReqFreq +
        ", reportDevStatusBattery=" + reportDevStatusBattery +
        ", reportDevStatusMargin=" + reportDevStatusMargin +
        ", drMin=" + drMin +
        ", drMax=" + drMax +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
