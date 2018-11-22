package com.gateway.model;

/**
 * 消息实体类
 * @author guosen
 *
 */
public class IMMessage {
	/**
	 * 接收方微信号
	 */
	private String toUserName;
	/**
	 * 发送方帐号
	 */
	private String fromUserName;
	/**
	 * 消息创建时间 （整型）
	 */
	private int createTime;
	/**
	 * 消息类型
	 */
	private String msgType;
	/**
	 * 文本消息内容
	 */
	private String content;
	/**
	 * 消息id，64位整型
	 */
	private String msgId;

	/**
	 * 图片链接（由系统生成）
	 */
	private String picUrl;
	/**
	 * 消息媒体id，可以调用多媒体文件下载接口拉取数据
	 */
	private String mediaId;
	/**
	 * 多媒体格式(amr, speex等)
	 */
	private String format;
	/**
	 * 语音识别结果
	 */
	private String recognition;

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

}
