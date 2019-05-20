package com.polycis.main.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
@TableName("iot_gateway_pro")
public class GatewayPro implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 配置文件名
     */
    private String name;
    /**
     * 网关配置文件
     */
    private String gatewayProfileId;
    /**
     * 网络id
     */
    private Integer netId;
    /**
     * 频道表
     */
    private String channels;
    /**
     * 第三方服务id
     */
    private String otherServerId;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 删除标识
     */
    private Integer del;
    /**
     * 组织id
     */
    private Integer orgId;

    @TableField(exist=false)
    private List<GatewayProChannel> gatewayProChannel;

    @TableField(exist=false)
    private String total;

    public List<GatewayProChannel> getGatewayProChannel() {
        return gatewayProChannel;
    }

    public void setGatewayProChannel(List<GatewayProChannel> gatewayProChannel) {
        this.gatewayProChannel = gatewayProChannel;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

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

    public String getGatewayProfileId() {
        return gatewayProfileId;
    }

    public void setGatewayProfileId(String gatewayProfileId) {
        this.gatewayProfileId = gatewayProfileId;
    }

    public Integer getNetId() {
        return netId;
    }

    public void setNetId(Integer netId) {
        this.netId = netId;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public String getOtherServerId() {
        return otherServerId;
    }

    public void setOtherServerId(String otherServerId) {
        this.otherServerId = otherServerId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return "GatewayPro{" +
        ", id=" + id +
        ", name=" + name +
        ", gatewayProfileId=" + gatewayProfileId +
        ", netId=" + netId +
        ", channels=" + channels +
        ", otherServerId=" + otherServerId +
        ", updateTime=" + updateTime +
        ", createTime=" + createTime +
        ", del=" + del +
        ", orgId=" + orgId +
        "}";
    }
}
