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
	public static void hangUpByUser(String userKey, String content, boolean ifNotify) {
		User user = UserMemoryCache.getInstance().getUser(userKey);
		String toUserName = "system";
		if (ifNotify) {
			List<String> seats = SeatMemoryCache.getInstance().getSeatIdBySessionId(user.getSessionId());
			if (seats != null) {
				toUserName = seats.get(0);
				SendMessageUtil.getInstance().createAndSendToSeat(user.getUserId(), toUserName, "超时挂机", IMConstants.MSG_TYPE_TEXT, user.getChannel(), user.getSessionId(), IMConstants.CODE_HANG_UP_BY_USER, toUserName);
			}
		}
		SendMessageUtil.getInstance().createAndSendToUser(toUserName, user.getUserId(), content, IMConstants.MSG_TYPE_TEXT, userKey);
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
		UserMemoryCache.getInstance().getUser(userKey).setLastActiveTime(System.currentTimeMillis());
		UserMemoryCache.getInstance().getUser(userKey).setStatus(IMConstants.USER_STATUS_INIT);
		IMQueue.removeFromQueue(userKey);
	}
	
	/**
	 * 坐席接入
	 * @param seatId
	 * @param userKey
	 * @param sessionId
	 */
	public static void accept(String seatId, String userKey, String sessionId) {
		long current = System.currentTimeMillis();
		User user = UserMemoryCache.getInstance().getUser(userKey);
		SeatMemoryCache.getInstance().getSeat(seatId).getPreSessions().remove(userKey);
		SeatMemoryCache.getInstance().getSeat(seatId).getSessions().put(sessionId, current);
		SeatMemoryCache.getInstance().putSessionKey(sessionId, seatId);
		
		UserMemoryCache.getInstance().getUser(userKey).setStatus(IMConstants.USER_STATUS_ONLINE);
		UserMemoryCache.getInstance().getUser(userKey).setLastActiveTime(current);
		SendMessageUtil.getInstance().createAndSendToUser(seatId, user.getUserId(), "坐席接入", IMConstants.MSG_TYPE_TEXT, userKey);
	}
	
	/**
	 * 清理用户相关缓存
	 * @param userKey
	 */
	public static void userClear(String userKey) {
		System.out.println("userClear==>" + userKey);
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
