package com.adms.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class JobInfo implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 执行器主键ID
     */
    private Integer jobExecutorId;

    private String jobDesc;

    /**
     * 所属项目id
     */
    private Integer projectId;

    private Date addTime;

    private Date updateTime;

    /**
     * 修改用户
     */
    private Integer userId;

    /**
     * 执行器路由策略
     */
    private String executorRouteStrategy;

    /**
     * 执行器任务handler
     */
    private String executorHandler;

    /**
     * 执行器任务参数
     */
    private String executorParam;

    /**
     * 阻塞处理策略
     */
    private String executorBlockStrategy;

    /**
     * 任务执行超时时间，单位分钟
     */
    private Integer executorTimeout;

    /**
     * 失败重试次数
     */
    private Integer executorFailRetryCount;

    /**
     * 数据源id
     */
    private Long datasourceId;


}
