package com.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.gateway.common.WSConfigPropertiesUtil;
import com.gateway.handler.SeatWebSocketHandler;
import com.gateway.handler.UserWebSocketHandler;
import com.gateway.service.SeatMessageService;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	@Autowired
	private WSConfigPropertiesUtil wcpu;
	@Autowired
	private UserWebSocketHandler userHander;
	@Autowired
	private SeatWebSocketHandler seatHandler;
	@Autowired
	private SeatMessageService messageService;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// 原生websocket
		registry.addHandler(userHander, "/imgateway").setAllowedOrigins("*");
		// sockjs
		registry.addHandler(userHander, "/imgateway").setAllowedOrigins("*")
				.addInterceptors(new HttpSessionHandshakeInterceptor()).withSockJS();
		
		// 原生websocket
		registry.addHandler(seatHandler, "/seatgateway").setAllowedOrigins("*");
		// sockjs
		registry.addHandler(seatHandler, "/seatgateway").setAllowedOrigins("*")
				.addInterceptors(new HttpSessionHandshakeInterceptor()).withSockJS();		
	}
	
	@Bean
	@Scope("prototype")
	public UserWebSocketHandler dealUserHandler() {
		return new UserWebSocketHandler(wcpu);
	}
	
	@Bean
	@Scope("prototype")
	public SeatWebSocketHandler dealSeatHandler() {
		return new SeatWebSocketHandler(messageService);
	}
}
