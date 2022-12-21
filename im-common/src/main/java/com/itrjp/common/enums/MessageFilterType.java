package com.itrjp.common.enums;

public enum MessageFilterType {
    AUTO(0), BLACK(1);
    private final int code;

    MessageFilterType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
