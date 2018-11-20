package com.gateway.common;

import com.thoughtworks.xstream.XStream;

public class XmlUtil {
	
	@SuppressWarnings("unchecked")
	public static <T> T toBean(Class<T> clazz, String xml) {
		try {
			System.out.println(xml);
			XStream xstream = new XStream();
			xstream.processAnnotations(clazz);
			xstream.autodetectAnnotations(true);
			xstream.setClassLoader(clazz.getClassLoader());
			return (T) xstream.fromXML(xml);
		} catch (Exception e) {
			System.out.println(e.getCause());
			throw new RuntimeException("[XStream]XML转对象出错");
		}
	}
	
}
