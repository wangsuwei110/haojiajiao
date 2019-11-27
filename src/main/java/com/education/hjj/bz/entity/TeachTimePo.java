package com.education.hjj.bz.entity;

import java.io.Serializable;

public class TeachTimePo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1796219611751813099L;
	
	private String week;
	
	private String time;

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public TeachTimePo(String week, String time) {
		super();
		this.week = week;
		this.time = time;
	}

	public TeachTimePo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public boolean equals(Object object) {
        
        if (this == object) {
            return true;
        }
        if (object instanceof TeachTimePo) {
        	TeachTimePo teachTimePo = (TeachTimePo) object;
            return this.week.equals(teachTimePo.week) 
                    && this.time.equals(teachTimePo.time);
        }
        return super.equals(object);
    }
}
