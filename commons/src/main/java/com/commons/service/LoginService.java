package com.commons.service;

/**
 * @author yyh
 * @date 2023/4/3 18:43
 */
public interface LoginService {

    String login(String username, String password);

    void logout();
}
