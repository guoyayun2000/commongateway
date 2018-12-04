package com.gateway.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.common.WSConfigPropertiesUtil;
import com.gateway.model.Results;
import com.gateway.model.TulingRobotResult;

@Service
public class TulingRobotService {
	@Autowired
	private WSConfigPropertiesUtil wsputil;
	private static ObjectMapper om = new ObjectMapper().setSerializationInclusion(Include.NON_NULL);

	public String req(String question, int reqType, String userId) {
		String ret = "没有找到";
		Map<String, Object> perception = new HashMap<String, Object>();
		Map<String, Object> text = new HashMap<String, Object>();
		text.put("text", question);
		perception.put("inputText", text);
		Map<String, Object> userInfo = new HashMap<String, Object>();
		userInfo.put("apiKey", wsputil.getProperty("robot_apikey"));
		userInfo.put("userId", userId);

		Map<String, Object> reqParams = new HashMap<String, Object>();
		reqParams.put("reqType", reqType);
		reqParams.put("perception", perception);
		reqParams.put("userInfo", userInfo);

		try {
			String params = om.writeValueAsString(reqParams);
			System.out.println(params);
			RestTemplate template = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(type);
			HttpEntity<String> entity = new HttpEntity<String>(params, headers);

			ResponseEntity<String> result = template.postForEntity(wsputil.getProperty("robot_url"), entity,
					String.class);
			System.out.println("收到机器人==>" + result.getBody());
			TulingRobotResult res = om.readValue(result.getBody(), TulingRobotResult.class);
			if (!wsputil.getProperty("robot_errcode").contains(String.valueOf(res.getIntent().getCode()))) {
				List<Results> list = res.getResults();
				for (Results r : list) {
					if ("text".equals(r.getResultType())) {
						ret = new String(((String) r.getValues().get("text")).getBytes("ISO-8859-1"), "UTF-8");
						System.out.println(ret);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

}
