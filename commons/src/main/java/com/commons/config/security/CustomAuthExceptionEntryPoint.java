package com.commons.config.security;

import com.commons.util.WebUtil;
import com.commons.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yyh
 * @date 2023/4/2 22:26
 * @description
 */
@Slf4j
@Component
public class CustomAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("==========>>权限出错:{}", authException.getMessage());
        Result result = Result.fail(400, "无权访问");
        WebUtil.returnResult(response, result);
    }
}
