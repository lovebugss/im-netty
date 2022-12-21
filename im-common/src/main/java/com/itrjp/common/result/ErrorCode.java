package com.itrjp.common.result;

public enum ErrorCode {
    ERROR(500, "系统内部错误, 请联系管理员"),
    PARAM_ERROR(422, "参数错误"),
    ;
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
