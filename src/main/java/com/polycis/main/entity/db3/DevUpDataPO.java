package com.polycis.main.entity.db3;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 上行数据对象
 * @author weiao
 * */
@Data
@TableName(value = "dev_data_up")
public class DevUpDataPO implements Serializable {
    /*自增主键*/
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    /*设备uuid*/
    private String deviceUuid;
    /*设备所属类型*/
    private Integer platform;
    /*设备mac*/
    private String mac;
    /*原始paylaod数据*/
    private String encodeData;
    /*解析后payload*/
    private String decodeData;
    /*明文数据*/
    private String dataInfo;
    /*推送时间*/
    private Date pushTime;
    /*推送状态 0 未推送 1 推送成功 1 推送失败*/
    private Integer pushStatus;
    /*数据上报时间*/
    private Date reportTime;
    /*创建时间*/
    private Date createTime;
    /*修改时间*/
    private Date modifyTime;

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceUuid() {
        return deviceUuid;
    }

    public void setDeviceUuid(String deviceUuid) {
        this.deviceUuid = deviceUuid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getEncodeData() {
        return encodeData;
    }

    public void setEncodeData(String encodeData) {
        this.encodeData = encodeData;
    }

    public String getDecodeData() {
        return decodeData;
    }

    public void setDecodeData(String decodeData) {
        this.decodeData = decodeData;
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

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
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
        return "DevUpDataPO{" +
                "id=" + id +
                ", deviceUuid='" + deviceUuid + '\'' +
                ", mac='" + mac + '\'' +
                ", encodeData='" + encodeData + '\'' +
                ", decodeData='" + decodeData + '\'' +
                ", dataInfo='" + dataInfo + '\'' +
                ", pushTime=" + pushTime +
                ", pushStatus=" + pushStatus +
                ", reportTime=" + reportTime +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
