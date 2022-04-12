package cn.com.metamedical.service;

import java.util.Map;

public interface IAuthService {
	public Map<String, Object> login(String userCode, String passwd);
}
