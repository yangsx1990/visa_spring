package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user")
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 逻辑删除状态
     */
    private Boolean deleted;

    /**
     * 用户所属角色（1-客服；2-设计；3-商品中心；4-运营；5-管理员）
     */
    private Integer role;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取账号
     *
     * @return username - 账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置账号
     *
     * @param username 账号
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取创建时间
     *
     * @return created - 创建时间
     */
    public Date getCreated() {
        return created;
    }

    /**
     * 设置创建时间
     *
     * @param created 创建时间
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * 获取逻辑删除状态
     *
     * @return deleted - 逻辑删除状态
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * 设置逻辑删除状态
     *
     * @param deleted 逻辑删除状态
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取用户所属角色（1-客服；2-设计；3-商品中心；4-运营；5-管理员）
     *
     * @return role - 用户所属角色（1-客服；2-设计；3-商品中心；4-运营；5-管理员）
     */
    public Integer getRole() {
        return role;
    }

    /**
     * 设置用户所属角色（1-客服；2-设计；3-商品中心；4-运营；5-管理员）
     *
     * @param role 用户所属角色（1-客服；2-设计；3-商品中心；4-运营；5-管理员）
     */
    public void setRole(Integer role) {
        this.role = role;
    }
}