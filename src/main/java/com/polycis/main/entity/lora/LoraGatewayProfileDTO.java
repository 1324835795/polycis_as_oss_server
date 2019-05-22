package com.polycis.main.entity.lora;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 网关配置文件 接口调用传输类<br>
 *     其中，只接收channelsStr， 不接收channels
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
public class LoraGatewayProfileDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    private String id;
    /**
     * 配置文件名
     */
    private String name;
    /**
     * 频道表
     */
    private String channelsStr;
    private List<Integer> channels;

    /**
     * 网络服务id
     */
    private String networkServerID;

    /**
     * 其它通道
     */
    private List<LoraGatewayProfileChannelDTO> extraChannels;

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

    public String getChannelsStr() {
        return channelsStr;
    }

    public void setChannelsStr(String channelsStr) {
        this.channelsStr = channelsStr;
    }

    public List<Integer> getChannels() {
        return channels;
    }

    public void setChannels(List<Integer> channels) {
        this.channels = channels;
    }

    public String getNetworkServerID() {
        return networkServerID;
    }

    public void setNetworkServerID(String networkServerID) {
        this.networkServerID = networkServerID;
    }

    public List<LoraGatewayProfileChannelDTO> getExtraChannels() {
        return extraChannels;
    }

    public void setExtraChannels(List<LoraGatewayProfileChannelDTO> extraChannels) {
        this.extraChannels = extraChannels;
    }
}
