package com.github.qinxuewu.entity;
public class ClassLoadEntity {
	/**
	 * 当前应用进程ID
	 */
	private Integer id;
	/**
	 * 当前应用名称  配置文件 spring.application.name=xxxx
	 */
	private String name;
	/**
	 * 时间 格式 MM/dd HH:mm"
	 */
	private String date;
	/**
	 * 表示载入了类的数量
	 */
    private String Loaded;
	/**
	 * 表示载入了类的合计
	 */
	private String Bytes1;
	/**
	 * 表示卸载类的数量
	 */
	private String Unloaded;
	/**
	 * 表示卸载类合计大小
	 */
    private String Bytes2;
	/**
	 * 表示加载和卸载类总共的耗时
	 */
	private String Time1;
	/**
	 * 表示编译任务执行的次数
	 */
	private String Compiled;
	/**
	 * 表示编译失败的次数
	 */
    private String Failed;
	/**
	 * 表示编译不可用的次数
	 */
	private String Invalid;
	/**
	 * 表示编译的总耗时
	 */
    private String Time2;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLoaded() {
		return Loaded;
	}
	public void setLoaded(String loaded) {
		Loaded = loaded;
	}
	public String getBytes1() {
		return Bytes1;
	}
	public void setBytes1(String bytes1) {
		Bytes1 = bytes1;
	}
	public String getUnloaded() {
		return Unloaded;
	}
	public void setUnloaded(String unloaded) {
		Unloaded = unloaded;
	}
	public String getBytes2() {
		return Bytes2;
	}
	public void setBytes2(String bytes2) {
		Bytes2 = bytes2;
	}
	public String getTime1() {
		return Time1;
	}
	public void setTime1(String time1) {
		Time1 = time1;
	}
	public String getCompiled() {
		return Compiled;
	}
	public void setCompiled(String compiled) {
		Compiled = compiled;
	}
	public String getFailed() {
		return Failed;
	}
	public void setFailed(String failed) {
		Failed = failed;
	}
	public String getInvalid() {
		return Invalid;
	}
	public void setInvalid(String invalid) {
		Invalid = invalid;
	}
	public String getTime2() {
		return Time2;
	}
	public void setTime2(String time2) {
		Time2 = time2;
	}

    
}
