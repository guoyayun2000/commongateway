package com.gateway.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.common.UserMemoryCache;
import com.gateway.common.WSConfigPropertiesUtil;
import com.gateway.model.IMMessage;
import com.gateway.model.User;
import com.gateway.service.ProcessInterface;
/**
 * 用户websocket连接处理类
 * @author guosen
 *
 */
public class UserWebSocketHandler extends TextWebSocketHandler {
	private WSConfigPropertiesUtil wcpu;
	
	public UserWebSocketHandler(WSConfigPropertiesUtil wcpu) {
		this.wcpu = wcpu;
	}

	/**
	 * 1、解析URL参数
	 * 2、判断是否有userId
	 * 3、判断是否缓存会话
	 * 3、判断是否缓存用户信息
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String query = session.getUri().getQuery();
		Map<String, String> params = new HashMap<String, String>();
		if (query != null && !"".equals(query.trim())) {
			query = query.trim();
			String[] queryParams = query.split("&");
			for (String param : queryParams) {
				String[] qp = param.split("=");
				params.put(qp[0], qp[1]);
			}
		}
		if (!params.containsKey("userId")) {
			// TODO: 提示错误
			session.close();
		}
		
		String userId = params.get("userId");
		String channel = params.get("channel");
		
		String userKey = userId + "," + channel;
		
		UserMemoryCache.getInstance().putWebSocketSession(userKey, session);
		User user = UserMemoryCache.getInstance().getUser(userKey);
		if (user == null) {
			user = new User();
			user.setUserId(userId);
			user.setChannel(channel);
			user.setParamMap(params);
			user.setSessionId(session.getId());
			List<String> steps = new ArrayList<String>();
			String step_temp = wcpu.getProperty("channel", channel);
			if (step_temp != null && !"".equals(step_temp)) {
				String[] temps = step_temp.split(",");
				for (int i = 0; i < temps.length; i++) {
					steps.add(temps[i]);
				}
			}
			user.setProcessStep(steps);
			UserMemoryCache.getInstance().putUser(userKey, user);
		}
		
		// TODO: 欢迎语
	}

	/**
	 * 1、将websocket消息转换为IM消息对象
	 * 2、获取用户信息
	 * 3、获取用户流程信息
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		if (payload == null || "".equals(payload)) {
			return;
		}
		
		try {
			ObjectMapper om = new ObjectMapper();
			IMMessage imm = om.readValue(payload, IMMessage.class);
			
			String userKey = UserMemoryCache.getInstance().getUserKey(session);
			System.out.println("handler==>" + userKey);
			User user = UserMemoryCache.getInstance().getUser(userKey);
			List<String> processSteps = user.getProcessStep();
			int step = user.getStep();
			while (processSteps.size() > step) {
				// 获取流程名称
				String processStep = processSteps.get(step);
				// 根据流程名称获取流程
				ProcessInterface pif = user.getProcessServices().get(processStep);
				if (pif == null) {
					// 根据流程名称获取流程类,用反射创建流程对象
					pif = (ProcessInterface) Class.forName(wcpu.getProperty(processStep)).newInstance();
					UserMemoryCache.getInstance().getUser(userKey).getProcessServices().put(processStep, pif);
				}
				boolean flag = pif.service(userKey, imm);
				if (flag) {
					step = step + 1;
					UserMemoryCache.getInstance().getUser(userKey).setStep(step);
					UserMemoryCache.getInstance().getUser(userKey).getProcessServices().remove(processStep);
				} else {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		exception.printStackTrace();
		session.close();
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("closed==>" + status.getCode() + "\t" + status.getReason());
		session.close();
	}

}
