package com.polycis.main.entity;

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
 * @since 2019-04-28
 */
@TableName("iot_network_data")
public class IotNetworkData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 网络账户标识
     */
    private Integer sysserviceid;
    /**
     * 类型1设备，2网关
     */
    private Integer type;
    /**
     * 数据topic
     */
    private String topic;
    /**
     * RX参数
     */
    private String jsondata;
    /**
     * 创建时间
     */
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysserviceid() {
        return sysserviceid;
    }

    public void setSysserviceid(Integer sysserviceid) {
        this.sysserviceid = sysserviceid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getJsondata() {
        return jsondata;
    }

    public void setJsondata(String jsondata) {
        this.jsondata = jsondata;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "IotNetworkData{" +
        ", id=" + id +
        ", sysserviceid=" + sysserviceid +
        ", type=" + type +
        ", topic=" + topic +
        ", jsondata=" + jsondata +
        ", createTime=" + createTime +
        "}";
    }
}
