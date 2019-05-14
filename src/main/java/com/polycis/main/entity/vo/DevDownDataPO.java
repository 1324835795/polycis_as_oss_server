package com.polycis.main.entity.vo;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 下行数据对象
 *
 * @author weiao
 */
public class DevDownDataPO implements Serializable {

    private Integer id;
    /*设备唯一标识*/
    private String deviceUuid;
    /*平台类型 1 lora 2 nb 3 mqtt*/
    private Integer platform;
    /*mac地址*/
    private String mac;
    /*下发原始数据*/
    private String downData;
    /*下发编码后数据*/
    private String encodeData;
    /*下发消息状态 1 成功 0 失败*/
    private String downStatus;
    /*0 只发一次 1 保证收到一次 2 保证收到*/
    private Integer qos;
    /*创建时间*/
    private Date createTime;
    /*修改时间*/
    private Date modifyTime;

    private List<String> devUuidList;

    public List<String> getDevUuidList() {
        return devUuidList;
    }

    public void setDevUuidList(List<String> devUuidList) {
        this.devUuidList = devUuidList;
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

    public String getDownData() {
        return downData;
    }

    public void setDownData(String downData) {
        this.downData = downData;
    }

    public String getDownStatus() {
        return downStatus;
    }

    public void setDownStatus(String downStatus) {
        this.downStatus = downStatus;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
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

    public String getEncodeData() {
        return encodeData;
    }

    public void setEncodeData(String encodeData) {
        this.encodeData = encodeData;
    }
}
