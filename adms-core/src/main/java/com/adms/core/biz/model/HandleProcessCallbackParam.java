package com.adms.core.biz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author wangyz
 * @date 2021年03月25日 14:43
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HandleProcessCallbackParam implements Serializable {
    private static final long serialVersionUID = -6135508604060764632L;

    private long logId;
    private String processId;
    private long logDateTime;
}
