package com.gateway.common;

import java.util.List;

import com.gateway.model.Seat;
import com.gateway.model.User;

/**
 * 分配
 * @author guosen
 *
 */
public class Allocation {
	
	/**
	 * 分配坐席
	 * @param userKey 用户Key
	 * @return true分配成功,false未分配成功
	 */
	public boolean allocationSeat(String userKey) {
		boolean flag = false;
		List<Seat> list = SeatMemoryCache.getInstance().getAllSeats();
		User user = UserMemoryCache.getInstance().getUser(userKey);
		String seatId = null;
		int tempLen = 10;
		for(Seat seat : list) {
			if (seat.getSessions().size() < tempLen) {
				seatId = seat.getSeatId();
			}
		}
		if (seatId != null) {
			/*
			 * 1、将客户放入坐席的排队队列
			 * 2、将缓存中客户的最后活动时间更改为当前时间
			 * 3、将客户的状态改为等待接入状态
			 * 4、发送接入提示给坐席
			 */
			SeatMemoryCache.getInstance().getSeat(seatId).getPreSessions().add(userKey);
			UserMemoryCache.getInstance().getUser(userKey).setLastActiveTime(System.currentTimeMillis());
			UserMemoryCache.getInstance().getUser(userKey).setStatus(IMConstants.USER_STATUS_WAIT_ACCESS);
			
			SendMessageUtil.getInstance().createAndSendMessage(user.getUserId(), seatId, "接入", IMConstants.MSG_TYPE_NOTICE_IN, seatId, IMConstants.DIRECTION_USER_SEAT);
			flag = true;
		}
		return flag;
	}
	
	public boolean allocation(String userKey) {
		boolean flag = false;
		List<Seat> list = SeatMemoryCache.getInstance().getAllSeats();
		if (list.size() > 0) {
			
		}
		return flag;
	}
	
	
	
}
