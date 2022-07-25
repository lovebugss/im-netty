package com.itrjp.common.exception;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/23 12:35
 */
public class BizException extends RuntimeException {


    private final int code;

    public BizException(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }
}
