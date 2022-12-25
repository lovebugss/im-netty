package com.itrjp.im.connect.websocket.listener;


import com.itrjp.im.connect.websocket.HandshakeData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/18 16:37
 */
public interface AuthorizationListener {
    AuthorizationResult authorize(HandshakeData data);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class AuthorizationResult {
        private boolean success;
        private ErrorType errorType;

        public static AuthorizationResult success() {
            return new AuthorizationResult(true, null);
        }

        public static AuthorizationResult fail(ErrorType errorType) {
            return new AuthorizationResult(false, null);
        }
    }

    enum ErrorType {
        TOKEN_INVALID, TOKEN_EXPIRES, ROOM_THROTTLING;

    }
}
