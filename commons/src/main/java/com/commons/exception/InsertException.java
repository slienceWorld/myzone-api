package com.commons.exception;

import lombok.Data;

/**
 * @author yyh
 * @date 2023/4/21 18:55
 * @description
 */

@Data
public class InsertException extends RuntimeException {
    private  int code=301;
    private String message;

    public InsertException(String msg) {
        super(msg);
        this.message = msg;
    }

    public InsertException(String msg, int code) {
        super(msg);
        this.message = msg;
        this.code = code;
    }

    public InsertException(String msg, Throwable e) {
        super(msg, e);
        this.message = msg;
    }

    public InsertException(String msg, int code, Throwable e) {
        super(msg, e);
        this.message = msg;
        this.code = code;

    }
}
