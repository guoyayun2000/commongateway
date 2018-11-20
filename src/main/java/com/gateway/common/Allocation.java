package com.gateway.common;

import java.util.List;

import com.gateway.model.Seat;

/**
 * 分配
 * @author guosen
 *
 */
public class Allocation {
	
	
	public boolean allocationSeat(String userKey) {
		boolean flag = false;
		List<Seat> list = SeatMemoryCache.getInstance().getAllSeats();
		if (list.size() > 0) {
			
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
