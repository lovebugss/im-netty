package com.itrjp.im.api.pojo.param.consul;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://www.consul.io/docs/dynamic-app-config/watches">Consul watches</a>
 * 请求实体
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/26 15:12
 */
@Data
public class ConsulChecksWatchEntity {

    @JsonProperty("node")
    private String node;
    @JsonProperty("checkID")
    private String checkID;
    @JsonProperty("name")
    private String name;
    @JsonProperty("status")
    private String status;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("output")
    private String output;
    @JsonProperty("serviceID")
    private String serviceID;
    @JsonProperty("serviceName")
    private String serviceName;
    @JsonProperty("serviceTags")
    private List<String> serviceTags;
    @JsonProperty("type")
    private String type;
    @JsonProperty("createIndex")
    private Integer createIndex;
    @JsonProperty("modifyIndex")
    private Integer modifyIndex;
    @JsonProperty("definition")
    private Definition definition;

    @Data
    static class Definition {
        @JsonProperty("interval")
        private String interval;
        @JsonProperty("timeout")
        private String timeout;
        @JsonProperty("deregisterCriticalServiceAfter")
        private String deregisterCriticalServiceAfter;
        @JsonProperty("http")
        private String http;
        @JsonProperty("header")
        private Map<String, String> header;
        @JsonProperty("method")
        private String method;
        @JsonProperty("body")
        private String body;
        @JsonProperty("tLSServerName")
        private String tLSServerName;
        @JsonProperty("tLSSkipVerify")
        private boolean tLSSkipVerify;
        @JsonProperty("tcp")
        private String tcp;
    }
}
