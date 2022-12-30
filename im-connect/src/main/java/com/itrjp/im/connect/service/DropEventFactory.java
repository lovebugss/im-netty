package com.itrjp.im.connect.service;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/29 23:20
 */
public class DropEventFactory {

    public static DropEventStrategy createDropEventStrategy(DropEventStrategy.DropType dropType) {
        switch (dropType) {
            case NOT_DROP:
                return new NotDropEventStrategy();
            case DROP_ALL:
                return new DropAllEventStrategy();
            case DEFAULT:
            default:
                return new DefaultDropEventStrategy(1000);
        }
    }

}
