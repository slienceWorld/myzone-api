package com.commons.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;

/**
 * @TableName user
 */
@TableName(value = "user")
@Data
public class SysUser implements UserDetails {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 账户是否可用
     */
    private Integer enable;

    /**
     * 账户是否过期
     */
    private Integer expired;

    /**
     * 账户是否锁定
     */
    private Integer locked;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Set<GrantedAuthority> roles;


    @Override
    @JsonInclude
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }



    /**
     * 账户是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否可用
     */
    @Override
    public boolean isEnabled() {
        return enable!=1;
    }
}