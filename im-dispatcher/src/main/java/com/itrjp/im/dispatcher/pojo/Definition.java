/**
 * Copyright 2022 bejson.com
 */
package com.itrjp.im.dispatcher.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Auto-generated: 2022-12-25 18:3:47
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Definition {

    @JsonProperty("Interval")
    private String interval;
    @JsonProperty("Timeout")
    private String timeout;
    @JsonProperty("DeregisterCriticalServiceAfter")
    private String deregisterCriticalServiceAfter;
    @JsonProperty("HTTP")
    private String http;
    @JsonProperty("Header")
    private String header;
    @JsonProperty("Method")
    private String method;
    @JsonProperty("Body")
    private String body;
    @JsonProperty("TLSServerName")
    private String tlsServerName;
    @JsonProperty("TLSSkipVerify")
    private boolean tlsSkipVerify;
    @JsonProperty("TCP")
    private String tcp;
    @JsonProperty("GRPC")
    private String grpc;
    @JsonProperty("GRPCUseTLS")
    private boolean gRpcUseTLS;
}