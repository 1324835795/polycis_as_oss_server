package com.polycis.main.entity.db2;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2019-04-22
 */
@TableName("dev_mq_queue")
public class DevMqQueue implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 应用eui
     */
    private String appEui;
    /**
     * 队列名称
     */
    private String queueName;
    /**
     * 优先级(1正常队列 2 优先级队列)
     */
    private Integer priority;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppEui() {
        return appEui;
    }

    public void setAppEui(String appEui) {
        this.appEui = appEui;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "DevMqQueue{" +
        ", id=" + id +
        ", appEui=" + appEui +
        ", queueName=" + queueName +
        ", priority=" + priority +
        "}";
    }
}
