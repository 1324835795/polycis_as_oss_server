package com.polycis.main.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 网关配置文件频段表
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
@TableName("iot_gateway_pro_channel")
public class GatewayProChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 网关配置文件id
     */
    private String gatewayProfileId;
    /**
     * 带宽
     */
    private String bandwidth;
    /**
     * 比特率
     */
    private String bitrate;
    /**
     * 频率
     */
    private String frequency;
    /**
     * 调制
     */
    private String modulation;
    /**
     * 扩散因子
     */
    private String spreadFactors;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 删除标识(0删除,1未删除)
     */
    private Integer isDelete;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGatewayProfileId() {
        return gatewayProfileId;
    }

    public void setGatewayProfileId(String gatewayProfileId) {
        this.gatewayProfileId = gatewayProfileId;
    }

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getModulation() {
        return modulation;
    }

    public void setModulation(String modulation) {
        this.modulation = modulation;
    }

    public String getSpreadFactors() {
        return spreadFactors;
    }

    public void setSpreadFactors(String spreadFactors) {
        this.spreadFactors = spreadFactors;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "GatewayProChannel{" +
        ", id=" + id +
        ", gatewayProfileId=" + gatewayProfileId +
        ", bandwidth=" + bandwidth +
        ", bitrate=" + bitrate +
        ", frequency=" + frequency +
        ", modulation=" + modulation +
        ", spreadFactors=" + spreadFactors +
        ", createTime=" + createTime +
        ", isDelete=" + isDelete +
        "}";
    }
}
