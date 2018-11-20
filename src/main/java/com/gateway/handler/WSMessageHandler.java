package com.gateway.handler;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.common.UserMemoryCache;
import com.gateway.model.IMMessage;
/**
 * 处理websocket消息发送
 * @author guosen
 *
 */
public class WSMessageHandler implements Runnable {
	private String userKey;
	private IMMessage message;
	
	public WSMessageHandler(String userKey, IMMessage message) {
		this.userKey = userKey;
		this.message = message;
	}

	@Override
	public void run() {
		try {
			ObjectMapper om = new ObjectMapper();
			om.setSerializationInclusion(Include.NON_NULL);
			WebSocketSession session = UserMemoryCache.getInstance().getWebSocketSession(userKey);
			TextMessage tm = new TextMessage(om.writeValueAsString(message));
			System.out.println(om.writeValueAsString(message));
			session.sendMessage(tm);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
