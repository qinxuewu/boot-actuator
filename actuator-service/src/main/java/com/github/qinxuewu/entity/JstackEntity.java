package com.github.qinxuewu.entity;



public class JstackEntity {
	private String id;
    private int total;
    private int RUNNABLE;
    private int TIMED_WAITING;
    private int WAITING;

    public JstackEntity(String id, int total, int RUNNABLE, int TIMED_WAITING, int WAITING) {
        this.id = id;
        this.total = total;
        this.RUNNABLE = RUNNABLE;
        this.TIMED_WAITING = TIMED_WAITING;
        this.WAITING = WAITING;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRUNNABLE() {
		return RUNNABLE;
	}

	public void setRUNNABLE(int rUNNABLE) {
		RUNNABLE = rUNNABLE;
	}

	public int getTIMED_WAITING() {
		return TIMED_WAITING;
	}

	public void setTIMED_WAITING(int tIMED_WAITING) {
		TIMED_WAITING = tIMED_WAITING;
	}

	public int getWAITING() {
		return WAITING;
	}

	public void setWAITING(int wAITING) {
		WAITING = wAITING;
	}
}
