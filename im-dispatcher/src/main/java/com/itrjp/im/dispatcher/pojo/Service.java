/**
 * Copyright 2022 bejson.com
 */
package com.itrjp.im.dispatcher.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Auto-generated: 2022-12-25 18:3:47
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Service {

    @JsonProperty("ID")
    private String id;
    @JsonProperty("Service")
    private String service;
    @JsonProperty("Tags")
    private List<String> tags;
    @JsonProperty("Meta")
    private Map<String, Object> meta;
    @JsonProperty("Port")
    private int port;
    @JsonProperty("Address")
    private String address;
    @JsonProperty("TaggedAddresses")
    private Map<String, Object> taggedAddresses;
    @JsonProperty("Weights")
    private Weights weights;
    @JsonProperty("EnableTagOverride")
    private boolean enableTagOverride;
    @JsonProperty("CreateIndex")
    private int createIndex;
    @JsonProperty("ModifyIndex")
    private int modifyIndex;
    @JsonProperty("Proxy")
    private Proxy proxy;
    @JsonProperty("Connect")
    private Connect connect;
}