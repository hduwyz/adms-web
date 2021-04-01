package com.adms.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * xxl-job info
 *
 * @author jingwk  2019-11-17 14:25:49
 */
@Data
public class JobInfo {

	private int id;

	private int jobGroup;

	private String jobCron;

	private String jobDesc;

	private Date addTime;

	private Date updateTime;

	private int userId;

	private String alarmEmail;

	private String executorRouteStrategy;

	private String executorHandler;

	private String executorParam;

	private String executorBlockStrategy;

	private int executorTimeout;

	private int executorFailRetryCount;

	private String glueType;

	private String glueSource;

	private String glueRemark;

	private Date glueUpdatetime;

	private String childJobId;

	private int triggerStatus;

	private long triggerLastTime;

	private long triggerNextTime;

	private String jobJson;

	private String replaceParam;

	private String replaceParamType;

	private String jvmParam;

	private Date incStartTime;

	private String partitionInfo;

	private int lastHandleCode;

	private int projectId;

	private String primaryKey;

	private Long incStartId;

	private int incrementType;

	private  String readerTable;

	private int datasourceId;

	@TableField(exist=false)
	private String projectName;

	@TableField(exist=false)
	private String userName;
}
