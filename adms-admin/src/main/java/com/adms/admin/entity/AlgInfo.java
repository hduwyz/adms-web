package com.adms.admin.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangyz
 * @since 2021-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AlgInfo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 算法主键
     */
      private Long id;

    /**
     * 算法名称
     */
    private String name;

    /**
     * 算法code
     */
    private String executorHandler;

    /**
     * 算法状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private Long createrId;

    private Long updaterId;


}
