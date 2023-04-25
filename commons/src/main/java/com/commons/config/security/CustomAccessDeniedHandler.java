package com.commons.config.security;

import com.commons.util.WebUtil;
import com.commons.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author yyh
 * @date 2022/12/14 13:57
 * @description 授权异常处理
 */
@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("=========>>拒绝验证：{}", accessDeniedException.getMessage());
        Result result = Result.fail(400, accessDeniedException.getMessage());
        WebUtil.returnResult(response, result);
    }
}
