package com.gateway.handler;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gateway.common.IMConstants;
import com.gateway.common.SeatMemoryCache;
import com.gateway.common.UserMemoryCache;
/**
 * 处理websocket消息发送
 * @author guosen
 *
 */
public class WSMessageHandler implements Runnable {
	private String userKey;
	private String message;
	private int direction;
	
	public WSMessageHandler(String userKey, String message, int direction) {
		this.userKey = userKey;
		this.message = message;
		this.direction = direction;
	}

	@Override
	public void run() {
		try {
			WebSocketSession session = null;
			if (IMConstants.DIRECTION_USER_SEAT == direction) {
				session = SeatMemoryCache.getInstance().getWebSocketSession(userKey);
			}
			if (IMConstants.DIRECTION_SEAT_USER == direction) {
				session = UserMemoryCache.getInstance().getWebSocketSession(userKey);
			}
			TextMessage tm = new TextMessage(message);
			System.out.println(message);
			session.sendMessage(tm);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
