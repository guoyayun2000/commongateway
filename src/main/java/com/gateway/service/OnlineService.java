package com.gateway.service;

import java.util.List;

import com.gateway.common.IMConstants;
import com.gateway.common.IMQueue;
import com.gateway.common.SendMessageUtil;
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
		switch (status) {
		case IMConstants.USER_STATUS_WAIT_ALLOCATION:
			// TODO: 发送提示
			sendMessage(fromUserName, toUserName, "排队中", userKey);
			break;
		case IMConstants.USER_STATUS_WAIT_ACCESS:
			// TODO: 发送提示
			sendMessage(fromUserName, toUserName, "等待接入", userKey);
			break;
		case IMConstants.USER_STATUS_ONLINE:
			// TODO: 发送消息到坐席
			String result = "";
			if ("99".equals(message.getContent())) {
				isNext = true;
				result = "您已经主动退出人工";
			} else {
				result = "已经收到您的信息[" + message.getContent() + "]";
				List<User> list = UserMemoryCache.getInstance().getUsers();
				for (User u : list) {
					String tempKey = u.getUserId() + "," + u.getChannel();
					if (!tempKey.equals(userKey)) {
						toUserName = u.getUserId();
						fromUserName = message.getFromUserName();
						userKey = tempKey;
						result = message.getContent();
						break;
					}
				}
			}
			sendMessage(fromUserName, toUserName, result, userKey);
			break;
		default:
			// TODO: 加入排队队列
			UserMemoryCache.getInstance().getUser(userKey).setStatus(IMConstants.USER_STATUS_WAIT_ALLOCATION);
			IMQueue.putToWaitQueue(userKey);
			break;
		}
		
		return isNext;
	}

	private void sendMessage(String fromUserName, String toUserName, String content, String userKey) {
		IMMessage im = SendMessageUtil.getInstance().createMessage(fromUserName, toUserName, content, IMConstants.MSG_TYPE_TEXT);
		SendMessageUtil.getInstance().sendMessage(userKey, im, IMConstants.DIRECTION_SEAT_USER);
	}
	
}
