package com.gateway.schedule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gateway.common.Allocation;
import com.gateway.common.IMConstants;
import com.gateway.common.IMQueue;
import com.gateway.common.SeatMemoryCache;
import com.gateway.common.SessionUtil;
import com.gateway.common.UserMemoryCache;
import com.gateway.model.Seat;
import com.gateway.model.User;

@Component
@EnableScheduling
public class AllocationScheduledTasks {
	private Logger logger = LoggerFactory.getLogger(AllocationScheduledTasks.class);
	
	@Scheduled(fixedDelayString = "${jobs.allocation.delay}")
	public void allocationUser() {
		String userKey = IMQueue.takeFromWaitQueue();
		logger.info("allocationUser==>" + userKey);
		Allocation all = new Allocation();
		boolean flag = all.allocationSeat(userKey);
		if (!flag) {
			IMQueue.putToWaitQueue(userKey);
		}
	}
	
	@Scheduled(fixedDelayString = "${jobs.cache.clear.delay}")
	public void clearUserCache() {
		/*
		 * 1、如果用户处于初始状态且最后活跃时间大于5分钟,清理关闭连接
		 * 2、如果用户处于机器人状态且最后活跃时间大于5分钟,清理发送关闭连接提醒
		 * 3、如果用户处于排队状态且最后活跃时间大于2分钟,清理发送挂机提醒
		 * 4、如果用户处于人工状态且最后活跃时间大于10分钟,清理发送挂机提醒(双向)
		 */
		List<User> users = UserMemoryCache.getInstance().getUsers();
		long current = System.currentTimeMillis();
		for (User user : users) {
			long lastActive = user.getLastActiveTime();
			long interval = current - lastActive;
			int status = user.getStatus();
			String userKey = user.getUserId() + "," + user.getChannel();
			System.out.println("clearUserCache==>" + userKey + "\t" + status + "\t" + lastActive);
			switch (status) {
			case IMConstants.USER_STATUS_INIT:
				if (interval > 60000) {
					SessionUtil.userClear(userKey);
				}
				break;
			case IMConstants.USER_STATUS_ROBOT:
				if (interval > 300000) {
					SessionUtil.userClear(userKey);
				}
				break;
			case IMConstants.USER_STATUS_WAIT_ALLOCATION:
				if (interval > 120000) {
					IMQueue.removeFromQueue(userKey);
					SessionUtil.hangUpByUser(userKey, "分配超时挂机", false);
				}
				break;
			case IMConstants.USER_STATUS_ONLINE:
				if (interval > 600000) {
					IMQueue.removeFromQueue(userKey);
					SessionUtil.hangUpByUser(userKey, "超过最大聊天时间挂机", true);
				}
				break;
			default:
				break;
			}
		}
	}
	
	@Scheduled(fixedDelayString = "${jobs.cache.clear.delay}")
	public void clearSeatCache() {
		/*
		 * 1、如果坐席预接入队列中的排队时间大于1分钟则从预接入队列中删除,重新加入排队队列(更新用户状态和最后活跃时间)
		 */
		List<Seat> seats = SeatMemoryCache.getInstance().getAllSeats();
		long current = System.currentTimeMillis();
		for (Seat seat : seats) {
			Map<String, Long> map = seat.getPreSessions();
			if (!map.isEmpty()) {
				Iterator<Entry<String, Long>> iterator = map.entrySet().iterator();
				List<String> keys = new ArrayList<String>();
				while (iterator.hasNext()) {
					Entry<String, Long> entry = iterator.next();
					if ((current - entry.getValue()) > 60000) {
						keys.add(entry.getKey());
					}
				}
				for (String key : keys) {
					SeatMemoryCache.getInstance().getSeat(seat.getSeatId()).getSessions().remove(key);
					UserMemoryCache.getInstance().getUser(key).setLastActiveTime(current);
					UserMemoryCache.getInstance().getUser(key).setStatus(IMConstants.USER_STATUS_WAIT_ALLOCATION);
					IMQueue.putToWaitQueue(key);
				}
			}
		}
	}
	
	@Scheduled(cron = "${jobs.cron}")
	public void test() {
		logger.info("test==>" + System.currentTimeMillis());
	}
}
