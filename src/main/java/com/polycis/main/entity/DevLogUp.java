package com.polycis.main.entity;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/5/17
 * description : 描述
 */
public class DevLogUp {
    private String modifyTime;
    private String decodeData;
    private String encodeData;
    private int platform;
    private String devName;
    private String devUuid;
    private String status;

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime.length()==21?modifyTime.substring(0,19):modifyTime;
    }

    public String getDecodeData() {
        return decodeData;
    }

    public void setDecodeData(String decodeData) {
        this.decodeData = decodeData;
    }

    public String getEncodeData() {
        return encodeData;
    }

    public void setEncodeData(String encodeData) {
        this.encodeData = encodeData;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevUuid() {
        return devUuid;
    }

    public void setDevUuid(String devUuid) {
        this.devUuid = devUuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
