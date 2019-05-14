package com.polycis.main.entity.vo;

import java.util.Date;

/**
 *  监控中心应用信息
 */
public class AppVo {

    /**
     * 应用id
     */
    private Integer id;
    private String name;
    private Date createTime;
    private Integer devCount;
    private Integer alarmCount;

    private String  deviceOnlineRate;


    private String  appEui;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDevCount() {
        return devCount;
    }

    public void setDevCount(Integer devCount) {
        this.devCount = devCount;
    }

    public Integer getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(Integer alarmCount) {
        this.alarmCount = alarmCount;
    }

    public String getDeviceOnlineRate() {
        return deviceOnlineRate;
    }

    public void setDeviceOnlineRate(String deviceOnlineRate) {
        this.deviceOnlineRate = deviceOnlineRate;
    }

    public String getAppEui() {
        return appEui;
    }

    public void setAppEui(String appEui) {
        this.appEui = appEui;
    }

    @Override
    public String toString() {
        return "AppVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", devCount=" + devCount +
                ", alarmCount=" + alarmCount +
                ", deviceOnlineRate='" + deviceOnlineRate + '\'' +
                ", appEui='" + appEui + '\'' +
                '}';
    }
}
