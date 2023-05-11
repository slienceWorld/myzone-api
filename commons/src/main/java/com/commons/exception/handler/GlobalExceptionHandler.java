package com.commons.exception.handler;

import com.commons.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yyh
 * @date 2023/3/29 18:52
 * @description
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(RedisSystemException.class)
    public Result httpRequestMethodNotSupportedException(RedisSystemException e) {
        return Result.fail(400, e.getMessage());
    }

}
