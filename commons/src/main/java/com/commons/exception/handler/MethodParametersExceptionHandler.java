package com.commons.exception.handler;

import cn.hutool.http.HttpStatus;
import com.commons.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yyh
 * @date 2023/3/27 17:56
 * @description
 */

@Slf4j
@RestControllerAdvice
public class MethodParametersExceptionHandler {


    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMsg = bindingResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        log.error(errorMsg);
        return Result.fail(400, errorMsg);
    }

    /**
     * CheckParam参数校验异常
     */
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public Result illegalArgumentException(IllegalArgumentException e) {
        log.error("=========>>:{}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return getResult(e);
    }


    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result bindException(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        return Result.fail(400, "参数有误");
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Result.fail(400, e.getMessage());
    }

    private Result getResult(BindException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        List<String> msgList = new ArrayList<>();
        for (ObjectError allError : allErrors) {
            msgList.add(allError.getDefaultMessage());
        }
        return Result.fail(HttpStatus.HTTP_BAD_REQUEST, msgList.toString());
    }

}

