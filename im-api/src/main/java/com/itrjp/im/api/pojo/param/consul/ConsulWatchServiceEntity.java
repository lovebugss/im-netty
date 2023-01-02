/**
 * Copyright 2022 bejson.com
 */
package com.itrjp.im.api.pojo.param.consul;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2022-12-25 18:3:47
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class ConsulWatchServiceEntity {

    @JsonProperty("Node")
    private Node node;
    @JsonProperty("Service")
    private Service service;
    @JsonProperty("Checks")
    private List<Checks> checks;

}