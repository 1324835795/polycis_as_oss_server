package com.polycis.main.entity.db2;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-04-22
 */
@TableName("dev_union_device")
public class DevUnionDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 设备eui
     */
    private String devUuid;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 设备类型
     */
    private String type;
    /**
     * 创建时间
     */
    private Date creatTime;
    /**
     * 设备配置文件
     */
    private String devPro;
    /**
     * 优先级
     */
    private String priority;
    /**
     * 设备上报时间
     */
    private Date reportTime;
    /**
     * 在线状态 0不在线 1在线
     */
    private Integer status;

    /**
     * 应用eui
     */
    private String appEui;


    public String getAppEui() {
        return appEui;
    }

    public void setAppEui(String appEui) {
        this.appEui = appEui;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDevUuid() {
        return devUuid;
    }

    public void setDevUuid(String devUuid) {
        this.devUuid = devUuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getDevPro() {
        return devPro;
    }

    public void setDevPro(String devPro) {
        this.devPro = devPro;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DevUnionDevice{" +
                "id=" + id +
                ", devUuid='" + devUuid + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", creatTime=" + creatTime +
                ", devPro='" + devPro + '\'' +
                ", priority='" + priority + '\'' +
                ", reportTime=" + reportTime +
                ", status=" + status +
                ", appEui='" + appEui + '\'' +
                '}';
    }
}
