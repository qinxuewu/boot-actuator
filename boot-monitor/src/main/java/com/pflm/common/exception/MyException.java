
package com.pflm.common.exception;
/**
 *  自定义异常
 * @author qinxuewu
 * @version 1.00
 * @time  26/11/2018 下午 6:26
 * @email 870439570@qq.com
 */
public class MyException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String msg;
    private int code = 500;
    
    public MyException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public MyException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public MyException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public MyException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
