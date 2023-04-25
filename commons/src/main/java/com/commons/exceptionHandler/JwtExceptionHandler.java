package com.commons.exceptionHandler;

import com.commons.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yyh
 * @date 2023/4/26 15:20
 * @description
 */

@Slf4j
@RestControllerAdvice
public class JwtExceptionHandler {

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public Result illegalArgumentException(IllegalArgumentException e) {
        log.error("==========>>illegalArgumentException:{}", e.getMessage());
        return Result.fail(400, e.getMessage());
    }
}
