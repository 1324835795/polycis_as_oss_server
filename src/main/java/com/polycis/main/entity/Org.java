package com.polycis.main.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 组织管理表
 * </p>
 *
 * @author ${author}
 * @since 2019-05-14
 */
@TableName("iot_org")
public class Org implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 客户名称
     */
    private String name;
    /**
     * 登录名称
     */
    private String loginname;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 组织地址
     */
    private String address;
    /**
     * 联系人姓名
     */
    private String linkmanName;
    /**
     * 联系人电话
     */
    private String linkmanPhone;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 删除标识(0删除,1未删除)
     */
    private Integer isDelete;

    private Integer start;

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkmanName() {
        return linkmanName;
    }

    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName;
    }

    public String getLinkmanPhone() {
        return linkmanPhone;
    }

    public void setLinkmanPhone(String linkmanPhone) {
        this.linkmanPhone = linkmanPhone;
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
        return "Org{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", loginname='" + loginname + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", linkmanName='" + linkmanName + '\'' +
                ", linkmanPhone='" + linkmanPhone + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", isDelete=" + isDelete +
                ", start=" + start +
                '}';
    }
}
