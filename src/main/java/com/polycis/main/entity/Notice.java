package com.polycis.main.entity;

import java.io.Serializable;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/5/16
 * description : 公告
 */
public class Notice implements Serializable {
    private int id;
    private String topic;
    private String msg;
    private String createTime;
    private String workTime;
    private String deadTime;
    private int userId;

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime.length()==21?workTime.substring(0,19):workTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime.length()==21?createTime.substring(0,19):createTime;
    }

    public String getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(String deadTime) {
        this.deadTime = deadTime.length()==21?deadTime.substring(0,19):deadTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
