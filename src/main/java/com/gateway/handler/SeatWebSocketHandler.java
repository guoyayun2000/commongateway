package com.gateway.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.common.IMConstants;
import com.gateway.common.SendMessageUtil;
import com.gateway.model.IMMessage;
import com.gateway.model.IMResult;
import com.gateway.model.SeatMessage;
import com.gateway.service.SeatMessageService;
/**
 * 坐席websocket连接处理类
 * @author guosen
 *
 */
public class SeatWebSocketHandler extends TextWebSocketHandler {
	private SeatMessageService messageService;
	private ObjectMapper om = new ObjectMapper().setSerializationInclusion(Include.NON_NULL);
	
	public SeatWebSocketHandler(SeatMessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		SeatMessage<IMMessage> msg = om.readValue(payload, new TypeReference<SeatMessage<IMMessage>>() {});
		IMResult result = messageService.process(msg);
		
		SendMessageUtil smu = SendMessageUtil.getInstance();
		smu.sendMessage(msg.getContent().getFromUserName(), result, IMConstants.DIRECTION_USER_SEAT);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		super.handleTransportError(session, exception);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
	}

}
