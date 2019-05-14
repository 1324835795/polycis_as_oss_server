package com.polycis.main.entity.vo;

import com.polycis.main.entity.Device;
import com.polycis.main.entity.Product;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 设备基本信息
 * </p>
 * @author ${author}
 * @since 2019-04-19
 */
public class DeviceDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 设备唯一标识
     */
    private String deviceUuid;
    /**
     * 应用标识
     */
    private String appEui;

    /**
     * 协议类型 nb 2g mqtt
     */
    private Integer  platform;

    /**
     * 关联的lora设备模式 a:1 b:2 c:3
     */
    private Integer loraDeviceClass;
    /**
     * 数据解析方式 0透传 1解析
     */
    private Integer analysisWay;

    /**
     * 激活方式 1:ABP 2:OTAA
     */
    private Integer activeWay;


    public DeviceDTO(){
    }

    public DeviceDTO(Device device, Product product) {
        this.activeWay=product.getActiveWay();
        this.analysisWay=product.getAnalysisWay();
        this.deviceUuid=device.getDeviceUuid();

        if(null !=product.getLoraDeviceClass())
        this.loraDeviceClass=product.getLoraDeviceClass();
        if(null!=product.getLoraDeviceClass())
            this.loraDeviceClass=product.getLoraDeviceClass();
        this.name=device.getName();
        this.platform=product.getPlatform();
    }


    public Integer getActiveWay() {
        return activeWay;
    }

    public void setActiveWay(Integer activeWay) {
        this.activeWay = activeWay;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceUuid() {
        return deviceUuid;
    }

    public void setDeviceUuid(String deviceUuid) {
        this.deviceUuid = deviceUuid;
    }

    public String getAppEui() {
        return appEui;
    }

    public void setAppEui(String appEui) {
        this.appEui = appEui;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Integer getLoraDeviceClass() {
        return loraDeviceClass;
    }

    public void setLoraDeviceClass(Integer loraDeviceClass) {
        this.loraDeviceClass = loraDeviceClass;
    }

    public Integer getAnalysisWay() {
        return analysisWay;
    }

    public void setAnalysisWay(Integer analysisWay) {
        this.analysisWay = analysisWay;
    }

    @Override
    public String toString() {
        return "DeviceDTO{" +
                "name='" + name + '\'' +
                ", deviceUuid='" + deviceUuid + '\'' +
                ", appEui='" + appEui + '\'' +
                ", platfrom=" + platform +
                ", loraDeviceClass=" + loraDeviceClass +
                ", analysisWay=" + analysisWay +
                ", activeWay=" + activeWay +
                '}';
    }
}
