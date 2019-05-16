package com.polycis.main.entity.vo;



import com.polycis.main.entity.GatewayProChannel;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class GatewayProVO implements Serializable {

     private Integer[] channels;
     private List<GatewayProChannel> extraChannels;
     private String name;
     private Integer organizationId;
     private String networkServerID;
     private String netId;

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public Integer[] getChannels() {
        return channels;
    }

    public void setChannels(Integer[] channels) {
        this.channels = channels;
    }

    public List<GatewayProChannel> getExtraChannels() {
        return extraChannels;
    }

    public void setExtraChannels(List<GatewayProChannel> extraChannels) {
        this.extraChannels = extraChannels;
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

    @Override
    public String toString() {
        return "GatewayProVO{" +
                "channels=" + Arrays.toString(channels) +
                ", extraChannels=" + extraChannels +
                ", name='" + name + '\'' +
                ", networkServerID='" + networkServerID + '\'' +
                '}';
    }
}
