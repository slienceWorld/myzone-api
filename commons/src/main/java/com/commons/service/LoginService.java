package com.commons.service;

import com.commons.vo.UserVo;

/**
 * @author yyh
 * @date 2023/4/3 18:43
 */
public interface LoginService {

    UserVo login(String username, String password);

    void logout();
}
