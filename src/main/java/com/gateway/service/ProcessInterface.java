package com.gateway.service;

import com.gateway.model.IMMessage;

/**
 * 业务处理接口
 * @author guosen
 *
 */
public interface ProcessInterface {
	/**
	 * 
	 * @param userKey
	 * @param message
	 * @return true表示继续执行下一个流程,false表示到本流程结束
	 */
	public boolean service(String userKey, IMMessage message);
}
