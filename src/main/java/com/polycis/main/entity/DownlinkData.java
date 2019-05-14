package com.polycis.main.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 设备下行数据表
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
 */
@TableName("iot_downlink_data")
public class DownlinkData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 设备唯一标识
     */
    private String deviceId;
    /**
     * base64内容
     */
    private String info;
    /**
     * base64解密内容(原始内容)
     */
    private String data;
    /**
     * 发送状态(0 失败,1成功)
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 回调时间
     */
    private Date callbackTime;

    /**
     * 回调时间
     */
    private Integer org;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCallbackTime() {
        return callbackTime;
    }

    public void setCallbackTime(Date callbackTime) {
        this.callbackTime = callbackTime;
    }

    public Integer getOrg() {
        return org;
    }

    public void setOrg(Integer org) {
        this.org = org;
    }

    @Override
    public String toString() {
        return "DownlinkData{" +
                "id=" + id +
                ", deviceId='" + deviceId + '\'' +
                ", info='" + info + '\'' +
                ", data='" + data + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", callbackTime=" + callbackTime +
                ", org=" + org +
                '}';
    }
}
