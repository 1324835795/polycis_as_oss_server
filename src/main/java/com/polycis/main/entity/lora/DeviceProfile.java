package com.polycis.main.entity.lora;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 设备配置文件
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
@TableName("lora_device_profile")
public class DeviceProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 设备配置文件名字
     */
    private String name;
    /**
     * lora设备配置文件id
     */
    @TableField("deviceProfileID")
    private String deviceProfileID;
    /**
     * 网络服务id
     */
    @TableField("networkServerID")
    private String networkServerID;
    /**
     * 组织id
     */
    @TableField("organizationID")
    private String organizationID;
    /**
     * LoRaWAN MAC版本
     */
    @TableField("macVersion")
    private String macVersion;
    /**
     * LoRaWAN区域参数
     */
    @TableField("regParamsRevision")
    private String regParamsRevision;
    /**
     * 出厂预设频率列表（Hz），逗号分隔
     */
    @TableField("factoryPresetFreqsStr")
    private String factoryPresetFreqsStr;
    /**
     * 设备支持的最大EIRP
     */
    @TableField("maxEIRP")
    private Integer maxEIRP;
    /**
     * 设备支持OTAA,0:ABP,1:OTAA
     */
    @TableField("supportsJoin")
    private Boolean supportsJoin;
    /**
     * RX1延迟
     */
    @TableField("rxDelay1")
    private Integer rxDelay1;
    /**
     * RX1数据速率偏移
     */
    @TableField("rxDROffset1")
    private Integer rxDROffset1;
    /**
     * RX2数据速率
     */
    @TableField("rxDataRate2")
    private Integer rxDataRate2;
    /**
     * RX2频道频率
     */
    @TableField("rxFreq2")
    private Integer rxFreq2;
    /**
     * 设备支持Class-B
     */
    @TableField("supportsClassB")
    private Boolean supportsClassB;
    /**
     * B类确认下行链路超时
     */
    @TableField("classBTimeout")
    private Integer classBTimeout;
    /**
     * B类ping槽周期
     */
    @TableField("pingSlotPeriod")
    private Integer pingSlotPeriod;
    /**
     * B类ping槽数据速率
     */
    @TableField("pingSlotDR")
    private Integer pingSlotDR;
    /**
     * B类ping槽频率
     */
    @TableField("pingSlotFreq")
    private Integer pingSlotFreq;
    /**
     * 设备支持Class-C
     */
    @TableField("supportsClassC")
    private Boolean supportsClassC;
    /**
     * C类确认下行链路超时
     */
    @TableField("classCTimeout")
    private Integer classCTimeout;
    /**
     * 设备支持Class-A
     */
    @TableField("supportsClassA")
    private Boolean supportsClassA;

    /**
     * 备注
     */
    private String remark;
    private Date createTime;
    private Date updateTime;

    /**
     * 出厂预设频率列表（Hz），逗号分隔, LoraServer存储用的
     */
    @TableField(exist=false)
    private int[] factoryPresetFreqs;

    //查询条件 class 类型 0:class_a,1:class_b,2:class_c
    @TableField(exist=false)
    private String classType;

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
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

    public String getNetworkServerID() {
        return networkServerID;
    }

    public void setNetworkServerID(String networkServerID) {
        this.networkServerID = networkServerID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public String getMacVersion() {
        return macVersion;
    }

    public void setMacVersion(String macVersion) {
        this.macVersion = macVersion;
    }

    public String getRegParamsRevision() {
        return regParamsRevision;
    }

    public void setRegParamsRevision(String regParamsRevision) {
        this.regParamsRevision = regParamsRevision;
    }

    public String getFactoryPresetFreqsStr() {
        return factoryPresetFreqsStr;
    }

    public void setFactoryPresetFreqsStr(String factoryPresetFreqsStr) {
        this.factoryPresetFreqsStr = factoryPresetFreqsStr;
    }

    public Integer getMaxEIRP() {
        return maxEIRP;
    }

    public void setMaxEIRP(Integer maxEIRP) {
        this.maxEIRP = maxEIRP;
    }


    public Integer getRxDelay1() {
        return rxDelay1;
    }

    public void setRxDelay1(Integer rxDelay1) {
        this.rxDelay1 = rxDelay1;
    }

    public Integer getRxDROffset1() {
        return rxDROffset1;
    }

    public void setRxDROffset1(Integer rxDROffset1) {
        this.rxDROffset1 = rxDROffset1;
    }

    public Integer getRxDataRate2() {
        return rxDataRate2;
    }

    public void setRxDataRate2(Integer rxDataRate2) {
        this.rxDataRate2 = rxDataRate2;
    }

    public Integer getRxFreq2() {
        return rxFreq2;
    }

    public void setRxFreq2(Integer rxFreq2) {
        this.rxFreq2 = rxFreq2;
    }


    public Integer getClassBTimeout() {
        return classBTimeout;
    }

    public void setClassBTimeout(Integer classBTimeout) {
        this.classBTimeout = classBTimeout;
    }

    public Integer getPingSlotPeriod() {
        return pingSlotPeriod;
    }

    public void setPingSlotPeriod(Integer pingSlotPeriod) {
        this.pingSlotPeriod = pingSlotPeriod;
    }

    public Integer getPingSlotDR() {
        return pingSlotDR;
    }

    public void setPingSlotDR(Integer pingSlotDR) {
        this.pingSlotDR = pingSlotDR;
    }

    public Integer getPingSlotFreq() {
        return pingSlotFreq;
    }

    public void setPingSlotFreq(Integer pingSlotFreq) {
        this.pingSlotFreq = pingSlotFreq;
    }

    public Integer getClassCTimeout() {
        return classCTimeout;
    }

    public void setClassCTimeout(Integer classCTimeout) {
        this.classCTimeout = classCTimeout;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Boolean getSupportsJoin() {
        return supportsJoin;
    }

    public void setSupportsJoin(Boolean supportsJoin) {
        this.supportsJoin = supportsJoin;
    }

    public Boolean getSupportsClassB() {
        return supportsClassB;
    }

    public void setSupportsClassB(Boolean supportsClassB) {
        this.supportsClassB = supportsClassB;
    }

    public void setSupportsClassC(Boolean supportsClassC) {
        this.supportsClassC = supportsClassC;
    }

    public Boolean getSupportsClassA() {
        return supportsClassA;
    }

    public void setSupportsClassA(Boolean supportsClassA) {
        this.supportsClassA = supportsClassA;
    }

    public Boolean getSupportsClassC() {
        return supportsClassC;
    }

    public String getDeviceProfileID() {
        return deviceProfileID;
    }

    public void setDeviceProfileID(String deviceProfileID) {
        this.deviceProfileID = deviceProfileID;
    }

    public int[] getFactoryPresetFreqs() {
        return factoryPresetFreqs;
    }

    public void setFactoryPresetFreqs(int[] factoryPresetFreqs) {
        this.factoryPresetFreqs = factoryPresetFreqs;
    }

    @Override
    public String toString() {
        return "DeviceProfile{" +
        ", id=" + id +
        ", name=" + name +
        ", deviceProfileID=" + deviceProfileID +
        ", networkServerID=" + networkServerID +
        ", organizationID=" + organizationID +
        ", macVersion=" + macVersion +
        ", regParamsRevision=" + regParamsRevision +
        ", factoryPresetFreqsStr=" + factoryPresetFreqsStr +
        ", maxEIRP=" + maxEIRP +
        ", supportsJoin=" + supportsJoin +
        ", rxDelay1=" + rxDelay1 +
        ", rxDROffset1=" + rxDROffset1 +
        ", rxDataRate2=" + rxDataRate2 +
        ", rxFreq2=" + rxFreq2 +
        ", supportsClassB=" + supportsClassB +
        ", classBTimeout=" + classBTimeout +
        ", pingSlotPeriod=" + pingSlotPeriod +
        ", pingSlotDR=" + pingSlotDR +
        ", pingSlotFreq=" + pingSlotFreq +
        ", supportsClassC=" + supportsClassC +
        ", classCTimeout=" + classCTimeout +
        ", supportsClassA=" + supportsClassA +
        ", remark=" + remark +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", factoryPresetFreqs=" + factoryPresetFreqs +
        "}";
    }
}
