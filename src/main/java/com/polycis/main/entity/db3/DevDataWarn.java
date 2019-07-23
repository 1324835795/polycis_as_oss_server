package com.polycis.main.entity.db3;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author ${author}
 * @since 2019-04-25
 */
@TableName("dev_data_warn")
public class DevDataWarn implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 设备uuid
     */
    private String deviceUuid;
    /**
     * 1 lora 2 nb 3 mqtt
     */
    private Integer platform;
    /**
     * 设备mac
     */
    private String mac;
    /**
     * 1、离线告警 2、 故障告警 3、电量告警 4、温度告警
     */
    private String eventType;
    /**
     * 解密码payload
     */
    private String decodeData;
    /**
     * 原始paylaod数据
     */
    private String encodeData;
    /**
     * 明文数据
     */
    private String dataInfo;
    /**
     * 对外推送时间
     */
    private Date pushTime;
    /**
     * 推送状态 0 未推送 1 推送成功 1 推送失败
     */
    private Integer pushStatus;
    /**
     * 告警开始时间(数据上报时间)
     */
    private Date startTime;
    /**
     * 告警结束时间（离线告警）
     */
    private Date endTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 告警状态 0 未处理   1 处理中   2 已经处理
     */
    private Integer status;

    /**
     *是否已读 0 未读 1 已读
     */
    private Integer readStatus;

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceUuid() {
        return deviceUuid;
    }

    public void setDeviceUuid(String deviceUuid) {
        this.deviceUuid = deviceUuid;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDecodeData() {
        return decodeData;
    }

    public void setDecodeData(String decodeData) {
        this.decodeData = decodeData;
    }

    public String getEncodeData() {
        return encodeData;
    }

    public void setEncodeData(String encodeData) {
        this.encodeData = encodeData;
    }

    public String getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(String dataInfo) {
        this.dataInfo = dataInfo;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "DevDataWarn{" +
                "id=" + id +
                ", deviceUuid='" + deviceUuid + '\'' +
                ", platform=" + platform +
                ", mac='" + mac + '\'' +
                ", eventType='" + eventType + '\'' +
                ", decodeData='" + decodeData + '\'' +
                ", encodeData='" + encodeData + '\'' +
                ", dataInfo='" + dataInfo + '\'' +
                ", pushTime=" + pushTime +
                ", pushStatus=" + pushStatus +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", status=" + status +
                ", readStatus=" + readStatus +
                ", type=" + type +
                '}';
    }
}

