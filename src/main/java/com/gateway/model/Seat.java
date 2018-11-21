package com.gateway.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 坐席信息
 * 
 * @author guosen
 *
 */
public class Seat {
	/*
	 * 坐席ID
	 */
	private String seatId;
	/*
	 * 坐席名称
	 */
	private String seatName;
	/*
	 * 坐席状态
	 */
	private String seatStatus;
	/*
	 * 可以服务的人数
	 */
	private int serviceLength = 10;
	/*
	 * 服务会话信息
	 */
	private List<String> sessions = new ArrayList<String>();
	/*
	 * 预服务会话队列
	 */
	private List<String> preSessions = new ArrayList<String>();

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public String getSeatName() {
		return seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	public String getSeatStatus() {
		return seatStatus;
	}

	public void setSeatStatus(String seatStatus) {
		this.seatStatus = seatStatus;
	}

	public int getServiceLength() {
		return serviceLength;
	}

	public void setServiceLength(int serviceLength) {
		this.serviceLength = serviceLength;
	}

	public List<String> getSessions() {
		return sessions;
	}

	public void setSessions(List<String> sessions) {
		this.sessions = sessions;
	}

	public List<String> getPreSessions() {
		return preSessions;
	}

}
