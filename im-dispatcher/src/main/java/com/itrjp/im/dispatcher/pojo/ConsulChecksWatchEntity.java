package com.itrjp.im.dispatcher.pojo;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://www.consul.io/docs/dynamic-app-config/watches">Consul Watches</a>
 * 请求实体
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/26 15:12
 */
public class ConsulChecksWatchEntity {

    private String node;
    private String checkID;
    private String name;
    private String status;
    private String notes;
    private String output;
    private String serviceID;
    private String serviceName;
    private List<String> serviceTags;
    private String type;
    private Integer createIndex;
    private Integer modifyIndex;
    private Definition definition;

    @Override
    public String toString() {
        return "ConsulChecksWatchEntity{" +
                "node='" + node + '\'' +
                ", checkID='" + checkID + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                ", output='" + output + '\'' +
                ", serviceID='" + serviceID + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceTags=" + serviceTags +
                ", type='" + type + '\'' +
                ", createIndex=" + createIndex +
                ", modifyIndex=" + modifyIndex +
                ", definition=" + definition +
                '}';
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getCheckID() {
        return checkID;
    }

    public void setCheckID(String checkID) {
        this.checkID = checkID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<String> getServiceTags() {
        return serviceTags;
    }

    public void setServiceTags(List<String> serviceTags) {
        this.serviceTags = serviceTags;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCreateIndex() {
        return createIndex;
    }

    public void setCreateIndex(Integer createIndex) {
        this.createIndex = createIndex;
    }

    public Integer getModifyIndex() {
        return modifyIndex;
    }

    public void setModifyIndex(Integer modifyIndex) {
        this.modifyIndex = modifyIndex;
    }

    public Definition getDefinition() {
        return definition;
    }

    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    static class Definition {
        private String interval;
        private String timeout;
        private String deregisterCriticalServiceAfter;
        private String http;
        private Map<String, String> header;
        private String method;
        private String body;
        private String tLSServerName;
        private Boolean tLSSkipVerify;
        private String tcp;

        @Override
        public String toString() {
            return "Definition{" +
                    "interval='" + interval + '\'' +
                    ", timeout='" + timeout + '\'' +
                    ", deregisterCriticalServiceAfter='" + deregisterCriticalServiceAfter + '\'' +
                    ", http='" + http + '\'' +
                    ", header=" + header +
                    ", method='" + method + '\'' +
                    ", body='" + body + '\'' +
                    ", tLSServerName='" + tLSServerName + '\'' +
                    ", tLSSkipVerify=" + tLSSkipVerify +
                    ", tcp='" + tcp + '\'' +
                    '}';
        }

        public String getInterval() {
            return interval;
        }

        public void setInterval(String interval) {
            this.interval = interval;
        }

        public String getTimeout() {
            return timeout;
        }

        public void setTimeout(String timeout) {
            this.timeout = timeout;
        }

        public String getDeregisterCriticalServiceAfter() {
            return deregisterCriticalServiceAfter;
        }

        public void setDeregisterCriticalServiceAfter(String deregisterCriticalServiceAfter) {
            this.deregisterCriticalServiceAfter = deregisterCriticalServiceAfter;
        }

        public String getHttp() {
            return http;
        }

        public void setHttp(String http) {
            this.http = http;
        }

        public Map<String, String> getHeader() {
            return header;
        }

        public void setHeader(Map<String, String> header) {
            this.header = header;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String gettLSServerName() {
            return tLSServerName;
        }

        public void settLSServerName(String tLSServerName) {
            this.tLSServerName = tLSServerName;
        }

        public Boolean gettLSSkipVerify() {
            return tLSSkipVerify;
        }

        public void settLSSkipVerify(Boolean tLSSkipVerify) {
            this.tLSSkipVerify = tLSSkipVerify;
        }

        public String getTcp() {
            return tcp;
        }

        public void setTcp(String tcp) {
            this.tcp = tcp;
        }
    }
}
