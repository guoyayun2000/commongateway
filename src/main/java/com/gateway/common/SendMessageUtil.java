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
	
	/**
	 * 根据userKey通过websocket发送信息
	 * @param userKey userKey
	 * @param im 需要通过websocket发送的信息
	 */
	public void sendMessage(String userKey, IMMessage im, int direction) {
		service.execute(new WSMessageHandler(userKey, im, direction));
	}
	
	/**
	 * 创建websocket需要的信息发送实体类
	 * @param fromUserName
	 * @param toUserName
	 * @param content
	 * @param msgType
	 * @return
	 */
	public synchronized IMMessage createMessage(String fromUserName, String toUserName, String content, String msgType){
		IMMessage im = new IMMessage();
		im.setFromUserName(fromUserName);
		im.setToUserName(toUserName);
		im.setMsgType(msgType);
		im.setContent(content);
		im.setCreateTime((int)(System.currentTimeMillis() / 1000));
		return im;
	}
	
	/**
	 * 综合了{@link SendMessageUtil#createMessage}方法和{@link SendMessageUtil#sendMessage}方法
	 * @param fromUserName
	 * @param toUserName
	 * @param content
	 * @param msgType
	 * @param userKey
	 * @param direction 信息方向:0用户到坐席 1坐席到用户
	 * @return
	 */
	public synchronized IMMessage createAndSendMessage(String fromUserName, String toUserName, String content, String msgType, String userKey, int direction){
		IMMessage im = createMessage(fromUserName, toUserName, content, msgType);
		sendMessage(userKey, im, direction);
		return im;
	}
	
	
}
