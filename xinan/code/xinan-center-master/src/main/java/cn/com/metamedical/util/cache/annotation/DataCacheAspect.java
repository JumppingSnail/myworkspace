package cn.com.metamedical.util.cache.annotation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.com.metamedical.util.cache.JvmCacheTool;
import cn.com.metamedical.util.tool.JsonUtil;
import cn.com.metamedical.util.tool.MD5Util;

/**
 * 用于处理缓存的切片，切片的处理点是系统中使用了自定义注解的所有方法
 * 方式是使用“类名+方法名+参数的json串”组成的字符串的md5值作为key，函数结果作为value，存入缓存
 * 
 * @author Allen
 */
@Aspect
@Component
@Order(0)
public class DataCacheAspect {

	@Around(value = "@annotation(dataCache)")
	public Object doCache(ProceedingJoinPoint joinPoint, DataCacheAnnotation dataCache) throws Throwable {
		String cacheType = dataCache.value();

		Signature signature = joinPoint.getSignature();
		String clazzName = signature.getDeclaringTypeName();
		String methodName = signature.getName();
		Object[] args = joinPoint.getArgs();
		String argsJson = JsonUtil.allToJson(args);

		StringBuilder sb = new StringBuilder();
		sb.append(clazzName).append("##");
		sb.append(methodName).append("##");
		sb.append(argsJson);
		String key = MD5Util.get32BitMd5EncString(sb.toString());

		Object result = JvmCacheTool.getCacheByType(cacheType, key);

		if (result == null) {
			result = joinPoint.proceed(); // continue on the intercepted method
			JvmCacheTool.putCacheByType(cacheType, key, result);
		}

		if (dataCache.cloneFlag()) {
			return cloneObj(result);
		}
		return result;
	}

	private Object cloneObj(Object obj) {
		Object cloneObj = null;
		try {
			// 写入字节流
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream obs = new ObjectOutputStream(out);
			obs.writeObject(obj);
			obs.close();

			// 分配内存，写入原始对象，生成新对象
			ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(ios);
			// 返回生成的新对象
			cloneObj = ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cloneObj;
	}
}
