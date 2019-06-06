package com.polycis.main.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:
 * 系统日志
 *
 * @auhtor weitao
 * @create 2019-05-15:35
 */
@TableName("sys_log")
public class SysLogoPO implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private  int id;
    private  String username;
    private  String describe;
    private  int operation;
    private  String method;
    private  String params;
    private  String ip;
    private Date createTime;
    private Date updateTime;

    private transient String operationName;

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operation) {
        this.operationName = operationName;
    }

    /**
     * 用户类型 1运营 2客户
     */
    private Integer userType;

    /**
     * 用户的组织id
     */
    private Integer orgId;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public void setOperation(int operation) {
        this.operation = operation;
        if(10601==operation){
            this.operationName="增加";
        }else if(10602==operation){
            this.operationName="删除";
        }else if(10603==operation){
            this.operationName="修改";
        }else if(10604==operation){
            this.operationName="查询";
        }else {
            this.operationName="其他";
        }
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        if("ADD".equals(operation)){
            this.operation = 10601;
        }else if("UPDATE".equals(operation)){
            this.operation = 10603;
        }else if("DELETE".equals(operation)){
            this.operation = 10602;
        }else if("OTHER".equals(operation)){
            this.operation = -1;
        }else if("QUERY".equals(operation)){
            this.operation = 10604;
        }
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }



    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SysLogoPO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", describe='" + describe + '\'' +
                ", operation=" + operation +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                ", ip='" + ip + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", userType=" + userType +
                ", orgId=" + orgId +
                '}';
    }
}
