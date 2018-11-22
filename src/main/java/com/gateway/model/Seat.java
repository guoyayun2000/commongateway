package com.gateway.model;

import java.util.HashMap;
import java.util.Map;

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
	private Map<String, Long> sessions = new HashMap<String, Long>();
	/*
	 * 预服务会话队列(userKey-time)
	 */
	private Map<String, Long> preSessions = new HashMap<String, Long>();

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

	public Map<String, Long> getSessions() {
		return sessions;
	}

	public void setSessions(Map<String, Long> sessions) {
		this.sessions = sessions;
	}

	public Map<String, Long> getPreSessions() {
		return preSessions;
	}

}
