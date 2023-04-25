package com.commons.config.security;

import cn.hutool.core.util.ObjectUtil;
import com.commons.dto.UserDto;
import com.commons.util.JwtUtil;
import com.commons.util.RedisUtil;
import com.commons.util.WebUtil;
import com.commons.vo.Result;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yyh
 * @date 2023/4/3 19:24
 * @description
 */
@ComponentScan(basePackageClasses = RedisUtil.class)
@Slf4j
@Component
public class JwtSecurityFilter extends OncePerRequestFilter {


    private static final String HEADER_PREFIX = "Bearer ";

    @Resource
    private RedisUtil redisUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith(HEADER_PREFIX)) {
            log.warn("==========>>请求里面没有token");
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authorizationHeader.substring(7);
        String username = getUsername(response, jwtToken);
        if (username == null) {
            return;
        }
        UserDto userDto = (UserDto) redisUtil.get("login:" + username);

        if (ObjectUtil.isNull(userDto) || !userDto.getJwtToken().equals(jwtToken)) {
            log.warn("==========>>没有登录信息或者信息过期");
            WebUtil.returnResult(response,Result.fail(400,"没有登录信息或者信息过期"));
            return;

        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword(), userDto.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }


    @SneakyThrows
    private static String getUsername(HttpServletResponse response, String jwtToken) {
        String username = null;
        try {
            username = JwtUtil.getUsernameFromToken(jwtToken);
        } catch (SignatureException | MalformedJwtException |
                ExpiredJwtException | UnsupportedJwtException |
                IllegalArgumentException e) {
            Result result = Result.fail(400, e.getMessage());
            WebUtil.returnResult(response, result);
        }
        return username;
    }


}
