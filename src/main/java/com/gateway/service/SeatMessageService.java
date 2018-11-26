package com.gateway.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.gateway.common.IMConstants;
import com.gateway.common.SendMessageUtil;
import com.gateway.common.SessionUtil;
import com.gateway.model.IMMessage;
import com.gateway.model.IMResult;
import com.gateway.model.SeatMessage;

@Service
@Scope("prototype")
public class SeatMessageService {
	
	public IMResult process(SeatMessage<IMMessage> message) {
		IMResult result = new IMResult(IMConstants.RESULT_SUCCESS, "成功");
		int code = message.getCode();
		switch (code) {
		case IMConstants.CODE_CHAT:
			result = chat(message);
			break;
		case IMConstants.CODE_HANG_UP_BY_SEAT:
			result = hangup(message);
			break;
		case IMConstants.CODE_ACCEPT:
			result = accept(message);
			break;
		default:
			break;
		}
		return result;
	}
	
	private IMResult chat(SeatMessage<IMMessage> message) {
		IMResult result = new IMResult(IMConstants.RESULT_SUCCESS, "成功");
		try {
			String userKey = message.getUserId() + "," + message.getChannel();
			SendMessageUtil.getInstance().sendMessage(userKey, message.getContent(), IMConstants.DIRECTION_SEAT_USER);
		} catch (Exception e) {
			result = new IMResult(IMConstants.RESULT_ERROR, e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	private IMResult hangup(SeatMessage<IMMessage> message) {
		IMResult result = new IMResult(IMConstants.RESULT_SUCCESS, "成功");
		try {
			String userKey = message.getUserId() + "," + message.getChannel();
			SessionUtil.hangUpBySeat(message.getContent().getFromUserName(), userKey, message.getSessionId());
		} catch (Exception e) {
			result = new IMResult(IMConstants.RESULT_ERROR, e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	private IMResult accept(SeatMessage<IMMessage> message) {
		IMResult result = new IMResult(IMConstants.RESULT_SUCCESS, "成功");
		try {
			String userKey = message.getUserId() + "," + message.getChannel();
			SessionUtil.accept(message.getContent().getFromUserName(), userKey, message.getSessionId());
		} catch (Exception e) {
			result = new IMResult(IMConstants.RESULT_ERROR, e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}
