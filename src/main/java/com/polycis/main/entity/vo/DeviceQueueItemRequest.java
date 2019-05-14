package com.polycis.main.entity.vo;/**
 * Created with IntelliJ IDEA.
 * User: Liangxiaolong
 * Date: 2018/7/5
 * Time: 19:27
 * To change this template use File | Settings | File Templates.
 */

/**
 * Created with IntelliJ IDEA.
 * User: Liangxiaolong
 * Date: 2018/7/5
 * Time: 19:27
 * To change this template use File | Settings | File Templates.
 */
public class DeviceQueueItemRequest {
    private boolean confirmed;
    private String data;
    private String devEUI;
    private String jsonObject;
    private String reference;
    private int fCnt;
    private int fPort;

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDevEUI() {
        return devEUI;
    }

    public void setDevEUI(String devEUI) {
        this.devEUI = devEUI;
    }

    public String getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getfPort() {
        return fPort;
    }

    public void setfPort(int fPort) {
        this.fPort = fPort;
    }

    public int getfCnt() {
        return fCnt;
    }

    public void setfCnt(int fCnt) {
        this.fCnt = fCnt;
    }

    @Override
    public String toString() {
        return "DeviceQueueItemRequest{" +
                "confirmed=" + confirmed +
                ", data='" + data + '\'' +
                ", devEUI='" + devEUI + '\'' +
                ", jsonObject='" + jsonObject + '\'' +
                ", reference='" + reference + '\'' +
                ", fCnt=" + fCnt +
                ", fPort=" + fPort +
                '}';
    }
}
