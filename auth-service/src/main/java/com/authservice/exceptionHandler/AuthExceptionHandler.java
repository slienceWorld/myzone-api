package com.authservice.exceptionHandler;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yyh
 * @date 2023/4/3 22:16
 * @description 验证相关异常处理
 */

@RestControllerAdvice
@ComponentScan(basePackages = "com.commons.exception.handler")
public class AuthExceptionHandler {

}
