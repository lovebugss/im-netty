package com.itrjp.im.api.service;

/**
 * UserService
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2023/1/6 16:55
 */
public interface UserService {
    /**
     * 检查用户是否存在
     *
     * @param userId 用户id
     * @throws com.itrjp.common.exception.UserNotExistException
     */
    void checkUserExist(String userId);
}
