package cn.com.metamedical.util.comm;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GloablExceptionHandler {
	private static Logger log = LoggerFactory.getLogger(GloablExceptionHandler.class);

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public Object handleException(Exception e) {
		String msg = e.getMessage();
		if (StringUtils.isBlank(msg)) {
			msg = "服务器端报错";
		}

		log.error("ERROR at Class: " + e.getStackTrace()[0].getClassName() + "   MethodName: "
				+ e.getStackTrace()[0].getMethodName() + "   Line: " + e.getStackTrace()[0].getLineNumber()
				+ "  Message: " + msg);

		Map<String, Object> resMap = new HashMap<>();
		resMap.put("result", "error");
		resMap.put("message", msg);
		resMap.put("data", "");

		return resMap;
	}
}
