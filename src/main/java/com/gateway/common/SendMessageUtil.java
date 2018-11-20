package com.gateway.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.gateway.handler.WSMessageHandler;
import com.gateway.model.IMMessage;

/**
 * 发送消息工具类
 * @author guosen
 *
 */
public class SendMessageUtil {
	private static SendMessageUtil smu;
	private ExecutorService service = new ThreadPoolExecutor(2, 10,  0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());;
	
	private SendMessageUtil(){};
	
	public synchronized static SendMessageUtil getInstance() {
		if (smu == null) {
			smu = new SendMessageUtil();
		}
		return smu;
	}
	
	public void sendMessage(String userKey, IMMessage im) {
		service.execute(new WSMessageHandler(userKey, im));
	}
	
	public synchronized IMMessage createMessage(String fromUserName, String toUserName, String content, String msgType){
		IMMessage im = new IMMessage();
		im.setFromUserName(fromUserName);
		im.setToUserName(toUserName);
		im.setMsgType(msgType);
		im.setContent(content);
		im.setCreateTime((int)(System.currentTimeMillis() / 1000));
		return im;
	}
}
