package com.polycis.main.controller.callname;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 演示点名表
 * </p>
 *
 * @author ${author}
 * @since 2019-03-25
 */
public class DemoCallName implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String uid;
    private String devid;
    /**
     * 在线标签数组
     */
    private String tagOn;
    /**
     * 离线标签数组
     */
    private String tagOff;
    /**
     * 总数据
     */
    private String tagAll;
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 启动扫描时间
     */
    private Integer scanTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public String getTagOn() {
        return tagOn;
    }

    public void setTagOn(String tagOn) {
        this.tagOn = tagOn;
    }

    public String getTagOff() {
        return tagOff;
    }

    public void setTagOff(String tagOff) {
        this.tagOff = tagOff;
    }

    public String getTagAll() {
        return tagAll;
    }

    public void setTagAll(String tagAll) {
        this.tagAll = tagAll;
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

    public Integer getScanTime() {
        return scanTime;
    }

    public void setScanTime(Integer scanTime) {
        this.scanTime = scanTime;
    }

    @Override
    public String toString() {
        return "DemoCallName{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", devid='" + devid + '\'' +
                ", tagOn='" + tagOn + '\'' +
                ", tagOff='" + tagOff + '\'' +
                ", tagAll='" + tagAll + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", scanTime=" + scanTime +
                '}';
    }
}
