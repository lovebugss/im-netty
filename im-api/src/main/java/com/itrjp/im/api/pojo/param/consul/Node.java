/**
 * Copyright 2022 bejson.com
 */
package com.itrjp.im.api.pojo.param.consul;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * Auto-generated: 2022-12-25 18:3:47
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Node {

    @JsonProperty("ID")
    private String id;
    @JsonProperty("Node")
    private String node;
    @JsonProperty("Address")
    private String address;
    @JsonProperty("Datacenter")
    private String datacenter;
    @JsonProperty("TaggedAddresses")
    private Map<String, Object> taggedAddresses;
    @JsonProperty("Meta")
    private Meta meta;
    @JsonProperty("CreateIndex")
    private int createIndex;
    @JsonProperty("ModifyIndex")
    private int modifyIndex;
}