package com.gateway.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.WebSocketSession;

import com.gateway.model.Seat;

public class SeatMemoryCache {
	private static final SeatMemoryCache memoryCache = new SeatMemoryCache();
	
	// websocket会话缓存
	private final ConcurrentHashMap<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
	// 坐席信息缓存
	private final ConcurrentHashMap<String, Seat> seatMap = new ConcurrentHashMap<>();
	
	public static SeatMemoryCache getInstance() {
		return memoryCache;
	}
	
	/**
	 * 获取WebSocketSession
	 * @param key WebSocketSession对应的key
	 * @return
	 */
	public WebSocketSession getWebSocketSession(String key) {
		if (key == null) {
			return null;
		}
		return sessionMap.get(key);
	}
	
	/**
	 * 存放WebSocketSession和对应的key
	 * @param key WebSocketSession对应的key
	 * @param session WebSocketSession
	 * @return the previous value associated with key, or null if there was no mapping for key
	 */
	public WebSocketSession putWebSocketSession(String key, WebSocketSession session) {
		return sessionMap.put(key, session);
	}
	
	/**
	 * 根据key移除WebSocketSession缓存
	 * @param key WebSocketSession对应的key
	 * @return the previous value associated with key, or null if there was no mapping for key
	 */
	public WebSocketSession removeWebSocketSession(String key) {
		if (key == null) {
			return null;
		}
		return sessionMap.remove(key);
	}
	
	/**
	 * 根据WebSocketSession移除WebSocketSession缓存
	 * @param session WebSocketSession
	 * @return the previous value associated with key, or null if there was no mapping for key
	 */
	public WebSocketSession removeWebSocketSession(WebSocketSession session) {
		boolean flag = sessionMap.containsValue(session);
		if (!flag) {
			return null;
		}
		Iterator<Entry<String, WebSocketSession>> iterator = sessionMap.entrySet().iterator();
		String key = null;
		while (iterator.hasNext()) {
			Entry<String, WebSocketSession> entry = iterator.next();
			if (entry.getValue() == session) {
				key = entry.getKey();
				break;
			}
		}
		if (key == null) {
			return null;
		}
		return sessionMap.remove(key);
	}
	
	/**
	 * 存放key对应的坐席对象
	 * @param key 坐席信息对象对应的唯一标志
	 * @param Seat 坐席信息对象
	 * @return the previous value associated with key, or null if there was no mapping for key
	 */
	public Seat putSeat(String key, Seat Seat) {
		if (key == null || Seat == null) {
			return null;
		}
		return seatMap.put(key, Seat);
	}
	
	/**
	 * 存放坐席信息对象
	 * @param Seat 坐席信息对象
	 * @return the previous value associated with key, or null if there was no mapping for key
	 */
	public Seat putSeat(Seat Seat) {
		String key = Seat.getSeatId();
		return putSeat(key, Seat);
	}
	
	/**
	 * 根据key获取坐席信息对象
	 * @param key 坐席信息对象对应的唯一标志
	 * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
	 */
	public Seat getSeat(String key) {
		if (key == null) {
			return null;
		}
		return seatMap.get(key);
	}
	
	/**
	 * 获取所有坐席
	 * @return
	 */
	public List<Seat> getAllSeats() {
		Iterator<Entry<String, Seat>> iterator = seatMap.entrySet().iterator();
		List<Seat> seatList = new ArrayList<Seat>();
		while (iterator.hasNext()) {
			Entry<String, Seat> entry = iterator.next();
			seatList.add(entry.getValue());
		}
		return seatList;
	}
	
	/**
	 * 根据坐席信息对象唯一标志移除坐席信息对象缓存
	 * @param key 坐席信息对象对应的唯一标志
	 * @return the previous value associated with key, or null if there was no mapping for key
	 */
	public Seat removeSeat(String key) {
		if (key == null) {
			return null;
		}
		return seatMap.remove(key);
	}
}
