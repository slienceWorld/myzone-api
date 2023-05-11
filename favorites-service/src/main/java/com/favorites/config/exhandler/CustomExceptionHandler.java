package com.favorites.config.exhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yyh
 * @date 2023/4/18 17:27
 * @description
 */
@Slf4j
@RestControllerAdvice
@ComponentScan(basePackages = "com.commons.exception.handler")
public class CustomExceptionHandler {

}
