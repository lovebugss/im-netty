package com.itrjp.common.exception;

public class ChannelNotFoundException extends BizException {
    public ChannelNotFoundException() {
        super(40001, "频道不存在");
    }
}
