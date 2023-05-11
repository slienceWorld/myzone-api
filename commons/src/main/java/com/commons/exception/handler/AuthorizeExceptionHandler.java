package com.commons.exception.handler;

import com.commons.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yyh
 * @date 2023/4/3 22:16
 * @description 验证相关异常处理
 */
@Slf4j
@RestControllerAdvice
public class AuthorizeExceptionHandler {

    @ResponseBody
    @ExceptionHandler(AccountExpiredException.class)
    public Result accountExpiredException(AccountExpiredException e) {
        return Result.fail(400, "账号已过期");
    }

    @ResponseBody
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public Result internalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        return Result.fail(400, "查无此人");
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public Result badCredentialsException(BadCredentialsException e) {
        log.error("==========>>BadCredentialsException:{}", e.getMessage());
        return Result.fail(400, "密码错误");
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    public Result usernameNotFoundException(UsernameNotFoundException e) {
        return Result.fail(400, "找不到指定用户");
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Result.fail(400, "请求方法不支持");
    }


//
//    @ResponseBody
//    @ExceptionHandler(AccessDeniedException.class)
//    public Result accessDeniedException(AccessDeniedException e) {
//        return Result.fail(400, "没有足够权限访问");
//    }


}
