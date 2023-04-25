package com.commons.exceptionHandler;

import cn.hutool.http.HttpStatus;
import com.commons.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yyh
 * @date 2023/3/27 17:56
 * @description
 */

@Slf4j
@RestControllerAdvice
public class InterfaceParametersExceptionHandler {


    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(BindException e) {
        return getResult(e);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return getResult(e);
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
