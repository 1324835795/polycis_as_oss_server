package com.polycis.main.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 组织管理表
 * </p>
 *
 * @author ${author}
 * @since 2019-04-19
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
     * 组织名称
     */
    private String name;
    /**
     * 备注
     */
    private String remark;
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
     * 创建时间
     */
    private Date createTime;
    /**
     * 删除标识(0删除,1未删除)
     */
    private Integer isDelete;


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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public String toString() {
        return "Org{" +
        ", id=" + id +
        ", name=" + name +
        ", remark=" + remark +
        ", address=" + address +
        ", linkmanName=" + linkmanName +
        ", linkmanPhone=" + linkmanPhone +
        ", createTime=" + createTime +
        ", isDelete=" + isDelete +
        "}";
    }
}
