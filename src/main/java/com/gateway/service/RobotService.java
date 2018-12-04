package com.gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.gateway.common.IMConstants;
import com.gateway.common.SendMessageUtil;
import com.gateway.common.SessionUtil;
import com.gateway.model.IMMessage;
/**
 * 机器人流程
 * @author guosen
 *
 */
@Service
@Scope("prototype")
public class RobotService implements ProcessInterface{
	@Autowired
	private TulingRobotService tulingRobotService;
	
	@Override
	public boolean service(String userKey, IMMessage message) {
		boolean isNext = false;
		String result = "";
		String content = message.getContent();
		if ("99".equals(content)) {
			isNext = false;
			SessionUtil.hangUpByUser(userKey, false);
		} else if ("9".equals(content)) {
			isNext = true;
			result = "转人工";
		} else {
			result = tulingRobotService.req(content, 0, message.getFromUserName());
		}
		
		IMMessage im = SendMessageUtil.getInstance().createMessage(message.getToUserName(), message.getFromUserName(), result, IMConstants.MSG_TYPE_TEXT);
		SendMessageUtil.getInstance().sendMessage(userKey, im, IMConstants.DIRECTION_SEAT_USER);
		return isNext;
	}
	
}
