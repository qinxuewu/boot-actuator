package com.pflm.modules.job.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务日志
 * 
 * @author Levan
 * @email 
 * @date 2018-12-14 15:02:40
 */
@TableName("schedule_job_log")
public class ScheduleJobLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 任务日志id
	 */
	@TableId(type = IdType.AUTO)
	private Long logId;
	/**
	 * 任务id
	 */
	private Long jobId;
	/**
	 * spring bean名称
	 */
	private String beanName;
	/**
	 * 方法名
	 */
	private String methodName;
	/**
	 * 参数
	 */
	private String params;
	/**
	 * 任务状态    0：成功    1：失败
	 */
	private Integer status;
	/**
	 * 失败信息
	 */
	private String error;
	/**
	 * 耗时(单位：毫秒)
	 */
	private Integer times;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 设置：任务日志id
	 */
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	/**
	 * 获取：任务日志id
	 */
	public Long getLogId() {
		return logId;
	}
	/**
	 * 设置：任务id
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	/**
	 * 获取：任务id
	 */
	public Long getJobId() {
		return jobId;
	}
	/**
	 * 设置：spring bean名称
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	/**
	 * 获取：spring bean名称
	 */
	public String getBeanName() {
		return beanName;
	}
	/**
	 * 设置：方法名
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * 获取：方法名
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * 设置：参数
	 */
	public void setParams(String params) {
		this.params = params;
	}
	/**
	 * 获取：参数
	 */
	public String getParams() {
		return params;
	}
	/**
	 * 设置：任务状态    0：成功    1：失败
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：任务状态    0：成功    1：失败
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：失败信息
	 */
	public void setError(String error) {
		this.error = error;
	}
	/**
	 * 获取：失败信息
	 */
	public String getError() {
		return error;
	}
	/**
	 * 设置：耗时(单位：毫秒)
	 */
	public void setTimes(Integer times) {
		this.times = times;
	}
	/**
	 * 获取：耗时(单位：毫秒)
	 */
	public Integer getTimes() {
		return times;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
