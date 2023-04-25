package com.commons.exception;

import lombok.Data;

/**
 * @author yyh
 * @date 2023/4/20 19:53
 * @description
 */


@Data
public class EmptyArrayException extends RuntimeException {

    private int code = 300;
    private String message;

    public EmptyArrayException(String msg) {
        super(msg);
        this.message = msg;
    }

    public EmptyArrayException(String msg, int code) {
        super(msg);
        this.message = msg;
        this.code = code;
    }

    public EmptyArrayException(String msg, Throwable e) {
        super(msg, e);
        this.message = msg;
    }

    public EmptyArrayException(String msg, int code, Throwable e) {
        super(msg, e);
        this.message = msg;
        this.code = code;

    }
}