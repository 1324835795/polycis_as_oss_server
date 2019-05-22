package com.polycis.main.entity.lora;

import java.io.Serializable;

/**
 * <p>
 * 网关 接口传输类
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
public class LoraGatewayDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 网关配置文件名称
     */
    private String name;
    /**
     * 网关配置文件id
     */
    private String gatewayProfileID;
    /**
     * 网络服务id
     */
    private String networkServerID;
    /**
     * 组织id
     */
    private String organizationID;
    /**
     * 描述
     */
    private String description;
    /**
     * 启用网关发现
     */
    private Boolean discoveryEnabled;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;

    /**
     * 海拔高度
     */
    private String altitude;

    /**
     * 精确度
     */
    private Integer accuracy = 0;

    private String source = "UNKNOWN";

    private Object location;

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

    public String getGatewayProfileID() {
        return gatewayProfileID;
    }

    public void setGatewayProfileID(String gatewayProfileID) {
        this.gatewayProfileID = gatewayProfileID;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDiscoveryEnabled() {
        return discoveryEnabled;
    }

    public void setDiscoveryEnabled(Boolean discoveryEnabled) {
        this.discoveryEnabled = discoveryEnabled;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }
}
