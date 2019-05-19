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
    private Date creteTime;
    private Date updateTime;

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

    public Date getCreteTime() {
        return creteTime;
    }

    public void setCreteTime(Date creteTime) {
        this.creteTime = creteTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
