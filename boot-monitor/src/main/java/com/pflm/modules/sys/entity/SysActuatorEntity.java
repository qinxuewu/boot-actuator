package com.pflm.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 监控应用表
 * 
 * @author qinxuewu
 * @email 
 * @date 2018-12-13 11:14:48
 */
@TableName("sys_actuator")
public class SysActuatorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 应用名称
	 */
	private String name;
	/**
	 * 访问域名
	 */
	private String url;
	/**
	 * 
	 */
	private Date date;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：应用名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：应用名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：访问域名
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：访问域名
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * 获取：
	 */
	public Date getDate() {
		return date;
	}
}
