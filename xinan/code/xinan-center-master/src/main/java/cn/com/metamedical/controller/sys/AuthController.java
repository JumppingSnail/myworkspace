package cn.com.metamedical.controller.sys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.metamedical.bean.comm.MenuTotalBean;
import cn.com.metamedical.bean.comm.UserBean;
import cn.com.metamedical.service.IAuthService;
import cn.com.metamedical.util.auth.UserLoginToken;
import cn.com.metamedical.util.cache.JvmCacheTool;
import cn.com.metamedical.util.comm.ControllerTool;
import cn.com.metamedical.util.constant.CommonConstant;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private ControllerTool controllerTool;

	@Autowired
	private IAuthService authService;

	/**
	 * 登录
	 */
	@PostMapping("/login.do")
	public Map<String, Object> login(String userCode, String password) {
		Map<String, Object> resMap = authService.login(userCode, password);

		if (resMap.get("error") != null) {
			return controllerTool.error(resMap.get("error").toString(), "Error Happens!");
		}

		String token = resMap.get("token").toString();
		UserBean userBean = (UserBean) resMap.get("user");

		JvmCacheTool.putTokenInfo(token, userBean);

		return controllerTool.success(token);
	}

	@UserLoginToken
	@GetMapping("/logout.do")
	public Map<String, Object> logout(HttpServletRequest request) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		if (StringUtils.isNotBlank(token)) {
			JvmCacheTool.deleteToken(token);
		}

		return controllerTool.success("ok");
	}

	@UserLoginToken
	@GetMapping("/getUserInfo.do")
	public Map<String, Object> getUserInfo(HttpServletRequest request) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean user = JvmCacheTool.getTokenInfo(token);

		return controllerTool.success(user);
	}

	@UserLoginToken
	@GetMapping("/getMenus.do")
	public MenuTotalBean getMenus(HttpServletRequest request) {
		String token = request.getHeader(CommonConstant.TOKEN_KEY);
		UserBean user = JvmCacheTool.getTokenInfo(token);

		return user.getMenuBean();
	}
	
	@UserLoginToken
	@PostMapping("/unlockUser.do")
	public Map<String, Object> unlockUser(String userCode) {
		JvmCacheTool.deleteCache(userCode + "@xinan-login");
		return controllerTool.responseToClient("ok");
	}
}
