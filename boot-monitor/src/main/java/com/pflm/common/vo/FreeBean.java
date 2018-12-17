package com.pflm.common.vo;
/**
 * linux服务器 实际占用内存
 * @author qxw
 *
 */
public class FreeBean {
	
	/**
	 * 总内存
	 */
	public String total;
	/**
	 * 已使用内存
	 */
	public String used;
	/**
	 * 空闲的内存数:
	 */
	public String free;
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getFree() {
		return free;
	}
	public void setFree(String free) {
		this.free = free;
	}
	@Override
	public String toString() {
		return "FreeBean [total=" + total + ", used=" + used + ", free=" + free
				+ "]";
	}

	
}
