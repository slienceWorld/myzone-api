package com.commons.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yyh
 * @date 2022/12/11 22:43
 * @description 认证异常处理
 */

@Deprecated
@Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String result;
        if (e instanceof AccountExpiredException) {
            result = "账号过期";
        } else if (e instanceof BadCredentialsException) {
            result = "密码错误";
        } else if (e instanceof CredentialsExpiredException) {
            result = "密码过期";
        } else if (e instanceof DisabledException) {
            result = "账号不可用";
        } else if (e instanceof LockedException) {
            result = "账号锁定";
        } else if (e instanceof InternalAuthenticationServiceException) {
            result = "用户不存在";
        } else {
            result = "其他错误";
        }
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(result);

    }
}
