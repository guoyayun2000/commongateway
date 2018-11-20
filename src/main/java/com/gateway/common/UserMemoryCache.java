package com.gateway.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.WebSocketSession;

import com.gateway.model.User;

/**
 * 用户信息缓存缓存
 * @author guosen
 *
 */
public class UserMemoryCache {
	private static final UserMemoryCache memoryCache = new UserMemoryCache();
	
	// websocket会话缓存
	private final ConcurrentHashMap<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
	// 用户信息缓存
	private final ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap<>();
	
	public static UserMemoryCache getInstance() {
		return memoryCache;
	}
	
	/**
	 * 获取WebSocketSession
	 * @param key WebSocketSession对应的key
	 * @return
	 */
	public String getUserKey(WebSocketSession session) {
		Iterator<Entry<String, WebSocketSession>> iterator = sessionMap.entrySet().iterator();
		String userKey = "";
		while (iterator.hasNext()) {
			Entry<String, WebSocketSession> entry = iterator.next();
			if (entry.getValue() == session) {
				userKey = entry.getKey();
			}
		}
		return userKey;
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
	 * 存放key对应的用户对象
	 * @param key 用户信息对象对应的唯一标志
	 * @param user 用户信息对象
	 * @return the previous value associated with key, or null if there was no mapping for key
	 */
	public User putUser(String key, User user) {
		if (key == null || user == null) {
			return null;
		}
		return userMap.put(key, user);
	}
	
	/**
	 * 存放用户信息对象
	 * @param user 用户信息对象
	 * @return the previous value associated with key, or null if there was no mapping for key
	 */
	public User putUser(User user) {
		String key = user.getUserId() + "," + user.getChannel();
		return putUser(key, user);
	}
	
	/**
	 * 根据key获取用户信息对象
	 * @param key 用户信息对象对应的唯一标志
	 * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
	 */
	public User getUser(String key) {
		if (key == null) {
			return null;
		}
		return userMap.get(key);
	}
	
	public List<User> getUsers() {
		Iterator<Entry<String, User>> iterator = userMap.entrySet().iterator();
		List<User> users = new ArrayList<User>();
		while (iterator.hasNext()) {
			Entry<String, User> entry = iterator.next();
			users.add(entry.getValue());
		}
		return users;
	}
	
	/**
	 * 根据用户信息对象唯一标志移除用户信息对象缓存
	 * @param key 用户信息对象对应的唯一标志
	 * @return the previous value associated with key, or null if there was no mapping for key
	 */
	public User removeUser(String key) {
		if (key == null) {
			return null;
		}
		return userMap.remove(key);
	}
}
