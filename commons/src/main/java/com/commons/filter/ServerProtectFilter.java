package com.commons.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.commons.constant.CloudConstant;
import com.commons.util.WebUtil;
import com.commons.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yyh
 * @date 2023/4/9 21:53
 * @description
 */


@Slf4j
@Component
public class ServerProtectFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = ((HttpServletRequest) request).getRequestURI();

        String value = httpRequest.getHeader(CloudConstant.GATEWAY_HEADER_KEY);
        String gateway = new String(Base64Utils.encode(CloudConstant.GATEWAY_HEADER_VALUE.getBytes()));

        if (StrUtil.equals(value, gateway) && value != null) {
            log.info("==========>>网关验证成功！");
            filterChain.doFilter(request, response);
        } else {

            log.error("==========>>{} 不能绕过网关访问！", uri);
            Result fail = Result.fail(HttpStatus.HTTP_BAD_GATEWAY, "请通过网关访问");
            WebUtil.returnResult((HttpServletResponse) response, fail);
        }
    }


}

