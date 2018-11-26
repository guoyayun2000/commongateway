package com.gateway.model;
/**
 * 坐席消息实体类(发送给坐席和接收坐席消息)
 * @author guosen
 *
 * @param <T>
 */
public class SeatMessage<T> {
	/**
	 * 指令码
	 */
	private int code;
	/**
	 * 客户渠道
	 */
	private String channel;
	/**
	 * 客户会话ID
	 */
	private String sessionId;
	/**
	 * 客户名称
	 */
	private String userId;
	/**
	 * 消息实体
	 */
	private T content;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

}
