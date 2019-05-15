package com.polycis.main.entity;

import java.io.Serializable;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/5/15
 * description : 告警级别
 */
public class WarnLevel implements Serializable {
    private int id;
    private int devTypeId;
    private int warnTypeId;
    private int warnLevelId;
    private String devTypeName;
    private String warnTypeName;
    private String warnLevelName;
    private int state;
    private String createTime;
    private String updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(int devTypeId) {
        this.devTypeId = devTypeId;
    }

    public int getWarnTypeId() {
        return warnTypeId;
    }

    public void setWarnTypeId(int warnTypeId) {
        this.warnTypeId = warnTypeId;
    }

    public int getWarnLevelId() {
        return warnLevelId;
    }

    public void setWarnLevelId(int warnLevelId) {
        this.warnLevelId = warnLevelId;
    }

    public String getDevTypeName() {
        return devTypeName;
    }

    public void setDevTypeName(String devTypeName) {
        this.devTypeName = devTypeName;
    }

    public String getWarnTypeName() {
        return warnTypeName;
    }

    public void setWarnTypeName(String warnTypeName) {
        this.warnTypeName = warnTypeName;
    }

    public String getWarnLevelName() {
        return warnLevelName;
    }

    public void setWarnLevelName(String warnLevelName) {
        this.warnLevelName = warnLevelName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
