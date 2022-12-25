package com.itrjp.im.connect.websocket;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * HandshakeData
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/18 16:44
 */

public final class HandshakeData {
    private final Map<String, List<String>> parameters;
    private final String uri;

    /**
     *
     */
    public HandshakeData(Map<String, List<String>> parameters, String uri) {
        this.parameters = parameters;
        this.uri = uri;
    }

    public Map<String, List<String>> parameters() {
        return parameters;
    }

    public String uri() {
        return uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandshakeData that = (HandshakeData) o;
        return Objects.equals(parameters, that.parameters) && Objects.equals(uri, that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters, uri);
    }

    @Override
    public String toString() {
        return "HandshakeData[" +
                "parameters=" + parameters + ", " +
                "uri=" + uri + ']';
    }


}
