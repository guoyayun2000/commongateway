package com.gateway.common;
/**
 * 常量类
 * @author guosen
 *
 */
public class IMConstants {
	// ******************信息类型定义*******************
	/**
	 * 文本消息
	 */
	public static final String MSG_TYPE_TEXT = "text";
	/**
	 * 图片消息
	 */
	public static final String MSG_TYPE_IMAGE = "image";
	/**
	 * 语音消息
	 */
	public static final String MSG_TYPE_VOICE = "voice";
	/**
	 * 视频消息
	 */
	public static final String MSG_TYPE_VIDEO = "video";
	/**
	 * 小视频消息
	 */
	public static final String MSG_TYPE_SHORTVIDEO = "shortvideo";
	/**
	 * 位置消息
	 */
	public static final String MSG_TYPE_LOCATION = "location";
	/**
	 * 链接消息
	 */
	public static final String MSG_TYPE_LINK = "link";
	
	// ******************信息发送方向定义*******************
	/**
	 * 信息方向:用户到坐席
	 */
	public static final int DIRECTION_USER_SEAT = 0;
	/**
	 * 信息方向:坐席到用户
	 */
	public static final int DIRECTION_SEAT_USER = 1;
	
	// ******************返回结果定义*******************
	/**
	 * 处理结果-成功
	 */
	public static final int RESULT_SUCCESS = 0;
	/**
	 * 处理结果-异常
	 */
	public static final int RESULT_ERROR = 9999;
	
	// ******************用户状态定义*******************
	/**
	 * 初始状态
	 */
	public static final int USER_STATUS_INIT = 0;
	/**
	 * 机器人状态
	 */
	public static final int USER_STATUS_ROBOT = 1;
	/**
	 * 转人工-排队状态
	 */
	public static final int USER_STATUS_WAIT_ALLOCATION = 2;
	/**
	 * 转人工-等待接入状态
	 */
	public static final int USER_STATUS_WAIT_ACCESS = 3;
	/**
	 * 转人工-接入状态
	 */
	public static final int USER_STATUS_ONLINE = 4;
	
	// ********************坐席状态定义**********************
	/**
	 * 签入
	 */
	public static final int SEAT_STATUS_CHECKIN = 1;
	/**
	 * 签出
	 */
	public static final int SEAT_STATUS_CHECKOUT = 2;
	/**
	 * 忙碌
	 */
	public static final int SEAT_STATUS_BUSY = 3;
	/**
	 * 空闲
	 */
	public static final int SEAT_STATUS_FREE = 4;
	
	// ********************指令定义**********************
	/**
	 * 指令-转人工
	 */
	public static final int CODE_ALLOCATION = 100;
	/**
	 * 指令-坐席接入
	 */
	public static final int CODE_ACCEPT = 101;
	/**
	 * 指令-聊天
	 */
	public static final int CODE_CHAT = 102;
	/**
	 * 指令-客户挂机
	 */
	public static final int CODE_HANG_UP_BY_USER = 103;
	/**
	 * 指令-坐席挂机
	 */
	public static final int CODE_HANG_UP_BY_SEAT = 104;
}
