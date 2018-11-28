package com.gateway.service;

import java.util.List;

import com.gateway.common.IMConstants;
import com.gateway.common.IMQueue;
import com.gateway.common.SeatMemoryCache;
import com.gateway.common.SendMessageUtil;
import com.gateway.common.SessionUtil;
import com.gateway.common.UserMemoryCache;
import com.gateway.model.IMMessage;
import com.gateway.model.User;
/**
 * 人工流程
 * @author guosen
 *
 */
public class OnlineService implements ProcessInterface{

	@Override
	public boolean service(String userKey, IMMessage message) {
		System.out.println(userKey);
		boolean isNext = false;
		
		String toUserName = message.getFromUserName();
		String fromUserName = message.getToUserName();
		
		// 获取用户对象
		User user = UserMemoryCache.getInstance().getUser(userKey);
		int status = user.getStatus();
		
		String result = "";
		if ("99".equals(message.getContent())) {
			isNext = true;
			result = "您已经主动退出人工";
			SessionUtil.hangUpByUser(userKey, false);
			isNext = true;
			return isNext;
		}
		
		switch (status) {
		case IMConstants.USER_STATUS_WAIT_ALLOCATION:
			// TODO: 发送提示
			SendMessageUtil.getInstance().createAndSendToUser(fromUserName, toUserName, "排队中", IMConstants.MSG_TYPE_TEXT, userKey);
			break;
		case IMConstants.USER_STATUS_WAIT_ACCESS:
			// TODO: 发送提示
			SendMessageUtil.getInstance().createAndSendToUser(fromUserName, toUserName, "等待接入", IMConstants.MSG_TYPE_TEXT, userKey);
			break;
		case IMConstants.USER_STATUS_ONLINE:
			// TODO: 发送消息到坐席
			result = "已经收到您的信息[" + message.getContent() + "]";
			fromUserName = message.getFromUserName();
			List<String> seats = SeatMemoryCache.getInstance().getSeatIdBySessionId(user.getSessionId());
			toUserName = seats.get(0);
			result = message.getContent();
			userKey = seats.get(0);
			SendMessageUtil.getInstance().createAndSendToSeat(fromUserName, toUserName, result, message.getMsgType(), user.getChannel(), user.getSessionId(), IMConstants.CODE_CHAT, userKey);
			
			break;
		default:
			// TODO: 加入排队队列
			UserMemoryCache.getInstance().getUser(userKey).setStatus(IMConstants.USER_STATUS_WAIT_ALLOCATION);
			IMQueue.putToWaitQueue(userKey);
			break;
		}
		
		return isNext;
	}

}
