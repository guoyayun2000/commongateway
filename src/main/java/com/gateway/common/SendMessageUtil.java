package com.gateway.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.handler.WSMessageHandler;
import com.gateway.model.IMMessage;
import com.gateway.model.SeatMessage;

/**
 * 发送消息工具类
 * @author guosen
 *
 */
public class SendMessageUtil {
	private static SendMessageUtil smu;
	private ExecutorService service = new ThreadPoolExecutor(2, 10,  0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());;
	private ObjectMapper om = new ObjectMapper();
	
	private SendMessageUtil(){
		om.setSerializationInclusion(Include.NON_NULL);
	};
	
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
	public void sendMessage(String userKey, Object im, int direction) {
		try {
			String pyload = om.writeValueAsString(im);
			service.execute(new WSMessageHandler(userKey, pyload, direction));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
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
	public synchronized IMMessage createAndSendToUser(String fromUserName, String toUserName, String content, String msgType, String userKey){
		IMMessage im = createMessage(fromUserName, toUserName, content, msgType);
		sendMessage(userKey, im, IMConstants.DIRECTION_SEAT_USER);
		return im;
	}
	
	/**
	 * 综合了{@link SendMessageUtil#createMessage}方法和{@link SendMessageUtil#sendMessage}方法
	 * @param fromUserName
	 * @param toUserName
	 * @param content
	 * @param msgType
	 * @param userKey
	 * @param channel
	 * @param sessionId
	 * @return
	 */
	public synchronized SeatMessage<IMMessage> createAndSendToSeat(String fromUserName, String toUserName, String content, String msgType, String channel, String sessionId, int code, String userKey){
		IMMessage im = createMessage(fromUserName, toUserName, content, msgType);
		SeatMessage<IMMessage> sm = new SeatMessage<IMMessage>();
		sm.setCode(code);
		sm.setChannel(channel);
		sm.setSessionId(sessionId);
		sm.setUserId(fromUserName);
		sm.setContent(im);
		sendMessage(userKey, sm, IMConstants.DIRECTION_USER_SEAT);
		return sm;
	}
}
