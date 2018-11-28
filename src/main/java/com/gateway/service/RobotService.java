package com.gateway.service;

import com.gateway.common.IMConstants;
import com.gateway.common.SendMessageUtil;
import com.gateway.common.SessionUtil;
import com.gateway.model.IMMessage;
/**
 * 机器人流程
 * @author guosen
 *
 */
public class RobotService implements ProcessInterface{

	@Override
	public boolean service(String userKey, IMMessage message) {
		boolean isNext = false;
		String result = "";
		
		if ("99".equals(message.getContent())) {
			isNext = false;
			SessionUtil.hangUpByUser(userKey, false);
		} else if ("9".equals(message.getContent())) {
			isNext = true;
			result = "转人工";
		} else {
			result = "已经收到您的信息[" + message.getContent() + "]";
		}
		
		IMMessage im = SendMessageUtil.getInstance().createMessage(message.getToUserName(), message.getFromUserName(), result, IMConstants.MSG_TYPE_TEXT);
		SendMessageUtil.getInstance().sendMessage(userKey, im, IMConstants.DIRECTION_SEAT_USER);
		return isNext;
	}
	
}
