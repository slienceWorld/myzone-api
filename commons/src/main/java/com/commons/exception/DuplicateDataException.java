package com.commons.exception;

import lombok.Data;

/**
 * @author yyh
 * @date 2023/4/22 11:58
 * @description
 */
@Data
public class DuplicateDataException extends RuntimeException {
    private int code = 302;
    private String message;

    public DuplicateDataException(String msg) {
        super(msg);
        this.message = msg;
    }

    public DuplicateDataException(String msg, int code) {
        super(msg);
        this.message = msg;
        this.code = code;
    }

    public DuplicateDataException(String msg, Throwable e) {
        super(msg, e);
        this.message = msg;
    }

    public DuplicateDataException(String msg, int code, Throwable e) {
        super(msg, e);
        this.message = msg;
        this.code = code;

    }
}
