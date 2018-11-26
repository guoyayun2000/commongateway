package com.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 处理坐席消息的controller
 * @author guosen
 *
 */

import com.gateway.model.IMMessage;
import com.gateway.model.IMResult;
import com.gateway.model.SeatMessage;
import com.gateway.service.SeatMessageService;
@RestController
public class SeatController {
	@Autowired
	private SeatMessageService messageService;
	
	/**
	 * 聊天消息
	 * @param message
	 * @return
	 */
	@PostMapping("/seat/chat")
	public IMResult chat(SeatMessage<IMMessage> message) {
		IMResult result = messageService.process(message);
		return result;
	}
	
}
