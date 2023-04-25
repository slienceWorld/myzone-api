package com.favorites.exceptionHandler;

import com.commons.exception.DuplicateDataException;
import com.commons.exception.InsertException;
import com.commons.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLSyntaxErrorException;
import java.util.stream.Collectors;

/**
 * @author yyh
 * @date 2023/4/18 17:27
 * @description
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result bindException(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        return Result.fail(400, "参数有误");
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result bindException(HttpRequestMethodNotSupportedException e) {
        return Result.fail(400, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public Result bindException(SQLSyntaxErrorException e) {
        log.error(e.getMessage());
        return Result.fail(400, e.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Result bindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMsg = bindingResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        log.error(errorMsg);
        return Result.fail(400, errorMsg);
    }

    @ResponseBody
    @ExceptionHandler(InsertException.class)
    public Result insertException(InsertException e) {
        String message = e.getMessage();
        int code = e.getCode();
        log.error(message);
        return Result.fail(code, message);
    }

    @ResponseBody
    @ExceptionHandler(BuilderException.class)
    public Result builderException(BuilderException e) {
        String message = e.getMessage();
        log.error(message);
        return Result.fail(message);
    }

    @ResponseBody
    @ExceptionHandler(DuplicateDataException.class)
    public Result duplicateDataException(DuplicateDataException e) {
        String message = e.getMessage();
        int code = e.getCode();
        log.error(message);
        return Result.fail(code, message);
    }

}
