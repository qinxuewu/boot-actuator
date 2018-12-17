package com.github.qinxuewu.entity;
public class GcEntity {
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
	 *  Survivor0空间的大小。单位KB。
	 */
    private String S0C;
	/**
	 * Survivor1空间的大小。单位KB。
	 */
	private String S1C;
	/**
	 * Survivor0已用空间的大小。单位KB
	 */
    private String S0U;
	/**
	 * Survivor1已用空间的大小。单位KB。
	 */
	private String S1U;
	/**
	 * Eden空间的大小。单位KB。
	 */
    private String EC;
	/**
	 * Eden已用空间的大小。单位KB。
	 */
	private String EU;
	/**
	 * 老年代空间的大小。单位KB。
	 */
    private String OC;
	/**
	 * 老年代已用空间的大小。单位KB。
	 */
	private String OU;
	/**
	 * 元空间的大小（Metaspace）
	 */
    private String MC;
	/**
	 * 元空间已使用大小（KB）
	 */
	private String MU;
	/**
	 * 压缩类空间大小（compressed class space）
	 */
    private String CCSC;
	/**
	 * 压缩类空间已使用大小（KB）
	 */
	private String CCSU;
	/**
	 * 新生代gc次数
	 */
    private String YGC;
	/**
	 * 新生代gc耗时（秒）
	 */
	private String YGCT;
	/**
	 * Full gc次数
	 */
    private String FGC;
	/**
	 * Full gc耗时（秒）
	 */
	private String FGCT;
	/**
	 * gc总耗时（秒）
	 */
    private String GCT;
	public void setId(Integer id) { this.id = id; }
	public Integer getId() { return id; }
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
	public String getS0C() {
		return S0C;
	}
	public void setS0C(String s0c) {
		S0C = s0c;
	}
	public String getS1C() {
		return S1C;
	}
	public void setS1C(String s1c) {
		S1C = s1c;
	}
	public String getS0U() {
		return S0U;
	}
	public void setS0U(String s0u) {
		S0U = s0u;
	}
	public String getS1U() {
		return S1U;
	}
	public void setS1U(String s1u) {
		S1U = s1u;
	}
	public String getEC() {
		return EC;
	}
	public void setEC(String eC) {
		EC = eC;
	}
	public String getEU() {
		return EU;
	}
	public void setEU(String eU) {
		EU = eU;
	}
	public String getOC() {
		return OC;
	}
	public void setOC(String oC) {
		OC = oC;
	}
	public String getOU() {
		return OU;
	}
	public void setOU(String oU) {
		OU = oU;
	}
	public String getMC() {
		return MC;
	}
	public void setMC(String mC) {
		MC = mC;
	}
	public String getMU() {
		return MU;
	}
	public void setMU(String mU) {
		MU = mU;
	}
	public String getCCSC() {
		return CCSC;
	}
	public void setCCSC(String cCSC) {
		CCSC = cCSC;
	}
	public String getCCSU() {
		return CCSU;
	}
	public void setCCSU(String cCSU) {
		CCSU = cCSU;
	}
	public String getYGC() {
		return YGC;
	}
	public void setYGC(String yGC) {
		YGC = yGC;
	}
	public String getYGCT() {
		return YGCT;
	}
	public void setYGCT(String yGCT) {
		YGCT = yGCT;
	}
	public String getFGC() {
		return FGC;
	}
	public void setFGC(String fGC) {
		FGC = fGC;
	}
	public String getFGCT() {
		return FGCT;
	}
	public void setFGCT(String fGCT) {
		FGCT = fGCT;
	}
	public String getGCT() {
		return GCT;
	}
	public void setGCT(String gCT) {
		GCT = gCT;
	}

    
}
