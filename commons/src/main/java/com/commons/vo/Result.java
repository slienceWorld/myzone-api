package com.commons.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"code", "msg", "data"})
public class Result {

    private int code;
    private String msg;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object data;

    public static Result success(Object data) {
        return new Result(200, "success", data);
    }

    public static Result success(String msg) {
        return new Result(200, msg, null);
    }
    public static Result success(int code, String msg,Object data) {
        return new Result(code, msg, data);
    }

    public static Result success(int code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result fail(String msg) {
        return new Result(400, msg, null);
    }

    public static Result fail(int code, String msg) {
        return new Result(code, msg, null);
    }

    private Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
