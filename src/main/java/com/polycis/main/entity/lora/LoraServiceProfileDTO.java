package com.polycis.main.entity.lora;

import java.io.Serializable;

/**
 * <p>
 * 服务配置文件 接口传输类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-15
 */
public class LoraServiceProfileDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 服务配置文件名字
     */
    private String name;

    /**
     * 网络服务id
     */
    private String networkServerID;
    /**
     * 组织id
     */
    private String organizationID;
    /**
     * 是否添加网关元数据
     */
    private Boolean addGWMetaData;
    /**
     * 设备状态请求频率
     */
    private Integer devStatusReqFreq;
    /**
     * 向应用程序服务器报告设备电池电量
     */
    private Boolean reportDevStatusBattery;
    /**
     * 向应用程序服务器报告设备链接边距
     */
    private Boolean reportDevStatusMargin;
    /**
     * 最低允许数据速率。用于ADR
     */
    private Integer drMin;
    /**
     * 最大允许数据速率。用于ADR。
     */
    private Integer drMax;


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

    public Boolean getAddGWMetaData() {
        return addGWMetaData;
    }

    public void setAddGWMetaData(Boolean addGWMetaData) {
        this.addGWMetaData = addGWMetaData;
    }

    public Integer getDevStatusReqFreq() {
        return devStatusReqFreq;
    }

    public void setDevStatusReqFreq(Integer devStatusReqFreq) {
        this.devStatusReqFreq = devStatusReqFreq;
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
}
