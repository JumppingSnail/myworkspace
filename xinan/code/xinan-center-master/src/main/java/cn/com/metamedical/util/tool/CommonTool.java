package cn.com.metamedical.util.tool;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hutool.core.util.ReflectUtil;

@Component
public class CommonTool {
	private static Logger log = LoggerFactory.getLogger(CommonTool.class);
	private final AtomicInteger serialNumber = new AtomicInteger(1);

	public String getSerialNumber(int len) {
		int resInt = serialNumber.getAndAdd(1);
		String res = "" + resInt;

		if (res.length() >= len) {
			return res.substring(res.length() - len);

		} else {
			int diff = len - res.length();
			for (int i = 0; i < diff; i++) {
				res = "0" + res;
			}
			return res;
		}
	}

	public String genTableId() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 根据map中的key为待包含在zip包中的文件名，value是文件的输入流，
	 * fullZipFileName是导出zip文件的全名（一般放在临时目录，完成导出后再传到前台下载）
	 * */
	public void zipFilesByStream(String fullZipFileName, Map<String, InputStream> inputMap) throws IOException {
		try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(fullZipFileName))) {
			byte[] buf = new byte[1024];
			for (String fileName : inputMap.keySet()) {
				zipOut.putNextEntry(new ZipEntry(fileName));

				int len;
				while ((len = inputMap.get(fileName).read(buf)) > 0) {
					zipOut.write(buf, 0, len);
				}

				zipOut.closeEntry();
				inputMap.get(fileName).close();
			}
			zipOut.flush();
		}
	}

	/**
	 * 单文件打zip包
	 * */
	public void downFilesByStream(String fullZipFileName, InputStream fin) throws IOException {
		try (FileOutputStream fout = new FileOutputStream(fullZipFileName)) {
			byte[] buf = new byte[1024];
			int len;
			while ((len = fin.read(buf)) > 0) {
				fout.write(buf, 0, len);
			}
			fout.flush();
			fin.close();
		}
	}

	/**
	 * 返回6位随机数字验证码
	 * */
	public String genVerifyCode() {
		long num = 0;
		while (num < 100000) {
			num = (System.currentTimeMillis() + new Random().nextInt(1000000)) % 1000000;
		}
		return "" + num;
	}

	/**
	 * 将图片转换成base64编码字符串
	 * */
	public String img2Base64(InputStream fin) throws IOException {
		ByteArrayOutputStream fout = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = fin.read(buffer)) != -1) {
			fout.write(buffer, 0, len);
		}

		byte[] resArr = fout.toByteArray();
		fin.close();
		fout.close();

		return Base64.getEncoder().encodeToString(resArr);
	}

	/**
	 * 得到以当前时间为基准的本月、本周、本日的起始结束日期
	 * flag值： week、month、today
	 * */
	public String[] getTwoDays(String flag) {
		String[] res = { "", "" };
		LocalDate today = LocalDate.now();

		if (flag.equals("week")) {
			res[0] = today.minusDays(today.getDayOfWeek().getValue() - 1).toString() + " 00:00:00";
			res[1] = today.plusDays(7 - today.getDayOfWeek().getValue()).toString() + " 23:59:59";

		} else if (flag.equals("month")) {
			res[0] = today.withDayOfMonth(1).toString() + " 00:00:00";
			res[1] = today.plusMonths(1).withDayOfMonth(1).minusDays(1).toString() + " 23:59:59";

		} else {
			res[0] = today.toString() + " 00:00:00";
			res[1] = today.toString() + " 23:59:59";
		}

		return res;
	}

	/**
	 * 字符串转bigdecimal，处理空串以及转换异常
	 * */
	public BigDecimal str2BigDecimal(String str, BigDecimal defaultValue) {
		if (StringUtils.isBlank(str)) {
			return defaultValue;
		} else {
			try {
				return new BigDecimal(str);
			} catch (Exception e) {
				return defaultValue;
			}
		}
	}
	
	/**
	 * 对象转字符串
	 * */
	public String obj2String(Object obj) {
		if (obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}

	public String double2String(Double dbl) {
		if (dbl == null) {
			return null;
		} else {
			return dbl.toString();
		}
	}

	/**
	 * 解析执行js格式编写的公式，map中包含公式所有用到的变量值
	 * */
	public String calExpressValueWithJs(String expressStr, Map<String, String> inputMap) throws ScriptException {
		if(StringUtils.isBlank(expressStr)) {
			return "true";
		}
		ScriptEngine script = new ScriptEngineManager().getEngineByName("js");
		// 将input参数放入本次脚本执行环境
		if(inputMap!=null) {
            for (String key : inputMap.keySet()) {
                script.put(key, inputMap.get(key));
            }
        }

		return script.eval(expressStr).toString();
	}

	/**
	 * 判断是否为数字
	 * */
	public boolean isNumeric(String str) {
		if (str == null)
			return false;
		Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 对象转成Map
	 * */
	public <T> Map<String, Object> entity2Map(T entity) {
		Map<String, Object> map = new HashMap<>();
		if(entity==null) {
			return map;
		}
		
		Field[] fields = ReflectUtil.getFields(entity.getClass());
		Stream.of(fields).filter(f -> {
			char c = f.getName().charAt(0);
			return c >= 'A' && c <= 'Z';
		}).forEach(f -> {
			try {
				String mapKey = f.get(entity).toString();
				Object mapValue = ReflectUtil.invoke(entity, changeCamelStr(mapKey, "get"));
				map.put(mapKey, mapValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return map;
	}

	/**
	 * 将下划线分隔字符串，装换成驼峰格式，
	 * 例如，project_id，
	 *   如果preStr有值为get，则转换为getProjectId
	 *   如果preStr没值为空，则转换为projectId
	 * */
	private String changeCamelStr(String srcStr, String preStr) {
		StringBuilder sb = new StringBuilder();
		srcStr = srcStr.toLowerCase();

		String[] arr = srcStr.split("_");
		if (StringUtils.isNotBlank(preStr)) {
			sb.append(preStr);
			Stream.of(arr).forEach(str -> sb.append(StringUtils.capitalize(str)));
		} else {
			Stream.of(arr).skip(1).forEach(str -> sb.append(StringUtils.capitalize(str)));
		}

		return sb.toString();
	}
	
	/**
	 * 将对象转换未字符串，如果对象为null，转为""
	 * */
	public String convertObjectToString(Object obj) {
		if(null == obj) {
			return "";
		}else if(StringUtils.isBlank(String.valueOf(obj))){
			return "";
		}else {
			return String.valueOf(obj);
		}
	}

	/**
	 * 将对象转换未字符串，如果对象为null或者转换为字符串后是""，替换为指定的字符
	 * */
	public String convertObjectToString(Object obj,String str) {
		if(null == obj) {
			return str;
		}else if(StringUtils.isBlank(String.valueOf(obj))){
			return str;
		}else {
			return String.valueOf(obj);
		}
	}
	
	/**
	 * 检查对象的属性是否都为空值，
	 * */
	public boolean checkObjFieldIsNull(Object obj) throws IllegalAccessException {
	    boolean flag = true;
	    for(Field f : obj.getClass().getDeclaredFields()){
	        f.setAccessible(true);
	        log.info(f.getName());
	        if(f.get(obj) != null){
	            flag = false;
	            return flag;
	        }
	    }
	    return flag;
	}
}
