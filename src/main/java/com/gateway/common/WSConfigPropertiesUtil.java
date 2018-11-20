package com.gateway.common;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

@Component
public class WSConfigPropertiesUtil {
	@Value("${wsconfig.name}")
	private String resourceName;
	
	private Properties properties = null;
	
	private long loadTime = 0;
	
	public WSConfigPropertiesUtil(){
	}
	
	private synchronized void loadProperties() {
		try {
			properties = PropertiesLoaderUtils.loadAllProperties(resourceName);
			loadTime = System.currentTimeMillis();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getProperty(String name) {
		long current = System.currentTimeMillis();
		if ((current - loadTime) > 60000) {
			loadProperties();
		}
		return properties.getProperty(name);
	}

	public String getProperty(String name, String channel) {
		return getProperty(name + "_" + channel);
	}
	
	public void setResourceName(String resourceName) {
		System.out.println(resourceName);
		this.resourceName = resourceName;
	}
}
