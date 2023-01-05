package com.itrjp.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.util.Objects;

public enum MessageFilterType implements IEnum<Integer> {
    AUTO(0), MANUAL(1);
    private final int code;

    MessageFilterType(int code) {
        this.code = code;
    }

    public static MessageFilterType valueOfCode(Integer code) {
        if (code != null) {
            for (MessageFilterType value : values()) {
                if (Objects.equals(value.getValue(), code)) {
                    return value;
                }
            }
        }
        return null;
    }

    @Override
    public Integer getValue() {
        return code;
    }
}
