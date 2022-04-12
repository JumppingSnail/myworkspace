package cn.com.metamedical.util.comm;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ControllerTool {
	// 统一包装平台返回信息反馈给客户
	public Map<String, Object> success(Object resObj) {
		return responseToClient(resObj);
	}
	public Map<String, Object> responseToClient(Object resObj) {
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("result", "ok");
		resMap.put("data", resObj);
		resMap.put("message", "");
		return resMap;
	}

	public Map<String, Object> error(String errorCode, String errorMessage) {
		return responseToClient(errorCode, errorMessage);
	}
	
	public Map<String, Object> responseToClient(String errorCode, String errorMessage) {
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("result", errorCode);
		resMap.put("message", errorMessage);
		return resMap;
	}
}
