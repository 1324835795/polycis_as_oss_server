package com.polycis.main.entity.lora;

import java.io.Serializable;

/**
 * <p>
 *     设备激活信息类 接口传输类<br>
 *         abp激活，只需要传   devEUI, appSKey, devAddr, nwkSEncKey<br>
 *         otaa激活，只需要传  appSKey, devEUI, nwkSEncKey
 * </p>
 * @auther cheng
 * @date 2019-04-22
 */
public class LoraDeviceActivationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    /** 设备eui  */
    private String devEUI;
    /**   */
    private String devAddr;
    /**   */
    private String appSKey;
    /**   */
    private String fNwkSIntKey;
    /**   */
    private String nwkSEncKey;
    /**   */
    private String sNwkSIntKey;
    /**   */
    private Integer aFCntDown;
    /**   */
    private Integer fCntUp;
    /**   */
    private Integer nFCntDown;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDevEUI() {
        return devEUI;
    }

    public void setDevEUI(String devEUI) {
        this.devEUI = devEUI;
    }

    public String getDevAddr() {
        return devAddr;
    }

    public void setDevAddr(String devAddr) {
        this.devAddr = devAddr;
    }

    public String getAppSKey() {
        return appSKey;
    }

    public void setAppSKey(String appSKey) {
        this.appSKey = appSKey;
    }

    public String getfNwkSIntKey() {
        return fNwkSIntKey;
    }

    public void setfNwkSIntKey(String fNwkSIntKey) {
        this.fNwkSIntKey = fNwkSIntKey;
    }

    public String getNwkSEncKey() {
        return nwkSEncKey;
    }

    public void setNwkSEncKey(String nwkSEncKey) {
        this.nwkSEncKey = nwkSEncKey;
    }

    public String getsNwkSIntKey() {
        return sNwkSIntKey;
    }

    public void setsNwkSIntKey(String sNwkSIntKey) {
        this.sNwkSIntKey = sNwkSIntKey;
    }

    public Integer getaFCntDown() {
        return aFCntDown;
    }

    public void setaFCntDown(Integer aFCntDown) {
        this.aFCntDown = aFCntDown;
    }

    public Integer getfCntUp() {
        return fCntUp;
    }

    public void setfCntUp(Integer fCntUp) {
        this.fCntUp = fCntUp;
    }

    public Integer getnFCntDown() {
        return nFCntDown;
    }

    public void setnFCntDown(Integer nFCntDown) {
        this.nFCntDown = nFCntDown;
    }
}
