package com.polycis.main.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author ${author}
 * @since 2018-11-29
 */
@TableName("iot_user")
public class Users  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户数据标识，唯一id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 组织标识
     */
    private Integer org;

    /**
     * 组织标识
     */
    private String role;


    private String loginname;
    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户类型1.用户2，管理员
     */
    private Integer type;

    /**
     * 用户名字
     */
    private String name;


    /**
     * 创建
     */
    private Date createTime;
    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 用户头像(接口写了,但没用到)
     */
    private String picturepath;

    /**
     * 是否启用
     */
    private Integer isStart;



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrg() {
        return org;
    }

    public void setOrg(Integer org) {
        this.org = org;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getPicturepath() {
        return picturepath;
    }

    public void setPicturepath(String picturepath) {
        this.picturepath = picturepath;
    }

    public Integer getIsStart() {
        return isStart;
    }

    public void setIsStart(Integer isStart) {
        this.isStart = isStart;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", org=" + org +
                ", role='" + role + '\'' +
                ", loginname='" + loginname + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", isDelete=" + isDelete +
                ", picturepath='" + picturepath + '\'' +
                ", isStart=" + isStart +
                '}';
    }
}
