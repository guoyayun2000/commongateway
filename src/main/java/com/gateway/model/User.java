package com.gateway.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gateway.common.IMConstants;
import com.gateway.service.ProcessInterface;

/**
 * 用户信息
 * 
 * @author guosen
 *
 */
public class User {
	/*
	 * 用户ID
	 */
	private String userId;
	/*
	 * 用户适用的处理流程
	 */
	private List<String> processStep;
	/*
	 * 目前的流程位置(当前在进行的流程步骤位置)
	 */
	private int step = 0;
	/*
	 * 用户所属渠道
	 */
	private String channel;
	/*
	 * 会话ID
	 */
	private String sessionId;
	/*
	 * 用户状态
	 */
	private int status = IMConstants.USER_STATUS_INIT;
	/*
	 * 用户自带参数
	 */
	private Map<String, String> paramMap;
	/*
	 * 业务处理实例
	 */
	private Map<String, ProcessInterface> processServices = new HashMap<String, ProcessInterface>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getProcessStep() {
		return processStep;
	}

	public void setProcessStep(List<String> processStep) {
		this.processStep = processStep;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	public void setProcessService(String processName, ProcessInterface processService) {
		processServices.put(processName, processService);
	}
	
	public Map<String, ProcessInterface> getProcessServices() {
		return processServices;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
