package com.adms.core.biz.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author wangyz
 * @date 2021年03月25日 14:45
 */
@Data
@ToString
public class RegistryParam implements Serializable {
    private static final long serialVersionUID = -2588318854230483318L;

    private String registryGroup;
    private String registryKey;
    private String registryValue;
    private double cpuUsage;
    private double memoryUsage;
    private double loadAverage;

    public RegistryParam() {
    }

    public RegistryParam(String registryGroup, String registryKey, String registryValue) {
        this.registryGroup = registryGroup;
        this.registryKey = registryKey;
        this.registryValue = registryValue;
    }

    public RegistryParam(String registryGroup, String registryKey, String registryValue, double cpuUsage, double memoryUsage, double loadAverage) {
        this.registryGroup = registryGroup;
        this.registryKey = registryKey;
        this.registryValue = registryValue;
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.loadAverage = loadAverage;
    }
}
