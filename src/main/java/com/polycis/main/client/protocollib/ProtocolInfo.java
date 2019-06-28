package com.polycis.main.client.protocollib;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/6/19
 * description : 描述
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProtocolInfo {
    private Integer id;
    private String protocolName;
    private String classPath;
    private String languageContent;
    private Integer languageType;
    private String protocolRemark;
    private Integer isEnable;
    private Integer isDelete;
    private String updateTime;
    private String createTime;
    /**当前页码*/
    private Integer pageNo;
    /**每页行数*/
    private Integer pageSize;
    /**总记录数*/
    private Integer totalRecord;
     /**总页数*/
    private Integer totalPage;
    /**查询条件*/
    private String queryParam;

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime.length()>19?updateTime.substring(0,19):updateTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime.length()>19?createTime.substring(0,19):createTime;
    }
}
