package com.gateway.common;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IMQueue {
	public final static BlockingQueue<String> WAIT_QUEUE = new LinkedBlockingQueue<>();
	
	/**
	 * 将用户加入排队队列
	 * @param userKey
	 */
	public static void putToWaitQueue(String userKey) {
		try {
			WAIT_QUEUE.put(userKey);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves and removes the head of this queue, waiting if necessary until an element becomes available.
	 * @return the head of this queue
	 */
	public static String takeFromWaitQueue() {
		String e = null;
		try {
			e = WAIT_QUEUE.take();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		return e;
	}
}
