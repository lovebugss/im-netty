package com.itrjp.common.exception;

/**
 * 用户不存在异常
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/6 16:57
 */
public class UserNotExistException extends BizException {
    public UserNotExistException(int code) {
        super(code, "用户不存在");
    }
}
