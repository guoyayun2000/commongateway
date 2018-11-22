package com.gateway.common;

import java.io.IOException;
import java.util.List;

import org.springframework.web.socket.WebSocketSession;

import com.gateway.model.User;

/**
 * 会话处理工具类
 * @author guosen
 *
 */
public class SessionUtil {
	
	/**
	 * 客户挂机
	 * @param userKey
	 * @param ifNotify
	 */
	public static void hangUpByUser(String userKey, boolean ifNotify) {
		User user = UserMemoryCache.getInstance().getUser(userKey);
		String toUserName = "system";
		if (ifNotify) {
			List<String> seats = SeatMemoryCache.getInstance().getSeatIdBySessionId(user.getSessionId());
			if (seats != null) {
				toUserName = seats.get(0);
				SendMessageUtil.getInstance().createAndSendToSeat(user.getUserId(), toUserName, "超时挂机", IMConstants.MSG_TYPE_TEXT, user.getChannel(), user.getSessionId(), IMConstants.CODE_HANG_UP_BY_USER, toUserName);
			}
		}
		SendMessageUtil.getInstance().createAndSendToUser(toUserName, user.getUserId(), "超时挂机", IMConstants.MSG_TYPE_TEXT, userKey);
		resetUser(userKey);
	}
	
	/**
	 * 坐席挂机
	 * @param seatId
	 * @param userKey
	 * @param sessionId
	 */
	public static void hangUpBySeat(String seatId, String userKey, String sessionId) {
		User user = UserMemoryCache.getInstance().getUser(userKey);
		SeatMemoryCache.getInstance().getSeat(seatId).getSessions().remove(userKey);
		SeatMemoryCache.getInstance().removeSessionKey(sessionId, seatId);
		resetUser(userKey);
		SendMessageUtil.getInstance().createAndSendToUser(seatId, user.getUserId(), "坐席挂机", IMConstants.MSG_TYPE_TEXT, userKey);
	}
	
	private static void resetUser(String userKey) {
		UserMemoryCache.getInstance().getUser(userKey).setStatus(IMConstants.USER_STATUS_INIT);
	}
	
	/**
	 * 清理用户相关缓存
	 * @param userKey
	 */
	public static void userClear(String userKey) {
		User user = UserMemoryCache.getInstance().getUser(userKey);
		SeatMemoryCache.getInstance().removeSessionKey(user.getSessionId());
		UserMemoryCache.getInstance().removeUser(userKey);
		WebSocketSession session = UserMemoryCache.getInstance().removeWebSocketSession(userKey);
		if (session != null && session.isOpen()) {
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
