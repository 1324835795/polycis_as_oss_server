package com.polycis.main.entity.lora;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 网关
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
@TableName("lora_gateway")
public class Gateway implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 网关配置文件名称
     */
    private String name;
    /**
     * 网关mac
     */
    private String mac;
    /**
     * 网关id
     */
    @TableField("gatewayId")
    private String gatewayId;
    /**
     * 网关配置文件id
     */
    @TableField("gatewayProfileID")
    private String gatewayProfileID;
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
     * 描述
     */
    private String description;
    /**
     * 启用网关发现
     */
    @TableField("discoveryEnabled")
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
     * 挂高
     */
    private Integer hightUp;
    /**
     * 生产厂家
     */
    private String factory;
    /**
     * 网关类别,0:室内，1:室外
     */
    private String type;
    /**
     * 天线类型,0:定向，1:全向
     */
    private String antennaType;
    /**
     * 天线增益(dB)
     */
    private Integer antennaDb;
    /**
     * 网关工作频段(0-9)
     *
     AS_923  0
     AU_915_928  1
     CN_470_510  2
     CN_779_787  3
     EU_433  4
     EU_863_870  5
     IN_865_867  6
     KR_920_923  7
     RU_864_870  8
     US_902_928  9
     */
    private String workFreq;
    private Date createTime;
    private Date updateTime;


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

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
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

    public Integer getHightUp() {
        return hightUp;
    }

    public void setHightUp(Integer hightUp) {
        this.hightUp = hightUp;
    }


    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAntennaType() {
        return antennaType;
    }

    public void setAntennaType(String antennaType) {
        this.antennaType = antennaType;
    }

    public Integer getAntennaDb() {
        return antennaDb;
    }

    public void setAntennaDb(Integer antennaDb) {
        this.antennaDb = antennaDb;
    }

    public String getWorkFreq() {
        return workFreq;
    }

    public void setWorkFreq(String workFreq) {
        this.workFreq = workFreq;
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

    @Override
    public String toString() {
        return "Gateway{" +
                ", id=" + id +
                ", name=" + name +
                ", mac=" + mac +
                ", gatewayId=" + gatewayId +
                ", gatewayProfileID=" + gatewayProfileID +
                ", networkServerID=" + networkServerID +
                ", organizationID=" + organizationID +
                ", description=" + description +
                ", discoveryEnabled=" + discoveryEnabled +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", hightUp=" + hightUp +
                ", factory=" + factory +
                ", type=" + type +
                ", antennaType=" + antennaType +
                ", antennaDb=" + antennaDb +
                ", workFreq=" + workFreq +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
