package com.polycis.main.entity.lora;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 设备配置文件 接口传输类<br>
 *     其中，只接收factoryPresetFreqsStr， 不接收factoryPresetFreqs
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
public class LoraDeviceProfileDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 设备配置文件名字
     */
    private String name;
    /**
     * 网络服务id
     */
    private String networkServerID;
    /**
     * 组织id
     */
    private String organizationID;
    /**
     * LoRaWAN MAC版本
     */
    private String macVersion;
    /**
     * LoRaWAN区域参数
     */
    private String regParamsRevision;
    /**
     * 出厂预设频率列表（Hz），逗号分隔
     */
    private String factoryPresetFreqsStr;
    /**
     * 出厂预设频率列表（Hz），逗号分隔, LoraServer存储用的
     */
    private List<Integer> factoryPresetFreqs;
    /**
     * 设备支持的最大EIRP
     */
    private Integer maxEIRP;
    /**
     * 设备支持OTAA,0:ABP,1:OTAA
     */
    private Boolean supportsJoin;
    /**
     * RX1延迟
     */
    private Integer rxDelay1;
    /**
     * RX1数据速率偏移
     */
    private Integer rxDROffset1;
    /**
     * RX2数据速率
     */
    private Integer rxDataRate2;
    /**
     * RX2频道频率
     */
    private Integer rxFreq2;
    /**
     * 设备支持Class-B
     */
    private Boolean supportsClassB;
    /**
     * B类确认下行链路超时
     */
    private Integer classBTimeout;
    /**
     * B类ping槽周期
     */
    private Integer pingSlotPeriod;
    /**
     * B类ping槽数据速率
     */
    private Integer pingSlotDR;
    /**
     * B类ping槽频率
     */
    private Integer pingSlotFreq;
    /**
     * 设备支持Class-C
     */
    private Boolean supportsClassC;
    /**
     * C类确认下行链路超时
     */
    private Integer classCTimeout;


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<Integer> getFactoryPresetFreqs() {
        return factoryPresetFreqs;
    }

    public void setFactoryPresetFreqs(List<Integer> factoryPresetFreqs) {
        this.factoryPresetFreqs = factoryPresetFreqs;
    }

    public Integer getMaxEIRP() {
        return maxEIRP;
    }

    public void setMaxEIRP(Integer maxEIRP) {
        this.maxEIRP = maxEIRP;
    }

    public Boolean getSupportsJoin() {
        return supportsJoin;
    }

    public void setSupportsJoin(Boolean supportsJoin) {
        this.supportsJoin = supportsJoin;
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

    public Boolean getSupportsClassB() {
        return supportsClassB;
    }

    public void setSupportsClassB(Boolean supportsClassB) {
        this.supportsClassB = supportsClassB;
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

    public Boolean getSupportsClassC() {
        return supportsClassC;
    }

    public void setSupportsClassC(Boolean supportsClassC) {
        this.supportsClassC = supportsClassC;
    }

    public Integer getClassCTimeout() {
        return classCTimeout;
    }

    public void setClassCTimeout(Integer classCTimeout) {
        this.classCTimeout = classCTimeout;
    }
}
