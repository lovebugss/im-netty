package com.itrjp.common.result;

import com.itrjp.common.exception.BizException;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/11/17 23:25
 */
public class Result<T> {

    private int code;
    private String message;
    private T data;

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result<Void> error() {
        return error(ErrorCode.ERROR);
    }

    public static Result<Void> error(ErrorCode errorCode) {
        return new Result<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static Result<Void> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    public static Result<Void> error(BizException exception) {
        return new Result<>(exception.getCode(), exception.getMessage(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    public static Result<Void> success() {
        return success(null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
