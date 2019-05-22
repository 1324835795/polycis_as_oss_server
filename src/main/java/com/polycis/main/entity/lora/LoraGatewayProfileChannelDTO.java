package com.polycis.main.entity.lora;

import com.polycis.main.entity.GatewayProChannel;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 网关配置文件频段表, 这个类不参与传输， 传输用其父类 GatewayProfileDTO<br>
 *     其中，只接收spreadingFactorsStr， 不接收spreadingFactors
 * </p>
 *
 * @author ${author}
 * @since 2019-05-16
 */
public class LoraGatewayProfileChannelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 调制, 有 俩种类型
     * 1：LORA,如果是LORA,则有 modulation、bandwidth、frequency、spreadingFactors、spreadingFactorsStr
     * 2：FSK,如果是FSK,则有   modulation、bandwidth、frequency、bitrate
     */
    private String modulation;
    /**
     * 扩散因子
     */
    private String spreadingFactorsStr;
    private List<Integer> spreadingFactors;

    public LoraGatewayProfileChannelDTO(GatewayProChannel dto){
        this.bandwidth = dto.getBandwidth();
        this.bitrate = dto.getBitrate();
        this.frequency = dto.getFrequency();
        this.modulation = dto.getModulation();
        this.spreadingFactorsStr = dto.getSpreadFactors();
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

    public String getSpreadingFactorsStr() {
        return spreadingFactorsStr;
    }

    public void setSpreadingFactorsStr(String spreadingFactorsStr) {
        this.spreadingFactorsStr = spreadingFactorsStr;
    }

    public List<Integer> getSpreadingFactors() {
        return spreadingFactors;
    }

    public void setSpreadingFactors(List<Integer> spreadingFactors) {
        this.spreadingFactors = spreadingFactors;
    }
}
