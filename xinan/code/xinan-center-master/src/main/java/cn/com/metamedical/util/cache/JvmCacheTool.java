package cn.com.metamedical.util.cache;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import cn.com.metamedical.bean.comm.UserBean;

public class JvmCacheTool {
	private static int NORMAL_MINUTE = 30;

	private static int LONG_MINUTE = 120;
	private static int MEDIUM_MINUTE = 30;
	private static int SHORT_MINUTE = 1;
	
	private static int ONE_DAY = 1440;

	private static Cache<String, UserBean> authTokenCache = Caffeine.newBuilder()
			.expireAfterAccess(ONE_DAY, TimeUnit.MINUTES).initialCapacity(100).maximumSize(5000).build();

	private static Cache<String, String> commonCache = Caffeine.newBuilder()
			.expireAfterWrite(NORMAL_MINUTE, TimeUnit.MINUTES).initialCapacity(100).maximumSize(5000).build();

	private static Cache<String, Object> longCache = Caffeine.newBuilder()
			.expireAfterWrite(LONG_MINUTE, TimeUnit.MINUTES).initialCapacity(100).maximumSize(5000).build();

	private static Cache<String, Object> mediumCache = Caffeine.newBuilder()
			.expireAfterWrite(MEDIUM_MINUTE, TimeUnit.MINUTES).initialCapacity(100).maximumSize(5000).build();

	private static Cache<String, Object> shortCache = Caffeine.newBuilder()
			.expireAfterWrite(SHORT_MINUTE, TimeUnit.MINUTES).initialCapacity(100).maximumSize(5000).build();

	public static void putCache(String key, String value) {
		if (StringUtils.isBlank(key)) {
			return;
		}
		commonCache.put(key, value);
	}

	public static boolean putNxCache(String key, String value) {
		if (commonCache.getIfPresent(key) == null) {
			commonCache.put(key, value);
			return true;
		} else {
			return false;
		}
	}

	public static String getCache(String key) {
		if (StringUtils.isBlank(key)) {
			return "";
		}
		return commonCache.getIfPresent(key);
	}

	public static boolean isExsitInCache(String key) {
		return commonCache.getIfPresent(key) == null ? false : true;
	}

	public static void deleteCache(String key) {
		commonCache.invalidate(key);
	}

	public static void putCacheByType(String type, String key, Object value) {
		if (value == null || StringUtils.isBlank(key)) {
			return;
		}
		if ("long".equalsIgnoreCase(type) || "l".equalsIgnoreCase(type)) {
			longCache.put(key, value);

		} else if ("medium".equalsIgnoreCase(type) || "m".equalsIgnoreCase(type)) {
			mediumCache.put(key, value);

		} else if ("short".equalsIgnoreCase(type) || "s".equalsIgnoreCase(type)) {
			shortCache.put(key, value);
		}
	}

	public static Object getCacheByType(String type, String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		if ("long".equalsIgnoreCase(type) || "l".equalsIgnoreCase(type)) {
			return longCache.getIfPresent(key);

		} else if ("medium".equalsIgnoreCase(type) || "m".equalsIgnoreCase(type)) {
			return mediumCache.getIfPresent(key);

		} else if ("short".equalsIgnoreCase(type) || "s".equalsIgnoreCase(type)) {
			return shortCache.getIfPresent(key);
		}
		return null;
	}

	public static void cleanAllCache() {
		longCache.invalidateAll();
		mediumCache.invalidateAll();
		shortCache.invalidateAll();
	}
	
	public static void putTokenInfo(String tokenStr, UserBean user) {
		if (StringUtils.isNoneEmpty(tokenStr)) {
			authTokenCache.put(tokenStr, user);
		}
	}

	public static UserBean getTokenInfo(String tokenStr) {
		return authTokenCache.getIfPresent(tokenStr);
	}

	public static void deleteToken(String tokenStr) {
		authTokenCache.invalidate(tokenStr);
	}
	
	public static boolean isRightToken(String tokenStr) {
		if (authTokenCache.getIfPresent(tokenStr) == null) {
			return false;
		}
		return true;
	}
}
