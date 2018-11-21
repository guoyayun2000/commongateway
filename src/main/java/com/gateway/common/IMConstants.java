package com.gateway.common;
/**
 * 常量类
 * @author guosen
 *
 */
public class IMConstants {
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
	/**
	 * 客户接入消息
	 */
	public static final String MSG_TYPE_NOTICE_IN = "allocation";
	/**
	 * 挂机消息
	 */
	public static final String MSG_TYPE_NOTICE_EXIT = "exit";
	
	/**
	 * 信息方向:用户到坐席
	 */
	public static final int DIRECTION_USER_SEAT = 0;
	/**
	 * 信息方向:坐席到用户
	 */
	public static final int DIRECTION_SEAT_USER = 1;
	
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
}
