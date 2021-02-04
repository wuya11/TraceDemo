package com.trace.base.tool.domain.base;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 登录用户信息
 *
 * @author wl
 */
public class User implements Serializable {
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("user_name")
    private String userName;
    /**
     * 所属组织数组
     */
    @JsonProperty("org_id")
    private Integer orgId;
    @JsonProperty("sup_id")
    private Integer supId;
    /**
     * 供应商名称
     */
    @JsonProperty("sup_name")
    private String supName;
    /**
     * 新的账户编号
     */
    private Long acId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getSupId() {
        return supId;
    }

    public void setSupId(Integer supId) {
        this.supId = supId;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public Long getAcId() {
        return acId;
    }

    public void setAcId(Long acId) {
        this.acId = acId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", orgId=" + orgId +
                ", supId=" + supId +
                ", supName='" + supName + '\'' +
                ", acId=" + acId +
                '}';
    }
}
