package com.adms.admin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangyz
 * @date 2021年03月26日 11:11
 */
@Data
public class TriggerJobDto implements Serializable {
    private String executorParam;

    private int jobId;
}
