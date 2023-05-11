package com.commons.vo;

import lombok.Data;

/**
 * @author yyh
 * @date 2023/5/10 10:56
 * @description
 */
@Data
public class UserVo {

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
     * 简介
     */
    private String introduction;

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
    private Integer sex;

    /**
     * 邮箱
     */
    private String email;


    private String jwtToken;
}
